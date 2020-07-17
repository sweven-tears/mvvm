package pers.sweven.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import pers.sweven.rabbitmq.common.Config;

/**
 * Created by Sweven on 2020/7/17--10:02.
 * Email: sweventears@163.com
 */
public class RabbitMQPush implements IPush {
    private ConnectionFactory factory;
    private Channel channel;


    public RabbitMQPush() {
        factory = new ConnectionFactory();
        setupConnectFactory();
    }

    @Override
    public void receiver(@NotNull String message) {
        System.out.println("RabbitMQ-receive:" + message);
    }

    @Override
    public void send(@NotNull final String message) throws IOException, TimeoutException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    invoke(getChannel(), message);
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void setupConnectFactory() {
        factory.setHost(Config.RabbitMQ_HOST);
        factory.setVirtualHost("/");
        factory.setPort(Config.RabbitMQ_PORT);
        factory.setUsername(Config.RabbitMQ_USERNAME);
        factory.setPassword(Config.RabbitMQ_PASSWORD);
    }

    public Channel getChannel() {
        if (channel != null) return channel;
        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            return channel;
        }
        return channel;
    }

    private void invoke(Channel channel, String messageBodyBytes) throws IOException, TimeoutException {
        try {
            channel.basicPublish("amq.direct", "yunban_rpc_msg",
                    new AMQP.BasicProperties.Builder()
                            .contentType("text/plain")
                            .deliveryMode(2)
                            .priority(1)
                            .userId("guest")
                            .replyTo("bleReplyTo")
                            .build(),
                    messageBodyBytes.getBytes());
            System.out.println("Invoked, msg: " + messageBodyBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            channel.close();
            channel.getConnection().close();
            this.channel = null;
        }
    }
}

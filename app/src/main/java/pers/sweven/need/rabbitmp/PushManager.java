package pers.sweven.need.rabbitmp;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import pers.sweven.rabbitmq.RabbitMQPush;

/**
 * Created by Sweven on 2020/7/17--11:08.
 * Email: sweventears@163.com
 */
public class PushManager extends RabbitMQPush {
    private static PushManager instance;

    private Queue<String> messages = new ArrayDeque<>();

    public static PushManager getInstance() {
        if (instance == null) {
            synchronized (PushManager.class) {
                instance = new PushManager();
            }
        }
        return instance;
    }

    public PushManager() {
        super();
    }

    @Override
    public void send(@NotNull String message) {
        try {
            super.send(message);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiver(@NotNull String message) {
        super.receiver(message);
        // TODO: 2020/7/17 deal receive's message.
        messages.add(message);
    }

    public String getMessage() {
        return messages.poll();
    }
}

package pers.sweven.rabbitmq

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.rabbitmq.client.QueueingConsumer
import pers.sweven.rabbitmq.common.Config
import java.lang.Thread.sleep
import java.lang.reflect.ParameterizedType
import java.util.*

open abstract class RabbitMQService<T : RabbitMQPush> : Service() {

    private var rabbitMQPush: T? = null
    private var thread: Thread? = null
    private val timer = Timer()
    private var timerTask: TimerTask? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val parameterizedType: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        val p = (parameterizedType.actualTypeArguments[0] as Class<*>).newInstance() as T
        rabbitMQPush = p.javaClass.getDeclaredMethod("getInstance").invoke(p) as T?
        if (useThread()) {
            consume2()
        } else {
            consume()
        }
        return START_STICKY
    }

    abstract fun useThread(): Boolean

    private fun consume() {
        timerTask = object : TimerTask() {
            override fun run() {
                val channel = rabbitMQPush!!.channel
                channel.basicQos(1)
                val queueDeclare = channel.queueDeclare()
                channel.queueBind(queueDeclare.queue, Config.RabbitMQ_EXCHANGE, Config.getSerialId())
                val consumer = QueueingConsumer(channel)
                channel.basicConsume(queueDeclare.queue, true, consumer)
                val delivery = consumer.nextDelivery()
                val message = String(delivery.body)
                rabbitMQPush!!.receiver(message)
            }
        }
        timer.schedule(timerTask, 0, 3000)
    }

    private fun consume2() {
        thread = Thread {
            while (true) {
                try {
                    val channel = rabbitMQPush!!.channel
                    channel.basicQos(1)
                    val queueDeclare = channel.queueDeclare()
                    channel.queueBind(queueDeclare.queue, Config.RabbitMQ_EXCHANGE, Config.getSerialId())
                    val consumer = QueueingConsumer(channel)
                    channel.basicConsume("getBleInfo", true, consumer)
                    while (true) {
                        val delivery = consumer.nextDelivery()
                        val message = String(delivery.body)
                        rabbitMQPush!!.receiver(message)
                    }
                } catch (e: Exception) {
                    sleep(3000)
                }
            }
        }
        thread?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        thread?.interrupt()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}
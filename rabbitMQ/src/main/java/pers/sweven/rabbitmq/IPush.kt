package pers.sweven.rabbitmq

import java.io.IOException
import java.util.concurrent.TimeoutException

interface IPush {
    fun receiver(message: String)

    @Throws(IOException::class, TimeoutException::class)
    fun send(message: String)

    fun setupConnectFactory()
}
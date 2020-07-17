package pers.sweven.rabbitmq.common

import java.net.NetworkInterface
import java.net.SocketException
import java.security.MessageDigest

object Config {
    const val RabbitMQ_HOST = "120.77.1.161"
    const val RabbitMQ_PORT = 5672
    const val RabbitMQ_USERNAME = "guest"
    const val RabbitMQ_PASSWORD = "guest"
    const val RabbitMQ_EXCHANGE = "amq.direct"

    @JvmStatic
    fun getSerialId(): String? {
        var serialnum: String? = null
        try {
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java, String::class.java)
            serialnum = get.invoke(c, "ro.serialno", "unknown") as String
        } catch (ignored: Exception) {
        }
        if (serialnum == null || serialnum == "unknown") {
            serialnum = MD5(getMacAddress())
        }
        return serialnum
    }

    @JvmStatic
    fun getMacAddress(): String {
        var macAddress: String? = null
        val buf = StringBuffer()
        var networkInterface: NetworkInterface? = null
        try {
            networkInterface = NetworkInterface.getByName("eth1")
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0")
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02"
            }
            val addr = networkInterface.hardwareAddress
            for (b in addr) {
                buf.append(String.format("%02X:", b))
            }
            if (buf.length > 0) {
                buf.deleteCharAt(buf.length - 1)
            }
            macAddress = buf.toString()
        } catch (e: SocketException) {
            e.printStackTrace()
            return "02:00:00:00:00:02"
        }
        return macAddress
    }

    @JvmStatic
    fun MD5(sourceStr: String): String? {
        var result: String? = null
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(sourceStr.toByteArray())
            val b = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            result = buf.toString().substring(8, 24).toUpperCase()
            // System.out.println("MD5(" + sourceStr + ",32) = " + result);
            // System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (e: java.lang.Exception) {
            result = "unknown"
        }
        return result
    }
}
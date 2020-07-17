package pers.sweven.need.rabbitmp;

import android.content.Intent;

import org.jetbrains.annotations.Nullable;

import pers.sweven.rabbitmq.RabbitMQService;

/**
 * Created by Sweven on 2020/7/17--10:20.
 * Email: sweventears@163.com
 */
public class MQService extends RabbitMQService<PushManager> {

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean useThread() {
        return false;
    }
}

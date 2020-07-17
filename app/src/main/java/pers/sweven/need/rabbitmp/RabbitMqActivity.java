package pers.sweven.need.rabbitmp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pers.sweven.mvvm.base.BaseActivity;
import pers.sweven.mvvm.base.BaseViewModel;
import pers.sweven.need.App;
import pers.sweven.need.R;
import pers.sweven.need.databinding.ActivityRabbitMqBinding;

public class RabbitMqActivity extends BaseActivity<ActivityRabbitMqBinding, BaseViewModel> {

    @Override
    protected void initParams(Bundle bundle) {
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_rabbit_mq;
    }

    @Override
    protected void initData() {
        startService(new Intent(this, MQService.class));
    }

    public void onSend(View view) {
        String message = getBinding().input.getText().toString();
        if (message.trim().equals("")) {
            App.toast("do not null.");
            return;
        }
        runOnUiThread(() -> PushManager.getInstance().send(message));

    }

    public void onReceive(View view) {
        getBinding().receiveMessage.setText(PushManager.getInstance().getMessage());
    }
}
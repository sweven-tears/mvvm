package pers.sweven.need;

import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import pers.sweven.mvvm.base.BaseActivity;
import pers.sweven.mvvm.base.BaseViewModel;
import pers.sweven.need.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    @Override
    protected void initParams(Bundle bundle) {
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        showLoadingDialog();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dismissLoadingDialog();
            }
        }, 1000 * 10);
    }

    @Override
    protected void doBusiness() {

    }
}
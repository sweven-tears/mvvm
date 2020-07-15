package pers.sweven.need;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sweven on 2020/7/14--15:14.
 * Email: sweventears@163.com
 */
public class App extends Application {
    private static App app;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static void toast(String msg) {
        if (getApp() == null) return;
        Toast.makeText(getApp(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void log(String msg) {
        Log.i("life", msg);
    }
}

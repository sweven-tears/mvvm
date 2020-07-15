package pers.sweven.mvvm.common;

import java.util.Stack;

import pers.sweven.mvvm.base.RootActivity;

/**
 * Created by Sweven on 2020/7/13--10:54.
 * Email: sweventears@163.com
 */
public class ActivityManager {
    private static ActivityManager instance;

    private Stack<RootActivity> activityStack = new Stack<>();

    public static ActivityManager get() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void add(RootActivity activity) {
        activityStack.add(activity);
    }

    public void remove(RootActivity activity) {
        activityStack.remove(activity);
    }

    public int count() {
        return activityStack.size();
    }

    public void finishAll() {
        while (!activityStack.empty()) {
            RootActivity activity = activityStack.remove(0);
            activity.finish();
        }
    }

    public RootActivity getTopActivity() {
        if (count() == 0) return null;
        return activityStack.get(count() - 1);
    }
}

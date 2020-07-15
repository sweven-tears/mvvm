package pers.sweven.mvvm.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Sweven on 2020/7/13--14:20.
 * Email: sweventears@163.com
 */
public class RootDialog extends Dialog {

    public RootDialog(@NonNull Context context) {
        super(context);
    }

    public RootDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected RootDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}

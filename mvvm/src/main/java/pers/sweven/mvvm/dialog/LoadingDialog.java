package pers.sweven.mvvm.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import pers.sweven.mvvm.R;
import pers.sweven.mvvm.base.BaseDialog;
import pers.sweven.mvvm.databinding.DialogLoadingBinding;

/**
 * Created by Sweven on 2020/7/13--14:19.
 * Email: sweventears@163.com
 */
public class LoadingDialog extends BaseDialog<DialogLoadingBinding> {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int bindLayout() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void doBusiness() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}

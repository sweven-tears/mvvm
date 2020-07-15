package pers.sweven.mvvm.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import pers.sweven.mvvm.R;

/**
 * Created by Sweven on 2020/7/13--14:19.
 * Email: sweventears@163.com
 */
public abstract class BaseDialog<T extends ViewDataBinding> extends RootDialog {
    private T binding;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.NormalDialogStyle);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), bindLayout(), null, false);
        setContentView(binding.getRoot());
        initView();
        doBusiness();
    }

    public T getBinding() {
        return binding;
    }

    protected abstract int bindLayout();

    protected abstract void initView();

    protected abstract void doBusiness();
}

package pers.sweven.mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pers.sweven.mvvm.common.ActivityManager;
import pers.sweven.mvvm.dialog.LoadingDialog;

/**
 * Created by Administrator on 2020/7/13--9:54.
 * Email: sweventears@163.com
 */
public abstract class BaseActivity<T extends ViewDataBinding, VM extends BaseViewModel> extends RootActivity {

    private T binding;
    private VM viewModel;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.get().add(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initParams(bundle);
        }
        initLoadingDialog();
        if (bindLayout() > 0) {
            binding = DataBindingUtil.setContentView(this, bindLayout());
        }
        viewModel = initModel();
        initData();
        initObservable();
    }

    /**
     * 初始化等待 dialog
     */
    private void initLoadingDialog() {
        loadingDialog = new LoadingDialog(this);
    }

    /**
     * @return 获取viewModel
     */
    private VM initModel() {
        VM vm = null;
        try {
            ParameterizedType p = (ParameterizedType) this.getClass().getGenericSuperclass();
            if (p.getActualTypeArguments().length < 2) return null;
            Class<VM> clz = (Class<VM>) p.getActualTypeArguments()[1];
            vm = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (vm != null) vm.attachLifecycle(this);
        return vm;
    }

    /**
     * 初始化上一个activity传递的 bundle
     *
     * @param bundle 。
     */
    protected abstract void initParams(Bundle bundle);

    /**
     * @return layoutId
     */
    protected abstract int bindLayout();

    /**
     * 初始化组件
     */
    protected abstract void initData();

    /**
     * 初始化观察者
     */
    protected void initObservable() {
        if (viewModel == null) return;
        viewModel.getShowLoading().observe(this, val -> {
            if (val != null && val) {
                showLoadingDialog();
            } else {
                dismissLoadingDialog();
            }
        });
        viewModel.getThrowableLiveData().observe(this, this::toastError);
    }

    /**
     * 显示 dialog
     */
    protected void showLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    /**
     * 关闭 dialog
     */
    protected void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public T getBinding() {
        return binding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.get().remove(this);
        if (viewModel != null) {
            viewModel.detach();
            viewModel = null;
        }
    }

    protected void toastError(Throwable throwable) {
        if (throwable == null) return;
        throwable.printStackTrace();
    }

    //*******************fragment*****************//
    private List<RootFragment> fragments = new ArrayList<>();
    private RootFragment currentFragment = null;
    private int currentIndex = 0;
    private int fragmentId;

    public void initFragment(RootFragment... fragment) {
        fragments.addAll(Arrays.asList(fragment));
    }

    public void replaceFragment(int containerId, RootFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void replaceFragmentWithStack(int containerId, RootFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment.getClass().getCanonicalName())
                .commitAllowingStateLoss();
    }

    public void removeFragment(RootFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();
    }
}

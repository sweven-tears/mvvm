package pers.sweven.mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import pers.sweven.mvvm.dialog.LoadingDialog;

/**
 * Created by Sweven on 2020/7/14--10:40.
 * Email: sweventears@163.com
 */
public abstract class BaseFragment<T extends ViewDataBinding, VM extends BaseViewModel> extends RootFragment {
    private T binding;
    private VM viewModel;

    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, bindLayout(), null, false);
        } else {
            ViewGroup parent = (ViewGroup) binding.getRoot().getParent();
            if (parent != null) {
                parent.removeView(binding.getRoot());
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = initViewModel();
        initParams();
        initViews();
        onBusiness();
        initObservable();
    }

    private VM initViewModel() {
        VM vm = null;
        try {
            ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
            if (superclass.getActualTypeArguments().length < 2) return null;
            Class<VM> clazz = (Class<VM>) superclass.getActualTypeArguments()[1];
            vm = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (vm != null) vm.attachLifecycle(this);
        return vm;
    }

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

    protected RootActivity getHostActivity() {
        return (RootActivity) getActivity();
    }

    protected void showLoadingDialog() {
        if (getHostActivity() != null) {
            ((BaseActivity) getHostActivity()).showLoadingDialog();
        }
    }

    protected void dismissLoadingDialog() {
        if (getHostActivity() != null) {
            ((BaseActivity) getHostActivity()).dismissLoadingDialog();
        }
    }

    protected abstract int bindLayout();

    protected abstract void initParams();

    protected abstract void initViews();

    protected abstract void onBusiness();

    protected void toastError(Throwable throwable) {
        if (throwable == null) return;
        throwable.printStackTrace();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.detach();
            viewModel = null;
            binding = null;
        }
    }

    public VM getModel() {
        return viewModel;
    }

    public T getBinding() {
        return binding;
    }

    //*******************fragment*****************//
    public void replaceFragment(int containerId, RootFragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void replaceFragmentWithStack(int containerId, RootFragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment.getClass().getCanonicalName())
                .commitAllowingStateLoss();
    }

    public void removeFragment(RootFragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();
    }
}

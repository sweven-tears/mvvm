package pers.sweven.mvvm.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by Administrator on 2020/7/13--9:21.
 * Email: sweventears@163.com
 */
public class BaseViewModel extends ViewModel {
    private LifecycleProvider provider;
    private MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    private MutableLiveData<Throwable> mThrowableLiveData = new MutableLiveData<>();

    public void attachLifecycle(LifecycleProvider lifecycleProvider) {
        this.provider = lifecycleProvider;
    }

    public void detach() {
        this.provider = null;
    }

    public LifecycleProvider getProvider() {
        return this.provider;
    }

    public MutableLiveData<Throwable> getThrowableLiveData() {
        return mThrowableLiveData;
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return showLoading;
    }


}

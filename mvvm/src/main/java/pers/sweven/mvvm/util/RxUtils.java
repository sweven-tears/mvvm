package pers.sweven.mvvm.util;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pers.sweven.mvvm.base.BaseViewModel;

/**
 * Created by Sweven on 2020/7/13--9:34.
 * Email: sweventears@163.com
 */
public class RxUtils {

    public static <T> LifecycleTransformer<T> bindToLifecycle(LifecycleProvider provider) {
        if (provider instanceof RxAppCompatActivity) {
            return provider.bindUntilEvent(ActivityEvent.DESTROY);
        } else if (provider instanceof RxFragment) {
            return provider.bindUntilEvent(FragmentEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("lifecycle bind error");
        }
    }

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final BaseViewModel viewModel) {
        return applySchedulers(viewModel, true);
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final BaseViewModel viewModel, final boolean showLoading) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> viewModel.getShowLoading().setValue(showLoading))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> viewModel.getShowLoading().setValue(!showLoading))
                .compose(bindToLifecycle(viewModel.getProvider()));
    }

}

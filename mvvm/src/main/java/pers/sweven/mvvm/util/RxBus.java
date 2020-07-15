package pers.sweven.mvvm.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import pers.sweven.mvvm.base.BaseViewModel;

/**
 * Created by Administrator on 2020/7/13--10:04.
 * Email: sweventears@163.com
 */
public class RxBus {
    private static RxBus instance;
    private Subject<Object> mBus;

    public RxBus() {
        this.mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus get() {
        if (instance == null) {
            synchronized (RxBus.class) {
                instance = new RxBus();
            }
        }
        return instance;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> clazz) {
        return mBus.ofType(clazz);
    }

    public <T> Observable<T> toSafeObservable(Class<T> clazz, BaseViewModel viewModel) {
        return mBus.ofType(clazz).compose(RxUtils.applySchedulers(viewModel, false));
    }

}

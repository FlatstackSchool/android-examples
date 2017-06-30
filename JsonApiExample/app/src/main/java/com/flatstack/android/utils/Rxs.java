package com.flatstack.android.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ilya Eremin on 4/25/16.
 */
public class Rxs {

    public static <T> Observable.Transformer<T, T> doInBackgroundDeliverToUI() {
        return observable -> observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> doInBackgroundDeliverToBackground() {
        return observable -> observable.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io());
    }
}
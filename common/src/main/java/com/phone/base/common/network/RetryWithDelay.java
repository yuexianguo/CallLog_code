package com.phone.base.common.network;

import com.phone.base.common.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/8/19
 */
public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {
    private static final String TAG = "RetryWithDelay";

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            LogUtil.d(TAG, "apply: count=" + retryCount);
            if (throwable.getMessage() != null) {
                LogUtil.d(TAG, "apply: error=" + throwable.getMessage());
            }
            if (++retryCount <= maxRetries) {
                return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
            }
            // Max retries hit. Just pass the error along.
            return Observable.error(throwable);
        });
    }
}

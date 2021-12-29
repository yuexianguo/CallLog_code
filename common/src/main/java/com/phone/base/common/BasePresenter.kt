package com.phone.base.common

import androidx.lifecycle.LifecycleObserver

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/9/15
 */
interface BasePresenter<V> : LifecycleObserver {
    fun start()

    fun cancel()

    fun attach(v: V?)

    fun detach()

    fun destroy()
}
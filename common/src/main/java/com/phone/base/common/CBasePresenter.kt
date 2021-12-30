package com.phone.base.common

import androidx.lifecycle.LifecycleObserver

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/9/15
 */
interface CBasePresenter<V> : LifecycleObserver {

    fun start()

    fun cancel()

    fun attach(v: V?)

    fun detach()

    fun destroy()
}
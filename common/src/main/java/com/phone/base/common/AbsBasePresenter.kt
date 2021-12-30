package com.phone.base.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.phone.base.common.data.source.BaseDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/4/23
 */
/**
 * @param <V> View UI
 * @param <R> Repository
</R></V> */
abstract class AbsBasePresenter<V : BaseView?, D : BaseDataSource?>(d: D) : BasePresenter<V> {
    //view
    protected var baseView: V? = null
        private set

    //data
    protected var baseRepository: D?
        private set
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    init {
        baseRepository = d
    }

    override fun attach(v: V?) {
        this.baseView = v
        baseView?.lifecycle?.addObserver(this)
    }

    override fun cancel() {
        mCompositeDisposable?.clear()
        baseView?.setRefreshing(false)
        baseView?.dismissLoading()
    }

    protected fun addOperatorDisposable(disposable: Disposable) {
        mCompositeDisposable?.add(disposable)
    }

    override fun detach() {
        cancel()
        baseView?.lifecycle?.removeObserver(this)
        baseView = null
    }

    override fun destroy() {
        detach()
        mCompositeDisposable = null
        baseRepository = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        destroy()
    }
}
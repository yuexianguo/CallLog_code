package com.phone.base.common

import androidx.lifecycle.coroutineScope
import com.phone.base.common.data.source.BaseDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * description :
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/2/4
 */
/**
 * @param <V> View
 * @param <R> CoroutineScope
</R></V> */
abstract class AbsCBasePresenter<V : BaseView?, D : BaseDataSource?>(d: D) : CBasePresenter<V> {
    //view
    protected var baseView: V? = null
        private set

    //data
    protected var baseRepository: D?
        private set

    protected var scope: CoroutineScope? = null

    init {
        baseRepository = d
    }

    override fun start() {

    }

    override fun cancel() {
//        scope?.cancel()
        baseView?.setRefreshing(false)
        baseView?.dismissLoading()
    }

    override fun attach(v: V?) {
        baseView = v
        scope = baseView?.lifecycle?.coroutineScope
        baseView?.lifecycle?.addObserver(this)
    }

    override fun detach() {
        cancel()
        baseView?.lifecycle?.removeObserver(this)
        baseView = null
    }

    override fun destroy() {
        detach()
        baseRepository = null
        scope = null
    }

}
package com.phone.base.common

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.phone.base.common.dialog.EditNameDialogFragment
import com.phone.base.common.dialog.MsgDialogFragment
import com.phone.base.common.utils.LogUtil

private const val TAG = "BaseFragment"

@Suppress("DEPRECATION")
abstract class BaseFragment : Fragment(), BaseView {
    private var isViewPrepared = false
    private var hasFetchData = false
    private var mRootView: View? = null
    private var mCallback: OperationCallback? = null
    private var mEditNameDialogFragment: EditNameDialogFragment? = null
    private var mMsgDialog: MsgDialogFragment? = null

    private val TAG_BASE_EDITNAME_DIALOG = "EditNameDialogFragment"
    private val TAG_BASE_MSG_DIALOG = "BaseMsgDialogFragment"

    @get:LayoutRes
    protected abstract val layoutId: Int
    protected abstract fun initViews()
    protected abstract fun lazyFetchData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbarBackButton(isBlack = false, enableBack = true)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(layoutId, container, false)
        return mRootView
    }

    private fun lazyFetchDataIfPrepared() {
        if (userVisibleHint && !hasFetchData && isViewPrepared) {
            hasFetchData = true
            lazyFetchData()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.d("class=" ,this.javaClass.simpleName)
//        setTips(null, null)
        initViews()
        isViewPrepared = true
        lazyFetchDataIfPrepared()
    }

    protected fun <T : View> findView(@IdRes id: Int): T {
        val view = mRootView?.findViewById<T>(id)
        return view as T
    }

    fun showLoading(label: String?, detailLabel: String?) {
        showLoading(label, detailLabel, false)
    }

    override fun showLoading(label: String?, detailLabel: String?, cancellable: Boolean) {
        (activity as? BaseActivity)?.showLoading(label, detailLabel, cancellable)
    }

    override fun dismissLoading() {
        (activity as? BaseActivity)?.dismissLoading()
    }

    override fun toastNetError() {
        (activity as? BaseActivity)?.toastNetError()
    }

    override fun toastGetDataFail() {
        (activity as? BaseActivity)?.toastGetDataFail()
    }

    override fun toastMsg(msg: String?) {
        toastMsg(msg, true)
    }

    override fun toastMsg(msg: String?, isShortTime: Boolean) {
        (activity as? BaseActivity)?.toastMsg(msg, isShortTime)
    }

    override fun showError() {
        (activity as? BaseActivity)?.showError()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCallback = (context as? OperationCallback)
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }

    protected fun finishActivity() {
        mCallback?.finishActivity()
    }

    protected fun setResult(resultCode: Int, intent: Intent?) {
        mCallback?.setResult(resultCode, intent)
    }

    protected fun setToolbarTitle(title: String?, isLeft: Boolean) {
        (activity as? BaseActivity)?.setToolbarTitle(title, isLeft)
    }

    protected fun setToolbarTitle(title: String?, isLeft: Boolean, isDropDown: Boolean, listener: View.OnClickListener?) {
        (activity as? BaseActivity)?.setToolbarTitle(title, isLeft, isDropDown, listener)
    }

    protected fun setToolbarBackButton(isBlack: Boolean, enableBack: Boolean) {
        (activity as? BaseActivity)?.setToolbarBackButton(isBlack, enableBack)
    }

//    protected fun setTips(title: String?, tips: String?) {
//        (activity as? BaseActivity)?.setTips(title, tips)
//    }

    protected val actionBar: ActionBar?
        get() {
            return (activity as? BaseActivity)?.supportActionBar
        }

    fun setDrawerLayoutEnable(enable: Boolean) {
        (activity as? BaseActivity)?.setDrawerLayoutEnable(enable)
    }

    fun showMsgDialog(title: String?, msg: String?, oKClickListener: DialogInterface.OnClickListener, cancelClickListener: DialogInterface.OnClickListener?, cancellable: Boolean, outside: Boolean = true): MsgDialogFragment {
        dismissMsgDialog()
        val dialog = MsgDialogFragment.newInstance(title, msg, cancellable, outside)
        dialog.setOkClickListener(oKClickListener)
        dialog.setCancelClickListener(cancelClickListener)
        dialog.isCancelable = cancellable
        dialog.show(childFragmentManager, TAG_BASE_MSG_DIALOG)
        mMsgDialog = dialog
        return dialog
    }

    fun showMsgDialog(title: String?, msg: String?, oKClickListener: DialogInterface.OnClickListener, cancelClickListener: DialogInterface.OnClickListener?): MsgDialogFragment {
        return showMsgDialog(title, msg, oKClickListener, cancelClickListener, true, true)
    }

    fun showMsgDialog(title: String?, msg: String?, oKClickListener: DialogInterface.OnClickListener, cancelClickListener: DialogInterface.OnClickListener?,cancelable:Boolean): MsgDialogFragment {
        return showMsgDialog(title, msg, oKClickListener, cancelClickListener, cancelable, true)
    }

    fun dismissMsgDialog() {
        if (mMsgDialog?.isAdded == true) {
            mMsgDialog?.dismiss()
        }
        mMsgDialog = null
    }


    fun showEditNameDialog(title: String?, msg: String?, preName: String?, okClickListener: EditNameDialogFragment.MyOkClickListener?, cancelClickListener: DialogInterface.OnClickListener): EditNameDialogFragment {
        dismissEditNameDialog()
        val dialog = EditNameDialogFragment.newInstance(title, msg, preName)
        dialog.setOkClickListener(okClickListener)
        dialog.setCancelClickListener(cancelClickListener)
        dialog.show(childFragmentManager, TAG_BASE_EDITNAME_DIALOG)
        mEditNameDialogFragment = dialog
        return dialog
    }

    fun dismissEditNameDialog() {
        if (mEditNameDialogFragment?.isAdded == true) {
            mEditNameDialogFragment?.dismiss()
        }
        mEditNameDialogFragment = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
        hasFetchData = false
        isViewPrepared = false
        dismissMsgDialog()
        dismissEditNameDialog()
    }

    interface OperationCallback {
        fun finishActivity()
        fun setResult(resultCode: Int, intent: Intent?)
    }

//    protected fun <T> listChunked(list: List<T>): List<List<T>> {
////        if (BrainManager.localControl) {
////            if (BrainManager.deviceObject == null) {
////                return list.chunked(READ_NUM_LOCAL)
////            }
////            BrainManager.deviceObject?.also {
////                if (AllFwOTAUtils.isNeedReadMax(it.iotmVer)) {
////                    return list.chunked(READ_NUM_CLOUD)
////                } else {
////                    return list.chunked(READ_NUM_LOCAL)
////                }
////            }
////        }
////        return list.chunked(READ_NUM_CLOUD)
//    }
}
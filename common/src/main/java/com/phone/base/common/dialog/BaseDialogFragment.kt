package com.phone.base.common.dialog

import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.phone.base.common.BaseActivity
import com.phone.base.common.BaseView

abstract class BaseDialogFragment : DialogFragment(), BaseView {

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val mDismissed = DialogFragment::class.java.getDeclaredField("mDismissed")
            val mShownByMe = DialogFragment::class.java.getDeclaredField("mShownByMe")
            mDismissed.isAccessible = true
            mShownByMe.isAccessible = true
            mDismissed.setBoolean(this, false)
            mShownByMe.setBoolean(this, true)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun onDestroyView() {
        dialog?.apply {
            setDismissMessage(null)
            setCancelMessage(null)
            setOnCancelListener(null)
            setOnDismissListener(null)
        }
        super.onDestroyView()
    }

    override fun showLoading(label: String?, detailLabel: String?, cancellable: Boolean) {
        if (activity is BaseActivity) {
            (activity as? BaseActivity)?.showLoading(label, detailLabel, cancellable)
        }
    }

    override fun dismissLoading() {
        if (activity is BaseActivity) {
            (activity as? BaseActivity)?.dismissLoading()
        }
    }

    override fun toastNetError() {
        if (activity is BaseActivity) {
            (activity as? BaseActivity)?.toastNetError()
        }
    }

    override fun toastGetDataFail() {
        if (activity is BaseActivity) {
            (activity as? BaseActivity)?.toastGetDataFail()
        }
    }

    override fun toastMsg(msg: String?) {
        toastMsg(msg, true)
    }

    override fun toastMsg(msg: String?, isShortTime: Boolean) {
        if (activity is BaseActivity) {
            (activity as? BaseActivity)?.toastMsg(msg, isShortTime)
        }
    }

    override fun showError() {
        if (activity is BaseActivity) {
            (activity as? BaseActivity)?.showError()
        }
    }

    fun showMsgDialog(title: String?, msg: String?, oKClickListener: DialogInterface.OnClickListener, cancelClickListener: DialogInterface.OnClickListener?, cancellable: Boolean, outside: Boolean = true): MsgDialogFragment? {
        return (activity as? BaseActivity)?.showMsgDialog(title, msg, oKClickListener, cancelClickListener, cancellable, outside)
    }
}
package com.phone.base.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.derry.serialportlibrary.T
import com.phone.base.R
import com.phone.base.common.BaseActivity
import com.phone.base.common.utils.LogUtil
import com.phone.base.fragment.IncomingCallFragment
import com.phone.base.fragment.TAG_INCOMING_CALL_FRAGMENT


class IncomingCallActivity : BaseActivity() {

    companion object {
        private const val EXTRA_KEY_TARGET_FRAGMENT = "target_fragment"
        private const val EXTRA_KEY_TARGET_PHONEITEM = "extra_key_target_phoneitem"
        private const val EXTRA_KEY_TARGET_PHONENUM = "extra_key_target_phonenum"
        private const val TAG = "DialingActivity"

        fun startIncomingCallFragment(context: Context?, phoneNum: String) {
            if (context != null) {
                val intent = Intent(context, IncomingCallActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_INCOMING_CALL_FRAGMENT)
                intent.putExtra(EXTRA_KEY_TARGET_PHONENUM, phoneNum)
//                intent.putExtra(TAG_TARGET_DEPART_ITEM, targetDept)
                context.startActivity(intent)
            }
        }

    }

    override val layoutId: Int
        get() = R.layout.activity_dialing_container

    override fun initViews() {
        LogUtil.d(T.TAG, "initViews")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕常亮
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //在锁屏情况下也可以显示屏幕
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        ) //启动Activity的时候点亮屏幕
        if (savedInstanceState == null) {
            LogUtil.d(T.TAG, "onCreate")
            startTarget()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtil.d(T.TAG, "onNewIntent")
        startTarget()
    }

    private fun showDialingPage(phoneNum: String) {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_INCOMING_CALL_FRAGMENT) as IncomingCallFragment?
        fragment?.dismissAllowingStateLoss()
        IncomingCallFragment.newInstance(phoneNum).show(supportFragmentManager, TAG_INCOMING_CALL_FRAGMENT)
    }

    private fun startTarget() {
        var target = intent.getStringExtra(EXTRA_KEY_TARGET_FRAGMENT)
        val phoneNum = intent.getStringExtra(EXTRA_KEY_TARGET_PHONENUM)
        if (!TextUtils.isEmpty(target) && !phoneNum.isNullOrEmpty()) {
            showDialingPage(phoneNum)
        }
    }

}
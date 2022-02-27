package com.phone.base.common.listener

import android.view.View
import com.phone.base.common.SINGLE_CLICK_EVENT_TIME


abstract class OnSingleClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    override fun onClick(v: View) {
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime >= SINGLE_CLICK_EVENT_TIME) {
            onSingleClick(v)
        }
        lastClickTime = currentClickTime
    }

    abstract fun onSingleClick(v: View)
}
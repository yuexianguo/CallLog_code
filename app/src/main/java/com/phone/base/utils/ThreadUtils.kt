package com.phone.base.utils

import android.os.Handler
import android.os.Looper

object ThreadUtils {
    fun runOnUiThread(runnable: Runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Handler(Looper.getMainLooper()).post(runnable)
        } else {
            runnable.run()
        }
    }
}
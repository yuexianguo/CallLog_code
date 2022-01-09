package com.phone.base

import com.phone.base.common.BaseApplication
import com.phone.base.manager.PhoneInfoManager

class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        PhoneInfoManager.instance.init(this)
    }
}
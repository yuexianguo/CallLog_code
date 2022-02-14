package com.phone.base.activity

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.phone.base.R
import com.phone.base.common.BaseActivity
import com.phone.base.common.BaseApplication.Companion.context
import com.phone.base.common.utils.ActivityUtils.replaceFragment
import com.phone.base.fragment.HomeFragment
import com.phone.base.fragment.TAG_HOME_FRAGMENT
import com.phone.base.jobservice.Helpers
import com.phone.base.manager.PhoneInfoManager

class MainActivity : BaseActivity() {
    private var mIsActive = false

    companion object {
        private val REQUES_READ_WRITE_CODE = 0x01
        private const val TAG = "MainActivity"
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            startHomeFragment(false)
        }
    }

    override fun initViews() {
        findViewById<View>(android.R.id.list)
        android.R.layout.simple_selectable_list_item
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_main_container)
        if (fragment is HomeFragment) {
            showMsgDialog(
                getString(R.string.call_log),
                getString(R.string.close_app_tips), { dialog: DialogInterface, _: Int ->
                    super@MainActivity.onBackPressed()
                    dialog.dismiss()
                    super.onBackPressed()
                }) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(
        fragment: Fragment,
        addToStack: Boolean,
        tag: String,
        allowLoss: Boolean
    ) {
        replaceFragment(this, R.id.fl_main_container, fragment, addToStack, tag, allowLoss)
    }

    private fun startHomeFragment(addToStack: Boolean) {
        replaceFragment(HomeFragment.newInstance(), addToStack, TAG_HOME_FRAGMENT, true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        mIsActive = true
        //开启后台服务
        Helpers.schedule(this)
    }

    override fun onStop() {
        super.onStop()
        mIsActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }


}
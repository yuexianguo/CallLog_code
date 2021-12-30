package com.phone.base.activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.phone.base.R
import com.phone.base.common.BaseActivity
import com.phone.base.common.utils.ActivityUtils.replaceFragment
import com.phone.base.fragment.HomeFragment
import com.phone.base.fragment.TAG_HOME_FRAGMENT

class MainActivity : BaseActivity() {
    private var mIsActive = false
    companion object {
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

    override fun onResume() {
        super.onResume()
        mIsActive = true

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
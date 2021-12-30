package com.phone.base.activity

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.phone.base.R
import com.phone.base.common.BaseActivity
import com.phone.base.common.BaseApplication.Companion.context
import com.phone.base.common.data.source.StrutDataRepository
import com.phone.base.common.data.source.StrutDataSource
import com.phone.base.common.utils.ActivityUtils.replaceFragment
import com.phone.base.utils.FileSystem
import com.phone.base.file.PhoneBookInfo
import com.phone.base.fragment.HomeFragment
import com.phone.base.fragment.TAG_HOME_FRAGMENT
import com.phone.base.utils.PhoneFileUtils
import io.reactivex.disposables.CompositeDisposable
import java.io.File

class MainActivity : BaseActivity() {
    private var mIsActive = false
    companion object {
        private const val TAG = "MainActivity"
        const val TAG_FROM_ADD_START = "tag_from_add_start"
        const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0x01
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            startMainFragment(false)
        }
    }

    override fun initViews() {

    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_main_container)
        if (fragment is MainFragment) {
            showMsgDialog(
                getString(R.string.call_log),
                getString(R.string.close_app_tips), { dialog: DialogInterface, _: Int ->
                    super@MainActivity.onBackPressed()
                    dialog.dismiss()
                    super.onBackPressed()
                }) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
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

    private fun startMainFragment(addToStack: Boolean) {
        replaceFragment(HomeFragment.newInstance(), addToStack, TAG_HOME_FRAGMENT, true)
    }

//    fun startDeviceTypesFragment(addToStack: Boolean) {
//        replaceFragment(
//            DeviceTypesFragment.newInstance(),
//            addToStack,
//            DeviceTypesFragment::class.java.simpleName,
//            true
//        )
//    }

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
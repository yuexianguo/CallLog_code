package com.phone.base.fragment

import android.content.Context
import android.os.Bundle
import com.phone.base.R
import com.phone.base.activity.MainActivity
import com.phone.base.common.BaseFragment


private const val REQUEST_CODE_CAMERA_AND_LOCATION = 0x001
const val TAG_DIALED_CALL_FRAGMENT = "DialedCallLogFragment"

class DialedCallLogFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null

    override val layoutId: Int
        get() = R.layout.fragment_main

    companion object {
        @JvmStatic
        fun newInstance() =
            DialedCallLogFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {
        
    }

    override fun lazyFetchData() {
    }
    

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mMainActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mMainActivity = null
    }

}
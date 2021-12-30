package com.phone.base.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phone.base.R
import com.phone.base.common.BaseActivity
import com.phone.base.common.BaseApplication
import com.phone.base.common.BaseFragment
import com.phone.base.common.Intents
import com.phone.base.common.adapter.CustomBaseAdapter
import kotlinx.android.synthetic.main.fragment_main.*

private const val REQUEST_CODE_CAMERA_AND_LOCATION = 0x001
const val TAG_MAIN_FRAGMENT = "MainFragment"

class MainFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null

    override val layoutId: Int
        get() = R.layout.fragment_main

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
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
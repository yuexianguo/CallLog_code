package com.phone.base.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.base.R
import com.phone.base.activity.MainActivity
import com.phone.base.adapter.CallLogAdapter
import com.phone.base.common.BaseFragment
import com.phone.base.common.utils.LinearItemDivider
import com.phone.base.file.PhoneBookItem
import kotlinx.android.synthetic.main.fragment_call_log.*


private const val REQUEST_CODE_CAMERA_AND_LOCATION = 0x001
const val TAG_ALL_CALL_FRAGMENT = "AllCallLogFragment"

class AllCallLogFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null
    private var callLogAdapter:CallLogAdapter?=null
    override val layoutId: Int
        get() = R.layout.fragment_call_log

    companion object {
        @JvmStatic
        fun newInstance() =
            AllCallLogFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {
        callLogAdapter = CallLogAdapter(mutableListOf(PhoneBookItem("研发部门", "137613761376"), PhoneBookItem("生活部门", "13761376333")))
        recyclerview_call_log.layoutManager = LinearLayoutManager(context)
        recyclerview_call_log.addItemDecoration(LinearItemDivider(Color.GRAY))
        recyclerview_call_log.adapter = callLogAdapter
    }

    override fun lazyFetchData() {
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            callLogAdapter?.notifyDataSetChanged()
        }
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
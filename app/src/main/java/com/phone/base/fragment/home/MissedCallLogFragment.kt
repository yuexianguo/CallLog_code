package com.phone.base.fragment.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.base.R
import com.phone.base.activity.MainActivity
import com.phone.base.adapter.CallLogAdapter
import com.phone.base.bean.DIAL_TYPE_MISSED_CALL
import com.phone.base.bean.PhoneHistoryItem
import com.phone.base.common.BaseFragment
import com.phone.base.common.utils.LinearItemDivider
import com.phone.base.jobservice.T
import com.phone.base.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_call_log.*


private const val REQUEST_CODE_CAMERA_AND_LOCATION = 0x001
const val TAG_MISSED_CALL_FRAGMENT = "MissedCallLogFragment"

class MissedCallLogFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null
    private var callLogAdapter: CallLogAdapter? = null
    private var mPhoneHistoryList: ArrayList<PhoneHistoryItem> = arrayListOf()
    override val layoutId: Int
        get() = R.layout.fragment_call_log

    companion object {
        @JvmStatic
        fun newInstance() =
            MissedCallLogFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {
        callLogAdapter = CallLogAdapter(mPhoneHistoryList)
        recyclerview_call_log.layoutManager = LinearLayoutManager(context)
        recyclerview_call_log.addItemDecoration(LinearItemDivider(Color.GRAY))
        recyclerview_call_log.adapter = callLogAdapter
        updateData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(T.TAG,"miss call log onHiddenChanged hidden=$hidden")
        if (!hidden) {
            updateData()
        }
    }

    private fun updateData() {
        mPhoneHistoryList.clear()
        mPhoneHistoryList.addAll(PhoneInfoManager.instance.phoneInfo.phoneHistoryItemList.filter { it.dialType == DIAL_TYPE_MISSED_CALL })
        callLogAdapter?.notifyDataSetChanged()
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
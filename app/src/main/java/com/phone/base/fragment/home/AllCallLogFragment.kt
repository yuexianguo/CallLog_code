package com.phone.base.fragment.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.base.R
import com.phone.base.activity.MainActivity
import com.phone.base.adapter.CallLogAdapter
import com.phone.base.bean.PhoneHistoryItem
import com.phone.base.common.BaseFragment
import com.phone.base.common.utils.LinearItemDivider
import com.phone.base.intent.IntentActions.INTENT_ACTION_CALL_LOG_CHANGED
import com.phone.base.jobservice.T
import com.phone.base.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_call_log.*


private const val REQUEST_CODE_CAMERA_AND_LOCATION = 0x001
const val TAG_ALL_CALL_FRAGMENT = "AllCallLogFragment"
const val PARAMS_PHONE_HISTORY_LIST = "params_phone_list"

class AllCallLogFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null
    private var callLogAdapter:CallLogAdapter?=null
    private var mPhoneHistoryList:ArrayList<PhoneHistoryItem> = arrayListOf()
    override val layoutId: Int
        get() = R.layout.fragment_call_log

    companion object {
        @JvmStatic
        fun newInstance(phoneList:ArrayList<PhoneHistoryItem>) =
            AllCallLogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PARAMS_PHONE_HISTORY_LIST,phoneList)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mPhoneHistoryList.clear()
            val serializableExtra = getSerializable(PARAMS_PHONE_HISTORY_LIST)
            val list = if (serializableExtra != null) serializableExtra as ArrayList<PhoneHistoryItem> else null
            list?.let { mPhoneHistoryList.addAll(it) }
        }
        context?.also {
            val intentFilter:IntentFilter = IntentFilter()
            intentFilter.addAction(INTENT_ACTION_CALL_LOG_CHANGED)
            LocalBroadcastManager.getInstance(it).registerReceiver(broadcastReceiver,intentFilter)
        }
    }

    private val broadcastReceiver:BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.run {
                intent?.let {
                    if (it.action == INTENT_ACTION_CALL_LOG_CHANGED) {
                        updateData()
                    }
                }
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

    private fun updateData() {
        mPhoneHistoryList.clear()
        mPhoneHistoryList.addAll(PhoneInfoManager.instance.phoneInfo.phoneHistoryItemList)
        callLogAdapter?.notifyDataSetChanged()
    }

    override fun lazyFetchData() {
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(T.TAG,"onHiddenChanged hidden=$hidden")
//        if (!hidden) {
//            updateData()
//        }
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

    fun setList(phoneList: java.util.ArrayList<PhoneHistoryItem>) {
        mPhoneHistoryList.clear()
        mPhoneHistoryList.addAll(phoneList)
        callLogAdapter?.notifyDataSetChanged()
        
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.also {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(broadcastReceiver)
        }
    }

}
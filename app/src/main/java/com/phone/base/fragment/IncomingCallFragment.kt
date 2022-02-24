package com.phone.base.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.derry.serialportlibrary.T
import com.phone.base.R
import com.phone.base.activity.IncomingCallActivity
import com.phone.base.bean.DIAL_TYPE_MISSED_CALL
import com.phone.base.bean.DIAL_TYPE_RECEIVED_CALL
import com.phone.base.bean.PhoneBookItem
import com.phone.base.bean.PhoneHistoryItem
import com.phone.base.common.dialog.BaseDialogFragment
import com.phone.base.common.listener.OnSingleClickListener
import com.phone.base.common.utils.LogUtil
import com.phone.base.common.utils.RxBus
import com.phone.base.manager.PhoneInfoManager
import com.phone.base.rxevent.HangUpCallEvent
import com.phone.base.rxevent.IncomeCallEvent
import com.phone.base.utils.DialTimeUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_incoming_call.*


const val TAG_INCOMING_CALL_FRAGMENT = "IncomingCallFragment"

class IncomingCallFragment : BaseDialogFragment() {
    private var mActivity: IncomingCallActivity? = null
    private var mPhoneNum: String = ""
    private lateinit var et_incoming_call_phone_number: EditText
    private lateinit var tv_incoming_call_dept_name: TextView
    private lateinit var tv_incoming_call_person_name: TextView
    private lateinit var tv_incoming_call_work: TextView
    private lateinit var incoming_call_right_layout: LinearLayout
    private var mDisposableIncomeCallEvent: Disposable? = null
    private var mDisposableHangUpCallEvent: Disposable? = null
    private var mHandler: Handler = Handler(Looper.getMainLooper())
    private var mDialSeconds: Int = 0
    private var mDialMinutes: Int = 0
    private var mDialHours: Int = 0
    private lateinit var tv_incoming_call_address_desc: TextView
    private lateinit var tv_incoming_call_remark_desc: TextView
    private lateinit var bt_incoming_call_time_hours: Button
    private lateinit var bt_incoming_call_time_minutes: Button
    private lateinit var bt_incoming_call_time_seconds: Button
    private lateinit var bt_incoming_call_record_hours: Button
    private lateinit var bt_incoming_call_record_minutes: Button
    private lateinit var bt_incoming_call_record_seconds: Button
    private lateinit var bt_incoming_call_out: Button
    private var mStartDialTime: String = ""
    private var phoneBookItem: PhoneBookItem? = null
    private var isReceiveCall: Boolean = false

    companion object {
        private const val ARG_PHONENUM = "arg_phonenum"

        @JvmStatic
        fun newInstance(phoneNum: String) = IncomingCallFragment().apply {
            arguments = Bundle().apply {
                phoneNum.let {
                    putString(ARG_PHONENUM, it)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mPhoneNum = it.getString(ARG_PHONENUM) ?: ""
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val dialog = AlertDialog.Builder(requireContext(), R.style.commonDialogCustom).create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        dialog.setContentView(R.layout.fragment_incoming_call)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        //for soft input could show
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        initViews(dialog)
        return dialog
    }


    fun initViews(dialog: AlertDialog) {
        Log.w(T.TAG, "IncoingCallFragment initViews")
        phoneBookItem = PhoneInfoManager.instance.phoneInfo.foundUserByCertainNumber(mPhoneNum)
        initToolbar()
        et_incoming_call_phone_number = dialog.findViewById(R.id.et_incoming_call_phone_number)!!
        tv_incoming_call_dept_name = dialog.findViewById(R.id.tv_incoming_call_dept_name)!!
        tv_incoming_call_person_name = dialog.findViewById(R.id.tv_incoming_call_person_name)!!
        tv_incoming_call_work = dialog.findViewById(R.id.tv_incoming_call_work)!!
        incoming_call_right_layout = dialog.findViewById(R.id.incoming_call_right_layout)!!

        tv_incoming_call_address_desc = dialog.findViewById(R.id.tv_incoming_call_address_desc)!!
        tv_incoming_call_remark_desc = dialog.findViewById(R.id.tv_incoming_call_remark_desc)!!
        bt_incoming_call_time_hours = dialog.findViewById(R.id.bt_incoming_call_time_hours)!!
        bt_incoming_call_time_minutes = dialog.findViewById(R.id.bt_incoming_call_time_minutes)!!
        bt_incoming_call_time_seconds = dialog.findViewById(R.id.bt_incoming_call_time_seconds)!!
        bt_incoming_call_record_hours = dialog.findViewById(R.id.bt_incoming_call_record_hours)!!
        bt_incoming_call_record_minutes = dialog.findViewById(R.id.bt_incoming_call_record_minutes)!!
        bt_incoming_call_record_seconds = dialog.findViewById(R.id.bt_incoming_call_record_seconds)!!
        bt_incoming_call_out = dialog.findViewById(R.id.bt_incoming_call_out)!!

        et_incoming_call_phone_number.setText(mPhoneNum)
        tv_incoming_call_dept_name.text = phoneBookItem?.department?.name ?: ""
        tv_incoming_call_person_name.text = phoneBookItem?.name ?: ""
        tv_incoming_call_work.text = phoneBookItem?.work ?: ""

        bt_incoming_call_out.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                dismissAllowingStateLoss()
            }
        })

        initData()
    }

    private fun initData() {
        mStartDialTime = DialTimeUtils.getCurrentTimeByFormat()
        Log.d(T.TAG, "mStartDialTime =$mStartDialTime")
        val fragment = this
        mDisposableIncomeCallEvent = RxBus.toObservable(IncomeCallEvent::class.java).subscribe {
            LogUtil.d(T.TAG, "mDisposableCallEvent  IncomeCallEvent context=$context, isAdded =$isAdded")
            if (context != null) {
                isReceiveCall = true
                fragment?.incoming_call_right_layout?.visibility = View.VISIBLE
                startDialTimeRecord()
            }
        }

        mDisposableHangUpCallEvent = RxBus.toObservable(HangUpCallEvent::class.java).subscribe {
            LogUtil.d(T.TAG, "mDisposableCallEvent HangUpCallEvent context=$context, isAdded =$isAdded")
            if (context != null) {
                mActivity?.onBackPressed()
            }
        }
    }

    private fun startDialTimeRecord() {
        mHandler.removeCallbacks(mDialTimeRunnable)
        mHandler.postDelayed(mDialTimeRunnable, 1000L)
    }

    private val mDialTimeRunnable: Runnable
        get() = Runnable {
            mDialSeconds++
            if (mDialSeconds >= 60) {
                mDialSeconds = 0
                mDialMinutes++
            }
            if (mDialMinutes >= 60) {
                mDialSeconds = 0
                mDialMinutes = 0
                mDialHours++
            }
            bt_incoming_call_time_seconds.text = String.format("%02d", mDialSeconds)
            bt_incoming_call_time_minutes.text = String.format("%02d", mDialMinutes)
            bt_incoming_call_time_hours.text = String.format("%02d", mDialHours)
            mHandler.postDelayed(mDialTimeRunnable, 1000L)
        }


    private fun initToolbar() {

    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(T.TAG,"incoming call fragment onDestroy")
        savingIncomingTime(phoneBookItem)
        mHandler.removeCallbacks(mDialTimeRunnable)
        mDisposableHangUpCallEvent?.dispose()
        mDisposableIncomeCallEvent?.dispose()
        mActivity?.finish()
    }

    private fun savingIncomingTime(phoneBookItem: PhoneBookItem?) {
        var phonebookId = phoneBookItem?.id ?: 0L
        var phonebookName = phoneBookItem?.name ?: ""
        if (isReceiveCall) {
            //已接
            PhoneInfoManager.instance.phoneInfo.insertPhoneHistoryItem(
                PhoneHistoryItem(
                    PhoneInfoManager.instance.phoneInfo.generatePhoneHistoryItemId(), phonebookId,
                    DIAL_TYPE_RECEIVED_CALL, phonebookName, mPhoneNum, mStartDialTime, String.format("%02d:%02d:%02d", mDialHours, mDialMinutes, mDialSeconds)
                )
            )
        } else {
            //未接
            PhoneInfoManager.instance.phoneInfo.insertPhoneHistoryItem(
                PhoneHistoryItem(
                    PhoneInfoManager.instance.phoneInfo.generatePhoneHistoryItemId(), phonebookId,
                    DIAL_TYPE_MISSED_CALL, phonebookName, mPhoneNum, mStartDialTime, "00:00:00"
                )
            )
        }
        PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IncomingCallActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }
}
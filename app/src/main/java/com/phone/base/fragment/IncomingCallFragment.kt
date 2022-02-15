package com.phone.base.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.derry.serialportlibrary.T
import com.phone.base.R
import com.phone.base.activity.IncomingCallActivity
import com.phone.base.common.dialog.BaseDialogFragment
import com.phone.base.common.fragment.ARG_BG_RES_NAME
import com.phone.base.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_incoming_call.*


const val TAG_INCOMING_CALL_FRAGMENT = "IncomingCallFragment"

class IncomingCallFragment : BaseDialogFragment() {
    private var mActivity: IncomingCallActivity? = null
    private var mPhoneNum:String = ""
    private lateinit var et_incoming_call_phone_number: EditText
    private lateinit var tv_incoming_call_dept_name: TextView
    private lateinit var tv_incoming_call_person_name: TextView
    private lateinit var tv_incoming_call_work: TextView
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
            mPhoneNum = it.getString(ARG_PHONENUM)?:""
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
        val phoneBookItem = PhoneInfoManager.instance.phoneInfo.foundUserByCertainNumber(mPhoneNum)
        initToolbar()
        et_incoming_call_phone_number = dialog.findViewById(R.id.et_incoming_call_phone_number)!!
        tv_incoming_call_dept_name = dialog.findViewById(R.id.tv_incoming_call_dept_name)!!
        tv_incoming_call_person_name = dialog.findViewById(R.id.tv_incoming_call_person_name)!!
        tv_incoming_call_work = dialog.findViewById(R.id.tv_incoming_call_work)!!

        et_incoming_call_phone_number.setText(mPhoneNum)
        tv_incoming_call_dept_name.text = phoneBookItem?.department?.name?:""
        tv_incoming_call_person_name.text = phoneBookItem?.name?:""
        tv_incoming_call_work.text = phoneBookItem?.work?:""
        initData()
    }

    private fun initData() {

    }


    private fun initToolbar() {
        mActivity?.hideLogo()
//        setToolbarTitle("拨号", true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity?.finish()
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
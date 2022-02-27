package com.phone.base.adapter

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.phone.base.R
import com.phone.base.bean.PhoneHistoryItem
import com.phone.base.common.adapter.CustomBaseAdapter
import com.phone.base.common.listener.OnSingleClickListener

class CallLogAdapter(list: MutableList<PhoneHistoryItem>) :
    CustomBaseAdapter<PhoneHistoryItem, CallLogAdapter.CallLogHolder>(R.layout.adapter_item_call_log, list) {

    override fun onCreateCustomViewHolder(view: View): CallLogHolder {
        return CallLogHolder(view)
    }

    override fun onBindCustomViewHolder(callLogHolder: CallLogHolder, phoneHistoryItem: PhoneHistoryItem) {
        try {
            callLogHolder.item_call_name.text = phoneHistoryItem.name
            callLogHolder.item_call_phone_number.text = phoneHistoryItem.phone
            callLogHolder.item_call_start_time.text = if (phoneHistoryItem.startTime.length >= 18) phoneHistoryItem.startTime.substringAfter('-') else phoneHistoryItem.startTime
            callLogHolder.item_call_start_time.setOnClickListener(object :OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    Toast.makeText(callLogHolder.itemView.context, phoneHistoryItem.startTime, Toast.LENGTH_SHORT).show()
                }
            })
            callLogHolder.item_call_time_long.text = phoneHistoryItem.dialogTimeLength
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class CallLogHolder(itemView: View) : CustomBaseHolder(itemView) {
        val item_call_name: TextView = itemView.findViewById(R.id.item_call_name)
        val item_call_phone_number: TextView = itemView.findViewById(R.id.item_call_phone_number)
        val item_call_start_time: TextView = itemView.findViewById(R.id.item_call_start_time)
        val item_call_time_long: TextView = itemView.findViewById(R.id.item_call_time_long)
    }
}
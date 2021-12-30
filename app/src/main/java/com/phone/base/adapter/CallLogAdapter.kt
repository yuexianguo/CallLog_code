package com.phone.base.adapter

import android.view.View
import android.widget.TextView
import com.phone.base.R
import com.phone.base.common.adapter.CustomBaseAdapter
import com.phone.base.file.PhoneBookItem

class CallLogAdapter(list: MutableList<PhoneBookItem>) :
        CustomBaseAdapter<PhoneBookItem, CallLogAdapter.CallLogHolder>(R.layout.adapter_item_call_log, list) {

    override fun onCreateCustomViewHolder(view: View): CallLogHolder {
        return CallLogHolder(view)
    }

    override fun onBindCustomViewHolder(callLogHolder: CallLogHolder, phoneBookItem: PhoneBookItem) {
        try {
            callLogHolder.item_call_name.text = phoneBookItem.department
            callLogHolder.item_call_phone_number.text = phoneBookItem.phoneNumer
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
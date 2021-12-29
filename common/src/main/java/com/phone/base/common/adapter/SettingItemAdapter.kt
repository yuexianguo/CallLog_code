package com.phone.base.common.adapter

import android.view.View
import android.widget.CompoundButton
import com.phone.base.common.R
import com.phone.base.common.listener.OnSingleClickListener
import com.phone.base.common.view.SettingItemBean
import com.phone.base.common.view.SettingItemView
import com.phone.base.common.view.SettingType

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/1/14
 */

class SettingItemAdapter(layoutId: Int, list: List<SettingItemBean>) :
        CustomBaseAdapter<SettingItemBean, SettingItemAdapter.SettingItemHolder>(layoutId, list) {

    var descClickListener: DescClickListener? = null
    var swCheckedChangeListener: SWCheckedChangeListener? = null

    override fun onCreateCustomViewHolder(view: View): SettingItemHolder {
        return SettingItemHolder(view)
    }

    override fun onBindCustomViewHolder(vh: SettingItemHolder, itemBean: SettingItemBean) {
        vh.sivItem.apply {
            name = itemBean.name
            desc = itemBean.desc
            itemBean.nameColor?.let {
                nameColor = it
            }
            itemBean.descColor?.let {
                descColor = it
            }
            itemBean.nameSize?.let {
                nameSize = it
            }
            itemBean.descSize?.let {
                descSize = it
            }
            itemBean.isDividerEnable?.let {
                isDividerEnable = it
            }
            itemBean.dividerColor?.let {
                dividerColor = it
            }
            itemBean.dividerWidth?.let {
                dividerWidth = it
            }
            itemBean.descMarginLeft?.let {
                descMarginLeft = it
            }
            itemBean.indicatorDrawable?.let {
                indicatorDrawable = it
            }
            itemBean.indicatorDrawablePadding?.let {
                indicatorDrawablePadding = it
            }
            itemBean.nameMarginLeft?.let {
                nameMarginLeft = it
            }
            itemBean.indicatorMarginRight?.let {
                indicatorMarginRight = it
            }
            itemBean.switchMarginRight?.let {
                switchMarginRight = it
            }
            itemBean.dividerMarginLeft?.let {
                dividerMarginLeft = it
            }
            itemBean.dividerMarginRight?.let {
                dividerMarginRight = it
            }

            if (settingType != itemBean.settingType || descPosition != itemBean.descPosition) {
                relayout(itemBean.settingType, itemBean.descPosition)
            }

            //Need after the relayout
            switchChecked = itemBean.isSwitchChecked == true
            if (itemBean.settingType == SettingType.TEXT_ONLY) {
                setDescClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View) {
                        descClickListener?.onClick(v, vh.adapterPosition)
                    }
                })
            }

            setSWOnCheckedChangeListener { view, isChecked ->
                swCheckedChangeListener?.onCheckedChanged(view, isChecked, vh.adapterPosition)
            }
        }
    }

    inner class SettingItemHolder(itemView: View) : CustomBaseHolder(itemView) {
        val sivItem: SettingItemView = itemView.findViewById(R.id.siv_common_setting_item)
    }

    interface DescClickListener {
        fun onClick(v: View, position: Int)
    }

    interface SWCheckedChangeListener {
        fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean, position: Int)
    }
}


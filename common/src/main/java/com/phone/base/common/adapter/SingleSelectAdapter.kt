package com.phone.base.common.adapter

import android.view.View
import android.widget.RadioButton
import com.phone.base.common.R

/**
 * description ：Used for single select in recyclerview
 * author :
 * email : @waclighting.com.cn
 * date : 2021/1/26
 */
abstract class SingleSelectAdapter<T>(list: List<T>) : CustomBaseAdapter<T, SingleSelectAdapter<T>.SingleSelectHolder>(R.layout.common_adapter_item_single_selector, list) {

    private var mListener: OnItemSelectedListener? = null
    private var mCurrentSelectedIndex = -1
    private var mListViews: MutableList<SingleSelectHolder> = mutableListOf()

    override fun onCreateCustomViewHolder(view: View): SingleSelectHolder {
        return SingleSelectHolder(view)
    }

    override fun onViewAttachedToWindow(holder: SingleSelectHolder) {
        super.onViewAttachedToWindow(holder)
        mListViews.add(holder)
    }

    override fun onViewDetachedFromWindow(holder: SingleSelectHolder) {
        super.onViewDetachedFromWindow(holder)
        mListViews.remove(holder)
    }

    abstract fun onBindSingleViewHolder(vh: SingleSelectHolder, t: T)

    override fun onBindCustomViewHolder(vh: SingleSelectHolder, t: T) {
        vh.rbtItem.isChecked = mCurrentSelectedIndex == vh.adapterPosition
        onBindSingleViewHolder(vh, t)
    }

    inner class SingleSelectHolder(itemView: View) : CustomBaseHolder(itemView) {
        val rbtItem: RadioButton = itemView.findViewById(R.id.rbt_single_select)

        init {
            rbtItem.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    setOthersNoneSelect(this)
                    mCurrentSelectedIndex = adapterPosition
                    mListener?.onItemSelected(itemView, adapterPosition)
                }
            }
        }
    }

    private fun setOthersNoneSelect(viewSelected: SingleSelectHolder) {
        mListViews.forEach {
            if (viewSelected != it) {
                it.rbtItem.isChecked = false
            }
        }
    }

    fun selectIndex(position: Int) {
        mCurrentSelectedIndex = position
        notifyDataSetChanged()
    }

    /**
     * If none, return -1
     */
    fun getCurrentSelectedIndex(): Int {
        return mCurrentSelectedIndex
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        mListener = listener
    }

    interface OnItemSelectedListener {
        fun onItemSelected(view: View, position: Int)
    }

}
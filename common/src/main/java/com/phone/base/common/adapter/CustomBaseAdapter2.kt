package com.phone.base.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/3/5
 */

private const val CLICK_EVENT_TIME = 600
private const val VIEW_TYPE_HEADER = 0x10000001
private const val VIEW_TYPE_FOOTER = 0x10000002
private typealias OnItemClickListener<T> = (View, T?, Int) -> Unit
private typealias OnItemLongClickListener<T> = (View, T?, Int) -> Boolean

abstract class CustomBaseAdapter2<T>(private val layoutId: Int, private val list: List<T>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mLastClickTime: Long = 0

    private var mOnItemClickListener: OnItemClickListener<T>? = null

    private var mOnItemLongClickListener: OnItemLongClickListener<T>? = null

    private var mHeaderView: View? = null

    private var mFooterView: View? = null

    fun setOnItemClickListener(listener: OnItemClickListener<T>?) {
        this.mOnItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener<T>?) {
        this.mOnItemLongClickListener = listener
    }

    fun setFooterLayout(header: View) {
        mFooterView = header
    }

    fun setHeaderLayout(footer: View) {
        mHeaderView = footer
    }

    protected abstract fun onCreateCustomViewHolder(view: View): RecyclerView.ViewHolder

    protected abstract fun onBindCustomViewHolder(vh: RecyclerView.ViewHolder, t: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val headerView = mHeaderView
        val footerView = mFooterView
        return if (VIEW_TYPE_HEADER == viewType && headerView != null) {
            HeaderViewHolder(headerView)
        } else if (VIEW_TYPE_FOOTER == viewType && footerView != null) {
            FootViewHolder(footerView)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            onCreateCustomViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        var lastPosition = position
//        if (mHeaderView != null) {
//            lastPosition = position + 1
//        }
//        if (mFooterView != null && lastPosition == itemCount - 1) {
//            onBindCustomViewHolder(holder, list[lastPosition - 1])
//        } else {
//            onBindCustomViewHolder(holder, list[lastPosition])
//        }
//        holder.apply {
//            itemView.setOnClickListener {
//                val currentTime = System.currentTimeMillis()
//                if (currentTime - mLastClickTime >= CLICK_EVENT_TIME) {
//                    when (holder) {
//                        is HeaderViewHolder -> {
//                            mOnItemClickListener?.invoke(it, null, position)
//                        }
//                        is FootViewHolder -> {
//                            mOnItemClickListener?.invoke(it, null, position)
//                        }
//                        else -> {
//                            if (!list.isNullOrEmpty()) {
//
//
//                            }
//                        }
//                    }
//                }
//                mLastClickTime = currentTime
//            }
//            itemView.setOnLongClickListener {
//                var result = false
//                if (position >= 0) {
//                    result = mOnItemLongClickListener?.invoke(it, list[position], position)
//                            ?: result
//                }
//                result
//            }
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && mHeaderView != null) {
            VIEW_TYPE_HEADER
        } else if (position + 1 == itemCount && mFooterView != null) {
            VIEW_TYPE_FOOTER
        } else {
            super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        val count = list?.size ?: 0
        return if (mHeaderView != null && mFooterView != null) {
            count + 2
        } else if (mHeaderView != null || mFooterView != null) {
            count + 1
        } else {
            count
        }
    }

    //Header
    private class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //Footer
    private class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
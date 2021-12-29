package com.phone.base.common.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.base.common.BaseFragment
import com.phone.base.common.R
import com.phone.base.common.adapter.SingleSelectAdapter
import com.phone.base.common.listener.OnSingleClickListener
import com.phone.base.common.manager.BrainManager
import kotlinx.android.synthetic.main.common_fragment_select_bg.*


const val ARG_BG_RES_NAME = "bg_res_name"

class SelectBgFragment : BaseFragment() {

    private var mAdapter: SingleSelectAdapter<String>? = null
    private var mActivity: Activity? = null
    private var mBgResName: String? = null
    private lateinit var homeBgArray: Array<String>
    private lateinit var cabinBgArray: Array<String>
    private lateinit var buildingBgArray: Array<String>
    private lateinit var hotelBgArray: Array<String>
    private lateinit var restaurantBgArray: Array<String>
    private lateinit var othersBgArray: Array<String>

    private val mAllList: MutableList<String> = mutableListOf(
    )

    override val layoutId: Int
        get() = R.layout.common_fragment_select_bg

    companion object {
        @JvmStatic
        fun newInstance(bgResName: String?) = SelectBgFragment().apply {
            arguments = Bundle().apply {
                bgResName?.let {
                    putString(ARG_BG_RES_NAME, it)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            mBgResName = it.getString(ARG_BG_RES_NAME)
        }
        homeBgArray = resources.getStringArray(R.array.Home)
        cabinBgArray = resources.getStringArray(R.array.Cabin)
        buildingBgArray = resources.getStringArray(R.array.Building)
        hotelBgArray = resources.getStringArray(R.array.Hotel)
        restaurantBgArray = resources.getStringArray(R.array.Restaurant)
        othersBgArray = resources.getStringArray(R.array.Others)
        setBgData(false)
    }

    override fun initViews() {
        mAdapter = object : SingleSelectAdapter<String>(mAllList) {
            override fun onBindSingleViewHolder(vh: SingleSelectHolder, t: String) {
                vh.apply {
                    t.let {
                        val index = adapterPosition % mAllList.size
                        val resId = resources.getIdentifier(mAllList[index], "drawable", requireContext().packageName)
                        itemView.setBackgroundResource(resId)
                    }
                }
            }
        }
        recyclerview_bg.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_bg.adapter = mAdapter
        setToolbarTitle(getString(R.string.common_pictures), true)

        run {
            mAllList.forEachIndexed { index, i ->
                if (i == mBgResName) {
                    mAdapter?.selectIndex(index)
                    return@run
                }
            }
        }

        tv_see_more.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                tv_see_more.visibility = View.GONE
                setBgData(true)
                run {
                    mAllList.forEachIndexed { index, i ->
                        if (i == mBgResName) {
                            mAdapter?.selectIndex(index)
                            return@run
                        }
                    }
                }
                mAdapter?.notifyDataSetChanged()
            }
        })
    }

    private fun setBgData(showAll: Boolean) {
        if (context == null) {
            return
        }
        val locationType = BrainManager.deviceObject?.locationType
        mAllList.clear()
        when (locationType) {
            "Home" -> {
                mAllList.addAll(homeBgArray.toList())
                if (showAll) {
                    mAllList.addAll(cabinBgArray.toList())
                    mAllList.addAll(buildingBgArray.toList())
                    mAllList.addAll(hotelBgArray.toList())
                    mAllList.addAll(restaurantBgArray.toList())
                    mAllList.addAll(othersBgArray.toList())
                }
            }
            "Restaurant" -> {
                mAllList.addAll(restaurantBgArray.toList())
                if (showAll) {
                    mAllList.addAll(homeBgArray.toList())
                    mAllList.addAll(cabinBgArray.toList())
                    mAllList.addAll(buildingBgArray.toList())
                    mAllList.addAll(hotelBgArray.toList())
                    mAllList.addAll(othersBgArray.toList())
                }
            }
//            "Cabin" -> {
//                mAllList.addAll(cabinBgArray.toList())
//                if (showAll) {
//                    mAllList.addAll(homeBgArray.toList())
//                    mAllList.addAll(buildingBgArray.toList())
//                    mAllList.addAll(hotelBgArray.toList())
//                    mAllList.addAll(restaurantBgArray.toList())
//                    mAllList.addAll(othersBgArray.toList())
//                }
//            }
//            "Building" -> {
//                mAllList.addAll(buildingBgArray.toList())
//                if (showAll) {
//                    mAllList.addAll(homeBgArray.toList())
//                    mAllList.addAll(cabinBgArray.toList())
//                    mAllList.addAll(hotelBgArray.toList())
//                    mAllList.addAll(restaurantBgArray.toList())
//                    mAllList.addAll(othersBgArray.toList())
//                }
//            }
//            "Hotel" -> {
//                mAllList.addAll(hotelBgArray.toList())
//                if (showAll) {
//                    mAllList.addAll(homeBgArray.toList())
//                    mAllList.addAll(cabinBgArray.toList())
//                    mAllList.addAll(buildingBgArray.toList())
//                    mAllList.addAll(restaurantBgArray.toList())
//                    mAllList.addAll(othersBgArray.toList())
//                }
//            }
            else -> {
                mAllList.addAll(homeBgArray.toList())
                mAllList.addAll(cabinBgArray.toList())
                mAllList.addAll(buildingBgArray.toList())
                mAllList.addAll(hotelBgArray.toList())
                mAllList.addAll(restaurantBgArray.toList())
                mAllList.addAll(othersBgArray.toList())
            }
        }
    }

    override fun lazyFetchData() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.common_menu_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                mAdapter?.getCurrentSelectedIndex()?.also {
                    val list = mAllList
                    setResult(Activity.RESULT_OK, Intent().apply {
                        if (it != -1 && !list.isNullOrEmpty()) {
                            putExtra(ARG_BG_RES_NAME, list[it])
                        }
                    })
                }
                mActivity?.finish()
            }
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAllList.clear()
        mAdapter = null
    }

}
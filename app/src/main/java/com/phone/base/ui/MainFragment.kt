package com.phone.base.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phone.base.R
import com.phone.base.common.BaseActivity
import com.phone.base.common.BaseApplication
import com.phone.base.common.BaseFragment
import com.phone.base.common.Intents
import com.phone.base.common.adapter.CustomBaseAdapter
import kotlinx.android.synthetic.main.fragment_main.*

private const val REQUEST_CODE_CAMERA_AND_LOCATION = 0x001
const val TAG_MAIN_FRAGMENT = "MainFragment"

class MainFragment : BaseFragment() {

    private var mList: MutableList<String> = mutableListOf()
    private var mStrutHomeAdapter: StrutHomeAdapter? = null
    private var mMainActivity: MainActivity? = null

    override val layoutId: Int
        get() = R.layout.fragment_main

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        mList.add(getString(R.string.device_type_strut))
        mList.add(getString(R.string.device_type_mf))
        mList.add(getString(R.string.device_type_wac))
        setHasOptionsMenu(true)
    }

    override fun initViews() {
        initToolbar()
        mStrutHomeAdapter = StrutHomeAdapter(R.layout.adapter_strut_home_item, mList)
        recycler_struct_home.layoutManager =
            GridLayoutManager(BaseApplication.context, 2, RecyclerView.VERTICAL, false)
        recycler_struct_home.adapter = mStrutHomeAdapter
        mStrutHomeAdapter?.setOnItemClickListener { _, _, position ->
            val packageManager = mMainActivity?.packageManager
            if (packageManager != null) {
                when (position) {
                    0 -> {
                        val intent = Intent(Intents.ACTION_START_STRUT_ACTIVITY)
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                    1 -> {
                        val intent = Intent(Intents.ACTION_START_MF_FANS_ACTIVITY)
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                    else -> {
                        toastMsg("Not Ready")
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        (activity as? BaseActivity)?.setToolbar(toolbar_main, tv_main_left_title)
        setToolbarTitle(getString(R.string.welcome), false)
        setToolbarBackButton(isBlack = false, enableBack = false)
    }

    override fun lazyFetchData() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    inner class StrutHomeAdapter(layoutId: Int, list: MutableList<String>) :
        CustomBaseAdapter<String, StrutHomeAdapter.StrutHomeHolder>(layoutId, list) {
        override fun onCreateCustomViewHolder(view: View): StrutHomeHolder {
            return StrutHomeHolder(view)
        }

        override fun onBindCustomViewHolder(holder: StrutHomeHolder, homeObject: String) {
            holder.apply {
                tvTypeName.text = homeObject
            }

        }

        inner class StrutHomeHolder(itemView: View) : CustomBaseHolder(itemView) {
            val tvTypeName: TextView = itemView.findViewById(R.id.tv_home_object_type)
        }
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
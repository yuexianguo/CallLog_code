package com.phone.base.fragment

import android.content.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.tabs.TabLayout
import com.phone.base.R
import com.phone.base.activity.MainActivity
import com.phone.base.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

const val TAG_HOME_FRAGMENT = "HomeFragment"

class HomeFragment : BaseFragment(){

    private var mMainActivity: MainActivity? = null
    private var mTabTitles: Array<String>? = null
    private var mAllCallLogFragment: AllCallLogFragment? = null
    private var mMissedCallLogFragment: MissedCallLogFragment? = null
    private var mReceivedCallLogFragment: ReceivedCallLogFragment? = null
    private var mDialedCallLogFragment: DialedCallLogFragment? = null
    private var mSeekLogFragment: SeekLogFragment? = null
    private var mCurrentIndex = 0


    companion object {
        private const val TAG = "HomeFragment"

        private const val KEY_CURRENT_SELECTED_POSITION = "key_current_selected_position"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_home



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCurrentIndex = 1
        savedInstanceState?.let {
            mCurrentIndex = it.getInt(KEY_CURRENT_SELECTED_POSITION)
            mAllCallLogFragment= childFragmentManager.findFragmentByTag(TAG_ALL_CALL_FRAGMENT) as AllCallLogFragment?
            mMissedCallLogFragment = childFragmentManager.findFragmentByTag(TAG_MISSED_CALL_FRAGMENT) as MissedCallLogFragment?
            mReceivedCallLogFragment = childFragmentManager.findFragmentByTag(TAG_RECEIVED_CALL_FRAGMENT) as ReceivedCallLogFragment?
            mDialedCallLogFragment = childFragmentManager.findFragmentByTag(TAG_DIALED_CALL_FRAGMENT) as DialedCallLogFragment?
            mSeekLogFragment = childFragmentManager.findFragmentByTag(TAG_SEEK_CALL_FRAGMENT) as SeekLogFragment?
        }

        mTabTitles = arrayOf(getString(R.string.all_call), getString(R.string.missed_call), getString(R.string.received_call), getString(R.string.dialed_call), getString(R.string.seek_call))
    }

    override fun initViews() {
        mTabTitles?.forEach {
            tab_home.addTab(tab_home.newTab().setText(it))
        }
        tab_home.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                selectedIndex(p0?.position ?: 0)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                selectedIndex(p0?.position ?: 0)
            }
        })

        tab_home.getTabAt(mCurrentIndex)?.select()
        selectedIndex(mCurrentIndex)
        mMainActivity?.hideAppbar()
        mMainActivity?.hideToolbar()
    }



    override fun onResume() {
        super.onResume()

    }

    override fun lazyFetchData() {
    }



    fun selectedIndex(index: Int) {
        mCurrentIndex = index
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                if (mAllCallLogFragment == null) {
                    mAllCallLogFragment = AllCallLogFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mAllCallLogFragment!!, TAG_ALL_CALL_FRAGMENT)
                }
                transaction.show(mAllCallLogFragment!!)
                mMissedCallLogFragment?.also { transaction.hide(it) }
                mReceivedCallLogFragment?.also { transaction.hide(it) }
                mDialedCallLogFragment?.also { transaction.hide(it) }
                mSeekLogFragment?.also { transaction.hide(it) }
            }
            1 -> {
                if (mMissedCallLogFragment == null) {
                    mMissedCallLogFragment = MissedCallLogFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mMissedCallLogFragment!!, TAG_MISSED_CALL_FRAGMENT)
                }
                transaction.show(mMissedCallLogFragment!!)
                mAllCallLogFragment?.also { transaction.hide(it) }
                mReceivedCallLogFragment?.also { transaction.hide(it) }
                mDialedCallLogFragment?.also { transaction.hide(it) }
                mSeekLogFragment?.also { transaction.hide(it) }
            }
            2 -> {
                if (mReceivedCallLogFragment == null) {
                    mReceivedCallLogFragment = ReceivedCallLogFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mReceivedCallLogFragment!!, TAG_RECEIVED_CALL_FRAGMENT)
                }
                transaction.show(mReceivedCallLogFragment!!)
                mAllCallLogFragment?.also { transaction.hide(it) }
                mMissedCallLogFragment?.also { transaction.hide(it) }
                mDialedCallLogFragment?.also { transaction.hide(it) }
                mSeekLogFragment?.also { transaction.hide(it) }
            }
            3 -> {
                if (mDialedCallLogFragment == null) {
                    mDialedCallLogFragment = DialedCallLogFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mDialedCallLogFragment!!, TAG_DIALED_CALL_FRAGMENT)
                }
                transaction.show(mDialedCallLogFragment!!)
                mAllCallLogFragment?.also { transaction.hide(it) }
                mMissedCallLogFragment?.also { transaction.hide(it) }
                mReceivedCallLogFragment?.also { transaction.hide(it) }
                mSeekLogFragment?.also { transaction.hide(it) }
            }
            4 -> {
                if (mSeekLogFragment == null) {
                    mSeekLogFragment = SeekLogFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mSeekLogFragment!!, TAG_SEEK_CALL_FRAGMENT)
                }
                transaction.show(mSeekLogFragment!!)
                mAllCallLogFragment?.also { transaction.hide(it) }
                mMissedCallLogFragment?.also { transaction.hide(it) }
                mReceivedCallLogFragment?.also { transaction.hide(it) }
                mDialedCallLogFragment?.also { transaction.hide(it) }
            }
        }
        transaction.commit()
    }



    override fun onPause() {
        super.onPause()
        mCurrentIndex = tab_home.selectedTabPosition
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_SELECTED_POSITION, mCurrentIndex)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mMainActivity = context
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDetach() {
        super.onDetach()
        mMainActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}

package com.phone.base.common.view

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.phone.base.common.BaseActivity
import com.phone.base.common.Intents
import com.phone.base.common.R
import com.phone.base.common.listener.OnSingleClickListener


class RightDrawerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val systemService = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        if (systemService != null) {
            val layoutInflater: LayoutInflater = systemService as LayoutInflater
            val view: View = layoutInflater.inflate(R.layout.common_view_right_drawer, this, true)
            val ivUser: ImageView = view.findViewById(R.id.iv_right_drawer_layout_user)
            val tvGroups: TextView = view.findViewById(R.id.tv_right_drawer_layout_groups)
            val tvAutomation: TextView = view.findViewById(R.id.tv_right_drawer_layout_automation)
            val tvSchedules: TextView = view.findViewById(R.id.tv_right_drawer_layout_schedules)
            val tvSettings: TextView = view.findViewById(R.id.tv_right_drawer_layout_settings)
            val tvSignOut: TextView = view.findViewById(R.id.tv_right_drawer_layout_sign_out)
            val tvEmail: TextView = view.findViewById(R.id.tv_right_drawer_email)

            ivUser.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (context is BaseActivity) {
                        context.closeLeftDrawerLayout()
                        context.closeRightDrawerLayout()
                    }
                }
            })

            tvGroups.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (context is BaseActivity) {
                        context.closeLeftDrawerLayout()
                        context.closeRightDrawerLayout()
                    }
                    context.startActivity(Intent().apply {
                        action = Intents.ACTION_MENU_START_GROUPS
                    })
                }
            })

            tvAutomation.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (context is BaseActivity) {
                        context.closeLeftDrawerLayout()
                        context.closeRightDrawerLayout()
                    }
                    context.startActivity(Intent().apply {
                        action = Intents.ACTION_MENU_START_AUTOMATION
                    })
                }
            })

            tvSchedules.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (context is BaseActivity) {
                        context.closeLeftDrawerLayout()
                        context.closeRightDrawerLayout()
                        context.startActivity(Intent().apply {
                            action = Intents.ACTION_MENU_START_SCHEDULES
                        })
                    }
                }
            })

            tvSettings.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (context is BaseActivity) {
                        context.closeLeftDrawerLayout()
                        context.closeRightDrawerLayout()
                    }
                    context.startActivity(Intent().apply {
                        action = Intents.ACTION_MENU_START_SETTINGS
                    })
                }
            })

            tvSignOut.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (context is BaseActivity) {
                        context.closeLeftDrawerLayout()
                        context.closeRightDrawerLayout()
                        context.showMsgDialog(context.getString(R.string.common_strut),
                                context.getString(R.string.common_tip_sign_out),
                                DialogInterface.OnClickListener { dialog, _ ->
                                    dialog.dismiss()
                                    signOut(context)
                                }, DialogInterface.OnClickListener { dialog, _ ->
                            dialog.cancel()
                        })
                    }
                }
            })
        }
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    fun signOut(activity: Activity) {
//        DbUtils.deleteAllExcludeLocal()
        activity.startActivity(Intent().apply {
            action = Intents.ACTION_START_LOGIN_ACTIVITY
        })
        activity.finish()
    }
}
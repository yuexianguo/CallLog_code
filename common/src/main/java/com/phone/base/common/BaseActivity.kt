package com.phone.base.common

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.AppBarLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.phone.base.common.BaseFragment.OperationCallback
import com.phone.base.common.dialog.MsgDialogFragment
import com.phone.base.common.listener.OnSingleClickListener
import com.phone.base.common.utils.LogUtil
import com.phone.base.common.utils.NetUtils
import com.phone.base.common.utils.OnClickUtils
import com.phone.base.common.utils.StatusBarUtil

private const val TAG_BASE_MSG_DIALOG = "BaseMsgDialogFragment"


@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity(), OperationCallback, BaseView {

    /**
     * Common TextView, for toolbar title
     */
    var leftTitleTV: TextView? = null

    /**
     * get the current appbar
     *
     * @return
     */
    /**
     * Common Appbar
     */
    private var appBar: AppBarLayout? = null

    /**
     * get the current toolbar
     *
     * @return
     */
    /**
     * Common Toolbar
     */
    private var toolbar: Toolbar? = null

    /**
     * Common Toast
     */
    private var mToast: Toast? = null

    /**
     * Common loading dialog
     */
    private var mHud: KProgressHUD? = null

    /**
     * Common drawer layout
     */
    private var mDrawerLayout: DrawerLayout? = null

    /**
     * Right menu
     */
    private var mIvSettings: ImageView? = null

    /**
     * Left menu
     */
    private var mIvFavorites: ImageView? = null

    /**
     * Common message dialog
     */
    private var mMsgDialog: MsgDialogFragment? = null

    /**
     * Layout id resource
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideWindowStatusbar()
        setContentView(R.layout.common_activity_base)
        initCommonAppbar()
        setContentLayout()
        initDrawLayout()
        initViews()
    }

    private fun hideWindowStatusbar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private fun initDrawLayout() {
        mDrawerLayout = findViewById(R.id.base_drawer_layout)
        mIvFavorites = findViewById(R.id.iv_base_favorites)
        mIvSettings = findViewById(R.id.iv_base_settings)
        val rightDrawerLayout: ViewGroup = findViewById(R.id.ll_right_drawer)
//        val leftDrawableLayout: ViewGroup = findViewById(R.id.cl_left_drawer)

        //default: disable
        setDrawerLayoutEnable(false)
        mIvFavorites?.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
            }
        })

        mIvSettings?.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                mDrawerLayout?.openDrawer(Gravity.RIGHT)
            }
        })

        rightDrawerLayout.setOnTouchListener { _, _ ->
            true
        }
    }

    fun showBottomMenu() {
        showLeftMenu()
        showRightMenu()
    }

    fun showLeftMenu() {
        mIvFavorites?.visibility = View.VISIBLE
    }

    fun showRightMenu() {
        mIvSettings?.visibility = View.VISIBLE
    }

    private fun hideBottomMenu() {
        hideLeftMenu()
        hideRightMenu()
    }

    fun hideLeftMenu() {
        mIvFavorites?.visibility = View.GONE
    }

    fun hideRightMenu() {
        mIvSettings?.visibility = View.GONE
    }

    fun isDrawerOpen(): Boolean {
        return mDrawerLayout?.isDrawerOpen(Gravity.LEFT) == true || mDrawerLayout?.isDrawerOpen(Gravity.RIGHT) == true
    }

    fun closeLeftDrawerLayout() {
        if (mDrawerLayout?.isDrawerOpen(Gravity.LEFT) == true) {
            mDrawerLayout?.closeDrawer(Gravity.LEFT)
        }
    }

    fun closeRightDrawerLayout() {
        if (mDrawerLayout?.isDrawerOpen(Gravity.RIGHT) == true) {
            mDrawerLayout?.closeDrawer(Gravity.RIGHT)
        }
    }

    /**
     * set content layout
     */
    private fun setContentLayout() {
        val vsContentView = findViewById<ViewStub>(R.id.vs_content_base)
        vsContentView.layoutResource = layoutId
        vsContentView.inflate()
    }

    /**
     * init appbar，include status bar, toolbar and tips
     */
    private fun initCommonAppbar() {
        appBar = findViewById(R.id.appbar_base)
        toolbar = findViewById(R.id.toolbar_base)
        leftTitleTV = findViewById(R.id.tv_middle_title_base)
        setSupportActionBar(toolbar)
        leftTitleTV?.text = ""

        //default
        setAppbarToolbarWhite(true)
    }

    /**
     * Black
     *
     * @param enableBack
     */
    fun setAppbarToolbarBlack(enableBack: Boolean) {
        //setAppbarBackgroundColor(Color.WHITE)
        setStatusToolbarBlack(enableBack)
    }

    /**
     * White
     * appbar
     *
     * @param enableBack
     */
    fun setAppbarToolbarWhite(enableBack: Boolean) {
//        setAppbarBackgroundColor(Color.BLACK)
        setStatusToolbarWhite(enableBack)
    }

    /**
     * default no drop down icon
     *
     * @param title
     * @param isLeft
     */
    fun setToolbarTitle(title: String?, isLeft: Boolean) {
        setToolbarTitle(title, isLeft, false, null)
    }

    /**
     * set toolbar title
     *
     * @param title
     */
    fun setToolbarTitle(title: String?, isLeft: Boolean, isDropDown: Boolean, listener: View.OnClickListener?) {
        leftTitleTV?.also {
            it.text = title
            if (isLeft) {
                val params = it.layoutParams as Toolbar.LayoutParams
                params.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
                it.layoutParams = params
            } else {
                val params = it.layoutParams as Toolbar.LayoutParams
                params.gravity = Gravity.CENTER
                it.layoutParams = params
            }
            if (isDropDown && !title.isNullOrEmpty()) {
                val drawable: Drawable = resources.getDrawable(android.R.drawable.arrow_down_float)
//                drawable.mutate()
//                drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
                it.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
                it.compoundDrawablePadding = 20
                it.setOnClickListener(listener)
            }
        }
    }

    /**
     * toolbar title color
     *
     * @param color
     */
    fun setToolbarTitleColor(color: Int) {
        leftTitleTV?.setTextColor(color)
    }

    /**
     * background color of appbar
     *
     * @param color
     */
    fun setAppbarBackgroundColor(color: Int) {
        appBar?.setBackgroundColor(color)
    }

    /**
     * background resource of appbar
     *
     * @param resId
     */
    fun setAppbarBackgroundResource(resId: Int) {
        appBar?.setBackgroundResource(resId)
    }

    /**
     * hide appbar
     */
    fun hideAppbar() {
        appBar?.visibility = View.GONE
    }

    /**
     * hide toolbar
     */
    fun hideToolbar() {
        toolbar?.visibility = View.GONE
    }

    /**
     * show appbar
     */
    fun showAppbar() {
        appBar?.visibility = View.VISIBLE
    }

    /**
     * show appbar
     */
    fun showToolbar() {
        toolbar?.visibility = View.VISIBLE
    }

    /**
     * black status bar,toolbar
     *
     * @param enableBack
     */
    fun setStatusToolbarBlack(enableBack: Boolean) {
        //black back button
        setToolbarBackButton(true, enableBack)
        //black title
        setToolbarTitleColor(Color.BLACK)
        toolbar?.setTitleTextColor(Color.BLACK)
        //dart status
        StatusBarUtil.setStatusBar(this, false, false)
        StatusBarUtil.setStatusTextColor(true, this)
    }

    /**
     * white status bar, toolbar
     *
     * @param enableBack
     */
    fun setStatusToolbarWhite(enableBack: Boolean) {
        //white back button
        setToolbarBackButton(false, enableBack)
        //white title
        setToolbarTitleColor(Color.WHITE)
        toolbar?.setTitleTextColor(Color.WHITE)
        //white status
        StatusBarUtil.setStatusBar(this, false, false)
        StatusBarUtil.setStatusTextColor(false, this)
    }

    /**
     * back button of toolbar
     *
     * @param isBlack
     * @param enableBack
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun setToolbarBackButton(isBlack: Boolean, enableBack: Boolean) {
        supportActionBar?.also {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(enableBack)
            //            custom icon
            val backDrawable = resources.getDrawable(R.drawable.common_ic_back_white)
            if (isBlack) {
                backDrawable.mutate()
                backDrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            }
            it.setHomeAsUpIndicator(backDrawable)
        }
    }

    /**
     * set a new appbar
     *
     * @param appBarLayout
     */
    private fun setAppBar(appBarLayout: AppBarLayout) {
        //hide the origin appbar
        hideAppbar()
        //set a new appbar
        appBar = appBarLayout
    }

    /**
     * set a new toolbar
     *
     * @param toolbar
     */
    fun setToolbar(toolbar: Toolbar?, tvTitle: TextView?) {
        hideToolbar()
        setSupportActionBar(toolbar)
        this.toolbar = toolbar
        leftTitleTV = tvTitle
    }

    /**
     * set the new appbar, toolbar and TextView of title
     *
     * @param appBarLayout
     * @param toolbar
     * @param tvTitle
     */
    fun setAppBarAndToolbar(appBarLayout: AppBarLayout, toolbar: Toolbar?, tvTitle: TextView?) {
        setAppBar(appBarLayout)
        setToolbar(toolbar, tvTitle)
    }

//    fun setTips(title: String?, tips: String?) {
//        LogUtil.d("class= setTips=" , this.javaClass.simpleName)
//        val vgTips: ViewGroup = findViewById(R.id.vg_tips_base)
//        if (!title.isNullOrEmpty() || !tips.isNullOrEmpty()) {
//            vgTips.visibility = View.VISIBLE
//            val tvTipsTitle = vgTips.findViewById<TextView>(R.id.tv_tips_title_base)
//            val tvTips = vgTips.findViewById<TextView>(R.id.tv_tips_text_base)
//            val ivTipsClose = vgTips.findViewById<ImageView>(R.id.iv_close_tips_base)
//            ivTipsClose.setOnClickListener { vgTips.visibility = View.GONE }
//            if (title.isNullOrEmpty()) {
//                tvTipsTitle.visibility = View.GONE
//            } else {
//                tvTipsTitle.visibility = View.VISIBLE
//                tvTipsTitle.text = title
//            }
//            if (tips.isNullOrEmpty()) {
//                tvTips.visibility = View.GONE
//                ivTipsClose.visibility = View.GONE
//            } else {
//                ivTipsClose.visibility = View.VISIBLE
//                tvTips.visibility = View.VISIBLE
//                tvTips.text = tips
//            }
//        } else {
//            vgTips.visibility = View.GONE
//        }
//    }

    /**
     * finish the current page, if user press back button
     *
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (OnClickUtils.isFastClick()) {
            return true
        }
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val isRightOpen: Boolean = mDrawerLayout?.isDrawerOpen(Gravity.RIGHT) ?: false
        val isLeftOpen: Boolean = mDrawerLayout?.isDrawerOpen(Gravity.LEFT) ?: false
        if (isRightOpen) {
            mDrawerLayout?.closeDrawer(Gravity.RIGHT)
            return
        } else if (isLeftOpen) {
            mDrawerLayout?.closeDrawer(Gravity.LEFT)
            return
        }
    }

    override fun showLoading(label: String?, detailLabel: String?, cancellable: Boolean) {
        if (mHud == null) {
            mHud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
        }
        mHud?.also {
            it.setLabel(label)
            it.setDetailsLabel(detailLabel)
            it.setCancellable(cancellable)
            it.show()
        }
    }

    override fun dismissLoading() {
        mHud?.dismiss()
    }

    override fun toastNetError() {
        toastLongBase(getString(R.string.common_network_error))
    }

    override fun toastGetDataFail() {
        toastShortBase(getString(R.string.common_get_data_error))
    }

    override fun toastMsg(msg: String?, isShortTime: Boolean) {
        if (isShortTime) {
            toastShortBase(msg)
        } else {
            toastLongBase(msg)
        }
    }

    override fun toastMsg(msg: String?) {
        toastMsg(msg, true)
    }

    override fun showError() {
        dismissLoading()
        if (NetUtils.isNetworkAvailable(this)) {
            toastGetDataFail()
        } else {
            toastNetError()
        }
    }

    private fun toastShortBase(msg: String?) {
        if (!msg.isNullOrEmpty()) {
            mToast?.cancel()
            mToast = null
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
            mToast?.show()
        }
    }

    private fun toastLongBase(msg: String?) {
        if (!msg.isNullOrEmpty()) {
            mToast?.cancel()
            mToast = null
            mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
            mToast?.show()
        }
    }

    override fun finishActivity() {
        finish()
    }

    fun setDrawerLayoutEnable(enable: Boolean) {
        mDrawerLayout?.apply {
            val lockMode = if (enable) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            setDrawerLockMode(lockMode)
        }
        if (enable) {
            showBottomMenu()
        } else {
            hideBottomMenu()
        }
    }

    fun showMsgDialog(title: String?, msg: String?, oKClickListener: DialogInterface.OnClickListener, cancelClickListener: DialogInterface.OnClickListener?, cancellable: Boolean,outside: Boolean = true): MsgDialogFragment {
        dismissMsgDialog()
        val dialog = MsgDialogFragment.newInstance(title, msg, cancellable,outside)
        dialog.setOkClickListener(oKClickListener)
        dialog.setCancelClickListener(cancelClickListener)
        dialog.isCancelable = cancellable
        dialog.show(supportFragmentManager, TAG_BASE_MSG_DIALOG)
        mMsgDialog = dialog
        return dialog
    }

    fun showMsgDialog(title: String?, msg: String?, oKClickListener: DialogInterface.OnClickListener, cancelClickListener: DialogInterface.OnClickListener?): MsgDialogFragment {
        return showMsgDialog(title, msg, oKClickListener, cancelClickListener, cancellable = true, outside = true)
    }

    fun dismissMsgDialog() {
        if (mMsgDialog?.isAdded == true) {
            mMsgDialog?.dismiss()
        }
        mMsgDialog = null
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }

    override fun onDestroy() {
        super.onDestroy()
        mToast?.cancel()
        mToast = null

        dismissMsgDialog()
    }
}
package com.phone.base.common

import android.app.Application
import android.content.Context
import com.phone.base.common.manager.VolleyManager
import com.phone.base.common.utils.LogUtil
import io.reactivex.plugins.RxJavaPlugins
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/9/1
 */
open class BaseApplication : Application() {

    companion object {
        private lateinit var INSTANCE: BaseApplication

        @JvmStatic
        val application: Application
            get() = INSTANCE

        @JvmStatic
        val context: Context
            get() = INSTANCE.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initRealm()
        initVolley()
        LogUtil.setEnable(BuildConfig.DEBUG)
        RxJavaPlugins.setErrorHandler {
            LogUtil.e("error: $it")
        }
    }

    private fun initRealm() {
        Realm.init(this.applicationContext)
        //Call `Realm.init(Context)` before creating a RealmConfiguration to avoid IllegalStateException
        val defaultConfig = Realm.getDefaultConfiguration()
        defaultConfig?.let {
            Realm.deleteRealm(it)
        }
        val config = RealmConfiguration.Builder()
                .name("$packageName.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }

    private fun initVolley() {
        VolleyManager.getInstance().init(this)
    }
}
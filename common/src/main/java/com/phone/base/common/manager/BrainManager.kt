package com.phone.base.common.manager

import android.content.Intent
import androidx.annotation.MainThread
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.phone.base.common.BaseApplication
import com.phone.base.common.Intents.Companion.ACTION_BRAIN_STATUS_CHANGED
import com.phone.base.common.network.ApiFactory
import com.phone.base.common.realmObject.DeviceObject
import com.phone.base.common.utils.LogUtil
import io.realm.Realm

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/10/26
 */
object BrainManager {
    var deviceObject: DeviceObject? = null

    @Volatile
    var localControl: Boolean = false
        set(value) {
            if (field != value) {
                LocalBroadcastManager.getInstance(BaseApplication.context).sendBroadcast(Intent().apply {
                    action = ACTION_BRAIN_STATUS_CHANGED
                })
                field = value
            }
        }

    @Volatile
    var cloudControl: Boolean = false
        set(value) {
            if (field != value) {
                LocalBroadcastManager.getInstance(BaseApplication.context).sendBroadcast(Intent().apply {
                    action = ACTION_BRAIN_STATUS_CHANGED
                })
                field = value
            }
        }

    val canControl: Boolean
        get() {
            return localControl || cloudControl
        }

    init {
        LogUtil.d("BrainManager init")
    }

    fun clear() {
        deviceObject = null
        localControl = false
        cloudControl = false
    }

    fun toJsonObject(): JsonObject {
        var jsonObject = JsonObject()
        deviceObject?.let {
            val jsonStr = Gson().toJson(it)
            jsonObject = JsonParser.parseString(jsonStr).asJsonObject
        }
        return jsonObject
    }

    @MainThread
    fun saveNewIp(ip: String) {
        if (ip != deviceObject?.ip) {
            ApiFactory.setTargetBaseIp(ip)
            val realm = Realm.getDefaultInstance()
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            deviceObject?.ip = ip
            realm.commitTransaction()
        }
    }
}
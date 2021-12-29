package com.phone.base.common.utils

import android.text.TextUtils
import com.phone.base.common.manager.BrainManager
import com.phone.base.common.realmObject.DeviceObject
import io.realm.Realm

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/5/25
 */
object DeviceObjectUtils {
    fun getDeviceName(staMac: String, realm: Realm):String{
        var targetName = ""
        val deviceObject = realm.where(DeviceObject::class.java).equalTo("staMac", staMac).findFirst()
        if (deviceObject != null) {
            targetName = deviceObject.deviceName
        }
        return targetName
    }

    fun getDeviceObject(staMac:String):DeviceObject?{
        val realm = Realm.getDefaultInstance()
        return realm.where(DeviceObject::class.java).equalTo("staMac", staMac).findFirst()
    }

    fun setScmVer(ver:String){
        val realm = Realm.getDefaultInstance()
        val deviceObject = BrainManager.deviceObject
        deviceObject?.apply {
            val deviceObject = realm.where(DeviceObject::class.java).equalTo("staMac", staMac).findFirst()
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            deviceObject?.scmVer = ver
            BrainManager.deviceObject = deviceObject
            realm.commitTransaction()
        }
    }

    fun setIOTMVer(ver:String){
        val realm = Realm.getDefaultInstance()
        val deviceObject = BrainManager.deviceObject
        deviceObject?.apply {
            val deviceObject = realm.where(DeviceObject::class.java).equalTo("staMac", staMac).findFirst()
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            deviceObject?.iotmVer = ver
            BrainManager.deviceObject = deviceObject
            realm.commitTransaction()
        }
    }
}
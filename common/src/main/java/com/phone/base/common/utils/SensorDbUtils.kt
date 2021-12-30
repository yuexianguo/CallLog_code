package com.phone.base.common.utils

import androidx.annotation.MainThread
import com.google.gson.JsonObject
import com.phone.base.common.BaseApplication
import com.phone.base.common.R
import com.phone.base.common.realmObject.SensorObject
import com.phone.base.common.utils.PrefUtils.readString
import io.realm.Realm

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2021/1/8
 */
const val SENSITIVITY_HIGH = 3
const val SENSITIVITY_MEDIUM = 2
const val SENSITIVITY_LOW = 254
const val RANGE_HIGH = 5
const val RANGE_MEDIUM = 3
const val RANGE_LOW = 2
val sensitivityArray = intArrayOf(SENSITIVITY_HIGH, SENSITIVITY_MEDIUM, SENSITIVITY_LOW)
val rangeArray = intArrayOf(RANGE_HIGH, RANGE_MEDIUM, RANGE_LOW)

object SensorDbUtils {

    fun saveSensor(sensorAddress: Int, responseObj: JsonObject, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val isOnline = if (!responseObj.has("online")) false else responseObj.get("online").asBoolean
            if (!isOnline) {
                val sensorObject = realm.where(SensorObject::class.java).equalTo("addr", sensorAddress).findFirst()
                sensorObject?.deleteFromRealm()
            } else {

                if (responseObj.has("sensitivity") && responseObj.has("range")) {
                    var occStateJson = JsonObject()
                    occStateJson.addProperty("sensitivity", responseObj.get("sensitivity").asInt)
                    occStateJson.addProperty("range", responseObj.get("range").asInt)
                    responseObj.add("occState", occStateJson)
                }

                responseObj.addProperty("addr", sensorAddress)
                if (responseObj.has("occState")) {
                    val occState = responseObj.get("occState").asJsonObject
                    if (occState.has("occupied")) {
                        responseObj.addProperty("occupied", occState.get("occupied").asInt)
                    }
                    if (occState.has("occToUnoccDelay")) {
                        responseObj.addProperty("occToUnoccDelay", occState.get("occToUnoccDelay").asInt)
                    }
                    if (occState.has("UnoccToOccDelay")) {
                        responseObj.addProperty("UnoccToOccDelay", occState.get("UnoccToOccDelay").asInt)
                    }
                    if (occState.has("sensitivity")) {
                        responseObj.addProperty("sensitivity", occState.get("sensitivity").asInt)
                    }
                    if (occState.has("range")) {
                        responseObj.addProperty("range", occState.get("range").asInt)
                    }
                    responseObj.remove("occState")
                }
                if (responseObj.has("lightState")) {
                    val lightState = responseObj.get("lightState").asJsonObject
                    if (lightState.has("measLvl")) {
                        responseObj.addProperty("measLvl", lightState.get("measLvl").asInt)
                    }
                    if (lightState.has("minMeasLvl")) {
                        responseObj.addProperty("minMeasLvl", lightState.get("minMeasLvl").asInt)
                    }
                    if (lightState.has("maxMeasLvl")) {
                        responseObj.addProperty("maxMeasLvl", lightState.get("maxMeasLvl").asInt)
                    }
                    if (lightState.has("targetLvl")) {
                        responseObj.addProperty("targetLvl", lightState.get("targetLvl").asInt)
                    }
                    if (lightState.has("changeRate")) {
                        responseObj.addProperty("changeRate", lightState.get("changeRate").asInt)
                    }
                    responseObj.remove("lightState")
                }
                realm.createOrUpdateObjectFromJson(SensorObject::class.java, responseObj.toString())
            }
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun saveSensorAsync(sensorAddress: Int, sensorJsonObj: JsonObject, onSuccess: Realm.Transaction.OnSuccess?,
                        onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                sensorJsonObj.addProperty("addr", sensorAddress)
                it.createOrUpdateObjectFromJson(SensorObject::class.java, sensorJsonObj.toString())
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteSensor(sensorAddress: Int, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val sensorObject = realm.where(SensorObject::class.java).equalTo("addr", sensorAddress).findFirst()
            sensorObject?.deleteFromRealm()
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun deleteSensorAsync(sensorAddress: Int, onSuccess: Realm.Transaction.OnSuccess?,
                          onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                val sensorObject = it.where(SensorObject::class.java).equalTo("addr", sensorAddress).findFirst()
                sensorObject?.deleteFromRealm()
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun modifySensor(sensorAddress: Int, params: JsonObject, isNeedClose: Boolean) {
        params.addProperty("online", true)
        saveSensor(sensorAddress, params, isNeedClose)
    }

    @MainThread
    fun modifySensorAsync(sensorAddress: Int, params: JsonObject, onSuccess: Realm.Transaction.OnSuccess?,
                          onError: Realm.Transaction.OnError?) {
        saveSensorAsync(sensorAddress, params, onSuccess, onError)
    }

    @MainThread
    fun getSensor(sensorAddress: Int): SensorObject? {
        return Realm.getDefaultInstance().where(SensorObject::class.java).equalTo("addr", sensorAddress).findFirst()
    }

    @MainThread
    fun getSensors(type: Int?): List<SensorObject> {
        return if (type == null) {
            Realm.getDefaultInstance().where(SensorObject::class.java).findAll()
        } else {
            Realm.getDefaultInstance().where(SensorObject::class.java).equalTo("type", type).findAll()
        }
    }

    fun updateSensorVer(sensorAddress: Int, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val sensorObject = realm.where(SensorObject::class.java).equalTo("addr", sensorAddress).findFirst()
            readString(PrefUtils.KEY_SENSOR_FW_LOCAL_VER)?.also {
                if (it.length > 5) {
                    sensorObject?.fwVer = it.substring(0, 5)
                    realm.commitTransaction()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    fun getSensitivityLevelString(position: Int): String {
        val stringArray = BaseApplication.context.resources.getStringArray(R.array.switch_sensitivity)
        return stringArray[position]
    }

    fun getSenLevelPositionByValue(sensitivity: Int?, range: Int?): Int? {
        var position: Int? = null
        if (sensitivity != null && range != null) {
            for (i in sensitivityArray.indices) {
                LogUtil.d("sensitivityArray[$i] = ${sensitivityArray[i]} init sensitivity =$sensitivity,rangeArray[$i] = ${rangeArray[i]} init range=$range")
                if (sensitivity.toInt() == sensitivityArray[i] && range.toInt() == rangeArray[i]) {
                    position = i
                }
            }
        }
        return position
    }

//    fun getSensitivityValue(position:Int):Int{
//        return sensitivityArray[position]
//    }
//
//    fun getSensitivityValue(position:Int):String{
//        val stringArray = BaseApplication.context.resources.getStringArray(R.array.switch_sensitivity)
//        return stringArray[position]
//    }
}
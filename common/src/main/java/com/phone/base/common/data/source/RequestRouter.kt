package com.phone.base.common.data.source

import com.google.gson.JsonObject
import com.phone.base.common.MQTT_REQUEST_TIMEOUT
import com.phone.base.common.manager.BrainManager
import com.phone.base.common.network.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/3/16
 */
object RequestRouter {

    fun getScheduleFlow(params: JsonObject, timeoutTime: Long = MQTT_REQUEST_TIMEOUT, callback: (Flow<JsonObject?>) -> Unit) {
        if (BrainManager.localControl) {
            callback.invoke(flow {
                val requestBody = params.toString().toRequestBody(BaseDataSource.MEDIA_TYPE_JSON)
                val response = ApiFactory.getCoroutineController().requestSchedulesAsync(requestBody).await()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(body)
                    } else {
                        throw Throwable("Response is null")
                    }
                } else {
                    throw Throwable("Response is error, code ${response.code()}")
                }
            }.flowOn(Dispatchers.Default))

        }
    }

    fun getDeviceFlow(params: JsonObject, timeoutTime: Long = MQTT_REQUEST_TIMEOUT, callback: (Flow<JsonObject?>) -> Unit) {
        if (BrainManager.localControl) {
            callback.invoke(flow {
                val requestBody = params.toString().toRequestBody(BaseDataSource.MEDIA_TYPE_JSON)
                val response = ApiFactory.getCoroutineController().requestDeviceAsync(requestBody).await()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(body)
                    } else {
                        throw Throwable("Response is null")
                    }
                } else {
                    throw Throwable("Response is error, code ${response.code()}")
                }
            }.flowOn(Dispatchers.Default))

        }
    }
}

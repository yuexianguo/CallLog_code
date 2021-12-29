package com.phone.base.common.data.source

import androidx.core.util.Consumer
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.phone.base.common.RequestActions
import com.phone.base.common.data.bean.*
import com.phone.base.common.exception.MqttNullResponseException
import com.phone.base.common.utils.LogUtil
import com.phone.base.common.utils.ScheduleDbUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * description ：Use Flow
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/2/3
 */
class CStrutDataRepository : CStrutDataSource {

    override fun getScheduleList(scope: CoroutineScope?, success: Consumer<in ScheduleListResponse>, fail: Consumer<in Throwable?>) {
        val params = JsonObject()
        params.addProperty("action", RequestActions.SCHEDULE_LIST)
        LogUtil.d(BaseDataSource.TAG, "getScheduleList: request json=$params")

        RequestRouter.getScheduleFlow(params) { flow ->
            scope?.launch(context = Dispatchers.Main) {
                flow.map { jsonObject ->
                    LogUtil.d(BaseDataSource.TAG, "getScheduleList: response json=$jsonObject")
                    val scheduleListResponse: ScheduleListResponse? = Gson().fromJson(jsonObject.toString(), ScheduleListResponse::class.java)
                    scheduleListResponse
                            ?: throw MqttNullResponseException("Schedule Mqtt Response is null")
                }.onEach {
                    ScheduleDbUtils.deleteAllSchedules(false)
                }.catch { e ->
                    fail.accept(e)
                }.collect {
                    success.accept(it)
                }
            }
        }
    }

    override fun readSchedule(scope: CoroutineScope?, scheduleReadRequest: ScheduleReadRequest, success: Consumer<in ScheduleReadResponse>, fail: Consumer<in Throwable?>) {
        val gson = Gson()
        val params = gson.toJson(scheduleReadRequest)
        val paramsJsonObj = JsonParser.parseString(params).asJsonObject

        if (scheduleReadRequest.addrs == null) {
            paramsJsonObj.remove("addrs")
        } else {
            paramsJsonObj.remove("time")
            paramsJsonObj.remove("startAddr")
        }
        if (scheduleReadRequest.time == null) {
            paramsJsonObj.remove("time")
        } else {
            paramsJsonObj.remove("addrs")
            paramsJsonObj.remove("startAddr")
        }
        LogUtil.d(BaseDataSource.TAG, "readSchedule: request json=$paramsJsonObj")

        RequestRouter.getScheduleFlow(paramsJsonObj) { flow ->
            scope?.launch(context = Dispatchers.Main) {
                flow.map { jsonObject ->
                    LogUtil.d(BaseDataSource.TAG, "readSchedule: response json=$jsonObject")
                    val scheduleReadResponse: ScheduleReadResponse? = gson.fromJson(jsonObject.toString(), ScheduleReadResponse::class.java)
                    scheduleReadResponse
                            ?: throw MqttNullResponseException("Schedule Mqtt Response is null")
                }.onEach {
                    ScheduleDbUtils.saveReadSchedules(it, false)
                }.catch { e ->
                    fail.accept(e)
                }.collect {
                    success.accept(it)
                }
            }
        }
    }

    override fun createSchedule(scope: CoroutineScope?, scheduleCreateRequest: ScheduleCreateRequest, success: Consumer<in ScheduleCreateResponse>, fail: Consumer<in Throwable?>) {
        val gson = Gson()
        val params = gson.toJson(scheduleCreateRequest)
        val jsonObjParams = JsonParser.parseString(params).asJsonObject
        LogUtil.d(BaseDataSource.TAG, "createSchedule: request json=$jsonObjParams")

        RequestRouter.getScheduleFlow(jsonObjParams) { flow ->
            scope?.launch(context = Dispatchers.Main) {
                flow.map { jsonObject ->
                    LogUtil.d(BaseDataSource.TAG, "createSchedule: response json=$jsonObject")
                    val scheduleCreateResponse: ScheduleCreateResponse? = gson.fromJson(jsonObject.toString(), ScheduleCreateResponse::class.java)
                    scheduleCreateResponse
                            ?: throw MqttNullResponseException("Schedule Mqtt Response is null")
                }.onEach {
                    ScheduleDbUtils.saveNewSchedule(it.addr, scheduleCreateRequest, false)
                }.catch { e ->
                    fail.accept(e)
                }.collect {
                    success.accept(it)
                }
            }
        }
    }

    override fun modifySchedule(scope: CoroutineScope?, scheduleModifyRequest: ScheduleModifyRequest, success: Consumer<in ScheduleModifyResponse>, fail: Consumer<in Throwable?>) {
        val gson = Gson()
        val params = gson.toJson(scheduleModifyRequest)
        val paramsJsonObj = JsonParser.parseString(params).asJsonObject
//        scheduleModifyRequest.name ?: paramsJsonObj.remove("name")
//        scheduleModifyRequest.automation ?: paramsJsonObj.remove("automation")
//        scheduleModifyRequest.type ?: paramsJsonObj.remove("type")
//        scheduleModifyRequest.time ?: paramsJsonObj.remove("time")
//        scheduleModifyRequest.enabled ?: paramsJsonObj.remove("enable")
        LogUtil.d(BaseDataSource.TAG, "modifySchedule: request json=$paramsJsonObj")

        RequestRouter.getScheduleFlow(paramsJsonObj) { flow ->
            scope?.launch(context = Dispatchers.Main) {
                flow.map { jsonObject ->
                    LogUtil.d(BaseDataSource.TAG, "modifySchedule: response json=$jsonObject")
                    val scheduleModifyResponse: ScheduleModifyResponse? = Gson().fromJson(jsonObject.toString(), ScheduleModifyResponse::class.java)
                    scheduleModifyResponse
                            ?: throw MqttNullResponseException("Schedule Mqtt Response is null")
                }.onEach {
                    ScheduleDbUtils.modifySchedule(scheduleModifyRequest, false)
                }.catch { e ->
                    fail.accept(e)
                }.collect {
                    success.accept(it)
                }
            }
        }
    }

    override fun deleteSchedule(scope: CoroutineScope?, scheduleAddress: Int, success: Consumer<in DeleteResponse>, fail: Consumer<in Throwable?>) {
        val params = JsonObject()
        params.addProperty("action", RequestActions.SCHEDULE_DELETE)
        params.addProperty("addr", scheduleAddress)
        LogUtil.d(BaseDataSource.TAG, "deleteSchedule: request json=$params")
        RequestRouter.getScheduleFlow(params) { flow ->
            scope?.launch(context = Dispatchers.Main) {
                flow.map { jsonObject ->
                    LogUtil.d(BaseDataSource.TAG, "deleteSchedule: response json=$jsonObject")
                    val scheduleDeleteResponse: DeleteResponse? = Gson().fromJson(jsonObject.toString(), DeleteResponse::class.java)
                    scheduleDeleteResponse
                            ?: throw MqttNullResponseException("Schedule Mqtt Response is null")
                }.onEach {
                    ScheduleDbUtils.deleteSchedule(scheduleAddress, false)
                }.catch { e ->
                    fail.accept(e)
                }.collect {
                    success.accept(it)
                }
            }
        }
    }

    override fun sendTimeZoneToBrain(scope: CoroutineScope?, timeZone: String, success: Consumer<in JsonObject>, fail: Consumer<in Throwable?>) {
        val params = JsonObject()
        params.addProperty("timeZone", timeZone)
        LogUtil.d(BaseDataSource.TAG, "sendTimeZoneToBrain: request json=$params")
        RequestRouter.getDeviceFlow(params) { flow ->
            scope?.launch(context = Dispatchers.Main) {
                flow.map { jsonObject ->
                    LogUtil.d(BaseDataSource.TAG, "sendTimeZoneToBrain: response json=$jsonObject")
                    jsonObject ?: JsonObject()
                }.catch { e ->
                    fail.accept(e)
                }.collect {
                    success.accept(it)
                }
            }
        }
    }
}

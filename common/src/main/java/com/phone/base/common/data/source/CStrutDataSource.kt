package com.phone.base.common.data.source

import androidx.core.util.Consumer
import com.google.gson.JsonObject
import com.phone.base.common.data.bean.*
import kotlinx.coroutines.CoroutineScope

/**
 * description ：Use Flow
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/4/23
 */
interface CStrutDataSource : BaseDataSource {

    fun createSchedule(scope: CoroutineScope?, scheduleCreateRequest: ScheduleCreateRequest, success: Consumer<in ScheduleCreateResponse>, fail: Consumer<in Throwable?>)

    fun modifySchedule(scope: CoroutineScope?, scheduleModifyRequest: ScheduleModifyRequest, success: Consumer<in ScheduleModifyResponse>, fail: Consumer<in Throwable?>)

    fun getScheduleList(scope: CoroutineScope?, success: Consumer<in ScheduleListResponse>, fail: Consumer<in Throwable?>)

    fun readSchedule(scope: CoroutineScope?, scheduleReadRequest: ScheduleReadRequest, success: Consumer<in ScheduleReadResponse>, fail: Consumer<in Throwable?>)

    fun deleteSchedule(scope: CoroutineScope?, scheduleAddress: Int, success: Consumer<in DeleteResponse>, fail: Consumer<in Throwable?>)

    fun sendTimeZoneToBrain(scope: CoroutineScope?, timeZone: String, success: Consumer<in JsonObject>, fail: Consumer<in Throwable?>)
}
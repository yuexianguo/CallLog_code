package com.phone.base.common.network.api

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/2/3
 */
interface CoroutineController {

    @POST("/schedules")
    fun requestSchedulesAsync(@Body body: RequestBody): Deferred<Response<JsonObject>>

    @POST("/device")
    fun requestDeviceAsync(@Body body: RequestBody): Deferred<Response<JsonObject>>
}
package com.phone.base.common.network.api

import com.google.gson.JsonObject
import com.phone.base.common.data.bean.OTAResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 *
 */
interface StrutController {

    @Headers("Content-Type:$TOKEN_URL_CONTENT_TYPE", "Authorization:$TOKEN_URL_AUTHORIZATION")
    @POST
    fun thirdLoginToken(@Url url: String, @Body body: RequestBody): Observable<JsonObject?>

    @GET("path")
    fun getQueryHint(@Query("params1") params1: String, @Query("params2") params2: String): Observable<JsonObject?>

    @POST("path")
    fun updateAlexaStatus(@Body body: RequestBody): Observable<JsonObject>

    @POST("/fixture")
    fun sendActionToFixture(@Body body: RequestBody): Observable<JsonObject>

    @POST("/group")
    fun sendActionToGroup(@Body body: RequestBody): Observable<JsonObject>

    @POST("/scene")
    fun sendActionToScene(@Body body: RequestBody): Observable<JsonObject>

    @POST("/sensor")
    fun sendActionToSensor(@Body body: RequestBody): Observable<JsonObject>

    @POST("/schedules")
    fun sendActionToSchedules(@Body body: RequestBody): Observable<JsonObject>

    @POST("/automation")
    fun sendActionToAutomation(@Body requestBody: RequestBody): Observable<JsonObject>

    @POST("/smartsocket")
    fun sendActionToSmartSocket(@Body body: RequestBody): Observable<JsonObject>

    @POST("/input")
    fun sendActionToInput(@Body body: RequestBody): Observable<JsonObject>

    @POST("/device")
    fun configDevice(@Body body: RequestBody): Observable<JsonObject>

    @POST("/dmx")
    fun sendActionToDmx(@Body body: RequestBody): Observable<JsonObject>

    @POST
    fun configDeviceWithUrl(@Url url: String, @Body body: RequestBody): Observable<JsonObject>

    @POST
    fun configNetWithUrl(@Url url: String, @Body body: RequestBody): Observable<JsonObject>

    //used for paring
    @Headers("Content-Type:application/ota-stream", "Keep-Alive:true")
    @POST("/ota")
    fun uploadFile(@Header("Module") moduleHeader: String, @Body formBody: RequestBody): Observable<OTAResponse>

    //used for paring
    @Headers("Content-Type:application/ota-stream", "Keep-Alive:true")
    @POST("/ota")
    fun uploadFileWithAddress(@Header("Module") moduleHeader: String, @Header("Address") address: Int, @Body formBody: RequestBody): Observable<OTAResponse>

    //used for special url
    @Headers("Content-Type:application/ota-stream", "Keep-Alive:true")
    @POST
    fun uploadFileWithUrl(@Url url: String, @Header("Module") moduleHeader: String, @Body formBody: RequestBody): Observable<OTAResponse>

    //used for special url
    @Headers("Content-Type:application/ota-stream", "Keep-Alive:true")
    @POST
    fun uploadFileWithUrlAndAddress(@Url url: String, @Header("Module") moduleHeader: String, @Header("Address") address: Int, @Body formBody: RequestBody): Observable<OTAResponse>

    @Streaming
    @GET
    fun downloadFw(@Url url: String): Observable<ResponseBody>

    @POST("/batchOTA")
    fun batchAllOTA(@Body requestBody: RequestBody): Observable<JsonObject>

    //used for paring
    @Headers("Content-Type:application/ota-stream", "Keep-Alive:true")
    @POST("/updateImage")
    fun prepareAllFw(@Header("Module") moduleHeader: String, @Header("Subtype") subTypeHeader: String, @Header("Version") versionHeader: String,  @Header("Checksum") checksumHeader: String,@Body formBody: RequestBody): Observable<OTAResponse>
}
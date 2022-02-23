package com.phone.base.common.network.api

import com.google.gson.JsonObject
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

   }
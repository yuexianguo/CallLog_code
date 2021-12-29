package com.phone.base.common.lamdaFunction

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction
import com.google.gson.JsonObject
import com.phone.base.common.lamdaFunction.bean.StrutUserAuthResponse
import com.phone.base.common.lamdaFunction.bean.ValidateUserRequest
import com.phone.base.common.lamdaFunction.bean.ValidateUserResponse

interface LambdaFunctions {
    interface LambdaValidateUser {
        @LambdaFunction
        fun windermierValidateUser(request: ValidateUserRequest): ValidateUserResponse?
    }

    interface StrutUserAuthorize {
        @LambdaFunction
        fun strutUserAuthorize(params: HashMap<String, String>): StrutUserAuthResponse
    }

    interface StrutGetFirmware {
        @LambdaFunction(functionName = "strutGetLatestFirmware")
        fun strutGetLatestFWJson(jsonObject: JsonObject?): JsonObject?
        @LambdaFunction(functionName = "strutGetLatestFirmware:prodLive")
        fun strutGetLatestFWJson_pro(jsonObject: JsonObject?): JsonObject?
        @LambdaFunction(functionName = "strutGetLatestFirmware:dev")
        fun strutGetLatestFWJson_dev(jsonObject: JsonObject?): JsonObject?
    }
    interface StrutGetMyBrainDevices {
        @LambdaFunction(functionName = "strutGetMyBrainDevices")
        fun strutGetMyBrainDevices(jsonObject: JsonObject?): JsonObject?
        @LambdaFunction(functionName = "strutGetMyBrainDevices:prodLive")
        fun strutGetMyBrainDevices_pro(jsonObject: JsonObject?): JsonObject?
        @LambdaFunction(functionName = "strutGetMyBrainDevices:dev")
        fun strutGetMyBrainDevices_dev(jsonObject: JsonObject?): JsonObject?
    }

    interface StrutGetGeoLocation {
        @LambdaFunction(functionName = "strutGetGeoLocation:dev")
        fun getGeoLocationDev(jsonObject: JsonObject): JsonObject

        @LambdaFunction(functionName = "strutGetGeoLocation:prodLive")
        fun getGeoLocationLive(jsonObject: JsonObject): JsonObject
    }
}
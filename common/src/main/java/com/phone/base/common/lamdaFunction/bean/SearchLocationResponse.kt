package com.phone.base.common.lamdaFunction.bean

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2021/3/25
 */
data class SearchLocationResponse(
        val body: LocationBody,
        val result: Boolean
)

data class LocationBody(
        val address: String,
        val location: LocationData
)

data class LocationData(
        val lat: Double,
        val lng: Double
)
package com.phone.base.common.data.bean

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/12/10
 */
data class SensorListResponse(
        val action: Int,
        val result: String,
        //addr, Optional.
        //In the case of success, a numerical list of the addresses of all sensors.
        //In the case of failure, this will not be present.
        val addr: List<Int>?

)
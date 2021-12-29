package com.phone.base.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/12/16
 */
data class ScheduleListResponse(
        val action: Int,
        val result: String,
        val addr: List<Int>?
)
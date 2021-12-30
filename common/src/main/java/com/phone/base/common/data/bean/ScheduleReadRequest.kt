package com.phone.base.common.data.bean

import com.phone.base.common.RequestActions

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/12/16
 */
data class ScheduleReadRequest(
        //four ways to Read Schedule, select one only.
        var addrs: List<Int>? = null,
        var time: String? = null,
        var startAddr: Int = 0
) {
    val action: Int = RequestActions.SCHEDULE_READ
}
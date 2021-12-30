package com.phone.base.common.data.bean

import com.phone.base.common.RequestActions

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/12/16
 */
data class ScheduleModifyRequest(
        val addr: Int,
        var name: String? = null, // null value, do not need to modify
        var time: String? = null,
        var automation: Int? = null,
        //EXECUTE = 1, ENABLE = 2, DISABLE = 3
        var triggerAction: Int? = null,
        var type: String? = null,
        var enabled: Boolean? = null
) {
    val action: Int = RequestActions.SCHEDULE_MODIFY
}
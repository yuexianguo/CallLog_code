package com.phone.base.common.data.bean

import com.phone.base.common.RequestActions

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/12/10
 */
data class SensorModifyRequest(
        val action: Int = RequestActions.SENSOR_MODIFY,
        val addr: Int,
        val name: String?,
        val targetLvl: Int?,
        val changeRate: Int?,
        val occState: Int?,
        val unoccScene: Int?,
        val disScene: Int?,
        val occToUnoccDelay: Int?,
        val UnoccToOccDelay: Int?
)
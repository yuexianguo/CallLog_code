package com.phone.base.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/12/10
 */
data class SensorReadResponse(
        val action: Int,
        val result: String,
        val name: String? = null,
        val type: Int? = null,
        val online: Boolean? = null,
        val fwVer: String? = null,
        val occState: OccState? = null,
        val lightState: LightState? = null
)

data class LightState(
        val changeRate: Int,
        val maxMeasLvl: Int,
        val measLvl: Int,
        val minMeasLvl: Int,
        val targetLvl: Int
)

data class OccState(
        val UnoccToOccDelay: Int,
        val occToUnoccDelay: Int,
        val occupied: Int,
        val sensitivity: Int,
        val range: Int
)
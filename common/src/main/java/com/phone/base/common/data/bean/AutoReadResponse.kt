package com.phone.base.common.data.bean

/**
 * description ：
 * author : Andy.Guo
 * email : Andy.Guo@waclighting.com.cn
 * date : 2020/12/23
 */
data class AutoReadResponse(
        val action: Int,
        val result: String,
        val name: String,
        val type: Int,
        val enabled:Boolean,
        //standard
        val fixture: List<AutoSingleStateBean>,
        val group: List<AutoSingleStateBean>,
        //occupy
        val occSensors:List<Int>?,
        val fixtWhenOcc:List<AutoSingleStateBean>?,
        val grpWhenOcc:List<AutoSingleStateBean>?,
        val fixtWhenUnocc:List<AutoSingleStateBean>?,
        val grpWhenUnocc:List<AutoSingleStateBean>?,
        val fixtWhenDisabled:List<AutoSingleStateBean>?,
        val grpWhenDisabled:List<AutoSingleStateBean>?,
        //light
        val lightSensor:List<Int>?,
        val daylightGroup:Int,
        val minTargetLevel:Int,
        val maxTargetLevel:Int
)
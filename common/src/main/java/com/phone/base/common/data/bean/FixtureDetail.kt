package com.phone.base.common.data.bean

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/8/12
 */
data class FixtureDetail(
        //Single Color Dimmable Light
        var busVer: String,
        var currentLevel: Int,
        var currentOutput: Int,
        var dateCode: String,
        var factory: Int,
        var fwVer: String,
        var ledDriver: String,
        var model: String,
        var pcbVer: String,
        //more, added for Tunable White Dimmable Light
        var schemVer: String?,
        var upDownCircleTime: Int,
        val leftRightCircleTime:Int
)
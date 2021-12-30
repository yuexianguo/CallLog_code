package com.phone.base.common.data.bean

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/8/12
 */
data class FixtureState(
        //Single Color Dimmable Light
        var status: Boolean,
        var level: Int,
        var online: Boolean,
        //more, added for Tunable White Dimmable Light
        var temperature: Int = 0,
        //more, RGBW Dimmable Light
        var blue: Int = 0,
        var green: Int = 0,
        var red: Int = 0,
        // more, Motorized Trackhead – Single Color Dimmable Light
        var zoom: Int = 0,
        var axis: Int = 0,
        var timeLr: Int = 0,
        var timeUd: Int = 0,
        var tilt: Int = 0,
        var pan: Int? = 0
)
package com.phone.base.common.data.bean

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/8/24
 */
data class NewAutoReadResponse(
        val action: Int,
        val enabled: Boolean,
        val fixture: Fixture,
        val group: Group,
        val name: String,
        val occSensors: List<Int>,
        val result: String,
        val type: Int
)

data class Fixture(
        val disabled: List<AutoSingleStateBean>?,
        val occupied: List<AutoSingleStateBean>?,
        val vacant: List<AutoSingleStateBean>?
)

data class Group(
        val disabled: List<AutoSingleStateBean>?,
        val occupied: List<AutoSingleStateBean>?,
        val vacant: List<AutoSingleStateBean>?
)

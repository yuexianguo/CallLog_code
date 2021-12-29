package com.phone.base.common.data.bean

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/3/12
 */
data class FixturesReadBean(
        val addr: Int,
        val name: String,
        val type: Int,
        val detail: FixtureDetail?,
        val state: FixtureState,
        val tune: FixtureTune?
)

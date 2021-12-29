package com.phone.base.common.data.bean

/**
 * description ：
 * author : Andy.Guo
 * email : Andy.Guo@waclighting.com.cn
 * date : 2021/3/12
 */
data class FixtureListReadResponse(
        val action: Int,
        val result: String,
        val fixture:List<FixturesReadBean>?
)
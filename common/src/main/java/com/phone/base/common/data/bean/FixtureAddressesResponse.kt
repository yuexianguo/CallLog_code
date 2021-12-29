package com.phone.base.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/7/23
 */
data class FixtureAddressesResponse(
        val action: Int,
        val result: String,
        val addr: List<Int>?
)
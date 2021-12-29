package com.phone.base.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/3/29
 */
data class DmxReadResponse(
        val addr: Int,
        val mapping: List<Mapping>,
        val thing: Int,
        val type: Int
) : BaseResponse()

data class Mapping(
        val channel: Int,
        val `field`: String,
        val input: String
)
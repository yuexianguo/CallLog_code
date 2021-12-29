package com.phone.base.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/3/29
 */
data class DmxCreateResponseBean(
        val type: Int,
        val thing: Int,
        val mapping: List<Mapping>,
) : BaseResponse()
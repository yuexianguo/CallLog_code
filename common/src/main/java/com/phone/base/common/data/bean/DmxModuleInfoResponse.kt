package com.phone.base.common.data.bean

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2021/3/29
 */
data class DmxModuleInfoResponse(
        //module address
        val addr: Int,
        val online: Boolean,
        val enabled: Boolean,
        val detail: DmxDetail
) : BaseResponse()

data class DmxDetail(
        //Single Color Dimmable Light
        var busVer: String,
        var dateCode: String,
        var factory: Int,
        var fwVer: String,
        var pcbVer: String
)
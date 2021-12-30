package com.phone.base.common.data.bean

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/7/23
 */
data class GroupReadResponse(
        val action: Int,
        val result: String,
        val name: String,
        //fixture address list in a group
        val fixture: List<Int>?
)
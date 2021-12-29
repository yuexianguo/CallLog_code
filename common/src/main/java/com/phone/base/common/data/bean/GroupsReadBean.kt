package com.phone.base.common.data.bean

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/3/12
 */
data class GroupsReadBean(
        val addr: Int,
        val name: String,
        //fixture address list in a group
        val fixture: List<Int>?
)

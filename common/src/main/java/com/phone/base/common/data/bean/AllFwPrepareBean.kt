package com.phone.base.common.data.bean

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/3/3
 */
data class AllFwPrepareBean(
        val Module: String,
        val Subtype: Int,
        val Version: String,
        val Blocking: Boolean,
        val filePath: String
)

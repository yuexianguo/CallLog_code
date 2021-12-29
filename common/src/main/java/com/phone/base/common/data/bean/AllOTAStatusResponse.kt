package com.phone.base.common.data.bean

data class AllOTAStatusResponse(
    val CCMStatus: CCMStatus?,
    val DCMStatus: List<DCMStatus>?,
    val action: Int,
    val result: String
)

data class CCMStatus(
    val IOTMStatus: Int?,
    val SCMStatus: Int?,
    val deviceName: String?
)

data class DCMStatus(
    val nodeStatus: List<NodeStatus>?,
    val Subtype: Int?
)

data class NodeStatus(
    val addr: Int?,
    val name: String?,
    val status: Int?
)
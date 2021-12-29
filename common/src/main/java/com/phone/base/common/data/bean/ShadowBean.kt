package com.phone.base.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/3/22
 */
data class ShadowBean(
    val clientToken: String,
    val state: State
)

data class State(
    val reported: Reported?,
    val desired: Desired?
)

data class Reported(
    val RSSI: String,
    val SSID: String,
    val apMac: String,
    val bleMac: String,
    val clientId: String,
    val commissioned: Boolean,
    val connected: Boolean,
    val decommission: Boolean,
    val deprovision: Boolean,
    val factoryReset: Boolean,
    val iotmVer: String,
    val iotmVerURL: String,
    val ip_addr: String,
    val mfgDateCode: String,
    val provisioned: Boolean,
    val reboot: Boolean,
    val resetRfPairList: Boolean,
    val restVer: String,
    val rfPairModeActive: Boolean,
    val schedule: String,
    val scheduleVer: Int,
    val scmVer: String,
    val scmVerURL: String,
    val shadowVer: Int,
    val staMac: String,
    val totalFixtures: Int,
    val tz: String,
    val wifiAuth: String,
    val wifiChannel: Int
)

data class Desired(
        val RSSI: String,
        val SSID: String,
        val apMac: String,
        val bleMac: String,
        val clientId: String,
        val commissioned: Boolean,
        val connected: Boolean,
        val decommission: Boolean,
        val deprovision: Boolean,
        val factoryReset: Boolean,
        val iotmVer: String,
        val iotmVerURL: String,
        val ip_addr: String,
        val mfgDateCode: String,
        val provisioned: Boolean,
        val reboot: Boolean,
        val resetRfPairList: Boolean,
        val restVer: String,
        val rfPairModeActive: Boolean,
        val schedule: String,
        val scheduleVer: Int,
        val scmVer: String,
        val scmVerURL: String,
        val shadowVer: Int,
        val staMac: String,
        val totalFixtures: Int,
        val tz: String,
        val wifiAuth: String,
        val wifiChannel: Int
)
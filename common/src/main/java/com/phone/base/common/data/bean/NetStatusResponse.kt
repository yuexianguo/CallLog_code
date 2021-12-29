package com.phone.base.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/3/9
 */

class NetStatusResponse : BaseResponse() {
    var provisioned: Boolean? = null
    var ssid: String? = null

    //0	Not connected. 1 Connected via Ethernet. 2 Connected via WiFi.
    var connected: Int? = null
    var ipAddr: String? = null
    var mac: String? = null
    var rssi: String? = null
    var auth: String? = null
    var channel: Int? = null

    //0 Disconnected from AWS. 1 Connected to AWS. 2 Attempting to reconnect to AWS.
    var awsConnected: Int? = null
    var commissioned: Boolean? = null
}
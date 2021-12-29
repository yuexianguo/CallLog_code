package com.phone.base.common

interface ConfigNetAction {
    companion object {
        const val SCAN_START = 0
        const val SCAN_STATUS = 1
        const val SCAN_RESULTS = 2
        const val NETWORK_STATUS = 3
        const val RESET = 4
        const val PROVISION = 5
        const val UN_PROVISION = 6
        const val ACTIVATE = 7
        const val REGISTER = 8
        const val DECOMMISSION = 9
        const val ENABLE_AP = 10
    }
}
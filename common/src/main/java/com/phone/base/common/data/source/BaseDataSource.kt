package com.phone.base.common.data.source

import androidx.annotation.StringDef
import okhttp3.MediaType.Companion.toMediaTypeOrNull

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2020/4/23
 */

const val URI_FIXTURE = "fixture"
const val URI_GROUP = "group"
const val URI_AUTOMATION = "automation"
const val URI_SCHEDULES = "schedules"
const val URI_INPUT = "input"
const val URI_DEVICE = "device"
const val URI_DMX = "dmx"
const val URI_SENSOR = "sensor"

interface BaseDataSource {
    companion object {
        const val TAG = "BaseDataSource"
        val MEDIA_TYPE_JSON = "application/json;charset=utf-8".toMediaTypeOrNull()
        val MEDIA_TYPE_OTA = "file/*.bin".toMediaTypeOrNull()
    }

    @StringDef(OTAHeaderModule.IOTM, OTAHeaderModule.SCM, OTAHeaderModule.DCM)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @Target(AnnotationTarget.VALUE_PARAMETER)
    annotation class OTAHeaderModule {
        companion object {
            const val IOTM = "IOTM"
            const val SCM = "SCM"
            const val DCM = "DCM"
        }
    }
}
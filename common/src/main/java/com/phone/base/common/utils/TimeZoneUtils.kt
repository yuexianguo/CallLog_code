package com.phone.base.common.utils

import java.util.*

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/2/26
 */
object TimeZoneUtils {
    fun getTimeZone(): () -> String = {
        var theTimeStamp: String = ""
        val cal = Calendar.getInstance()
        val tz = cal.timeZone

        val displayName = Calendar.getInstance().timeZone.getDisplayName(false, TimeZone.LONG)
        LogUtil.d("getTimeZone: displayName =$displayName")

        if (displayName.contains("Eastern")) {
            theTimeStamp = "EST5EDT"
        } else if (displayName.contains("Central")) {
            theTimeStamp = "CST6CDT"
        } else if (displayName.contains("Mountain")) {
            theTimeStamp = "MST7MDT"
        } else if (displayName.contains("Arizona")) {
            theTimeStamp = "MST7"
        } else if (displayName.contains("Pacific")) {
            theTimeStamp = "PST8PDT"
        } else if (displayName.contains("Alaska")) {
            theTimeStamp = "AKS9AKD"
        } else if (displayName.contains("Hawaii")) {
            theTimeStamp = "HST10"
        }
        if (theTimeStamp.isEmpty()) {
            val now = Date()
            val offsetFromUtc = -1 * tz.getOffset(now.time) / 1000
            val minutes = offsetFromUtc / 60 % 60
            val hours = offsetFromUtc / 3600
            if (offsetFromUtc > 0) {
                theTimeStamp = "UTC+" + String.format("%02d", hours) + ":" + String.format("%02d", minutes)
            } else {
                theTimeStamp = "UTC" + String.format("%02d", hours) + ":" + String.format("%02d", minutes)
            }
        }
        LogUtil.d("getTimeZone =$theTimeStamp")
        theTimeStamp;
    }
}
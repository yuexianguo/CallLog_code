package com.phone.base.utils

import java.text.SimpleDateFormat
import java.util.*

object DialTimeUtils {

    fun getCurrentTimeByFormat(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return simpleDateFormat.format(Date())
    }

}
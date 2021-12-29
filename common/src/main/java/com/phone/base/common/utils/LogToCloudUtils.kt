package com.phone.base.common.utils

import android.os.Build
import android.os.Environment
import com.phone.base.common.BaseApplication
import java.io.File

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/5/6
 */
object LogToCloudUtils {
    const val logFileName = "STRUTDebugLog.txt"
    const val logBucket = "windermier-log-to-cloud"
    const val logKeyFormat = "STRUT/%s/%s.log"

    @Synchronized
    fun saveLogToFile(logMsg: String) {
        try {
            var targetLog = "${TimeUtils.getCurrentTime()} # $logMsg\n"
            LogUtil.d(targetLog)
            val logPath = getLogPath()
            logPath?.also {
                val folder = File(it)
                val targetFile = File(folder, logFileName)
                if (!targetFile.exists()) {
                    FileUtils.createFile(it, logFileName)
                }
                FileUtils.writeStringToFile(targetFile, targetLog)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun getLogPath(): String? {
        var logPath: String? = null
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            logPath = Environment.getExternalStorageDirectory().absolutePath + "/STRUTLog"
        } else {
            val externalFilesDir = BaseApplication.context.getExternalFilesDir(null)
            externalFilesDir?.also {
                logPath = it.path
            }
        }
        return logPath
    }

    fun deleteFile() {
        val file = File(getLogPath(), logFileName)
        if (file.exists() && file.isFile) {
            file.delete()
        }
    }
}
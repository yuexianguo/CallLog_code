package com.phone.base.common.utils

import com.phone.base.common.BaseApplication
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.RandomAccessFile
import java.lang.Exception
import java.nio.ByteBuffer

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/1/9
 */
const val URL_TYPE_SCM = "SCM"
const val URL_TYPE_IOTM = "IOTM"
const val URL_TYPE_DCM = "DCM"
const val URL_TYPE_SENSOR = "SENSOR"
const val URL_TYPE_DMX = "DMX"
const val URL_TYPE_MOTOR = "MOTOR"

object FileUtils {

    @Synchronized
    fun writeFwFile(fwVersion: String, url: String, inputStream: InputStream) {
        if (url.isNotEmpty() && url.split("/").size > 1) {
            var fileOutputStream: FileOutputStream? = null
            try {
                val split = url.split("/")
                val fwFileName = split[split.size - 1]
                val filesDir: File = BaseApplication.context.filesDir
                val folder = File(filesDir, "/Firmware")
                folder.mkdir()

                val tempFile = File(folder, fwFileName)

                if (url.contains(URL_TYPE_SCM)) {
                    val preSCMFile = File(PrefUtils.KEY_SCM_FW_FILE_PATH)
                    if (preSCMFile.exists()) {
                        LogUtil.d("Pref SCM file extist,delete first")
                        preSCMFile.delete()
                        PrefUtils.writeString(PrefUtils.KEY_SCM_FW_FILE_PATH, "")
                        PrefUtils.writeString(PrefUtils.KEY_SCM_FW_LOCAL_VER, "")
                    }
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                    deleteRedundantFile(URL_TYPE_SCM, folder)

                } else if (url.contains(URL_TYPE_IOTM)) {
                    val preIOTMFile = File(PrefUtils.KEY_IOTM_FW_FILE_PATH)
                    if (preIOTMFile.exists()) {
                        LogUtil.d("Pref IOTM file extist,delete first")
                        preIOTMFile.delete()
                        PrefUtils.writeString(PrefUtils.KEY_IOTM_FW_FILE_PATH, "")
                        PrefUtils.writeString(PrefUtils.KEY_IOTM_FW_LOCAL_VER, "")
                    }
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                    deleteRedundantFile(URL_TYPE_IOTM, folder)

                } else if (url.contains(URL_TYPE_DCM)) {
                    val preDCMFile = File(PrefUtils.KEY_DCM_FW_FILE_PATH)
                    if (preDCMFile.exists()) {
                        LogUtil.d("Pref DCM file extist,delete first")
                        preDCMFile.delete()
                        PrefUtils.writeString(PrefUtils.KEY_DCM_FW_FILE_PATH, "")
                        PrefUtils.writeString(PrefUtils.KEY_DCM_FW_LOCAL_VER, "")
                    }
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                    deleteRedundantFile(URL_TYPE_DCM, folder)
                } else if (url.contains(URL_TYPE_SENSOR)) {
                    val preDCMFile = File(PrefUtils.KEY_SENSOR_FW_FILE_PATH)
                    if (preDCMFile.exists()) {
                        LogUtil.d("Pref SENSOR file extist,delete first")
                        preDCMFile.delete()
                        PrefUtils.writeString(PrefUtils.KEY_SENSOR_FW_FILE_PATH, "")
                        PrefUtils.writeString(PrefUtils.KEY_SENSOR_FW_LOCAL_VER, "")
                    }
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                    deleteRedundantFile(URL_TYPE_SENSOR, folder)
                } else if (url.contains(URL_TYPE_DMX)) {
                    val preDMXFile = File(PrefUtils.KEY_DMX_FW_FILE_PATH)
                    if (preDMXFile.exists()) {
                        LogUtil.d("Pref DMX file extist,delete first")
                        preDMXFile.delete()
                        PrefUtils.writeString(PrefUtils.KEY_DMX_FW_FILE_PATH, "")
                        PrefUtils.writeString(PrefUtils.KEY_DMX_FW_LOCAL_VER, "")
                    }
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                    deleteRedundantFile(URL_TYPE_DMX, folder)
                } else if (url.contains(URL_TYPE_MOTOR)) {
                    val preDMXFile = File(PrefUtils.KEY_MOTOR_FW_FILE_PATH)
                    if (preDMXFile.exists()) {
                        LogUtil.d("Pref DMX file extist,delete first")
                        preDMXFile.delete()
                        PrefUtils.writeString(PrefUtils.KEY_MOTOR_FW_FILE_PATH, "")
                        PrefUtils.writeString(PrefUtils.KEY_MOTOR_FW_LOCAL_VER, "")
                    }
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                    deleteRedundantFile(URL_TYPE_MOTOR, folder)
                }

                tempFile.createNewFile()
                fileOutputStream = FileOutputStream(tempFile)
                val data = ByteArray(4096)
                var count: Int
                while (inputStream.read(data).also({ count = it }) != -1) {
                    // allow canceling with back button
                    fileOutputStream.write(data, 0, count)
                }
                val finishFile = File(folder, "finished_$fwFileName")
                if (tempFile.renameTo(finishFile)) {
                    if (url.contains(URL_TYPE_SCM)) {
                        PrefUtils.writeString(PrefUtils.KEY_SCM_FW_FILE_PATH, finishFile.absolutePath)
                        PrefUtils.writeString(PrefUtils.KEY_SCM_FW_LOCAL_VER, fwVersion)
                        LogUtil.d("writeFwFile: complete SCM Fw")
                    } else if (url.contains(URL_TYPE_IOTM)) {
                        PrefUtils.writeString(PrefUtils.KEY_IOTM_FW_FILE_PATH, finishFile.absolutePath)
                        PrefUtils.writeString(PrefUtils.KEY_IOTM_FW_LOCAL_VER, fwVersion)
                        LogUtil.d("writeFwFile: complete IOTM Fw")
                    } else if (url.contains(URL_TYPE_DCM)) {
                        PrefUtils.writeString(PrefUtils.KEY_DCM_FW_FILE_PATH, finishFile.absolutePath)
                        PrefUtils.writeString(PrefUtils.KEY_DCM_FW_LOCAL_VER, fwVersion)
                        LogUtil.d("writeFwFile: complete DCM Fw")
                    } else if (url.contains(URL_TYPE_SENSOR)) {
                        PrefUtils.writeString(PrefUtils.KEY_SENSOR_FW_FILE_PATH, finishFile.absolutePath)
                        PrefUtils.writeString(PrefUtils.KEY_SENSOR_FW_LOCAL_VER, fwVersion)
                        LogUtil.d("writeFwFile: complete SENSOR Fw")
                    } else if (url.contains(URL_TYPE_DMX)) {
                        PrefUtils.writeString(PrefUtils.KEY_DMX_FW_FILE_PATH, finishFile.absolutePath)
                        PrefUtils.writeString(PrefUtils.KEY_DMX_FW_LOCAL_VER, fwVersion)
                        LogUtil.d("writeFwFile: complete DMX Fw")
                    } else if (url.contains(URL_TYPE_MOTOR)) {
                        PrefUtils.writeString(PrefUtils.KEY_MOTOR_FW_FILE_PATH, finishFile.absolutePath)
                        PrefUtils.writeString(PrefUtils.KEY_MOTOR_FW_LOCAL_VER, fwVersion)
                        LogUtil.d("writeFwFile: complete MOTOR Fw")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fileOutputStream?.close()
                inputStream.close()
            }
        }
    }

    private fun deleteRedundantFile(fileType: String, folder: File) {
        if (folder.isDirectory && folder.exists()) {
            val listFiles = folder.listFiles()
            if (listFiles != null && listFiles.isNotEmpty()) {
                for (file in listFiles) {
                    if (file.name.contains(fileType)) {
                        file.delete()
                    }
                }
            }
        }
    }

    @Synchronized
    fun writeStringToFile(targetFile: File, msg: String) {
        if (targetFile.exists()) {
            try {
                val length = targetFile.length()
                //delete file when beyond 10 M
                if (length / (1024 * 1024) > 10) {
                    targetFile.delete()
                    return
                }
                val rf = RandomAccessFile(targetFile, "rw")
                val channel = rf.channel
                val position = rf.length()
                rf.seek(position)
                channel.write(ByteBuffer.wrap(msg.toByteArray()))
                channel.close()
                rf.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createFile(path: String, name: String): File? {
        val dir = createDir(path)
        if (dir != null) {
            val file = File(dir, name)
            if (!file.exists()) {
                file.createNewFile()
                return file
            } else {
                return file
            }
        }
        return null
    }

    fun createDir(path: String): File? {
        val dir = File(path)
        if (!dir.exists()) {
            val mkdirs = dir.mkdirs()
            if (mkdirs) {
                return dir
            }
        } else {
            return dir
        }
        return null
    }
}
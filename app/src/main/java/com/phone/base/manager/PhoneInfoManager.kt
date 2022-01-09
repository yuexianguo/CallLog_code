package com.phone.base.manager

import android.content.Context
import com.google.gson.Gson
import com.phone.base.bean.PhoneBookInfo
import com.phone.base.utils.FileSystem
import com.phone.base.utils.PhoneFileUtils

import java.io.File

class PhoneInfoManager {
    private constructor()
    private lateinit var context: Context
    lateinit var phoneInfo: PhoneBookInfo
    companion object {
        val instance: PhoneInfoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PhoneInfoManager()
        }
    }
    fun init(context: Context) {
        this.context = context.applicationContext
        initPhoneInfo()
    }

    private fun initPhoneInfo() {
        PhoneFileUtils.initPublicFileToAppFile(context,File(context.filesDir, PhoneFileUtils.FILE_NAME).absolutePath)
        val configObj = FileSystem.readString(File(context.filesDir, PhoneFileUtils.FILE_NAME))
        if (configObj == null || configObj.isEmpty()) {
            phoneInfo = PhoneBookInfo.createNewInstance()
        } else {
            phoneInfo = Gson().fromJson(configObj, PhoneBookInfo::class.java)
        }
    }

    fun updatePhoneInfo(){
        PhoneFileUtils.initPublicFileToAppFile(context,File(context.filesDir, PhoneFileUtils.FILE_NAME).absolutePath)
        val configObj = FileSystem.readString(File(context.filesDir, PhoneFileUtils.FILE_NAME))
        if (configObj == null || configObj.isEmpty()) {
            phoneInfo = PhoneBookInfo.createNewInstance()
            phoneInfo.saveOrUpdate(context)
        } else {
            phoneInfo = Gson().fromJson(configObj, PhoneBookInfo::class.java)
            phoneInfo.saveOrUpdate(context)
        }
    }

}
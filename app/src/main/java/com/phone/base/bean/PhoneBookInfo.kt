package com.phone.base.bean

import android.content.Context
import android.os.Build
import android.os.Environment
import com.google.gson.Gson
import com.phone.base.utils.FileSystem
import com.phone.base.utils.PhoneFileUtils.FILE_NAME

import java.io.File
import java.io.Serializable

const val TYPE_MAN = "男士"
const val TYPE_WOMAN = "女士"

class PhoneBookInfo : Serializable {
    var phoneList: ArrayList<PhoneBookItem> = arrayListOf()
    var phoneDepartItemList: ArrayList<PhoneDepartItem> = arrayListOf()

    companion object {
        fun createNewInstance(): PhoneBookInfo {
            return PhoneBookInfo()
        }
    }

    fun saveOrUpdate(context: Context) {
        FileSystem.writeString(context.filesDir, FILE_NAME, Gson().toJson(this))
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            //android 9 以下
            val storagePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            FileSystem.writeString(storagePublicDirectory, FILE_NAME, Gson().toJson(this))
//        } else {
//            PhoneFileUtils.copyPrivateToDocuments(context, File(context.filesDir, FILE_NAME).absolutePath)
//        }
    }

    fun insertPhoneItem(phoneBookItem: PhoneBookItem) {
        phoneList.add(phoneBookItem)
    }

    fun insertDeptItem(phoneDepartItem: PhoneDepartItem) {
        phoneDepartItemList.add(phoneDepartItem)
    }

    //for create PhoneBookItem only
    fun generatePhoneId(): Long {
        return if (phoneList.size == 0) 1 else (phoneList.size + 1).toLong()
    }

    //for create DepartItem only
    fun generateDeptId(): Long {
        return if (phoneDepartItemList.size == 0) 1 else (phoneDepartItemList.size + 1).toLong()
    }

    fun getPhoneBookItem(id: Long): PhoneBookItem? {
        var targetItem: PhoneBookItem? = null
        for (phoneBookItem in phoneList) {
            if (phoneBookItem.id == id) {
                targetItem = phoneBookItem
            }
        }
        return targetItem
    }

    fun getPhoneDepartItem(id: Long): PhoneDepartItem? {
        var targetItem: PhoneDepartItem? = null
        for (phoneDepartItem in phoneDepartItemList) {
            if (phoneDepartItem.id == id) {
                targetItem = phoneDepartItem
            }
        }
        return targetItem
    }

    fun deleteDept(id: Long) {
        if (phoneDepartItemList.isNotEmpty()) {
            var deleteIds = arrayListOf<Long>()
            deleteIds.add(id)
            var targetId = id
            for (phoneDepartItem in phoneDepartItemList) {
                //1层子类
                if (targetId == phoneDepartItem.pid) {
                    deleteIds.add(phoneDepartItem.id)
                    targetId = phoneDepartItem.id
                    for (phoneDepartItem in phoneDepartItemList) {
                        //2层
                        if (targetId == phoneDepartItem.pid) {
                            deleteIds.add(phoneDepartItem.id)
                            targetId = phoneDepartItem.id

                            for (phoneDepartItem in phoneDepartItemList) {
                                //3层
                                if (targetId == phoneDepartItem.pid) {
                                    deleteIds.add(phoneDepartItem.id)
                                    targetId = phoneDepartItem.id

                                    for (phoneDepartItem in phoneDepartItemList) {
                                        //4层
                                        if (targetId == phoneDepartItem.pid) {
                                            deleteIds.add(phoneDepartItem.id)
                                            targetId = phoneDepartItem.id

                                            for (phoneDepartItem in phoneDepartItemList) {
                                                //5层
                                                if (targetId == phoneDepartItem.pid) {
                                                    deleteIds.add(phoneDepartItem.id)
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }

                }
            }

            val iterator = phoneDepartItemList.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (deleteIds.contains(next.id)) {
                    iterator.remove()
                }
            }
            val iterator1 = phoneList.iterator()
            while (iterator1.hasNext()) {
                iterator1.next().department?.apply {
                    if (deleteIds.contains(id)) {
                        iterator1.remove()
                    }
                }

            }


        }
    }
}

class PhoneBookItem : Serializable {
    var id: Long = 0;
    var name: String = "";
    var department: PhoneDepartItem;
    var work: String = "";
    var extension1: String = "";
    var phone1: String = "";
    var call1: String = "";
    var extension2: String = "";
    var phone2: String = "";
    var call2: String = "";
    var home_phone: String = "";
    var fax: String = "";
    var area_code: String = "";
    var email: String = "";
    var sex: String = "";//"男士" "女士;
    var remarks: String = "";

    constructor(
        id: Long, name: String, department: PhoneDepartItem, work: String,
        extension1: String, phone1: String, call1: String, extension2: String, phone2: String, call2: String,
        home_phone: String, fax: String, area_code: String, email: String, sex: String, remarks: String
    ) {
        this.id = id
        this.name = name
        this.department = department
        this.work = work
        this.extension1 = extension1
        this.phone1 = phone1
        this.call1 = call1
        this.extension2 = extension2
        this.phone2 = phone2
        this.call2 = call2
        this.home_phone = home_phone
        this.fax = fax
        this.area_code = area_code
        this.email = email
        this.sex = sex
        this.remarks = remarks
    }
}

class PhoneDepartItem : Serializable {
    var id: Long;
    var pid: Long;
    var level: Int;
    var name: String;

    constructor(id: Long, pid: Long, level: Int, name: String) {
        this.id = id
        this.pid = pid
        this.level = level
        this.name = name
    }
}
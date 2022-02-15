package com.phone.base.bean

import android.content.Context
import android.os.Build
import android.os.Environment
import com.google.gson.Gson
import com.phone.base.utils.FileSystem
import com.phone.base.utils.PhoneFileUtils
import com.phone.base.utils.PhoneFileUtils.FILE_NAME
import com.phone.base.utils.PinyinUtils

import java.io.File
import java.io.Serializable

const val TYPE_MAN = "男士"
const val TYPE_WOMAN = "女士"
const val DIAL_TYPE_MISSED_CALL = "未接"
const val DIAL_TYPE_RECEIVED_CALL = "已接"
const val DIAL_TYPE_DIALED_CALL = "已拨"

class PhoneBookInfo : Serializable {
    var phoneList: ArrayList<PhoneBookItem> = arrayListOf()
    var phoneDepartItemList: ArrayList<PhoneDepartItem> = arrayListOf()
    var phoneHistoryItemList: ArrayList<PhoneHistoryItem> = arrayListOf()
    var letterArray = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
        "Y", "Z"
    )


    companion object {
        fun createNewInstance(): PhoneBookInfo {
            return PhoneBookInfo()
        }
    }

    fun saveOrUpdate(context: Context) {
        FileSystem.writeString(context.filesDir, FILE_NAME, Gson().toJson(this))
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            //android 9 以下
            val storagePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            FileSystem.writeString(storagePublicDirectory, FILE_NAME, Gson().toJson(this))
        } else {
            PhoneFileUtils.copyPrivateToDocuments(context, File(context.filesDir, FILE_NAME).absolutePath)
        }
    }

    fun insertPhoneItem(phoneBookItem: PhoneBookItem) {
        phoneList.add(phoneBookItem)
    }

    fun insertDeptItem(phoneDepartItem: PhoneDepartItem) {
        phoneDepartItemList.add(phoneDepartItem)
    }

    fun insertPhoneHistoryItem(phoneHistoryItem: PhoneHistoryItem) {
        phoneHistoryItemList.add(phoneHistoryItem)
    }


    //for create PhoneBookItem only
    fun generatePhoneId(): Long {
        var targetId = 0L
        if (phoneList.size <= 0) {
            targetId = 1
        } else {
            targetId = phoneList[phoneList.size - 1].id + 1
        }
        return targetId
    }

    //for create DepartItem only
    fun generateDeptId(): Long {
        var targetId = 0L
        if (phoneDepartItemList.size <= 0) {
            targetId = 1
        } else {
            targetId = phoneDepartItemList[phoneDepartItemList.size - 1].id + 1
        }
        return targetId
    }

    //for create PhoneHistoryItem only
    fun generatePhoneHistoryItemId(): Long {
        var targetId = 0L
        if (phoneHistoryItemList.size <= 0) {
            targetId = 1
        } else {
            targetId = phoneHistoryItemList[phoneHistoryItemList.size - 1].id + 1
        }
        return targetId
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
                    val targetId2 = phoneDepartItem.id
                    for (phoneDepartItem in phoneDepartItemList) {
                        //2层
                        if (targetId2 == phoneDepartItem.pid) {
                            deleteIds.add(phoneDepartItem.id)
                            val targetId3 = phoneDepartItem.id

                            for (phoneDepartItem in phoneDepartItemList) {
                                //3层
                                if (targetId3 == phoneDepartItem.pid) {
                                    deleteIds.add(phoneDepartItem.id)
                                    val targetId4 = phoneDepartItem.id

                                    for (phoneDepartItem in phoneDepartItemList) {
                                        //4层
                                        if (targetId4 == phoneDepartItem.pid) {
                                            deleteIds.add(phoneDepartItem.id)
                                            val targetId5 = phoneDepartItem.id

                                            for (phoneDepartItem in phoneDepartItemList) {
                                                //5层
                                                if (targetId5 == phoneDepartItem.pid) {
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

            val iterator1 = phoneList.iterator()
            while (iterator1.hasNext()) {
                val next = iterator1.next()
                next.department?.apply {
                    val deptId = this.id
                    if (deptId != 0L) {
                        if (deleteIds.contains(deptId)) {
                            iterator1.remove()
                        }
                    }
                }
            }

            val iterator = phoneDepartItemList.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                val deptId = next.id
                if (deptId != 0L) {
                    if (deleteIds.contains(deptId)) {
                        iterator.remove()
                    }
                }
            }
        }
    }

    fun deletePhoneItem(id: Long): Boolean {
        val iterator = phoneList.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            next.apply {
                if (id == this.id) {
                    iterator.remove()
                    return true
                }
            }
        }
        return false
    }

    //found
    fun foundPhoneBySimpleName(seekSimpleName: String): ArrayList<PhoneBookItem> {
        var targetList: ArrayList<PhoneBookItem> = arrayListOf()
        if (phoneList.isNotEmpty()) {
            for (phoneBookItem in phoneList) {
                if (phoneBookItem.name.isNotEmpty()) {
                    val pingYin = PinyinUtils.getPingYin(phoneBookItem.name)
                    if (pingYin.contains(seekSimpleName, ignoreCase = true)) {
                        targetList.add(phoneBookItem)
                    }
                }
            }
        }
        return targetList
    }

    fun foundPhoneByNumber(number: String): ArrayList<PhoneBookItem> {
        var targetList: ArrayList<PhoneBookItem> = arrayListOf()
        if (phoneList.isNotEmpty()) {
            for (phoneBookItem in phoneList) {
                if (phoneBookItem.extension1.isNotEmpty()) {
                    if (phoneBookItem.extension1.contains(number)) {
                        targetList.add(phoneBookItem)
                    }
                } else if (phoneBookItem.extension2.isNotEmpty()) {
                    if (phoneBookItem.extension2.contains(number)) {
                        targetList.add(phoneBookItem)
                    }
                }
            }
        }
        return targetList
    }

    fun foundUserByCertainNumber(number: String): PhoneBookItem? {
        var targetPhoneBookItem: PhoneBookItem? = null
        if (number.isNotEmpty()) {
            if (phoneList.isNotEmpty()) {
                for (phoneBookItem in phoneList) {
                    if (phoneBookItem.extension1.isNotEmpty()) {
                        if (phoneBookItem.extension1 == number) {
                            return phoneBookItem
                        }
                    } else if (phoneBookItem.extension2.isNotEmpty()) {
                        if (phoneBookItem.extension2 == number) {
                            return phoneBookItem
                        }
                    } else if (phoneBookItem.phone1.isNotEmpty()) {
                        if (phoneBookItem.phone1 == number) {
                            return phoneBookItem
                        }
                    } else if (phoneBookItem.phone2.isNotEmpty()) {
                        if (phoneBookItem.phone2 == number) {
                            return phoneBookItem
                        }
                    }
                }
            }
        }

        return targetPhoneBookItem
    }

    fun foundPhoneBySimpleNameOrNum(foundString: String): ArrayList<PhoneBookItem> {
        var targetList: ArrayList<PhoneBookItem> = arrayListOf()
        if (phoneList.isNotEmpty()) {
            for (phoneBookItem in phoneList) {
                if (phoneBookItem.extension1.isNotEmpty()) {
                    if (phoneBookItem.extension1.contains(foundString)) {
                        targetList.add(phoneBookItem)
                        continue
                    }
                } else if (phoneBookItem.extension2.isNotEmpty()) {
                    if (phoneBookItem.extension2.contains(foundString)) {
                        targetList.add(phoneBookItem)
                        continue
                    }
                }
                if (phoneBookItem.name.isNotEmpty()) {
                    val pingYin = PinyinUtils.getPingYin(phoneBookItem.name)
                    val pingYinFound = PinyinUtils.getPingYin(foundString)
                    if (pingYin.contains(pingYinFound, ignoreCase = true)) {
                        targetList.add(phoneBookItem)
                    }
                }
            }
        }
        return targetList
    }

    fun foundPhoneByTwoString(desc1: String, desc2: String): ArrayList<PhoneBookItem> {
        //desc1 和 desc2同时满足
        var targetList: ArrayList<PhoneBookItem> = arrayListOf()
        val foundDesc1List = foundPhoneBySimpleNameOrNum(desc1)
        if (foundDesc1List.isEmpty()) {
            val foundDesc2List = foundPhoneBySimpleNameOrNum(desc2)
            targetList.clear()
            targetList.addAll(foundDesc2List)
        } else {
            for (phoneBookItem in foundDesc1List) {
                if (phoneBookItem.extension1.isNotEmpty()) {
                    if (phoneBookItem.extension1.contains(desc2)) {
                        targetList.add(phoneBookItem)
                        continue
                    }
                } else if (phoneBookItem.extension2.isNotEmpty()) {
                    if (phoneBookItem.extension2.contains(desc2)) {
                        targetList.add(phoneBookItem)
                        continue
                    }
                }
                if (phoneBookItem.name.isNotEmpty()) {
                    val pingYin = PinyinUtils.getPingYin(phoneBookItem.name)
                    val pingYinFound = PinyinUtils.getPingYin(desc2)
                    if (pingYin.contains(pingYinFound, ignoreCase = true)) {
                        targetList.add(phoneBookItem)
                    }
                }
            }
        }
        return targetList
    }

    fun getAllLetterNameList(): ArrayList<HunLetterBean> {
        var targetLetterNameList: ArrayList<HunLetterBean> = arrayListOf()
        for (letter in letterArray) {
            if (phoneList.isNotEmpty()) {
                var lastNameList: ArrayList<String> = arrayListOf()
                for (phoneBookItem in phoneList) {
                    val pingYin = PinyinUtils.getPingYin(phoneBookItem.name)
                    if (pingYin.isNotEmpty()) {
                        if (pingYin.substring(0, 1).equals(letter, ignoreCase = true)) {
                            if (!lastNameList.contains(phoneBookItem.name.substring(0, 1))) {
                                lastNameList.add(phoneBookItem.name.substring(0, 1))
                            }
                        }
                    }
                }
                if (lastNameList.isNotEmpty()) {
                    targetLetterNameList.add(HunLetterBean(letter, lastNameList))
                }
            }
        }
        return targetLetterNameList
    }

    fun getPhoneListByLastName(lastName:String): ArrayList<PhoneBookItem> {
        var targetList: ArrayList<PhoneBookItem> = arrayListOf()
        if (phoneList.isNotEmpty()) {
            for (phoneBookItem in phoneList) {
                if (phoneBookItem.name.isNotEmpty()) {
                    val name = phoneBookItem.name
                    if (name.isNotEmpty() && name.substring(0,1).equals(lastName, ignoreCase = true)) {
                        targetList.add(phoneBookItem)
                    }
                }
            }
        }
        return targetList
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

class PhoneHistoryItem : Serializable {
    var id: Long;
    var phoneBookItemid: Long;
    var dialType: String; //"未接"， "已接"， "已拨"
    var name: String;
    var phone: String;
    var startTime: String;
    var dialogTimeLength: String;

    constructor(id: Long, phoneBookItemid: Long, dialType: String, name: String, phone: String, startTime: String, dialogTimeLength: String) {
        this.id = id
        this.phoneBookItemid = phoneBookItemid
        this.dialType = dialType
        this.name = name
        this.phone = phone
        this.startTime = startTime
        this.dialogTimeLength = dialogTimeLength
    }
}
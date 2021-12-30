package com.phone.base.common.utils

import androidx.annotation.MainThread
import com.phone.base.common.data.bean.ScheduleCreateRequest
import com.phone.base.common.data.bean.ScheduleModifyRequest
import com.phone.base.common.data.bean.ScheduleReadResponse
import com.phone.base.common.realmObject.ScheduleObject
import io.realm.Realm
import io.realm.RealmList

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2021/1/6
 */
object ScheduleDbUtils {

    fun saveNewSchedule(scheduleAddress: Int, createRequest: ScheduleCreateRequest, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val scheduleObject = ScheduleObject(scheduleAddress, createRequest.name, createRequest.time,
                    createRequest.automation, createRequest.type, createRequest.enabled, createRequest.triggerAction)
            scheduleObject.setTimestamp(System.currentTimeMillis())
            realm.insert(scheduleObject)
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun saveScheduleAsync(scheduleAddress: Int, createRequest: ScheduleCreateRequest, onSuccess: Realm.Transaction.OnSuccess?,
                          onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                val scheduleObject = ScheduleObject(scheduleAddress, createRequest.name, createRequest.time,
                        createRequest.automation, createRequest.type, createRequest.enabled, createRequest.triggerAction)
                it.insert(scheduleObject)
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveReadSchedules(readResponse: ScheduleReadResponse, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            val schedules = readResponse.schedules
            if (schedules.isNullOrEmpty()) {
                return
            }
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val list = RealmList<ScheduleObject>()
            schedules.forEach {
                val scheduleObject = ScheduleObject(it.addr, it.name, it.time, it.automation, it.type, it.enabled, it.triggerAction)
                scheduleObject.setTimestamp(System.currentTimeMillis())
                list.add(scheduleObject)
            }
            realm.insertOrUpdate(list)
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    fun deleteSchedule(scheduleAddress: Int, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            val scheduleObject = realm.where(ScheduleObject::class.java).equalTo("addr", scheduleAddress).findFirst()
            scheduleObject?.deleteFromRealm()
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun deleteScheduleAsync(scheduleAddress: Int, onSuccess: Realm.Transaction.OnSuccess?,
                            onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                val scheduleObject = it.where(ScheduleObject::class.java).equalTo("addr", scheduleAddress).findFirst()
                scheduleObject?.deleteFromRealm()
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteAllSchedules(isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            realm.delete(ScheduleObject::class.java)
            realm.commitTransaction()
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun deleteAllSchedulesAsync(onSuccess: Realm.Transaction.OnSuccess?,
                                onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                it.delete(ScheduleObject::class.java)
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun modifySchedule(modifyRequest: ScheduleModifyRequest, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            val scheduleObject = realm.where(ScheduleObject::class.java).equalTo("addr", modifyRequest.addr).findFirst()
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            if (scheduleObject != null) {
                modifyRequest.name?.let { name ->
                    scheduleObject.name = name
                }
                modifyRequest.enabled?.let { isEnable ->
                    scheduleObject.isEnabled = isEnable
                }
                modifyRequest.type?.let { type ->
                    scheduleObject.type = type
                }
                modifyRequest.time?.let { time ->
                    scheduleObject.time = time
                }
                modifyRequest.automation?.let { auto ->
                    scheduleObject.automation = auto
                }
                modifyRequest.triggerAction?.let { triggerAction ->
                    scheduleObject.triggerAction = triggerAction
                }
                scheduleObject.setTimestamp(System.currentTimeMillis())
                realm.commitTransaction()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (realm.isInTransaction) {
                realm.cancelTransaction()
            }
        } finally {
            if (isNeedClose && !realm.isClosed) {
                realm.close()
            }
        }
    }

    @MainThread
    fun modifyScheduleAsync(scheduleAddress: Int, modifyRequest: ScheduleModifyRequest, onSuccess: Realm.Transaction.OnSuccess?,
                            onError: Realm.Transaction.OnError?) {
        try {
            val transaction = Realm.Transaction {
                val scheduleObject = it.where(ScheduleObject::class.java).equalTo("addr", scheduleAddress).findFirst()
                if (scheduleObject != null) {
                    modifyRequest.name?.let { name ->
                        scheduleObject.name = name
                    }
                    modifyRequest.enabled?.let { isEnable ->
                        scheduleObject.isEnabled = isEnable
                    }
                    modifyRequest.type?.let { type ->
                        scheduleObject.type = type
                    }
                    modifyRequest.time?.let { time ->
                        scheduleObject.time = time
                    }
                    modifyRequest.automation?.let { auto ->
                        scheduleObject.automation = auto
                    }
                }
            }
            Realm.getDefaultInstance().executeTransactionAsync(transaction, onSuccess, onError)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getAllSchedules(): List<ScheduleObject> {
        return Realm.getDefaultInstance().where(ScheduleObject::class.java).findAll()
    }

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getSchedule(scheduleAddress: Int): ScheduleObject? {
        return Realm.getDefaultInstance().where(ScheduleObject::class.java).equalTo("addr", scheduleAddress).findFirst()
    }
}
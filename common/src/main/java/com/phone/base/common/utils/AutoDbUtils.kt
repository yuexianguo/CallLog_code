package com.phone.base.common.utils

import androidx.annotation.MainThread
import com.phone.base.common.realmObject.*
import io.realm.Realm
import java.util.*
import kotlin.collections.ArrayList

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/1/21
 */
object AutoDbUtils {

    fun modifyAutoFavorite(autoAddress: Int, isFavorite: Boolean, isNeedClose: Boolean) {
        val realm = Realm.getDefaultInstance()
        try {
            val automationObject = realm.where(AutomationObject::class.java).equalTo("addr", autoAddress).findFirst()
            if (automationObject != null) {
                if (!realm.isInTransaction) {
                    realm.beginTransaction()
                }
                automationObject.isFavorite = isFavorite
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

    fun getSensorName(sensorId: Int): String {
        val realm = Realm.getDefaultInstance()
        var name = ""
        try {
            val sensorObject = realm.where(SensorObject::class.java).equalTo("addr", sensorId).findFirst()
            sensorObject?.also {
                it.name?.apply {
                    name = this
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return name
    }

    fun getGroupName(groupId: Int): String {
        val realm = Realm.getDefaultInstance()
        var name = ""
        try {
            val groupObject = realm.where(GroupObject::class.java).equalTo("addr", groupId).findFirst()
            groupObject?.also {
                it.name?.apply {
                    name = this
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return name
    }

    @MainThread
    fun getAutomation(autoAddress: Int): AutomationObject? {
        return Realm.getDefaultInstance().where(AutomationObject::class.java).equalTo("addr", autoAddress).findFirst()
    }

    @MainThread
    fun getAutos(type: Int?): List<AutomationObject> {
        return if (type == null) {
            Realm.getDefaultInstance().where(AutomationObject::class.java).findAll()
        } else {
            Realm.getDefaultInstance().where(AutomationObject::class.java).equalTo("type", type).findAll()
        }
    }

    fun getCheckFixtureStateListForStandard(autoAddr: Int, checkFixturesList: ArrayList<Int>, automationObject: AutomationObject?): ArrayList<AutomationShadow> {
        var checkedFixtureStateList: ArrayList<AutomationShadow> = arrayListOf()
        val realm = Realm.getDefaultInstance()
        for (addr in checkFixturesList) {
            LogUtil.d("check fixture addr =" + addr)
            realm?.where(FixtureObject::class.java)?.equalTo("addr", addr)?.findFirst()?.apply {
                realm?.copyFromRealm(this)?.apply {
//                    val singleLightState: SingleLightState
                    val automationShadow: AutomationShadow = AutomationShadow()
                    automationShadow.autoAddr = autoAddr
                    automationShadow.fixtureAddr = this.addr
                    val fixtureautoShadow = getFixtureAutoShadow(this, automationObject)
                    val tempShadow = OccuAutoShadowMapUtils.getConfigState(addr)

                    if (tempShadow != null) {
                        automationShadow.isStatus = tempShadow.isStatus
                        automationShadow.zoom = tempShadow.zoom
                        automationShadow.tilt = tempShadow.tilt
                        automationShadow.pan = tempShadow.pan
                        automationShadow.preset = tempShadow.preset
                        if (tempShadow.isStatus) {
                            automationShadow.level = tempShadow.level
                        } else {
                            automationShadow.level = 0
                        }
                    } else if (fixtureautoShadow != null) {
                        automationShadow.isStatus = fixtureautoShadow.isStatus
                        automationShadow.level = fixtureautoShadow.level
                        automationShadow.zoom = fixtureautoShadow.zoom
                        automationShadow.tilt = fixtureautoShadow.tilt
                        automationShadow.pan = fixtureautoShadow.pan
                        automationShadow.preset = fixtureautoShadow.preset
                    } else {
                        automationShadow.isStatus = this.shadow.status
                        automationShadow.level = this.shadow.level
                        automationShadow.zoom = this.shadow.zoom
                        automationShadow.tilt = this.shadow.tilt
                        automationShadow.pan = this.shadow.pan
                    }

                    checkedFixtureStateList.add(automationShadow)
                }
            }
        }
        return checkedFixtureStateList
    }

    private fun getFixtureAutoShadow(aFixtureObject: FixtureObject, automationObject: AutomationObject?): AutomationShadow? {
        var newAutoShadow: AutomationShadow? = null
        aFixtureObject.let { fixtureObjcet ->
            automationObject?.let { autoObjcet ->
                val fixtureAutoShadowList = autoObjcet.fixture
                if (fixtureAutoShadowList.isNotEmpty()) {
                    for (autoShadow in fixtureAutoShadowList) {
                        if (autoShadow != null && autoShadow.unionKey == String.format(Locale.getDefault(), "%d_%d", autoObjcet.addr, fixtureObjcet.addr)) {
                            newAutoShadow = autoShadow
                        }
                    }
                }
            }
        }
        return newAutoShadow

    }

    fun getCheckFixtureStateListForAction(autoAddr: Int, checkFixturesList: ArrayList<Int>): List<AutomationShadow> {
        var checkedFixtureStateList: ArrayList<AutomationShadow> = arrayListOf()
        val realm = Realm.getDefaultInstance()
        for (addr in checkFixturesList) {
            LogUtil.d("check fixture addr =" + addr)
            realm?.where(FixtureObject::class.java)?.equalTo("addr", addr)?.findFirst()?.apply {
                realm?.copyFromRealm(this)?.apply {
//                    val singleLightState: SingleLightState
                    val automationShadow: AutomationShadow = AutomationShadow()
                    automationShadow.autoAddr = autoAddr
                    automationShadow.fixtureAddr = this.addr
                    val tempShadow = OccuAutoShadowMapUtils.getConfigState(addr)
                    if (tempShadow != null) {
                        automationShadow.isStatus = tempShadow.isStatus
                        automationShadow.zoom = tempShadow.zoom
                        automationShadow.tilt = tempShadow.tilt
                        automationShadow.pan = tempShadow.pan
                        automationShadow.preset = tempShadow.preset
                        if (tempShadow.isStatus) {
                            automationShadow.level = tempShadow.level
                        } else {
                            automationShadow.level = 0
                        }
                    } else {
                        automationShadow.isStatus = this.shadow.status
                        automationShadow.level = this.shadow.level
                        automationShadow.zoom = this.shadow.zoom
                        automationShadow.tilt = this.shadow.tilt
                        automationShadow.pan = this.shadow.pan
                    }
                    checkedFixtureStateList.add(automationShadow)
                }
            }
        }
        return checkedFixtureStateList
    }
}
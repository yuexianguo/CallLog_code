package com.phone.base.common.utils

import androidx.annotation.MainThread
import com.phone.base.common.CustomTypes
import com.phone.base.common.data.bean.DmxModuleInfoResponse
import com.phone.base.common.realmObject.DmxModuleObject
import com.phone.base.common.realmObject.DmxObject
import io.realm.Realm
import java.lang.Exception

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/3/31
 */
object DMXUtils {
    const val DMX_TYPE_FIXTURE = 1
    const val DMX_TYPE_GROUP = 2
    const val DMX_TYPE_AUTOMATION = 3
    const val NAME_TYPE_STATIC_CCT = "Static CCT Light Element"
    const val NAME_TYPE_MOTORIZED_ACCENT = "Motorized Accent"

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getDMXObject(dmxAddress: Int): DmxObject? {
        return Realm.getDefaultInstance().where(DmxObject::class.java).equalTo("addr", dmxAddress).findFirst()
    }


    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getDMXThingId(dmxAddress: Int): Int? {
        val dmxObject = Realm.getDefaultInstance().where(DmxObject::class.java).equalTo("addr", dmxAddress).findFirst()
        var thingId: Int? = dmxObject?.thing
        return thingId
    }

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getDMXLevelChannel(dmxAddress: Int): Int {
        var targetChannel = 0
        val dmxObjectInit = Realm.getDefaultInstance().where(DmxObject::class.java).equalTo("addr", dmxAddress).findFirst()
        dmxObjectInit?.also {
            val dmxObject = Realm.getDefaultInstance().copyFromRealm(it)
            dmxObject?.let {
                val mapping = it.mapping
                for (dmxChannelObject in mapping) {
//                    if (dmxChannelObject.field.equals("level")) {
                        targetChannel = dmxChannelObject.channel
//                    }
                }
                mapping
            }
        }
        return targetChannel
    }

    fun isCurrentChannelOccupied(currentChannel: Int): Boolean {
        var isOccupied = false
        val allDmxObjects = Realm.getDefaultInstance().where(DmxObject::class.java).findAll()
        allDmxObjects?.also {
            if (it.size > 0) {
                for (dmxObject in it) {
                    dmxObject?.let {
                        val mapping = it.mapping
                        for (dmxChannelObject in mapping) {
//                            if (dmxChannelObject.field.equals("level")) {
                                if (currentChannel == dmxChannelObject.channel) {
                                    return true
                                }
//                            }
                        }
                        mapping
                    }
                }
            }
        }
        return isOccupied
    }

    fun saveDMXModule(dmxGetInfoResponse: DmxModuleInfoResponse) {
        DbUtils.deleteAllDmx()
        val realm = Realm.getDefaultInstance()
        var dmxModuleObject = realm.where(DmxModuleObject::class.java).equalTo("addr", dmxGetInfoResponse.addr).findFirst()
        if (dmxModuleObject == null) {
            dmxModuleObject = DmxModuleObject(dmxGetInfoResponse.addr)
        }
        if (!realm.isInTransaction) {
            realm.beginTransaction()
        }
        dmxModuleObject.fwVer = dmxGetInfoResponse.detail.fwVer
        dmxModuleObject.isOnline = dmxGetInfoResponse.online
        dmxModuleObject.isEnabled = dmxGetInfoResponse.enabled
        realm.copyToRealmOrUpdate(dmxModuleObject)
        if (realm.isInTransaction) {
            realm.commitTransaction()
        }
    }

    fun getDMXModuleVer(): String {
        var targetVer = ""
        try {
            val realm = Realm.getDefaultInstance()
            var dmxModuleObject = realm.where(DmxModuleObject::class.java).findAll()
            if (dmxModuleObject != null && dmxModuleObject.isNotEmpty()) {
                dmxModuleObject[0]?.also {
                    targetVer = it.fwVer
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return targetVer
    }

    fun getDMXModuleObject(realm: Realm, addr: Int): DmxModuleObject? {
        return realm.where(DmxModuleObject::class.java).equalTo("addr", addr)?.findFirst()
    }

    fun getDMXItemTypeDesc(dmxType: Int, dmxThingId: Int): String {
        var targetDes = NAME_TYPE_STATIC_CCT
        when (dmxType) {
            1 -> {
                //fixture
                val fixture = FixtureDbUtils.getFixture(dmxThingId)
                fixture?.also {
                    if (it.type == CustomTypes.FixturesTypes.MOTORIZED_TRACK_HEAD) {
                        targetDes = NAME_TYPE_MOTORIZED_ACCENT
                    }
                }
            }
            2 -> {
                val group = GroupDbUtils.getGroup(dmxThingId)
                group?.also {
                    val groupCtrlType: Int = GroupObjectUtils.getGroupCtrlType(it)
                    if (groupCtrlType == CustomTypes.GroupTypes.MOTORIZED_TRACK_HEAD) {
                        targetDes = NAME_TYPE_MOTORIZED_ACCENT
                    }
                }
            }
        }
        return targetDes
    }

    fun setModuleEnable(dmxAddr: Int, enable: Boolean) {
        val realm = Realm.getDefaultInstance()
        val dmxModuleObject = getDMXModuleObject(realm, dmxAddr)
        dmxModuleObject?.also {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }
            dmxModuleObject.isEnabled = enable
            if (realm.isInTransaction) {
                realm.commitTransaction()
            }
        }

    }

    @MainThread
    fun getExistDMXSize(): Int {
        var targetSize = 0
        val allDmxObjects = Realm.getDefaultInstance().where(DmxObject::class.java).findAll()
        if (allDmxObjects.isNotEmpty()) {
            targetSize = allDmxObjects.size
        }
        return targetSize
    }
}
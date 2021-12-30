package com.phone.base.common.utils

import androidx.annotation.MainThread
import com.phone.base.common.realmObject.GroupObject
import io.realm.Realm

/**
 * description ：
 * author :
 * email : @waclighting.com.cn
 * date : 2021/1/21
 */
object GroupDbUtils {

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getAllGroups(): List<GroupObject> {
        return Realm.getDefaultInstance().where(GroupObject::class.java).findAll()
    }

    /**
     * Must use this method in the main thread
     */
    @MainThread
    fun getGroup(groupAddress: Int): GroupObject? {
        return Realm.getDefaultInstance().where(GroupObject::class.java).equalTo("addr", groupAddress).findFirst()
    }

    @MainThread
    fun getGroupsOfFixture(fixtureAddress: Int?): List<GroupObject> {
        val realm = Realm.getDefaultInstance()
        val list: MutableList<GroupObject> = mutableListOf()
        if (fixtureAddress == null) {
            list.addAll(realm.where(GroupObject::class.java).findAll())
        } else {
            list.addAll(realm.where(GroupObject::class.java).findAll()
                    .filter { groupObj ->
                        groupObj.listFixtures.any { fixtureObj ->
                            fixtureObj.addr == fixtureAddress
                        }
                    })
        }
        return list
    }
}
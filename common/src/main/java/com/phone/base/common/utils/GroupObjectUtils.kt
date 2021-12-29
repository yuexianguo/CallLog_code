package com.phone.base.common.utils

import com.phone.base.common.CustomTypes
import com.phone.base.common.LIGHT_LEVEL_MAX
import com.phone.base.common.LIGHT_ZOOM_MAX
import com.phone.base.common.realmObject.FixtureObject
import com.phone.base.common.realmObject.GroupObject
import kotlin.math.min

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/7/24
 */
object GroupObjectUtils {
    fun getGroupStatus(groupObj: GroupObject): Boolean {
        var status = false
        groupObj.listFixtures?.run getIt@{
            forEach {
                it.shadow?.let { fixtureShadow ->
                    status = fixtureShadow.status
                    if (status) {
                        return@getIt
                    }
                }
            }
        }
        return status
    }

    fun getGroupAvgLevel(groupObj: GroupObject?): Int {
        var level = 0
        groupObj?.run {
            listFixtures?.run {
                if (isNotEmpty()) {
                    var sum = 0
                    forEach {
                        it.shadow.let { fixtureShadow ->
                            sum += fixtureShadow.level
                        }
                    }
                    level = sum / size
                }
            }
        }
        return min(level, LIGHT_LEVEL_MAX)
    }

    fun getGroupAvgZoom(groupObj: GroupObject?): Int {
        var zoom = 0
        groupObj?.run {
            listFixtures?.run {
                if (isNotEmpty()) {
                    var sum = 0
                    forEach {
                        it.shadow.let { fixtureShadow ->
                            sum += fixtureShadow.zoom
                        }
                    }
                    zoom = sum / size
                }
            }
        }
        return min(zoom, LIGHT_ZOOM_MAX)
    }

    fun getGroupCtrlType(groupObject: GroupObject): Int {
        val listFixtures = groupObject.listFixtures
        val fixtureList = ArrayList<FixtureObject>(listFixtures)
        var groupType: Int = CustomTypes.GroupTypes.SINGLE_COLOR
        var motorTypeCount = 0
        if (fixtureList.isNotEmpty() && fixtureList.size > 0) {
            fixtureList.forEach { fixtureObject ->
                if (fixtureObject.type == CustomTypes.GroupTypes.MOTORIZED_TRACK_HEAD) {
                    motorTypeCount++
                }
            }
            if (motorTypeCount == fixtureList.size) {
                groupType = CustomTypes.GroupTypes.MOTORIZED_TRACK_HEAD
            }
        }
        return groupType
    }

}
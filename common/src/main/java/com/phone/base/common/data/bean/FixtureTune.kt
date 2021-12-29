package com.phone.base.common.data.bean

import com.phone.base.common.realmObject.PresetInfoObject
import io.realm.RealmList

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/8/12
 */
data class FixtureTune(
        val dimmingCurve: Int,
        val offRate: Int,
        val onRate: Int,
        val presetInfo: RealmList<PresetInfoObject>?
)
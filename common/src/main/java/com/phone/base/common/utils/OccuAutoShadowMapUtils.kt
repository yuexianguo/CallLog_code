package com.phone.base.common.utils

import com.phone.base.common.realmObject.AutomationShadow

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/1/9
 */
object OccuAutoShadowMapUtils {
    var configStateMap: HashMap<Int, AutomationShadow> = HashMap() //key:fixtureAddr or groupAddr ;value automationshadow

    var actionFixturesCheckMap: HashMap<Int, Boolean> = HashMap() //key:fixtureAddr ; value:isCheck

    var actionGroupsCheckMap: HashMap<Int, Boolean> = HashMap() //key:groupAddr ; value:isCheck

    var cloneStandAutoAddrList: HashSet<Int> = HashSet()

    fun setConfigState(addr: Int, automationShadow: AutomationShadow) {
        configStateMap[addr] = automationShadow
    }

    fun getConfigState(addr: Int): AutomationShadow? {
        return configStateMap[addr]
    }

    fun clear() {
        configStateMap.clear()
        cloneStandAutoAddrList.clear()
        actionFixturesCheckMap.clear()
        actionGroupsCheckMap.clear()
    }

    fun addCloneStandAutoAddrs(standAutoAddrs: HashSet<Int>) {
        cloneStandAutoAddrList.addAll(standAutoAddrs)
    }

    fun modifyFixturesCheckMap(addr: Int, isCheck: Boolean) {
        actionFixturesCheckMap[addr] = isCheck
    }

    fun modifyGroupsCheckMap(addr: Int, isCheck: Boolean) {
        actionGroupsCheckMap[addr] = isCheck
    }

}
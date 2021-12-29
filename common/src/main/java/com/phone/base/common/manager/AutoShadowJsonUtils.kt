package com.phone.base.common.manager

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.phone.base.common.realmObject.AutomationShadow

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/1/8
 */
object AutoShadowJsonUtils {
    fun toJsonObject(automationShadow: AutomationShadow?): JsonObject {
        val jsonObject = JsonObject()
        automationShadow?.let {
            jsonObject.addProperty("unionKey", it.unionKey)
            jsonObject.addProperty("autoAddr", it.autoAddr)
            jsonObject.addProperty("fixtureAddr", it.fixtureAddr)
            jsonObject.addProperty("groupAddr", it.groupAddr)
            jsonObject.addProperty("status", it.isStatus)
            jsonObject.addProperty("level", it.level)
            jsonObject.addProperty("temperature", it.temperature)
            jsonObject.addProperty("blue", it.blue)
            jsonObject.addProperty("green", it.green)
            jsonObject.addProperty("red", it.red)
            jsonObject.addProperty("zoom", it.zoom)
            jsonObject.addProperty("axis", it.axis)
            jsonObject.addProperty("timeLr", it.timeLr)
            jsonObject.addProperty("timeUd", it.timeUd)
            jsonObject.addProperty("tilt", it.tilt)
            jsonObject.addProperty("pan", it.pan)
        }
        return jsonObject
    }

    fun getListJson(jsonStr: String?): ArrayList<AutomationShadow> {
        val list: ArrayList<AutomationShadow> = arrayListOf()
        if (jsonStr?.isNotEmpty() == true) {
            val jsonArray = Gson().fromJson(jsonStr, JsonArray::class.java)
            for (i in 0 until jsonArray.size()) {
                list.add(Gson().fromJson(jsonArray[i], AutomationShadow::class.java))
            }
        }
        return list
    }
}
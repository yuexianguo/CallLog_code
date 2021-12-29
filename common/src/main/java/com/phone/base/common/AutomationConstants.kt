package com.phone.base.common

import androidx.annotation.IntDef

/**
 * description ：
 * author : Andy.Guo
 * email : Andy.Guo@waclighting.com.cn
 * date : 2020/12/16
 */
interface AutomationConstants {
    companion object {
        const val AUTO_STANDARD = "Standard"
        const val AUTO_OCCUPANCY = "Occupancy"
        const val AUTO_Light = "Light Harvesting"
        const val AUTO_ACTION_TYPE_OCCUPIED = "Occupied Action"
        const val AUTO_ACTION_TYPE_UN_OCCUPIED = "Un-Occupied Action"
        const val AUTO_ACTION_TYPE_DISABLE = "Disabled Action"
    }

    @IntDef(AutoType.Standard,AutoType.Occupy,AutoType.Light)
    annotation class AutoType {
        companion object {
            const val Standard = 1
            const val Occupy = 2
            const val Light = 3
        }
    }

    @IntDef(StandardItem.Name,StandardItem.Type,StandardItem.Devices,StandardItem.Favorite)
    annotation class StandardItem {
        companion object {
            const val Name = 0
            const val Type = 1
            const val Devices = 2
            const val Favorite = 3
            const val Background = 4
        }
    }
    @IntDef(OccuFragmentItem.Name,OccuFragmentItem.Type,OccuFragmentItem.Active,OccuFragmentItem.Triggers,OccuFragmentItem.Occupied_Action,OccuFragmentItem.Un_Occupied_Action,OccuFragmentItem.Disabled_Action,OccuFragmentItem.Favorite)
    annotation class OccuFragmentItem {
        companion object {
            const val Name = 0
            const val Type = 1
            const val Active = 2
            const val Triggers = 3
            const val Occupied_Action = 4
            const val Un_Occupied_Action = 5
            const val Disabled_Action = 6
            const val Favorite = 7
            const val Background = 8
        }
    }

    @IntDef(LightFragmentItem.Name,LightFragmentItem.Type,LightFragmentItem.Active,LightFragmentItem.Sensor,LightFragmentItem.Group,LightFragmentItem.TargetLevel,LightFragmentItem.Favorite)
    annotation class LightFragmentItem {
        companion object {
            const val Name = 0
            const val Type = 1
            const val Active = 2
            const val Sensor = 3
            const val Group = 4
            const val TargetLevel = 5
            const val Favorite = 6
            const val Background = 7
        }
    }


}


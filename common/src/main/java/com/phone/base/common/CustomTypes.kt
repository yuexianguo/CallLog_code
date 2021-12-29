package com.phone.base.common

import androidx.annotation.IntDef
import androidx.annotation.StringDef

/**
 * description ： All types defined in this interface ars used as enum
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/8/27
 */
interface CustomTypes {
    @IntDef(AxisType.AXIS_RIGHT, AxisType.AXIS_LEFT, AxisType.AXIS_DOWN, AxisType.AXIS_UP)
    annotation class AxisType {
        companion object {
            const val AXIS_RIGHT = 16
            const val AXIS_LEFT = 32
            const val AXIS_DOWN = 64
            const val AXIS_UP = 128
        }
    }

    @IntDef(FixturesTypes.SINGLE_COLOR, FixturesTypes.TUNABLE_WHITE, FixturesTypes.RGBW, FixturesTypes.MOTORIZED_TRACK_HEAD, FixturesTypes.SCM, FixturesTypes.IOTM)
    annotation class FixturesTypes {
        companion object {
            const val SINGLE_COLOR = 0
            const val TUNABLE_WHITE = 1
            const val RGBW = 2
            const val MOTORIZED_TRACK_HEAD = 3
            const val SCM = 4
            const val IOTM = 100
        }
    }

    @IntDef(SubType.NOSUBTYPE, SubType.SENSOR,SubType.RESERVED,SubType.DOWN_LIGHT,SubType.CCT_LIGHT,SubType.RGBW_LIGHT)
    annotation class SubType {
        companion object {
            const val NOSUBTYPE = 0
            const val RESERVED = 1
            const val SENSOR = 2
            const val MOTORIZED_TRACK_HEAD = 3
            const val DOWN_LIGHT = 4  //single light
            const val CCT_LIGHT = 5
            const val RGBW_LIGHT = 6
            const val DMX = 7
        }
    }

    @IntDef(SensorTypes.UNKNOWN, SensorTypes.OCCUPANCY_ONLY, SensorTypes.LIGHT_ONLY, SensorTypes.OCCUPANCY_AND_LIGHT)
    annotation class SensorTypes {
        companion object {
            const val UNKNOWN = 0
            const val OCCUPANCY_ONLY = 1
            const val LIGHT_ONLY = 2
            const val OCCUPANCY_AND_LIGHT = 3
        }
    }

    @IntDef(GroupTypes.SINGLE_COLOR, GroupTypes.TUNABLE_WHITE, GroupTypes.RGBW, GroupTypes.MOTORIZED_TRACK_HEAD)
    annotation class GroupTypes {
        companion object {
            const val SINGLE_COLOR = 0
            const val TUNABLE_WHITE = 1
            const val RGBW = 2
            const val MOTORIZED_TRACK_HEAD = 3
        }
    }

    @IntDef(InputBindType.FIXTURE, InputBindType.GROUP)
    annotation class InputBindType {
        companion object {
            const val FIXTURE = 0
            const val GROUP = 1
        }
    }

    @IntDef(AddressType.FIXTURE, AddressType.GROUP, AddressType.AUTOMATION)
    annotation class AddressType {
        companion object {
            const val FIXTURE = 0
            const val GROUP = 1
            const val AUTOMATION = 2
        }
    }

    @IntDef(TriggerAction.EXECUTE, TriggerAction.ENABLE, TriggerAction.DISABLE)
    annotation class TriggerAction {
        companion object {
            const val EXECUTE = 1
            const val ENABLE = 2
            const val DISABLE = 3
        }
    }

    @StringDef(ControlDialogType.BASE_FIXTURE_CONTROL, ControlDialogType.MOTORIZE_FIXTURE_TRACK, ControlDialogType.BASE_GROUP_CONTROL, ControlDialogType.MOTORIZE_GROUP_TRACK)
    annotation class ControlDialogType {
        companion object {
            const val BASE_FIXTURE_CONTROL = "base_fixture_control_dialog"
            const val MOTORIZE_FIXTURE_TRACK = "motorize_fixture_track_dialog"
            const val BASE_GROUP_CONTROL = "base_group_control_dialog"
            const val MOTORIZE_GROUP_TRACK = "motorize_group_track_dialog"
        }
    }
}
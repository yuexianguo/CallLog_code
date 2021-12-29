package com.phone.base.common

interface MainContainerKeys {
    companion object {
        const val KEY_TARGET_FRAGMENT = "key_target_fragment"
        const val CONTROL_TYPE = "tag_control_detail_type"
        const val TAG_CONTROL_TYPE_FIXTURE = "ControlDetailFixture"
        const val TAG_CONTROL_TYPE_GROUP = "ControlDetailGroup"
        const val FRAGMENT_CONTROLDETAIL = "ControlDetailFragment"
        const val CONTROL_ITEM_TYPE = "control_item_type"
        const val LIGHT_CTRL_DIALOG = 1
        const val FAN_CTRL_DIALOG = 2
        const val MOVE_CTRL_DIALOG = 3
        const val CONTROL_ADDR = "control_addr"
        const val CONTROL_FIXTURE = 0
        const val CONTROL_GROUP = 1

        const val FRAGMENT_FIXTURESETTING = "FixtureSettingFragment"
        const val FRAGMENT_FIXTURECONFIG = "FixtureConfigFragment"
        const val FRAGMENT_GROUPSETTING = "GroupSettingFragment"
        const val TAG_ADD_START_FRAGMENT = "AddStartFragment"
        const val FRAGMENT_MOTORIZE = "MotorizeTrackFragment"
        const val FIXTURE_ADDR = "fixture_addr"
        const val GROUP_ADDR = "group_addr"
    }
}
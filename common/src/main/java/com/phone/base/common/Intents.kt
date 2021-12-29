package com.phone.base.common

interface Intents {
    companion object {
        private const val packageName = "com.phone.base"
        const val ACTION_START_LOGIN_ACTIVITY = "$packageName.action.START_ACCOUNT_ACTIVITY"

        //Start activity
        const val ACTION_START_MAIN_ACTIVITY = "$packageName.action.START_MAIN_ACTIVITY"
        const val ACTION_START_SMART_SOCKET_ACTIVITY = "$packageName.action.START_SMART_SOCKET_ACTIVITY"
        const val ACTION_START_MF_FANS_ACTIVITY = "$packageName.action.START_MF_FANS_ACTIVITY"
        const val ACTION_START_STRUT_ACTIVITY = "$packageName.action.START_STRUT_ACTIVITY"
        const val ACTION_START_CONFIG_STRUT_NET_ACTIVITY = "$packageName.action.START_CONFIG_STRUT_NET_ACTIVITY"
        const val ACTION_START_CONFIG_MF_NET_ACTIVITY = "$packageName.action.START_CONFIG_MF_NET_ACTIVITY"
        const val ACTION_START_CONFIG_WAC_NET_ACTIVITY = "$packageName.action.START_CONFIG_WAC_NET_ACTIVITY"

        //For setting menu
        const val ACTION_MENU_START_GROUPS = "$packageName.action.START_GROUPS"
        const val ACTION_MENU_START_AUTOMATION = "$packageName.action.START_AUTOMATION"
        const val ACTION_MENU_START_SCHEDULES = "$packageName.action.START_SCHEDULES"
        const val ACTION_MENU_START_SETTINGS = "$packageName.action.START_SETTINGS"

        //Broadcast
        const val ACTION_ADD_CPU_SUCCESS = "$packageName.action.ADD_CPU_SUCCESS"
        const val ACTION_REMOVE_CPU_SUCCESS = "$packageName.action.REMOVE_CPU_SUCCESS"
        const val ACTION_USE_AP_MODE = "$packageName.action.USE_AP_MODE"
        const val ACTION_BRAIN_STATUS_CHANGED = "$packageName.action.BRAIN_STATUS_CHANGED"

        const val ACTION_BRAIN_LIST_CHANGED = "$packageName.action.BRAIN_LIST_CHANGED"

        const val ACTION_CPU_NAME_CHANGED = "$packageName.action.CPU_NAME_CHANGED"

        const val ACTION_FIXTURE_FACTORY_RESET_SUCCESS = "$packageName.action.FIXTURE_FACTORY_RESET_SUCCESS"

        //ota 2.0 success
        const val ACTION_SYSTEM_UPDATE_SUCCESS = "$packageName.action.SYSTEM_UPDATE_SUCCESS"
        const val ACTION_AWS_TOKEN_INVALID = "$packageName.action.AWS_TOKEN_INVALID"

        //automation clone
        const val ACTION_AUTOMATION_ACTION_CLONE_SUCCESS = "$packageName.action.AUTOMATION_ACTION_CLONE_SUCCESS"
    }
}
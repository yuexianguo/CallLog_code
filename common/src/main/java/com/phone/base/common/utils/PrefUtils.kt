package com.phone.base.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.phone.base.common.BaseApplication.Companion.context
import com.phone.base.common.manager.BrainManager

object PrefUtils {
    // The name of sp, strut_config
    private const val STRUT_CONFIG = "strut_config"
    const val KEY_DCM_FW_FILE_PATH = "key_dcm_fw_file_path"
    const val KEY_IOTM_FW_FILE_PATH = "key_iotm_fw_file_path"
    const val KEY_SCM_FW_FILE_PATH = "key_scm_fw_file_path"
    const val KEY_SENSOR_FW_FILE_PATH = "key_sensor_fw_file_path"
    const val KEY_DMX_FW_FILE_PATH = "key_dmx_fw_file_path"
    const val KEY_MOTOR_FW_FILE_PATH = "key_motor_fw_file_path"

    const val KEY_DCM_FW_LOCAL_VER = "key_dcm_fw_local_ver"
    const val KEY_IOTM_FW_LOCAL_VER = "key_iotm_fw_local_ver"
    const val KEY_SCM_FW_LOCAL_VER = "key_scm_fw_local_ver"
    const val KEY_SENSOR_FW_LOCAL_VER = "key_sensor_fw_local_ver"
    const val KEY_DMX_FW_LOCAL_VER = "key_dmx_fw_local_ver"
    const val KEY_MOTOR_FW_LOCAL_VER = "key_motor_fw_local_ver"

    const val KEY_DCM_FW_CLOUD_VER = "key_dcm_fw_cloud_ver"
    const val KEY_IOTM_FW_CLOUD_VER = "key_iotm_fw_cloud_ver"
    const val KEY_SCM_FW_CLOUD_VER = "key_scm_fw_cloud_ver"
    const val KEY_SENSOR_FW_CLOUD_VER = "key_sensor_fw_cloud_ver"
    const val KEY_DMX_FW_CLOUD_VER = "key_dmx_fw_cloud_ver"
    const val KEY_MOTOR_FW_CLOUD_VER = "key_motor_fw_cloud_ver"

    const val KEY_DCM_FW_LINK = "key_dcm_fw_url"
    const val KEY_IOTM_FW_LINK = "key_iotm_fw_url"
    const val KEY_SCM_FW_LINK = "key_scm_fw_url"
    const val KEY_SENSOR_FW_LINK = "key_sensor_fw_link"
    const val KEY_DMX_FW_LINK = "key_dmx_fw_link"
    const val KEY_MOTOR_FW_LINK = "key_motor_fw_link"

    //for auth
    const val KEY_REFRESH_TOKEN = "key_refresh_token"
    const val KEY_FACEBOOK_ID_TOKEN = "key_face_book_id_token"
    const val KEY_GOOGLE_ID_TOKEN = "key_google_id_token"
//    const val KEY_THIRD_AUTH_TIME = "key_third_auth_time"

    //user info, saved as json string
    const val KEY_STRUT_USER_INFO = "key_user_info"
    //save the connected cpu mac
    const val KEY_CURRENT_CPU_MAC = "key_current_cpu_mac"
    const val KEY_IS_TEST_ENDPOINT = "key_is_test_endpoint"
    const val KEY_NUM_DOWNLOAD  = "key_num_download"

    const val KEY_IS_NOW_ON_OTA = "key_is_now_on_ota"
    const val KEY_IS_NOW_ON_CONFIG_NET = "key_is_now_on_config_net"

    @JvmStatic
    fun readString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    @JvmStatic
    fun readString(key: String, def: String?): String? {
        return sharedPreferences.getString(key, def)
    }

    @JvmStatic
    fun writeString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    @JvmStatic
    fun readBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    @JvmStatic
    fun readBoolean(key: String?, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    @JvmStatic
    fun writeBoolean(key: String?, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    @JvmStatic
    fun readInt(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    @JvmStatic
    fun readInt(key: String?, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    @JvmStatic
    fun writeInt(key: String?, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    @JvmStatic
    fun readLong(key: String?): Long {
        return sharedPreferences.getLong(key, 0)
    }

    @JvmStatic
    fun writeLong(key: String?, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    @JvmStatic
    fun remove(key: String?) {
        sharedPreferences.edit().remove(key).apply()
    }

    @JvmStatic
    fun removeAll() {
        sharedPreferences.edit().clear().apply()
    }

    @JvmStatic
    val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(STRUT_CONFIG, Context.MODE_PRIVATE)
}
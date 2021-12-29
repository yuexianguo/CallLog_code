package com.phone.base.common.utils

import android.text.TextUtils
import com.phone.base.common.BuildConfig
import com.phone.base.common.CustomTypes
import com.phone.base.common.READ_MAX_NUM_VER
import com.phone.base.common.data.bean.AllOTAStatusBean
import com.phone.base.common.data.bean.AllOTAStatusResponse
import com.phone.base.common.data.bean.GetLatestFwResponse
import com.phone.base.common.manager.BrainManager.deviceObject
import com.phone.base.common.utils.PrefUtils.KEY_DCM_FW_LOCAL_VER
import com.phone.base.common.utils.PrefUtils.KEY_DMX_FW_LOCAL_VER
import com.phone.base.common.utils.PrefUtils.KEY_IOTM_FW_LOCAL_VER
import com.phone.base.common.utils.PrefUtils.KEY_MOTOR_FW_LOCAL_VER
import com.phone.base.common.utils.PrefUtils.KEY_SCM_FW_LOCAL_VER
import com.phone.base.common.utils.PrefUtils.KEY_SENSOR_FW_LOCAL_VER
import com.phone.base.common.utils.PrefUtils.readString
import com.phone.base.common.utils.PrefUtils.writeString
import java.io.File
import java.util.*

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/3/2
 */
object AllFwOTAUtils {

    const val BATCH_STATUS_WAITING_OTA = 0 // waiting for OTA
    const val BATCH_STATUS_OTA_SUCCESS = 1 // OTA successful
    const val BATCH_STATUS_NODE_OFFLINE = 2 // node off line
    const val BATCH_STATUS_VERSION_ERROR = 3 // firmware version error
    const val BATCH_STATUS_RESET_ERROR = 4 // reset node fail
    const val BATCH_STATUS_DOING_OTA = 5 // doing ota
    const val BATCH_STATUS_OTA_FAIL = 6 // OTA FAIL
    const val BATCH_STATUS_LATEST_VERSION = 7 // Latest version

    fun getAllFwStatus(code: Int): String {
        var statusString = "Pending"
        when (code) {
            BATCH_STATUS_WAITING_OTA -> {
                statusString = "Pending"
            }
            BATCH_STATUS_OTA_SUCCESS -> {
                statusString = "Complete"
            }
            BATCH_STATUS_NODE_OFFLINE -> {
                statusString = "Node Offline"
            }
            BATCH_STATUS_VERSION_ERROR -> {
                statusString = "Version Error"
            }
            BATCH_STATUS_RESET_ERROR -> {
                statusString = "Reset Error"
            }
            BATCH_STATUS_DOING_OTA -> {
                statusString = "In Progress..."
            }
            BATCH_STATUS_OTA_FAIL -> {
                statusString = "Failed"
            }
            BATCH_STATUS_LATEST_VERSION -> {
                statusString = "Latest Version"
            }
            else -> {
            }
        }
        return statusString
    }

    fun isSupportAllOTAByIOTMVer(): Boolean {
        var isSupport = false
        deviceObject?.also {
            val iotmVer = it.iotmVer
            iotmVer?.also {
                if (iotmVer.isNotEmpty()) {
                    val iotmFwVer: Array<String> = iotmVer.split("\\.".toRegex()).toTypedArray()
                    if (iotmFwVer.size >= 3) {
                        if (iotmFwVer[1].toInt() >= 1 && iotmFwVer[2].substring(0, 4).toInt() >= 33) {
                            //iotm support
                            isSupport = true
                        }
                    }
                }
            }
        }
        return isSupport
    }

    fun isIOTMNeedOTA(iotmVer: String?): Boolean {
        var isIOTMNeedOTA = false
        iotmVer?.also {
            if (!TextUtils.isEmpty(it) && !TextUtils.isEmpty(readString(KEY_IOTM_FW_LOCAL_VER))) {
                val fwVer: Array<String> = it.split("\\.".toRegex()).toTypedArray()
                val targetVer = readString(KEY_IOTM_FW_LOCAL_VER)!!.split("\\.".toRegex()).toTypedArray()
                if (fwVer.size >= 3) {
                    isIOTMNeedOTA = checkByThreeSplit(fwVer, targetVer)
                }
            }
        }
        if (TextUtils.isEmpty(iotmVer)) {
            isIOTMNeedOTA = true
        }
        return isIOTMNeedOTA
    }

    fun isSCMNeedOTA(scmFwVer: String?): Boolean {
        var isSCMNeedOTA = false
        scmFwVer?.also {
            if (!TextUtils.isEmpty(it) && !TextUtils.isEmpty(readString(KEY_SCM_FW_LOCAL_VER))) {
                val fwVer: Array<String> = it.split("\\.".toRegex()).toTypedArray()
                val targetVer = readString(KEY_SCM_FW_LOCAL_VER)!!.split("\\.".toRegex()).toTypedArray()
                if (fwVer.size >= 2) {
                    isSCMNeedOTA = checkByTwoSplit(fwVer, targetVer)
                }
            }
        }
        if (TextUtils.isEmpty(scmFwVer)) {
            isSCMNeedOTA = true
        }
        return isSCMNeedOTA
    }

    fun isDCMNeedOTA(dcmFwVer: String?): Boolean {
        var isDCMNeedOTA = false
        if (!TextUtils.isEmpty(dcmFwVer) && !TextUtils.isEmpty(readString(KEY_DCM_FW_LOCAL_VER))) {
            dcmFwVer?.also {
                val fwVer: Array<String> = it.split("\\.".toRegex()).toTypedArray()
                val targetVer = readString(KEY_DCM_FW_LOCAL_VER)!!.split("\\.".toRegex()).toTypedArray()
                if (fwVer.size >= 2) {
                    isDCMNeedOTA = checkByTwoSplit(fwVer, targetVer)
                }
            }
        }
        if (TextUtils.isEmpty(dcmFwVer)) {
            isDCMNeedOTA = true
        }
        return isDCMNeedOTA
    }

    fun isSensorNeedOTA(sensorFwVer: String): Boolean {
        if (BuildConfig.isOneOneVer) return false
        var isSensorNeedOTA = false
        if (!TextUtils.isEmpty(sensorFwVer) && !TextUtils.isEmpty(readString(KEY_SENSOR_FW_LOCAL_VER))) {
            val fwVer: Array<String> = sensorFwVer.split("\\.".toRegex()).toTypedArray()
            val targetVer = readString(KEY_SENSOR_FW_LOCAL_VER)!!.split("\\.".toRegex()).toTypedArray()
            if (fwVer.size >= 2) {
                isSensorNeedOTA = checkByTwoSplit(fwVer, targetVer)
            }
        }
        return isSensorNeedOTA
    }

    fun isDMXNeedOTA(dmxVer: String): Boolean {
        var isDMXNeedOTA = false
        if (!TextUtils.isEmpty(dmxVer) && !TextUtils.isEmpty(readString(KEY_DMX_FW_LOCAL_VER))) {
            val fwVer: Array<String> = dmxVer.split("\\.".toRegex()).toTypedArray()
            val targetVer = readString(KEY_DMX_FW_LOCAL_VER)!!.split("\\.".toRegex()).toTypedArray()
            if (fwVer.size >= 2) {
                isDMXNeedOTA = checkByTwoSplit(fwVer, targetVer)
            }
        }
        if (TextUtils.isEmpty(dmxVer)) {
            isDMXNeedOTA = true
        }
        return isDMXNeedOTA
    }

    private fun checkByTwoSplit(fwVer: Array<String>, targetVer: Array<String>): Boolean {
        val major: Int = fwVer[0].toInt()
        val minor: Int = fwVer[1].toInt()
        val majorC = targetVer[0].toInt()
        val minorC = targetVer[1].toInt()
        return majorC > major || majorC == major && minorC > minor
    }

    private fun checkByThreeSplit(fwVer: Array<String>, targetVer: Array<String>): Boolean {
        val major: Int = fwVer[0].toInt()
        val minor: Int = fwVer[1].toInt()
        val min: Int = fwVer[2].substring(0, 4).toInt()
        val majorC = targetVer[0].toInt()
        val minorC = targetVer[1].toInt()
        val minC = targetVer[2].substring(0, 4).toInt()
        return majorC > major || majorC == major && minorC > minor || majorC == major && minorC == minor && minC > min
    }

    fun writePref(getLatestFwResponse: GetLatestFwResponse) {
        val scmLink: String = getLatestFwResponse.scm.link
        val iotmLink: String = getLatestFwResponse.iotm.link
        val dcmLink: String = getLatestFwResponse.dcm.link
        val scmVersion: String = getLatestFwResponse.scm.version
        val iotmVersion: String = getLatestFwResponse.iotm.version
        val dcmVersion: String = getLatestFwResponse.dcm.version
        writeString(PrefUtils.KEY_SCM_FW_LINK, getTargetLink(scmLink))
        writeString(PrefUtils.KEY_IOTM_FW_LINK, getTargetLink(iotmLink))
        writeString(PrefUtils.KEY_DCM_FW_LINK, getTargetLink(dcmLink))
        writeString(PrefUtils.KEY_SCM_FW_CLOUD_VER, scmVersion)
        writeString(PrefUtils.KEY_IOTM_FW_CLOUD_VER, iotmVersion)
        writeString(PrefUtils.KEY_DCM_FW_CLOUD_VER, dcmVersion)

        getLatestFwResponse.sensor.also {
            val sensorLink: String = it.link
            writeString(PrefUtils.KEY_SENSOR_FW_LINK, getTargetLink(sensorLink))
            val sensorVersion: String = it.version
            writeString(PrefUtils.KEY_SENSOR_FW_CLOUD_VER, sensorVersion)
        }

        getLatestFwResponse.dmx.also {
            val dmxLink: String = it.link
            writeString(PrefUtils.KEY_DMX_FW_LINK, getTargetLink(dmxLink))
            val dmxVersion: String? = it.version
            writeString(PrefUtils.KEY_DMX_FW_CLOUD_VER, dmxVersion)
        }

        if (getLatestFwResponse.motor == null) {
            writeString(PrefUtils.KEY_MOTOR_FW_LINK, "")
            writeString(PrefUtils.KEY_MOTOR_FW_CLOUD_VER, "")
        } else {
            getLatestFwResponse.motor.also {
                val motorVersion: String = it.version
                val motorLink: String = it.link
                writeString(PrefUtils.KEY_MOTOR_FW_LINK, getTargetLink(motorLink))
                writeString(PrefUtils.KEY_MOTOR_FW_CLOUD_VER, motorVersion)
            }
        }

    }

    fun getTargetLink(link: String): String {
        var targetLink = link
        if (!TextUtils.isEmpty(targetLink) && !targetLink.contains("https://")) targetLink = String.format("https://%s", targetLink)
        return targetLink
    }

    fun getCPUList(allOTAStatusResponse: AllOTAStatusResponse): ArrayList<AllOTAStatusBean> {
        var targetList: ArrayList<AllOTAStatusBean> = arrayListOf()
        val ccmStatus = allOTAStatusResponse.CCMStatus
        val iotmStatus = ccmStatus?.IOTMStatus
        val scmStatus = ccmStatus?.SCMStatus
        if (iotmStatus == null) {
            var iotmStatusBean = AllOTAStatusBean("IOTM", "Latest Version")
            targetList.add(iotmStatusBean)
        }
        iotmStatus?.also {
            var iotmStatusBean = AllOTAStatusBean("IOTM", getAllFwStatus(it))
            targetList.add(iotmStatusBean)
        }
        if (scmStatus == null) {
            var scmStatusBean = AllOTAStatusBean("SCM", "Latest Version")
            targetList.add(scmStatusBean)
        }
        scmStatus?.also {
            var scmStatusBean = AllOTAStatusBean("SCM", getAllFwStatus(it))
            targetList.add(scmStatusBean)
        }
        return targetList
    }

    fun getFixturesList(allOTAStatusResponse: AllOTAStatusResponse): ArrayList<AllOTAStatusBean> {
        var targetList: ArrayList<AllOTAStatusBean> = arrayListOf()
        val dcmStatus = allOTAStatusResponse.DCMStatus
        dcmStatus?.also {
            if (it.isNotEmpty()) {
                for (singleStatus in it) {
                    singleStatus.nodeStatus?.also {
                        for (nodeStatus in it) {
                            if (nodeStatus.name != null && nodeStatus.status != null) {
                                var fixtureStatusBean = AllOTAStatusBean(nodeStatus.name, getAllFwStatus(nodeStatus.status))
                                targetList.add(fixtureStatusBean)
                            }
                        }
                    }
                }
            }
        }
        return targetList
    }

    fun isGetStatusOver(allOTAStatusResponse: AllOTAStatusResponse): Boolean {
        var isGetStatusOver = true
        val dcmStatus = allOTAStatusResponse.DCMStatus
        val ccmStatus = allOTAStatusResponse.CCMStatus
        ccmStatus?.also {
            val iotmStatus = it.IOTMStatus
            val scmStatus = it.SCMStatus
            if (iotmStatus != null && (iotmStatus == BATCH_STATUS_WAITING_OTA || iotmStatus == BATCH_STATUS_DOING_OTA)) {
                isGetStatusOver = false
            }
            if (scmStatus != null && (scmStatus == BATCH_STATUS_WAITING_OTA || scmStatus == BATCH_STATUS_DOING_OTA)) {
                isGetStatusOver = false
            }
        }
        dcmStatus?.also {
            if (it.isNotEmpty()) {
                for (singleStatus in it) {
                    singleStatus.nodeStatus?.also {
                        for (nodeStatus in it) {
                            if (nodeStatus.status != null && (nodeStatus.status == BATCH_STATUS_WAITING_OTA || nodeStatus.status == BATCH_STATUS_DOING_OTA)) {
                                isGetStatusOver = false
                            }
                        }
                    }
                }
            }
        }
        return isGetStatusOver
    }


    fun isAllUpTodate(): Boolean {
        var isIOTMUpTodate = true
        var isSCMUpTodate = true
        deviceObject?.also {
            isIOTMUpTodate = !isIOTMNeedOTA(it.iotmVer)
            isSCMUpTodate = !isSCMNeedOTA(it.scmVer)
        }
        val isAllSensorUpTodate = isAllSensorUpTodate()
        val isAllFixturesUpTodate = isAllFixturesUpTodate() //contain fixture, motor
        return isIOTMUpTodate && isSCMUpTodate && isAllSensorUpTodate && isAllFixturesUpTodate && isDMXUpTodate()
    }

    private fun isDMXUpTodate(): Boolean {
        var isUpTodate = true
        val dmxModuleVer = DMXUtils.getDMXModuleVer()
        if (dmxModuleVer.isNotEmpty()) {
            if (isDMXNeedOTA(dmxModuleVer)) {
                return false
            }
        }
        return isUpTodate
    }

    fun isAllSensorUpTodate(): Boolean {
        var isAllUpTodate = true
        val sensorList = SensorDbUtils.getSensors(null)
        if (sensorList.isNotEmpty()) {
            for (sensorObject in sensorList) {
                isAllUpTodate = !isSensorNeedOTA(sensorObject.fwVer)
                if (!isAllUpTodate) {
                    return false
                }
            }
        }
        return isAllUpTodate
    }

    fun isAllFixturesUpTodate(): Boolean {
        var isAllUpTodate = true
        val allFixtures = FixtureDbUtils.getAllFixtures()
        if (allFixtures.isNotEmpty()) {
            for (fixture in allFixtures) {
                if (fixture.shadow != null) {
                    if (fixture.type == CustomTypes.FixturesTypes.SINGLE_COLOR) {
                        var isAllFixtureUpTodate = !isDCMNeedOTA(fixture.shadow.fwVer)
                        if (!isAllFixtureUpTodate) {
                            return false
                        }
                    } else if (!BuildConfig.isOneOneVer && fixture.type == CustomTypes.FixturesTypes.MOTORIZED_TRACK_HEAD) {
                        var isAllMotorUpTodate = !isMotorNeedOTA(fixture.shadow.fwVer)
                        if (!isAllMotorUpTodate) {
                            return false
                        }
                    }

                }
            }
        }
        return isAllUpTodate
    }

    fun isMotorNeedOTA(motorLightVer: String): Boolean {
        if (BuildConfig.isOneOneVer) return false
        var isMotorNeedOTA = false
        if (!TextUtils.isEmpty(motorLightVer) && !TextUtils.isEmpty(readString(KEY_MOTOR_FW_LOCAL_VER))) {
            val fwVer: Array<String> = motorLightVer.split("\\.".toRegex()).toTypedArray()
            val targetVer = readString(KEY_MOTOR_FW_LOCAL_VER)!!.split("\\.".toRegex()).toTypedArray()
            if (fwVer.size >= 2) {
                isMotorNeedOTA = checkByTwoSplit(fwVer, targetVer)
            }
        }
        if (TextUtils.isEmpty(motorLightVer)) {
            isMotorNeedOTA = true
        }
        return isMotorNeedOTA
    }

    fun isNeedReadMax(fwVer: String?): Boolean {
        var isNeedReadMax = false
        fwVer?.also {
            val fwVerSplit: Array<String> = it.split("\\.".toRegex()).toTypedArray()
            val maxVerSplit: Array<String> = READ_MAX_NUM_VER.split("\\.".toRegex()).toTypedArray()
            if (fwVerSplit.size >= 3) {
                isNeedReadMax = checkByThreeSplit(maxVerSplit, fwVerSplit)
            }
        }
        return isNeedReadMax
    }

    fun isCurrentSCMFwVerNeedOTA(): Boolean {
        var isCurrentSCMFwVerNeedOTA = false
        deviceObject?.also {
            isCurrentSCMFwVerNeedOTA = isSCMNeedOTA(it.scmVer)
        }
        return isCurrentSCMFwVerNeedOTA
    }

}
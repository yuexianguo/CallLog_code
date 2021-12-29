package com.phone.base.common.manager

import android.os.Handler
import android.os.Looper
import com.phone.base.common.BaseApplication
import com.phone.base.common.utils.LogUtil
import it.ennova.zerxconf.ZeRXconf
import rx.Subscription

private const val DEFAULT_BASE_URL = "10.10.10.1"

private const val CHECK_DEVICE_TIME = 1 * 5 * 1000L

private const val SEARCH_DEVICE_TIME = 1 * 30 * 1000L

private const val SEARCH_MF_GROUP_DEVICE_TIME = 1 * 6 * 1000L

object NsdHelper {
    fun checkDevice(lastSixMac: String, listener: OnCheckDeviceListener, timeout: Long = CHECK_DEVICE_TIME): Subscription? {
        if (lastSixMac.isEmpty()) {
            return null
        }
        LogUtil.d("ShadowManager", "lastSix mac=$lastSixMac, checkDevice start")
        val handler = Handler(Looper.getMainLooper())
        var isFound = false
        val subscribe = ZeRXconf.startDiscovery(BaseApplication.context, "_easylink._tcp.")
                .subscribe({ networkServiceDiscoveryInfo ->
                    LogUtil.d("ShadowManager", "discover info=${networkServiceDiscoveryInfo}")
                    networkServiceDiscoveryInfo?.let { networkInfo ->
                        val serviceName = networkInfo.serviceName
                        LogUtil.d("ShadowManager", "discover device , name =$serviceName")
                        networkInfo.address?.let { address ->
                            val ip = address.toString().replace("/", "")
                            if (serviceName.contains(lastSixMac, true) && DEFAULT_BASE_URL != ip) {
                                LogUtil.d("ShadowManager", "found STRUT device AP = $serviceName ，ip = $ip")
                                isFound = true
                                listener.onFoundDevice(DeviceInfo(serviceName, ip))
                            }
                        }
                    }
                }, {
                    listener.onError(it)
                    LogUtil.d("ShadowManager", "checkDevice error=${it.printStackTrace()}")
                })

        handler.postDelayed({
            LogUtil.d("ShadowManager", "isFound=${isFound}, isUnSubscribe=${subscribe.isUnsubscribed}")
            if (!isFound && !subscribe.isUnsubscribed) {
                LogUtil.d("ShadowManager", "checkDevice time out")
                listener.onTimeout()
            }
        }, timeout)

        return subscribe
    }

    fun searchStrutDevices(listener: OnSearchDevicesListener): Subscription {
        LogUtil.d("ShadowManager", "searchDevice start")
        val mapDevices = mutableMapOf<String, DeviceInfo>()
        val handler = Handler(Looper.getMainLooper())
        val subscribe = ZeRXconf.startDiscovery(BaseApplication.context, "_easylink._tcp.")
                .subscribe({ networkServiceDiscoveryInfo ->
                    LogUtil.d("ShadowManager", "discover info=${networkServiceDiscoveryInfo}")
                    networkServiceDiscoveryInfo?.let { networkInfo ->
                        val serviceName = networkInfo.serviceName
                        LogUtil.d("ShadowManager", "discover device , name =$serviceName networkInfo.address= ${networkInfo.address}")
                        networkInfo.address?.let { address ->
                            val ip = address.toString().replace("/", "")
                            if (serviceName.contains("STRUT", true) && DEFAULT_BASE_URL != ip && !mapDevices.containsKey(serviceName)) {
                                LogUtil.d("ShadowManager", "found STRUT device AP = $serviceName ，ip = $ip")
                                mapDevices[serviceName] = DeviceInfo(serviceName, ip)
                                listener.onUpdateDevices(mapDevices.values)
                            }
                        }
                    }
                }, {
                    listener.onError(it)
                    LogUtil.d("ShadowManager", "searchDevice error=${it.printStackTrace()}")
                })

        handler.postDelayed({
            if (mapDevices.isEmpty() && !subscribe.isUnsubscribed) {
                LogUtil.d("ShadowManager", "searchDevice time out")
                listener.onTimeout()
            }
        }, SEARCH_DEVICE_TIME)

        return subscribe
    }

    fun searchMFGroupDevices(listener: OnSearchDevicesListener): Subscription {
        LogUtil.d("ShadowManager", "searchMFGroupDevices start")
        val mapDevices = mutableMapOf<String, String>() //ssid,ip
        val handler = Handler(Looper.getMainLooper())
        val subscribe = ZeRXconf.startDiscovery(BaseApplication.context, "_easylink._tcp.")
                .subscribe({ networkServiceDiscoveryInfo ->
                    LogUtil.d("ShadowManager", "searchMFGroupDevices discover info=${networkServiceDiscoveryInfo}")
                    networkServiceDiscoveryInfo?.let { networkInfo ->
                        val serviceName = networkInfo.serviceName
                        LogUtil.d("ShadowManager", "searchMFGroupDevices discover device , name =$serviceName networkInfo.address= ${networkInfo.address}")
                        networkInfo.address?.let { address ->
                            val ip = address.toString().replace("/", "")
                            if (serviceName.contains("MF", true) && !mapDevices.containsKey(serviceName)) {
                                LogUtil.d("ShadowManager", "searchMFGroupDevices found MF device AP = $serviceName ，ip = $ip")
                                mapDevices[serviceName] = ip
                            }
                        }
                    }
                }, {
                })

        handler.postDelayed({
            LogUtil.d("ShadowManager", "searchDevice time out")
            listener.onMFGroupDevices(mapDevices)
        }, SEARCH_MF_GROUP_DEVICE_TIME)

        return subscribe
    }

    interface OnCheckDeviceListener {
        fun onFoundDevice(deviceInfo: DeviceInfo)
        fun onTimeout()
        fun onError(error: Throwable)
    }

    interface OnSearchDevicesListener {
        fun onUpdateDevices(devices: Collection<DeviceInfo>) {}
        fun onTimeout() {}
        fun onError(error: Throwable) {}
        fun onMFGroupDevices(map: MutableMap<String, String>) {}
    }

    class DeviceInfo(val name: String, val ip: String) {
        val lastSixMac: String by lazy {
            if (this.name.length > 6) {
                this.name.substring(this.name.length - 6)
            } else {
                ""
            }
        }
    }
}
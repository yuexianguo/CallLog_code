package com.phone.base.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/5/27
 */
public class NetUtils {

    //=========================================================
    //
    // This is used to get the name of the current WiFi
    //
    //=========================================================
    public static String getCurrentSsid(Context context) {
        String ssid = "";
        LogUtil.d("AddDeviceActivity:", "|                                       |");
        LogUtil.d("AddDeviceActivity:", "Starting to check current network name");
        if (context == null) {
            return ssid;
        }
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager == null) {
            return ssid;
        }
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            LogUtil.d("AddDeviceActivity:", "ConnectivityManager ended up null... Going to send a blank string for SSID name.");
            LogUtil.d("AddDeviceActivity:", "|                                       |");
            return ssid;
        }

        LogUtil.d("AddDeviceActivity:", "ConnectivityManager was not null... attempting to get SSID name.");
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager == null) {
                return ssid;
            }
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                LogUtil.d("AddDeviceActivity:", "ConnectionInfo was not null and connectionInfo.getSSID has information!!!");
                ssid = connectionInfo.getSSID().replace("\"", ""); //+.replace("\"","")), BZ 20190319
            } else if (connectionInfo == null) {
                LogUtil.d("AddDeviceActivity:", "ConnectionInfo was null...");
            } else if (TextUtils.isEmpty(connectionInfo.getSSID())) {
                LogUtil.d("AddDeviceActivity:", "connectionInfo.getSSID was empty...");
            }
        } else {
            LogUtil.d("AddDeviceActivity:", "Network Info is not connected....");
            if (networkInfo.isConnectedOrConnecting()) {
                LogUtil.d("AddDeviceActivity:", "Network Info is at least trying to connect...");
            } else {
                LogUtil.d("AddDeviceActivity:", "Network Info is not trying to connect...");
            }
        }
        LogUtil.d("AddDeviceActivity:", "|                                       |");
        return ssid;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
                return activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            }
        }

        return false;
    }
}

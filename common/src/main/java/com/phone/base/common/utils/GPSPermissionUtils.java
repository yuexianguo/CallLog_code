package com.phone.base.common.utils;

import android.location.LocationManager;
import android.os.Build;

import com.phone.base.common.BaseApplication;

public class GPSPermissionUtils {
    public static boolean checkGPSpermission() {
        LocationManager locationManager
                = (LocationManager) BaseApplication.getContext().getSystemService(BaseApplication.getContext().LOCATION_SERVICE);

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        LogUtil.d("gps=" + gps + ",network =" + network);


        /*Android 6 close gps=false,network =false open gps=true,network =false
        Android 7 close gps=false,network =false，open gps=true,network =false
        Android 8 close gps=false,network =false，open gps=false,network =true
        Android 9 close：gps=false,network =false。 open：gps=true,network =true
        android 10 close GPS，gps=false,network =false， open：gps=true,network =true    */
        
        //Only Android 8 close gps=false,network =false，open gps=false,network =true ;
        // other android version close gps=false,network =false，open gps=true
        if ((Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.O) && network)
            return true;
        //maybe some other phone only network = true
        if (gps || network) {
            return true;
        }
        return false;
    }

}

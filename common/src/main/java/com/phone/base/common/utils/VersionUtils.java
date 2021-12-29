package com.phone.base.common.utils;

import android.text.TextUtils;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/6/8
 */
public class VersionUtils {
    public static final boolean isGoogleVerLarge(String currentVer, String googleVer){
        boolean isGoogleVerLarge = false;
        if (!TextUtils.isEmpty(currentVer) && !TextUtils.isEmpty(googleVer)){
            String[] currentVerSplit = currentVer.split("\\.");
            String[] googleVerSplit = googleVer.split("\\.");
            isGoogleVerLarge = checkVerThreeSplit(currentVerSplit, googleVerSplit);
        }
        return isGoogleVerLarge;
    }
    private static boolean checkVerThreeSplit(String[] fwVer, String[] targetVer) {
        int major = Integer.parseInt(fwVer[0]);
        int minor = Integer.parseInt(fwVer[1]);
        int min = Integer.parseInt(fwVer[2].substring(0, 1));
        int majorC = Integer.parseInt(targetVer[0]);
        int minorC = Integer.parseInt(targetVer[1]);
        int minC = Integer.parseInt(targetVer[2].substring(0, 1));
        return majorC > major || majorC == major && minorC > minor || majorC == major && minorC == minor && minC > min;
    }
}

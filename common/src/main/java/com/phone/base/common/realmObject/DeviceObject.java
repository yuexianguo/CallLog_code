package com.phone.base.common.realmObject;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/10/22
 */

public class DeviceObject extends RealmObject {
    @PrimaryKey
    private String staMac;
    private String apMac;
    private String owner;
    private String deviceName;
    private String bleMac;
    private String dateCode;
    private String iotmVer;
    private String restVer;
    private String scmVer;
    private String time;
    private String timeZone;
    private String ip;

    //location data of the brain
    private double longitude;
    private double latitude;
    private String locationName;
    private String locationType;
    private String address;
    private String imageName;

    public DeviceObject() {

    }

    public DeviceObject(String staMac) {
        this.staMac = staMac;
    }

    public DeviceObject(String staMac, String deviceName) {
        this.staMac = staMac;
        this.deviceName = deviceName;
    }

    public String getStaMac() {
        return this.staMac;
    }

    public void setStaMac(String staMac) {
        this.staMac = staMac;
    }

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDeviceName() {
        if (TextUtils.isEmpty(deviceName)) {
            return staMac.replace(":", "").substring(6);
        }
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBleMac() {
        return bleMac;
    }

    public void setBleMac(String bleMac) {
        this.bleMac = bleMac;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getIotmVer() {
        return iotmVer;
    }

    public void setIotmVer(String iotmVer) {
        this.iotmVer = iotmVer;
    }

    public String getRestVer() {
        return restVer;
    }

    public void setRestVer(String restVer) {
        this.restVer = restVer;
    }

    public String getScmVer() {
        return scmVer;
    }

    public void setScmVer(String scmVer) {
        this.scmVer = scmVer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Nullable
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getClientId() {
        String clientId = "";
        if (!TextUtils.isEmpty(staMac)) {
            String replaceMac = staMac.replace(":", "");
            clientId = String.format("MF_%s", replaceMac);
        }
        return clientId;
    }
}

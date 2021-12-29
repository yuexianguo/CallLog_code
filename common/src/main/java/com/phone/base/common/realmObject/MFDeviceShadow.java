package com.phone.base.common.realmObject;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kc on 5/31/18.
 * Modified by BZ on 04/01/2019
 */

public class MFDeviceShadow extends RealmObject {
    @PrimaryKey
    public String clientId;
    public boolean lightOn;
    public boolean fanOn;
    public float lightBrightness;
    public int fanSpeed;
    public String fanDirection;
    public String owner;
    public String deviceName;
    public long fanSleepTimer;
    public long lightSleepTimer;
    public boolean decommission;
    public String schedule;
    public String mac;
    public String lightType;
    public String fanType;
    public String fanMotorType;
    public String productionLotNumber;
    public String productSku;
    public String federatedIdentity;
    public String firmwareVersion;
    public boolean adaptiveLearning;
    //+ new features, BZ 20190225
    public boolean connected;
    //+ new features, BZ 20190416
    public @Nullable String localIP;
    //+new for G3 wind  AG 2020/01/08
    public boolean wind;
    public int windSpeed;
    //add for G3 schedule AG 20200205
    public String scheduleVer;
    public long fanTimer;
    public long lightTimer;
    public boolean awayModeEnabled;
    public long uvcUsageInMins;
    public boolean uvcLampAtEol;
    public boolean uvcAutoOffEnabled;
    public int uvcAutoOffTimeInMins;
    public boolean isUvcFan;

    //add from local shadow
    public boolean factoryReset;
    public String mainMcuFirmwareVersion;
    public boolean resetRfPairList;
    public String firmwareUrl;
    public boolean rfPairModeActive;

    //just used in app, from 20210825
    public boolean localControl = false;
    //just used in app, end
    public String FrCodes;

    public MFDeviceShadow(String clientId, boolean lightOn, boolean fanOn, float lightBrightness, int fanSpeed,
                          String fanDirection, String owner, String deviceName, long fanSleepTimer, long lightSleepTimer,
                          boolean decommission, String schedule, String mac, String lightType, String fanType, String fanMotorType,
                          String productionLotNumber, String productSku, String federatedIdentity, String firmwareVersion,
                          boolean adaptiveLearning, boolean wind, int windSpeed, String scheduleVer, long fanTimer, long lightTimer,
                          boolean factoryReset, String mainMcuFirmwareVersion, boolean resetRfPairList, String firmwareUrl, boolean rfPairModeActive) {
        this.clientId = clientId;
        this.lightOn = lightOn;
        this.fanOn = fanOn;
        this.lightBrightness = lightBrightness;
        this.fanSpeed = fanSpeed;
        this.fanDirection = fanDirection;
        this.owner = owner;
        this.deviceName = deviceName;
        this.fanSleepTimer = fanSleepTimer;
        this.lightSleepTimer = lightSleepTimer;
        this.decommission = decommission;
        this.schedule = schedule;
        this.mac = mac;
        this.lightType = lightType;
        this.fanType = fanType;
        this.fanMotorType = fanMotorType;
        this.productionLotNumber = productionLotNumber;
        this.productSku = productSku;
        this.federatedIdentity = federatedIdentity;
        this.firmwareVersion = firmwareVersion;
        this.adaptiveLearning = adaptiveLearning;
        this.wind = wind;
        this.windSpeed = windSpeed;
        this.scheduleVer = scheduleVer;
        this.fanTimer = fanTimer;
        this.lightTimer = lightTimer;
        this.factoryReset = factoryReset;
        this.mainMcuFirmwareVersion = mainMcuFirmwareVersion;
        this.resetRfPairList = resetRfPairList;
        this.firmwareUrl = firmwareUrl;
        this.rfPairModeActive = rfPairModeActive;
    }

    public boolean isLightOn() {
        return lightOn;
    }

    public boolean isFanOn() {
        return fanOn;
    }

    public boolean isDecommission() {
        return decommission;
    }

    public boolean isAdaptiveLearning() {
        return adaptiveLearning;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isUvcFan() {
        return isUvcFan;
    }

    public void setUvcFan(boolean uvcFan) {
        isUvcFan = uvcFan;
    }

    public long getFanTimer() {
        return fanTimer;
    }

    public void setFanTimer(long fanTimer) {
        this.fanTimer = fanTimer;
    }

    public long getLightTimer() {
        return lightTimer;
    }

    public void setLightTimer(long lightTimer) {
        this.lightTimer = lightTimer;
    }

    public String getScheduleVer() {
        return scheduleVer;
    }

    public void setScheduleVer(String scheduleVer) {
        this.scheduleVer = scheduleVer;
    }

    public MFDeviceShadow() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean getLightOn() {
        return lightOn;
    }

    public void setLightOn(boolean lightOn) {
        this.lightOn = lightOn;
    }

    public boolean getFanOn() {
        return fanOn;
    }

    public void setFanOn(boolean fanOn) {
        this.fanOn = fanOn;
    }

    public float getLightBrightness() {
        return lightBrightness;
    }

    public void setLightBrightness(float lightBrightness) {
        this.lightBrightness = lightBrightness;
    }

    public int getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(int fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public String getFanDirection() {
        return fanDirection;
    }

    public void setFanDirection(String fanDirection) {
        this.fanDirection = fanDirection;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public long getFanSleepTimer() {
        return fanSleepTimer;
    }

    public void setFanSleepTimer(long fanSleepTimer) {
        this.fanSleepTimer = fanSleepTimer;
    }

    public long getLightSleepTimer() {
        return lightSleepTimer;
    }

    public void setLightSleepTimer(long lightSleepTimer) {
        this.lightSleepTimer = lightSleepTimer;
    }

    public boolean getDecommission() {
        return decommission;
    }

    public void setDecommission(boolean decommission) {
        this.decommission = decommission;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLightType() {
        return lightType;
    }

    public void setLightType(String lightType) {
        this.lightType = lightType;
    }

    public String getFanType() {
        return fanType;
    }

    public void setFanType(String fanType) {
        this.fanType = fanType;
    }

    public String getFanMotorType() {
        return fanMotorType;
    }

    public void setFanMotorType(String fanMotorType) {
        this.fanMotorType = fanMotorType;
    }

    public String getProductionLotNumber() {
        return productionLotNumber;
    }

    public void setProductionLotNumber(String productionLotNumber) {
        this.productionLotNumber = productionLotNumber;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getFederatedIdentity() {
        return federatedIdentity;
    }

    public void setFederatedIdentity(String federatedIdentity) {
        this.federatedIdentity = federatedIdentity;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public boolean getAdaptiveLearning() {
        return adaptiveLearning;
    }

    public void setAdaptiveLearning(boolean adaptiveLearning) {
        this.adaptiveLearning = adaptiveLearning;
    }

    //+ new features, BZ 20190225
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean getConnected() {
        return connected;
    }

    //+ new features, BZ 20190416
    public void setLocalIP(String localIP) {
        this.localIP = localIP;
    }

    public String getLocalIP() {
        return localIP;
    }

    public boolean getWind() {
        return wind;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public boolean isAwayModeEnabled() {
        return awayModeEnabled;
    }

    public void setAwayModeEnabled(boolean awayModeEnabled) {
        this.awayModeEnabled = awayModeEnabled;
    }

    public long getUvcUsageInMins() {
        return uvcUsageInMins;
    }

    public boolean isUvcLampAtEol() {
        return uvcLampAtEol;
    }

    public void setUvcLampAtEol(boolean uvcLampAtEol) {
        this.uvcLampAtEol = uvcLampAtEol;
    }

    public void setUvcUsageInMins(long uvcUsageInMins) {
        this.uvcUsageInMins = uvcUsageInMins;
    }

    public boolean isUvcAutoOffEnabled() {
        return uvcAutoOffEnabled;
    }

    public void setUvcAutoOffEnabled(boolean uvcAutoOffEnabled) {
        this.uvcAutoOffEnabled = uvcAutoOffEnabled;
    }

    public int getUvcAutoOffTimeInMins() {
        return uvcAutoOffTimeInMins;
    }

    public void setUvcAutoOffTimeInMins(int uvcAutoOffTimeInMins) {
        this.uvcAutoOffTimeInMins = uvcAutoOffTimeInMins;
    }

    public boolean getCloudControl() {
        return connected;
    }

    public boolean isLocalControl() {
        return localControl;
    }

    public void setLocalControl(boolean localControl) {
        this.localControl = localControl;
    }
    public boolean isG2() {
        return firmwareVersion.startsWith("01");
    }

    public boolean isG3() {
        return firmwareVersion.startsWith("02");
    }
    public String getFrCodes() {
        return FrCodes;
    }

    public void setFrCodes(String frCodes) {
        FrCodes = frCodes;
    }
}

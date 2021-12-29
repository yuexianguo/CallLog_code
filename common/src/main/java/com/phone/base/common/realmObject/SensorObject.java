package com.phone.base.common.realmObject;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/12/10
 */
public class SensorObject extends RealmObject {
    //sensor addrsss
    @PrimaryKey
    private int addr;
    private String name;
    private int type;
    private boolean online = false;
    private String fwVer = null;
    //Light state
    //The current read light level of the light sensor
    private int measLvl;
    //The minimum read light level of the light sensor
    private int minMeasLvl;
    //The maximum read light level of the light sensor
    private int maxMeasLvl;
    //config level
    private int targetLvl;
    //The maximum rate of change per minute
    private int changeRate;
    //occState
    //The occScene address that should be activated when only one sensor assigned to this scene are occupied.
    private int occupied;
    //Specifies the delay in seconds before the sensor changes to unoccupied state after the last detected movement.
    private int occToUnoccDelay;
    //Specifies the delay in seconds before the sensor changes to occupied state after the detection of movement.
    private int UnoccToOccDelay;
    private int sensitivity;
    private int range;

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Nullable
    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    @Nullable
    public int getMeasLvl() {
        return measLvl;
    }

    public void setMeasLvl(int measLvl) {
        this.measLvl = measLvl;
    }

    @Nullable
    public int getMinMeasLvl() {
        return minMeasLvl;
    }

    public void setMinMeasLvl(int minMeasLvl) {
        this.minMeasLvl = minMeasLvl;
    }

    @Nullable
    public int getMaxMeasLvl() {
        return maxMeasLvl;
    }

    public void setMaxMeasLvl(int maxMeasLvl) {
        this.maxMeasLvl = maxMeasLvl;
    }

    @Nullable
    public int getTargetLvl() {
        return targetLvl;
    }

    public void setTargetLvl(int targetLvl) {
        this.targetLvl = targetLvl;
    }

    @Nullable
    public int getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(int changeRate) {
        this.changeRate = changeRate;
    }

    @Nullable
    public int getOccToUnoccDelay() {
        return occToUnoccDelay;
    }

    public void setOccToUnoccDelay(int occToUnoccDelay) {
        this.occToUnoccDelay = occToUnoccDelay;
    }

    @Nullable
    public int getUnoccToOccDelay() {
        return UnoccToOccDelay;
    }

    public void setUnoccToOccDelay(int unoccToOccDelay) {
        UnoccToOccDelay = unoccToOccDelay;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getFwVer() {
        return fwVer;
    }

    public void setFwVer(String fwVer) {
        this.fwVer = fwVer;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}

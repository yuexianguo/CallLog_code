package com.phone.base.common.realmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：
 * author : Andy.Guo
 * email : Andy.Guo@waclighting.com.cn
 * date : 2020/12/23
 */
public class AutomationShadow extends RealmObject {
    @PrimaryKey
    private String unionKey;
    private int autoAddr;
    //fixture addr
    private int fixtureAddr;
    private int groupAddr;
    //Single Color Dimmable Light
    private boolean status;
    private int level;
    //more, added for Tunable White Dimmable Light
    private int temperature;
    //more, RGBW Dimmable Light
    private int blue;
    private int green;
    private int red;
    // more, Motorized Trackhead – Single Color Dimmable Light
    private int zoom;
    private int axis;
    private int timeLr;
    private int timeUd;
    private int tilt;
    private int pan;
    private int preset;

    public AutomationShadow() {
    }

    public AutomationShadow(String unionKey, int autoAddr, int fixtureAddr, int groupAddr, boolean status, int level) {
        this.unionKey = unionKey;
        this.autoAddr = autoAddr;
        this.fixtureAddr = fixtureAddr;
        this.groupAddr = groupAddr;
        this.status = status;
        this.level = level;
    }

    public AutomationShadow(String unionKey, int autoAddr, int fixtureAddr, int groupAddr, boolean status, int level, int temperature, int blue, int green, int red, int zoom, int axis, int timeLr, int timeUd, int tilt, int pan) {
        this.unionKey = unionKey;
        this.autoAddr = autoAddr;
        this.fixtureAddr = fixtureAddr;
        this.groupAddr = groupAddr;
        this.status = status;
        this.level = level;
        this.temperature = temperature;
        this.blue = blue;
        this.green = green;
        this.red = red;
        this.zoom = zoom;
        this.axis = axis;
        this.timeLr = timeLr;
        this.timeUd = timeUd;
        this.tilt = tilt;
        this.pan = pan;
    }

    public String getUnionKey() {
        return unionKey;
    }

    public void setUnionKey(String unionKey) {
        this.unionKey = unionKey;
    }

    public int getAutoAddr() {
        return autoAddr;
    }

    public void setAutoAddr(int autoAddr) {
        this.autoAddr = autoAddr;
    }

    public int getFixtureAddr() {
        return fixtureAddr;
    }

    public void setFixtureAddr(int fixtureAddr) {
        this.fixtureAddr = fixtureAddr;
    }

    public int getGroupAddr() {
        return groupAddr;
    }

    public void setGroupAddr(int groupAddr) {
        this.groupAddr = groupAddr;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getAxis() {
        return axis;
    }

    public void setAxis(int axis) {
        this.axis = axis;
    }

    public int getTimeLr() {
        return timeLr;
    }

    public void setTimeLr(int timeLr) {
        this.timeLr = timeLr;
    }

    public int getTimeUd() {
        return timeUd;
    }

    public void setTimeUd(int timeUd) {
        this.timeUd = timeUd;
    }

    public int getTilt() {
        return tilt;
    }

    public void setTilt(int tilt) {
        this.tilt = tilt;
    }

    public int getPan() {
        return pan;
    }

    public void setPan(int pan) {
        this.pan = pan;
    }

    public int getPreset() {
        return preset;
    }

    public void setPreset(int preset) {
        this.preset = preset;
    }
}

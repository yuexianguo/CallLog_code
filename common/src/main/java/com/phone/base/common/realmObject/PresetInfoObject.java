package com.phone.base.common.realmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/5/27
 */
public class PresetInfoObject extends RealmObject {
    @PrimaryKey
    private int position;//1 ~ 4
    private int tilt;
    private int pan;
    private int zoom;
    private int level;
    private boolean status;

    public PresetInfoObject() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

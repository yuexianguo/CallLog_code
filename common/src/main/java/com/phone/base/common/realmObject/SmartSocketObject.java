package com.phone.base.common.realmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2020/12/17
 */
public class SmartSocketObject extends RealmObject {
    @PrimaryKey
    private String mac;
    private String name;
    private String ip;


    public SmartSocketObject() {
    }

    public SmartSocketObject(String mac, String name,String ip) {
        this.mac = mac;
        this.name = name;
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

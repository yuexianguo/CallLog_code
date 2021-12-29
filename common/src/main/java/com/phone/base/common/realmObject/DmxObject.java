package com.phone.base.common.realmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/3/29
 */
public class DmxObject extends RealmObject {
    //dmx address
    @PrimaryKey
    private int addr;
    private RealmList<DmxChannelObject> mapping;
    //Specifies the type of Thing that the mapping is for, //1 = fixture, 2 = group, 3 = automation
    private int type;
    //The address of the Thing. This is an address of a Fixture, a Group, or an Automation
    private int thing;

    public DmxObject() {
    }

    public DmxObject(int addr) {
        this.addr = addr;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public RealmList<DmxChannelObject> getMapping() {
        return mapping;
    }

    public void setMapping(RealmList<DmxChannelObject> mapping) {
        this.mapping = mapping;
    }

    public int getThing() {
        return thing;
    }

    public void setThing(int thing) {
        this.thing = thing;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.phone.base.common.realmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/8/19
 */
public class InputObject extends RealmObject {
    @PrimaryKey
    private int input;
    private int addr; //group addr
    private int addrType;
    private String name;
    private int minLevel;
    private int maxLevel;
    private int incrStep;

    public InputObject() {
    }

    public InputObject(int input, int addrType,int addr, String name) {
        this.input = input;
        this.addrType = addrType;
        this.addr = addr;
        this.name = name;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getIncrStep() {
        return incrStep;
    }

    public void setIncrStep(int incrStep) {
        this.incrStep = incrStep;
    }

    public int getAddrType() {
        return addrType;
    }

    public void setAddrType(int addrType) {
        this.addrType = addrType;
    }
}

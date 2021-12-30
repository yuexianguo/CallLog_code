package com.phone.base.common.realmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2021/3/29
 */
public class DmxChannelObject extends RealmObject {
    //This specifies the value of the DMX channel to use
    @PrimaryKey
    private int channel;
    //The name of the field in the object.
    private String field;
    //1=normal, 2=reversed
    private String input;

    //For automations:
    private int action;

    public DmxChannelObject() {
    }

    public DmxChannelObject(int channel) {
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}

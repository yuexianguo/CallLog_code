package com.phone.base.common.realmObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.phone.base.common.ConstantsKt;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：
 * author : 
 * email : @waclighting.com.cn
 * date : 2020/12/16
 */
public class ScheduleObject extends RealmObject {
    @PrimaryKey
    private int addr;
    //length 32
    private String name = "Schedule 1"; //default Schedule 1
    //format 24 hour time, like T14:30
    //format sunrise or sunset, like sunrise+T00:15 or sunset-T00:15, max +_ 3 hours.
    private String time;
    //will be activated auto address
    private int automation;
    //"O","A","M|T|W|R|F|S|U", "H:XX/XX,XX/XX"
    private String type;
    //enable or disable the schedule
    private boolean enabled = true;

    private int triggerAction;
    // data saving time
    private long timestamp;

    public ScheduleObject() {
    }

    public ScheduleObject(int addr, String name, String time, int automation, String type, boolean enabled, int triggerAction) {
        this.addr = addr;
        this.name = name;
        this.time = time;
        this.automation = automation;
        this.type = type;
        this.enabled = enabled;
        this.triggerAction = triggerAction;
    }

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

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @NonNull
    public int getAutomation() {
        return automation;
    }

    public void setAutomation(int automation) {
        this.automation = automation;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSunRise () {
        return time.contains("sunrise");
    }

    public boolean isSunSet () {
        return time.contains("sunset");
    }

    public int getTriggerAction() {
        return triggerAction;
    }

    public void setTriggerAction(int triggerAction) {
        this.triggerAction = triggerAction;
    }

    public boolean isDirty() {
        return System.currentTimeMillis() - timestamp >= ConstantsKt.TIME_DIRTY;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

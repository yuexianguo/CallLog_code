package com.phone.base.common.realmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2020/12/23
 */
public class AutomationObject extends RealmObject {
    @PrimaryKey
    //automation addr
    private int addr;
    private String name;
    //1 = standard, 2 = occupancy, 3 = daylight
    private int type;
    private boolean enabled;
    //standard
    private RealmList<AutomationShadow> fixture;
    private RealmList<AutomationShadow> group;
    //occupy
    private RealmList<Integer> occSensors;
    private RealmList<AutomationShadow> fixtWhenOcc;
    private RealmList<AutomationShadow> grpWhenOcc;
    private RealmList<AutomationShadow> fixtWhenUnocc;
    private RealmList<AutomationShadow> grpWhenUnocc;
    private RealmList<AutomationShadow> fixtWhenDisabled;
    private RealmList<AutomationShadow> grpWhenDisabled;
    //light
    private int lightSensor;
    private int daylightGroup;
    private int minTargetLevel;
    private int maxTargetLevel;
    private boolean isFavorite = false;
    private String bgName;

    public AutomationObject() {
    }

    public AutomationObject(int addr, String name, int type, boolean enabled) {
        this.addr = addr;
        this.name = name;
        this.type = type;
        this.enabled = enabled;
    }

    public AutomationObject(int addr, String name, int type, boolean enabled, RealmList<AutomationShadow> fixture, RealmList<AutomationShadow> group, RealmList<Integer> occSensors, RealmList<AutomationShadow> fixtWhenOcc, RealmList<AutomationShadow> grpWhenOcc, RealmList<AutomationShadow> fixtWhenUnocc, RealmList<AutomationShadow> grpWhenUnocc, RealmList<AutomationShadow> fixtWhenDisabled, RealmList<AutomationShadow> grpWhenDisabled, int lightSensor, int daylightGroup, int minTargetLevel, int maxTargetLevel) {
        this.addr = addr;
        this.name = name;
        this.type = type;
        this.enabled = enabled;
        this.fixture = fixture;
        this.group = group;
        this.occSensors = occSensors;
        this.fixtWhenOcc = fixtWhenOcc;
        this.grpWhenOcc = grpWhenOcc;
        this.fixtWhenUnocc = fixtWhenUnocc;
        this.grpWhenUnocc = grpWhenUnocc;
        this.fixtWhenDisabled = fixtWhenDisabled;
        this.grpWhenDisabled = grpWhenDisabled;
        this.lightSensor = lightSensor;
        this.daylightGroup = daylightGroup;
        this.minTargetLevel = minTargetLevel;
        this.maxTargetLevel = maxTargetLevel;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RealmList<AutomationShadow> getFixture() {
        return fixture;
    }

    public void setFixture(RealmList<AutomationShadow> fixture) {
        this.fixture = fixture;
    }

    public RealmList<AutomationShadow> getGroup() {
        return group;
    }

    public void setGroup(RealmList<AutomationShadow> group) {
        this.group = group;
    }

    public RealmList<Integer> getOccSensors() {
        return occSensors;
    }

    public void setOccSensors(RealmList<Integer> occSensors) {
        this.occSensors = occSensors;
    }

    public RealmList<AutomationShadow> getFixtWhenOcc() {
        return fixtWhenOcc;
    }

    public void setFixtWhenOcc(RealmList<AutomationShadow> fixtWhenOcc) {
        this.fixtWhenOcc = fixtWhenOcc;
    }

    public RealmList<AutomationShadow> getGrpWhenOcc() {
        return grpWhenOcc;
    }

    public void setGrpWhenOcc(RealmList<AutomationShadow> grpWhenOcc) {
        this.grpWhenOcc = grpWhenOcc;
    }

    public RealmList<AutomationShadow> getFixtWhenUnocc() {
        return fixtWhenUnocc;
    }

    public void setFixtWhenUnocc(RealmList<AutomationShadow> fixtWhenUnocc) {
        this.fixtWhenUnocc = fixtWhenUnocc;
    }

    public RealmList<AutomationShadow> getGrpWhenUnocc() {
        return grpWhenUnocc;
    }

    public void setGrpWhenUnocc(RealmList<AutomationShadow> grpWhenUnocc) {
        this.grpWhenUnocc = grpWhenUnocc;
    }

    public RealmList<AutomationShadow> getFixtWhenDisabled() {
        return fixtWhenDisabled;
    }

    public void setFixtWhenDisabled(RealmList<AutomationShadow> fixtWhenDisabled) {
        this.fixtWhenDisabled = fixtWhenDisabled;
    }

    public RealmList<AutomationShadow> getGrpWhenDisabled() {
        return grpWhenDisabled;
    }

    public void setGrpWhenDisabled(RealmList<AutomationShadow> grpWhenDisabled) {
        this.grpWhenDisabled = grpWhenDisabled;
    }

    public int getLightSensor() {
        return lightSensor;
    }

    public void setLightSensor(int lightSensor) {
        this.lightSensor = lightSensor;
    }

    public int getDaylightGroup() {
        return daylightGroup;
    }

    public void setDaylightGroup(int daylightGroup) {
        this.daylightGroup = daylightGroup;
    }

    public int getMinTargetLevel() {
        return minTargetLevel;
    }

    public void setMinTargetLevel(int minTargetLevel) {
        this.minTargetLevel = minTargetLevel;
    }

    public int getMaxTargetLevel() {
        return maxTargetLevel;
    }

    public void setMaxTargetLevel(int maxTargetLevel) {
        this.maxTargetLevel = maxTargetLevel;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getBgName() {
        return bgName;
    }

    public void setBgName(String bgName) {
        this.bgName = bgName;
    }
}

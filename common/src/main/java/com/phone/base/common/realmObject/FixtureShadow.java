package com.phone.base.common.realmObject;

import androidx.annotation.Nullable;

import com.phone.base.common.data.bean.FixtureDetail;
import com.phone.base.common.data.bean.FixtureState;
import com.phone.base.common.data.bean.FixtureTune;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * {
 * "action": 3,
 * "result": "0",
 * "name": "New Fixture 100663300",
 * "type": 2,
 * "state": {
 * "red": 16379,
 * "green": 43368,
 * "blue": 16379,
 * "level": 26985,
 * "temperature": 0,
 * "status": true
 * "online":false
 * },
 * "detail": {
 * "currentLevel": 26985,
 * "factory": 1,
 * "busVer": "69.69",
 * "ledDriver": "iiiiiiiiiiiiiiiiii",
 * "fwVer": "69.69",
 * "dateCode": "6969-69-69",
 * "currentOutput": 26985,
 * "model": "iiiiiiiiiiiiiiiiii",
 * "pcbVer": "26985"
 * },
 * "tune": {
 * "onRate": 26985,
 * "offRate": 26985,
 * "dimmingCurve": 105
 * }
 * }
 */
public class FixtureShadow extends RealmObject {
    @PrimaryKey
    private int addr;
    //state start
    private boolean status;
    private int level;
    private boolean online;
    //state more, added for Tunable White Dimmable Light
    private int temperature;
    //state more, RGBW Dimmable Light
    private int blue;
    private int green;
    private int red;
    //state more, Motorized Trackhead – Single Color Dimmable Light
    private int zoom;//spot,0—10000，
    private int axis;//16 right,32 left,64 down,128 up
    private int timeLr;
    private int timeUd;
    private int tilt;//0 — 180,0 is ceiling,180 is floor.
    private int pan;// rota，0 — 359，
    private int upDownCircleTime;//
    private int leftRightCircleTime;// rota，0 — 359，

    //state end
    //tune start
    private int dimmingCurve;
    private int offRate;
    private int onRate;
    //tune end
    //details start
    private String busVer;
    private int currentLevel;
    private int currentOutput;
    private String dateCode;
    private int factory;
    private String fwVer;
    private String ledDriver;
    private String model;
    private String pcbVer;
    //detail more, added for Tunable White Dimmable Light
    private String schemVer;
    private RealmList<PresetInfoObject> presetInfo;
    //details end

    public FixtureShadow() {
    }

    public FixtureShadow(int addr) {
        this.addr = addr;
    }

//    /**
//     * base state
//     *
//     * @param addr
//     * @param fixtureState
//     */
//    public FixtureShadow(int addr) {
//        this.addr = addr;
////        setFixtureState(fixtureState);
//    }

    public void setFixtureState(FixtureState fixtureState) {
        this.status = fixtureState.getStatus();
        this.level = fixtureState.getLevel();
        this.online = fixtureState.getOnline();
        this.temperature = fixtureState.getTemperature();
        this.blue = fixtureState.getBlue();
        this.green = fixtureState.getGreen();
        this.red = fixtureState.getRed();
        this.zoom = fixtureState.getZoom();
        this.axis = fixtureState.getAxis();
        this.timeLr = fixtureState.getTimeLr();
        this.timeUd = fixtureState.getTimeUd();
        this.tilt = fixtureState.getTilt();
        this.pan = fixtureState.getPan() == null?0:fixtureState.getPan();
    }

    public FixtureState getFixtureState() {
        return new FixtureState(this.status, this.level, this.online, this.temperature, this.blue, this.green,
                this.red, this.zoom, this.axis, this.timeLr, this.timeUd, this.tilt, this.pan);
    }

    public void setFixtureTune(FixtureTune fixtureTune) {
        this.dimmingCurve = fixtureTune.getDimmingCurve();
        this.offRate = fixtureTune.getOffRate();
        this.onRate = fixtureTune.getOnRate();
        this.presetInfo = fixtureTune.getPresetInfo();
    }

    public void setFixtureDetails(FixtureDetail fixtureDetails) {
        this.busVer = fixtureDetails.getBusVer();
        this.currentLevel = fixtureDetails.getCurrentLevel();
        this.currentOutput = fixtureDetails.getCurrentOutput();
        this.dateCode = fixtureDetails.getDateCode();
        this.factory = fixtureDetails.getFactory();
        this.fwVer = fixtureDetails.getFwVer();
        this.ledDriver = fixtureDetails.getLedDriver();
        this.model = fixtureDetails.getModel();
        this.pcbVer = fixtureDetails.getPcbVer();
        this.schemVer = fixtureDetails.getSchemVer();
        this.upDownCircleTime = fixtureDetails.getUpDownCircleTime();
        this.leftRightCircleTime = fixtureDetails.getLeftRightCircleTime();
    }

    public FixtureDetail getFixtureDetails() {
        return new FixtureDetail(this.busVer, this.currentLevel, this.currentOutput, this.dateCode,
                this.factory, this.fwVer, this.ledDriver, this.model, this.pcbVer, this.schemVer, this.upDownCircleTime, this.leftRightCircleTime);
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public boolean getStatus() {
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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
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

    public int getDimmingCurve() {
        return dimmingCurve;
    }

    public void setDimmingCurve(int dimmingCurve) {
        this.dimmingCurve = dimmingCurve;
    }

    public int getOffRate() {
        return offRate;
    }

    public void setOffRate(int offRate) {
        this.offRate = offRate;
    }

    public int getOnRate() {
        return onRate;
    }

    public void setOnRate(int onRate) {
        this.onRate = onRate;
    }

    public String getBusVer() {
        return busVer;
    }

    public void setBusVer(String busVer) {
        this.busVer = busVer;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentOutput() {
        return currentOutput;
    }

    public void setCurrentOutput(int currentOutput) {
        this.currentOutput = currentOutput;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public int getFactory() {
        return factory;
    }

    public void setFactory(int factory) {
        this.factory = factory;
    }

    public String getFwVer() {
        return fwVer;
    }

    public void setFwVer(String fwVer) {
        this.fwVer = fwVer;
    }

    public String getLedDriver() {
        return ledDriver;
    }

    public void setLedDriver(String ledDriver) {
        this.ledDriver = ledDriver;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPcbVer() {
        return pcbVer;
    }

    public void setPcbVer(String pcbVer) {
        this.pcbVer = pcbVer;
    }

    public String getSchemVer() {
        return schemVer;
    }

    public void setSchemVer(String schemVer) {
        this.schemVer = schemVer;
    }

    public boolean isStatus() {
        return status;
    }

    public RealmList<PresetInfoObject> getPresetInfo() {
        return presetInfo;
    }

    public void setPresetInfo(RealmList<PresetInfoObject> presetInfo) {
        this.presetInfo = presetInfo;
    }

    public int getUpDownCircleTime() {
        return upDownCircleTime;
    }

    public void setUpDownCircleTime(int upDownCircleTime) {
        this.upDownCircleTime = upDownCircleTime;
    }

    public int getLeftRightCircleTime() {
        return leftRightCircleTime;
    }

    public void setLeftRightCircleTime(int leftRightCircleTime) {
        this.leftRightCircleTime = leftRightCircleTime;
    }
}

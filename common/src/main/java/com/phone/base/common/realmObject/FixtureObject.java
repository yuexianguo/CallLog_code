package com.phone.base.common.realmObject;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
four type
1.Single Color Dimmable Light  0
2.Tunable White Dimmable Light  1
3.RGBW Dimmable Light  2
4.Motorized Trackhead – Single Color Dimmable Light  3
*/
public class FixtureObject extends RealmObject {
    /**
     * addr : fixture_addr1
     * name : example
     * type : 0
     */
    @PrimaryKey
    private int addr;
    private int action;
    private String result;
    private String name;
    private int type;
    private FixtureShadow shadow;
    private boolean isFavorite;
    private String bgName;

    public FixtureObject() {
    }

    public FixtureObject(int action, String result, int addr, String name, int type, FixtureShadow shadow) {
        this.action = action;
        this.result = result;
        this.addr = addr;
        this.name = name;
        this.type = type;
        this.shadow = shadow;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public FixtureShadow getShadow() {
        return shadow;
    }

    public void setShadow(FixtureShadow shadow) {
        this.shadow = shadow;
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

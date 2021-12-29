package com.phone.base.common.realmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GroupObject extends RealmObject {
    @PrimaryKey
    //group address
    private int addr;
    private int action;
    private String result;
    private String name;
    private RealmList<FixtureObject> listFixtures;
    private boolean isFavorite = false;
    private String bgName;

    public GroupObject() {
    }

    public GroupObject(int action, String result, int addr, String name) {
        this.action = action;
        this.result = result;
        this.addr = addr;
        this.name = name;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public RealmList<FixtureObject> getListFixtures() {
        return listFixtures;
    }

    public void setListFixtures(RealmList<FixtureObject> listFixtures) {
        this.listFixtures = listFixtures;
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

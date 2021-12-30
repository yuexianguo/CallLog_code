package com.phone.base.common.realmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：Used to save local information of the brain
 * author : 
 * email : @waclighting.com.cn
 * date : 2021/1/27
 */
public class LocalBrainObject extends RealmObject {

    @PrimaryKey
    private String staMac;

    private RealmList<Integer> favoriteFixtures;

    private RealmList<Integer> favoriteGroups;

    private RealmList<Integer> favoriteAutos;

    public LocalBrainObject() {

    }

    public LocalBrainObject(String staMac) {
        this.staMac = staMac;
    }

    public String getStaMac() {
        return staMac;
    }

    public void setStaMac(String staMac) {
        this.staMac = staMac;
    }

    public RealmList<Integer> getFavoriteFixtures() {
        return favoriteFixtures;
    }

    public void setFavoriteFixtures(RealmList<Integer> favoriteFixtures) {
        this.favoriteFixtures = favoriteFixtures;
    }

    public RealmList<Integer> getFavoriteGroups() {
        return favoriteGroups;
    }

    public void setFavoriteGroups(RealmList<Integer> favoriteGroups) {
        this.favoriteGroups = favoriteGroups;
    }

    public RealmList<Integer> getFavoriteAutos() {
        return favoriteAutos;
    }

    public void setFavoriteAutos(RealmList<Integer> favoriteAutos) {
        this.favoriteAutos = favoriteAutos;
    }

}

package com.phone.base.common.realmObject;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description ：Used to save local information of the account
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2021/1/27
 */
public class LocalDataObject extends RealmObject {

    @PrimaryKey
    private String userId;
    private Long timeStamp;
    private RealmList<LocalBrainObject> listBrainInfo;
    private String currentMac;

    public LocalDataObject() {

    }

    public LocalDataObject(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RealmList<LocalBrainObject> getListBrainInfo() {
        return listBrainInfo;
    }

    public void setListBrainInfo(RealmList<LocalBrainObject> brainInfoList) {
        this.listBrainInfo = brainInfoList;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCurrentMac() {
        return currentMac;
    }

    public void setCurrentMac(String currentMac) {
        this.currentMac = currentMac;
    }
}

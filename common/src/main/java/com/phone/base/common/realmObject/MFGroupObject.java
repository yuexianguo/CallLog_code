package com.phone.base.common.realmObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/8/5
 */
public class MFGroupObject extends RealmObject {
    @PrimaryKey
    private String groupId;
    private String email;
    private String groupName;
    private String bucketKey; //cloud image uri
    private String objectKey; //cloud image name
    private RealmList<MFDeviceObject> devices;
    private String bgName;

    public MFGroupObject() {
    }

    public MFGroupObject(String groupId, String email, String groupName, String bucketKey, String objectKey, RealmList<MFDeviceObject> devices) {
        this.groupId = groupId;
        this.email = email;
        this.groupName = groupName;
        this.bucketKey = bucketKey;
        this.objectKey = objectKey;
        this.devices = devices;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBucketKey() {
        return bucketKey;
    }

    public void setBucketKey(String bucketKey) {
        this.bucketKey = bucketKey;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public RealmList<MFDeviceObject> getDevices() {
        return devices;
    }

    public void setDevices(RealmList<MFDeviceObject> devices) {
        this.devices = devices;
    }

    public String getBgName() {
        return bgName;
    }

    public void setBgName(String bgName) {
        this.bgName = bgName;
    }
}

package com.phone.base.common.realmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/8/5
 */
public class MFDeviceObject extends RealmObject {
    @PrimaryKey
    private String clientId;
    private String deviceName;
    private String email;
    private Boolean enabled;
    private String federatedIdentity;
    private Boolean shared; //true-> was a device shared from others
    private String bucketKey;
    private String objectKey;
    private Boolean isLocalOnly;
    private String bgName;
    private String brand; //1->mf , 2-> wac

    public MFDeviceObject() {
    }

    public MFDeviceObject(String clientId, String deviceName, String email, Boolean enabled, String federatedIdentity, Boolean shared, String bucketKey, String objectKey) {
        this.clientId = clientId;
        this.deviceName = deviceName;
        this.email = email;
        this.enabled = enabled;
        this.federatedIdentity = federatedIdentity;
        this.shared = shared;
        this.bucketKey = bucketKey;
        this.objectKey = objectKey;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFederatedIdentity() {
        return federatedIdentity;
    }

    public void setFederatedIdentity(String federatedIdentity) {
        this.federatedIdentity = federatedIdentity;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
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

    public Boolean getLocalOnly() {
        return isLocalOnly;
    }

    public void setLocalOnly(Boolean localOnly) {
        isLocalOnly = localOnly;
    }

    public String getBgName() {
        return bgName;
    }

    public void setBgName(String bgName) {
        this.bgName = bgName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

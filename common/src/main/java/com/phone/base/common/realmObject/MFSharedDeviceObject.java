package com.phone.base.common.realmObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kc on 5/21/18.
 * Modified by BZ on 04/01/2019
 */

public class MFSharedDeviceObject extends RealmObject {
    public String clientId;
    public Boolean activated;
    public String code;
    public String federatedIdentity;
    public String recipient;
    public String recipientName;
    public String recipientUsername;
    public String sender;
    public String senderName;
    public Boolean sharingEnabled;
    @PrimaryKey
    public String clientIdAndRecipient;

    public MFSharedDeviceObject(String clientId, Boolean activated, String code, String federatedIdentity, String recipient, String recipientName, String sender, String senderName, Boolean sharingEnabled, String recipientUsername) {
        this.clientId = clientId;
        this.activated = activated;
        this.code = code;
        this.federatedIdentity = federatedIdentity;
        this.recipient = recipient;
        this.recipientName = recipientName;
        this.sender = sender;
        this.senderName = senderName;
        this.sharingEnabled = sharingEnabled;
        this.recipientUsername = recipientUsername;
        this.clientIdAndRecipient = clientId + "|" + recipient;
    }

    public MFSharedDeviceObject() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFederatedIdentity() {
        return federatedIdentity;
    }

    public void setFederatedIdentity(String federatedIdentity) {
        this.federatedIdentity = federatedIdentity;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Boolean getSharingEnabled() {
        return sharingEnabled;
    }

    public void setSharingEnabled(Boolean sharingEnabled) {
        this.sharingEnabled = sharingEnabled;
    }

    public String getClientIdAndRecipient() {
        return clientIdAndRecipient;
    }

    public void setClientIdAndRecipient(String clientIdAndRecipient) {
        this.clientIdAndRecipient = clientIdAndRecipient;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

}

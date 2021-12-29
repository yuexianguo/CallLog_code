package com.phone.base.common.lamdaFunction.bean;

/**
 * Created by kc on 5/11/18.
 */

public class ValidateUserRequest {
    String email;
    String provider;
    String federatedIdentity;
    String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getFederatedIdentity() {
        return federatedIdentity;
    }

    public void setFederatedIdentity(String federatedIdentity) {
        this.federatedIdentity = federatedIdentity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValidateUserRequest(String email, String provider, String federatedIdentity, String name) {
        this.email = email;
        this.provider = provider;
        this.federatedIdentity = federatedIdentity;
        this.name = name;
    }


}

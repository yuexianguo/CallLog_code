package com.phone.base.common.lamdaFunction.bean;

/**
 * Created by kc on 5/11/18.
 */

public class ValidateUserResponse {
    String message;
    UserObject user;

    public String getMessage() {
        return message;
    }

    public UserObject getUser() {
        return user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }

    public ValidateUserResponse(String message, UserObject user) {
        this.message = message;
        this.user = user;
    }

    public ValidateUserResponse() {
    }

    public static class UserObject {
        String email;
        String federatedIdentity;
        String name;
        String provider;

        public String getEmail() {
            return email;
        }

        public String getFederatedIdentity() {
            return federatedIdentity;
        }

        public String getName() {
            return name;
        }

        public String getProvider() {
            return provider;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFederatedIdentity(String federatedIdentity) {
            this.federatedIdentity = federatedIdentity;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public UserObject() {
        }

        public UserObject(String email, String federatedIdentity, String name, String provider) {
            this.email = email;
            this.federatedIdentity = federatedIdentity;
            this.name = name;
            this.provider = provider;
        }

    }

}

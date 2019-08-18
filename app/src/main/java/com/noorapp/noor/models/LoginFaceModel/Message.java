package com.noorapp.noor.models.LoginFaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {
    @SerializedName("username")
    @Expose
    private List<String> username = null;
    @SerializedName("facebook")
    @Expose
    private List<String> facebook = null;
    @SerializedName("gender")
    @Expose
    private List<String> gender = null;
    @SerializedName("device_token")
    @Expose
    private List<String> deviceToken = null;

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getFacebook() {
        return facebook;
    }

    public void setFacebook(List<String> facebook) {
        this.facebook = facebook;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public List<String> getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(List<String> deviceToken) {
        this.deviceToken = deviceToken;
    }
    @SerializedName("errorInfo")
    @Expose
    private List<String> errorInfo = null;

    public List<String> getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(List<String> errorInfo) {
        this.errorInfo = errorInfo;
    }
}

package com.noorapp.noor.models.LoginGoogleLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleLoginResponse {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("message")
    @Expose
    private Message message;
    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

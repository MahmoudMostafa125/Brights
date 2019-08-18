package com.noorapp.noor.models.ProfileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProfileResponse {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("profile")
    @Expose
    private List<Profile> profile = null;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public List<Profile> getProfile() {
        return profile;
    }

    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }
}

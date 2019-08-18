package com.noorapp.noor.models.ContactusModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactusResponse {
    @SerializedName("response")
    @Expose
    private Boolean response;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }
}

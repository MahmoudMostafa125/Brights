package com.noorapp.noor.models.PaymentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayTokenResponse {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("clientToken")
    @Expose
    private String clientToken;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
}

package com.noorapp.noor.models.TransplaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class transresponse {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("trans_places")
    @Expose
    private List<TransPlace> transPlaces = null;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<TransPlace> getTransPlaces() {
        return transPlaces;
    }

    public void setTransPlaces(List<TransPlace> transPlaces) {
        this.transPlaces = transPlaces;
    }
}

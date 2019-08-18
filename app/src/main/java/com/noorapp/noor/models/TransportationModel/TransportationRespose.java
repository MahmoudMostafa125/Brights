package com.noorapp.noor.models.TransportationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransportationRespose {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("transportations")
    @Expose
    private List<Transportation> transportations = null;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Transportation> getTransportations() {
        return transportations;
    }

    public void setTransportations(List<Transportation> transportations) {
        this.transportations = transportations;
    }

}

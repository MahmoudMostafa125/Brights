package com.noorapp.noor.models.PlacesDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceDetailsResponse {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("place")
    @Expose
    private List<Place> place = null;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Place> getPlace() {
        return place;
    }

    public void setPlace(List<Place> place) {
        this.place = place;
    }
}

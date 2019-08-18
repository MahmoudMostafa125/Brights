package com.noorapp.noor.models.TripModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripResponse {
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("trips")
    @Expose
    private List<Trip> trips = null;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

}

package com.noorapp.noor.models.ReservationsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationsResponse {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("reservations")
    @Expose
    private Reservations reservations;

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

    public Reservations getReservations() {
        return reservations;
    }

    public void setReservations(Reservations reservations) {
        this.reservations = reservations;
    }
}

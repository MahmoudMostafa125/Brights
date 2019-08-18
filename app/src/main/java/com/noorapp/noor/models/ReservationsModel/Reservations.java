package com.noorapp.noor.models.ReservationsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.noorapp.noor.models.ReservationsModel.Place;
import com.noorapp.noor.models.TransportationModel.Trip;

import java.util.List;


public class Reservations {


    @SerializedName("places")
    @Expose
    private List<Place> places = null;
    @SerializedName("trips")
    @Expose
    private List<com.noorapp.noor.models.ReservationsModel.Trip> trips = null;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<com.noorapp.noor.models.ReservationsModel.Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<com.noorapp.noor.models.ReservationsModel.Trip> trips) {
        this.trips = trips;
    }
}

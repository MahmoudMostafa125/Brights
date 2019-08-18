package com.noorapp.noor.models.FavouriteModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.noorapp.noor.models.FavouriteModel.Place;

import java.util.List;

public class Favorites {

    @SerializedName("places")
    @Expose
    private List<Place> places = null;
    @SerializedName("Transportations")
    @Expose
    private List<Transportation> transportations = null;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Transportation> getTransportations() {
        return transportations;
    }

    public void setTransportations(List<Transportation> transportations) {
        this.transportations = transportations;
    }
}

package com.noorapp.noor.models.TransportationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transportation {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("arrangement")
    @Expose
    private String arrangement;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;
    @SerializedName("trips")
    @Expose
    private List<Trip> trips = null;
    @SerializedName("images")
    @Expose
    private List<Image_> images = null;
    @SerializedName("videos")
    @Expose
    private List<Object> videos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Image_> getImages() {
        return images;
    }

    public void setImages(List<Image_> images) {
        this.images = images;
    }

    public List<Object> getVideos() {
        return videos;
    }

    public void setVideos(List<Object> videos) {
        this.videos = videos;
    }

}

package com.noorapp.noor.models.TripModel;

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
    private List<Translation____> translations = null;

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

    public List<Translation____> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation____> translations) {
        this.translations = translations;
    }

}

package com.noorapp.noor.models.GuideModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GuideResponse {

    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("guide")
    @Expose
    private List<Guide> guide = null;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Guide> getGuide() {
        return guide;
    }

    public void setGuide(List<Guide> guide) {
        this.guide = guide;
    }

}

package com.noorapp.noor.models.GuideModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation__ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("language_code")
    @Expose
    private String languageCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

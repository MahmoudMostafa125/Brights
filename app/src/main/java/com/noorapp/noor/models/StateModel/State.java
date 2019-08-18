package com.noorapp.noor.models.StateModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class State {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("phone_code")
    @Expose
    private String phoneCode;
    @SerializedName("phone_length")
    @Expose
    private Integer phoneLength;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;
    @SerializedName("areas")
    @Expose
    private List<Object> areas = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public Integer getPhoneLength() {
        return phoneLength;
    }

    public void setPhoneLength(Integer phoneLength) {
        this.phoneLength = phoneLength;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Object> getAreas() {
        return areas;
    }

    public void setAreas(List<Object> areas) {
        this.areas = areas;
    }
}

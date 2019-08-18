package com.noorapp.noor.models.CityModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class State {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("phone_code")
    @Expose
    private Object phoneCode;
    @SerializedName("phone_length")
    @Expose
    private String phoneLength;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("translations")
    @Expose
    private List<Translation__> translations = null;
    @SerializedName("areas")
    @Expose
    private List<Area> areas = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Object phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneLength() {
        return phoneLength;
    }

    public void setPhoneLength(String phoneLength) {
        this.phoneLength = phoneLength;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Translation__> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation__> translations) {
        this.translations = translations;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

}

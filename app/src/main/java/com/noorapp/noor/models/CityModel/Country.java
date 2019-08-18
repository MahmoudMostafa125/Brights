package com.noorapp.noor.models.CityModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("iso")
    @Expose
    private String iso;
    @SerializedName("phone_code")
    @Expose
    private String phoneCode;
    @SerializedName("phone_length")
    @Expose
    private String phoneLength;
    @SerializedName("mobile_length")
    @Expose
    private String mobileLength;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;
    @SerializedName("cites")
    @Expose
    private List<Cite> cites = null;
    @SerializedName("currencies")
    @Expose
    private List<Currency> currencies = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneLength() {
        return phoneLength;
    }

    public void setPhoneLength(String phoneLength) {
        this.phoneLength = phoneLength;
    }

    public String getMobileLength() {
        return mobileLength;
    }

    public void setMobileLength(String mobileLength) {
        this.mobileLength = mobileLength;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public List<Cite> getCites() {
        return cites;
    }

    public void setCites(List<Cite> cites) {
        this.cites = cites;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}

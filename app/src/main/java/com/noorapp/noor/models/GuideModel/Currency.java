package com.noorapp.noor.models.GuideModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Currency {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("country_id")
    @Expose
    private String countryId;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
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

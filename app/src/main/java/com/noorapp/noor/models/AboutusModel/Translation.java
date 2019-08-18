package com.noorapp.noor.models.AboutusModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("info_id")
    @Expose
    private String infoId;
    @SerializedName("language_code")
    @Expose
    private String languageCode;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("status")
    @Expose
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

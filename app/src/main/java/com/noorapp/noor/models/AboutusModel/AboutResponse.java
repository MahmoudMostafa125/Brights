package com.noorapp.noor.models.AboutusModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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
}

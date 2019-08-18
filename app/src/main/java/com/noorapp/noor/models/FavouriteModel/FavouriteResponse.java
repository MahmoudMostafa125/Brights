package com.noorapp.noor.models.FavouriteModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouriteResponse {
    @SerializedName("response")
    @Expose
    private Boolean response;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("favorites")
    @Expose
    private Favorites favorites;

    @SerializedName("error")
    @Expose
    private String error;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }
}

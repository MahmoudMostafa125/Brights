package com.noorapp.noor.models.AddFavouriteModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  AddFavouriteResponse {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("favorites_count")
    @Expose
    private Integer favoritesCount;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }
}

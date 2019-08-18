package com.noorapp.noor.models.FavouriteModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.noorapp.noor.models.GuideModel.City;
import com.noorapp.noor.models.GuideModel.Country;
import com.noorapp.noor.models.GuideModel.Currency;
import com.noorapp.noor.models.GuideModel.Guide;
import com.noorapp.noor.models.GuideModel.Image;
import com.noorapp.noor.models.GuideModel.Review;
import com.noorapp.noor.models.GuideModel.Ticket;
import com.noorapp.noor.models.GuideModel.Translation;

import java.util.List;

public class Place_ {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("arrangement")
    @Expose
    private String arrangement;
    @SerializedName("guide_id")
    @Expose
    private String guideId;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("state_id")
    @Expose
    private Object stateId;
    @SerializedName("area_id")
    @Expose
    private Object areaId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("open")
    @Expose
    private String open;
    @SerializedName("close")
    @Expose
    private String close;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("old_price")
    @Expose
    private String oldPrice;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("best")
    @Expose
    private String best;
    @SerializedName("tickets")
    @Expose
    private List<Ticket> tickets = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bought")
    @Expose
    private String bought;
    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;
    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("area")
    @Expose
    private Object area;
    @SerializedName("currency")
    @Expose
    private Currency currency;
    @SerializedName("guide")
    @Expose
    private List<Guide> guide = null;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("videos")
    @Expose
    private List<Object> videos = null;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public String getGuideId() {
        return guideId;
    }

    public void setGuideId(String guideId) {
        this.guideId = guideId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }

    public Object getAreaId() {
        return areaId;
    }

    public void setAreaId(Object areaId) {
        this.areaId = areaId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBought() {
        return bought;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<Guide> getGuide() {
        return guide;
    }

    public void setGuide(List<Guide> guide) {
        this.guide = guide;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Object> getVideos() {
        return videos;
    }

    public void setVideos(List<Object> videos) {
        this.videos = videos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}


package com.noorapp.noor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("old_price")
  @Expose
  private String oldPrice;
  @SerializedName("price")
  @Expose
  private String price;
  @SerializedName("cover")
  @Expose
  private String cover;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @SerializedName("available")
  @Expose
  private String available;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("type")
  @Expose
  private String type;
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getAvailable() {
    return available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

}
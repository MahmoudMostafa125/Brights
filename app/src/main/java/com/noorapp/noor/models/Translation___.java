
package com.noorapp.noor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation___ {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("currency_id")
  @Expose
  private String currencyId;
  @SerializedName("language_code")
  @Expose
  private String languageCode;
  @SerializedName("iso")
  @Expose
  private String iso;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("status")
  @Expose
  private String status;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
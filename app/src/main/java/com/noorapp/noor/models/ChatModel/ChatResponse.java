package com.noorapp.noor.models.ChatModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatResponse {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("chat_id")
    @Expose
    private String chatId;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

}

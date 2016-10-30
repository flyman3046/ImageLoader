package com.example.flyman3046.imageloader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("message")
    @Expose
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}

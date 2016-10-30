package com.example.flyman3046.imageloader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img {

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("contentType")
    @Expose
    private String contentType;

    public String getContentType() {
        return contentType;
    }

    public void getContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     *
     * @return
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }
}

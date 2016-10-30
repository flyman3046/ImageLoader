package com.example.flyman3046.imageloader.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private List<Byte> data = new ArrayList<>();

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Byte> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Byte> data) {
        this.data = data;
    }

}

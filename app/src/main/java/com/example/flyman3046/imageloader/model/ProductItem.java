package com.example.flyman3046.imageloader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductItem {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("img")
    @Expose
    private Img img;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The v
     */
    public Integer getV() {
        return v;
    }

    /**
     *
     * @param v
     * The __v
     */
    public void setV(Integer v) {
        this.v = v;
    }

    /**
     *
     * @return
     * The img
     */
    public Img getImg() {
        return img;
    }

    /**
     *
     * @param img
     * The img
     */
    public void setImg(Img img) {
        this.img = img;
    }

}

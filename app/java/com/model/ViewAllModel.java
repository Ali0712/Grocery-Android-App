package com.example.yourmart.model;

import java.io.Serializable;

public class ViewAllModel implements Serializable {
    String name;
    String description;
    int price;
    String img_url;
    String type;

    public ViewAllModel() {
    }

    public ViewAllModel(String name, String description, int price, String img_url, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.img_url = img_url;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

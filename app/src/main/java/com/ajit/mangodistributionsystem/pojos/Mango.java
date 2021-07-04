package com.ajit.mangodistributionsystem.pojos;

import java.io.Serializable;

public class Mango implements Serializable {

    private String mangoName;
    private String price;
    private String quantity;
    private String description;
    private String imageUrl;

    public Mango() {
    }

    public Mango(String mangoName, String price, String quantity, String description, String imageUrl) {
        this.mangoName = mangoName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getMangoName() {
        return mangoName;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Mango{" +
                "mangoName='" + mangoName + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
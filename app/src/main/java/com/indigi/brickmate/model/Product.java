package com.indigi.brickmate.model;

public class Product {

    private String id;
    private String particular;
    private String description;
    private String unit;
    private String price;
    private String gST;


    public Product() {
    }

    public Product(String id, String particular, String description, String unit, String price, String gST) {
        this.id = id;
        this.particular = particular;
        this.description = description;
        this.unit = unit;
        this.price = price;
        this.gST = gST;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getgST() {
        return gST;
    }

    public void setgST(String gST) {
        this.gST = gST;
    }
}

package com.indigi.brickmate;

public class addmoreModel {


    private String product_Name;
    private String uoM;
    private String product_Price;
    private String gst_Rate;
    private String quantiTy;
    private String total_Price;

    public addmoreModel() {
    }

    public addmoreModel(String product_Name, String uoM, String product_Price, String gst_Rate, String quantiTy, String total_Price) {
        this.product_Name = product_Name;
        this.uoM = uoM;
        this.product_Price = product_Price;
        this.gst_Rate = gst_Rate;
        this.quantiTy = quantiTy;
        this.total_Price = total_Price;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getUoM() {
        return uoM;
    }

    public void setUoM(String uoM) {
        this.uoM = uoM;
    }

    public String getProduct_Price() {
        return product_Price;
    }

    public void setProduct_Price(String product_Price) {
        this.product_Price = product_Price;
    }

    public String getGst_Rate() {
        return gst_Rate;
    }

    public void setGst_Rate(String gst_Rate) {
        this.gst_Rate = gst_Rate;
    }

    public String getQuantiTy() {
        return quantiTy;
    }

    public void setQuantiTy(String quantiTy) {
        this.quantiTy = quantiTy;
    }

    public String getTotal_Price() {
        return total_Price;
    }

    public void setTotal_Price(String total_Price) {
        this.total_Price = total_Price;
    }
}

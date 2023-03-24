package com.indigi.brickmate.model;

public class Order {

    private String id;
    private String customer_name;
    private String phone_number;
    private String gst_number;
    private String customer_address;
    private String product_name;
    private String uom;
    private String product_price;
    private String gst_rate;
    private String quantity;
    private String total_price;
    private String delivery_address;

    public Order() {
    }

    public Order(String id, String customer_name, String phone_number, String gst_number, String customer_address, String product_name, String uom, String product_price, String gst_rate, String quantity, String total_price, String delivery_address) {
        this.id = id;
        this.customer_name = customer_name;
        this.phone_number = phone_number;
        this.gst_number = gst_number;
        this.customer_address = customer_address;
        this.product_name = product_name;
        this.uom = uom;
        this.product_price = product_price;
        this.gst_rate = gst_rate;
        this.quantity = quantity;
        this.total_price = total_price;
        this.delivery_address = delivery_address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGst_number() {
        return gst_number;
    }

    public void setGst_number(String gst_number) {
        this.gst_number = gst_number;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getGst_rate() {
        return gst_rate;
    }

    public void setGst_rate(String gst_rate) {
        this.gst_rate = gst_rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }
}

package com.indigi.brickmate.model;

public class OrderList {


    private String customer_name;
    private String phone_number;
    private String product_name;
    private String quantity;
    private String total_price;
    private String delivery_address;

    public OrderList() {
    }

    public OrderList(String customer_name, String phone_number, String product_name, String quantity, String total_price, String delivery_address) {
        this.customer_name = customer_name;
        this.phone_number = phone_number;
        this.product_name = product_name;
        this.quantity = quantity;
        this.total_price = total_price;
        this.delivery_address = delivery_address;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

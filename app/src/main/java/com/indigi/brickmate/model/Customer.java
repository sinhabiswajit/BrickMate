package com.indigi.brickmate.model;

public class Customer {

    private String id;
    private String customer_name;
    private String phone_number;
    private String email;
    private String address;
    private String gst;

    public Customer() {
    }

    public Customer(String id, String name, String phone, String email, String address, String gstNumber) {
        this.id = id;
        this.customer_name = name;
        this.phone_number = phone;
        this.email = email;
        this.address = address;
        this.gst = gstNumber;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }
}


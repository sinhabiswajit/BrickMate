package com.indigi.brickmate.model;

public class Payment {
    private String id;
    private String customer_name;
    private String phone_number;
    private String total_Payment;
    private String receive_Payment;
    private String outstanding_payment;

    public Payment() {
    }

    public Payment(String id, String customer_name, String phone_number, String total_Payment, String receive_Payment, String outstanding_payment) {
        this.id = id;
        this.customer_name = customer_name;
        this.phone_number = phone_number;
        this.total_Payment = total_Payment;
        this.receive_Payment = receive_Payment;
        this.outstanding_payment = outstanding_payment;
    }

    public String getId() {
        return id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getTotal_Payment() {
        return total_Payment;
    }

    public String getReceive_Payment() {
        return receive_Payment;
    }

    public String getOutstanding_payment() {
        return outstanding_payment;
    }
}

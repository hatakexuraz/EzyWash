package com.assignment.firebasetest;

public class BookingPayment {
    private int booking_id;
    private String bill_user;
    private String detail;
    private String price;


    public int getBooking_id() {
        return booking_id;
    }

    public BookingPayment() {
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getBill_user() {
        return bill_user;
    }

    public void setBill_user(String bill_user) {
        this.bill_user = bill_user;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

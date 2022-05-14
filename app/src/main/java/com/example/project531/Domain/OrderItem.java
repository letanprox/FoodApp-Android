package com.example.project531.Domain;

public class OrderItem {
    private int OrderNum;
    private String Date;
    private double Price;
    private String status;


    public OrderItem(int orderNum, String date, double price, String status) {
        OrderNum = orderNum;
        Date = date;
        Price = price;
        this.status = status;
    }

    public int getOrderNum() {
        return OrderNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderNum(int orderNum) {
        OrderNum = orderNum;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}

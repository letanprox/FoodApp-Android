package com.example.project531.Domain;

import java.io.Serializable;

public class FoodItem implements Serializable {

    private int id;
    private String title;
    private String pic;
    private String description;
    private Double fee;
    private int sold;
    private int numberInCart;

    public FoodItem(String title, String pic, String description, Double fee, int sold, int numberInCart) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.sold = sold;
        this.numberInCart = numberInCart;
    }


    public FoodItem(int id, String title, String pic, String description, Double fee, int sold, int numberInCart) {
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.sold = sold;
        this.numberInCart = numberInCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}

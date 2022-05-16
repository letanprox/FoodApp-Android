package com.example.project531.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private int ID;
    private String title;
    private String pic;
    private String description;
    private Double fee;
    private double star;
    private int time;
    private int calories;
    private int numberInCart;
    private String position;
    private String timeopen;

    public FoodDomain(String title, String pic, String description, Double fee, double star, int time, int calories,String position) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.star = star;
        this.time = time;
        this.calories = calories;
        this.position = position;
    }

    public FoodDomain(String title, String pic, String description, Double fee, double star, int time, int calories, String position, String timeopen) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.star = star;
        this.time = time;
        this.calories = calories;
        this.position = position;
        this.timeopen = timeopen;
    }

    public FoodDomain(String title, String pic, String description, Double fee, double star, int time, int calories) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.star = star;
        this.time = time;
        this.calories = calories;
    }

    public FoodDomain(int ID, String title, String pic, String description, Double fee, double star, int time, int calories, int numberInCart, String position, String timeopen) {
        this.ID = ID;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.star = star;
        this.time = time;
        this.calories = calories;
        this.numberInCart = numberInCart;
        this.position = position;
        this.timeopen = timeopen;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTimeopen() {
        return timeopen;
    }

    public void setTimeopen(String timeopen) {
        this.timeopen = timeopen;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
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

    public double getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

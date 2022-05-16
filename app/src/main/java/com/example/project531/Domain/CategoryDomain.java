package com.example.project531.Domain;

import java.io.Serializable;

public class CategoryDomain implements Serializable {
    private int id;
    private String title;
    private String pic;

    public CategoryDomain(String title, String pic) {
        this.title = title;
        this.pic = pic;
    }

    public CategoryDomain(int id, String title, String pic) {
        this.id = id;
        this.title = title;
        this.pic = pic;
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
}

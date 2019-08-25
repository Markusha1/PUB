package com.example.pub.models;

import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Goal implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String category;
    public String money;
    public String photoUri;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}

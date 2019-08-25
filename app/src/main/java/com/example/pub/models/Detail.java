package com.example.pub.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import javax.inject.Inject;


@Entity
public class Detail implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String date;
    public String time;
    public String money;
    public String parentID;
    public String category;
    public String description;
    public String location;
    public String photoPath;

    @Inject
    public Detail(){
        this.location = "";
        this.photoPath = "";
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setId(@NonNull String id){
        parentID = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate(){
        return date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return id;
    }

    public String getMyLocation(){
        return location;
    }

    public void setMyLocation(String mylocation){
        location = mylocation;
    }

    public String getPhotoFileName(){
        return "IMG_" + getId() + ".jpg";
    }

    public String getPhotoPath(){
        return photoPath;
    }

    public void setPhotoPath(String photoPath){
        this.photoPath = photoPath;
    }
}

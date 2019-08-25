package com.example.pub.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

@Entity
public class Budget implements Serializable {

    @ColumnInfo(name = "money")
    @NonNull
    public String mBudgetMoney;

    @ColumnInfo(name = "title")
    @NonNull
    public String mBudgetText;

    public String mBudgetDate;

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    public String mBudgetIdentifier;


    @Inject
    public Budget() {
        this.mBudgetText = "";
        this.mBudgetMoney = "";
        this.mBudgetDate = new Date().toString();
        this.mBudgetIdentifier = UUID.randomUUID().toString();
    }


    public String getBudgetText() {
        return mBudgetText;
    }

    public void setBudgetText(String mBudgetText) {
        this.mBudgetText = mBudgetText;
    }

    public String getBudgetDate() {
        return mBudgetDate;
    }

    public void setBudgetDate(String mBudgetDate) {
        this.mBudgetDate = mBudgetDate;
    }

    public String getBudgetMoney (){
        return mBudgetMoney;
    }

    public void setBudgetMoney(String mBudgetMoney) {
        this.mBudgetMoney = mBudgetMoney;
    }

    public String getIdentifier() {
        return mBudgetIdentifier;
    }

}
package com.example.pub.Models;

import com.example.pub.R;

import java.util.HashMap;


public class Categories {

    public static HashMap<String, Integer> loadList(){
        HashMap<String, Integer> mList = new HashMap<>();
        mList.put("Продукты", R.drawable.food);
        mList.put("Аренда", R.drawable.rent);
        mList.put("ЖКХ", R.drawable.gkx);
        mList.put("Банк", R.drawable.bank);
        mList.put("Зарплата", R.drawable.salary);
        mList.put("Долг", R.drawable.debt);
        mList.put("Накопления", R.drawable.piggybank);
        mList.put("Налоги", R.drawable.tax);
        mList.put("Бизнес", R.drawable.business);
        mList.put("Развлечения", R.drawable.entertainment);
        mList.put("Одежда", R.drawable.clothes);
        mList.put("Путешествия", R.drawable.travel);
        mList.put("Транспорт", R.drawable.car);
        mList.put("Cвязь", R.drawable.enternet);
        mList.put("Домашние животные", R.drawable.pets);
        mList.put("Спорт", R.drawable.sport);
        mList.put("Услуги", R.drawable.services);
        mList.put("Прочее", R.drawable.menu);
        return mList;
    }
}

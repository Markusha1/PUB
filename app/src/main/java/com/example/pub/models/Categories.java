package com.example.pub.models;

import com.example.pub.R;

import java.util.HashMap;


public class Categories {
    static HashMap<String, Integer> mList = new HashMap<>();

    public static HashMap<String, Integer> loadDetailList(){
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

    public static HashMap<String, Integer> loadGoalList(){
        mList.put("Другое", R.drawable.menu);
        mList.put("Отдых", R.drawable.sailboat);
        mList.put("Бытовая техника", R.drawable.washing_machine);
        mList.put("Ремонт", R.drawable.paintroller);
        mList.put("Финансовый резерв", R.drawable.bank);
        mList.put("Мебель", R.drawable.bed);
        mList.put("Свое дело", R.drawable.business);
        mList.put("Недвижимость", R.drawable.rent);
        mList.put("Праздник", R.drawable.balloons);
        mList.put("Автомобиль", R.drawable.car);
        mList.put("Образование", R.drawable.mortarboard);
        return mList;
    }
}

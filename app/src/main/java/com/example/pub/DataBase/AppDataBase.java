package com.example.pub.DataBase;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pub.Models.Budget;
import com.example.pub.Models.Detail;

@Database(entities = {Budget.class, Detail.class}, version = AppDataBase.VERSION, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    static final int VERSION = 8;

    public abstract BudgetDao getBudgetDao();

    public  abstract DetailDao getDetailDao();
}

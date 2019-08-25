package com.example.pub.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pub.models.Budget;
import com.example.pub.models.Detail;

@Database(entities = {Budget.class, Detail.class}, version = AppDataBase.VERSION, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    static final int VERSION = 8;

    public abstract BudgetDao getBudgetDao();

    public  abstract DetailDao getDetailDao();
}

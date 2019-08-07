package com.example.pub.DI;


import android.content.Context;

import androidx.room.Room;

import com.example.pub.DataBase.AppDataBase;
import com.example.pub.DataBase.BudgetDao;
import com.example.pub.DataBase.DetailDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Singleton
    @Provides
    AppDataBase providesAppDataBase(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, "budget")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    BudgetDao providesDao(AppDataBase database) {
        return database.getBudgetDao();
    }

    @Singleton
    @Provides
    DetailDao providesDetailDao(AppDataBase dataBase){
        return dataBase.getDetailDao();
    }
}


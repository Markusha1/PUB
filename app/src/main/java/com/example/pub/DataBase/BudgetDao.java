package com.example.pub.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pub.Models.Budget;

import java.util.List;

@Dao
public interface BudgetDao {
    @Query("SELECT * FROM Budget")
    List<Budget> getAll();

    @Query("SELECT * FROM Budget WHERE id  =:id")
    Budget getBudget(String id);

    @Insert
    void insertBudget(Budget budget);

    @Delete
    void deleteBudget(Budget budget);

    @Update
    void updateBudget(Budget budget);

}

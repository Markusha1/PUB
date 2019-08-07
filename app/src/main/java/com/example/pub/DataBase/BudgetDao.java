package com.example.pub.DataBase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

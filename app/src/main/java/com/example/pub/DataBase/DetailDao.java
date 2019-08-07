package com.example.pub.DataBase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pub.Models.Detail;

import java.util.List;

@Dao
public interface DetailDao {

    @Insert
    void addDetail(Detail detail);

    @Delete
    void deleteDetail(Detail detail);

    @Query("SELECT * FROM detail WHERE parentID =:uuid")
    List<Detail> getAll(String uuid);

    @Update
    void update(Detail detail);

}

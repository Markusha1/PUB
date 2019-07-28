package com.example.pub.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

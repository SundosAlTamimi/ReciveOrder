package com.hiaryabeer.receiptsystem.Interfaces;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.hiaryabeer.receiptsystem.models.Items;

import java.util.List;

@Dao
public interface Items_Dao {
    @Insert
    void addAll(List<Items> items);

    @Query("DELETE FROM Items_Table")
    void deleteAll();

    @Delete
    void deleteItem(Items item);

    @Query("SELECT * FROM Items_Table WHERE Item_Num= :itemNo")
   Items getItembyCode(String itemNo);


}

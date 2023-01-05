package com.hiaryabeer.receiptsystem.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hiaryabeer.receiptsystem.models.Item_Unit_Details;

import java.util.List;

@Dao
public interface ItemUnits_Dao {

    @Insert
    void addAll(List<Item_Unit_Details> unitDetails);

    @Query("DELETE FROM Item_Unit_Details")
    void deleteAll();

    @Query("SELECT UNITID FROM Item_Unit_Details WHERE ITEMNO = :itemNo AND UNITID <> ''")
    List<String> getItemUnits(String itemNo);

    @Query("SELECT CONVRATE FROM Item_Unit_Details WHERE ITEMNO = :itemNo AND UNITID = :unitId")
    Double getConvRate(String itemNo, String unitId);
    @Query("SELECT CONVRATE FROM Item_Unit_Details WHERE ITEMNO = :itemNo")
    double getConvRatebyitemnum(String itemNo);

    @Query("SELECT CONVRATE FROM Item_Unit_Details WHERE ITEMNO = :itemNo AND ITEMBARCODE= :Barcode")
    double getConvRatebyitemnumAndBarcode(String itemNo,String Barcode);



    @Query("SELECT * FROM Item_Unit_Details WHERE ITEMNO = :itemNo AND UNITID <> ''")
    List<Item_Unit_Details> getItemUnitsOfItem(String itemNo);

    @Query("SELECT * FROM Item_Unit_Details WHERE ITEMNO = :itemNo AND ITEMBARCODE= :Barcode AND UNITID <> ''")
    Item_Unit_Details getItemUnitsOfItembybarcode(String itemNo,String Barcode);
    @Query("SELECT * FROM Item_Unit_Details WHERE ITEMBARCODE= :Barcode")
    Item_Unit_Details SelectItemUnitsBybarcode(String Barcode);

}

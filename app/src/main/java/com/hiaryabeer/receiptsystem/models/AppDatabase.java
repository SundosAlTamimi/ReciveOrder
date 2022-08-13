package com.hiaryabeer.receiptsystem.models;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hiaryabeer.receiptsystem.Interfaces.Customers_Dao;
import com.hiaryabeer.receiptsystem.Interfaces.ItemUnits_Dao;
import com.hiaryabeer.receiptsystem.Interfaces.Items_Dao;
import com.hiaryabeer.receiptsystem.Interfaces.ReceiptDetails_Dao;
import com.hiaryabeer.receiptsystem.Interfaces.ReceiptMaster_Dao;
import com.hiaryabeer.receiptsystem.Interfaces.Users_Dao;

@Database(entities = {Items.class,ReceiptMaster.class,ReceiptDetails.class,Item_Unit_Details.class,CustomerInfo.class,User.class}, version =10)

public abstract class AppDatabase extends RoomDatabase {
    public abstract Items_Dao itemsDao();
    public abstract ItemUnits_Dao itemUnitsDao();
    public abstract Customers_Dao customers_dao();
    public abstract ReceiptMaster_Dao receiptMaster_dao();
    public abstract ReceiptDetails_Dao receiptDetails_dao();
    private static AppDatabase InstanceDatabase;
    public abstract Users_Dao usersDao();
    public static String DatabaseName = "ReceiptSystem_Database";
    public static synchronized AppDatabase getInstanceDatabase(Context context) {

        if (InstanceDatabase == null) {

            InstanceDatabase = Room.databaseBuilder(context, AppDatabase.class, DatabaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }

        return InstanceDatabase;

    }

}

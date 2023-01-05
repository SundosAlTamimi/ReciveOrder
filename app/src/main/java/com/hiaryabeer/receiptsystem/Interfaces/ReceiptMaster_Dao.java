package com.hiaryabeer.receiptsystem.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.hiaryabeer.receiptsystem.models.ReceiptMaster;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ReceiptMaster_Dao {


    @Query("SELECT * FROM ReceiptMaster_Table Where IS_Posted='0'")
    List<ReceiptMaster> getAllOrdersConfirm();
    @Insert
    void insertAllOrders(ReceiptMaster... receiptMasters);
    @Insert
    void insertOrder(ReceiptMaster  receiptMaster);

    @Delete
    void deleteOrderByVOHNO(ReceiptMaster receiptMaster);
    @Query("UPDATE ReceiptMaster_Table SET  IS_Posted='1' WHERE IS_Posted='0'")
    int updateVoucher();

    @Query("SELECT * FROM ReceiptMaster_Table where Customer_ID= :cusid and VHFNO= :orderno and Date= :date and IS_Posted='0'")
    List<ReceiptMaster> getOrdersByOrderNOandCusID(String orderno,int cusid,String date);

    @Query("SELECT * FROM ReceiptMaster_Table where VHFNO= :orderno and Date= :date and IS_Posted='0'")
    List<ReceiptMaster> getOrdersByOrderNO(String orderno,String date);

    @Query("SELECT * FROM ReceiptMaster_Table where Customer_ID= :cusid and Date= :date and IS_Posted='0'")
    List<ReceiptMaster> getOrdersByCusID(int cusid,String date);

    @Query("SELECT * FROM ReceiptMaster_Table where Date= :date and IS_Posted='0'")
    List<ReceiptMaster> getOrdersByDate (String date);



    @Query("SELECT MAX(VHFNO) FROM ReceiptMaster_Table WHERE VOUCHERTYPE=504")
    int getLastVoherNo ( );
    @Query("SELECT MAX(VHFNO) FROM ReceiptMaster_Table WHERE VOUCHERTYPE=505")
    int getLastorderNo ( );
    @Query("delete from ReceiptMaster_Table where VHFNO= :vohno")
    int deleteOrderByVOHNO(long vohno);


    @Query("SELECT * FROM ReceiptMaster_Table")
    List<ReceiptMaster> getAllOrders();
    @Query("select * from ReceiptMaster_Table where VHFNO= :vohno")
    ReceiptMaster getOrderByVOHNO(long vohno);
    @Query("select * from ReceiptMaster_Table where TransNo= :vohno")
    ReceiptMaster getOrderByTransNo(long vohno);
   @Query("select DISTINCT Cust_Name from ReceiptMaster_Table")
 List<String>   getCustomers();

}

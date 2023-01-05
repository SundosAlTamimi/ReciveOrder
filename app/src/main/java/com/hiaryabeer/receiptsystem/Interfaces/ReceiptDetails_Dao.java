package com.hiaryabeer.receiptsystem.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.hiaryabeer.receiptsystem.models.ReceiptDetails;
import com.hiaryabeer.receiptsystem.models.ReceiptMaster;

import java.util.List;

@Dao
public interface ReceiptDetails_Dao {

    @Query("SELECT * FROM ReceiptDetails_Table Where IS_Posted='0'")
    List<ReceiptDetails> getAllOrders();
    @Query("SELECT * FROM ReceiptDetails_Table Where IS_Posted='0'")
    List<ReceiptDetails> getAllOrdersConfirm();
    @Query("SELECT * FROM ReceiptDetails_Table Where VHFNO= :VHFNO and IS_Posted='0'")
    List<ReceiptDetails> getAllOrdersByNumber(int VHFNO );
    @Insert
    void insertAllOrders(ReceiptDetails  receiptDetails);
    @Insert
    void insertOrder(ReceiptDetails  receiptDetails);

    @Delete
    void deleteOrder(ReceiptDetails receiptDetails);

    @Query("UPDATE ReceiptDetails_Table SET  IS_Posted='1' WHERE IS_Posted='0'")
  int  updateVoucherDetails ();

    @Query("delete from ReceiptDetails_Table where VHFNO= :vohno")
    int deleteOrderByVOHNO(int vohno);

    @Query("SELECT * FROM ReceiptDetails_Table Where VHFNO= :VHFNO")
    List<ReceiptDetails> getOrdersByNumber(long VHFNO );

    @Query("select * from ReceiptDetails_Table where TransNo= :Tranno and VHFNO= :vohno")
    List<ReceiptDetails> getOrderByTransNo(long vohno,long Tranno);

}

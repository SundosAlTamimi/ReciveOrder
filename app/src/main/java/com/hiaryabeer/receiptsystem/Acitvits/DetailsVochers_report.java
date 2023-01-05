package com.hiaryabeer.receiptsystem.Acitvits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.receiptsystem.Adapters.DetailsAdapter;
import com.hiaryabeer.receiptsystem.Adapters.MasterAdapter;
import com.hiaryabeer.receiptsystem.R;
import com.hiaryabeer.receiptsystem.models.AppDatabase;
import com.hiaryabeer.receiptsystem.models.ReceiptDetails;
import com.hiaryabeer.receiptsystem.models.ReceiptMaster;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailsVochers_report extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView ordersDetalisRec;
    List<ReceiptDetails> detailsList = new ArrayList<>();
    AppDatabase mydatabase;
    TextView vochernum, Cus_name, date, total,tax,netsales;
   long VohNu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_vochers_report);
        try {
            init();

//            Bundle bundle = getIntent().getExtras();
//
//          VohNu = bundle.getLong("VOHNO");
//
//            Log.e("VohNu==",VohNu+"  ");
            detailsList=mydatabase.receiptDetails_dao().getOrderByTransNo(MasterAdapter.VOH_NUM,MasterAdapter.TransNo);
            Log.e("detailsList==",detailsList.size()+"");
            fillDataText();
            filladapter();
        }
        catch (Exception e){
            Log.e("Exception=",e.getMessage());
        }

    }

    private void filladapter() {
        ordersDetalisRec.setAdapter(new DetailsAdapter(detailsList,DetailsVochers_report.this));
    }

    void init(){
        ordersDetalisRec = findViewById(R.id.ordersDetalisRec);
        total = findViewById(R.id.total);
        vochernum = findViewById(R.id.ORDERNO);
        netsales= findViewById(R.id.netsales);
        Cus_name = findViewById(R.id.Cus_name);
        tax= findViewById(R.id.tax);
        Log.e("VOHNO==", VohNu + "");
        mydatabase = AppDatabase.getInstanceDatabase(DetailsVochers_report.this);
        ordersDetalisRec = findViewById(R.id.ordersDetalisRec);
        ordersDetalisRec.setLayoutManager(new LinearLayoutManager(DetailsVochers_report.this));
        date = findViewById(R.id.date);


    }
  void  fillDataText(){
      total.setText("");
      vochernum.setText(detailsList.get(0).getTransNo()+"");
      String customerName =mydatabase.customers_dao().getCustmByNumber(detailsList.get(0).getCustomerId()) ;

      Cus_name.setText(customerName+"");
      date    .setText(detailsList.get(0).getDate()+"");
     ReceiptMaster receiptMaster =mydatabase.receiptMaster_dao().getOrderByTransNo(Long.parseLong(detailsList.get(0).getTransNo()));
      netsales  .setText(receiptMaster.getNetTotal()+"");
      tax .setText(receiptMaster.getTax()+"");
    }
}
package com.hiaryabeer.receiptsystem.Adapters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.receiptsystem.Acitvits.MainActivity;
import com.hiaryabeer.receiptsystem.R;
import com.hiaryabeer.receiptsystem.models.AppDatabase;
import com.hiaryabeer.receiptsystem.models.GeneralMethod;
import com.hiaryabeer.receiptsystem.models.Items;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Context context; //context
    private List<Items> items; //data source of the list adapter
    public static final int REQUEST_Camera_Barcode = 1;
    double aviQty=0;
    AppDatabase mydatabase;

    //public constructor
    public SearchAdapter(Context context, List<Items> items) {
        this.context = context;
        this.items = items;
        mydatabase=AppDatabase.getInstanceDatabase(context);
//        this.my_database = RoomAllData.getInstanceDataBase(context);
//        this.serialTransfers = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.searchrec, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.   textViewItemName.setText(items.get(position).getNAME());
        holder.  textViewprice.setText(items.get(position).getF_D()+"");

        holder.   textViewqty.setText(items.get(position).getBalanceQty()+"");
        Log.e("aviinsearch===", items.get(position).getAviQty() + "");
        int avi;
        if (MainActivity.VochType == 504) {
          avi=CheckIsExistsINLocalList(items.get(position).getITEMNO());
    if(avi>0)
    {
      if(avi==1)  holder.   textViewqty.setText(aviQty+"");

        holder.   linearLayout.setVisibility(View.VISIBLE);
        holder.  View_.setVisibility(View.VISIBLE);
    }
  else   {
      holder.   linearLayout.setVisibility(View.GONE);
        holder.  View_.setVisibility(View.GONE);
  }}
        else {
            holder.   linearLayout.setVisibility(View.VISIBLE);
            holder.  View_.setVisibility(View.VISIBLE);

        }
        holder.   linearLayout.setOnClickListener(view -> {
            MainActivity.search.setEnabled(true);
            MainActivity.itemcode.setText(items.get(position).getITEMNO());
            Log.e("position6===", position + "");
            MainActivity.item = mydatabase.itemsDao().getItembyCode(MainActivity.itemcode.getText().toString().trim());

            if ( MainActivity.item != null) {


                MainActivity.   itemname.setText( MainActivity.item.getNAME());
                MainActivity.   itemqty.setText( MainActivity.item.getQty() + "");
                MainActivity.    itemprice.setText( MainActivity.item.getF_D() + "");

                MainActivity.     free.setText("");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.     itemqty.setError(null);
                        MainActivity.    itemcode.setError(null);
                        MainActivity.  itemname.setText( MainActivity.item.getNAME());
                        MainActivity.    itemqty.setText( MainActivity.item.getQty() + "");
                        MainActivity.    itemprice.setText( MainActivity.item.getF_D() + "");
                        MainActivity.     free.setText("");
                        MainActivity.    item.setConvRate("1");
                        //         addItem(item.getITEMNO());
                        //    itemqty.requestFocus();
                        MainActivity.      itemqty.setEnabled(true);
                        MainActivity.      itemqty.requestFocus();
                    }
                }, 100);
            } else {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("else3==", "else");
                        MainActivity.itemcode.setError("");
                        MainActivity.  itemcode.requestFocus();
                        MainActivity.   itemqty.setEnabled(false);
                    }
                }, 100);
            }

            MainActivity. dialog1.dismiss();

        });
    }

    @Override
    public int getItemCount() {
        return items.size(); //returns total of items in the list
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewItemName,textViewprice,textViewqty;
        LinearLayout linearLayout ,parentLinear;
View View_;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            View_=itemView.findViewById(R.id.View_);
          textViewItemName = (TextView)
                  itemView.findViewById(R.id.itemname);
         textViewprice = (TextView)
                 itemView.findViewById(R.id.price);
        textViewqty = (TextView)
                itemView.findViewById(R.id.qty);
            parentLinear = itemView.findViewById(R.id.parentLinear);
            linearLayout= itemView.findViewById(R.id.linear);
        }
    }
//    private boolean CheckIsExistsINLocalList(String barcode) {
//
//
//        boolean flag = false;
//        if (MainActivity.vocher_Items.size() != 0) {
//            for (int i = 0; i < MainActivity.vocher_Items.size(); i++) {
//
//                if (
//                        GeneralMethod.convertToEnglish(MainActivity.vocher_Items.get(i).getITEMNO()).equals(GeneralMethod.convertToEnglish(barcode))
//
//                ) {
//                    if (MainActivity.vocher_Items.get(i).getAviQty() > 0) {
//                        flag = true;
//                        aviQty=MainActivity.vocher_Items.get(i).getAviQty();
//                        break;
//                    } else {
//                        flag = false;
//                        break;
//                    }
//
//                } else {
//                    flag = true;
//                    aviQty=MainActivity.vocher_Items.get(i).getBalanceQty();
//                    continue;
//                }
//            }
//        }else
//        {
//            flag = true;
//        }
//        return flag;
//
//
//    }
    private int CheckIsExistsINLocalList(String barcode) {


        int flag = 0;
        if (MainActivity.vocher_Items.size() != 0) {
            for (int i = 0; i < MainActivity.vocher_Items.size(); i++) {

                if (
                        GeneralMethod.convertToEnglish(MainActivity.vocher_Items.get(i).getITEMNO()).equals(GeneralMethod.convertToEnglish(barcode))

                ) {
                    if (MainActivity.vocher_Items.get(i).getAviQty() > 0) {
                        flag = 1;
                        aviQty=MainActivity.vocher_Items.get(i).getAviQty();
                        break;
                    } else {
                        flag = 0;
                        break;
                    }

                } else {
                    flag = 3;

                    continue;
                }
            }
        }else
        {
            flag = 2;
        }
        return flag;


    }
}

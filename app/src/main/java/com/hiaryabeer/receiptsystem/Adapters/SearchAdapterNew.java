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
import androidx.recyclerview.widget.RecyclerView;


import com.hiaryabeer.receiptsystem.Acitvits.ReceivePO;
import com.hiaryabeer.receiptsystem.R;
import com.hiaryabeer.receiptsystem.databinding.ActivityReceivePoBinding;
import com.hiaryabeer.receiptsystem.models.AppDatabase;
import com.hiaryabeer.receiptsystem.models.GeneralMethod;
import com.hiaryabeer.receiptsystem.models.Items;
import com.hiaryabeer.receiptsystem.models.POitems;

import java.util.List;

public class SearchAdapterNew extends RecyclerView.Adapter<SearchAdapterNew.ViewHolder>{
    private Context context; //context
    private List<POitems> items; //data source of the list adapter
    public static final int REQUEST_Camera_Barcode = 1;
    double aviQty=0;
    AppDatabase mydatabase;

    //public constructor
    public SearchAdapterNew(Context context, List<POitems> items) {
        this.context = context;
        this.items = items;
        mydatabase=AppDatabase.getInstanceDatabase(context);
//        this.my_database = RoomAllData.getInstanceDataBase(context);
//        this.serialTransfers = new ArrayList<>();
    }
    @NonNull
    @Override
    public SearchAdapterNew.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapterNew.ViewHolder(LayoutInflater.from(context).inflate(R.layout.searchrec, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapterNew.ViewHolder holder, int position) {

        holder.   textViewItemName.setText(items.get(position).getITEMNAME());
        holder.  textViewprice.setText(items.get(position).getENTERPRICE()+"");

        holder.   textViewqty.setText(items.get(position).getQty()+"");
        Log.e("aviinsearch===", items.get(position).getQty() + "");
        int avi;

        holder.   linearLayout.setOnClickListener(view -> {
            ReceivePO.binding.search.setEnabled(true);

            ReceivePO.item = new Items();
            ReceivePO.  item.setITEMNO(items.get(holder.getAdapterPosition()).getItemOCode());
            ReceivePO. item.setF_D(Double.parseDouble(items.get(holder.getAdapterPosition()).getENTERPRICE()));
            ReceivePO.  item.setNAME(items.get(holder.getAdapterPosition()).getITEMNAME());
            ReceivePO.    item.setTAXPERC(Double.parseDouble(items.get(holder.getAdapterPosition()).getTTAXPERC() )/ 100);
            ReceivePO.    item.setItemNCode(items.get(holder.getAdapterPosition()).getItemNCode());
            ReceivePO.  binding.qty.setEnabled(true);
            ReceivePO. binding.qty.requestFocus();
            ReceivePO.binding.itemCode.setText(items.get(position).getItemOCode());
            Log.e("position6===", position + "");



                ReceivePO. binding.  itemName.setText(items.get(holder.getAdapterPosition()).getITEMNAME());
                ReceivePO. binding.   Poqty.setText( items.get(holder.getAdapterPosition()).getQty() + "");
                ReceivePO. binding.   price.setText(items.get(holder.getAdapterPosition()).getENTERPRICE() + "");

                ReceivePO. binding.    free.setText("");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ReceivePO. binding.    qty.setError(null);
                        ReceivePO. binding.   itemCode.setError(null);
                        ReceivePO. binding.  itemName.setText( items.get(holder.getAdapterPosition()).getITEMNAME());
                        ReceivePO. binding.   Poqty.setText( items.get(holder.getAdapterPosition()).getQty()+ "");
                        ReceivePO. binding.  price.setText( items.get(holder.getAdapterPosition()).getENTERPRICE() + "");
                        ReceivePO. binding.    free.setText("");


                        ReceivePO.    binding.    qty.setEnabled(true);
                        ReceivePO.  binding.      qty.requestFocus();
                    }
                }, 100);



            ReceivePO. dialog1.dismiss();

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


}

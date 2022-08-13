package com.hiaryabeer.receiptsystem.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.receiptsystem.Acitvits.MainActivity;
import com.hiaryabeer.receiptsystem.R;
import com.hiaryabeer.receiptsystem.models.Items;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
ArrayList<Items>itemsList=new ArrayList<>();
Context context;
   private int index = 0;
   public ItemsAdapter(ArrayList<Items> itemsList, Context context) {
      this.itemsList = itemsList;
      this.context = context;
   }

   public ArrayList<Items> getItemsList() {
      return itemsList;
   }

   public void setItemsList(ArrayList<Items> itemsList) {
      this.itemsList = itemsList;
   }

   public Context getContext() {
      return context;
   }

   public void setContext(Context context) {
      this.context = context;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemrow, parent, false));

   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.name.setText(itemsList.get(position).getNAME());
      holder.itemcode.setText(itemsList.get(position).getITEMNO());
      holder.qty.setText(itemsList.get(position).getQty()+"");
      holder.price.setText(itemsList.get(position).getF_D()+"");
     if(!IsExistsInList(itemsList.get(position).getITEMNO())) holder.free.setText("0");
     else holder.free.setText(MainActivity.vocher_Items.get(index).getFree()+"");
      holder.    free.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
            if(!editable.toString().equals(""))
            {
              MainActivity. vocher_Items.get(holder.getAdapterPosition()).
                      setFree( Double.parseDouble(holder.free.getText().toString().trim()));
            }else
            {
               MainActivity. vocher_Items.get(holder.getAdapterPosition()).
                       setFree( 0);

            }
         }
      });

      if(!IsExistsInList(itemsList.get(position).getITEMNO())) holder.discount.setText("0");
      else holder.discount.setText(MainActivity.vocher_Items.get(index).getDiscount()+"");
      holder.    discount.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
            if(!editable.toString().equals(""))
            {
               MainActivity. vocher_Items.get(holder.getAdapterPosition()).
                       setDiscount ( Double.parseDouble(holder.discount.getText().toString().trim())/100);
               double TotalDiscVal=itemsList.get(position).getQty()*itemsList.get(position).getF_D()*itemsList.get(position).getDiscount();
               holder. total.setText(String.valueOf( (itemsList.get(position).getQty()*itemsList.get(position).getF_D())-TotalDiscVal));


            }else
            {
               MainActivity. vocher_Items.get(holder.getAdapterPosition()).
                       setDiscount( 0);
               double TotalDiscVal=itemsList.get(position).getQty()*itemsList.get(position).getF_D()*itemsList.get(position).getDiscount();
               holder. total.setText(String.valueOf( (itemsList.get(position).getQty()*itemsList.get(position).getF_D())-TotalDiscVal));

            }
         }
      });
         double TotalDiscVal=itemsList.get(position).getQty()*itemsList.get(position).getF_D()*itemsList.get(position).getDiscount();
      holder. total.setText(String.valueOf( (itemsList.get(position).getQty()*itemsList.get(position).getF_D())-TotalDiscVal));


   }

   @Override
   public int getItemCount() {
      return itemsList.size();
   }

   class ViewHolder extends RecyclerView.ViewHolder{
      TextView name,itemcode,qty,price,total;
           EditText free,discount;
      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         name=itemView.findViewById(R.id.item_Name_);
                 itemcode=itemView.findViewById(R.id.item_code_);
                 qty=itemView.findViewById(R.id.item_Qty);
                 free=itemView.findViewById(R.id.free);
         discount=itemView.findViewById(R.id.discount);
         price=itemView.findViewById(R.id.price);
         total=itemView.findViewById(R.id.total);

      }
   }
   boolean IsExistsInList(String ItemOCode) {
       index = 0;
      boolean exists = false;
      for (int i = 0; i < MainActivity.vocher_Items.size(); i++)
         if (MainActivity.vocher_Items.get(i).getITEMNO().equals(ItemOCode)) {
            index = i;
            exists = true;
            break;

         }

      return exists;
   }
}

package com.hiaryabeer.receiptsystem.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiaryabeer.receiptsystem.Acitvits.MainActivity;
import com.hiaryabeer.receiptsystem.Acitvits.ReceivePO;
import com.hiaryabeer.receiptsystem.R;
import com.hiaryabeer.receiptsystem.models.AppDatabase;
import com.hiaryabeer.receiptsystem.models.GeneralMethod;
import com.hiaryabeer.receiptsystem.models.Items;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ItemsAdapterNew extends RecyclerView.Adapter<ItemsAdapterNew.ViewHolder>{
ArrayList<Items>itemsList=new ArrayList<>();
Context context;
AppDatabase appDatabase;
   private int index = 0;
   public static  TextView qtyinvalidrespon;
   String currentqty="";
   public ItemsAdapterNew(ArrayList<Items> itemsList, Context context) {
      this.itemsList = itemsList;
      this.context = context;
      appDatabase=AppDatabase.getInstanceDatabase(context);
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

      holder.reomveItem.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.delete_entry);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  Log.e("removeItemposition", holder.getAdapterPosition() + "");
                  itemsList.remove(holder.getAdapterPosition());
                  notifyDataSetChanged();
                  ReceivePO.desRespon.setText("aa");
                  dialog.dismiss();

               }
            });
            dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  dialog.dismiss();
               }
            });
            dialog.show();
            dialog.setCanceledOnTouchOutside(true);
         }
      });
      holder.qty.addTextChangedListener(new TextWatcher() {
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
               try {
                  if (Double.parseDouble(holder.qty.getText().toString())>0)
                  {
                     Log.e("itemsList===",itemsList.size()+"");
                     Log.e("case1","case1");

                     Log.e("case1.2","case1.2");

                     if(checkqty(itemsList.get(holder.getAdapterPosition()).getITEMNO(),holder.qty.getText().toString().trim()))

                     {
                        ReceivePO.vocher_Items.get(holder.getAdapterPosition()).
                                setQty(Double.parseDouble(holder.qty.getText().toString()));

                        double TotalDiscVal = itemsList.get(holder.getAdapterPosition()).getQty() * itemsList.get(holder.getAdapterPosition()).getF_D() * itemsList.get(holder.getAdapterPosition()).getDiscount();
                        holder.total.setText(String.valueOf((itemsList.get(holder.getAdapterPosition()).getQty() * itemsList.get(holder.getAdapterPosition()).getF_D()) - TotalDiscVal));
                        ReceivePO.desRespon.setText("aa");
                     }else{
                        GeneralMethod.showSweetDialog(context, 0, "",context.getResources().getString(R.string.aviQTY)+" "+currentqty);
                        MainActivity.vocher_Items.get(holder.getAdapterPosition()).
                                setQty(Double.parseDouble(currentqty));
                        holder.qty.setText(currentqty+"");
                        double TotalDiscVal = MainActivity.vocher_Items.get(holder.getAdapterPosition()).getQty() * MainActivity.vocher_Items.get(holder.getAdapterPosition()).getF_D() * MainActivity.vocher_Items.get(holder.getAdapterPosition()).getDiscount();
                        holder.total.setText(String.valueOf((MainActivity.vocher_Items.get(holder.getAdapterPosition()).getQty() * MainActivity.vocher_Items.get(holder.getAdapterPosition()).getF_D()) - TotalDiscVal));
                        Log.e("case1.1", "total=," + holder.total.getText());
                        Log.e("case1.1", "qty=," + MainActivity.vocher_Items.get(holder.getAdapterPosition()).getQty());
                        MainActivity.desRespon.setText("aa");
                        itemsList.get(holder.getAdapterPosition()) .setAviQty( Double.parseDouble(currentqty)- MainActivity.vocher_Items.get(holder.getAdapterPosition()).getQty());

                     }

                  }
                  else if(Double.parseDouble(holder.qty.getText().toString())<0)
                  {
                     holder.qty.setError("not valid");
                     ReceivePO.vocher_Items.get(holder.getAdapterPosition()).
                             setQty(0);

                     double TotalDiscVal = itemsList.get(holder.getAdapterPosition()).getQty() * itemsList.get(holder.getAdapterPosition()).getF_D() * itemsList.get(holder.getAdapterPosition()).getDiscount();
                     holder.total.setText(String.valueOf((itemsList.get(holder.getAdapterPosition()).getQty() * itemsList.get(holder.getAdapterPosition()).getF_D()) - TotalDiscVal));
                     ReceivePO.desRespon.setText("aa");
                  } else if(Double.parseDouble(holder.qty.getText().toString())==0)
                  {
                     ReceivePO.vocher_Items.get(holder.getAdapterPosition()).
                             setQty(0);

                     double TotalDiscVal = itemsList.get(holder.getAdapterPosition()).getQty() * itemsList.get(holder.getAdapterPosition()).getF_D() * itemsList.get(holder.getAdapterPosition()).getDiscount();
                     holder.total.setText(String.valueOf((itemsList.get(holder.getAdapterPosition()).getQty() * itemsList.get(holder.getAdapterPosition()).getF_D()) - TotalDiscVal));
                     ReceivePO.desRespon.setText("aa");

                  }
               }catch (Exception exception){

               }


            }else
            {
               Log.e("case1.3","case1.3");
               ReceivePO. vocher_Items.get(holder.getAdapterPosition()).
                       setQty(1);
                    double TotalDiscVal=itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D()*itemsList.get(holder.getAdapterPosition()).getDiscount();
               holder. total.setText(String.valueOf( (itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D())-TotalDiscVal));
               ReceivePO.desRespon.setText("aa");


            }
         }
      });
      holder.price.setText(itemsList.get(position).getF_D()+"");
      holder.price.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void afterTextChanged(Editable editable) {

            if(!editable.toString().equals(""))
            {     ReceivePO. vocher_Items.get(holder.getAdapterPosition()).setF_D( Double.parseDouble(holder.price.getText().toString()));
                     Log.e("f_d===",ReceivePO. vocher_Items.get(holder.getAdapterPosition()).getF_D()+"");
               double TotalDiscVal=itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D()*itemsList.get(holder.getAdapterPosition()).getDiscount();
               holder. total.setText(String.valueOf( (itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D())-TotalDiscVal));
               ReceivePO.desRespon.setText("aa");
            }else
            {
               ReceivePO. vocher_Items.get(holder.getAdapterPosition()).
                       setF_D( 1);
               Log.e("f_d===",ReceivePO. vocher_Items.get(holder.getAdapterPosition()).getF_D()+"");

               double TotalDiscVal=itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D()*itemsList.get(holder.getAdapterPosition()).getDiscount();
               holder. total.setText(String.valueOf( (itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D())-TotalDiscVal));
               ReceivePO.desRespon.setText("aa");
            }
         }
      });
     if(!IsExistsInList(itemsList.get(position).getITEMNO())) holder.free.setText("0");
     else holder.free.setText(ReceivePO.vocher_Items.get(index).getFree()+"");
     try {
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


                 ReceivePO.vocher_Items.get(holder.getAdapterPosition()).
                                 setFree(Double.parseDouble(holder.free.getText().toString().trim()));



              }else
              {
                 ReceivePO. vocher_Items.get(holder.getAdapterPosition()).
                         setFree( 0);

              }
           }
        });
     }catch (Exception e){

     }


      if(!IsExistsInList(itemsList.get(position).getITEMNO())) holder.discount.setText("0");

      else holder.discount.setText(ReceivePO.vocher_Items.get(index).getDiscount()+"");

      try {
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
                  ReceivePO. vocher_Items.get(holder.getAdapterPosition()).
                          setDiscount ( Double.parseDouble(holder.discount.getText().toString().trim())/100);
                  double TotalDiscVal=itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D()*itemsList.get(holder.getAdapterPosition()).getDiscount();
                  holder. total.setText(String.valueOf( (itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D())-TotalDiscVal));


               }else
               {
                  ReceivePO. vocher_Items.get(holder.getAdapterPosition()).
                          setDiscount( 0);
                  double TotalDiscVal=itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D()*itemsList.get(holder.getAdapterPosition()).getDiscount();
                  holder. total.setText(String.valueOf( (itemsList.get(holder.getAdapterPosition()).getQty()*itemsList.get(holder.getAdapterPosition()).getF_D())-TotalDiscVal));

               }
               ReceivePO.desRespon.setText("aa");   }
         });
      }catch (Exception e){


      }

         double TotalDiscVal=itemsList.get(position).getQty()*itemsList.get(position).getF_D()*itemsList.get(position).getDiscount();
      holder. total.setText(GeneralMethod.convertToEnglish(String.valueOf(String.  format("%.3f", (itemsList.get(position).getQty()*itemsList.get(position).getF_D())-TotalDiscVal))));
      //netsales.setText(GeneralMethod.convertToEnglish(String.  format("%.3f",9999999999)));

     qtyinvalidrespon.addTextChangedListener(new TextWatcher() {
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
              holder.qty.setError("");
            }
         }
      });

   }

   @Override
   public int getItemCount() {
      return itemsList.size();
   }

   class ViewHolder extends RecyclerView.ViewHolder{
      TextView name,itemcode,total,reomveItem;
           EditText free,discount,price,qty;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         qtyinvalidrespon=itemView.findViewById(R.id.qtyinvalidrespon);

         reomveItem=itemView.findViewById(R.id.reomveItem);
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
      for (int i = 0; i < ReceivePO.vocher_Items.size(); i++)
         if (ReceivePO.vocher_Items.get(i).getITEMNO().equals(ItemOCode)) {
            index = i;
            exists = true;
            break;

         }

      return exists;
   }
   boolean checkqty(String itemCode,String qty){
      currentqty="";
      int index = ReceivePO.POitems.stream()
                            .map(item -> item.getItemOCode())
                            .collect(Collectors.toList())
                            .indexOf(itemCode);
                    Log.e("index==",index+"");
      currentqty=ReceivePO.POitems.get(index).getQty();
      Log.e("currentqty==",currentqty+"");
               // if(index!=null)
                   if( Double.parseDouble(ReceivePO.POitems.get(index).getQty())>=Double.parseDouble(qty))
                    {



                       return true;

                    }
                    else
                    {
                       return false;

                    }

    //  return false;
   }

}

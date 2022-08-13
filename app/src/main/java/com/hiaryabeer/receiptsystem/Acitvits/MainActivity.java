package com.hiaryabeer.receiptsystem.Acitvits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hiaryabeer.receiptsystem.Adapters.ItemsAdapter;
import com.hiaryabeer.receiptsystem.R;
import com.hiaryabeer.receiptsystem.models.AppDatabase;
import com.hiaryabeer.receiptsystem.models.CustomerInfo;
import com.hiaryabeer.receiptsystem.models.ExportData;
import com.hiaryabeer.receiptsystem.models.GeneralMethod;
import com.hiaryabeer.receiptsystem.models.ImportData;
import com.hiaryabeer.receiptsystem.models.Item_Unit_Details;
import com.hiaryabeer.receiptsystem.models.Items;
import com.hiaryabeer.receiptsystem.models.ReceiptDetails;
import com.hiaryabeer.receiptsystem.models.ReceiptMaster;
import com.hiaryabeer.receiptsystem.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.receiptsystem.Acitvits.Login.SETTINGS_PREFERENCES;
import static com.hiaryabeer.receiptsystem.Acitvits.Login.coNo;
import static com.hiaryabeer.receiptsystem.Acitvits.Login.ipAddress;
import static com.hiaryabeer.receiptsystem.Acitvits.Login.ipPort;
import static com.hiaryabeer.receiptsystem.Acitvits.Login.listAllVendor;

public class MainActivity extends
        AppCompatActivity    {
        EditText
                itemcode,
                 itemqty,

                     free;

        RecyclerView itemRec;
        ItemsAdapter itemsAdapter;
        AppCompatButton Save,
                        close;

        AppDatabase mydatabase;
    private AutoCompleteTextView customerTv;
        TextView itemname  ,itemprice,vlauoftotaldis, netsales;
    Items item;
    int pos ,orderno ,vohno,num_items=1;
    public static ArrayList<Items> vocher_Items = new ArrayList<>();
    public  ArrayList<ReceiptDetails> ordersDetailslist = new ArrayList<>();
    GeneralMethod generalMethod;
    private List<CustomerInfo> customerInfoList=new ArrayList<>();
    private List<CustomerInfo> VendorInfoList=new ArrayList<>();
    private ArrayList<String> customerNames=new ArrayList<>();
    String Cus_selection;
    private TextInputLayout customer_textInput;
    double   itemTax=0,itemTotal=0,subTotal=0,itemTotalAfterTax=0,netTotal=0;
    private double itemTotalPerc=0,itemDiscVal=0,totalDiscount=0,totalTaxValue=0;
  RadioGroup radioGroup;
    AppCompatRadioButton order,vocher,cash,cridt;
ExportData exportData;
TextView menu;
 ImportData importData;
 double AllVocherDiscount=0;
 int distype=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
          init();

        itemcode.requestFocus();
          menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, menu);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.menu_example, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.importdata:
                                Log.e("importdata==", "importdata");
                                List<ReceiptMaster> vouchers=mydatabase.receiptMaster_dao().getAllOrdersConfirm();
                                if(vouchers.size()==0)
                                importdata();
                                else
                                    generalMethod.showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.exportdatamsg), "");

                                return true;
                            case R.id.exportdata:
                                exportData.exportSalesVoucherM();
                                return true;
                            case R.id.addTotaldis:
                                if(vocher_Items.size()==0)
                                    generalMethod.showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.fillbasket), "");

                                else

                                addTotalDisDailog();

                        }
                        return true;     }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

    }
    public void  init(){
        importData=new ImportData(MainActivity.this);
        menu=findViewById(R.id.menuBtn);
        order=findViewById(R.id.order);
        vocher=findViewById(R.id.vocher);
        cash=findViewById(R.id.cash);
        cridt=findViewById(R.id.cridt);

        final RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button
                        VendorInfoList.clear();
                        VendorInfoList = mydatabase.customers_dao().getAllVendor();
                        fillCustomerspinner(VendorInfoList);
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        break;
                    case 1: // secondbutton
                        customerInfoList.clear();
                        customerInfoList = mydatabase.customers_dao().getAllCustms();

                        fillCustomerspinner(customerInfoList);
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        break;
                }
            }
        });

        radioGroup=findViewById(R.id.radioGroup);
        exportData = new ExportData(MainActivity.this);
        generalMethod=new GeneralMethod(MainActivity.this);
        vocher_Items.clear();
        itemRec=findViewById(R.id.tableData);
        itemRec.setLayoutManager( new LinearLayoutManager(MainActivity.this));
        mydatabase=AppDatabase.getInstanceDatabase(MainActivity.this);
        itemname=findViewById(R.id.item_name);
        itemcode=findViewById(R.id.item_code);
        itemqty=findViewById(R.id.qty);
        itemprice=findViewById(R.id.price);
        free=findViewById(R.id.free);
        vlauoftotaldis=findViewById(R.id.vlauoftotaldis);
        netsales =findViewById(R.id.netsales);
        Save =findViewById(R.id.save);
        close=findViewById(R.id.cancel_btn);
        itemqty.setEnabled(false);
        HorizontalScrollView horizontalScroll=findViewById(R.id.HorizontalScroll);
        horizontalScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                itemcode.requestFocus();
            }
        });
//      itemcode.setOnKeyListener(onKeyListener);
//        itemqty.setOnKeyListener(onKeyListener);
        itemcode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

              String  itemNo_text = itemcode.getText().toString().trim();



                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (i) {

                            case KeyEvent.KEYCODE_DPAD_CENTER:
                            case KeyEvent.KEYCODE_ENTER:
//                                switch (view.getId()) {
//
//                                    case R.id.item_code:
                                    if ((!itemcode.getText().equals(""))) {


                                        Log.e("itemcode==", itemcode.getText().toString().trim());
                                        item = mydatabase.itemsDao().getItembyCode(itemcode.getText().toString().trim());

                                        if (item != null) {


                                            itemname.setText(item.getNAME());
                                            itemqty.setText(item.getQty() + "");
                                            itemprice.setText(item.getF_D() + "");
                                            free.setText("");

                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    itemname.setText(item.getNAME());
                                                    itemqty.setText(item.getQty() + "");
                                                    itemprice.setText(item.getF_D() + "");
                                                    free.setText("");

                                                    //         addItem(item.getITEMNO());
                                                    //    itemqty.requestFocus();
                                                    itemqty.setEnabled(true);
                                                }
                                            }, 100);
                                        } else {

                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Log.e("else3==", "else");
                                                    itemcode.setError("");
                                                    itemcode.requestFocus();
                                                    itemqty.setEnabled(false);
                                                }
                                            }, 100);
                                        }
//
                                    } else {
                                        Log.e("else2==", "else");
                                        itemcode.setError("");
                                        itemcode.requestFocus();
                                        itemqty.setEnabled(false);

                                    }
//                                }
                                return true;
                            default:
                                break;
                        }
                    }


                return false;
            }
        });
        itemqty.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                String  itemNo_text = itemqty.getText().toString().trim();

                Log.e("setOnKeyactionId==", i+"KeyEvent=="+keyEvent.getAction());


                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
//                            switch (view.getId()) {
//
//                                case R.id.item_Qty:
                                    if ((!itemcode.getText().equals(""))) {
                                        if ((!itemqty.getText().equals(""))) {


                                            Log.e("KeyListeneritemqty==", itemqty.getText().toString().trim());


                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    addItem(item.getITEMNO());
                                                    itemcode.requestFocus();


                                                }

                                            }, 100);


//
                                        } else {
                                            Log.e("else==", "else");
                                            itemqty.setError("");
                                            itemqty.requestFocus();
                                        }
                                    } else {
                                        Log.e("else==", "else");
                                        itemcode.setError("");
                                        itemcode.requestFocus();
                                    }
//                            }
                            return true;
                        default:
                            break;
                    }
                }


                return false;
            }
        });
        itemqty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
              //  Log.e("actionId==", actionId+"KeyEvent=="+event.getAction());

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {
//                    Log.e("setOnEditorActio", "afterTextChangedNOT" +"errorData\t"+actionId);
                     if ((!itemcode.getText().equals(""))  ) {
                        Log.e("setOnEditorActionitemqty==", itemqty.getText().toString().trim());

                        if ((!itemqty.getText().equals(""))) {

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                addItem(item.getITEMNO());
                                itemcode.requestFocus();


                            }

                        }, 100);

                        Log.e("itemqty==", itemqty.getText().toString().trim());


//
                    } else {
                        Log.e("else==", "else");
                        itemqty.setError("");
                        itemqty.requestFocus();
                    }
                }else {
                        Log.e("else==", "else");
                        itemcode.setError("");
                        itemcode.requestFocus();
                    }
                        }
                return false;
            }
        });
        customer_textInput = findViewById(R.id.customer_textInput);
        customerTv = findViewById(R.id.customerTv);


        Save.setOnClickListener(onClickListener);
        close.setOnClickListener(onClickListener);


        customerTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                Cus_selection = (String) parent.getItemAtPosition(position);
             customer_textInput.setError(null);
                customer_textInput.clearFocus();
            }
        });
    }
    public void  fillCustomerspinner(List<CustomerInfo>customerInfoList){
       // customerInfoList.clear();
        customerNames.clear();





        for (int i = 0; i < customerInfoList.size(); i++) {

            customerNames.add(customerInfoList.get(i).getCustomerName());

        }

        ArrayAdapter<String> customersAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, customerNames);

        customerTv.setAdapter(customersAdapter);
    }
    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.save:
                 if(vocher_Items.size()!=0) {
                     Log.e("vocher_Items==", vocher_Items.size() + "");

                     if(Cus_selection!=null&&!Cus_selection.equals("")) {
                         final Handler handler = new Handler();
                         handler.postDelayed(new Runnable() {
                             @Override
                             public void run() {

                                     SaveDetialsVocher();
                                     SaveMasterVocher();
                                     generalMethod.showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                                 customer_textInput.setError(null);
                             }

                         }, 100);


                     }else

                         customer_textInput.setError(getResources().getString(R.string.required));
                 }
                 else
                     generalMethod.showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.fillbasket), "");
                      break;

                case R.id.cancel_btn:

                    try {
                        Handler h = new Handler(Looper.getMainLooper());
                        h.post(new Runnable() {
                            public void run() {


                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.confirm_title))
                                        .setContentText(getResources().getString(R.string.messageExit))
                                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                if (vocher_Items.size() != 0) {
                                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                                            .setContentText(getResources().getString(R.string.messageExit2))
                                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    sweetAlertDialog.dismissWithAnimation();

                                                                    vocher_Items.clear();
                                                                    fillAdapter();
                                                                    clearText();

                                                                    startActivity(new Intent(MainActivity.this,Login.class));

                                                                }
                                                            })
                                                            .setCancelButton(getResources().getString(R.string.No), new SweetAlertDialog.OnSweetClickListener() {
                                                                @Override
                                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                    sweetAlertDialog.dismiss();
                                                                }
                                                            })
                                                            .show();

                                                } else {
                                                    sweetAlertDialog.dismiss();
                                                    Intent intent = new Intent(MainActivity.this, Login.class);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }

                                        })
                                        .setCancelButton(getResources().getString(R.string.No), new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                sweetAlertDialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        });


                    }catch (Exception e){

                    }
                        break;
            }

        }
    };
//    View.OnKeyListener onKeyListener =new View.OnKeyListener() {
//        @Override
//        public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//            if (i != KeyEvent.KEYCODE_ENTER) {
//                {
//
//                    if (keyEvent.getAction() == KeyEvent.ACTION_UP)
//
//                        switch (view.getId()) {
//
//                            case R.id.item_code: {
//                                Log.e("itemcode==",itemcode.getText().toString().trim());
//                          item= mydatabase.itemsDao().getItembyCode(itemcode.getText().toString().trim());
//                                Log.e("item==",item.getItemK()+"");
//                                itemname.setText(item.getNAME());
//                                        itemqty.setText(item.getQty()+"");
//                                        itemprice.setText(item.getF_D()+"");
//                                        free.setText("");
//                                addItem(item.getITEMNO());
//                                break;
//                            }
//
//
//                        }
//                }
//                return true;
//            }
//            return false;
//        }
//    };
void addItem(String itemno){
//    Items items=new Items();
//    items.set
    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
    if(!CheckIsExistsINLocalList(itemno))
    {


        item.setQty(Double.parseDouble(itemqty.getText().toString().trim()));
        item.setAmount(item.getF_D() * item.getQty() * num_items);

        Log.e("vocher_Items4==", vocher_Items.size()+"") ;
        vocher_Items.add(item);


    }
    else {
        vocher_Items.get(pos).setQty(vocher_Items.get(pos).getQty()+Double.parseDouble(itemqty.getText().toString().trim()));

        vocher_Items.get(pos).setAmount(vocher_Items.get(pos).getF_D()* vocher_Items.get(pos).getQty()* num_items);
        Log.e("vocher_Items3==", vocher_Items.size()+"") ;


    }
        }

    }, 100);
    fillAdapter();
}
    private boolean CheckIsExistsINLocalList(String barcode) {


        boolean flag = false;
        if (vocher_Items.size() != 0)
            for (int i = 0; i < vocher_Items.size(); i++) {

                if (
                        GeneralMethod.convertToEnglish(vocher_Items.get(i).getITEMNO()).equals(GeneralMethod.convertToEnglish(barcode))

                ) {
                    pos = i;
                    flag = true;
                    break;

                } else {
                    flag = false;
                    continue;
                }
            }

        return flag;


    }
  void  fillAdapter(){

   //  clearText();
      final Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
          @Override
          public void run() {
              itemRec.setAdapter(new ItemsAdapter(vocher_Items,MainActivity.this));
              FillLastRowCalculations();

              itemname.setText("");
              itemqty.setText("");
              itemprice.setText("");
              free.setText("");

              customer_textInput.setError(null);
              AllVocherDiscount=0;
              Log.e("clearText","clearText");
              itemqty.setEnabled(false);
              itemcode.setText("");
              itemcode.requestFocus();
          }

      }, 100);
  }
    void clearText(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                itemname.setText("");
                itemqty.setText("");
                itemprice.setText("");
                free.setText("");
                itemcode.setText("");
                itemcode.requestFocus();
                customer_textInput.setError(null);
                AllVocherDiscount=0;
                Log.e("clearText","clearText");
                itemqty.setEnabled(false);
            }

        }, 100);


}


    void SaveDetialsVocher() {
        Log.e("vocher_ItemsSize==",vocher_Items.size()+"");
        for (int i = 0; i < vocher_Items.size(); i++) {
            ReceiptDetails ordersDetails = new ReceiptDetails();
            if(order.isChecked()) {
                ordersDetails.setVOUCHERTYPE(505);

            }
            else {
                ordersDetails.setVOUCHERTYPE(504);

            }
            if(cash.isChecked()) {
                ordersDetails.setPaymethod(1);

            }
            else {
                ordersDetails.setVOUCHERTYPE(2);

            }
            ordersDetails.setDiscount(vocher_Items.get(i).getDiscount());
            ordersDetails.setItemNo(vocher_Items.get(i).getITEMNO());
            ordersDetails.setFree(vocher_Items.get(i).getFree());
            ordersDetails.setItemName(vocher_Items.get(i).getNAME());
            ordersDetails.setQty(vocher_Items.get(i).getQty());
            ordersDetails.setPrice(vocher_Items.get(i).getF_D());
            ordersDetails.setDate(generalMethod.getCurentTimeDate(1));
            ordersDetails.setTime(generalMethod.getCurentTimeDate(2));
            ordersDetails.setAmount(vocher_Items.get(i).getAmount());
            ordersDetails.setTaxPercent(vocher_Items.get(i).getTAXPERC());
            ordersDetails.setUnit("1");
            if(ordersDetails.getUnit().equals("One Unit"))
                ordersDetails.setWhichUNIT("0");
            else
                ordersDetails.setWhichUNIT("1");

            ordersDetails.setWhichUNITSTR("One Unit");
            double  num_items = mydatabase.itemUnitsDao().getConvRatebyitemnum( ordersDetails.getItemNo());
            ordersDetails.setWHICHUQTY(num_items+"");
            ordersDetails.setCustomerId(mydatabase.customers_dao().getCustmByName(Cus_selection));
            ordersDetails.setIsPosted(0);
            ordersDetails.setArea("");

//            ordersDetails.setUnit(vocher_Items.get(i).getUnit().equals("One Unit") ? 0 : 1);

            //Discount calcualtios
            ordersDetails.setTotalDiscVal( vocher_Items.get(i).getAmount()* ordersDetails.getDiscount());
            ordersDetails.setTotal(vocher_Items.get(i).getAmount()- ordersDetails.getTotalDiscVal());

            //Tax calcualtios
            Log.e("fd=",vocher_Items.get(i).getF_D()+"ttax"+vocher_Items.get(i).getTAXPERC());
            ordersDetails.setTax(vocher_Items.get(i).getF_D()*vocher_Items.get(i).getTAXPERC());
            Log.e("tax=",ordersDetails.getTax()+"");
            ordersDetails.setTaxValue(ordersDetails.getTax()*vocher_Items.get(i).getQty());

            // ضريبة شاملة
            double subtotal=0;
            subtotal= ordersDetails.getAmount()-ordersDetails.getTaxValue()-ordersDetails.getTotalDiscVal();

            ordersDetails.setSubtotal(subtotal);
            Log.e("ordersDetails.getSubtotal()=",ordersDetails.getSubtotal()+"");

            double nettotal=0;
            nettotal=ordersDetails.getSubtotal()+ordersDetails.getTaxValue();
            ordersDetails.setNetTotal(nettotal);
            Log.e("ordersDetails.getNetTotal()=",ordersDetails.getNetTotal()+"");



            // ضريبة خاضعة
      /*    double subtotal=ordersDetails.getAmount()-ordersDetails.getDiscount();
            ordersDetails.setSubtotal(subtotal);*/







            ordersDetailslist.add(ordersDetails);
            Log.e("hereordersDetailslist==",ordersDetailslist.size()+"");




            Log.e("ordersDetails.getarea",ordersDetails.getArea()+"");
            Log.e("ordersDetails.getAmount()=",ordersDetails.getAmount()+"");
            Log.e("ordersDetails.getTaxValue()=",ordersDetails.getTaxValue()+"");
            Log.e("ordersDetails.getDiscount()=",ordersDetails.getDiscount()+"");






        }


    }
    void SaveMasterVocher() {

        ReceiptMaster orderMaster = new ReceiptMaster();
        orderMaster.setIsPosted(0);

        if(cash.isChecked()) {
            orderMaster.setPaymethod(1);

        }
        else {
            orderMaster.setVOUCHERTYPE(2);

        }

        orderMaster.setUserNo(Login.salmanNumber);
        orderMaster. setDiscounttype(distype);
        if(order.isChecked())
        orderMaster.setVOUCHERTYPE(505);
        else
            orderMaster.setVOUCHERTYPE(504);
        orderMaster.setDate(generalMethod.getCurentTimeDate(1));
        orderMaster.setTime(generalMethod.getCurentTimeDate(2));
        // orderMaster.setCustomerId();

        orderMaster.setCustomerId(mydatabase.customers_dao().getCustmByName(Cus_selection) );




        Log.e("netTotal444==",netTotal+"");

        orderMaster.setNetTotal(netTotal);
        Log.e("netTotal777==",orderMaster.getNetTotal()+"");



        double totalnetsales=0;
        double totalqty=0;
        double totaltax=0;
        double subtotal=0;
        double dis=0;
        for (int x = 0; x < ordersDetailslist.size(); x++) {
            totalqty+=ordersDetailslist.get(x).getQty();
            totalnetsales+= ordersDetailslist.get(x).getNetTotal();
            totaltax+= ordersDetailslist.get(x).getTaxValue();
            subtotal+= ordersDetailslist.get(x).getSubtotal();
            dis+= ordersDetailslist.get(x).getTotalDiscVal();
        }
        orderMaster.setTotalQty(totalqty);
     if(AllVocherDiscount<=0)
     {
         Log.e("totdisvalu1==",(totalnetsales*AllVocherDiscount)+"");
         orderMaster.setTotalVoucherDiscount(0);
         orderMaster.setVoucherDiscountvalue(AllVocherDiscount);
         orderMaster.setNetTotal(totalnetsales);
         orderMaster.setSubTotal(subtotal );
     }
     else {
         if( orderMaster.getDiscounttype()==0) { //value
             Log.e("totdisvalu2==",(totalnetsales*AllVocherDiscount)+"");
             orderMaster.setVoucherDiscountvalue(AllVocherDiscount);
             orderMaster.setNetTotal(totalnetsales - AllVocherDiscount);
             orderMaster.setSubTotal(subtotal - AllVocherDiscount);

         }else { //perc

             orderMaster.setVoucherDiscountPerc(AllVocherDiscount);
             orderMaster.setNetTotal(totalnetsales-(totalnetsales*AllVocherDiscount));
             orderMaster.setSubTotal(subtotal-(subtotal*AllVocherDiscount));
             Log.e("totdisvalu3==",(totalnetsales*AllVocherDiscount)+"");
         }

         }
        orderMaster.setTotalVoucherDiscount(AllVocherDiscount);
        orderMaster.setTax(totaltax);






            SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
            vohno =Integer.parseInt(sharedPref.getString(Login.maxVoch_PREF, "")) ;
     orderno =Integer.parseInt(sharedPref.getString(Login.max_Order_PREF, "")) ;
            Log.e("vohno===",vohno+"");
            // vohno =Integer.parseInt(vohno) ;

     if(orderMaster.getVOUCHERTYPE()==504)
     {   orderMaster.setVhfNo(vohno);
            mydatabase.receiptMaster_dao().insertOrder(orderMaster);

            for (int l = 0; l < ordersDetailslist.size(); l++) {
                ordersDetailslist.get(l).setVhfNo(vohno);
                mydatabase.receiptDetails_dao().insertOrder(ordersDetailslist.get(l));
            }
        }

        else
        { orderMaster.setVhfNo(orderno);
            mydatabase.receiptMaster_dao().insertOrder(orderMaster);

            for (int l = 0; l < ordersDetailslist.size(); l++) {
                ordersDetailslist.get(l).setVhfNo(orderno);
                mydatabase.receiptDetails_dao().insertOrder(ordersDetailslist.get(l));
            }

        }

        ordersDetailslist.clear();
        vocher_Items.clear();
        UpdateMaxVo();

        fillAdapter();

    }
    void   UpdateMaxVo(){

        SharedPreferences.Editor editor = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE).edit();
        Log.e("vohno==",vohno+"");
        editor.putString(Login.maxVoch_PREF,String.valueOf(++vohno) );
        editor.apply();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_example, popup.getMenu());
        popup.show();
    }


void importdata(){
    {

        mydatabase.itemsDao().deleteAll();
        mydatabase.itemUnitsDao().deleteAll();
        mydatabase.customers_dao().deleteAll();
//                            appDatabase.usersDao().deleteAll();
        mydatabase.itemUnitsDao().deleteAll();
        ImportData.AllImportItemlist.clear();
        Login.         allUnitDetails.clear();
        Login.  allCustomers.clear();
        listAllVendor.clear();
        Login.    allUsers.clear();

        //          importData.getAllItems3();


        importData.getAllItems(new ImportData.GetItemsCallBack() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray itemsArray = response.getJSONArray("Items_Master");

                    for (int i = 0; i < itemsArray.length(); i++) {

                        Items item = new Items();
                        item.setNAME(itemsArray.getJSONObject(i).getString("NAME"));
                        item.setBARCODE(itemsArray.getJSONObject(i).getString("BARCODE"));
                        item.setITEMNO(itemsArray.getJSONObject(i).getString("ITEMNO"));

                        item.setItemK(itemsArray.getJSONObject(i).getString("ItemK"));
                        item.setF_D(Double.parseDouble(itemsArray.getJSONObject(i).getString("F_D")));
                        item.setCATEOGRYID(itemsArray.getJSONObject(i).getString("CATEOGRYID"));

                        item.setTAXPERC(Double.parseDouble(itemsArray.getJSONObject(i).getString("TAXPERC")) / 100);
                        item.setQty(1);
                        ImportData.AllImportItemlist.add(item);

                    }

                    mydatabase.itemsDao().addAll(ImportData.AllImportItemlist);

                    JSONArray unitsArray = response.getJSONArray("Item_Unit_Details");

                    for (int i = 0; i < unitsArray.length(); i++) {

                        Item_Unit_Details itemUnitDetails = new Item_Unit_Details();
                        itemUnitDetails.setCompanyNo(unitsArray.getJSONObject(i).getString("COMAPNYNO"));
                        itemUnitDetails.setItemNo(unitsArray.getJSONObject(i).getString("ITEMNO"));
                        itemUnitDetails.setUnitId(unitsArray.getJSONObject(i).getString("UNITID"));
                        itemUnitDetails.setConvRate(Double.parseDouble(unitsArray.getJSONObject(i).getString("CONVRATE")));

                        Login.   allUnitDetails.add(itemUnitDetails);

                    }

                    mydatabase.itemUnitsDao().addAll(Login.allUnitDetails);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                importData.getAllCustomers(new ImportData.GetCustomersCallBack() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {

                            try {

                               Login. allCustomers.add(new CustomerInfo(
                                        response.getJSONObject(i).getString("CUSTID"),
                                        response.getJSONObject(i).getString("CUSTNAME"),
                                        response.getJSONObject(i).getString("MOBILE"),
                                        1,0));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mydatabase.customers_dao().addAll(customerInfoList);
                        importData.getAllUsers(new ImportData.GetUsersCallBack() {
                            @Override
                            public void onResponse(JSONArray response) {


                                for (int i = 0; i < response.length(); i++) {

                                    try {

                                        Login.allUsers.add(new User(
                                                response.getJSONObject(i).getString("SALESNO"),
                                                response.getJSONObject(i).getString("ACCNAME").toLowerCase(Locale.ROOT),
                                                response.getJSONObject(i).getString("USER_PASSWORD"),
                                                Integer.parseInt(response.getJSONObject(i).getString("USERTYPE")),
                                                Integer.parseInt("1"),
                                                1));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                mydatabase.usersDao().addAll(Login.allUsers);


                            }

                            @Override
                            public void onError(String error) {


                            }
                        }, ipAddress, ipPort, coNo);


                    }

                    @Override
                    public void onError(String error) {

                        if (!((error + "").contains("SSLHandshakeException") || (error + "").equals("null") ||
                                (error + "").contains("ConnectException") || (error + "").contains("NoRouteToHostException"))) {

                        }


                    }
                });
            }

            @Override
            public void onError(String error) {

                if (!((error + "").contains("SSLHandshakeException") || (error + "").equals("null") ||
                        (error + "").contains("ConnectException") || (error + "").contains("NoRouteToHostException"))) {


                }

            }
        });


    }
}
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
//
//        switch (item.getItemId()) {
//
//            case R.id.importdata:
//                Log.e("importdata==","importdata");
//                importdata();
//                return true;
//            case R.id.exportdata:
//                exportData.exportSalesVoucherM();
//                return true;
//            default:
//                return false;
//        }
//    }


void addTotalDisDailog(){
    final Dialog dialog = new Dialog(MainActivity.this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.addtotaldiscount);

    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.19);
    dialog.getWindow().setAttributes(lp);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    ImageView cancelButton=dialog.findViewById(R.id.cancelButton);
    AppCompatButton okButton=dialog.findViewById(R.id.okButton);
    EditText    discEditText=dialog.findViewById(R.id.discEditText);
   RadioButton discValueRadioButton=dialog.findViewById(R.id.discValueRadioButton);
    RadioButton  discPercRadioButton=dialog.findViewById(R.id.discPercRadioButton);

    dialog.show();


    cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });
    okButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!discEditText.getText().toString().trim().equals(""))
            {
                if(discPercRadioButton.isChecked())
                {
                    distype=1;
                    AllVocherDiscount=Double.parseDouble(discEditText.getText().toString().trim())*0.01;
//                total_dis.setText(AllVocherDiscount+ "");


                }
                else
                {  distype=0;
                    AllVocherDiscount=Double.parseDouble(discEditText.getText().toString().trim());
          //          total_dis.setText(AllVocherDiscount+ "");
                }
                FillLastRowCalculations();
                dialog.dismiss();
            }
            else
                discEditText.setError(getResources().getString(R.string.required));
        }
    });
    }

    void FillLastRowCalculations(){
        double totalnetsales=0;
        double totaltax=0;
        double dis=0;
        double disvalu=0;
        double amount=0;
        double tax=0;
        double taxvalu=0;
        double subtotal=0;
        double netsal=0;
        double totalnetsal=0;
    for(int i=0;i<vocher_Items.size();i++)
    {   amount=vocher_Items.get(i).getF_D()*vocher_Items.get(i).getQty();
        disvalu=vocher_Items.get(i).getDiscount()*amount;

        tax=vocher_Items.get(i).getF_D()*vocher_Items.get(i).getTAXPERC();
        taxvalu=tax*vocher_Items.get(i).getQty();
        subtotal=amount-disvalu;
        netsal=subtotal;


        totalnetsal+=netsal;

    }

        Log.e("totdisvalu22==",(totalnetsal*AllVocherDiscount)+"");
        netsales.setText(GeneralMethod.convertToEnglish(String.  format("%.3f",Math.abs(totalnetsal-(totalnetsal*AllVocherDiscount)))));


        vlauoftotaldis.setText(GeneralMethod.convertToEnglish(String.  valueOf(totalnetsal*AllVocherDiscount)));

    }


    TextView.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {


            Log.e("keyEvent.getAction()", keyEvent.getAction() + "");



                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (i) {
                            case KeyEvent.KEYCODE_DPAD_CENTER:
                            case KeyEvent.KEYCODE_ENTER:


                        switch (view.getId()) {

                            case R.id.item_code: {
                                if ((!itemcode.getText().equals(""))  ) {



                                    Log.e("itemcode==",itemcode.getText().toString().trim());
                                    item= mydatabase.itemsDao().getItembyCode(itemcode.getText().toString().trim());
                                    Log.e("itemcode==",item+"");
                                    if(item!=null)
                                    {

                                        itemqty.requestFocus();
                                        Log.e("itemname==",item.getNAME()+"");
                                        Log.e("qty==",item.getQty()+"");
                                        Log.e("itemprice==",item.getF_D()+"");
                                        Log.e("free==","");
                                        Log.e("itemcode==",item.getITEMNO()+"");

                                        itemname.setText(item.getNAME());
                                        itemqty.setText(item.getQty()+"");
                                        itemprice.setText(item.getF_D()+"");
                                        free.setText("");

                                        //         addItem(item.getITEMNO());
                                        itemqty.requestFocus();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                itemname.setText(item.getNAME());
                                                itemqty.setText(item.getQty()+"");
                                                itemprice.setText(item.getF_D()+"");
                                                free.setText("");

                                                //         addItem(item.getITEMNO());
                                                itemqty.requestFocus();
                                                itemqty.setEnabled(true);
                                            }
                                        }, 100);
                                    }
                                    else{

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.e("else3==","else");
                                                itemcode.setError("");
                                                itemcode.requestFocus();
                                                itemqty.setEnabled(false);
                                            }
                                        }, 100);
                                    }
//
                                }     else{
                                    Log.e("else2==","else");
                                    itemcode.setError("");
                                    itemcode.requestFocus();
                                    itemqty.setEnabled(false);

                                }

                                break;
                            }
                            case R.id.qty:
                                if ((!itemcode.getText().equals(""))) {
                                    if ((!itemqty.getText().equals(""))) {


                                        Log.e("KeyListeneritemqty==", itemqty.getText().toString().trim());


                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                addItem(item.getITEMNO());
                                                itemcode.requestFocus();


                                            }

                                        }, 100);


//
                                    } else {
                                        Log.e("else==", "else");
                                        itemqty.setError("");
                                        itemqty.requestFocus();
                                    }
                                }else {
                                    Log.e("else==", "else");
                                    itemcode.setError("");
                                    itemcode.requestFocus();
                                }
                                break;


                        }
                }

                return true;
            }
            return false;
        }
    };


}
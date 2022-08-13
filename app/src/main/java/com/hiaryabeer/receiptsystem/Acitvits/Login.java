package com.hiaryabeer.receiptsystem.Acitvits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hiaryabeer.receiptsystem.R;
import com.hiaryabeer.receiptsystem.models.AppDatabase;
import com.hiaryabeer.receiptsystem.models.CustomerInfo;
import com.hiaryabeer.receiptsystem.models.GeneralMethod;
import com.hiaryabeer.receiptsystem.models.ImportData;
import com.hiaryabeer.receiptsystem.models.Item_Unit_Details;
import com.hiaryabeer.receiptsystem.models.Items;
import com.hiaryabeer.receiptsystem.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Login extends AppCompatActivity {
 AppCompatButton LoginButton,OpenSetting;
    ImageView editVochNo,saveVochNo;
    public final static String SETTINGS_PREFERENCES = "SETTINGS_PREFERENCES";
    public final static String IP_PREF = "IP_Address";
    public final static String PORT_PREF = "IP_Port";
    public final static String CONO_PREF = "Company_No";
    public final static String maxVoch_PREF = "maxVoch";
    public final static String Ordermaxserial_PREF = "Ordermaxserial";
   public static String  ipAddress, ipPort, coNo,Max_Voch,Max_Order;
    private TextInputEditText ipEdt, portEdt, coNoEdt,maxVochEdt,MaxOrderEdt;
    ImportData importData;
    public static List<Item_Unit_Details> allUnitDetails=new ArrayList<>();
    public static List<CustomerInfo> allCustomers=new ArrayList<>();
    AppDatabase mydatabase;
    EditText unameEdt, passEdt;
    EditText editPassword;
    public static String salmanNumber="";
    public static List<User> allUsers= new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        init();

        if (!checkIpSettings())
            showSettingsDialog();
        importData=new ImportData(Login.this);
    }
    public void init(){
        mydatabase=AppDatabase.getInstanceDatabase(Login.this);
        unameEdt = findViewById(R.id.unameEdt);
        passEdt = findViewById(R.id.passEdt);
        LoginButton=findViewById(R.id.login);
        OpenSetting=findViewById(R.id.OpenSetting);
        OpenSetting.setOnClickListener(onClickListener);
        LoginButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
             case R.id.login:

                 if (!checkIpSettings()) {
                     showSettingsDialog();

                 } else
                     checkUnameAndPass();
               // startActivity(new Intent(Login.this, MainActivity.class));
                break;

                case R.id.OpenSetting:
                   showSettingsDialog();
                    break;
            }

    }
   };
    private boolean checkIpSettings() {

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");
        Max_Voch = sharedPref.getString(maxVoch_PREF, "");
        Max_Order  = sharedPref.getString(Ordermaxserial_PREF, "");
        Log.e("IP_PREF", ipAddress + "");
        Log.e("PORT_PREF", ipPort);
        Log.e("CONO_PREF", coNo);
        Log.e("Max_Voch",  Max_Voch);
        return !(ipAddress + "").trim().equals("")
                &&
                !(coNo + "").trim().equals("");


    }
    void showSettingsDialog() {

        final Dialog ip_settings_dialog = new Dialog(Login.this);
        ip_settings_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ip_settings_dialog.setCancelable(false);
        ip_settings_dialog.setContentView(R.layout.ip_settings_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(ip_settings_dialog.getWindow().getAttributes());
        lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.19);
        ip_settings_dialog.getWindow().setAttributes(lp);

        ip_settings_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ip_settings_dialog.show();
        editVochNo= ip_settings_dialog.findViewById(R.id.editVochNo);

        int MaxVo= mydatabase.receiptMaster_dao(). getLastVoherNo();




        editVochNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(Login.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.passwordsettings);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());

                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
//                    TextView cancelButton = (TextView) dialog.findViewById(R.id.cancel);

                editPassword = (EditText) dialog.findViewById(R.id.passowrdEdit);


                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editPassword.getText().toString().equals("")) {
                            if(editPassword.getText().toString().trim().equals("1234"))
                            {


                                maxVochEdt.setEnabled(true);
                                maxVochEdt.requestFocus();
                                dialog.dismiss();



                            }else
                            {
                                editPassword.setError("password not correct");
                            }
                        } else {
                            editPassword.setError("Required");
                        }


                    }
                });
//
//                    cancelButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
                dialog.show();



            }
        });
        ipEdt = ip_settings_dialog.findViewById(R.id.ipEdt);
        portEdt = ip_settings_dialog.findViewById(R.id.portEdt);
        coNoEdt = ip_settings_dialog.findViewById(R.id.coNoEdt);
        maxVochEdt= ip_settings_dialog.findViewById(R.id.MaxVoEdt);
        MaxOrderEdt= ip_settings_dialog.findViewById(R.id.OrderMaxEdt);
        MaxOrderEdt.setEnabled(true);
        maxVochEdt.setEnabled(true);
        TextInputLayout textInputIpAddress, textInputPort, textInputCoNo,textInputMaxVoch;
        textInputIpAddress = ip_settings_dialog.findViewById(R.id.textInputIpAddress);
        textInputPort = ip_settings_dialog.findViewById(R.id.portInputIpAddress);
        textInputCoNo = ip_settings_dialog.findViewById(R.id.textInputCoNo);
        textInputMaxVoch = ip_settings_dialog.findViewById(R.id.textInputMaxNu);
        Button okBtn, cancelBtn;
        okBtn = ip_settings_dialog.findViewById(R.id.submitBtn);
        cancelBtn = ip_settings_dialog.findViewById(R.id.cancel_btn);

        textInputMaxVoch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!maxVochEdt.getText().toString().trim().equals(""))
                {
                    Log.e("MaxVo==",MaxVo+"");

                    if(Integer.parseInt(maxVochEdt.getText().toString().trim())>MaxVo)
                    {
                        maxVochEdt.setEnabled(false);
                        SharedPreferences.Editor editor = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE).edit();
                        editor.putString(maxVoch_PREF,  maxVochEdt.getText().toString().trim());
                        editor.putString(Ordermaxserial_PREF,  MaxOrderEdt.getText().toString().trim());

                        editor.apply();
                    }
                    else
                    {
                        maxVochEdt.setText("");
                        if(MaxVo!=0)
                            GeneralMethod.showSweetDialog(Login.this,0,"",getResources().getString(R.string.MaxVoMsg1)+" "+MaxVo+getResources().getString(R.string.MaxVoMsg2)+(MaxVo+1)+"  " +getResources().getString(R.string.MaxVoMsg3));
                        else   GeneralMethod.showSweetDialog(Login.this,0,"",getResources().getString(R.string.MaxVoMsg2)+(MaxVo+1)+"  " +getResources().getString(R.string.MaxVoMsg3));

                        maxVochEdt.setError("");
                    }

                }}
        });
        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

        ipEdt.setText(sharedPref.getString(IP_PREF, ""));
        portEdt.setText(sharedPref.getString(PORT_PREF, ""));
        coNoEdt.setText(sharedPref.getString(CONO_PREF, ""));
        maxVochEdt.setText(sharedPref.getString(maxVoch_PREF, ""));
        MaxOrderEdt.setText(sharedPref.getString(Ordermaxserial_PREF, ""));
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ip_settings_dialog.dismiss();

            }
        });

        ipEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                textInputIpAddress.setError(null);

            }
        });

//        portEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                textInputPort.setError(null);
//
//            }
//        });

        coNoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                textInputCoNo.setError(null);

            }
        });
        maxVochEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                textInputMaxVoch.setError(null);

            }
        });
//        MaxOrderEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                textInputMaxVoch.setError(null);
//
//            }
//        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ipAddress1 = ipEdt.getText().toString().trim();
                String port1 = portEdt.getText().toString().trim();
                String coNo1 = coNoEdt.getText().toString().trim();
                String maxvo1 =maxVochEdt.getText().toString().trim();
                String maxOrder =MaxOrderEdt.getText().toString().trim();
                if (!ipAddress1.equals("")) {



                        if (!coNo1.equals(""))
                        {

                            SharedPreferences.Editor editor = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putString(IP_PREF, ipAddress1);
                            editor.putString(PORT_PREF, port1);
                            editor.putString(CONO_PREF, coNo1);

                            if(!maxOrder.equals(""))
                                editor.putString(Ordermaxserial_PREF, maxOrder);
                            else
                                editor.putString(Ordermaxserial_PREF, "1");
                            editor.apply();
                           if(!maxvo1.equals(""))
                               editor.putString(maxVoch_PREF, maxOrder);
                           else
                               editor.putString(maxVoch_PREF, "1");
                            editor.apply();

                            ipAddress = sharedPref.getString(IP_PREF, "");
                            ipPort = sharedPref.getString(PORT_PREF, "");
                            coNo = sharedPref.getString(CONO_PREF, "");
                            Max_Voch = sharedPref.getString(maxVoch_PREF, "");
                            Max_Order = sharedPref.getString(Ordermaxserial_PREF, "");
                            ip_settings_dialog.dismiss();
                          mydatabase.itemsDao().deleteAll();
                            mydatabase.itemUnitsDao().deleteAll();
                        mydatabase.customers_dao().deleteAll();
//                            appDatabase.usersDao().deleteAll();
                            mydatabase.itemUnitsDao().deleteAll();
                            ImportData.AllImportItemlist.clear();
                            allUnitDetails.clear();
                            allCustomers.clear();
                           allUsers.clear();
                            importData=new ImportData(Login.this);
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

                                            allUnitDetails.add(itemUnitDetails);

                                        }

                                        mydatabase.itemUnitsDao().addAll(allUnitDetails);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    importData.getAllCustomers(new ImportData.GetCustomersCallBack() {
                                        @Override
                                        public void onResponse(JSONArray response) {

                                            for (int i = 0; i < response.length(); i++) {

                                                try {

                                                    allCustomers.add(new CustomerInfo(
                                                            response.getJSONObject(i).getString("CUSTID"),
                                                            response.getJSONObject(i).getString("CUSTNAME"),
                                                            response.getJSONObject(i).getString("MOBILE"),
                                                            1));

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            mydatabase.customers_dao().addAll(allCustomers);

                                            importData.getAllUsers(new ImportData.GetUsersCallBack() {
                                                @Override
                                                public void onResponse(JSONArray response) {


                                                    for (int i = 0; i < response.length(); i++) {

                                                        try {

                                                            allUsers.add(new User(
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

                                                    mydatabase.usersDao().addAll(allUsers);


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



                            ip_settings_dialog.dismiss();

                        } else {

                            textInputCoNo.setError(getString(R.string.required));


                        }



                } else {

                    textInputIpAddress.setError(getString(R.string.required));


                }

            }
        });

    }
    void checkUnameAndPass() {

        String uname = unameEdt.getText().toString().trim().toLowerCase(Locale.ROOT) + "";
        String pass = passEdt.getText().toString().trim() + "";

        allUsers = mydatabase.usersDao().getAllUsers();
        boolean valid = false;
        int i;

        if (allUsers.size() != 0) {

            if (!uname.equals("")) {

                if (!pass.equals("")) {

                    for (i = 0; i < allUsers.size(); i++) {

                        if ((uname.equals(allUsers.get(i).getUserId())||Integer.parseInt(uname)==Integer.parseInt(allUsers.get(i).getUserId())) &&
                                pass.equals(allUsers.get(i).getUserPassword()))  {
                              salmanNumber=uname;
                            valid = true;
                            break;

                        }
                    }

                    if (valid) {


                        Toast.makeText(Login.this, "SUCCESS LOGIN!", Toast.LENGTH_SHORT).show();

                   //     addUserLogs(i);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(Login.this, "username or password not correct", Toast.LENGTH_SHORT).show();

                    }



                } else {


                    passEdt.setError(getString(R.string.required));
//                passEdt.setError("");

                }

            } else {


                unameEdt.setError(getString(R.string.required));

            }

        } else {

            Toast.makeText(this, "No Saved Users Found !", Toast.LENGTH_SHORT).show();


        }


    }
}
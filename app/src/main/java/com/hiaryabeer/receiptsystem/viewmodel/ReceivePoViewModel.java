package com.hiaryabeer.receiptsystem.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hiaryabeer.receiptsystem.Acitvits.Login;
import com.hiaryabeer.receiptsystem.Interfaces.ApiService;
import com.hiaryabeer.receiptsystem.models.AppDatabase;
import com.hiaryabeer.receiptsystem.models.GeneralMethod;
import com.hiaryabeer.receiptsystem.models.POitems;
import com.hiaryabeer.receiptsystem.models.PoInfo;
import com.hiaryabeer.receiptsystem.models.RetrofitInstance;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;
import static com.hiaryabeer.receiptsystem.Acitvits.Login.SETTINGS_PREFERENCES;

public class ReceivePoViewModel  extends ViewModel {
    public MutableLiveData<List<PoInfo>> PoInfoMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<List<POitems>> PoItemsMutableLiveData=new MutableLiveData<>();
    ApiService apiService;
    private static Context context;
    public String ipAddress = "", CONO = "",ipPort="", headerDll = "", link = "";
    public static SweetAlertDialog pditem,pDialog,pDialog2, pDialog3, pDialog4,pDialog5;
    AppDatabase my_dataBase;
    SharedPreferences sharedPref;
    private void getIpAddress() {
        headerDll="";

        ipAddress = Login.ipAddress;
        ipPort = Login.ipPort;
        CONO =Login.coNo;

    }
    public ReceivePoViewModel() {

        //my_dataBase = AppDatabase.getInstanceDatabase(context);
        try {
            getIpAddress();
            link = "http://" + ipAddress.trim() + headerDll.trim();

            Retrofit retrofit = RetrofitInstance.getInstance(link);
            Log.e("link",link+"");
            apiService = retrofit.create(ApiService.class);
            //sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

        } catch (Exception e) {
            Log.e("Exception==",e.getMessage());

        }
    }

    public void fetchPOData(String VHFNO,Context context) {
        pditem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pditem.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pditem.setTitleText(" Start get Data");
        pditem.setCancelable(false);
        pditem.show();
        Call<List<PoInfo>> myData = apiService.GetPoInfo(CONO,VHFNO);

        myData.enqueue(new Callback<List<PoInfo>>() {

            @Override
            public void onResponse(Call<List<PoInfo>> call, retrofit2.Response<List<PoInfo>> response) {

                if (!response.isSuccessful()) {
                    PoInfoMutableLiveData.setValue(null);
                    Log.e("fetchPOData", "not=" + response.message()+"  "+call.request());
                    GeneralMethod.showSweetDialog(context, 0, response.message(), null);
                    pditem.dismiss();
                } else {
                    Log.e("fetchPOData", "onResponse=" + response.message());
                    PoInfoMutableLiveData.setValue(response.body());

                    fetchPOItems( VHFNO,context);


                }
            }

            @Override
            public void onFailure(Call<List<PoInfo>> call, Throwable t) {
                if(t.getMessage().contains("Failed to connect"))
                    GeneralMethod.showSweetDialog(context, 0, "No Internet Connection", null);

               else if(t.getMessage().contains("Expected BEGIN_ARRAY but was BEGIN_OBJECT"))
                    GeneralMethod.showSweetDialog(context, 0, "Not found", null);

               else    GeneralMethod.showSweetDialog(context, 0, t.getMessage()+"", null);
                Log.e("fetchPOData", "=" + t.getMessage()+"  "+call.request());
                PoInfoMutableLiveData.setValue(null);
                pditem.dismiss();

            }
        });
    }
    public void fetchPOItems(String VHFNO,Context context) {

        Call<List<POitems>> myData = apiService.GetPoItems(CONO,VHFNO);

        myData.enqueue(new Callback<List<POitems>>() {

            @Override
            public void onResponse(Call<List<POitems>> call, retrofit2.Response<List<POitems>> response) {

                if (!response.isSuccessful()) {
                    PoItemsMutableLiveData.setValue(null);
                    Log.e("fetchPOItems", "not=" + response.message()+"  "+call.request());

                    pditem.dismiss();

                } else {
                    Log.e("fetchPOItems", "onResponse=" + response.body().get(0).getItemOCode());
                   PoItemsMutableLiveData.setValue(response.body());
                    pditem.dismiss();


                }
            }

            @Override
            public void onFailure(Call<List<POitems>> call, Throwable t) {
                Log.e("fetchPOItems", "=" + t.getMessage()+"  "+call.request());
                PoItemsMutableLiveData.setValue(null);
                pditem.dismiss();

            }
        });
    }
}

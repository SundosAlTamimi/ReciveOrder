package com.hiaryabeer.receiptsystem.models;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;


import com.hiaryabeer.receiptsystem.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class GeneralMethod {
    public Context myContext;
    public AppDatabase my_dataBase;

    public GeneralMethod(Context context) {
        this.myContext = context;
        my_dataBase = AppDatabase.getInstanceDatabase(myContext);
    }

    public static void showSweetDialog(Context context, int type, String title, String content) {
        switch (type) {
            case 0://Error Type

                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(title)
                        .setContentText(content)

                        .setConfirmText(context.getString(R.string.ok))

                        .show();
                break;
            case 1://Success Type
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;
            case 3://warning Type
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(context.getString(R.string.ok))
                        .show();
                break;

        }
    }

    public String getCurentTimeDate(int flag) {
        String dateCurent = "", timeCurrent, dateTime = "";
        Date currentTimeAndDate;
        SimpleDateFormat dateFormat, timeformat;
        currentTimeAndDate = Calendar.getInstance().getTime();
        if (flag == 1)// return date
        {

            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateCurent = dateFormat.format(currentTimeAndDate);
            dateTime = convertToEnglish(dateCurent);

        } else {
            if (flag == 2)// return time
            {
                timeformat = new SimpleDateFormat("hh:mm");
                dateCurent = timeformat.format(currentTimeAndDate);
                dateTime = convertToEnglish(dateCurent);
            }
        }
        return dateTime;

    }

    public static String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("??", "1")).replaceAll("??", "2")).replaceAll("??", "3")).replaceAll("??", "4")).replaceAll("??", "5")).replaceAll("??", "6")).replaceAll("??", "7")).replaceAll("??", "8")).replaceAll("??", "9")).replaceAll("??", "0").replaceAll("??", "."));
        return newValue;
    }

    public boolean validateNotEmpty(EditText editText) {
        if (!editText.getText().toString().trim().equals("")) {
            editText.setError(null);
            return true;
        } else {
            editText.setError(myContext.getResources().getString(R.string.required));
            editText.requestFocus();
            return false;
        }

    }

    public boolean validateNotZero(EditText editText) {
        if (!editText.getText().toString().trim().equals("0") && Integer.parseInt(editText.getText().toString().trim()) != 0) {
            editText.setError(null);
            return true;
        } else {
            editText.setError(myContext.getResources().getString(R.string.invaledZero));
            editText.requestFocus();
            return false;
        }

    }

}


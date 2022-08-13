package com.hiaryabeer.receiptsystem.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "ReceiptDetails_Table")
public class ReceiptDetails {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Serial")
    private int  Serial;

    @ColumnInfo(name = "vhfNo")
    private int vhfNo;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Time")
    private String time;

    @ColumnInfo(name = "Item_No")
    private String itemNo;

    @ColumnInfo(name = "Item_Name")
    private String itemName;

    @ColumnInfo(name = "Qty")
    private double qty;

    @ColumnInfo(name = "Discount")
    private double discount;

    @ColumnInfo(name = "Tax")
    private double tax;


    @ColumnInfo(name = "Total")
    private double total;

    @ColumnInfo(name = "Subtotal")
    private double Subtotal;

    @ColumnInfo(name = "Price")
    private double price;

    @ColumnInfo(name = "Customer_ID")
    private int customerId;
    @ColumnInfo(name = "Unit")
    private String Unit;
    @ColumnInfo(name = "IS_Posted", defaultValue = "0")
    private int isPosted;


    @ColumnInfo(name = "taxPercent")
    private double taxPercent;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "taxValue")
    private double taxValue;

    @ColumnInfo(name = "totalDiscVal")
    private double totalDiscVal;

    @ColumnInfo(name = "Item_Discount",defaultValue = "0")
    double Discount;
    @ColumnInfo(name = "NetTotal",defaultValue = "0")
    private double NetTotal;

    @ColumnInfo(name = "area")
    public String area;


    @ColumnInfo(name = "WhichUNIT")
    public String WhichUNIT;
    @ColumnInfo(name = "WhichUNITSTR")
    public String WhichUNITSTR;
    @ColumnInfo(name = "WHICHUQTY")
    public String WHICHUQTY;


    @ColumnInfo(name = "VOUCHERTYPE")
    private int  VOUCHERTYPE;

    @ColumnInfo(name = "Free")
    private double  Free;

    public double getFree() {
        return Free;
    }

    public void setFree(double free) {
        Free = free;
    }

    public int getVOUCHERTYPE() {
        return VOUCHERTYPE;
    }

    public void setVOUCHERTYPE(int VOUCHERTYPE) {
        this.VOUCHERTYPE = VOUCHERTYPE;
    }

    public int getSerial() {
        return Serial;
    }

    public void setSerial(int serial) {
        Serial = serial;
    }

    public int getVhfNo() {
        return vhfNo;
    }

    public void setVhfNo(int vhfNo) {
        this.vhfNo = vhfNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getNetTotal() {
        return NetTotal;
    }

    public void setNetTotal(double netTotal) {
        NetTotal = netTotal;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWhichUNIT() {
        return WhichUNIT;
    }

    public void setWhichUNIT(String whichUNIT) {
        WhichUNIT = whichUNIT;
    }

    public String getWhichUNITSTR() {
        return WhichUNITSTR;
    }

    public void setWhichUNITSTR(String whichUNITSTR) {
        WhichUNITSTR = whichUNITSTR;
    }

    public String getWHICHUQTY() {
        return WHICHUQTY;
    }

    public void setWHICHUQTY(String WHICHUQTY) {
        this.WHICHUQTY = WHICHUQTY;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(double subtotal) {
        Subtotal = subtotal;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(int isPosted) {
        this.isPosted = isPosted;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(double taxValue) {
        this.taxValue = taxValue;
    }

    public double getTotalDiscVal() {
        return totalDiscVal;
    }

    public void setTotalDiscVal(double totalDiscVal) {
        this.totalDiscVal = totalDiscVal;
    }
    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("VOUCHERNO", vhfNo+"");
            obj.put("VOUCHERTYPE", VOUCHERTYPE);
            obj.put("ITEMNO", itemNo);
            obj.put("UNIT", Unit);
            obj.put("QTY", qty);
            obj.put("UNITPRICE", price);
            obj.put("BONUS", Free);
            obj.put("ITEMDISCOUNTVALUE", totalDiscVal);
            obj.put("ITEMDISCOUNTPRC", discount);
            obj.put("VOUCHERDISCOUNT", discount);
            obj.put("TAXVALUE", taxValue);
            obj.put("TAXPERCENT", taxPercent);
            obj.put("COMAPNYNO", ExportData.CONO);
            obj.put("ISPOSTED", "0");
            obj.put("VOUCHERYEAR", "2022");
            obj.put("ITEM_DESCRITION", "");
            obj.put("SERIAL_CODE", "");
            obj.put("ITEM_SERIAL_CODE", "");

            obj.put("WHICHUNIT", WhichUNIT);
            obj.put("WHICHUNITSTR", WhichUNITSTR);

            obj.put("WHICHUQTY", WHICHUQTY);

            obj.put("ENTERQTY", qty);
            obj.put("ENTERPRICE", price);
            obj.put("UNITBARCODE", "");
            obj.put("CALCQTY", "");
            obj.put("ORGVHFNO", "");






        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}

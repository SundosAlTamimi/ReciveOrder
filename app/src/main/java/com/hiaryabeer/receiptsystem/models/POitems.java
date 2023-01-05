package com.hiaryabeer.receiptsystem.models;

public class POitems {
  String VHFNo;
   String       ItemOCode;
   String ItemNCode;
   String      Qty;
   String Bonus;
   String     ENTERPRICE;
    String   TransNo;
    String TTAXPERC;
    String  ITEMNAME;
    String  WHICHUQTY;

    public String getWHICHUQTY() {
        return WHICHUQTY;
    }

    public void setWHICHUQTY(String WHICHUQTY) {
        this.WHICHUQTY = WHICHUQTY;
    }

    public String getITEMNAME() {
        return ITEMNAME;
    }

    public void setITEMNAME(String ITEMNAME) {
        this.ITEMNAME = ITEMNAME;
    }

    public String getTTAXPERC() {
        return TTAXPERC;
    }

    public void setTTAXPERC(String TTAXPERC) {
        this.TTAXPERC = TTAXPERC;
    }

    public String getTransNo() {
        return TransNo;
    }

    public void setTransNo(String transNo) {
        TransNo = transNo;
    }

    public String getVHFNo() {
      return VHFNo;
   }

   public void setVHFNo(String VHFNo) {
      this.VHFNo = VHFNo;
   }

   public String getItemOCode() {
      return ItemOCode;
   }

   public void setItemOCode(String itemOCode) {
      ItemOCode = itemOCode;
   }

   public String getItemNCode() {
      return ItemNCode;
   }

   public void setItemNCode(String itemNCode) {
      ItemNCode = itemNCode;
   }

   public String getQty() {
      return Qty;
   }

   public void setQty(String qty) {
      Qty = qty;
   }

   public String getBonus() {
      return Bonus;
   }

   public void setBonus(String bonus) {
      Bonus = bonus;
   }

   public String getENTERPRICE() {
      return ENTERPRICE;
   }

   public void setENTERPRICE(String ENTERPRICE) {
      this.ENTERPRICE = ENTERPRICE;
   }
}

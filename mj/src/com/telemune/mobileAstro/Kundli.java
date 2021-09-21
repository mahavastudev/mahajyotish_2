package com.telemune.mobileAstro;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class Kundli
/*    */ {
/*    */   private String kundliType;
/*    */   private String[] housesDetail;
/*    */ 
/*    */   public Kundli(String kundliType)
/*    */   {
/* 16 */     this.kundliType = kundliType;
/* 17 */     this.housesDetail = new String[12];
/*    */   }
/*    */ 
/*    */   public void setType(String _type)
/*    */   {
/* 24 */     if (_type.equals("BIRTH"))
/* 25 */       this.kundliType = "BIRTH";
/* 26 */     else if (_type.equals("CUSP"))
/* 27 */       this.kundliType = "CUSP";
/*    */   }
/*    */ 
/*    */   public void setHouseData(int houseNumber, String houseData)
/*    */   {
/* 35 */     this.housesDetail[(houseNumber - 1)] = houseData;
/*    */   }
/*    */ 
/*    */   public String getHouseData(int houseNumber)
/*    */   {
/* 43 */     return this.housesDetail[(houseNumber - 1)];
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 48 */     return this.kundliType;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 55 */     return Arrays.toString(this.housesDetail);
/*    */   }
/*    */ }

/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.Kundli
 * JD-Core Version:    0.6.0
 */

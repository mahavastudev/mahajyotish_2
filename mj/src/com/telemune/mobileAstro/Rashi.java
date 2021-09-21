package com.telemune.mobileAstro;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public enum Rashi
/*    */ {
/* 34 */   Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces, UNKNOWN;
/*    */ 
/*    */   private static final Hashtable<Integer, Rashi> lookup;
/*    */ 
/* 35 */   static { lookup = new Hashtable();
/*    */ 
/* 51 */     int ordinal = 0;
/* 52 */     for (Rashi rashi : EnumSet.allOf(Rashi.class)) {
/* 53 */       lookup.put(Integer.valueOf(ordinal), rashi);
/* 54 */       ordinal++;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Rashi toRashi(String str)
/*    */   {
/*    */     try
/*    */     {
/* 41 */       return valueOf(str);
/*    */     }
/*    */     catch (Exception ex) {
/*    */     }
/* 45 */     return UNKNOWN;
/*    */   }
/*    */ 
/*    */   public Rashi fromOrdinal(int ordinal)
/*    */   {
/* 59 */     return (Rashi)lookup.get(Integer.valueOf(ordinal));
/*    */   }
/*    */ }

/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.Rashi
 * JD-Core Version:    0.6.0
 */

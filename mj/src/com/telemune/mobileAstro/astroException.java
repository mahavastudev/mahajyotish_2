package com.telemune.mobileAstro;
/*    */ 
/*    */ public class astroException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 4165245260087549202L;
/* 18 */   String exception = "";
/*    */ 
/*    */   public astroException()
/*    */   {
/* 23 */     this.exception = "unknown";
/*    */   }
/*    */ 
/*    */   public astroException(String _exception)
/*    */   {
/* 28 */     super(_exception);
/* 29 */     this.exception = _exception;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 34 */     return this.exception;
/*    */   }
/*    */ }

/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.astroException
 * JD-Core Version:    0.6.0
 */

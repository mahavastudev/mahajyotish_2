package com.telemune.mobileAstro;
/*    */ 
/*    */ public class MahaDashaBean extends KundliHouseBean
/*    */ {
/*    */   private String startTime;
/*    */   private String endTime;
/*    */   private String parent;
/*    */   private String child;
/*    */   private String subChild;
/*    */   private String year;
/*    */ 
/*    */   public MahaDashaBean()
/*    */   {
/* 22 */     this.startTime = null;
/* 23 */     this.endTime = null;
/* 24 */     this.parent = null;
/* 25 */     this.child = null;
/* 26 */     this.subChild = null;
/* 27 */     this.year = null;
/*    */   }
/*    */ 
/*    */   public String getYear()
/*    */   {
/* 32 */     return this.year;
/*    */   }
/*    */ 
/*    */   public void setYear(String year) {
/* 36 */     this.year = year;
/*    */   }
/*    */   public String getChild() {
/* 39 */     return this.child;
/*    */   }
/*    */ 
/*    */   public void setChild(String child) {
/* 43 */     this.child = child;
/*    */   }
/*    */ 
/*    */   public String getParent() {
/* 47 */     return this.parent;
/*    */   }
/*    */ 
/*    */   public void setParent(String parent) {
/* 51 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   public String getSubChild() {
/* 55 */     return this.subChild;
/*    */   }
/*    */ 
/*    */   public void setSubChild(String subChild) {
/* 59 */     this.subChild = subChild;
/*    */   }
/*    */ 
/*    */   public String getStartTime()
/*    */   {
/* 65 */     return this.startTime;
/*    */   }
/*    */ 
/*    */   public void setStartTime(String startTime)
/*    */   {
/* 70 */     this.startTime = startTime;
/*    */   }
/*    */ 
/*    */   public String getEndTime()
/*    */   {
/* 75 */     return this.endTime;
/*    */   }
/*    */ 
/*    */   public void setEndTime(String endTime)
/*    */   {
/* 80 */     this.endTime = endTime;
/*    */   }

/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.MahaDashaBean
 * JD-Core Version:    0.6.0
 */


@Override
	public String toString() {
		return "MahaDashaBean [child=" + child + ", endTime=" + endTime
				+ ", parent=" + parent + ", startTime=" + startTime
				+ ", subChild=" + subChild + ", year=" + year + "]";
	}
/*    */ }

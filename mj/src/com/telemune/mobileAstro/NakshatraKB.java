package com.telemune.mobileAstro;

/*    */ public class NakshatraKB
/*    */ {

/*    */   private String signName;
/*    */   private String nakshatra;
/*    */   private String keyword;
/*    */   private String NL;
/*    */   private String padam;
/*    */   private double startDeg;
/*    */   private double endDeg;

/*    */   public NakshatraKB()
/*    */   {
/* 25 */     this.signName = null;
/* 26 */     this.nakshatra = null;
/* 29 */     this.keyword = null;
	     this.NL=null;
/* 30 *///     this.startDeg = null;
/* 31 */  //   this.endDeg = null;
/*    */   }


/*    */   public String getSignName() {
/* 45 */     return this.signName;
/*    */   } 
/*    */   public void setSignName(String signName) {
/* 49 */     this.signName = signName;
/*    */   }

/*    */   public String getNakshatra() {
/* 53 */     return this.nakshatra;
/*    */   }
/*    */   public void setNakshatra(String nakshatra) {
/* 57 */     this.nakshatra = nakshatra;
/*    */   }

	   public String getPadam() {
/* 53 */     return this.padam;
/*    */   }
/*    */   public void setPadam(String padam) {
/* 57 */     this.padam = padam;
/*    */   }

/*    */   public String getKeyword() {
/* 77 */     return this.keyword;
/*    */   }
/*    */   public void setKeyword(String keyword) {
/* 81 */     this.keyword = keyword;
/*    */   }

	   public String getNL() {
/* 77 */     return this.NL;
/*    */   }
/*    */   public void setNL(String NL) {
/* 81 */     this.NL = NL;
/*    */   }
 
/*    */   public double getStartDeg() {
/* 85 */     return this.startDeg;
/*    */   }
/*    */   public void setStartDeg(double startDeg) {
/* 89 */     this.startDeg = startDeg;
/*    */   }
 
	   public double getEndDeg() {
/* 85 */     return this.endDeg;
/*    */   }
/*    */
/*    */   public void setEndDeg(double endDeg) {
/* 89 */     this.endDeg = endDeg;
/*    */   }


	   public String toString()
	   {
			return  "signName  : "+ this.signName+", nakshatra:  "+ this.nakshatra+", keyword:  "+this.keyword+", startDeg: "+this.startDeg +", endDeg:"+ this.endDeg;
	   }



	}

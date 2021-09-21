package com.telemune.mobileAstro;
/*    */ 
/*    */ public class HouseDetailBean extends KundliHouseBean
/*    */ {
/*    */   private String degree;
/*    */   private String signName;
/*    */   private String nakshatra;
/*    */   private String keyword;
/*    */   private String padam;
/*    */   private String RL;
/*    */   private String NL;
/*    */   private String SL;
/*    */   private String SS;
/*    */   private String NLSL;
/*    */   private String keyIndex;
public String getKeyIndex() {
	return keyIndex;
}
public void setKeyIndex(String keyIndex) {
	this.keyIndex = keyIndex;
}
/*    */   public HouseDetailBean()
/*    */   {
/* 24 */     this.degree = null;
/* 25 */     this.signName = null;
/* 26 */     this.nakshatra = null;
/* 26 */     this.keyword = null;
/* 27 */     this.padam = null;
/* 28 */     this.RL = null;
/* 29 */     this.NL = null;
/* 30 */     this.SL = null;
/* 31 */     this.SS = null;
/*    */   }
/*    */ 
/*    */   public String getDegree()
/*    */   {
/* 37 */     return this.degree;
/*    */   }
/*    */ 
/*    */   public void setDegree(String degree) {
/* 41 */     this.degree = degree;
/*    */   }
/*    */ 
/*    */   public String getSignName() {
/* 45 */     return this.signName;
/*    */   }
/*    */ 
/*    */   public void setSignName(String signName) {
/* 49 */     this.signName = signName;
/*    */   }
/*    */ 
/*    */   public String getNakshatra() {
/* 53 */     return this.nakshatra;
/*    */   }
/*    */ 
/*    */   public void setNakshatra(String nakshatra) {
/* 57 */     this.nakshatra = nakshatra;
/*    */   }
/*    */ 
	   public String getKeyword() {
/* 53 */     return this.keyword;
/*    */   }
/*    */
/*    */   public void setKeyword(String keyword) {
/* 57 */     this.keyword = keyword;
/*    */   }
		

/*    */   public String getPadam() {
/* 61 */     return this.padam;
/*    */   }
/*    */ 
/*    */   public void setPadam(String padam) {
/* 65 */     this.padam = padam;
/*    */   }
/*    */ 
/*    */   public String getRL() {
/* 69 */     return this.RL;
/*    */   }
/*    */ 
/*    */   public void setRL(String rL) {
/* 73 */     this.RL = rL;
/*    */   }
/*    */ 
/*    */   public String getNL() {
/* 77 */     return this.NL;
/*    */   }
/*    */ 
/*    */   public void setNL(String nL) {
/* 81 */     this.NL = nL;
/*    */   }
/*    */ 
/*    */   public String getSL() {
/* 85 */     return this.SL;
/*    */   }
/*    */ 
/*    */   public void setSL(String sL) {
/* 89 */     this.SL = sL;
/*    */   }
/*    */ 


		 public String getNLSL() {
/* 85 */     return this.NLSL;
/*    */   }
/*    */
/*    */   public void setNLSL(String NLsL) {
/* 89 */     this.NLSL = NLsL;
/*    */   }


/*    */   public String getSS() {
/* 93 */     return this.SS;
/*    */   }
/*    */ 
/*    */   public void setSS(String sS) {
/* 97 */     this.SS = sS;
/*    */   }
@Override
public String toString() {
	return "HouseDetailBean [degree=" + degree + ", signName=" + signName + ", nakshatra=" + nakshatra + ", keyword="
			+ keyword + ", padam=" + padam + ", RL=" + RL + ", NL=" + NL + ", SL=" + SL + ", SS=" + SS + ", NLSL="
			+ NLSL + ", keyIndex=" + keyIndex + "]";
}






/*    */ }

/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.HouseDetailBean
 * JD-Core Version:    0.6.0
i */

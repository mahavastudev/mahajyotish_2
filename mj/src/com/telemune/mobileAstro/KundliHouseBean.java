package com.telemune.mobileAstro;
/*    */ 
/*    */ public class KundliHouseBean
/*    */ {
/*    */   private String planetName;
/*    */   private String houseNumber;
/*    */   private String signNumber;
/*    */   private String transitPlanetName;
           private String transitHouseNumber;
           private String transitSignNumber;
/*    */   public KundliHouseBean()
/*    */   {
/* 18 */     this.planetName = null;
/* 19 */     this.houseNumber = null;
/* 20 */     this.signNumber = null;
             this.transitPlanetName = null;
             this.transitHouseNumber = null;
             this.transitSignNumber = null;

/*    */   }
/*    */ 
/*    */   public String getPlanetName()
/*    */   {
/* 25 */     return this.planetName;
/*    */   }
/*    */ 
/*    */   public void setPlanetName(String planetName) {
/* 29 */     this.planetName = planetName;
/*    */   }
/*    */ 
/*    */   public String getHouseNumber() {
/* 33 */     return this.houseNumber;
/*    */   }
/*    */ 
/*    */   public void setHouseNumber(String houseNumber) {
/* 37 */     this.houseNumber = houseNumber;
/*    */   }
/*    */ 
/*    */   public String getSignNumber() {
/* 41 */     return this.signNumber;
/*    */   }
/*    */ 
/*    */   public void setSignNumber(String signNumber) {
/* 45 */     this.signNumber = signNumber;
/*    */   }
           
            public String getTransitPlanetName() {
                return this.transitPlanetName;
           }
           public void setTransitPlanetName(String transitPlanetName) {
                this.transitPlanetName = transitPlanetName;
           }


           public String getTransitHouseNumber() {
                return this.transitHouseNumber;
           }
           public void setTransitHouseNumber(String transitHouseNumber) {
                this.transitHouseNumber = transitHouseNumber;
           }


           public String getTransitSignNumber() {
                return this.transitSignNumber;
           }
           public void setTransitSignNumber(String transitSignNumber) {
                this.transitSignNumber = transitSignNumber;
           }


/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.KundliHouseBean
 * JD-Core Version:    0.6.0
 */


@Override
	public String toString() {
		return "KundliHouseBean [houseNumber=" + houseNumber + ", planetName="
				+ planetName + ", signNumber=" + signNumber +", transitHouseNumber"+transitHouseNumber+",transitPlanetName"+transitPlanetName+",transitSignNumber"+transitSignNumber+ "]";
	}
/*    */ }

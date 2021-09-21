package com.telemune.mobileAstro;
/*    */ 
/*    */ public class PlanetDetailBean extends HouseDetailBean
/*    */ {
			private String planet;
			public String getPlanet() {
				return planet;
			}
			public void setPlanet(String planet) {
				this.planet = planet;
			}
/*    */   private String RC="";
/*    */ 
/*    */   public PlanetDetailBean()
/*    */   {
/* 19 */     this.RC = null;
/*    */   }
/*    */ 
/*    */   public String getRC() {
/* 23 */     return this.RC;
/*    */   }
/*    */ 
/*    */   public void setRC(String rC) {
/* 27 */     this.RC = rC;
/*    */   }
/*    */ }

/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.PlanetDetailBean
 * JD-Core Version:    0.6.0
 */

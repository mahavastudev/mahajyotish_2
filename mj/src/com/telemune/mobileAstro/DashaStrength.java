package com.telemune.mobileAstro;
/*     * 
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class DashaStrength
/*     */ {
/*     */   private String dasaPlanet;
/*     */   private String[][] StrengthChart;
/*  15 */   private Kundli cuspChart = null;
/*     */   private StringBuffer Aspect_RAHU;
/*     */   private StringBuffer Conjuction_RAHU;
/*     */   private StringBuffer SignLoard_RAHU;
/*     */   private StringBuffer RashiLoard_RAHU;
/*     */   private StringBuffer Aspect_KETU;
/*     */   private StringBuffer Conjuction_KETU;
/*     */   private StringBuffer SignLoard_KETU;
/*     */   private StringBuffer RashiLoard_KETU;
/*     */   private String rahuSource;
/*     */   private String ketuSource;
/*     */   private boolean isCurrent;
/*  31 */   Hashtable<String, KundliHouseBean> dashaCuspPlanetHashTable = new Hashtable();
/*     */ 
/*  33 */   Hashtable<String, KundliHouseBean> dashaCuspHouseHashTable = new Hashtable();
/*     */   private String dashaStrengthData;
/*     */ 
/*     */   public DashaStrength()
/*     */   {
/*  39 */     this.dasaPlanet = "Current";
/*  40 */     this.isCurrent = false;
/*  41 */     this.StrengthChart = null;
/*  42 */     this.Aspect_KETU = new StringBuffer("");
/*  43 */     this.Conjuction_KETU = new StringBuffer("");
/*  44 */     this.SignLoard_KETU = new StringBuffer("");
/*  45 */     this.RashiLoard_KETU = new StringBuffer("");
/*  46 */     this.Aspect_RAHU = new StringBuffer("");
/*  47 */     this.Conjuction_RAHU = new StringBuffer("");
/*  48 */     this.SignLoard_RAHU = new StringBuffer("");
/*  49 */     this.RashiLoard_RAHU = new StringBuffer("");
/*     */   }
/*     */ 
/*     */   public void setCurrentFlag(boolean isCurrent)
/*     */   {
/*  56 */     this.isCurrent = isCurrent;
/*     */   }
/*     */ 
/*     */   public boolean getCurrentFlag()
/*     */   {
/*  61 */     return this.isCurrent;
/*     */   }
/*     */ 
/*     */   public String getDashaStrengthData()
/*     */   {
/*  67 */     return this.dashaStrengthData;
/*     */   }
/*     */ 
/*     */   public void setDashaStrengthData(String dashaStrengthData) {
/*  71 */     this.dashaStrengthData = dashaStrengthData;
/*     */   }
/*     */ 
/*     */   public Hashtable<String, KundliHouseBean> getDashaCuspHouseHashTable() {
/*  75 */     return this.dashaCuspHouseHashTable;
/*     */   }
/*     */ 
/*     */   public void setDashaCuspHouseHashTable(Hashtable<String, KundliHouseBean> dashaCuspHouseHashTable) {
/*  79 */     this.dashaCuspHouseHashTable = dashaCuspHouseHashTable;
/*     */   }
/*     */ 
/*     */   public Hashtable<String, KundliHouseBean> getDashaCuspPlanetHashTable() {
/*  83 */     return this.dashaCuspPlanetHashTable;
/*     */   }
/*     */ 
/*     */   public void setDashaCuspPlanetHashTable(Hashtable<String, KundliHouseBean> dashaCuspPlanetHashTable) {
/*  87 */     this.dashaCuspPlanetHashTable = dashaCuspPlanetHashTable;
/*     */   }
/*     */ 
/*     */   public String getRahuSource() {
/*  91 */     return this.rahuSource;
/*     */   }
/*     */   public void setRahuSource(String rahuSource) {
/*  94 */     this.rahuSource = rahuSource;
/*     */   }
/*     */   public String getKetuSource() {
/*  97 */     return this.ketuSource;
/*     */   }
/*     */   public void setKetuSource(String ketuSource) {
/* 100 */     this.ketuSource = ketuSource;
/*     */   }
/*     */ 
/*     */   public void setStrengthChart(String[][] _stChart)
/*     */   {
/* 105 */     this.StrengthChart = _stChart;
/*     */   }
/*     */ 
/*     */   public String[][] getNatalStrengthChart()
/*     */   {
/* 110 */     return this.StrengthChart;
/*     */   }
/*     */ 
/*     */   public void setDashaPlanet(String dashaPlanet)
/*     */   {
/* 115 */     this.dasaPlanet = dashaPlanet;
/*     */   }
/*     */ 
/*     */   public String getDashaPlanet()
/*     */   {
/* 120 */     return this.dasaPlanet;
/*     */   }
/*     */ 
/*     */   public Kundli getCuspKundli()
/*     */   {
/* 126 */     return this.cuspChart;
/*     */   }
/*     */ 
/*     */   public void setCuspKundli(Kundli _cusp)
/*     */   {
/* 131 */     this.cuspChart = _cusp;
/*     */   }
/*     */ 
/*     */   public void setAspect(Planet _planet, String _aspect)
/*     */     throws astroException
/*     */   {
/* 137 */     if (_planet == Planet.Rahu)
/*     */     {
/* 139 */       this.Aspect_RAHU.append(_aspect + "__");
/*     */     }
/* 141 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 143 */       this.Aspect_KETU.append(_aspect + "__");
/*     */     }
/*     */     else
/*     */     {
/* 147 */       throw new astroException("Invalid argument <" + _planet + "> while setting aspect");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConjuction(Planet _planet, String _aspect) throws astroException {
/* 152 */     if (_planet == Planet.Rahu)
/*     */     {
/* 154 */       this.Conjuction_RAHU.append(_aspect + "__");
/*     */     }
/* 156 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 158 */       this.Conjuction_KETU.append(_aspect + "__");
/*     */     }
/*     */     else
/*     */     {
/* 162 */       throw new astroException("Invalid argument <" + _planet + "> while setting conjuction");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSignLoard(Planet _planet, String _aspect) throws astroException {
/* 166 */     if (_planet == Planet.Rahu)
/*     */     {
/* 168 */       this.SignLoard_RAHU.append(_aspect);
/*     */     }
/* 170 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 172 */       this.SignLoard_KETU.append(_aspect);
/*     */     }
/*     */     else
/*     */     {
/* 176 */       throw new astroException("Invalid argument <" + _planet + "> while setting RashiLoard");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setRashiLoard(Planet _planet, String _aspect) throws astroException {
/* 180 */     if (_planet == Planet.Rahu)
/*     */     {
/* 182 */       this.RashiLoard_RAHU.append(_aspect);
/*     */     }
/* 184 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 186 */       this.RashiLoard_KETU.append(_aspect);
/*     */     }
/*     */     else
/*     */     {
/* 190 */       throw new astroException("Invalid argument <" + _planet + "> while setting RashiLoard");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String[] getAspect(Planet _planet) {
/* 195 */     if (_planet == Planet.Rahu)
/*     */     {
/* 197 */       if (!this.Aspect_RAHU.toString().equals(""))
/*     */       {
/* 199 */         return this.Aspect_RAHU.toString().split("__");
/*     */       }
/*     */     }
/* 202 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 204 */       if (!this.Aspect_KETU.toString().equals(""))
/*     */       {
/* 206 */         return this.Aspect_KETU.toString().split("__");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 212 */       return null;
/* 213 */     }return null;
/*     */   }
/*     */ 
		public String getAspectRahu(){
			return this.Aspect_RAHU.toString();
		}

/*     */   public String[] getConjuction(Planet _planet)
/*     */   {
/* 219 */     if (_planet == Planet.Rahu)
/*     */     {
/* 221 */       if (!this.Conjuction_RAHU.toString().equals(""))
/*     */       {
/* 223 */         return this.Conjuction_RAHU.toString().split("__");
/*     */       }
/*     */     }
/* 226 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 228 */       if (!this.Conjuction_KETU.toString().equals(""))
/*     */       {
/* 230 */         return this.Conjuction_KETU.toString().split("__");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 236 */       return null;
/* 237 */     }return null;
/*     */   }
/*     */ 
/*     */   public String getSignLoard(Planet _planet)
/*     */   {
/* 243 */     if (_planet == Planet.Rahu)
/*     */     {
/* 245 */       if (!this.RashiLoard_RAHU.toString().equals(""))
/*     */       {
/* 247 */         return this.SignLoard_RAHU.toString();
/*     */       }
/*     */     }
/* 250 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 252 */       if (!this.RashiLoard_KETU.toString().equals(""))
/*     */       {
/* 254 */         return this.SignLoard_KETU.toString();
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 260 */       return null;
/* 261 */     }return null;
/*     */   }
/*     */ 
/*     */   public String getRashiLoard(Planet _planet)
/*     */   {
/* 267 */     if (_planet == Planet.Rahu)
/*     */     {
/* 269 */       if (!this.RashiLoard_RAHU.toString().equals(""))
/*     */       {
/* 271 */         return this.RashiLoard_RAHU.toString();
/*     */       }
/*     */     }
/* 274 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 276 */       if (!this.RashiLoard_KETU.toString().equals(""))
/*     */       {
/* 278 */         return this.RashiLoard_KETU.toString();
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 284 */       return null;
/* 285 */     }return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.DashaStrength
 * JD-Core Version:    0.6.0
 */

package com.telemune.mobileAstro;
/*     */ 
/*     */ import java.util.*;
/*     */ 
/*     */ public class AstroBean
/*     */ {
/*     */   private String name;
			private String fileName;
/*     */   private String DOB1;
/*     */   private String POB;
/*     */   private String TOB;
/*     */   private String DOB;
/*     */   private Kundli birthChart;
/*     */   private Kundli cuspChart;
/*     */   private String[][] natalStrengthChart;


			private String aspectRahu;
			private String conjucRahu;
			private String rashiLordRahu;
			private String signLordRahu;
			private String aspectKetu;
			private String conjucKetu;
			private String rashiLordKetu;
			private String signLordKetu;
			
			
/*     */   private Vector<DashaStrength> dashaStrength;
/*     */   private Hashtable<String, KundliHouseBean> cuspPlanetHashTable;
/*     */   private Hashtable<String, KundliHouseBean> cuspHouseHashTable;
/*     */   private Hashtable<String, KundliHouseBean> birthPlanetHashTable;
/*     */   private Hashtable<String, KundliHouseBean> birthHouseHashTable;
/*     */   private LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable;
/*     */   private LinkedHashMap<String, HouseDetailBean> houseDetailHashTable;

///////1 oct 2015/////////////
/*     */   private LinkedHashMap<String, HouseDetailBean> houseSignDetailHashTable;
/*     */   private LinkedHashMap<String, HouseDetailBean> houseStarDetailHashTable;
/*     */   private LinkedHashMap<String, HashSet<String>> houseAspectHashTable;
/*     */   private LinkedHashMap<String, HouseDetailBean> houseSubLordHashTable;
/*     */   private LinkedHashMap<String, ArrayList<HouseDetailBean>> houseOccupantHashTable;
	 private LinkedHashMap<String,  HashMap<String,HashSet<String>>> houseOccAspectHashTable;
	private LinkedHashMap<String, HashMap<String,String>> cuspHouseAspectDetails;
	private LinkedHashMap<String, HashMap<String,String>> planetHouseAspectDetails;

////////////////////////////////
/*     */   private LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable;
/*     */   private LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable;
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
            private String astroHouseTable;
            private String astroPlanetTable;
            private String kundliData;
            private String transitCountry;
            private String transitState;
            private String transitCity;
            

public Kundli getBirthChart() {
				return birthChart;
			}


			public void setBirthChart(Kundli birthChart) {
				this.birthChart = birthChart;
			}


			public Kundli getCuspChart() {
				return cuspChart;
			}


			public void setCuspChart(Kundli cuspChart) {
				this.cuspChart = cuspChart;
			}


			public StringBuffer getAspect_RAHU() {
				return Aspect_RAHU;
			}


			public void setAspect_RAHU(StringBuffer aspect_RAHU) {
				Aspect_RAHU = aspect_RAHU;
			}


			public StringBuffer getConjuction_RAHU() {
				return Conjuction_RAHU;
			}


			public void setConjuction_RAHU(StringBuffer conjuction_RAHU) {
				Conjuction_RAHU = conjuction_RAHU;
			}


			public StringBuffer getSignLoard_RAHU() {
				return SignLoard_RAHU;
			}


			public void setSignLoard_RAHU(StringBuffer signLoard_RAHU) {
				SignLoard_RAHU = signLoard_RAHU;
			}


			public StringBuffer getRashiLoard_RAHU() {
				return RashiLoard_RAHU;
			}


			public void setRashiLoard_RAHU(StringBuffer rashiLoard_RAHU) {
				RashiLoard_RAHU = rashiLoard_RAHU;
			}


			public StringBuffer getAspect_KETU() {
				return Aspect_KETU;
			}


			public void setAspect_KETU(StringBuffer aspect_KETU) {
				Aspect_KETU = aspect_KETU;
			}


			public StringBuffer getConjuction_KETU() {
				return Conjuction_KETU;
			}


			public void setConjuction_KETU(StringBuffer conjuction_KETU) {
				Conjuction_KETU = conjuction_KETU;
			}


			public StringBuffer getSignLoard_KETU() {
				return SignLoard_KETU;
			}


			public void setSignLoard_KETU(StringBuffer signLoard_KETU) {
				SignLoard_KETU = signLoard_KETU;
			}


			public StringBuffer getRashiLoard_KETU() {
				return RashiLoard_KETU;
			}


			public void setRashiLoard_KETU(StringBuffer rashiLoard_KETU) {
				RashiLoard_KETU = rashiLoard_KETU;
			}


			public String getTransitCountry() {
				return transitCountry;
			}


			public void setTransitCountry(String transitCountry) {
				this.transitCountry = transitCountry;
			}


			public String getTransitState() {
				return transitState;
			}


			public void setTransitState(String transitState) {
				this.transitState = transitState;
			}


			public String getTransitCity() {
				return transitCity;
			}


			public void setTransitCity(String transitCity) {
				this.transitCity = transitCity;
			}


			public void setDashaStrength(Vector<DashaStrength> dashaStrength) {
				this.dashaStrength = dashaStrength;
			}


/*     */   public AstroBean()
/*     */   {
/*  50 */     this.name = null;
/*  51 */     this.POB = null;
/*  52 */     this.TOB = null;
/*  53 */     this.DOB = null;
/*  54 */     this.birthChart = null;
/*  55 */     this.cuspChart = null;
/*  56 */     this.Aspect_KETU = new StringBuffer("");
/*  57 */     this.Conjuction_KETU = new StringBuffer("");
/*  58 */     this.SignLoard_KETU = new StringBuffer("");
/*  59 */     this.RashiLoard_KETU = new StringBuffer("");
/*  60 */     this.Aspect_RAHU = new StringBuffer("");
/*  61 */     this.Conjuction_RAHU = new StringBuffer("");
/*  62 */     this.SignLoard_RAHU = new StringBuffer("");
/*  63 */     this.RashiLoard_RAHU = new StringBuffer("");
/*  64 */     this.dashaStrength = new Vector();
/*  65 */     this.cuspPlanetHashTable = null;
/*  66 */     this.cuspHouseHashTable = null;
/*  67 */     this.birthPlanetHashTable = null;
/*  68 */     this.birthHouseHashTable = null;
/*  69 */     this.planetDetailHashTable = null;
/*  70 */     this.houseDetailHashTable = null;
/*  71 */     this.antardashaDetailHashTable = null;
/*  72 */     this.sookshmadashaDetailHashTable = null;
/*     */   }
/*     */ 


	    public String getKundliData()
/*     */   {
/*  93 */     return this.kundliData;
/*     */   }
/*     */
/*     */   public void setKundliData(String kundliData) {
/*  97 */     this.kundliData = kundliData;
/*     */   }





/*     */   public String getRahuSource()
/*     */   {
/*  78 */     return this.rahuSource;
/*     */   }
/*     */   public void setRahuSource(String rahuSource) {
/*  81 */     this.rahuSource = rahuSource;
/*     */   }
/*     */   public String getKetuSource() {
/*  84 */     return this.ketuSource;
/*     */   }
/*     */   public void setKetuSource(String ketuSource) {
/*  87 */     this.ketuSource = ketuSource;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  93 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  97 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getPOB() {
/* 101 */     return this.POB;
/*     */   }
/*     */ 
/*     */   public void setPOB(String pOB) {
/* 105 */     this.POB = pOB;
/*     */   }
/*     */ 
/*     */   public String getTOB() {
/* 109 */     return this.TOB;
/*     */   }
/*     */ 
/*     */   public void setTOB(String tOB) {
/* 113 */     this.TOB = tOB;
/*     */   }
/*     */   public String getDOB1() {
/* 117 */     return this.DOB1;
/*     */   }
/*     */ 
/*     */   public void setDOB1(String dOB1) {
/* 121 */     this.DOB1 = dOB1;
/*     */   }
/*     */ 
/*     */ 
/*     */   public String getDOB() {
/* 117 */     return this.DOB;
/*     */   }
/*     */ 
/*     */   public void setDOB(String dOB) {
/* 121 */     this.DOB = dOB;
/*     */   }
/*     */ 
/*     */   public void setBirthKundli(Kundli _birth) {
/* 125 */     this.birthChart = _birth;
/*     */   }
/*     */ 
/*     */   public Kundli getBirthKundli()
/*     */   {
/* 130 */     return this.birthChart;
/*     */   }
/*     */ 
/*     */   public Kundli getCuspKundli()
/*     */   {
/* 135 */     return this.cuspChart;
/*     */   }
/*     */ 
/*     */   public void setCuspKundli(Kundli _cusp)
/*     */   {
/* 140 */     this.cuspChart = _cusp;
/*     */   }
/*     */ 
/*     */   public void setNatalStrengthChart(String[][] _natalStrengthChart)
/*     */   {
/* 145 */     this.natalStrengthChart = _natalStrengthChart;
/*     */   }
/*     */ 
/*     */   public String[][] getNatalStrengthChart()
/*     */   {
/* 151 */     return this.natalStrengthChart;
/*     */   }
/*     */ 
/*     */   public void setAspect(Planet _planet, String _aspect)
/*     */     throws astroException
/*     */   {
/* 157 */     if (_planet == Planet.Rahu)
/*     */     {
/* 159 */       this.Aspect_RAHU.append(_aspect + "__");
/*     */     }
/* 161 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 163 */       this.Aspect_KETU.append(_aspect + "__");
/*     */     }
/*     */     else
/*     */     {
/* 167 */       throw new astroException("Invalid argument <" + _planet + "> while setting aspect");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConjuction(Planet _planet, String _aspect) throws astroException {
/* 172 */     if (_planet == Planet.Rahu)
/*     */     {
/* 174 */       this.Conjuction_RAHU.append(_aspect + "__");
/*     */     }
/* 176 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 178 */       this.Conjuction_KETU.append(_aspect + "__");
/*     */     }
/*     */     else
/*     */     {
/* 182 */       throw new astroException("Invalid argument <" + _planet + "> while setting conjuction");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSignLoard(Planet _planet, String _aspect) throws astroException {
/* 186 */     if (_planet == Planet.Rahu)
/*     */     {
/* 188 */       this.SignLoard_RAHU.append(_aspect);
/*     */     }
/* 190 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 192 */       this.SignLoard_KETU.append(_aspect);
/*     */     }
/*     */     else
/*     */     {
/* 196 */       throw new astroException("Invalid argument <" + _planet + "> while setting SunLoard");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setRashiLoard(Planet _planet, String _aspect) throws astroException {
/* 200 */     if (_planet == Planet.Rahu)
/*     */     {
/* 202 */       this.RashiLoard_RAHU.append(_aspect);
/*     */     }
/* 204 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 206 */       this.RashiLoard_KETU.append(_aspect);
/*     */     }
/*     */     else
/*     */     {
/* 210 */       throw new astroException("Invalid argument <" + _planet + "> while setting RashiLoard");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String[] getAspect(Planet _planet) {
/* 215 */     if (_planet == Planet.Rahu)
/*     */     {
/* 217 */       if (!this.Aspect_RAHU.toString().equals(""))
/*     */       {
/* 219 */         return this.Aspect_RAHU.toString().split("__");
/*     */       }
/*     */     }
/* 222 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 224 */       if (!this.Aspect_KETU.toString().equals(""))
/*     */       {
/* 226 */         return this.Aspect_KETU.toString().split("__");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 232 */       return null;
/* 233 */     }return null;
/*     */   }
/*     */ 
/*     */   public String[] getConjuction(Planet _planet)
/*     */   {
/* 239 */     if (_planet == Planet.Rahu)
/*     */     {
/* 241 */       if (!this.Conjuction_RAHU.toString().equals(""))
/*     */       {
/* 243 */         return this.Conjuction_RAHU.toString().split("__");
/*     */       }
/*     */     }
/* 246 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 248 */       if (!this.Conjuction_KETU.toString().equals(""))
/*     */       {
/* 250 */         return this.Conjuction_KETU.toString().split("__");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 256 */       return null;
/* 257 */     }return null;
/*     */   }
/*     */ 
/*     */   public String getSignLoard(Planet _planet)
/*     */   {
/* 263 */     if (_planet == Planet.Rahu)
/*     */     {
/* 265 */       if (!this.SignLoard_RAHU.toString().equals(""))
/*     */       {
/* 267 */         return this.SignLoard_RAHU.toString();
/*     */       }
/*     */     }
/* 270 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 272 */       if (!this.SignLoard_KETU.toString().equals(""))
/*     */       {
/* 274 */         return this.SignLoard_KETU.toString();
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 280 */       return null;
/* 281 */     }return null;
/*     */   }
/*     */ 
/*     */   public String getRashiLoard(Planet _planet)
/*     */   {
/* 287 */     if (_planet == Planet.Rahu)
/*     */     {
/* 289 */       if (!this.RashiLoard_RAHU.toString().equals(""))
/*     */       {
/* 291 */         return this.RashiLoard_RAHU.toString();
/*     */       }
/*     */     }
/* 294 */     else if (_planet == Planet.Ketu)
/*     */     {
/* 296 */       if (!this.RashiLoard_KETU.toString().equals(""))
/*     */       {
/* 298 */         return this.RashiLoard_KETU.toString();
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 304 */       return null;
/* 305 */     }return null;
/*     */   }
/*     */ 
/*     */   public Hashtable<String, KundliHouseBean> getCuspPlanetHashTable()
/*     */   {
/* 311 */     return this.cuspPlanetHashTable;
/*     */   }
/*     */ 
/*     */   public void setCuspPlanetHashTable(Hashtable<String, KundliHouseBean> cuspPlanetHashTable)
/*     */   {
/* 316 */     this.cuspPlanetHashTable = cuspPlanetHashTable;
/*     */   }
/*     */ 
/*     */   public Hashtable<String, KundliHouseBean> getCuspHouseHashTable() {
/* 320 */     return this.cuspHouseHashTable;
/*     */   }
/*     */ 
/*     */   public void setCuspHouseHashTable(Hashtable<String, KundliHouseBean> cuspHouseHashTable)
/*     */   {
/* 325 */     this.cuspHouseHashTable = cuspHouseHashTable;
/*     */   }
/*     */ 
/*     */   public Hashtable<String, KundliHouseBean> getBirthPlanetHashTable() {
/* 329 */     return this.birthPlanetHashTable;
/*     */   }
/*     */ 
/*     */   public void setBirthPlanetHashTable(Hashtable<String, KundliHouseBean> birthPlanetHashTable)
/*     */   {
/* 334 */     this.birthPlanetHashTable = birthPlanetHashTable;
/*     */   }
/*     */ 
/*     */   public Hashtable<String, KundliHouseBean> getBirthHouseHashTable() {
/* 338 */     return this.birthHouseHashTable;
/*     */   }
/*     */ 
/*     */   public void setBirthHouseHashTable(Hashtable<String, KundliHouseBean> birthHouseHashTable)
/*     */   {
/* 343 */     this.birthHouseHashTable = birthHouseHashTable;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap<String, PlanetDetailBean> getPlanetDetailHashTable() {
/* 347 */     return this.planetDetailHashTable;
/*     */   }
/*     */ 
/*     */   public void setPlanetDetailHashTable(LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable)
/*     */   {
/* 352 */     this.planetDetailHashTable = planetDetailHashTable;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap<String, HouseDetailBean> getHouseDetailHashTable() {
/* 356 */     return this.houseDetailHashTable;
/*     */   }
/*     */ 
/*     */   public void setHouseDetailHashTable(LinkedHashMap<String, HouseDetailBean> houseDetailHashTable)
/*     */   {
/* 361 */     this.houseDetailHashTable = houseDetailHashTable;
/*     */   }


 public LinkedHashMap<String, HashSet<String>> getHouseAspectHashTable() {
/* 356 */     return this.houseAspectHashTable;
/*     */   }
/*     */   
/*     */   public void setHouseAspectHashTable(LinkedHashMap<String, HashSet<String>> houseAspectHashTable)
/*     */   {
/* 361 */     this.houseAspectHashTable = houseAspectHashTable;
/*     */   }




 public LinkedHashMap<String,  HashMap<String,HashSet<String>>> getHouseOccAspectHashTable() {
/* 356 */     return this.houseOccAspectHashTable;
/*     */   }
/*     */
/*     */   public void setHouseOccAspectHashTable(LinkedHashMap<String,  HashMap<String,HashSet<String>>> houseOccAspectHashTable)
/*     */   {
/* 361 */     this.houseOccAspectHashTable = houseOccAspectHashTable;
/*     */   }




 public LinkedHashMap<String,  HashMap<String,String>> getCuspHouseAspectDetails() {
/* 356 */     return this.cuspHouseAspectDetails;
/*     */   }
/*     */
/*     */   public void setCuspHouseAspectDetails(LinkedHashMap<String,  HashMap<String,String>> cuspHouseAspectDetails)
/*     */   {
/* 361 */     this.cuspHouseAspectDetails = cuspHouseAspectDetails;
/*     */   }



 public LinkedHashMap<String,  HashMap<String,String>> getPlanetHouseAspectDetails() {
/* 356 */     return this.planetHouseAspectDetails;
/*     */   }
/*     */
/*     */   public void setPlanetHouseAspectDetails(LinkedHashMap<String,  HashMap<String,String>> planetHouseAspectDetails)
/*     */   {
/* 361 */     this.planetHouseAspectDetails = planetHouseAspectDetails;
/*     */   }








public LinkedHashMap<String, HouseDetailBean> getHouseSignDetailHashTable() {
/* 356 */     return this.houseSignDetailHashTable;
/*     */   }
/*     */
/*     */   public void setHouseSignDetailHashTable(LinkedHashMap<String, HouseDetailBean> houseSignDetailHashTable)
/*     */   {
/* 361 */     this.houseSignDetailHashTable = houseSignDetailHashTable;
/*     */   }



public LinkedHashMap<String, HouseDetailBean> getHouseStarDetailHashTable() {
/* 356 */     return this.houseStarDetailHashTable;
/*     */   }
/*     */
/*     */   public void setHouseStarDetailHashTable(LinkedHashMap<String, HouseDetailBean> houseStarDetailHashTable)
/*     */   { 
/* 361 */     this.houseStarDetailHashTable = houseStarDetailHashTable;
/*     */   }




public LinkedHashMap<String, HouseDetailBean> getHouseSubLordHashTable() {
/* 356 */     return this.houseSubLordHashTable;
/*     */   }
/*     */
/*     */   public void setHouseSubLordHashTable(LinkedHashMap<String, HouseDetailBean> houseSubLordHashTable)
/*     */   {
/* 361 */     this.houseSubLordHashTable = houseSubLordHashTable;
/*     */   }



public LinkedHashMap<String, ArrayList<HouseDetailBean>> getHouseOccupantHashTable() {
/* 356 */     return this.houseOccupantHashTable;
/*     */   }
/*     */
/*     */   public void setHouseOccupantHashTable(LinkedHashMap<String,ArrayList< HouseDetailBean>> houseOccupantHashTable)
/*     */   {
/* 361 */     this.houseOccupantHashTable = houseOccupantHashTable;
/*     */   }




/*     */ 
/*     */   public LinkedHashMap<String, Vector<MahaDashaBean>> getAntardashaDetailHashTable() {
/* 365 */     return this.antardashaDetailHashTable;
/*     */   }
/*     */ 
/*     */   public void setAntardashaDetailHashTable(LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable)
/*     */   {
/* 370 */     this.antardashaDetailHashTable = antardashaDetailHashTable;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap<String, Vector<MahaDashaBean>> getSookshmadashaDetailHashTable() {
/* 374 */     return this.sookshmadashaDetailHashTable;
/*     */   }
/*     */ 
/*     */   public void setSookshmadashaDetailHashTable(LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable)
/*     */   {
/* 379 */     this.sookshmadashaDetailHashTable = sookshmadashaDetailHashTable;
/*     */   }
/*     */ 
/*     */   public Vector<DashaStrength> getDashaStrength()
/*     */   {
/* 384 */     return this.dashaStrength;
/*     */   }
/*     */   public void setDashaStrength(DashaStrength dashaStrength) {
/* 387 */     this.dashaStrength.add(dashaStrength);
/*     */   }
public String getAspectRahu() {
	return aspectRahu;
}
public void setAspectRahu(String aspectRahu) {
	this.aspectRahu = aspectRahu;
}
public String getConjucRahu() {
	return conjucRahu;
}
public void setConjucRahu(String conjucRahu) {
	this.conjucRahu = conjucRahu;
}
public String getRashiLordRahu() {
	return rashiLordRahu;
}
public void setRashiLordRahu(String rashiLordRahu) {
	this.rashiLordRahu = rashiLordRahu;
}
public String getSignLordRahu() {
	return signLordRahu;
}
public void setSignLordRahu(String signLordRahu) {
	this.signLordRahu = signLordRahu;
}
public String getAspectKetu() {
	return aspectKetu;
}
public void setAspectKetu(String aspectKetu) {
	this.aspectKetu = aspectKetu;
}
public String getConjucKetu() {
	return conjucKetu;
}
public void setConjucKetu(String conjucKetu) {
	this.conjucKetu = conjucKetu;
}
public String getRashiLordKetu() {
	return rashiLordKetu;
}
public void setRashiLordKetu(String rashiLordKetu) {
	this.rashiLordKetu = rashiLordKetu;
}
public String getSignLordKetu() {
	return signLordKetu;
}
public void setSignLordKetu(String signLordKetu) {
	this.signLordKetu = signLordKetu;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}

public String getAstroHouseTable() {
        return astroHouseTable;
}
public void setAstroHouseTable(String astroHouseTable) {
        this.astroHouseTable = astroHouseTable;
}

public String getAstroPlanetTable() {
        return astroPlanetTable;
}
public void setAstroPlanetTable(String astroPlanetTable) {
        this.astroPlanetTable = astroPlanetTable;
}






/*     */ 
/*	public String toString() {
                return 	"houseSignDetailHashTable=" + houseSignDetailHashTable + 
			", cuspChart=" + cuspChart+ 
			", birthChart="+ birthChart+ 
			", antardashaDetailHashTable= "+antardashaDetailHashTable +
			",sookshmadashaDetailHashTable= "+sookshmadashaDetailHashTable+
			", signLordKetu= "+ signLordKetu+
			", cuspPlanetHashTable= "+ cuspPlanetHashTable+
			", cuspHouseHashTable= "+ cuspHouseHashTable+
			", birthPlanetHashTable= "+ birthPlanetHashTable+
			", planetDetailHashTable= "+ planetDetailHashTable+
			", houseDetailHashTable= "+ houseDetailHashTable+
			", houseSignDetailHashTable= "+ houseSignDetailHashTable+
			", houseStarDetailHashTable= "+ houseStarDetailHashTable+
			", houseAspectHashTable= "+ houseAspectHashTable+
			", houseSubLordHashTable= "+ houseSubLordHashTable+
			", houseOccupantHashTable= "+ houseOccupantHashTable+
			", houseOccAspectHashTable= "+ houseOccAspectHashTable+
			", cuspHouseAspectDetails= "+ cuspHouseAspectDetails+
			", planetHouseAspectDetails= "+ planetHouseAspectDetails+
			",  dashaStrength= "+dashaStrength  +"]";        

}*/
}



/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
 * Qualified Name:     com.telemune.astro.AstroBean
 * JD-Core Version:    0.6.0
 */

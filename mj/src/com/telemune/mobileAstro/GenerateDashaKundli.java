package com.telemune.mobileAstro;
/*      */ 
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ public class GenerateDashaKundli
/*      */ {
	/*   24 */   static Logger logger = Logger.getLogger(GenerateDashaKundli.class);
	/*      */ 
	/*   26 */   String[] planetName = { "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury" };
	/*   27 */   String[] rahuAndKetu = { "Rahu", "Ketu" };
	/*   28 */   String[] planetEffect = { "Jupiter", "Saturn", "Mars" };
	/*   29 */   String[] rotatePlanetName = new String[this.planetName.length];
	/*   30 */   String dashaPlanetName = "CURRENT";
	/*   31 */   AstroBean astroBean = null;
	/*      */ 
	/*   35 */   Hashtable<String, KundliHouseBean> dashaCuspPlanetHashTable = new Hashtable();
	/*   36 */   Hashtable<String, KundliHouseBean> dashaCuspHouseHashTable = new Hashtable();
	/*      */ 
	/*   40 */   Hashtable<String, KundliHouseBean> cuspPlanetHashTable = new Hashtable();
	/*   41 */   Hashtable<String, KundliHouseBean> cuspHouseHashTable = new Hashtable();
	/*      */ 
	/*   45 */   Hashtable<String, KundliHouseBean> birthPlanetHashTable = new Hashtable();
	/*   46 */   Hashtable<String, KundliHouseBean> birthHouseHashTable = new Hashtable();
	/*      */ 
	/*   51 */   LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable = new LinkedHashMap();
	/*      */ 
	/*   55 */   LinkedHashMap<String, HouseDetailBean> houseDetailHashTable = new LinkedHashMap();
	/*      */ 
	/*   59 */   LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable = new LinkedHashMap();
	/*      */ 
	/*   63 */   LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable = new LinkedHashMap();
	/*      */ 
	/*   67 */   LinkedHashMap<String, StringBuffer> vacantHouseHashTable = new LinkedHashMap();
	/*      */ 
	/*      */   public GenerateDashaKundli()
		/*      */   {
			/*      */   }
	/*      */ 
	/*      */   public GenerateDashaKundli(AstroBean astroBean, String dashaPlanetName)
		/*      */   {
					if(logger.isDebugEnabled())
			/*   75 */     logger.debug("Inside Creating Constructor of GenerateDashaKundli My Dasha Planet Name || " + dashaPlanetName);
			/*      */ 
			/*   77 */     this.dashaPlanetName = dashaPlanetName;
			/*      */ 
			/*   79 */     this.astroBean = astroBean;
			/*      */     try
				/*      */     {
					/*   87 */       this.cuspPlanetHashTable = astroBean.getCuspPlanetHashTable();
					/*   88 */       this.cuspHouseHashTable = astroBean.getCuspHouseHashTable();
					/*      */ 
					/*   92 */       this.birthPlanetHashTable = astroBean.getBirthPlanetHashTable();
					/*   93 */       this.birthHouseHashTable = astroBean.getBirthHouseHashTable();
					/*      */ 
					/*   95 */       logger.info("Successfully Load Birth Chart And Cusp chart details");
					/*      */ 
					/*   99 */       this.planetDetailHashTable = astroBean.getPlanetDetailHashTable();
					/*      */ 
					/*  103 */       this.houseDetailHashTable = astroBean.getHouseDetailHashTable();
					/*      */ 
					/*  105 */       logger.info("Successfully Load Planets and Houses details");
					/*      */ 
					/*  109 */       this.antardashaDetailHashTable = astroBean.getAntardashaDetailHashTable();
					/*      */ 
					/*  111 */       this.sookshmadashaDetailHashTable = astroBean.getSookshmadashaDetailHashTable();
					/*      */ 
					/*  113 */       logger.info("Successfully Load  antardashaDetail and sookshmadashaDetail");
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/*  119 */       ex.printStackTrace();
					/*      */     }
			/*      */   }
	/*      */ 
	/*      */   public AstroBean getDashaStrength()
		/*      */   {
		                   if(logger.isDebugEnabled()){
			/*  127 */     logger.debug("*******************************************Dasha Bean Object creation********************************************");
			/*  128 */     logger.debug("Inside Function  getDashaStrength() for getting dasha Chart  Planet name  [ " + this.dashaPlanetName + " ]");
			/*      */ }
			/*  130 */     DashaStrength dashaBean = new DashaStrength();
			/*      */ 
			/*  132 */     if (this.dashaPlanetName.equalsIgnoreCase("CURRENT"))
				/*      */     {
					/*  134 */       dashaBean.setCurrentFlag(true);
					/*      */     }
			/*      */ 
			/*  138 */     Kundli dashaKundli = new Kundli("CUSP");
			/*      */     try
				/*      */     {
								if(logger.isDebugEnabled())
					/*  143 */       logger.debug("Before Call  rotateCuspChartWithDashaPlanet function "+this.dashaPlanetName);
					/*  144 */       rotateCuspChartWithDashaPlanet(this.dashaPlanetName, this.cuspHouseHashTable, this.cuspPlanetHashTable, this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable, dashaKundli);
					/*      */ if(logger.isDebugEnabled())
					/*  146 */       logger.debug("Dasha Chart Values || " + dashaKundli.toString());
					/*      */ 
					/*  148 */       dashaBean.setCuspKundli(dashaKundli);
					/*      */ 
					/*  151 */       getVacantHouseList(dashaKundli, this.vacantHouseHashTable, this.planetDetailHashTable);
					/*      */ 
					/*  159 */       String[][] tabularData = new String[9][7];
					/*      */ 
					/*  165 */       String rahuRashiLord = getNLAndSL("RL", "Rahu", this.planetDetailHashTable);
					/*  166 */       String ketuRashiLord = getNLAndSL("RL", "Ketu", this.planetDetailHashTable);
					/*  167 */       if (!StringUtils.isBlank(rahuRashiLord))
						/*      */       {
							/*  169 */         rahuRashiLord = searchPlanetLocation(rahuRashiLord, this.cuspPlanetHashTable, this.cuspHouseHashTable);
							/*      */       }
					/*      */ 
					/*  172 */       if (!StringUtils.isBlank(ketuRashiLord))
						/*      */       {
							/*  174 */         ketuRashiLord = searchPlanetLocation(ketuRashiLord, this.cuspPlanetHashTable, this.cuspHouseHashTable);
							/*      */       }
					/*      */ 
									if(logger.isDebugEnabled())
					/*  177 */       logger.debug(" My Rahu ketu sign houses number rahuRashiLord [ " + rahuRashiLord + " ] ketuRashiLord [ " + ketuRashiLord + " ]");
					/*      */ 
					/*  179 */       rahuRashiLord = rahuRashiLord.substring(rahuRashiLord.indexOf("/") + 1, rahuRashiLord.length());
					/*  180 */       ketuRashiLord = ketuRashiLord.substring(ketuRashiLord.indexOf("/") + 1, ketuRashiLord.length());
					/*      */ 
					/*  183 */       for (int loop = 0; loop < this.planetName.length; loop++)
						/*      */       {
							/*  187 */         String sourceValue = searchPlanetLocation(this.planetName[loop], this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
							/*      */ 
							/*  191 */         String resultNL = getNLAndSL("NL", this.planetName[loop], this.planetDetailHashTable);
							/*  192 */         if (!StringUtils.isBlank(resultNL)) {
								/*  193 */           resultNL = resultNL + searchPlanetLocation(resultNL.trim(), this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
								/*      */         }
							/*      */ 
							/*  197 */         String resultSL = getNLAndSL("SL", this.planetName[loop], this.planetDetailHashTable);
							/*  198 */         if (!StringUtils.isBlank(resultSL)) {
								/*  199 */           resultSL = resultSL + searchPlanetLocation(resultSL.trim(), this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
								/*      */         }
							/*      */ 
							/*  203 */         String resultNLOfSL = getNLAndSL("SL", this.planetName[loop], this.planetDetailHashTable);
							/*  204 */         if (!StringUtils.isBlank(resultNLOfSL)) {
								/*  205 */           resultNLOfSL = getNLAndSL("NL", resultNLOfSL.trim(), this.planetDetailHashTable);
								/*      */         }
							/*  207 */         if (!StringUtils.isBlank(resultNLOfSL)) {
								/*  208 */           resultNLOfSL = resultNLOfSL + searchPlanetLocation(resultNLOfSL.trim(), this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
								/*      */         }
							/*  210 */         String MDLValue = getMDLValue(this.planetName[loop], this.antardashaDetailHashTable);
							/*      */ 
							/*  212 */         String vacantHouse = getVacantHouse(this.planetName[loop]);
							/*      */ 
							/*  214 */         tabularData[loop][0] = this.planetName[loop];
							/*  215 */         tabularData[loop][1] = sourceValue;
							/*  216 */         tabularData[loop][2] = resultNL;
							/*  217 */         tabularData[loop][3] = vacantHouse;
							/*  218 */         tabularData[loop][4] = resultSL;
							/*  219 */         tabularData[loop][5] = resultNLOfSL;
							/*  220 */         tabularData[loop][6] = MDLValue;
							/*      */ 
							/*  225 */         if (resultNL.contains("Rahu"))
								/*  226 */           tabularData[loop][2] = (resultNL + rahuRashiLord);
							/*  227 */         if (resultSL.contains("Rahu"))
								/*  228 */           tabularData[loop][4] = (resultSL + rahuRashiLord);
							/*  229 */         if (resultNLOfSL.contains("Rahu"))
								/*  230 */           tabularData[loop][5] = (resultNLOfSL + rahuRashiLord);
							/*  231 */         if (this.planetName[loop].equalsIgnoreCase("Rahu")) {
								/*  232 */           tabularData[loop][1] = (sourceValue + rahuRashiLord);
								/*      */         }
							/*      */ 
							/*  236 */         if (resultNL.contains("Ketu"))
								/*  237 */           tabularData[loop][2] = (resultNL + ketuRashiLord);
							/*  238 */         if (resultSL.contains("Ketu"))
								/*  239 */           tabularData[loop][4] = (resultSL + ketuRashiLord);
							/*  240 */         if (resultNLOfSL.contains("Ketu"))
								/*  241 */           tabularData[loop][5] = (resultNLOfSL + ketuRashiLord);
							/*  242 */         if (this.planetName[loop].equalsIgnoreCase("Ketu")) {
								/*  243 */           tabularData[loop][1] = (sourceValue + ketuRashiLord);
								/*      */         }
							/*      */ 
							/*      */       }
					/*      */ 
					/*  252 */       dashaBean.setStrengthChart(tabularData);
					/*      */ 
					/*  255 */       dashaBean.setDashaPlanet(this.dashaPlanetName);
									if(logger.isDebugEnabled())
					/*  256 */       logger.debug(" Tabular Format Data inserted in 2D array ");
					/*  257 */       for (int outer = 0; outer < tabularData.length; outer++)
						/*      */       {
							/*  259 */         for (int inner = 0; inner < tabularData[outer].length; inner++)
								/*      */         {
														if(logger.isDebugEnabled())
									/*  262 */           logger.debug("     " + tabularData[outer][inner]);
									/*      */         }
							/*      */		if(logger.isDebugEnabled()) 
							/*  266 */         logger.debug("************************");
							/*      */       }
					/*      */ 			if(logger.isDebugEnabled())
					/*  269 */       logger.debug("Generate  Rahu/Ketu planets effect ");
					/*      */ 
					/*  273 */       getRahuKetuShadowEffect(dashaBean, this.birthPlanetHashTable, this.birthHouseHashTable);
					/*      */ 	if(logger.isDebugEnabled())
					/*  275 */       logger.debug("Load  Cache of DashaCuspPlanetDetail/DashaCuspHouseDetail into DashaBean Object ");
					/*      */ 
					/*  277 */       dashaBean.setDashaCuspHouseHashTable(this.dashaCuspHouseHashTable);
					/*  278 */       dashaBean.setDashaCuspPlanetHashTable(this.dashaCuspPlanetHashTable);
					/*      */ 
					/*  283 */       dashaBean.setDashaStrengthData(getDashaTabledata(this.antardashaDetailHashTable));
					/*      */ 		if(logger.isDebugEnabled())
					/*  287 */       logger.debug("Store DashaBean Object into Astro Bean Object");
					/*      */ 
					/*  289 */       this.astroBean.setDashaStrength(dashaBean);
					/*      */       if(logger.isDebugEnabled()){
					/*  291 */       logger.debug("Successfully Store Dashabean Object into AstroBean Object");
					}
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/*  296 */       ex.printStackTrace();
					/*      */     }
			/*      */ 
			/*  299 */     return this.astroBean;
			/*      */   }
	/*      */ 
	/*      */   public void rotateCuspChartWithDashaPlanet(String dashsPlanetName, Hashtable houseHashTable, Hashtable planetHashTable, Hashtable dashaPlanetHashTable, Hashtable dashaSignHashTable, Kundli dashaChartDetail)
		/*      */   {
			/*  307 */     logger.info("Inside Function rotateCuspChartWithDashaPlanet  rotate Cusp chart according to the planets either Current/Any Planet || " + dashsPlanetName);
			/*      */ 
			/*  309 */     String dashaPlanet = dashsPlanetName;
			/*  310 */     if (dashaPlanet.equalsIgnoreCase("CURRENT"))
				/*      */     {	if(logger.isDebugEnabled())
					/*  313 */       logger.debug("My Dasha Planet Name is Not Define So Call getDashaPlanetName Function For Getting Dasha Planete Name ");
					/*  314 */       dashaPlanet = getDashaPlanetName();
					/*  315 */       this.dashaPlanetName = dashaPlanet;
									if(logger.isDebugEnabled())
					/*  316 */       logger.debug("IF :: Inside Call function for search planet name ||  " + dashaPlanet);
					/*      */     }
			/*      */ 
			/*  319 */     if (!StringUtils.isBlank(dashaPlanet))
				/*      */     {
					/*  323 */       if (planetHashTable.containsKey(dashaPlanet))
						/*      */       {
							/*  325 */         KundliHouseBean obj = (KundliHouseBean)planetHashTable.get(dashaPlanet);
							/*      */ 			if(logger.isDebugEnabled())
							/*  327 */         logger.debug("Get Current House Number for rotation   Dasha Planet Name [ " + dashaPlanet + " ] House Number [ " + obj.getHouseNumber() + " ]");
							/*      */ 
							/*  329 */         int rotateHouseNumber = Integer.parseInt(obj.getHouseNumber());
							/*  330 */         int counter = 1;
							/*  331 */         int newID = 0;
							/*  332 */         for (int loop = rotateHouseNumber; loop <= rotateHouseNumber + 11; loop++)
								/*      */         {
									/*  334 */           StringBuffer dashaChartData = new StringBuffer();
									/*  335 */           if (loop % 12 != 0)
										/*  336 */             newID = loop % 12;
									/*      */           else {
										/*  338 */             newID = loop;
										/*      */           }
													if(logger.isDebugEnabled())
									/*  340 */           logger.debug("My HashTable  Iteration  loop value  [ " + loop + " ]  New ID value  [ " + newID + " ]");
									/*      */ 
									/*  344 */           KundliHouseBean beanObject = (KundliHouseBean)houseHashTable.get(Integer.toString(newID));
									/*      */ 
									/*  348 */           dashaChartData.append(beanObject.getSignNumber());
									/*      */ 
									/*  350 */           if (!StringUtils.isBlank(beanObject.getPlanetName()))
										/*      */           {
											/*  352 */             dashaChartData.append("_");
											/*  353 */             dashaChartData.append(beanObject.getPlanetName());
											/*      */           }
									/*      */ 
									/*  356 */           dashaChartDetail.setHouseData(counter, dashaChartData.toString());
									/*      */ 			if(logger.isDebugEnabled())
									/*  358 */          { logger.debug("Dasha Kundli House Number [ " + counter + " ] [  Houses Values " + dashaChartData.toString() + " ]");
									/*      */ 			
									/*  363 */           logger.debug("OLD ::house number [ " + loop + " ] Sign Number  [ " + beanObject.getSignNumber() + "] House Number [ " + beanObject.getHouseNumber() + " ]");
									}
									/*  364 */           beanObject.setHouseNumber(Integer.toString(counter));
													if(logger.isDebugEnabled())
									/*  365 */           logger.debug("NEW :: insert value in  dashaSignHashTable  Bean Object value at house number [ " + counter + " ] Sign Number  [ " + beanObject.getSignNumber() + " House Number " + beanObject.getHouseNumber() + " ]");
									/*  366 */           dashaSignHashTable.put(Integer.toString(counter), beanObject);
									/*      */ 
									/*  371 */           if (!StringUtils.isBlank(beanObject.getPlanetName()))
										/*      */           {
											/*  373 */             logger.info(" Planet Value is not null for house number " + loop + "Planet Names " + beanObject.getPlanetName());
											/*      */ 
											/*  375 */             String[] planetName = beanObject.getPlanetName().split(" ");
											/*  376 */             for (String planet : planetName)
												/*      */             {
													/*  378 */               KundliHouseBean planetObj = new KundliHouseBean();
													/*  379 */               planetObj.setPlanetName(planet.trim());
													/*  380 */               planetObj.setHouseNumber(Integer.toString(counter));
													/*  381 */               planetObj.setSignNumber(beanObject.getSignNumber().trim());
													/*  382 */               dashaPlanetHashTable.put(planet.trim(), planetObj);
													/*      */ 
																	if(logger.isDebugEnabled())
													/*  384 */               logger.debug(" Insert value in :: dashaPlanetHashTable:: table   Key " + planet + " House Value " + planetObj.getHouseNumber() + " sign Value " + planetObj.getSignNumber());
													/*      */             }
											/*      */ 
											/*      */           }
									/*      */ 
									/*  391 */           counter++;
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */       else
						/*      */       {
											   if(logger.isDebugEnabled()){
							/*  398 */         logger.debug("There is no any key for " + dashaPlanet + "Inside planetHashTable ");
											   }
							/*      */       }
					/*      */     }
			/*      */     else
				/*      */     {
									 if(logger.isDebugEnabled()){
					/*  403 */       logger.debug("There is no any dasha Planet founds from function getDashaPlanetName() Dasha Planet Values || " + dashaPlanet);
									 }
					/*      */     }
			/*      */   }
	/*      */ 
	/*      */   public String searchPlanetLocation(String planetName, Hashtable cuspPlanetHashTable, Hashtable cuspHouseHashTable)
		/*      */   {
						  if(logger.isDebugEnabled()){
			/*  410 */     logger.debug("Inside Function  searchPlanetLocation for planet name || " + planetName);
			/*  411 */     }
			             StringBuffer retObj = new StringBuffer();
			/*      */ 
			/*  413 */     retObj.append(" ");
			/*      */     try
				/*      */     {
					/*  417 */       if (cuspPlanetHashTable.containsKey(planetName))
						/*      */       {
							/*  420 */         KundliHouseBean obj = (KundliHouseBean)cuspPlanetHashTable.get(planetName);
							/*  421 */         retObj.append(obj.getHouseNumber());
							/*      */ 			if(logger.isDebugEnabled())
							/*  423 */         logger.debug("cuspPlanetHashTable  Contains  [" + planetName + " ] House Number  [" + obj.getHouseNumber() + " ]");
							/*      */ 
							/*  426 */         Planet getPlanetSignNumber = Planet.toPlanets(planetName);
							/*      */         String[] signStr;
							/*  430 */         if (getPlanetSignNumber.getSecondRashi() != 0)
								/*      */         {
									/*  432 */         signStr = new String[2];  //String[] removed by prianka
									/*  433 */           signStr[0] = Integer.toString(getPlanetSignNumber.getFirstRashi());
									/*  434 */           signStr[1] = Integer.toString(getPlanetSignNumber.getSecondRashi());
									/*      */         }
							/*      */         else
								/*      */         {
									/*  438 */           signStr = new String[2];
									/*  439 */           signStr[0] = Integer.toString(getPlanetSignNumber.getFirstRashi());
									/*      */         }
							/*      */ 
											if(logger.isDebugEnabled())
							/*  443 */         logger.debug("Getting Rashi numbers  of Planet [ " + planetName + " ] Array Length [ " + signStr.length + " ] FirstRashi No. [ " + getPlanetSignNumber.getFirstRashi() + " ] Second Rashi No.[ " + getPlanetSignNumber.getSecondRashi() + " ]");
							/*      */ 
							/*  445 */         retObj.append("/ ");
							/*      */ 
							/*  448 */         for (String st : signStr)
								/*      */         {
									/*  453 */           Iterator itr = cuspHouseHashTable.values().iterator();
									/*      */ 
									/*  457 */           while (itr.hasNext())
										/*      */           {
											/*  460 */             KundliHouseBean ob = (KundliHouseBean)itr.next();
											/*  461 */             if (!ob.getSignNumber().equals(st))
												/*      */               continue;
											/*  463 */             retObj.append(ob.getHouseNumber());
											/*  464 */             retObj.append(",");
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */ 
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/*  477 */       ex.printStackTrace();
					/*      */     }
			/*      */ 	if(logger.isDebugEnabled())
			/*  480 */     logger.debug("My Planet Value is return  || " + retObj.toString());
			/*      */ 
			/*  484 */     if (retObj.toString().endsWith(",")) {
				/*  485 */       return retObj.toString().substring(0, retObj.toString().length() - 1);
				/*      */     }
			/*  487 */     return retObj.toString();
			/*      */   }
	/*      */ 
	/*      */   public String getNLAndSL(String searchAttribute, String planetName, LinkedHashMap planetDetailHashTable)
		/*      */   {
		                   if(logger.isDebugEnabled()){
			/*  495 */     logger.debug("Inside function  getNLAndSL for Search Attribute [ " + searchAttribute + " ] of planet [ " + planetName + " ]");
			/*      */     }
							try
				/*      */     {
					/*  499 */       if (planetDetailHashTable.containsKey(planetName))
						/*      */       {
							/*  501 */         PlanetDetailBean planetBean = (PlanetDetailBean)planetDetailHashTable.get(planetName);
							/*      */ 
							/*  503 */         if (searchAttribute.equalsIgnoreCase("NL"))
								/*      */         {
									/*  505 */           return planetBean.getNL();
									/*      */         }
							/*      */ 
							/*  508 */         if (searchAttribute.equalsIgnoreCase("SL"))
								/*      */         {
									/*  510 */           return planetBean.getSL();
									/*      */         }
							/*  512 */         if (searchAttribute.equalsIgnoreCase("RL"))
								/*      */         {
									/*  514 */           return planetBean.getRL();
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */       else
						/*      */       {		
										if(logger.isDebugEnabled())
							/*  520 */         logger.debug("Inside function  getNLAndSL no any key [ " + planetName + " ] found inside planetDetailHashTable ");
							/*  521 */         return null;
							/*      */       }
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/*  526 */       ex.printStackTrace();
					/*  527 */       return null;
					/*      */     }
			/*      */ 
			/*  530 */     return null;
			/*      */   }
	/*      */ 
	/*      */   public void getRahuKetuShadowEffect(DashaStrength natalStrength, Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable)
		/*      */   {
						   if(logger.isDebugEnabled()){
			/*  537 */     logger.debug("Inside Function  getRahuKetuShadowEffect for generate rahu/ketu effect ");
						   }
			/*      */     try
				/*      */     {
					/*  543 */       for (String str : this.rahuAndKetu)
						/*      */       {
							/*  549 */         String rahuKetuSource = str;
							/*      */ 
							/*  564 */         String rahuAndKetuData = getRahuKetuEffectPlanet(str, birthPlanetHashTable, birthHouseHashTable);
												if(logger.isDebugEnabled())
							/*  565 */         logger.debug("Rahu/Ketu Effected Planets lists [ " + rahuAndKetuData + " ] for planet [ " + str + " ]");
							/*  566 */         String[] atr = rahuAndKetuData.split(" ");
							/*  567 */         for (String planetEffec : atr)
								/*      */         {
									/*  569 */           if ((planetEffec.equalsIgnoreCase("unsuccess")) || (planetEffec.equalsIgnoreCase("Rahu")) || (planetEffec.equalsIgnoreCase("Ketu")))
										/*      */           {	
																	if(logger.isDebugEnabled())
											/*  571 */             logger.debug(" Inside unsuccess and Rahu ketu Find Data");
											/*      */           }
									/*      */           else
										/*      */           {
																if(logger.isDebugEnabled())
											/*  577 */             logger.debug("Obtain Effect Of this planet " + planetEffec);
											/*      */ 
											/*  579 */             if (StringUtils.isBlank(planetEffec))
												/*      */               continue;
											/*  581 */             planetEffec = planetEffec + searchPlanetLocation(planetEffec.trim(), this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
											/*  582 */             if (str.equalsIgnoreCase("Rahu"))
												/*  583 */               natalStrength.setAspect(Planet.Rahu, planetEffec);
											/*  584 */             else if (str.equalsIgnoreCase("Ketu"))
												/*  585 */               natalStrength.setAspect(Planet.Ketu, planetEffec);
											/*      */             else {
																			if(logger.isDebugEnabled())
												/*  587 */               logger.debug("No any planet define plante name || " + str);
												/*      */             }
											/*      */ 
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */ 
							/*  595 */         for (int loop = 0; loop < this.planetEffect.length; loop++)
								/*      */         {
									/*  597 */           boolean isEffected = false;
									/*  598 */           Planet p = Planet.toPlanets(this.planetEffect[loop]);
									/*      */ 
									/*  600 */           String ptr = this.planetEffect[loop];
									/*      */ 
									/*  602 */           switch (loop)
										/*      */           {
											/*      */           case 0:
												/*  607 */             isEffected = getJupiterSaturnMarsEffect(this.planetEffect[loop], str, p.getFirstAspect(), p.getSecondAspect(), birthPlanetHashTable, birthHouseHashTable);
												/*  608 */             break;
											/*      */           case 1:
												/*  611 */             isEffected = getJupiterSaturnMarsEffect(this.planetEffect[loop], str, p.getFirstAspect(), p.getSecondAspect(), birthPlanetHashTable, birthHouseHashTable);
												/*  612 */             break;
											/*      */           case 2:
												/*  615 */             isEffected = getJupiterSaturnMarsEffect(this.planetEffect[loop], str, p.getFirstAspect(), p.getSecondAspect(), birthPlanetHashTable, birthHouseHashTable);
												/*      */           }
									/*      */ 
									/*  619 */           if (isEffected)
										/*      */           {
																						if(logger.isDebugEnabled())
											/*  621 */             logger.debug("There is aspect of [ " + str + " ] into [ " + ptr + " ]");
											/*  622 */             ptr = ptr + searchPlanetLocation(ptr, this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
											/*  623 */             if (str.equalsIgnoreCase("Rahu"))
												/*  624 */               natalStrength.setAspect(Planet.Rahu, ptr);
											/*  625 */             else if (str.equalsIgnoreCase("Ketu"))
												/*  626 */               natalStrength.setAspect(Planet.Ketu, ptr);
											/*      */             else {
																		if(logger.isDebugEnabled())
												/*  628 */               logger.debug("No any planet define plante name || " + str);
												/*      */             }
											/*      */ 
											/*      */           }
									/*      */           else
										/*      */           {
																	if(logger.isDebugEnabled())
											/*  634 */             logger.debug("There is no any aspect of [ " + str + " ] into " + this.planetEffect[loop]);
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */ 
							/*  644 */         String conjPlanetLists = getConjPlanetList(str, this.planetDetailHashTable);
																if(logger.isDebugEnabled())
							/*  645 */         logger.debug("Conjuction  Planet Lists  || " + conjPlanetLists);
							/*      */ 
							/*  647 */         if (!StringUtils.isBlank(conjPlanetLists))
								/*      */         {
									/*  649 */           String[] conj = conjPlanetLists.split("_");
									/*  650 */           String rahuKetuConj = "";
									/*  651 */           for (String conjPlanet : conj)
										/*      */           {
											/*  653 */             rahuKetuConj = conjPlanet;
											/*      */ 
											/*  655 */             if (conjPlanet.equalsIgnoreCase(str))
												/*      */               continue;
											/*  657 */             rahuKetuConj = rahuKetuConj + searchPlanetLocation(conjPlanet, this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
											/*      */ 
											/*  659 */             if (str.equalsIgnoreCase("Rahu"))
												/*  660 */               natalStrength.setConjuction(Planet.Rahu, rahuKetuConj);
											/*  661 */             else if (str.equalsIgnoreCase("Ketu"))
												/*  662 */               natalStrength.setConjuction(Planet.Ketu, rahuKetuConj);
											/*      */             else {
																		if(logger.isDebugEnabled())
												/*  664 */               logger.debug("No any planet define plante name || " + str);
												/*      */             }
											/*      */ 
																	if(logger.isDebugEnabled())
											/*  667 */             logger.debug(" Conjuction  of this planets   [ " + rahuKetuConj + " ] for [ " + str + " ]");
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */ 
												if(logger.isDebugEnabled())
							/*  677 */         logger.debug("Getting  Sign Loard and Rashi loard  ");
							/*      */ 
							/*  680 */         String rahuKetuSignLord = ""; String rahuKetuRashiLord = "";
							/*  681 */         rahuKetuSignLord = getNLAndSL("NL", str, this.planetDetailHashTable);
							/*  682 */         if (!StringUtils.isBlank(rahuKetuSignLord))
								/*      */         {
									/*  684 */           rahuKetuSignLord = rahuKetuSignLord + searchPlanetLocation(rahuKetuSignLord, this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
									/*      */ 
									/*  686 */           if (str.equalsIgnoreCase("Rahu"))
										/*  687 */             natalStrength.setSignLoard(Planet.Rahu, rahuKetuSignLord);
									/*  688 */           else if (str.equalsIgnoreCase("Ketu")) {
										/*  689 */             natalStrength.setSignLoard(Planet.Ketu, rahuKetuSignLord);
										/*      */           }
									/*      */         }
							/*  692 */         rahuKetuRashiLord = getNLAndSL("RL", str, this.planetDetailHashTable);
							/*  693 */         if (!StringUtils.isBlank(rahuKetuRashiLord))
								/*      */         {
									/*  695 */           rahuKetuRashiLord = rahuKetuRashiLord + searchPlanetLocation(rahuKetuRashiLord, this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
									/*  696 */           if (str.equalsIgnoreCase("Rahu"))
										/*  697 */             natalStrength.setRashiLoard(Planet.Rahu, rahuKetuRashiLord);
									/*  698 */           else if (str.equalsIgnoreCase("Ketu")) {
										/*  699 */             natalStrength.setRashiLoard(Planet.Ketu, rahuKetuRashiLord);
										/*      */           }
									/*      */ 
									/*      */         }
							/*      */ if(logger.isDebugEnabled())
							/*  704 */         logger.debug(" SignLord and RashiLord for planet name [ " + str + "] [S] " + rahuKetuSignLord + " [R] " + rahuKetuRashiLord);
							/*      */ 
							/*  708 */         rahuKetuSource = searchPlanetLocation(rahuKetuSource, this.cuspPlanetHashTable, this.cuspHouseHashTable);
							/*      */ 
							/*  714 */         rahuKetuSource = rahuKetuSource + rahuKetuRashiLord.substring(rahuKetuRashiLord.indexOf("/") + 1, rahuKetuRashiLord.length());
							/*  715 */         if (str.equalsIgnoreCase("Rahu"))
								/*  716 */           natalStrength.setRahuSource(rahuKetuSource);
							/*  717 */         else if (str.equalsIgnoreCase("Ketu"))
								/*  718 */           natalStrength.setKetuSource(rahuKetuSource);
							/*      */         else {
												if(logger.isDebugEnabled())
								/*  720 */           logger.debug("No any planet define plante name || " + str);
								/*      */         }
							/*      */ 
							/*      */       }
					/*      */ 
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/*  733 */       ex.printStackTrace();
					/*      */     }
			/*      */   }
	/*      */ 
	/*      */   public String getRahuKetuEffectPlanet(String rahuAndKetu, Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable)
		/*      */   {
						   if(logger.isDebugEnabled()){
			/*  743 */     logger.debug("Inside Function function getRahuKetuEffectPlanet for getting rahu/ketu effeted planets  || " + rahuAndKetu);
						   }
			/*      */     try
				/*      */     {
					/*  748 */       if (birthPlanetHashTable.containsKey(rahuAndKetu))
						/*      */       {
							/*  751 */         KundliHouseBean obj = (KundliHouseBean)birthPlanetHashTable.get(rahuAndKetu);
							/*      */ 
							/*  757 */         int houseNumber = Integer.parseInt(obj.getHouseNumber());
							/*      */ 			if(logger.isDebugEnabled())
							/*  759 */         logger.debug("My Original HouseNumber for planet " + rahuAndKetu + " house number" + houseNumber);
							/*      */ 
							/*  761 */         houseNumber -= 6;
							/*  762 */         if (houseNumber <= 0) {
								/*  763 */           houseNumber += 12;
								/*      */         }
											if(logger.isDebugEnabled())
							/*  765 */         logger.debug("After Move 6th position HouseNumber for planet " + rahuAndKetu + " house number" + houseNumber);
							/*      */ 
							/*  767 */         String houseNo = Integer.toString(houseNumber);
							/*      */ 
							/*  769 */         if (birthHouseHashTable.containsKey(houseNo))
								/*      */         {
									/*  773 */           KundliHouseBean objBean = (KundliHouseBean)birthHouseHashTable.get(houseNo);
															if(logger.isDebugEnabled())
										/*  774 */           logger.debug("Planets name return from  getRahuKetuEffectPlanet function which are effected  || " + objBean.getPlanetName());
									/*  775 */           return objBean.getPlanetName();
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */       else
						/*      */       {
												if(logger.isDebugEnabled())
							/*  783 */         logger.debug("Hash Table does not contain this planet " + rahuAndKetu);
							/*      */       }
					/*      */ 
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/*  789 */       ex.printStackTrace();
					/*      */     }
			/*  791 */     return "unsuccess";
			/*      */   }
	/*      */ 
	/*      */   public boolean getJupiterSaturnMarsEffect(String planetEffect, String planetName, int first, int second, Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable)
		/*      */   {
			/*  797 */     boolean retVal = false;
			/*      */ 
						   if(logger.isDebugEnabled()){
			/*  799 */     logger.debug("Inside function getJupiterSaturnMarsEffect for getting rahu/ketu planet effect on  Ju/Sa/Ma || " + planetEffect);
			/*      */     }
							try
				/*      */     {
					/*  804 */       if (birthPlanetHashTable.containsKey(planetEffect))
						/*      */       {
							/*  807 */         KundliHouseBean obj = (KundliHouseBean)birthPlanetHashTable.get(planetEffect);
							/*      */ 
												if(logger.isDebugEnabled())
							/*  809 */         logger.debug("Planet Name Contains " + planetEffect + "House Number " + obj.getHouseNumber());
							/*      */ 
							/*  812 */         int houseNumber = Integer.parseInt(obj.getHouseNumber());
							/*  813 */         int firstHouse = houseNumber + first;
							/*  814 */         int secondHouse = houseNumber + second;
							/*      */ 
							/*  816 */         if (firstHouse > 12)
								/*  817 */           firstHouse -= 12;
							/*  818 */         if (secondHouse > 12) {
								/*  819 */           secondHouse -= 12;
								/*      */         }
												if(logger.isDebugEnabled())
							/*  821 */         logger.debug("My New House Numbers FirstHouse " + firstHouse + " SecondHouse " + secondHouse);
							/*  822 */         String[] houseArray = { Integer.toString(firstHouse), Integer.toString(secondHouse) };
							/*  823 */         for (int loop = 0; loop < houseArray.length; loop++)
								/*      */         {
									/*  825 */           if (!birthHouseHashTable.containsKey(houseArray[loop]))
										/*      */             continue;
														if(logger.isDebugEnabled())
									/*  827 */           logger.debug("My hash Table contain house Number for " + houseArray[loop]);
									/*  828 */           KundliHouseBean beanObj = (KundliHouseBean)birthHouseHashTable.get(houseArray[loop]);
														if(logger.isDebugEnabled())
									/*  829 */           logger.debug("My planets name ||||||||||||||||||||||||" + beanObj.getPlanetName());
									/*      */ 
									/*  831 */           if (StringUtils.isBlank(beanObj.getPlanetName())) {
										/*      */             continue;
										/*      */           }
									/*  834 */           String[] str = beanObj.getPlanetName().split(" ");
														if(logger.isDebugEnabled())
									/*  835 */           logger.debug("Planet Name Contains house number " + houseArray[loop] + "Planet Name" + beanObj.getPlanetName() + " Planet Array " + Arrays.toString(str));
									/*  836 */           for (String planetNm : str)
										/*      */           {
											/*  838 */             if (!planetName.equalsIgnoreCase(planetNm.trim()))
												/*      */               continue;
																	if(logger.isDebugEnabled())
											/*  840 */             logger.debug("Effect of " + planetName + " for planet " + planetEffect);
											/*  841 */             return true;
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */       else
						/*      */       {
							/*  853 */         retVal = false;
							/*      */       }
					/*      */ 
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/*  860 */       ex.printStackTrace();
					/*  861 */       return false;
					/*      */     }
			/*      */ 
			/*  864 */     return retVal;
			/*      */   }
	/*      */ 
	/*      */   public String getConjPlanetList(String rahuAndKetu, LinkedHashMap planetDetailHashTable)
		/*      */   {
						   if(logger.isDebugEnabled()){
			/*  870 */     logger.debug("Inside Function getConjPlanetList for getting conjection planet list of || " + rahuAndKetu);
			/*      */ }
			/*  872 */     StringBuffer planetList = new StringBuffer();
			/*      */     try
				/*      */     {
					/*  877 */       int plusDegree = 0; int minusDegree = 0;
					/*  878 */       boolean isForword = false; boolean isBackword = false; boolean insertStatus = false;
					/*  879 */       if (planetDetailHashTable.containsKey(rahuAndKetu))
						/*      */       {
												if(logger.isDebugEnabled())
							/*  881 */         logger.debug("My conjHashTable contain key for planet || " + rahuAndKetu);
							/*      */ 
							/*  883 */         PlanetDetailBean rashiBean = (PlanetDetailBean)planetDetailHashTable.get(rahuAndKetu);
							/*      */ 				if(logger.isDebugEnabled())
							/*  885 */         logger.debug("Degree Value of planet [" + rahuAndKetu + "] [" + rashiBean.getDegree() + "]");
							/*      */ 
							/*  887 */         plusDegree = Integer.parseInt(rashiBean.getDegree().substring(0, 2).trim()) + 4;
							/*  888 */         minusDegree = Integer.parseInt(rashiBean.getDegree().substring(0, 2).trim()) - 4;
							/*      */ 			if(logger.isDebugEnabled())
							/*  890 */         logger.debug(" Before PlusDegree [" + plusDegree + "] MinusDegree [" + minusDegree + "] Rashi Name [ " + rashiBean.getSignNumber());
							/*  891 */         if (plusDegree > 30)
								/*      */         {
									/*  893 */           isForword = true;
									/*  894 */           plusDegree -= 30;
									/*      */         }
							/*  896 */         if (minusDegree < 0)
								/*      */         {
									/*  898 */           isBackword = true;
									/*  899 */           minusDegree += 30;
									/*      */         }
							/*      */ 			if(logger.isDebugEnabled())
							/*  902 */         logger.debug("After PlusDegree [" + plusDegree + "] MinusDegree [" + minusDegree + "] Forward Flag [" + isForword + "] Backword Flag [" + isBackword + "]");
							/*      */ 
							/*  906 */         Iterator it = planetDetailHashTable.values().iterator();
							/*  907 */         while (it.hasNext())
								/*      */         {
									/*  909 */           insertStatus = false;
									/*      */ 
									/*  911 */           PlanetDetailBean obj = (PlanetDetailBean)it.next();
									/*      */ 
									/*  913 */           if (obj.getSignName().equals(rashiBean.getSignName()))
										/*      */           {
																	if(logger.isDebugEnabled())
											/*  915 */             logger.debug("Sign conjuction aries at planetName " + obj.getPlanetName() + " and sign name " + obj.getSignName() + "Both planet name || " + obj.getPlanetName() + " and " + rashiBean.getPlanetName());
											/*  916 */             if (isForword)
												/*      */             {
													/*  918 */               if (minusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim()))
														/*  919 */                 insertStatus = true;
																			if(logger.isDebugEnabled())
													/*  920 */               logger.debug("Is Forword flag is true ");
													/*      */             }
											/*  922 */             if (isBackword)
												/*      */             {
													/*  924 */               if (plusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim()))
														/*  925 */                 insertStatus = true;
																			if(logger.isDebugEnabled())
													/*  926 */               logger.debug("Is Backword flag is true ");
													/*      */             }
											/*  928 */             if ((!isForword) && (!isBackword))
												/*      */             {
													/*  930 */               if ((plusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim())) || (minusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim()))) {
														/*  931 */                 insertStatus = true;
														/*      */               }
													/*      */             }
											/*      */ 
											/*      */           }
									/*      */ 
									/*  937 */           if (!insertStatus)
										/*      */             continue;
									/*  939 */           planetList.append(obj.getPlanetName());
									/*  940 */           planetList.append("_");
									/*      */         }
							/*      */ if(logger.isDebugEnabled())
							/*  945 */         logger.debug("Data Insert into planetList " + planetList.toString());
							/*      */ 
							/*  949 */         String forBackSignName = "";
							/*  950 */         int nextPrevSignNumber = -1;
							/*  951 */         Rashi myrashi = Rashi.toRashi(rashiBean.getSignName());
							/*      */ if(logger.isDebugEnabled())
							/*  953 */         logger.debug("My Previous Rashi Name || " + rashiBean.getSignName());
							/*  954 */         insertStatus = false;
							/*  955 */         if (isForword)
								/*      */         {
									/*  957 */           nextPrevSignNumber = myrashi.ordinal() + 1;
									/*      */ 
									/*  959 */           if (nextPrevSignNumber == 12)
										/*  960 */             nextPrevSignNumber = 0;
									/*      */         }
							/*  962 */         if (isBackword)
								/*      */         {
									/*  964 */           nextPrevSignNumber = myrashi.ordinal() - 1;
									/*  965 */           if (nextPrevSignNumber == -1) {
										/*  966 */             nextPrevSignNumber = 0;
										/*      */           }
									/*      */         }
							/*  969 */         if (nextPrevSignNumber >= 0)
								/*      */         {
									/*  971 */           forBackSignName = myrashi.fromOrdinal(nextPrevSignNumber).toString();
														if(logger.isDebugEnabled())
									/*  972 */           logger.debug("My PreviousORNext  Rashi Name || " + forBackSignName);
									/*  973 */           insertStatus = true;
									/*      */         }
							/*      */ 
							/*  976 */         if (insertStatus)
								/*      */         {		if(logger.isDebugEnabled())
									/*  978 */           logger.debug("Checking for forword/backword sign");
									/*  979 */           Iterator itr = planetDetailHashTable.values().iterator();
									/*  980 */           while (itr.hasNext())
										/*      */           {
											/*  982 */             insertStatus = false;
											/*      */ 
											/*  984 */             PlanetDetailBean objBen = (PlanetDetailBean)itr.next();
											/*      */ 
											/*  986 */             if (objBen.getSignName().equals(rashiBean.getSignName()))
												/*      */             {
																		if(logger.isDebugEnabled())
														/*  988 */               logger.debug("Sign conjuction aries at planetName " + objBen.getPlanetName() + " and sign name " + objBen.getSignName());
													/*  989 */               if (isForword)
														/*      */               {
															/*  991 */                 if (minusDegree <= Integer.parseInt(objBen.getDegree().substring(0, 2).trim()))
																/*  992 */                   insertStatus = true;
																					if(logger.isDebugEnabled())
															/*  993 */                 logger.debug("Is Forword flag is true ");
															/*      */               }
													/*  995 */               if (isBackword)
														/*      */               {
															/*  997 */                 if (plusDegree <= Integer.parseInt(objBen.getDegree().substring(0, 2).trim()))
																/*  998 */                   insertStatus = true;
																					if(logger.isDebugEnabled())
															/*  999 */                 logger.debug("Is Backword flag is true ");
															/*      */               }
													/*      */             }
											/*      */ 
											/* 1003 */             if (!insertStatus)
												/*      */               continue;
											/* 1005 */             planetList.append(objBen.getPlanetName());
											/* 1006 */             planetList.append("_");
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */         else
								/*      */         {
														if(logger.isDebugEnabled())
									/* 1013 */           logger.debug("There is no any foword/backword planet for this sign " + rashiBean.getSignNumber());
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */ 
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/* 1021 */       ex.printStackTrace();
					/*      */     }
			/*      */ 
			/* 1025 */     return planetList.toString();
			/*      */   }
	/*      */ 
	/*      */   public String getDashaPlanetName()
		/*      */   {
		if(logger.isDebugEnabled())
							{
			/* 1031 */     logger.debug("Inside Function getDashaPlanetName() find dasha planet ");
			/* 1031 */     logger.debug("User's TOB "+astroBean.getTOB() +" "+astroBean.getDOB() );
							}
			/*      */ 
			/* 1033 */     Iterator it = this.antardashaDetailHashTable.values().iterator();
			boolean isFutureKundli=false;
			java.util.Date currentDate=new java.util.Date();
			logger.info("currentDate=  "+currentDate);
                        try
                        {
                 
			logger.info("User's TOB "+astroBean.getTOB() );
		//	java.util.Date userDOB=new SimpleDateFormat("dd-MM-yyyy").parse(astroBean.getDOB1());
			java.util.Date userDOB=new SimpleDateFormat("yyyy-MM-dd").parse(astroBean.getDOB1());
			logger.info("userDOB=  "+userDOB);
			logger.info("compareTo=  "+userDOB.compareTo(currentDate));	
			if(userDOB.compareTo(currentDate)>0)
				isFutureKundli=true;
                        }
                        catch(java.text.ParseException e)
                        {
                        	e.printStackTrace();
                            logger.info("Excpetion in parsing user's dob ");
                            logger.error("Excpetion in parsing user's dob "+e.toString());
                        } 
			logger.info("########### IS FUTURE KUNDLI "+isFutureKundli); 
			if(!isFutureKundli)
			{ 
				while (it.hasNext())
				{
					MahaDashaBean obj = null;
					Vector data = (Vector)it.next();	
					if(logger.isDebugEnabled())
					logger.debug("## entering vector");
					for (int loop = 0; loop < data.size(); loop++)
					{
						obj = (MahaDashaBean)data.get(loop);

//						logger.info("testingggggg  ="+obj.getStartTime().trim());
						if(!StringUtils.isBlank(obj.getStartTime().trim()))
						{
//							logger.info("#my iffffffffff");
							break;
						}
						else
						{
//							logger.info("##my elseeeeeeeeee");
						}

					}

					if (!checkPeriod(obj.getStartTime().trim(), ((MahaDashaBean)data.get(data.size() - 1)).getEndTime()))
					{
//						logger.info("continue ..........");
						continue;
					}
					if(logger.isDebugEnabled()){
					logger.debug("CheckPeriod is true  Dasha Planet Name  [ " + obj.getParent() + " ] Start Time  [ " + obj.getStartTime() + " ] End Time [ " + obj.getEndTime() + " ]");
					}
					return obj.getParent();
				}
			}
			else
			{
				if(logger.isDebugEnabled()){
				logger.debug("########### Case when no matching date exist due to FUTURE KUNDLI so considering fiest most entry from ANTARDASHA" );
				}
				//for(java.util.Map.Entry<String, Vector<String>> entry : antardashaDetailHashTable.entrySet())
                                 String currentPlanet =null;
				for(java.util.Map.Entry  entry : antardashaDetailHashTable.entrySet())
                                {
					 currentPlanet = (String)entry.getKey();
				        return currentPlanet;
					// Do things with the list
				}
			}
                        return null;
		}
	/*      */ 
	/*      */   public static boolean checkPeriod(String stDate, String enDate)
		/*      */   {
			/*      */     try
				/*      */     {
					/* 1066 */       SimpleDateFormat xmlFormat = new SimpleDateFormat("dd-MM-yyyy");
					/* 1067 */       Date startDate = xmlFormat.parse(stDate);
					/* 1068 */       Date endDate = xmlFormat.parse(enDate);
					/* 1069 */       Date currentDate = new Date();
					/* 1070 */       currentDate = xmlFormat.parse(xmlFormat.format(currentDate));
					/*      */ 
					/* 1072 */       Calendar startCal = Calendar.getInstance();
					/* 1073 */       startCal.setTime(startDate);
					/*      */ 
					/* 1075 */       Calendar endCal = Calendar.getInstance();
					/* 1076 */       endCal.setTime(endDate);
					/*      */ 
					/* 1078 */       Calendar cCal = Calendar.getInstance();
					/* 1079 */       cCal.setTime(currentDate);
					/*      */ 
					/* 1086 */       return (startCal.getTimeInMillis() <= cCal.getTimeInMillis()) && (cCal.getTimeInMillis() <= endCal.getTimeInMillis());
					/*      */     }
			/*      */     catch (Exception e)
				/*      */     {
					/* 1093 */       e.printStackTrace();
					/* 1094 */     }return false;
			/*      */   }
	/*      */ 
	/*      */   public String getMDLValue(String planetName, LinkedHashMap antardashaDetailHashTable)
		/*      */   {
							if(logger.isDebugEnabled()){
			/* 1101 */     logger.debug("Inside Function getMDLValue get MDLValue from  antardashaDetailHashTable fot planet [ " + planetName + " ] Root Parent [ " + this.dashaPlanetName + " ]");
							}
			/*      */     try
				/*      */     {
					/* 1104 */       if (antardashaDetailHashTable.containsKey(this.dashaPlanetName))
						/*      */       {
							/* 1106 */         Vector data = (Vector)antardashaDetailHashTable.get(this.dashaPlanetName);
							/* 1107 */         Iterator itr = data.iterator();
							/* 1108 */         while (itr.hasNext())
								/*      */         {
									/* 1110 */           MahaDashaBean dashaBean = (MahaDashaBean)itr.next();
									/* 1111 */           if (dashaBean.getPlanetName().equalsIgnoreCase(planetName))
										/*      */           {
											/* 1113 */             return dashaBean.getEndTime();
											/*      */           }
									/*      */         }
							/*      */       }
					/*      */       else
						/*      */       {
												if(logger.isDebugEnabled()){
							/* 1119 */         logger.debug("Inside Function getMDLValue no any hash key [ " + this.dashaPlanetName + " ]  found in antardashaDetailHashTable ");
												}
							/*      */       }
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/* 1124 */       ex.printStackTrace();
					/*      */     }
			/*      */ 
			/* 1127 */     return null;
			/*      */   }
	/*      */ 
	/*      */   public void getVacantHouseList(Kundli cuspKundli, LinkedHashMap vacantHouseHashTable, LinkedHashMap planetDetailHashTable)
		/*      */   {
						   if(logger.isDebugEnabled()){
			/* 1135 */     logger.debug("Inside Function getVacantHouseList For Getting Vacant HashTable " + cuspKundli.toString());
			/*      */ }
			/* 1138 */     for (int planet = 0; planet < this.planetName.length; planet++)
				/*      */     {
					/* 1140 */       vacantHouseHashTable.put(this.planetName[planet], new StringBuffer());
					/*      */     }
			/*      */ 
			/*      */     try
				/*      */     {
					/* 1145 */       for (int houseNo = 1; houseNo <= 12; houseNo++)
						/*      */       {
											if(logger.isDebugEnabled())
							/* 1147 */         logger.debug("******************************Check For House No [ " + houseNo + " ] Value Of House [ " + cuspKundli.getHouseData(houseNo) + " ] ****************");
							/*      */ 
							/* 1150 */         String[] houseDetail = cuspKundli.getHouseData(houseNo).split("_");
							/*      */ 
							/* 1152 */         if (houseDetail.length != 1)
								/*      */           continue;
							if(logger.isDebugEnabled())
							/* 1154 */         logger.debug("My House Number [ " + houseNo + " ] is Vacant and Sign Number [ " + houseDetail[0] + " ] Planet Name For This Sign Number" + Planet.fromOrdinal(Integer.parseInt(houseDetail[0])) + " ] ");
							/*      */ 
							/* 1157 */         if (!StringUtils.isBlank(Planet.fromOrdinal(Integer.parseInt(houseDetail[0])).toString())) {
								/* 1158 */           insertVacantHouseEntry(houseNo, Planet.fromOrdinal(Integer.parseInt(houseDetail[0])).toString(), vacantHouseHashTable, planetDetailHashTable);
								/*      */         }
							/*      */ 
							/*      */       }
					/*      */ 
									if(logger.isDebugEnabled())
					/* 1163 */       logger.debug(" VacantHouse HashTable  Values  ||  " + vacantHouseHashTable.toString());
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/* 1168 */       ex.printStackTrace();
					/*      */     }
			/*      */   }
	/*      */ 
	/*      */   public void insertVacantHouseEntry(int houseNumber, String planetName, LinkedHashMap vacantHouseHashTable, LinkedHashMap planetDetailHashTable)
		/*      */   {
						   if(logger.isDebugEnabled()){
			/* 1176 */     logger.debug("Inside Function insertVacantHouseEntry For Search Planet Name " + planetName);
						   }
			/* 1177 */     boolean checkForSL = true;
			/* 1178 */     boolean checkForSS = true;
			/* 1179 */     StringBuffer vacantHouseList = new StringBuffer();
			/*      */     try
				/*      */     {
					/* 1182 */       Iterator nlIterator = planetDetailHashTable.values().iterator();
					/*      */ 
					/* 1186 */      // logger.info("Check For NL in Planets List");
					/* 1187 */       while (nlIterator.hasNext())
						/*      */       {
							/* 1189 */         PlanetDetailBean planetBean = (PlanetDetailBean)nlIterator.next();
							/*      */ 
							/* 1193 */         if ((planetBean.getPlanetName().equalsIgnoreCase("Uranus")) || (planetBean.getPlanetName().equalsIgnoreCase("Neptune")) || (planetBean.getPlanetName().equalsIgnoreCase("Pluto")) || (planetBean.getPlanetName().equalsIgnoreCase("Lagna")))
								/*      */         {
															if(logger.isDebugEnabled())
									/* 1196 */           logger.debug("NL :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  " + planetBean.getPlanetName() + " ]");
									/*      */         }
							/*      */         else
								/*      */         {
									/* 1204 */           if (!planetBean.getNL().equalsIgnoreCase(planetName))
										/*      */             continue;
									/* 1206 */           checkForSL = false;
									if(logger.isDebugEnabled())
									/* 1207 */           logger.debug(" NL [ " + planetBean.getNL() + " Planet Name [ " + planetBean.getPlanetName() + " ]");
									/* 1208 */           break;
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */ 
					/* 1217 */       if (checkForSL)
						/*      */       {
													if(logger.isDebugEnabled())
							/* 1219 */         logger.debug("Inside Check For SL in Planet List [Default Entry For Planet ] [ " + planetName + " ] House No. [ " + houseNumber + " ]");
							/* 1220 */         vacantHouseList.append(planetName);
							/* 1221 */         vacantHouseList.append("-");
							/* 1222 */         vacantHouseList.append(Integer.toString(houseNumber));
							/*      */ 
							/* 1224 */         Iterator slIterator = planetDetailHashTable.values().iterator();
							/*      */ 
							if(logger.isDebugEnabled())
							/* 1226 */         logger.debug("Check For SL in Planets List");
							/* 1227 */         while (slIterator.hasNext())
								/*      */         {
									/* 1229 */           PlanetDetailBean planetBean = (PlanetDetailBean)slIterator.next();
									/*      */ 
															if(logger.isDebugEnabled())
									/* 1231 */           logger.debug(" SL [ " + planetBean.getSL() + " ]  Planet Name [ " + planetBean.getPlanetName() + " ]");
									/*      */ 
									/* 1233 */           if ((planetBean.getPlanetName().equalsIgnoreCase("Uranus")) || (planetBean.getPlanetName().equalsIgnoreCase("Neptune")) || (planetBean.getPlanetName().equalsIgnoreCase("Pluto")) || (planetBean.getPlanetName().equalsIgnoreCase("Lagna")))
										/*      */           {
																if(logger.isDebugEnabled())
											/* 1236 */             logger.debug("SL :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  " + planetBean.getPlanetName() + " ]");
											/*      */           }
									/*      */           else
										/*      */           {
											/* 1241 */             if (!planetBean.getSL().equalsIgnoreCase(planetName))
												/*      */               continue;
											/* 1243 */             checkForSS = false;
											/* 1244 */             vacantHouseList.append("_");
											/* 1245 */             vacantHouseList.append(planetBean.getPlanetName());
											/* 1246 */             vacantHouseList.append("-");
											/* 1247 */             vacantHouseList.append(Integer.toString(houseNumber));
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */ 
														if(logger.isDebugEnabled())
							/* 1253 */         logger.debug("After SL iteration Vacant Planet Lists || " + vacantHouseList.toString());
							/*      */       }
					/*      */ 
					/* 1258 */       if ((checkForSS) && (checkForSL))
						/*      */       {
												if(logger.isDebugEnabled())
							/* 1260 */         logger.debug("Inside Check For SS in Planet List  [ " + planetName + " ] House No. [ " + houseNumber + " ]");
							/*      */ 
							/* 1262 */         Iterator ssIterator = planetDetailHashTable.values().iterator();
							/*      */ 
												if(logger.isDebugEnabled())
							/* 1264 */         logger.debug("Check For SL in Planets List");
							/* 1265 */         while (ssIterator.hasNext())
								/*      */         {
									/* 1267 */           PlanetDetailBean planetBean = (PlanetDetailBean)ssIterator.next();
									/*      */ 
									/* 1269 */           if ((planetBean.getPlanetName().equalsIgnoreCase("Uranus")) || (planetBean.getPlanetName().equalsIgnoreCase("Neptune")) || (planetBean.getPlanetName().equalsIgnoreCase("Pluto")) || (planetBean.getPlanetName().equalsIgnoreCase("Lagna")))
										/*      */           {
										if(logger.isDebugEnabled())
											/* 1271 */             logger.debug("SS :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  " + planetBean.getPlanetName() + " ]");
											/*      */           }
									/*      */           else
										/*      */           {
											/* 1277 */             if (!planetBean.getSS().equalsIgnoreCase(planetName))
												/*      */             {
													/*      */               continue;
													/*      */             }
											/*      */ 
											/* 1282 */             vacantHouseList.append("_");
											/* 1283 */             vacantHouseList.append(planetBean.getPlanetName());
											/* 1284 */             vacantHouseList.append("-");
											/* 1285 */             vacantHouseList.append(Integer.toString(houseNumber));
											/*      */           }
									/*      */ 
									/*      */         }
							/*      */ 
							if(logger.isDebugEnabled())
							/* 1291 */         logger.debug("After SS iteration Vacant Planet Lists || " + vacantHouseList.toString());
							/*      */       }
					/*      */ 
					/* 1296 */       if (!StringUtils.isBlank(vacantHouseList.toString()))
						/*      */       {
						if(logger.isDebugEnabled())
							/* 1298 */         logger.debug("Put String Values in HashTable || " + vacantHouseList.toString());
							/* 1299 */         String[] outer = vacantHouseList.toString().split("_");
							/* 1300 */         for (String str : outer)
								/*      */         {
								if(logger.isDebugEnabled())
									/* 1302 */           logger.debug("Check For String Iteration ||||||| " + str);
									/* 1303 */           String[] inner = str.toString().split("-");
									/*      */ 
									/* 1306 */           if (!vacantHouseHashTable.containsKey(inner[0]))
										/*      */             continue;
															if(logger.isDebugEnabled())
									/* 1308 */           logger.debug("Insert Data For Planet " + inner[0]);
									/* 1309 */           StringBuffer data = (StringBuffer)vacantHouseHashTable.get(inner[0]);
									/* 1310 */           data.append(inner[1]);
									/* 1311 */           data.append(",");
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */ 
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
					/* 1323 */       ex.printStackTrace();
					/*      */     }
			/*      */   }
	/*      */ 
	/*      */   public String getVacantHouse(String planetName)
		/*      */   {
			/* 1333 */     String vacanetHouse = null;
			/* 1334 */     if (this.vacantHouseHashTable.containsKey(planetName))
				/*      */     {
					/* 1336 */       vacanetHouse = ((StringBuffer)this.vacantHouseHashTable.get(planetName)).toString();
					/* 1337 */       if (!StringUtils.isBlank(vacanetHouse))
						/*      */       {
							/* 1339 */         if (vacanetHouse.endsWith(","))
								/*      */         {
									/* 1341 */           return vacanetHouse.substring(0, vacanetHouse.length() - 1);
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */       else
						/*      */       {
							/* 1347 */         return vacanetHouse;
							/*      */       }
					/*      */     }
			/*      */ 
			/* 1351 */     return null;
			/*      */   }
	/*      */ 
	/*      */   public static void rotateArrayByDashaPlanet(String[] sourse, String[] destinetion, String indexValue)
		/*      */   {
						   if(logger.isDebugEnabled()){
			/* 1356 */     logger.debug("Inside Function  rotateArrayByDashaPlanet rotaion of array with dasha planet name || " + indexValue);
						   }
			/* 1357 */     int location = -1;
			/* 1358 */     for (int loop = 0; loop < sourse.length; loop++)
				/*      */     {
					/* 1360 */       if (!indexValue.equalsIgnoreCase(sourse[loop]))
						/*      */         continue;
					/* 1362 */       location = loop;
										if(logger.isDebugEnabled())
					/* 1363 */       logger.debug("Source Index is [ " + loop + " ] Index Value [ " + sourse[loop] + " ]");
					/* 1364 */       break;
					/*      */     }
			/*      */ 
			/* 1369 */     if (location > -1)
				/*      */     {
					/* 1371 */       for (int i = 0; i < destinetion.length; i++)
						/*      */       {
							/* 1373 */         destinetion[i] = sourse[location];
							/* 1374 */         location++;
							/* 1375 */         if (location == sourse.length)
								/* 1376 */           location = 0;
							/*      */       }
					/*      */     }
						   if(logger.isDebugEnabled()){
			/* 1379 */     logger.debug("Source Value [ " + Arrays.toString(sourse) + " ]");
			/* 1380 */     logger.debug("Destinetion Value [ " + Arrays.toString(destinetion) + " ]");
						   }
			/*      */   }
	/*      */ 
	/*      */   public String getDashaTabledata(LinkedHashMap antardashaDetailHashTable)
		/*      */   {	   if(logger.isDebugEnabled()){
			/* 1386 */     logger.debug("Inside Function getDashaTabledata()  for getting dasha Tabular Data ");
			/*      */ }
			/* 1388 */     SimpleDateFormat yearDate = new SimpleDateFormat("yyyy");
			/* 1389 */     StringBuffer dashaTabledata = new StringBuffer();
			/*      */ 
			/* 1391 */     String currentYear = yearDate.format(new Date());
			/* 1392 */     String currentdashaYear = null;
			/* 1393 */     //logger.info("My Current Year " + currentYear);
			/*      */     try
				/*      */     {
					/* 1397 */       if (antardashaDetailHashTable.containsKey(this.dashaPlanetName))
						/*      */       {
							/* 1399 */         Vector data = (Vector)antardashaDetailHashTable.get(this.dashaPlanetName);
							/*      */ 
							/* 1401 */         MahaDashaBean obj = (MahaDashaBean)data.get(data.size() - 1);
						/*      */ 
//							/* 1404 */         logger.info("My MDL VALUE ");
							/*      */ 
							/* 1407 */         dashaTabledata.append(obj.getParent() + "#" + obj.getEndTime());
							/*      */ 
							/* 1413 */         Iterator itr = data.iterator();
							/* 1414 */         while (itr.hasNext())
								/*      */         {
									/* 1416 */           MahaDashaBean dashaBean = (MahaDashaBean)itr.next();
									/* 1417 */           if (!dashaBean.getPlanetName().equalsIgnoreCase(this.dashaPlanetName))
										/*      */             continue;
									/* 1419 */           if (!StringUtils.isBlank(dashaBean.getEndTime()))
										/*      */           {
											/* 1424 */             currentdashaYear = dashaBean.getEndTime().substring(dashaBean.getEndTime().length() - 4, dashaBean.getEndTime().length());
											/*      */ 
											/* 1426 */             break;
											/*      */           }
									/*      */ 			if(logger.isDebugEnabled())
									/* 1430 */           logger.debug("Error inside function getDashaTabledata() current dasha end date is null");
									/* 1431 */           return dashaTabledata.toString();
									/*      */         }
							/*      */ 
							/* 1438 */         Vector currentVector = (Vector)antardashaDetailHashTable.get(this.dashaPlanetName);
							/* 1439 */         int counter = 0;
							/* 1440 */         for (Iterator irt = data.iterator(); irt.hasNext(); )
								/*      */         {
									/* 1442 */           MahaDashaBean dashaBean = (MahaDashaBean)irt.next();
															String t=dashaBean.getEndTime().split("  ")[0];
													
									/* 1443 */           String yeardata = t.substring(t.length() - 4, t.length());
														
											
									/* 1445 */           if (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) <= 0)
										/*      */             continue;
													if(logger.isDebugEnabled())
									/* 1447 */           logger.debug("Inside the difference is positive Planet Name [ " + dashaBean.getPlanetName() + " ]and date [ " + dashaBean.getEndTime() + " ]");
									/*      */ 
									/* 1449 */           if ((Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 1) && (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 2) && (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 3) && (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 4)) {
										/*      */             continue;
										/*      */           }
														if(logger.isDebugEnabled())
									/* 1452 */           logger.debug("Insert data into StringBuffer  Planet Name [ " + dashaBean.getPlanetName() + " ]and date [ " + dashaBean.getEndTime() + " ]");
									/* 1453 */           dashaTabledata.append("_" + dashaBean.getPlanetName() + "#" + dashaBean.getEndTime());
									/* 1454 */           if (counter == 3)
										/*      */             break;
									/* 1456 */           counter++;
									/*      */         }
							/*      */ 
							/*      */       }
					/*      */       else
						/*      */       {
												if(logger.isDebugEnabled())
							/* 1465 */         logger.debug("Inside Function getMDLValue no any hash key [ " + this.dashaPlanetName + " ]  found in antardashaDetailHashTable ");
							/*      */       }
					/*      */     }
			/*      */     catch (Exception ex)
				/*      */     {
						ex.printStackTrace();
					logger.error(ex.toString());
					/*      */     }
			/*      */ 	if(logger.isDebugEnabled())
			/* 1473 */     logger.debug("My String value Returnd from function ===== getDashaTabledata ====== " + dashaTabledata.toString());
			/*      */ 
			/* 1475 */     return dashaTabledata.toString();
			/*      */   }
	/*      */ }

	/* Location:           C:\Users\SUCCESS\Downloads\kundli.jar
	 * Qualified Name:     com.telemune.astro.GenerateDashaKundli
	 * JD-Core Version:    0.6.0
	 */

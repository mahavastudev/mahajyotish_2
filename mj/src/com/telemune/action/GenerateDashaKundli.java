package com.telemune.action;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.telemune.model.AstroBean;
import com.telemune.model.HouseDetailBean;
import com.telemune.model.KundliHouseBean;
import com.telemune.model.MahaDashaBean;
import com.telemune.model.PlanetDetailBean;
import com.telemune.model.Kundli;

public class GenerateDashaKundli {
	static Logger logger = Logger.getLogger(GenerateDashaKundli.class);

	String[] planetName = { "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury" };
	String[] rahuAndKetu = { "Rahu", "Ketu" };
	String[] planetEffect = { "Jupiter", "Saturn", "Mars" };
	String[] rotatePlanetName = new String[this.planetName.length];
	String dashaPlanetName = "CURRENT";
	AstroBean astroBean = null;

	Hashtable<String, KundliHouseBean> dashaCuspPlanetHashTable = new Hashtable();
	Hashtable<String, KundliHouseBean> dashaCuspHouseHashTable = new Hashtable();

	Hashtable<String, KundliHouseBean> cuspPlanetHashTable = new Hashtable();
	Hashtable<String, KundliHouseBean> cuspHouseHashTable = new Hashtable();

	Hashtable<String, KundliHouseBean> birthPlanetHashTable = new Hashtable();
	Hashtable<String, KundliHouseBean> birthHouseHashTable = new Hashtable();

	LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, HouseDetailBean> houseDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, StringBuffer> vacantHouseHashTable = new LinkedHashMap();

	public GenerateDashaKundli() {
	}

	public GenerateDashaKundli(AstroBean astroBean, String dashaPlanetName) {
		logger.info("Inside Constructor of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled())
			logger.debug(
					"Inside Creating Constructor of GenerateDashaKundli My Dasha Planet Name || " + dashaPlanetName);

		this.dashaPlanetName = dashaPlanetName;

		this.astroBean = astroBean;
		try {
			this.cuspPlanetHashTable = astroBean.getCuspPlanetHashTable();
			this.cuspHouseHashTable = astroBean.getCuspHouseHashTable();

			this.birthPlanetHashTable = astroBean.getBirthPlanetHashTable();
			this.birthHouseHashTable = astroBean.getBirthHouseHashTable();

			logger.info("Successfully Load Birth Chart And Cusp chart details");

			this.planetDetailHashTable = astroBean.getPlanetDetailHashTable();

			this.houseDetailHashTable = astroBean.getHouseDetailHashTable();

			logger.info("Successfully Load Planets and Houses details");

			this.antardashaDetailHashTable = astroBean.getAntardashaDetailHashTable();

			this.sookshmadashaDetailHashTable = astroBean.getSookshmadashaDetailHashTable();

			logger.info("Successfully Load  antardashaDetail and sookshmadashaDetail");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public AstroBean getDashaStrength() {
		logger.info("Inside getDashaStrength method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug(
					"*******************************************Dasha Bean Object creation********************************************");
			logger.debug("Inside Function  getDashaStrength() for getting dasha Chart  Planet name  [ "
					+ this.dashaPlanetName + " ]");
		}
		DashaStrength dashaBean = new DashaStrength();

		if (this.dashaPlanetName.equalsIgnoreCase("CURRENT")) {
			dashaBean.setCurrentFlag(true);
		}

		Kundli dashaKundli = new Kundli("CUSP");
		try {
			if (logger.isDebugEnabled())
				logger.debug("Before Call  rotateCuspChartWithDashaPlanet function " + this.dashaPlanetName);
			rotateCuspChartWithDashaPlanet(this.dashaPlanetName, this.cuspHouseHashTable, this.cuspPlanetHashTable,
					this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable, dashaKundli);
			if (logger.isDebugEnabled())
				logger.debug("Dasha Chart Values || " + dashaKundli.toString());

			dashaBean.setCuspKundli(dashaKundli);

			getVacantHouseList(dashaKundli, this.vacantHouseHashTable, this.planetDetailHashTable);

			String[][] tabularData = new String[9][7];

			String rahuRashiLord = getNLAndSL("RL", "Rahu", this.planetDetailHashTable);
			String ketuRashiLord = getNLAndSL("RL", "Ketu", this.planetDetailHashTable);
			if (!StringUtils.isBlank(rahuRashiLord)) {
				rahuRashiLord = searchPlanetLocation(rahuRashiLord, this.cuspPlanetHashTable, this.cuspHouseHashTable);
			}

			if (!StringUtils.isBlank(ketuRashiLord)) {
				ketuRashiLord = searchPlanetLocation(ketuRashiLord, this.cuspPlanetHashTable, this.cuspHouseHashTable);
			}

			if (logger.isDebugEnabled())
				logger.debug(" My Rahu ketu sign houses number rahuRashiLord [ " + rahuRashiLord + " ] ketuRashiLord [ "
						+ ketuRashiLord + " ]");

			rahuRashiLord = rahuRashiLord.substring(rahuRashiLord.indexOf("/") + 1, rahuRashiLord.length());
			ketuRashiLord = ketuRashiLord.substring(ketuRashiLord.indexOf("/") + 1, ketuRashiLord.length());

			for (int loop = 0; loop < this.planetName.length; loop++) {
				String sourceValue = searchPlanetLocation(this.planetName[loop], this.dashaCuspPlanetHashTable,
						this.dashaCuspHouseHashTable);

				String resultNL = getNLAndSL("NL", this.planetName[loop], this.planetDetailHashTable);
				if (!StringUtils.isBlank(resultNL)) {
					resultNL = resultNL + searchPlanetLocation(resultNL.trim(), this.dashaCuspPlanetHashTable,
							this.dashaCuspHouseHashTable);
				}

				String resultSL = getNLAndSL("SL", this.planetName[loop], this.planetDetailHashTable);
				if (!StringUtils.isBlank(resultSL)) {
					resultSL = resultSL + searchPlanetLocation(resultSL.trim(), this.dashaCuspPlanetHashTable,
							this.dashaCuspHouseHashTable);
				}

				String resultNLOfSL = getNLAndSL("SL", this.planetName[loop], this.planetDetailHashTable);
				if (!StringUtils.isBlank(resultNLOfSL)) {
					resultNLOfSL = getNLAndSL("NL", resultNLOfSL.trim(), this.planetDetailHashTable);
				}
				if (!StringUtils.isBlank(resultNLOfSL)) {
					resultNLOfSL = resultNLOfSL + searchPlanetLocation(resultNLOfSL.trim(),
							this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
				}
				String MDLValue = getMDLValue(this.planetName[loop], this.antardashaDetailHashTable);

				String vacantHouse = getVacantHouse(this.planetName[loop]);

				tabularData[loop][0] = this.planetName[loop];
				tabularData[loop][1] = sourceValue;
				tabularData[loop][2] = resultNL;
				tabularData[loop][3] = vacantHouse;
				tabularData[loop][4] = resultSL;
				tabularData[loop][5] = resultNLOfSL;
				tabularData[loop][6] = MDLValue;

				if (resultNL.contains("Rahu"))
					tabularData[loop][2] = (resultNL + rahuRashiLord);
				if (resultSL.contains("Rahu"))
					tabularData[loop][4] = (resultSL + rahuRashiLord);
				if (resultNLOfSL.contains("Rahu"))
					tabularData[loop][5] = (resultNLOfSL + rahuRashiLord);
				if (this.planetName[loop].equalsIgnoreCase("Rahu")) {
					tabularData[loop][1] = (sourceValue + rahuRashiLord);
				}

				if (resultNL.contains("Ketu"))
					tabularData[loop][2] = (resultNL + ketuRashiLord);
				if (resultSL.contains("Ketu"))
					tabularData[loop][4] = (resultSL + ketuRashiLord);
				if (resultNLOfSL.contains("Ketu"))
					tabularData[loop][5] = (resultNLOfSL + ketuRashiLord);
				if (this.planetName[loop].equalsIgnoreCase("Ketu")) {
					tabularData[loop][1] = (sourceValue + ketuRashiLord);
				}

			}

			dashaBean.setStrengthChart(tabularData);

			dashaBean.setDashaPlanet(this.dashaPlanetName);
			if (logger.isDebugEnabled())
				logger.debug(" Tabular Format Data inserted in 2D array ");
			for (int outer = 0; outer < tabularData.length; outer++) {
				for (int inner = 0; inner < tabularData[outer].length; inner++) {
					if (logger.isDebugEnabled())
						logger.debug("     " + tabularData[outer][inner]);
				}
				if (logger.isDebugEnabled())
					logger.debug("************************");
			}
			if (logger.isDebugEnabled())
				logger.debug("Generate  Rahu/Ketu planets effect ");

			getRahuKetuShadowEffect(dashaBean, this.birthPlanetHashTable, this.birthHouseHashTable);
			if (logger.isDebugEnabled())
				logger.debug("Load  Cache of DashaCuspPlanetDetail/DashaCuspHouseDetail into DashaBean Object ");

			dashaBean.setDashaCuspHouseHashTable(this.dashaCuspHouseHashTable);
			dashaBean.setDashaCuspPlanetHashTable(this.dashaCuspPlanetHashTable);

			dashaBean.setDashaStrengthData(getDashaTabledata(this.antardashaDetailHashTable));
			if (logger.isDebugEnabled())
				logger.debug("Store DashaBean Object into Astro Bean Object");

			this.astroBean.setDashaStrength(dashaBean);
			if (logger.isDebugEnabled()) {
				logger.debug("Successfully Store Dashabean Object into AstroBean Object");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return this.astroBean;
	}

	public void rotateCuspChartWithDashaPlanet(String dashsPlanetName, Hashtable houseHashTable,
			Hashtable planetHashTable, Hashtable dashaPlanetHashTable, Hashtable dashaSignHashTable,
			Kundli dashaChartDetail) {
		logger.info("Inside rotateCuspChartWithDashaPlanet method of GenerateDashaKundli action Class.");
		logger.info(
				"Inside Function rotateCuspChartWithDashaPlanet  rotate Cusp chart according to the planets either Current/Any Planet || "
						+ dashsPlanetName);
		
		try{

		String dashaPlanet = dashsPlanetName;
		if (dashaPlanet.equalsIgnoreCase("CURRENT")) {
			if (logger.isDebugEnabled())
				logger.debug(
						"My Dasha Planet Name is Not Define So Call getDashaPlanetName Function For Getting Dasha Planete Name ");
			dashaPlanet = getDashaPlanetName();
			this.dashaPlanetName = dashaPlanet;
			if (logger.isDebugEnabled())
				logger.debug("IF :: Inside Call function for search planet name ||  " + dashaPlanet);
		}

		if (!StringUtils.isBlank(dashaPlanet)) {
			if (planetHashTable.containsKey(dashaPlanet)) {
				KundliHouseBean obj = (KundliHouseBean) planetHashTable.get(dashaPlanet);
				if (logger.isDebugEnabled())
					logger.debug("Get Current House Number for rotation   Dasha Planet Name [ " + dashaPlanet
							+ " ] House Number [ " + obj.getHouseNumber() + " ]");

				int rotateHouseNumber = Integer.parseInt(obj.getHouseNumber());
				int counter = 1;
				int newID = 0;
				for (int loop = rotateHouseNumber; loop <= rotateHouseNumber + 11; loop++) {
					StringBuffer dashaChartData = new StringBuffer();
					if (loop % 12 != 0)
						newID = loop % 12;
					else {
						newID = loop;
					}
					if (logger.isDebugEnabled())
						logger.debug("My HashTable  Iteration  loop value  [ " + loop + " ]  New ID value  [ " + newID
								+ " ]");

					KundliHouseBean beanObject = (KundliHouseBean) houseHashTable.get(Integer.toString(newID));

					dashaChartData.append(beanObject.getSignNumber());

					if (!StringUtils.isBlank(beanObject.getPlanetName())) {
						dashaChartData.append("_");
						dashaChartData.append(beanObject.getPlanetName());
					}

					dashaChartDetail.setHouseData(counter, dashaChartData.toString());
					if (logger.isDebugEnabled()) {
						logger.debug("Dasha Kundli House Number [ " + counter + " ] [  Houses Values "
								+ dashaChartData.toString() + " ]");

						logger.debug("OLD ::house number [ " + loop + " ] Sign Number  [ " + beanObject.getSignNumber()
								+ "] House Number [ " + beanObject.getHouseNumber() + " ]");
					}
					beanObject.setHouseNumber(Integer.toString(counter));
					if (logger.isDebugEnabled())
						logger.debug("NEW :: insert value in  dashaSignHashTable  Bean Object value at house number [ "
								+ counter + " ] Sign Number  [ " + beanObject.getSignNumber() + " House Number "
								+ beanObject.getHouseNumber() + " ]");
					dashaSignHashTable.put(Integer.toString(counter), beanObject);

					if (!StringUtils.isBlank(beanObject.getPlanetName())) {
						logger.info(" Planet Value is not null for house number " + loop + "Planet Names "
								+ beanObject.getPlanetName());

						String[] planetName = beanObject.getPlanetName().split(" ");
						for (String planet : planetName) {
							KundliHouseBean planetObj = new KundliHouseBean();
							planetObj.setPlanetName(planet.trim());
							planetObj.setHouseNumber(Integer.toString(counter));
							planetObj.setSignNumber(beanObject.getSignNumber().trim());
							dashaPlanetHashTable.put(planet.trim(), planetObj);

							if (logger.isDebugEnabled())
								logger.debug(" Insert value in :: dashaPlanetHashTable:: table   Key " + planet
										+ " House Value " + planetObj.getHouseNumber() + " sign Value "
										+ planetObj.getSignNumber());
						}

					}

					counter++;
				}

			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("There is no any key for " + dashaPlanet + "Inside planetHashTable ");
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"There is no any dasha Planet founds from function getDashaPlanetName() Dasha Planet Values || "
								+ dashaPlanet);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String searchPlanetLocation(String planetName, Hashtable cuspPlanetHashTable, Hashtable cuspHouseHashTable) {
		logger.info("Inside searchPlanetLocation method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function  searchPlanetLocation for planet name || " + planetName);
		}
		StringBuffer retObj = new StringBuffer();

		retObj.append(" ");
		try {
			if (cuspPlanetHashTable.containsKey(planetName)) {
				KundliHouseBean obj = (KundliHouseBean) cuspPlanetHashTable.get(planetName);
				retObj.append(obj.getHouseNumber());
				if (logger.isDebugEnabled())
					logger.debug("cuspPlanetHashTable  Contains  [" + planetName + " ] House Number  ["
							+ obj.getHouseNumber() + " ]");

				Planet getPlanetSignNumber = Planet.toPlanets(planetName);
				String[] signStr;
				if (getPlanetSignNumber.getSecondRashi() != 0) {
					signStr = new String[2]; // String[] removed by prianka
					signStr[0] = Integer.toString(getPlanetSignNumber.getFirstRashi());
					signStr[1] = Integer.toString(getPlanetSignNumber.getSecondRashi());
				} else {
					signStr = new String[2];
					signStr[0] = Integer.toString(getPlanetSignNumber.getFirstRashi());
				}

				if (logger.isDebugEnabled())
					logger.debug("Getting Rashi numbers  of Planet [ " + planetName + " ] Array Length [ "
							+ signStr.length + " ] FirstRashi No. [ " + getPlanetSignNumber.getFirstRashi()
							+ " ] Second Rashi No.[ " + getPlanetSignNumber.getSecondRashi() + " ]");

				retObj.append("/ ");

				for (String st : signStr) {
					Iterator itr = cuspHouseHashTable.values().iterator();

					while (itr.hasNext()) {
						KundliHouseBean ob = (KundliHouseBean) itr.next();
						if (!ob.getSignNumber().equals(st))
							continue;
						retObj.append(ob.getHouseNumber());
						retObj.append(",");
					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (logger.isDebugEnabled())
			logger.debug("My Planet Value is return  || " + retObj.toString());

		if (retObj.toString().endsWith(",")) {
			return retObj.toString().substring(0, retObj.toString().length() - 1);
		}
		return retObj.toString();
	}

	public String getNLAndSL(String searchAttribute, String planetName, LinkedHashMap planetDetailHashTable) {
		logger.info("Inside getNLAndSL method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside function  getNLAndSL for Search Attribute [ " + searchAttribute + " ] of planet [ "
					+ planetName + " ]");
		}
		try {
			if (planetDetailHashTable.containsKey(planetName)) {
				PlanetDetailBean planetBean = (PlanetDetailBean) planetDetailHashTable.get(planetName);

				if (searchAttribute.equalsIgnoreCase("NL")) {
					return planetBean.getNL();
				}

				if (searchAttribute.equalsIgnoreCase("SL")) {
					return planetBean.getSL();
				}
				if (searchAttribute.equalsIgnoreCase("RL")) {
					return planetBean.getRL();
				}

			} else {
				if (logger.isDebugEnabled())
					logger.debug("Inside function  getNLAndSL no any key [ " + planetName
							+ " ] found inside planetDetailHashTable ");
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return null;
	}

	public void getRahuKetuShadowEffect(DashaStrength natalStrength, Hashtable birthPlanetHashTable,
			Hashtable birthHouseHashTable) {
		logger.info("Inside getRahuKetuShadowEffect method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function  getRahuKetuShadowEffect for generate rahu/ketu effect ");
		}
		try {
			for (String str : this.rahuAndKetu) {
				String rahuKetuSource = str;

				String rahuAndKetuData = getRahuKetuEffectPlanet(str, birthPlanetHashTable, birthHouseHashTable);
				if (logger.isDebugEnabled())
					logger.debug(
							"Rahu/Ketu Effected Planets lists [ " + rahuAndKetuData + " ] for planet [ " + str + " ]");
				String[] atr = rahuAndKetuData.split(" ");
				for (String planetEffec : atr) {
					if ((planetEffec.equalsIgnoreCase("unsuccess")) || (planetEffec.equalsIgnoreCase("Rahu"))
							|| (planetEffec.equalsIgnoreCase("Ketu"))) {
						if (logger.isDebugEnabled())
							logger.debug(" Inside unsuccess and Rahu ketu Find Data");
					} else {
						if (logger.isDebugEnabled())
							logger.debug("Obtain Effect Of this planet " + planetEffec);

						if (StringUtils.isBlank(planetEffec))
							continue;
						planetEffec = planetEffec + searchPlanetLocation(planetEffec.trim(),
								this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
						if (str.equalsIgnoreCase("Rahu"))
							natalStrength.setAspect(Planet.Rahu, planetEffec);
						else if (str.equalsIgnoreCase("Ketu"))
							natalStrength.setAspect(Planet.Ketu, planetEffec);
						else {
							if (logger.isDebugEnabled())
								logger.debug("No any planet define plante name || " + str);
						}

					}

				}

				for (int loop = 0; loop < this.planetEffect.length; loop++) {
					boolean isEffected = false;
					Planet p = Planet.toPlanets(this.planetEffect[loop]);

					String ptr = this.planetEffect[loop];

					switch (loop) {
					case 0:
						isEffected = getJupiterSaturnMarsEffect(this.planetEffect[loop], str, p.getFirstAspect(),
								p.getSecondAspect(), birthPlanetHashTable, birthHouseHashTable);
						break;
					case 1:
						isEffected = getJupiterSaturnMarsEffect(this.planetEffect[loop], str, p.getFirstAspect(),
								p.getSecondAspect(), birthPlanetHashTable, birthHouseHashTable);
						break;
					case 2:
						isEffected = getJupiterSaturnMarsEffect(this.planetEffect[loop], str, p.getFirstAspect(),
								p.getSecondAspect(), birthPlanetHashTable, birthHouseHashTable);
					}

					if (isEffected) {
						if (logger.isDebugEnabled())
							logger.debug("There is aspect of [ " + str + " ] into [ " + ptr + " ]");
						ptr = ptr + searchPlanetLocation(ptr, this.dashaCuspPlanetHashTable,
								this.dashaCuspHouseHashTable);
						if (str.equalsIgnoreCase("Rahu"))
							natalStrength.setAspect(Planet.Rahu, ptr);
						else if (str.equalsIgnoreCase("Ketu"))
							natalStrength.setAspect(Planet.Ketu, ptr);
						else {
							if (logger.isDebugEnabled())
								logger.debug("No any planet define plante name || " + str);
						}

					} else {
						if (logger.isDebugEnabled())
							logger.debug("There is no any aspect of [ " + str + " ] into " + this.planetEffect[loop]);
					}

				}

				String conjPlanetLists = getConjPlanetList(str, this.planetDetailHashTable);
				if (logger.isDebugEnabled())
					logger.debug("Conjuction  Planet Lists  || " + conjPlanetLists);

				if (!StringUtils.isBlank(conjPlanetLists)) {
					String[] conj = conjPlanetLists.split("_");
					String rahuKetuConj = "";
					for (String conjPlanet : conj) {
						rahuKetuConj = conjPlanet;

						if (conjPlanet.equalsIgnoreCase(str))
							continue;
						rahuKetuConj = rahuKetuConj + searchPlanetLocation(conjPlanet, this.dashaCuspPlanetHashTable,
								this.dashaCuspHouseHashTable);

						if (str.equalsIgnoreCase("Rahu"))
							natalStrength.setConjuction(Planet.Rahu, rahuKetuConj);
						else if (str.equalsIgnoreCase("Ketu"))
							natalStrength.setConjuction(Planet.Ketu, rahuKetuConj);
						else {
							if (logger.isDebugEnabled())
								logger.debug("No any planet define plante name || " + str);
						}

						if (logger.isDebugEnabled())
							logger.debug(" Conjuction  of this planets   [ " + rahuKetuConj + " ] for [ " + str + " ]");
					}

				}

				if (logger.isDebugEnabled())
					logger.debug("Getting  Sign Loard and Rashi loard  ");

				String rahuKetuSignLord = "";
				String rahuKetuRashiLord = "";
				rahuKetuSignLord = getNLAndSL("NL", str, this.planetDetailHashTable);
				if (!StringUtils.isBlank(rahuKetuSignLord)) {
					rahuKetuSignLord = rahuKetuSignLord + searchPlanetLocation(rahuKetuSignLord,
							this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);

					if (str.equalsIgnoreCase("Rahu"))
						natalStrength.setSignLoard(Planet.Rahu, rahuKetuSignLord);
					else if (str.equalsIgnoreCase("Ketu")) {
						natalStrength.setSignLoard(Planet.Ketu, rahuKetuSignLord);
					}
				}
				rahuKetuRashiLord = getNLAndSL("RL", str, this.planetDetailHashTable);
				if (!StringUtils.isBlank(rahuKetuRashiLord)) {
					rahuKetuRashiLord = rahuKetuRashiLord + searchPlanetLocation(rahuKetuRashiLord,
							this.dashaCuspPlanetHashTable, this.dashaCuspHouseHashTable);
					if (str.equalsIgnoreCase("Rahu"))
						natalStrength.setRashiLoard(Planet.Rahu, rahuKetuRashiLord);
					else if (str.equalsIgnoreCase("Ketu")) {
						natalStrength.setRashiLoard(Planet.Ketu, rahuKetuRashiLord);
					}

				}
				if (logger.isDebugEnabled())
					logger.debug(" SignLord and RashiLord for planet name [ " + str + "] [S] " + rahuKetuSignLord
							+ " [R] " + rahuKetuRashiLord);

				rahuKetuSource = searchPlanetLocation(rahuKetuSource, this.cuspPlanetHashTable,
						this.cuspHouseHashTable);

				rahuKetuSource = rahuKetuSource
						+ rahuKetuRashiLord.substring(rahuKetuRashiLord.indexOf("/") + 1, rahuKetuRashiLord.length());
				if (str.equalsIgnoreCase("Rahu"))
					natalStrength.setRahuSource(rahuKetuSource);
				else if (str.equalsIgnoreCase("Ketu"))
					natalStrength.setKetuSource(rahuKetuSource);
				else {
					if (logger.isDebugEnabled())
						logger.debug("No any planet define plante name || " + str);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getRahuKetuEffectPlanet(String rahuAndKetu, Hashtable birthPlanetHashTable,
			Hashtable birthHouseHashTable) {
		logger.info("Inside getRahuKetuEffectPlanet method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function function getRahuKetuEffectPlanet for getting rahu/ketu effeted planets  || "
					+ rahuAndKetu);
		}
		try {
			if (birthPlanetHashTable.containsKey(rahuAndKetu)) {
				KundliHouseBean obj = (KundliHouseBean) birthPlanetHashTable.get(rahuAndKetu);

				int houseNumber = Integer.parseInt(obj.getHouseNumber());
				if (logger.isDebugEnabled())
					logger.debug("My Original HouseNumber for planet " + rahuAndKetu + " house number" + houseNumber);

				houseNumber -= 6;
				if (houseNumber <= 0) {
					houseNumber += 12;
				}
				if (logger.isDebugEnabled())
					logger.debug("After Move 6th position HouseNumber for planet " + rahuAndKetu + " house number"
							+ houseNumber);

				String houseNo = Integer.toString(houseNumber);

				if (birthHouseHashTable.containsKey(houseNo)) {
					KundliHouseBean objBean = (KundliHouseBean) birthHouseHashTable.get(houseNo);
					if (logger.isDebugEnabled())
						logger.debug(
								"Planets name return from  getRahuKetuEffectPlanet function which are effected  || "
										+ objBean.getPlanetName());
					return objBean.getPlanetName();
				}

			} else {
				if (logger.isDebugEnabled())
					logger.debug("Hash Table does not contain this planet " + rahuAndKetu);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "unsuccess";
	}

	public boolean getJupiterSaturnMarsEffect(String planetEffect, String planetName, int first, int second,
			Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable) {
		logger.info("Inside getJupiterSaturnMarsEffect method of GenerateDashaKundli action Class.");
		boolean retVal = false;

		if (logger.isDebugEnabled()) {
			logger.debug(
					"Inside function getJupiterSaturnMarsEffect for getting rahu/ketu planet effect on  Ju/Sa/Ma || "
							+ planetEffect);
		}
		try {
			if (birthPlanetHashTable.containsKey(planetEffect)) {
				KundliHouseBean obj = (KundliHouseBean) birthPlanetHashTable.get(planetEffect);

				if (logger.isDebugEnabled())
					logger.debug("Planet Name Contains " + planetEffect + "House Number " + obj.getHouseNumber());

				int houseNumber = Integer.parseInt(obj.getHouseNumber());
				int firstHouse = houseNumber + first;
				int secondHouse = houseNumber + second;

				if (firstHouse > 12)
					firstHouse -= 12;
				if (secondHouse > 12) {
					secondHouse -= 12;
				}
				if (logger.isDebugEnabled())
					logger.debug("My New House Numbers FirstHouse " + firstHouse + " SecondHouse " + secondHouse);
				String[] houseArray = { Integer.toString(firstHouse), Integer.toString(secondHouse) };
				for (int loop = 0; loop < houseArray.length; loop++) {
					if (!birthHouseHashTable.containsKey(houseArray[loop]))
						continue;
					if (logger.isDebugEnabled())
						logger.debug("My hash Table contain house Number for " + houseArray[loop]);
					KundliHouseBean beanObj = (KundliHouseBean) birthHouseHashTable.get(houseArray[loop]);
					if (logger.isDebugEnabled())
						logger.debug("My planets name ||||||||||||||||||||||||" + beanObj.getPlanetName());

					if (StringUtils.isBlank(beanObj.getPlanetName())) {
						continue;
					}
					String[] str = beanObj.getPlanetName().split(" ");
					if (logger.isDebugEnabled())
						logger.debug("Planet Name Contains house number " + houseArray[loop] + "Planet Name"
								+ beanObj.getPlanetName() + " Planet Array " + Arrays.toString(str));
					for (String planetNm : str) {
						if (!planetName.equalsIgnoreCase(planetNm.trim()))
							continue;
						if (logger.isDebugEnabled())
							logger.debug("Effect of " + planetName + " for planet " + planetEffect);
						return true;
					}

				}

			} else {
				retVal = false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return retVal;
	}

	public String getConjPlanetList(String rahuAndKetu, LinkedHashMap planetDetailHashTable) {
		logger.info("Inside getConjPlanetList method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function getConjPlanetList for getting conjection planet list of || " + rahuAndKetu);
		}
		StringBuffer planetList = new StringBuffer();
		try {
			int plusDegree = 0;
			int minusDegree = 0;
			boolean isForword = false;
			boolean isBackword = false;
			boolean insertStatus = false;
			if (planetDetailHashTable.containsKey(rahuAndKetu)) {
				if (logger.isDebugEnabled())
					logger.debug("My conjHashTable contain key for planet || " + rahuAndKetu);

				PlanetDetailBean rashiBean = (PlanetDetailBean) planetDetailHashTable.get(rahuAndKetu);
				if (logger.isDebugEnabled())
					logger.debug("Degree Value of planet [" + rahuAndKetu + "] [" + rashiBean.getDegree() + "]");

				plusDegree = Integer.parseInt(rashiBean.getDegree().substring(0, 2).trim()) + 4;
				minusDegree = Integer.parseInt(rashiBean.getDegree().substring(0, 2).trim()) - 4;
				if (logger.isDebugEnabled())
					logger.debug(" Before PlusDegree [" + plusDegree + "] MinusDegree [" + minusDegree
							+ "] Rashi Name [ " + rashiBean.getSignNumber());
				if (plusDegree > 30) {
					isForword = true;
					plusDegree -= 30;
				}
				if (minusDegree < 0) {
					isBackword = true;
					minusDegree += 30;
				}
				if (logger.isDebugEnabled())
					logger.debug("After PlusDegree [" + plusDegree + "] MinusDegree [" + minusDegree
							+ "] Forward Flag [" + isForword + "] Backword Flag [" + isBackword + "]");

				Iterator it = planetDetailHashTable.values().iterator();
				while (it.hasNext()) {
					insertStatus = false;

					PlanetDetailBean obj = (PlanetDetailBean) it.next();

					if (obj.getSignName().equals(rashiBean.getSignName())) {
						if (logger.isDebugEnabled())
							logger.debug("Sign conjuction aries at planetName " + obj.getPlanetName()
									+ " and sign name " + obj.getSignName() + "Both planet name || "
									+ obj.getPlanetName() + " and " + rashiBean.getPlanetName());
						if (isForword) {
							if (minusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim()))
								insertStatus = true;
							if (logger.isDebugEnabled())
								logger.debug("Is Forword flag is true ");
						}
						if (isBackword) {
							if (plusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim()))
								insertStatus = true;
							if (logger.isDebugEnabled())
								logger.debug("Is Backword flag is true ");
						}
						if ((!isForword) && (!isBackword)) {
							if ((plusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim()))
									|| (minusDegree <= Integer.parseInt(obj.getDegree().substring(0, 2).trim()))) {
								insertStatus = true;
							}
						}

					}

					if (!insertStatus)
						continue;
					planetList.append(obj.getPlanetName());
					planetList.append("_");
				}
				if (logger.isDebugEnabled())
					logger.debug("Data Insert into planetList " + planetList.toString());

				String forBackSignName = "";
				int nextPrevSignNumber = -1;
				Rashi myrashi = Rashi.toRashi(rashiBean.getSignName());
				if (logger.isDebugEnabled())
					logger.debug("My Previous Rashi Name || " + rashiBean.getSignName());
				insertStatus = false;
				if (isForword) {
					nextPrevSignNumber = myrashi.ordinal() + 1;

					if (nextPrevSignNumber == 12)
						nextPrevSignNumber = 0;
				}
				if (isBackword) {
					nextPrevSignNumber = myrashi.ordinal() - 1;
					if (nextPrevSignNumber == -1) {
						nextPrevSignNumber = 0;
					}
				}
				if (nextPrevSignNumber >= 0) {
					forBackSignName = myrashi.fromOrdinal(nextPrevSignNumber).toString();
					if (logger.isDebugEnabled())
						logger.debug("My PreviousORNext  Rashi Name || " + forBackSignName);
					insertStatus = true;
				}

				if (insertStatus) {
					if (logger.isDebugEnabled())
						logger.debug("Checking for forword/backword sign");
					Iterator itr = planetDetailHashTable.values().iterator();
					while (itr.hasNext()) {
						insertStatus = false;

						PlanetDetailBean objBen = (PlanetDetailBean) itr.next();

						if (objBen.getSignName().equals(rashiBean.getSignName())) {
							if (logger.isDebugEnabled())
								logger.debug("Sign conjuction aries at planetName " + objBen.getPlanetName()
										+ " and sign name " + objBen.getSignName());
							if (isForword) {
								if (minusDegree <= Integer.parseInt(objBen.getDegree().substring(0, 2).trim()))
									insertStatus = true;
								if (logger.isDebugEnabled())
									logger.debug("Is Forword flag is true ");
							}
							if (isBackword) {
								if (plusDegree <= Integer.parseInt(objBen.getDegree().substring(0, 2).trim()))
									insertStatus = true;
								if (logger.isDebugEnabled())
									logger.debug("Is Backword flag is true ");
							}
						}

						if (!insertStatus)
							continue;
						planetList.append(objBen.getPlanetName());
						planetList.append("_");
					}

				} else {
					if (logger.isDebugEnabled())
						logger.debug(
								"There is no any foword/backword planet for this sign " + rashiBean.getSignNumber());
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return planetList.toString();
	}

	public String getDashaPlanetName() {
		logger.info("Inside getDashaPlanetName method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function getDashaPlanetName() find dasha planet ");
			logger.debug("User's TOB " + astroBean.getTOB() + " " + astroBean.getDOB());
		}

		Iterator it = this.antardashaDetailHashTable.values().iterator();
		boolean isFutureKundli = false;
		java.util.Date currentDate = new java.util.Date();
		logger.info("currentDate=  " + currentDate);
		try {

			logger.info("userDOB before parse==  " + astroBean.getDOB1());
			logger.info("User's TOB " + astroBean.getTOB());
			// java.util.Date userDOB=new
			// SimpleDateFormat("dd-MM-yyyy").parse(astroBean.getDOB1());
			java.util.Date userDOB = new SimpleDateFormat("yyyy-MM-dd").parse(astroBean.getDOB1());
			logger.info("userDOB=  " + userDOB);
			logger.info("compareTo=  " + userDOB.compareTo(currentDate));
			if (userDOB.compareTo(currentDate) > 0)
				isFutureKundli = true;
		
		logger.info("########### IS FUTURE KUNDLI " + isFutureKundli);
		if (!isFutureKundli) {
			while (it.hasNext()) {
				MahaDashaBean obj = null;
				Vector data = (Vector) it.next();
				if (logger.isDebugEnabled())
					logger.debug("## entering vector");
				for (int loop = 0; loop < data.size(); loop++) {
					obj = (MahaDashaBean) data.get(loop);
					if (!StringUtils.isBlank(obj.getStartTime().trim())) {
						break;
					} else {
					}

				}

				if (!checkPeriod(obj.getStartTime().trim(), ((MahaDashaBean) data.get(data.size() - 1)).getEndTime())) {
					// logger.info("continue ..........");
					continue;
				}
				if (logger.isDebugEnabled()) {
					logger.debug("CheckPeriod is true  Dasha Planet Name  [ " + obj.getParent() + " ] Start Time  [ "
							+ obj.getStartTime() + " ] End Time [ " + obj.getEndTime() + " ]");
				}
				return obj.getParent();
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug(
						"########### Case when no matching date exist due to FUTURE KUNDLI so considering fiest most entry from ANTARDASHA");
			}
			// for(java.util.Map.Entry<String, Vector<String>> entry :
			// antardashaDetailHashTable.entrySet())
			String currentPlanet = null;
			for (java.util.Map.Entry entry : antardashaDetailHashTable.entrySet()) {
				currentPlanet = (String) entry.getKey();
				return currentPlanet;
				// Do things with the list
			}
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static boolean checkPeriod(String stDate, String enDate) {
		logger.info("Inside checkPeriod method of GenerateDashaKundli action Class.");
		try {
			SimpleDateFormat xmlFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date startDate = xmlFormat.parse(stDate);
			Date endDate = xmlFormat.parse(enDate);
			Date currentDate = new Date();
			currentDate = xmlFormat.parse(xmlFormat.format(currentDate));

			Calendar startCal = Calendar.getInstance();
			startCal.setTime(startDate);

			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);

			Calendar cCal = Calendar.getInstance();
			cCal.setTime(currentDate);

			return (startCal.getTimeInMillis() <= cCal.getTimeInMillis())
					&& (cCal.getTimeInMillis() <= endCal.getTimeInMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getMDLValue(String planetName, LinkedHashMap antardashaDetailHashTable) {
		logger.info("Inside getMDLValue method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function getMDLValue get MDLValue from  antardashaDetailHashTable fot planet [ "
					+ planetName + " ] Root Parent [ " + this.dashaPlanetName + " ]");
		}
		try {
			if (antardashaDetailHashTable.containsKey(this.dashaPlanetName)) {
				Vector data = (Vector) antardashaDetailHashTable.get(this.dashaPlanetName);
				Iterator itr = data.iterator();
				while (itr.hasNext()) {
					MahaDashaBean dashaBean = (MahaDashaBean) itr.next();
					if (dashaBean.getPlanetName().equalsIgnoreCase(planetName)) {
						return dashaBean.getEndTime();
					}
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Inside Function getMDLValue no any hash key [ " + this.dashaPlanetName
							+ " ]  found in antardashaDetailHashTable ");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public void getVacantHouseList(Kundli cuspKundli, LinkedHashMap vacantHouseHashTable,
			LinkedHashMap planetDetailHashTable) {
		logger.info("Inside getVacantHouseList method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function getVacantHouseList For Getting Vacant HashTable " + cuspKundli.toString());
		}
		for (int planet = 0; planet < this.planetName.length; planet++) {
			vacantHouseHashTable.put(this.planetName[planet], new StringBuffer());
		}

		try {
			for (int houseNo = 1; houseNo <= 12; houseNo++) {
				if (logger.isDebugEnabled())
					logger.debug("******************************Check For House No [ " + houseNo
							+ " ] Value Of House [ " + cuspKundli.getHouseData(houseNo) + " ] ****************");

				String[] houseDetail = cuspKundli.getHouseData(houseNo).split("_");

				if (houseDetail.length != 1)
					continue;
				if (logger.isDebugEnabled())
					logger.debug("My House Number [ " + houseNo + " ] is Vacant and Sign Number [ " + houseDetail[0]
							+ " ] Planet Name For This Sign Number"
							+ Planet.fromOrdinal(Integer.parseInt(houseDetail[0])) + " ] ");

				if (!StringUtils.isBlank(Planet.fromOrdinal(Integer.parseInt(houseDetail[0])).toString())) {
					insertVacantHouseEntry(houseNo, Planet.fromOrdinal(Integer.parseInt(houseDetail[0])).toString(),
							vacantHouseHashTable, planetDetailHashTable);
				}

			}

			if (logger.isDebugEnabled())
				logger.debug(" VacantHouse HashTable  Values  ||  " + vacantHouseHashTable.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void insertVacantHouseEntry(int houseNumber, String planetName, LinkedHashMap vacantHouseHashTable,
			LinkedHashMap planetDetailHashTable) {
		logger.info("Inside insertVacantHouseEntry method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function insertVacantHouseEntry For Search Planet Name " + planetName);
		}
		boolean checkForSL = true;
		boolean checkForSS = true;
		StringBuffer vacantHouseList = new StringBuffer();
		try {
			Iterator nlIterator = planetDetailHashTable.values().iterator();

			// logger.info("Check For NL in Planets List");
			while (nlIterator.hasNext()) {
				PlanetDetailBean planetBean = (PlanetDetailBean) nlIterator.next();

				if ((planetBean.getPlanetName().equalsIgnoreCase("Uranus"))
						|| (planetBean.getPlanetName().equalsIgnoreCase("Neptune"))
						|| (planetBean.getPlanetName().equalsIgnoreCase("Pluto"))
						|| (planetBean.getPlanetName().equalsIgnoreCase("Lagna"))) {
					if (logger.isDebugEnabled())
						logger.debug("NL :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  "
								+ planetBean.getPlanetName() + " ]");
				} else {
					if (!planetBean.getNL().equalsIgnoreCase(planetName))
						continue;
					checkForSL = false;
					if (logger.isDebugEnabled())
						logger.debug(
								" NL [ " + planetBean.getNL() + " Planet Name [ " + planetBean.getPlanetName() + " ]");
					break;
				}

			}

			if (checkForSL) {
				if (logger.isDebugEnabled())
					logger.debug("Inside Check For SL in Planet List [Default Entry For Planet ] [ " + planetName
							+ " ] House No. [ " + houseNumber + " ]");
				vacantHouseList.append(planetName);
				vacantHouseList.append("-");
				vacantHouseList.append(Integer.toString(houseNumber));

				Iterator slIterator = planetDetailHashTable.values().iterator();

				if (logger.isDebugEnabled())
					logger.debug("Check For SL in Planets List");
				while (slIterator.hasNext()) {
					PlanetDetailBean planetBean = (PlanetDetailBean) slIterator.next();

					if (logger.isDebugEnabled())
						logger.debug(" SL [ " + planetBean.getSL() + " ]  Planet Name [ " + planetBean.getPlanetName()
								+ " ]");

					if ((planetBean.getPlanetName().equalsIgnoreCase("Uranus"))
							|| (planetBean.getPlanetName().equalsIgnoreCase("Neptune"))
							|| (planetBean.getPlanetName().equalsIgnoreCase("Pluto"))
							|| (planetBean.getPlanetName().equalsIgnoreCase("Lagna"))) {
						if (logger.isDebugEnabled())
							logger.debug(
									"SL :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  "
											+ planetBean.getPlanetName() + " ]");
					} else {
						if (!planetBean.getSL().equalsIgnoreCase(planetName))
							continue;
						checkForSS = false;
						vacantHouseList.append("_");
						vacantHouseList.append(planetBean.getPlanetName());
						vacantHouseList.append("-");
						vacantHouseList.append(Integer.toString(houseNumber));
					}

				}

				if (logger.isDebugEnabled())
					logger.debug("After SL iteration Vacant Planet Lists || " + vacantHouseList.toString());
			}

			if ((checkForSS) && (checkForSL)) {
				if (logger.isDebugEnabled())
					logger.debug("Inside Check For SS in Planet List  [ " + planetName + " ] House No. [ " + houseNumber
							+ " ]");

				Iterator ssIterator = planetDetailHashTable.values().iterator();

				if (logger.isDebugEnabled())
					logger.debug("Check For SL in Planets List");
				while (ssIterator.hasNext()) {
					PlanetDetailBean planetBean = (PlanetDetailBean) ssIterator.next();

					if ((planetBean.getPlanetName().equalsIgnoreCase("Uranus"))
							|| (planetBean.getPlanetName().equalsIgnoreCase("Neptune"))
							|| (planetBean.getPlanetName().equalsIgnoreCase("Pluto"))
							|| (planetBean.getPlanetName().equalsIgnoreCase("Lagna"))) {
						if (logger.isDebugEnabled())
							logger.debug(
									"SS :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  "
											+ planetBean.getPlanetName() + " ]");
					} else {
						if (!planetBean.getSS().equalsIgnoreCase(planetName)) {
							continue;
						}

						vacantHouseList.append("_");
						vacantHouseList.append(planetBean.getPlanetName());
						vacantHouseList.append("-");
						vacantHouseList.append(Integer.toString(houseNumber));
					}

				}

				if (logger.isDebugEnabled())
					logger.debug("After SS iteration Vacant Planet Lists || " + vacantHouseList.toString());
			}

			if (!StringUtils.isBlank(vacantHouseList.toString())) {
				if (logger.isDebugEnabled())
					logger.debug("Put String Values in HashTable || " + vacantHouseList.toString());
				String[] outer = vacantHouseList.toString().split("_");
				for (String str : outer) {
					if (logger.isDebugEnabled())
						logger.debug("Check For String Iteration ||||||| " + str);
					String[] inner = str.toString().split("-");

					if (!vacantHouseHashTable.containsKey(inner[0]))
						continue;
					if (logger.isDebugEnabled())
						logger.debug("Insert Data For Planet " + inner[0]);
					StringBuffer data = (StringBuffer) vacantHouseHashTable.get(inner[0]);
					data.append(inner[1]);
					data.append(",");
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getVacantHouse(String planetName) {
		logger.info("Inside getVacantHouse method of GenerateDashaKundli action Class.");
		String vacanetHouse = null;
		if (this.vacantHouseHashTable.containsKey(planetName)) {
			vacanetHouse = ((StringBuffer) this.vacantHouseHashTable.get(planetName)).toString();
			if (!StringUtils.isBlank(vacanetHouse)) {
				if (vacanetHouse.endsWith(",")) {
					return vacanetHouse.substring(0, vacanetHouse.length() - 1);
				}

			} else {
				return vacanetHouse;
			}
		}

		return null;
	}

	public static void rotateArrayByDashaPlanet(String[] sourse, String[] destinetion, String indexValue) {
		logger.info("Inside rotateArrayByDashaPlanet method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function  rotateArrayByDashaPlanet rotaion of array with dasha planet name || "
					+ indexValue);
		}
		int location = -1;
		
		try{
		for (int loop = 0; loop < sourse.length; loop++) {
			if (!indexValue.equalsIgnoreCase(sourse[loop]))
				continue;
			location = loop;
			if (logger.isDebugEnabled())
				logger.debug("Source Index is [ " + loop + " ] Index Value [ " + sourse[loop] + " ]");
			break;
		}

		if (location > -1) {
			for (int i = 0; i < destinetion.length; i++) {
				destinetion[i] = sourse[location];
				location++;
				if (location == sourse.length)
					location = 0;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Source Value [ " + Arrays.toString(sourse) + " ]");
			logger.debug("Destinetion Value [ " + Arrays.toString(destinetion) + " ]");
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getDashaTabledata(LinkedHashMap antardashaDetailHashTable) {
		logger.info("Inside getDashaTabledata method of GenerateDashaKundli action Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function getDashaTabledata()  for getting dasha Tabular Data ");
		}
		SimpleDateFormat yearDate = new SimpleDateFormat("yyyy");
		StringBuffer dashaTabledata = new StringBuffer();

		String currentYear = yearDate.format(new Date());
		String currentdashaYear = null;
		// logger.info("My Current Year " + currentYear);
		try {
			if (antardashaDetailHashTable.containsKey(this.dashaPlanetName)) {
				Vector data = (Vector) antardashaDetailHashTable.get(this.dashaPlanetName);

				MahaDashaBean obj = (MahaDashaBean) data.get(data.size() - 1);

				// logger.info("My MDL VALUE ");

				dashaTabledata.append(obj.getParent() + "#" + obj.getEndTime());

				Iterator itr = data.iterator();
				while (itr.hasNext()) {
					MahaDashaBean dashaBean = (MahaDashaBean) itr.next();
					if (!dashaBean.getPlanetName().equalsIgnoreCase(this.dashaPlanetName))
						continue;
					if (!StringUtils.isBlank(dashaBean.getEndTime())) {
						currentdashaYear = dashaBean.getEndTime().substring(dashaBean.getEndTime().length() - 4,
								dashaBean.getEndTime().length());

						break;
					}
					if (logger.isDebugEnabled())
						logger.debug("Error inside function getDashaTabledata() current dasha end date is null");
					return dashaTabledata.toString();
				}

				Vector currentVector = (Vector) antardashaDetailHashTable.get(this.dashaPlanetName);
				int counter = 0;
				for (Iterator irt = data.iterator(); irt.hasNext();) {
					MahaDashaBean dashaBean = (MahaDashaBean) irt.next();
					String t = dashaBean.getEndTime().split("  ")[0];

					String yeardata = t.substring(t.length() - 4, t.length());

					if (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) <= 0)
						continue;
					if (logger.isDebugEnabled())
						logger.debug("Inside the difference is positive Planet Name [ " + dashaBean.getPlanetName()
								+ " ]and date [ " + dashaBean.getEndTime() + " ]");

					if ((Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 1)
							&& (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 2)
							&& (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 3)
							&& (Integer.parseInt(yeardata) - Integer.parseInt(currentYear) != 4)) {
						continue;
					}
					if (logger.isDebugEnabled())
						logger.debug("Insert data into StringBuffer  Planet Name [ " + dashaBean.getPlanetName()
								+ " ]and date [ " + dashaBean.getEndTime() + " ]");
					dashaTabledata.append("_" + dashaBean.getPlanetName() + "#" + dashaBean.getEndTime());
					if (counter == 3)
						break;
					counter++;
				}

			} else {
				if (logger.isDebugEnabled())
					logger.debug("Inside Function getMDLValue no any hash key [ " + this.dashaPlanetName
							+ " ]  found in antardashaDetailHashTable ");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		}
		if (logger.isDebugEnabled())
			logger.debug("My String value Returnd from function ===== getDashaTabledata ====== "
					+ dashaTabledata.toString());

		return dashaTabledata.toString();
	}
}

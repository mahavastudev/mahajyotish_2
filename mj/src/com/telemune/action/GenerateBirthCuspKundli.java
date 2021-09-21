package com.telemune.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.telemune.model.AstroBean;
import com.telemune.model.HouseDetailBean;
import com.telemune.model.KundliHouseBean;
import com.telemune.model.MahaDashaBean;
import com.telemune.model.PlanetDetailBean;
import com.telemune.model.Kundli;

public class GenerateBirthCuspKundli {
	static Logger logger = Logger.getLogger(GenerateBirthCuspKundli.class);

	private String personName;
	private static final double AVERAGE_MILLIS_PER_MONTH = 2629728000.0D;
	DocumentBuilderFactory factory = null;
	DocumentBuilder builder;
	Document doc = null;
	XPathExpression expr = null;

	String[] planetName = { "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury" };

	String[] rahuAndKetu = { "Rahu", "Ketu" };
	String[] planetEffect = { "Jupiter", "Saturn", "Mars" };

	Hashtable<String, KundliHouseBean> cuspPlanetHashTable = new Hashtable();
	Hashtable<String, KundliHouseBean> cuspHouseHashTable = new Hashtable();

	Hashtable<String, KundliHouseBean> birthPlanetHashTable = new Hashtable();
	Hashtable<String, KundliHouseBean> birthHouseHashTable = new Hashtable();

	LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, HouseDetailBean> houseDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable = new LinkedHashMap();

	LinkedHashMap<String, StringBuffer> vacantHouseHashTable = new LinkedHashMap();

	public GenerateBirthCuspKundli()
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
	}

	public GenerateBirthCuspKundli(String url)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.info("Inside Constructor of GenerateBirthCuspKundli");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Constructor call of  GenerateBirthCuspKundli class ");
		}
		this.personName = url.substring(url.lastIndexOf("=") + 1, url.indexOf(","));
		try {
			String query = "";
			this.factory = DocumentBuilderFactory.newInstance();
			this.factory.setNamespaceAware(true);
			this.builder = this.factory.newDocumentBuilder();

			this.doc = this.builder.parse(url);

			DOMSource domSource = new DOMSource(doc);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);
			logger.info("HEREERERE>> " + sw.toString().replaceAll("[\t\n\r]", ""));

			query = "BIRTHCHART";

			getCuspBirthHashTable(query, this.birthPlanetHashTable, this.birthHouseHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.birthPlanetHashTable " + this.birthPlanetHashTable);
				logger.debug("#### this.birthHouseHashTable " + this.birthHouseHashTable);
			}

			query = "CUSPCHART";

			getCuspBirthHashTable(query, this.cuspPlanetHashTable, this.cuspHouseHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.cuspPlanetHashTable " + this.cuspPlanetHashTable);
				logger.debug("#### this.cuspHouseHashTable  " + this.cuspHouseHashTable);
				logger.debug("Successfully Load BIRTHCHART/CUSPCHART cache ");
			}
			query = "HOUSES";

			getPlanetsHousesHashTable(query, this.houseDetailHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.houseDetailHashTable " + this.houseDetailHashTable);
			}

			query = "PLANETS";

			getPlanetsHousesHashTable(query, this.planetDetailHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.planetDetailHashTable " + this.planetDetailHashTable);
				logger.debug("Successfully Load HOUSES/PLANETS cache ");
			}
			query = "ANTARDASHA";
			getAntardashaSookshmadasha(query, this.antardashaDetailHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("####  this.antardashaDetailHashTable " + this.antardashaDetailHashTable);
			}

			query = "SOOKSHMADASHA";

			getAntardashaSookshmadasha(query, this.sookshmadashaDetailHashTable);

			if (logger.isDebugEnabled()) {
				logger.debug("#### this.sookshmadashaDetailHashTable " + this.sookshmadashaDetailHashTable);
				logger.debug("Successfully Load ANTARDASHA/SOOKSHMADASHA cache ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String searchPlanetLocation(String planetName, Hashtable cuspPlanetHashTable, Hashtable cuspHouseHashTable) {
		logger.info("Inside searchPlanetLocation method of GenerateBirthCuspKundli Class.");
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
					signStr = new String[2]; // String[] removed by
												// prianka
					signStr[0] = Integer.toString(getPlanetSignNumber.getFirstRashi());
					signStr[1] = Integer.toString(getPlanetSignNumber.getSecondRashi());
				} else {
					signStr = new String[1];
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

	public void getCuspBirthHashTable(String queryFetch, Hashtable planetHashTable, Hashtable houseHashTable)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		logger.info("Inside getCuspBirthHashTable method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function getCuspBirthHashTable Fetch data for  " + queryFetch);
		}
		try{
		String query = "";
		if (queryFetch.equalsIgnoreCase("BIRTHCHART"))
			query = "/ASTROCHART/BIRTHCHART/HOUSE//@NAME";
		else if (queryFetch.equalsIgnoreCase("CUSPCHART"))
			query = "/ASTROCHART/CUSPCHART/HOUSE//@NAME";
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Query Is not Metch from keywords  BIRTHCHART/CUSPCHART    query ||  " + queryFetch);
			}

		}
		XPathFactory xFactory = XPathFactory.newInstance();
		if (logger.isDebugEnabled())
			logger.debug("Query For Fetch CuspORBirth data  " + query);

		XPath xpath = xFactory.newXPath();

		this.expr = xpath.compile(query);

		Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);

		NodeList nodes = (NodeList) result;
		logger.info("node length>> " + nodes.getLength());
		for (int i = 0; i < nodes.getLength();) {
			Attr houseAttr = (Attr) nodes.item(i);
			Attr planetAttr = (Attr) nodes.item(i + 1);
			logger.info("planetAttr>> " + planetAttr);

			Attr signAttr = (Attr) nodes.item(i + 2);

			if (!StringUtils.isBlank(planetAttr.getValue())) {
				String[] planetName = planetAttr.getValue().split(" ");
				for (String planet : planetName) {
					KundliHouseBean obj = new KundliHouseBean();
					obj.setPlanetName(planet.trim());
					obj.setHouseNumber(houseAttr.getValue().trim());
					obj.setSignNumber(signAttr.getValue().trim());
					planetHashTable.put(planet.trim(), obj);
				}

			}

			KundliHouseBean beanObj = new KundliHouseBean();
			beanObj.setHouseNumber(houseAttr.getValue().trim());
			beanObj.setSignNumber(signAttr.getValue().trim());

			if (!StringUtils.isBlank(planetAttr.getValue())) {
				beanObj.setPlanetName(planetAttr.getValue().trim());
			}

			houseHashTable.put(houseAttr.getValue().trim(), beanObj);

			i += 3;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("planetHashTable " + queryFetch + " " + planetHashTable);
			logger.debug("houseHashTable  " + queryFetch + " " + houseHashTable);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getNLAndSL(String searchAttribute, String planetName, LinkedHashMap planetDashaHashTable) {
		logger.info("Inside getNLAndSL method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug("Inside function  getNLAndSL for Search Attribute [ " + searchAttribute + " ] of planet [ "
					+ planetName + " ]");
		try {
			if (!searchAttribute.equalsIgnoreCase("MDL")) {
				if (planetDashaHashTable.containsKey(planetName)) {
					PlanetDetailBean planetBean = (PlanetDetailBean) planetDashaHashTable.get(planetName);

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

			} else {
				if (planetDashaHashTable.containsKey(planetName)) {
					Vector data = (Vector) planetDashaHashTable.get(planetName);
					MahaDashaBean dashBean = (MahaDashaBean) data.get(data.size() - 1);
					if (logger.isDebugEnabled())
						logger.debug("My Last Vector Index  Parent Planet Name [ " + dashBean.getParent()
								+ " End Data  [ " + dashBean.getEndTime() + " ]");

					return dashBean.getEndTime();
				}
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

	public void getPlanetsHousesHashTable(String queryFetch, LinkedHashMap planetHousesHashTable)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.info("Inside getPlanetsHousesHashTable method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug("Inside Function getPlanetsHousesHashTable Fetch data for  " + queryFetch);

		try{
		String query = "";
		if (queryFetch.equalsIgnoreCase("PLANETS"))
			query = "/ASTROCHART/PLANETS//@* ";
		else if (queryFetch.equalsIgnoreCase("HOUSES"))
			query = "/ASTROCHART/HOUSES//@* ";
		else {
			if (logger.isDebugEnabled())
				logger.debug("Query Is not Metch  query " + queryFetch);
		}

		XPathFactory xFactory = XPathFactory.newInstance();
		if (logger.isDebugEnabled())
			logger.debug("Query For Fetch PlanetsHouses data  " + query);

		XPath xpath = xFactory.newXPath();

		this.expr = xpath.compile(query);

		Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);

		NodeList nodes = (NodeList) result;

		logger.info("node length in getPlanetsHousesHashTable>> " + nodes.getLength());
		for (int i = 0; i < nodes.getLength();) {
			if (queryFetch.equalsIgnoreCase("PLANETS")) {
				Attr degreeAttr = (Attr) nodes.item(i);
				Attr nakshatraAttr = (Attr) nodes.item(i + 1);
				Attr nameAttr = (Attr) nodes.item(i + 2);
				Attr NLAttr = (Attr) nodes.item(i + 3);
				Attr padamAttr = (Attr) nodes.item(i + 4);
				Attr RCAttr = (Attr) nodes.item(i + 5);
				Attr RLAttr = (Attr) nodes.item(i + 6);
				Attr signAttr = (Attr) nodes.item(i + 7);
				Attr SLAttr = (Attr) nodes.item(i + 8);
				Attr SSAttr = (Attr) nodes.item(i + 9);

				if ((!nameAttr.getValue().trim().equalsIgnoreCase("Uranus"))
						&& (!nameAttr.getValue().trim().equalsIgnoreCase("Neptune"))
						&& (!nameAttr.getValue().trim().equalsIgnoreCase("Pluto"))) {
					PlanetDetailBean planetDetail = new PlanetDetailBean();
					planetDetail.setDegree(degreeAttr.getValue().trim());
					planetDetail.setNakshatra(nakshatraAttr.getValue().trim());
					planetDetail.setPlanetName(nameAttr.getValue().trim());
					planetDetail.setNL(NLAttr.getValue().trim());
					planetDetail.setPadam(padamAttr.getValue().trim());

					if (planetDetail.getPlanetName().equalsIgnoreCase("Rahu")
							|| planetDetail.getPlanetName().equalsIgnoreCase("Ketu")) {
						planetDetail.setRC("R");

					} else {
						planetDetail.setRC(RCAttr.getValue().trim());
					}
					planetDetail.setRL(RLAttr.getValue());
					planetDetail.setSignName(signAttr.getValue().trim());
					planetDetail.setSL(SLAttr.getValue());
					planetDetail.setSS(SSAttr.getValue());

					planetHousesHashTable.put(nameAttr.getValue().trim(), planetDetail);
				}

				i += 10;
			} else {
				if (!queryFetch.equalsIgnoreCase("HOUSES")) {
					continue;
				}

				Attr degreeAttr = (Attr) nodes.item(i);
				Attr nakshatraAttr = (Attr) nodes.item(i + 1);
				Attr nameAttr = (Attr) nodes.item(i + 2);
				Attr NLAttr = (Attr) nodes.item(i + 3);
				Attr padamAttr = (Attr) nodes.item(i + 4);
				Attr RLAttr = (Attr) nodes.item(i + 5);
				Attr signAttr = (Attr) nodes.item(i + 6);
				Attr SLAttr = (Attr) nodes.item(i + 7);
				Attr SSAttr = (Attr) nodes.item(i + 8);

				HouseDetailBean houseDetail = new HouseDetailBean();
				houseDetail.setDegree(degreeAttr.getValue().trim());
				houseDetail.setNakshatra(nakshatraAttr.getValue().trim());
				houseDetail.setPlanetName(nameAttr.getValue().trim());
				houseDetail.setNL(NLAttr.getValue().trim());
				houseDetail.setPadam(padamAttr.getValue().trim());
				houseDetail.setRL(RLAttr.getValue());
				houseDetail.setSignName(signAttr.getValue().trim());
				houseDetail.setSL(SLAttr.getValue());
				houseDetail.setSS(SSAttr.getValue());

				planetHousesHashTable.put(nameAttr.getValue().trim(), houseDetail);

				i += 9;
			}
		}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public AstroBean getNatalStrength() {
		logger.info("Inside getNatalStrength method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug("Inside Function getNatalStrength() ");
		AstroBean natalStrength = null;
		try {
			Kundli birthKundli = new Kundli("BIRTH");

			if (getBirthCuspChartData("BIRTH", birthKundli) < 0) {
				if (logger.isDebugEnabled())
					logger.debug("Error in getting BIRTH data inside function getBirthCuspChartData ");
				return null;
			}
			if (logger.isDebugEnabled())
				logger.debug("Birth Chart Data || " + birthKundli.toString());

			Kundli cuspKundli = new Kundli("CUSP");

			if (getBirthCuspChartData("CUSP", cuspKundli) < 0) {
				if (logger.isDebugEnabled())
					logger.debug("Error in getting CUSP data inside function getBirthCuspChartData ");
				return null;
			}
			if (logger.isDebugEnabled())
				logger.debug("Cusp Chart Data || " + cuspKundli.toString());

			getVacantHouseList(cuspKundli, this.vacantHouseHashTable, this.planetDetailHashTable);

			String[][] tabularData = new String[9][7];

			String rahuRashiLord = getNLAndSL("RL", "Rahu", this.planetDetailHashTable);
			String ketuRashiLord = getNLAndSL("RL", "Ketu", this.planetDetailHashTable);
			if (!StringUtils.isBlank(rahuRashiLord)) {

				logger.info("rahuRashiLord>> " + rahuRashiLord);
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
				String sourceValue = searchPlanetLocation(this.planetName[loop], this.cuspPlanetHashTable,
						this.cuspHouseHashTable);

				String resultNL = getNLAndSL("NL", this.planetName[loop], this.planetDetailHashTable);
				if (!StringUtils.isBlank(resultNL)) {
					resultNL = resultNL
							+ searchPlanetLocation(resultNL.trim(), this.cuspPlanetHashTable, this.cuspHouseHashTable);
				}

				String resultSL = getNLAndSL("SL", this.planetName[loop], this.planetDetailHashTable);
				if (!StringUtils.isBlank(resultSL)) {
					resultSL = resultSL
							+ searchPlanetLocation(resultSL.trim(), this.cuspPlanetHashTable, this.cuspHouseHashTable);
				}

				String resultNLOfSL = getNLAndSL("SL", this.planetName[loop], this.planetDetailHashTable);
				if (!StringUtils.isBlank(resultNLOfSL)) {
					resultNLOfSL = getNLAndSL("NL", resultNLOfSL.trim(), this.planetDetailHashTable);
				}
				if (!StringUtils.isBlank(resultNLOfSL)) {
					resultNLOfSL = resultNLOfSL + searchPlanetLocation(resultNLOfSL.trim(), this.cuspPlanetHashTable,
							this.cuspHouseHashTable);
				}
				String MDLValue = getNLAndSL("MDL", this.planetName[loop], this.antardashaDetailHashTable);
				String vacantHouse = getVacantHouse(this.planetName[loop]);

				tabularData[loop][0] = this.planetName[loop];
				tabularData[loop][3] = vacantHouse;
				tabularData[loop][6] = MDLValue;
				tabularData[loop][1] = sourceValue;
				tabularData[loop][2] = resultNL;
				tabularData[loop][4] = resultSL;
				tabularData[loop][5] = resultNLOfSL;

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

			natalStrength = new AstroBean();
			if (logger.isDebugEnabled())
				logger.debug("Before Getting Person detail of user ");
			getPersonalDetails(natalStrength);
			if (logger.isDebugEnabled())
				logger.debug(" Tabular Format Data inserted in 2D array ");
			for (int outer = 0; outer < tabularData.length; outer++) {
				for (int inner = 0; inner < tabularData[outer].length; inner++) {
					if (logger.isDebugEnabled())
						logger.debug("     " + tabularData[outer][inner]);
				}

			}

			natalStrength.setBirthKundli(birthKundli);
			natalStrength.setCuspKundli(cuspKundli);
			natalStrength.setNatalStrengthChart(tabularData);
			if (logger.isDebugEnabled())
				logger.debug("Generate  Rahu/Ketu planets effect ");

			getRahuKetuShadowEffect(natalStrength, this.birthPlanetHashTable, this.birthHouseHashTable);
			if (logger.isDebugEnabled())
				logger.debug("Store HashTable Cache into AstroBean Object");

			natalStrength.setBirthHouseHashTable(this.birthHouseHashTable);
			natalStrength.setBirthPlanetHashTable(this.birthPlanetHashTable);

			natalStrength.setCuspHouseHashTable(this.cuspHouseHashTable);
			natalStrength.setCuspPlanetHashTable(this.cuspPlanetHashTable);

			natalStrength.setPlanetDetailHashTable(this.planetDetailHashTable);

			natalStrength.setHouseDetailHashTable(this.houseDetailHashTable);

			natalStrength.setSookshmadashaDetailHashTable(this.sookshmadashaDetailHashTable);
			natalStrength.setAntardashaDetailHashTable(this.antardashaDetailHashTable);

			if (logger.isDebugEnabled()) {
				logger.debug(" Successfully Store HashTable Cache into AstroBean Object");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return natalStrength;
	}

	public int getBirthCuspChartData(String query, Kundli kundliData)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.info("Inside getBirthCuspChartData method of GenerateBirthCuspKundli Class.");
		try {
			XPathFactory xFactory = XPathFactory.newInstance();

			XPath xpath = xFactory.newXPath();

			if (query.equalsIgnoreCase("BIRTH"))
				this.expr = xpath.compile("/ASTROCHART/BIRTHCHART/HOUSE//@NAME");
			else if (query.equalsIgnoreCase("CUSP"))
				this.expr = xpath.compile("/ASTROCHART/CUSPCHART/HOUSE//@NAME");
			else {
				if (logger.isDebugEnabled())
					logger.debug("Please select Birth Or Cusp");
			}

			Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);

			NodeList nodes = (NodeList) result;
			int counter = 1;
			for (int i = 0; i < nodes.getLength();) {
				StringBuffer birthData = new StringBuffer();
				Attr planetAttr = (Attr) nodes.item(i + 1);

				Attr signAttr = (Attr) nodes.item(i + 2);
				birthData.append(signAttr.getValue());
				birthData.append("_");
				birthData.append(planetAttr.getValue());
				if (logger.isDebugEnabled())
					logger.debug("Planet Name and   Sign Value  || " + birthData.toString());

				kundliData.setHouseData(counter, birthData.toString());

				i += 3;
				counter++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
		return 0;
	}

	public String getRahuKetuEffectPlanet(String rahuAndKetu, Hashtable birthPlanetHashTable,
			Hashtable birthHouseHashTable) {
		logger.info("Inside getRahuKetuEffectPlanet method of GenerateBirthCuspKundli Class.");
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
						logger.debug("Planets name return from  getRahuKetuEffectPlanet function || "
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

	public void getRahuKetuShadowEffect(AstroBean natalStrength, Hashtable birthPlanetHashTable,
			Hashtable birthHouseHashTable) {
		logger.info("Inside getRahuKetuShadowEffect method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled()) {
			logger.info("Inside Function  getRahuKetuShadowEffect for generate rahu/ketu effect ");
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
						planetEffec = planetEffec + searchPlanetLocation(planetEffec.trim(), this.cuspPlanetHashTable,
								this.cuspHouseHashTable);
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
							logger.debug("There is aspect of [ " + str + " ] into [ " + this.planetEffect[loop] + " ]");
						ptr = ptr + searchPlanetLocation(ptr, this.cuspPlanetHashTable, this.cuspHouseHashTable);
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
						rahuKetuConj = rahuKetuConj
								+ searchPlanetLocation(conjPlanet, this.cuspPlanetHashTable, this.cuspHouseHashTable);

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
					rahuKetuSignLord = rahuKetuSignLord
							+ searchPlanetLocation(rahuKetuSignLord, this.cuspPlanetHashTable, this.cuspHouseHashTable);
					if (str.equalsIgnoreCase("Rahu"))
						natalStrength.setSignLoard(Planet.Rahu, rahuKetuSignLord);
					else if (str.equalsIgnoreCase("Ketu")) {
						natalStrength.setSignLoard(Planet.Ketu, rahuKetuSignLord);
					}
				}

				rahuKetuRashiLord = getNLAndSL("RL", str, this.planetDetailHashTable);
				if (!StringUtils.isBlank(rahuKetuRashiLord)) {
					rahuKetuRashiLord = rahuKetuRashiLord + searchPlanetLocation(rahuKetuRashiLord,
							this.cuspPlanetHashTable, this.cuspHouseHashTable);
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

	public boolean getJupiterSaturnMarsEffect(String planetEffect, String planetName, int first, int second,
			Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable) {
		logger.info("Inside getJupiterSaturnMarsEffect method of GenerateBirthCuspKundli Class.");
		boolean retVal = false;
		if (logger.isDebugEnabled())
			logger.debug(
					"Inside function getJupiterSaturnMarsEffect for getting rahu/ketu planet effect on  Ju/Sa/Ma || "
							+ planetEffect);
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
		logger.info("Inside getConjPlanetList method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug("Inside Function getConjPlanetList for getting conjection planet list of || " + rahuAndKetu);

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
					if (nextPrevSignNumber == -1)
						nextPrevSignNumber = 0;
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

	public void getPersonalDetails(AstroBean personInfo)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.info("Inside getPersonalDetails method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug("Inside function getPersonalDetails for getting client NAME/DOB/POB");
		try {
			XPathFactory xFactory = XPathFactory.newInstance();

			XPath xpath = xFactory.newXPath();

			for (int loop = 0; loop < 3; loop++) {
				if (loop == 0) {
					this.expr = xpath.compile("/ASTROCHART/NAME/text()");
					Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;

					personInfo.setName(this.personName.trim());
					if (logger.isDebugEnabled())
						logger.debug("Name of User || " + nodes.item(0).getNodeValue());
				} else if (loop == 1) {
					this.expr = xpath.compile("/ASTROCHART/DOB/@*");
					Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;

					Attr dobAttr = (Attr) nodes.item(0);
					personInfo.setDOB(dobAttr.getValue());
					if (logger.isDebugEnabled())
						logger.debug("DOB of User || " + dobAttr.getValue());
				} else if (loop == 2) {
					this.expr = xpath.compile("/ASTROCHART/POB/@*");
					Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;

					Attr cityAttr = (Attr) nodes.item(0);
					Attr countryAttr = (Attr) nodes.item(1);
					Attr stateAttr = (Attr) nodes.item(2);

					personInfo.setPOB(cityAttr.getValue());
					if (logger.isDebugEnabled())
						logger.debug(" City  [  " + cityAttr.getValue() + " ]  Country [ " + countryAttr.getValue()
								+ " ] State [ " + stateAttr.getValue() + " ]");
				} else {
					if (logger.isDebugEnabled())
						logger.debug(" There is no any case match ");
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void getAntardashaSookshmadasha(String fetchQuery, LinkedHashMap antardashaSookshmadasha)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		logger.info("Inside getAntardashaSookshmadasha method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug(
					"Inside function getAntardashaSookshmadasha for getting ANTARDASHA/SOOKSHMADASHA Fetch Query is || "
							+ fetchQuery);
		try {
			XPathFactory xFactory = XPathFactory.newInstance();

			XPath xpath = xFactory.newXPath();

			String query = "";
			String previousYear = "0";
			String currentYear = "";
			boolean first = true;
			String[] antardashaArray = getAntarDashaSequence();
			if (fetchQuery.equalsIgnoreCase("ANTARDASHA")) {
				for (int loop = 0; loop < antardashaArray.length; loop++) {

					logger.info("antardashaArray value >> loop>>" + antardashaArray[loop] + " >> " + loop);
					StringBuffer displayData = new StringBuffer();
					displayData.append(antardashaArray[loop]);
					query = "";
					query = "/ASTROCHART/DASHAS/DASHA[@NAME='" + antardashaArray[loop] + "']/ANTARDASHA/@*";
					this.expr = xpath.compile(query);
					Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);

					NodeList nodes = (NodeList) result;

					Vector data = new Vector();

					for (int i = 0; i < 27;) {
						MahaDashaBean obj = new MahaDashaBean();
						Attr endAttr = (Attr) nodes.item(i);
						Attr nameAttr = (Attr) nodes.item(i + 1);
						Attr startAttr = (Attr) nodes.item(i + 2);

						obj.setPlanetName(nameAttr.getValue().trim());
						obj.setStartTime(startAttr.getValue().trim());
						obj.setEndTime(endAttr.getValue().trim());
						obj.setParent(antardashaArray[loop].trim());
						data.add(obj);
						i += 3;
					}

					String[] startDate = new String[3];
					String[] startYear = new String[2];
					String[] endDate = new String[3];
					String[] endYear = new String[2];

					for (int l = 0; l < data.size(); l++) {
						if (StringUtils.isBlank(((MahaDashaBean) data.get(l)).getStartTime())) {
							continue;
						}
						currentYear = calculateDateDifference(((MahaDashaBean) data.get(l)).getStartTime(),
								((MahaDashaBean) data.get(data.size() - 1)).getEndTime());
						startDate = (((MahaDashaBean) data.get(l)).getStartTime()).split("-");
						startYear = startDate[2].split("  ");
						endDate = (((MahaDashaBean) data.get(data.size() - 1)).getEndTime()).split("-");
						endYear = endDate[2].split("  ");
						int diff = (Integer.parseInt(endYear[0])) - (Integer.parseInt(startYear[0]));
						logger.info("diff  " + diff);

						if (first) {

							first = false;
							displayData.append(" (");
							displayData.append(currentYear.substring(0, currentYear.lastIndexOf("y")));
							displayData.append("y)");
							displayData.append("_");
							displayData.append("From 0y to");
							displayData.append(currentYear);
							previousYear = currentYear;
							break;
						}

						int sumOfYear = Integer.parseInt(previousYear.substring(0, previousYear.lastIndexOf("y")))
								+ Integer.parseInt(currentYear.substring(0, currentYear.lastIndexOf("y")));

						displayData.append(" (");

						// displayData.append(currentYear.substring(0,currentYear.lastIndexOf("y")));
						displayData.append(Integer.toString(diff));

						displayData.append("y)");
						displayData.append("_");
						displayData.append("From ");
						displayData.append(previousYear);
						displayData.append(" to ");
						displayData.append(sumOfYear);
						displayData.append(currentYear.substring(currentYear.lastIndexOf("y")));
						previousYear = Integer.toString(sumOfYear)
								+ currentYear.substring(currentYear.lastIndexOf("y"));

						break;
					}

					((MahaDashaBean) data.get(0)).setYear(displayData.toString());
					if (logger.isDebugEnabled())
						logger.debug(" **** For Planet Name [ " + antardashaArray[loop] + " ] Display Year Period [ "
								+ displayData.toString() + " ] Previous Year [ " + previousYear + " ] Current Year [ "
								+ currentYear + " ] Year Store in Bean [ " + ((MahaDashaBean) data.get(0)).getYear()
								+ " ]");

					antardashaSookshmadasha.put(antardashaArray[loop].trim(), data);
				}

			} else if (fetchQuery.equalsIgnoreCase("SOOKSHMADASHA")) {
				boolean insertInHash = false;
				for (int dasha = 0; dasha < this.planetName.length; dasha++) {
					insertInHash = false;
					for (int antardasha = 0; antardasha < this.planetName.length; antardasha++) {
						insertInHash = false;
						for (int pratyantardasha = 0; pratyantardasha < this.planetName.length; pratyantardasha++) {
							insertInHash = false;

							query = "";
							query = "/ASTROCHART/DASHAS/DASHA[@NAME='" + this.planetName[dasha]
									+ "']/ANTARDASHA[@NAME='" + this.planetName[antardasha]
									+ "']/PRATYANTARDASHA[@NAME='" + this.planetName[pratyantardasha]
									+ "']/SOOKSHMADASHA/@*";
							this.expr = xpath.compile(query);
							Object result = this.expr.evaluate(this.doc, XPathConstants.NODESET);
							NodeList nodes = (NodeList) result;

							Vector data = new Vector();

							if (nodes.getLength() > 0) {
								insertInHash = true;
								if (logger.isDebugEnabled())
									logger.debug("My Insert Flag Is true  and executed query is ||  " + query);
							}

							for (int i = 0; i < nodes.getLength();) {
								MahaDashaBean obj = new MahaDashaBean();
								Attr endAttr = (Attr) nodes.item(i);
								Attr nameAttr = (Attr) nodes.item(i + 1);
								Attr startAttr = (Attr) nodes.item(i + 2);

								obj.setPlanetName(nameAttr.getValue().trim());
								obj.setStartTime(startAttr.getValue().trim());
								obj.setEndTime(endAttr.getValue().trim());
								obj.setParent(this.planetName[dasha].trim());
								obj.setChild(this.planetName[antardasha].trim());
								obj.setSubChild(this.planetName[pratyantardasha].trim());

								data.add(obj);

								i += 3;
							}

							if (!insertInHash)
								continue;
							if (logger.isDebugEnabled())
								logger.debug("Insert Into Hash Table Planets Keys || "
										+ ((MahaDashaBean) data.get(0)).getStartTime().trim());
							antardashaSookshmadasha.put(((MahaDashaBean) data.get(0)).getStartTime().trim(), data);
						}

					}

				}
				if (logger.isDebugEnabled())
					logger.debug("My hash table data  for SOOKSHMADASHA ||  " + antardashaSookshmadasha.toString());
			} else {
				if (logger.isDebugEnabled())
					logger.debug("There is No any correct query || " + fetchQuery);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void getVacantHouseList(Kundli cuspKundli, LinkedHashMap vacantHouseHashTable,
			LinkedHashMap planetDetailHashTable) {
		logger.info("Inside getVacantHouseList method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug("Inside Function getVacantHouseList For Getting Vacant HashTable ");

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
		logger.info("Inside insertVacantHouseEntry method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled())
			logger.debug("Inside Function insertVacantHouseEntry For Search Planet Name " + planetName);
		boolean checkForSL = true;
		boolean checkForSS = true;
		StringBuffer vacantHouseList = new StringBuffer();
		try {
			Iterator nlIterator = planetDetailHashTable.values().iterator();
			if (logger.isDebugEnabled())
				logger.debug("Check For NL in Planets List");
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
			// checkForSL=false;//override by rips at 19-08-2013 as per
			// requirement of mahavstu (Mr. nitin) .There is no need of
			// SL che cking
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
						// rips vacantHouseList.append("_");
						// rips
						// vacantHouseList.append(planetBean.getPlanetName());
						// rips vacantHouseList.append("-");
						// rips
						// vacantHouseList.append(Integer.toString(houseNumber));
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

						// rips vacantHouseList.append("_");
						// rips
						// vacantHouseList.append(planetBean.getPlanetName());
						// rips vacantHouseList.append("-");
						// rips
						// vacantHouseList.append(Integer.toString(houseNumber));
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
		logger.info("Inside getVacantHouse method of GenerateBirthCuspKundli Class.");
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

		return vacanetHouse;
	}

	public String[] getAntarDashaSequence() {
		logger.info("Inside getAntarDashaSequence method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside Function getAntarDashaSequence() function ");
		}
		String[] planetArr = new String[9];
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//DASHAS/DASHA/@*");
			Object result = expr.evaluate(this.doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;

			for (int i = 0; i < 9; i++) {
				Attr endAttr = (Attr) nodes.item(i);
				String str = endAttr.getValue();
				planetArr[i] = str;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return planetArr;
	}

	public String calculateDateDifference(String startdate, String endDate) {
		logger.info("Inside calculateDateDifference method of GenerateBirthCuspKundli Class.");
		if (logger.isDebugEnabled()) {
			logger.debug("Inside calculateDateDifference() function for get two date difference Start/End || "
					+ startdate + "/" + endDate);
		}
		StringBuffer myYear = new StringBuffer();
		try {
			SimpleDateFormat xmlFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date st = xmlFormat.parse(startdate);
			Date ed = xmlFormat.parse(endDate);

			double diffInMonth = (ed.getTime() - st.getTime()) / 2629728000.0D;

			myYear.append(Integer.toString((int) diffInMonth / 12));
			myYear.append("y");
			myYear.append(Integer.toString((int) diffInMonth % 12));
			myYear.append("m");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// logger.info(" My Year Value || " + myYear.toString());
		return myYear.toString();
	}

}

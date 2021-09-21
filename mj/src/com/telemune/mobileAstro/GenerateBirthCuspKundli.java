package com.telemune.mobileAstro;

/*      */
/*      */import java.io.IOException;
/*      */
import java.text.SimpleDateFormat;
/*      */
import java.util.Arrays;
/*      */
import java.util.Collection;
/*      */
import java.util.Date;
/*      */
import java.util.Hashtable;
/*      */
import java.util.Iterator;
/*      */
import java.util.LinkedHashMap;
/*      */
import java.util.Vector;
/*      */
import javax.xml.parsers.DocumentBuilder;
/*      */
import javax.xml.parsers.DocumentBuilderFactory;
/*      */
import javax.xml.parsers.ParserConfigurationException;


import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/*      */
import javax.xml.xpath.XPath;
/*      */
import javax.xml.xpath.XPathConstants;
/*      */
import javax.xml.xpath.XPathExpression;
/*      */
import javax.xml.xpath.XPathExpressionException;
/*      */
import javax.xml.xpath.XPathFactory;
/*      */
import org.apache.commons.lang.StringUtils;
/*      */
import org.apache.log4j.Logger;
/*      */
import org.w3c.dom.Attr;
/*      */
import org.w3c.dom.Document;
/*      */
import org.w3c.dom.Node;
/*      */
import org.w3c.dom.NodeList;
/*      */
import org.xml.sax.SAXException;

/*      */
/*      */public class GenerateBirthCuspKundli
/*      */{
	/*     */private String personName;
	/*     */private String kundliData="";
	/* 25 */static Logger logger = Logger
			.getLogger(GenerateBirthCuspKundli.class);
	/*      */private static final double AVERAGE_MILLIS_PER_MONTH = 2629728000.0D;
	/* 29 */DocumentBuilderFactory factory = null;
	/*      */DocumentBuilder builder;
	/* 31 */Document doc = null;
	/* 32 */XPathExpression expr = null;
	/*      */
	/* 34 */String[] planetName = { "Ketu", "Venus", "Sun", "Moon", "Mars",
			"Rahu", "Jupiter", "Saturn", "Mercury" };
	/*      */
	/* 36 */String[] rahuAndKetu = { "Rahu", "Ketu" };
	/* 37 */String[] planetEffect = { "Jupiter", "Saturn", "Mars" };
	/*      */
	/* 42 */Hashtable<String, KundliHouseBean> cuspPlanetHashTable = new Hashtable();
	/* 43 */Hashtable<String, KundliHouseBean> cuspHouseHashTable = new Hashtable();
	/*      */
	/* 47 */Hashtable<String, KundliHouseBean> birthPlanetHashTable = new Hashtable();
	/* 48 */Hashtable<String, KundliHouseBean> birthHouseHashTable = new Hashtable();
	/*      */
	/* 53 */LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable = new LinkedHashMap();
	/*      */
	/* 57 */LinkedHashMap<String, HouseDetailBean> houseDetailHashTable = new LinkedHashMap();
	/*      */
	/* 61 */LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable = new LinkedHashMap();
	/*      */
	/* 65 */LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable = new LinkedHashMap();
	/*      */
	/* 69 */LinkedHashMap<String, StringBuffer> vacantHouseHashTable = new LinkedHashMap();

	/*      */
	/*      */public GenerateBirthCuspKundli()
	/*      */throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	/*      */{
		/*      */}

	/*      */
	/*      */public GenerateBirthCuspKundli(String url)
	/*      */throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	/*      */{
		if (logger.isDebugEnabled()) {
			/* 80 */logger
					.debug("Inside Constructor call of  GenerateBirthCuspKundli class ");
		}
		/* 81 */this.personName = url.substring(url.lastIndexOf("=") + 1,
				url.indexOf(","));
		/*      */try
		/*      */{
			/* 84 */String query = "";
			/* 85 */this.factory = DocumentBuilderFactory.newInstance();
			/* 86 */this.factory.setNamespaceAware(true);
			/* 87 */this.builder = this.factory.newDocumentBuilder();
			/*      */
			/* 90 */this.doc = this.builder.parse(url);
			
				DOMSource domSource = new DOMSource(doc);
    				Transformer transformer = TransformerFactory.newInstance().newTransformer();
    				StringWriter sw = new StringWriter();
    				StreamResult sr = new StreamResult(sw);
    				transformer.transform(domSource, sr);
				this.kundliData=sw.toString().replaceAll("[\t\n\r]","");
    	//			logger.info("HEREERERE>> "+sw.toString().replaceAll("[\t\n\r]",""));

			/*      */
			/* 92 */query = "BIRTHCHART";
			/*      */
			/* 94 */getCuspBirthHashTable(query, this.birthPlanetHashTable,
					this.birthHouseHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.birthPlanetHashTable "
						+ this.birthPlanetHashTable);
				logger.debug("#### this.birthHouseHashTable "
						+ this.birthHouseHashTable);
			}
			/*      */
			/* 96 */query = "CUSPCHART";
			/*      */
			/* 98 */getCuspBirthHashTable(query, this.cuspPlanetHashTable,
					this.cuspHouseHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.cuspPlanetHashTable "
						+ this.cuspPlanetHashTable);
				logger.debug("#### this.cuspHouseHashTable  "
						+ this.cuspHouseHashTable);
				logger.debug("Successfully Load BIRTHCHART/CUSPCHART cache ");
			}
			/* 102 */query = "HOUSES";
			/*      */
			/* 104 */getPlanetsHousesHashTable(query, this.houseDetailHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.houseDetailHashTable "
						+ this.houseDetailHashTable);
			}
			/*      */
			/* 106 */query = "PLANETS";
			/*      */
			/* 108 */getPlanetsHousesHashTable(query,
					this.planetDetailHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.planetDetailHashTable "
						+ this.planetDetailHashTable);
				logger.debug("Successfully Load HOUSES/PLANETS cache ");
			}
			/* 113 */query = "ANTARDASHA";
			/* 114 */getAntardashaSookshmadasha(query,
					this.antardashaDetailHashTable);
			if (logger.isDebugEnabled()) {
				logger.debug("####  this.antardashaDetailHashTable "
						+ this.antardashaDetailHashTable);
			}
			/*      */
			/* 116 */query = "SOOKSHMADASHA";
			/*      */
			/* 118 */getAntardashaSookshmadasha(query,
					this.sookshmadashaDetailHashTable);
			/*      */
			if (logger.isDebugEnabled()) {
				logger.debug("#### this.sookshmadashaDetailHashTable "
						+ this.sookshmadashaDetailHashTable);
				logger.debug("Successfully Load ANTARDASHA/SOOKSHMADASHA cache ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*      */public String searchPlanetLocation(String planetName,
			Hashtable cuspPlanetHashTable, Hashtable cuspHouseHashTable)
	/*      */{
		if (logger.isDebugEnabled()) {
			/* 137 */logger
					.debug("Inside Function  searchPlanetLocation for planet name || "
							+ planetName);
		}
		/* 138 */StringBuffer retObj = new StringBuffer();
		/* 139 */retObj.append(" ");
		/*      */try
		/*      */{
			/* 143 */if (cuspPlanetHashTable.containsKey(planetName))
			/*      */{
				/* 146 */KundliHouseBean obj = (KundliHouseBean) cuspPlanetHashTable
						.get(planetName);
				/* 147 */retObj.append(obj.getHouseNumber());
				/*      */if (logger.isDebugEnabled())
					/* 149 */logger.debug("cuspPlanetHashTable  Contains  ["
							+ planetName + " ] House Number  ["
							+ obj.getHouseNumber() + " ]");
				/*      */
				/* 152 */Planet getPlanetSignNumber = Planet
						.toPlanets(planetName);
				/*      */String[] signStr;
				/* 156 */if (getPlanetSignNumber.getSecondRashi() != 0)
				/*      */{
					/* 158 */signStr = new String[2]; // String[] removed by
														// prianka
					/* 159 */signStr[0] = Integer.toString(getPlanetSignNumber
							.getFirstRashi());
					/* 160 */signStr[1] = Integer.toString(getPlanetSignNumber
							.getSecondRashi());
					/*      */}
				/*      */else
				/*      */{
					/* 164 */signStr = new String[1];
					/* 165 */signStr[0] = Integer.toString(getPlanetSignNumber
							.getFirstRashi());
					/*      */}
				/*      */if (logger.isDebugEnabled())
					/* 169 */logger.debug("Getting Rashi numbers  of Planet [ "
							+ planetName + " ] Array Length [ "
							+ signStr.length + " ] FirstRashi No. [ "
							+ getPlanetSignNumber.getFirstRashi()
							+ " ] Second Rashi No.[ "
							+ getPlanetSignNumber.getSecondRashi() + " ]");
				/*      */
				/* 171 */retObj.append("/ ");
				/*      */
				/* 174 */for (String st : signStr)
				/*      */{
					/* 179 */Iterator itr = cuspHouseHashTable.values()
							.iterator();
					/*      */
					/* 183 */while (itr.hasNext())
					/*      */{
						/* 186 */KundliHouseBean ob = (KundliHouseBean) itr
								.next();
						/* 187 */if (!ob.getSignNumber().equals(st))
							/*      */continue;
						/* 189 */retObj.append(ob.getHouseNumber());
						/* 190 */retObj.append(",");
						/*      */}
					/*      */
					/*      */}
				/*      */
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 203 */ex.printStackTrace();
			/*      */}
		/*      */if (logger.isDebugEnabled())
			/* 206 */logger.debug("My Planet Value is return  || "
					+ retObj.toString());
		/*      */
		/* 209 */if (retObj.toString().endsWith(",")) {
			/* 210 */return retObj.toString().substring(0,
					retObj.toString().length() - 1);
			/*      */}
		/* 212 */return retObj.toString();
		/*      */}

	/*      */
	/*      */public void getCuspBirthHashTable(String queryFetch,
			Hashtable planetHashTable, Hashtable houseHashTable)
	/*      */throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	/*      */{
		if (logger.isDebugEnabled()) {
			/* 221 */logger
					.debug("Inside Function getCuspBirthHashTable Fetch data for  "
							+ queryFetch);
			/*      */}
		/* 225 */String query = "";
		/* 226 */if (queryFetch.equalsIgnoreCase("BIRTHCHART"))
			/* 227 */query = "/ASTROCHART/BIRTHCHART/HOUSE//@NAME";
		/* 228 */else if (queryFetch.equalsIgnoreCase("CUSPCHART"))
			/* 229 */query = "/ASTROCHART/CUSPCHART/HOUSE//@NAME";
		/*      */else {
			if (logger.isDebugEnabled()) {
				logger.debug("Query Is not Metch from keywords  BIRTHCHART/CUSPCHART    query ||  "
						+ queryFetch);
			}
			/* 231 */
			/*      */}
		/* 233 */XPathFactory xFactory = XPathFactory.newInstance();
		/*      */if (logger.isDebugEnabled())
			/* 235 */logger.debug("Query For Fetch CuspORBirth data  " + query);
		/*      */
		/* 237 */XPath xpath = xFactory.newXPath();
		/*      */
		/* 241 */this.expr = xpath.compile(query);
		/*      */
		/* 244 */Object result = this.expr.evaluate(this.doc,
				XPathConstants.NODESET);
		/*      */
		/* 247 */NodeList nodes = (NodeList) result;
		/* 248 */for (int i = 0; i < nodes.getLength();)
		/*      */{
			/* 252 */Attr houseAttr = (Attr) nodes.item(i);
			/* 253 */Attr planetAttr = (Attr) nodes.item(i + 1);
			
			/* 254 */Attr signAttr = (Attr) nodes.item(i + 2);
			/*      */
			/* 258 */if (!StringUtils.isBlank(planetAttr.getValue()))
			/*      */{
				/* 260 */String[] planetName = planetAttr.getValue().split(" ");
				/* 261 */for (String planet : planetName)
				/*      */{
					/* 263 */KundliHouseBean obj = new KundliHouseBean();
					/* 264 */obj.setPlanetName(planet.trim());
					/* 265 */obj.setHouseNumber(houseAttr.getValue().trim());
					/* 266 */obj.setSignNumber(signAttr.getValue().trim());
					/* 267 */planetHashTable.put(planet.trim(), obj);
					/*      */}
				/*      */
				/*      */}
			/*      */
			/* 277 */KundliHouseBean beanObj = new KundliHouseBean();
			/* 278 */beanObj.setHouseNumber(houseAttr.getValue().trim());
			/* 279 */beanObj.setSignNumber(signAttr.getValue().trim());
			/*      */
			/* 281 */if (!StringUtils.isBlank(planetAttr.getValue()))
			/*      */{
				/* 283 */beanObj.setPlanetName(planetAttr.getValue().trim());
				/*      */}
			/*      */
			/* 287 */houseHashTable.put(houseAttr.getValue().trim(), beanObj);
			/*      */
			/* 290 */i += 3;
			/*      */}
		if (logger.isDebugEnabled()) {
			logger.debug("planetHashTable " + queryFetch + " "
					+ planetHashTable);
			logger.debug("houseHashTable  " + queryFetch + " " + houseHashTable);
		}
		/*      */}

	/*      */
	/*      */public String getNLAndSL(String searchAttribute, String planetName,
			LinkedHashMap planetDashaHashTable)
	/*      */{
		if (logger.isDebugEnabled())
			/* 298 */logger
					.debug("Inside function  getNLAndSL for Search Attribute [ "
							+ searchAttribute
							+ " ] of planet [ "
							+ planetName
							+ " ]");
		/*      */try
		/*      */{
			/* 304 */if (!searchAttribute.equalsIgnoreCase("MDL"))
			/*      */{
				/* 306 */if (planetDashaHashTable.containsKey(planetName))
				/*      */{
					/* 308 */PlanetDetailBean planetBean = (PlanetDetailBean) planetDashaHashTable
							.get(planetName);
					/*      */
					/* 310 */if (searchAttribute.equalsIgnoreCase("NL"))
					/*      */{
						/* 312 */return planetBean.getNL();
						/*      */}
					/*      */
					/* 315 */if (searchAttribute.equalsIgnoreCase("SL"))
					/*      */{
						/* 317 */return planetBean.getSL();
						/*      */}
					/* 319 */if (searchAttribute.equalsIgnoreCase("RL"))
					/*      */{
						/* 321 */return planetBean.getRL();
						/*      */}
					/*      */
					/*      */}
				/*      */else
				/*      */{
					if (logger.isDebugEnabled())
						/* 327 */logger
								.debug("Inside function  getNLAndSL no any key [ "
										+ planetName
										+ " ] found inside planetDetailHashTable ");
					/* 328 */return null;
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				/* 334 */if (planetDashaHashTable.containsKey(planetName))
				/*      */{
					/* 336 */Vector data = (Vector) planetDashaHashTable
							.get(planetName);
					/* 337 */MahaDashaBean dashBean = (MahaDashaBean) data
							.get(data.size() - 1);
					/*      */if (logger.isDebugEnabled())
						/* 339 */logger
								.debug("My Last Vector Index  Parent Planet Name [ "
										+ dashBean.getParent()
										+ " End Data  [ "
										+ dashBean.getEndTime() + " ]");
					/*      */
					/* 341 */return dashBean.getEndTime();
					/*      */}
				/*      */if (logger.isDebugEnabled())
					/* 345 */logger
							.debug("Inside function  getNLAndSL no any key [ "
									+ planetName
									+ " ] found inside planetDetailHashTable ");
				/* 346 */return null;
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 352 */ex.printStackTrace();
			/* 353 */return null;
			/*      */}
		/*      */
		/* 356 */return null;
		/*      */}

	/*      */
	/*      */public void getPlanetsHousesHashTable(String queryFetch,
			LinkedHashMap planetHousesHashTable)
	/*      */throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	/*      */{
		if (logger.isDebugEnabled())
			/* 363 */logger
					.debug("Inside Function getPlanetsHousesHashTable Fetch data for  "
							+ queryFetch);
		/*      */
		/* 367 */String query = "";
		/* 368 */if (queryFetch.equalsIgnoreCase("PLANETS"))
			/* 369 */query = "/ASTROCHART/PLANETS//@* ";
		/* 370 */else if (queryFetch.equalsIgnoreCase("HOUSES"))
			/* 371 */query = "/ASTROCHART/HOUSES//@* ";
		/*      */else {
			if (logger.isDebugEnabled())
				/* 373 */logger
						.debug("Query Is not Metch  query " + queryFetch);
			/*      */}
		/*      */
		/* 376 */XPathFactory xFactory = XPathFactory.newInstance();
		/*      */if (logger.isDebugEnabled())
			/* 378 */logger.debug("Query For Fetch PlanetsHouses data  "
					+ query);
		/*      */
		/* 380 */XPath xpath = xFactory.newXPath();
		/*      */
		/* 384 */this.expr = xpath.compile(query);
		/*      */
		/* 387 */Object result = this.expr.evaluate(this.doc,
				XPathConstants.NODESET);
		/*      */
		/* 390 */NodeList nodes = (NodeList) result;
		/*      */


			//logger.info("node length in getPlanetsHousesHashTable>> "+ nodes.getLength());
		/* 392 */for (int i = 0; i < nodes.getLength();)
		/*      */{
			/* 395 */if (queryFetch.equalsIgnoreCase("PLANETS"))
			/*      */{
				/* 398 */Attr degreeAttr = (Attr) nodes.item(i);
				/* 399 */Attr nakshatraAttr = (Attr) nodes.item(i + 1);
				/* 400 */Attr nameAttr = (Attr) nodes.item(i + 2);
				/* 401 */Attr NLAttr = (Attr) nodes.item(i + 3);
				/* 402 */Attr padamAttr = (Attr) nodes.item(i + 4);
				/* 403 */Attr RCAttr = (Attr) nodes.item(i + 5);
				/* 404 */Attr RLAttr = (Attr) nodes.item(i + 6);
				/* 405 */Attr signAttr = (Attr) nodes.item(i + 7);
				/* 406 */Attr SLAttr = (Attr) nodes.item(i + 8);
				/* 407 */Attr SSAttr = (Attr) nodes.item(i + 9);
				/*      */
				/* 409 */if ((!nameAttr.getValue().trim()
						.equalsIgnoreCase("Uranus"))
						&& (!nameAttr.getValue().trim()
								.equalsIgnoreCase("Neptune"))
						&& (!nameAttr.getValue().trim()
								.equalsIgnoreCase("Pluto")))
				/*      */{
					/* 415 */PlanetDetailBean planetDetail = new PlanetDetailBean();
					/* 416 */planetDetail.setDegree(degreeAttr.getValue()
							.trim());
					/* 417 */planetDetail.setNakshatra(nakshatraAttr.getValue()
							.trim());
					/* 418 */planetDetail.setPlanetName(nameAttr.getValue()
							.trim());
					/* 419 */planetDetail.setNL(NLAttr.getValue().trim());
					/* 420 */planetDetail.setPadam(padamAttr.getValue().trim());

					if (planetDetail.getPlanetName().equalsIgnoreCase("Rahu")
							|| planetDetail.getPlanetName().equalsIgnoreCase(
									"Ketu")) {
						/* 421 */planetDetail.setRC("R");

					} else {
						/* 421 */planetDetail.setRC(RCAttr.getValue().trim());
					}
					/* 422 */planetDetail.setRL(RLAttr.getValue());
					/* 423 */planetDetail.setSignName(signAttr.getValue()
							.trim());
					/* 424 */planetDetail.setSL(SLAttr.getValue());
					/* 425 */planetDetail.setSS(SSAttr.getValue());
					/*      */
					/* 428 */planetHousesHashTable.put(nameAttr.getValue()
							.trim(), planetDetail);
					/*      */}
				/*      */
				/* 433 */i += 10;
				/*      */} else {
				/* 435 */if (!queryFetch.equalsIgnoreCase("HOUSES"))
				/*      */{
					/*      */continue;
					/*      */}
				/*      */
				/* 440 */Attr degreeAttr = (Attr) nodes.item(i);
				/* 441 */Attr nakshatraAttr = (Attr) nodes.item(i + 1);
				/* 442 */Attr nameAttr = (Attr) nodes.item(i + 2);
				/* 443 */Attr NLAttr = (Attr) nodes.item(i + 3);
				/* 444 */Attr padamAttr = (Attr) nodes.item(i + 4);
				/* 445 */Attr RLAttr = (Attr) nodes.item(i + 5);
				/* 446 */Attr signAttr = (Attr) nodes.item(i + 6);
				/* 447 */Attr SLAttr = (Attr) nodes.item(i + 7);
				/* 448 */Attr SSAttr = (Attr) nodes.item(i + 8);
				/*      */
				/* 450 */HouseDetailBean houseDetail = new HouseDetailBean();
				/* 451 */houseDetail.setDegree(degreeAttr.getValue().trim());
				/* 452 */houseDetail.setNakshatra(nakshatraAttr.getValue()
						.trim());
				/* 453 */houseDetail.setPlanetName(nameAttr.getValue().trim());
				/* 454 */houseDetail.setNL(NLAttr.getValue().trim());
				/* 455 */houseDetail.setPadam(padamAttr.getValue().trim());
				/* 456 */houseDetail.setRL(RLAttr.getValue());
				/* 457 */houseDetail.setSignName(signAttr.getValue().trim());
				/* 458 */houseDetail.setSL(SLAttr.getValue());
				/* 459 */houseDetail.setSS(SSAttr.getValue());
				/*      */
				/* 462 */planetHousesHashTable.put(nameAttr.getValue().trim(),
						houseDetail);
				/*      */
				/* 469 */i += 9;
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */public AstroBean getNatalStrength()
	/*      */{
		if (logger.isDebugEnabled())
			/* 489 */logger.debug("Inside Function getNatalStrength() ");
		/* 490 */AstroBean natalStrength = null;
		/*      */try
		/*      */{
			/* 496 */Kundli birthKundli = new Kundli("BIRTH");
			/*      */
			/* 498 */if (getBirthCuspChartData("BIRTH", birthKundli) < 0)
			/*      */{
				if (logger.isDebugEnabled())
					/* 500 */logger
							.debug("Error in getting BIRTH data inside function getBirthCuspChartData ");
				/* 501 */return null;
				/*      */}
			if (logger.isDebugEnabled())
				/* 503 */logger.debug("Birth Chart Data || "
						+ birthKundli.toString());
			/*      */
			/* 505 */Kundli cuspKundli = new Kundli("CUSP");
			/*      */
			/* 507 */if (getBirthCuspChartData("CUSP", cuspKundli) < 0)
			/*      */{
				if (logger.isDebugEnabled())
					/* 509 */logger
							.debug("Error in getting CUSP data inside function getBirthCuspChartData ");
				/* 510 */return null;
				/*      */}
			/*      */if (logger.isDebugEnabled())
				/* 514 */logger.debug("Cusp Chart Data || "
						+ cuspKundli.toString());
			/*      */
			/* 518 */getVacantHouseList(cuspKundli, this.vacantHouseHashTable,
					this.planetDetailHashTable);
			/*      */
			/* 523 */String[][] tabularData = new String[9][7];
			/*      */
			/* 529 */String rahuRashiLord = getNLAndSL("RL", "Rahu",
					this.planetDetailHashTable);
			/* 530 */String ketuRashiLord = getNLAndSL("RL", "Ketu",
					this.planetDetailHashTable);
			/* 531 */if (!StringUtils.isBlank(rahuRashiLord))
			/*      */{

					logger.info("rahuRashiLord>> "+rahuRashiLord);
				/* 533 */rahuRashiLord = searchPlanetLocation(rahuRashiLord,
						this.cuspPlanetHashTable, this.cuspHouseHashTable);
				/*      */}
			/*      */
			/* 536 */if (!StringUtils.isBlank(ketuRashiLord))
			/*      */{
				/* 538 */ketuRashiLord = searchPlanetLocation(ketuRashiLord,
						this.cuspPlanetHashTable, this.cuspHouseHashTable);
				/*      */}
			/*      */if (logger.isDebugEnabled())
				/* 541 */logger
						.debug(" My Rahu ketu sign houses number rahuRashiLord [ "
								+ rahuRashiLord
								+ " ] ketuRashiLord [ "
								+ ketuRashiLord + " ]");
			/*      */
			/* 543 */rahuRashiLord = rahuRashiLord.substring(
					rahuRashiLord.indexOf("/") + 1, rahuRashiLord.length());
			/* 544 */ketuRashiLord = ketuRashiLord.substring(
					ketuRashiLord.indexOf("/") + 1, ketuRashiLord.length());
			/*      */
			/* 546 */for (int loop = 0; loop < this.planetName.length; loop++)
			/*      */{
				/* 550 */String sourceValue = searchPlanetLocation(
						this.planetName[loop], this.cuspPlanetHashTable,
						this.cuspHouseHashTable);
				/*      */
				/* 554 */String resultNL = getNLAndSL("NL",
						this.planetName[loop], this.planetDetailHashTable);
				/* 555 */if (!StringUtils.isBlank(resultNL)) {
					/* 556 */resultNL = resultNL
							+ searchPlanetLocation(resultNL.trim(),
									this.cuspPlanetHashTable,
									this.cuspHouseHashTable);
					/*      */}
				/*      */
				/* 560 */String resultSL = getNLAndSL("SL",
						this.planetName[loop], this.planetDetailHashTable);
				/* 561 */if (!StringUtils.isBlank(resultSL)) {
					/* 562 */resultSL = resultSL
							+ searchPlanetLocation(resultSL.trim(),
									this.cuspPlanetHashTable,
									this.cuspHouseHashTable);
					/*      */}
				/*      */
				/* 566 */String resultNLOfSL = getNLAndSL("SL",
						this.planetName[loop], this.planetDetailHashTable);
				/* 567 */if (!StringUtils.isBlank(resultNLOfSL)) {
					/* 568 */resultNLOfSL = getNLAndSL("NL",
							resultNLOfSL.trim(), this.planetDetailHashTable);
					/*      */}
				/* 570 */if (!StringUtils.isBlank(resultNLOfSL)) {
					/* 571 */resultNLOfSL = resultNLOfSL
							+ searchPlanetLocation(resultNLOfSL.trim(),
									this.cuspPlanetHashTable,
									this.cuspHouseHashTable);
					/*      */}
				/* 573 */String MDLValue = getNLAndSL("MDL",
						this.planetName[loop], this.antardashaDetailHashTable);
				/* 574 */String vacantHouse = getVacantHouse(this.planetName[loop]);
				/*      */
				/* 576 */tabularData[loop][0] = this.planetName[loop];
				/* 577 */tabularData[loop][3] = vacantHouse;
				/* 578 */tabularData[loop][6] = MDLValue;
				/* 579 */tabularData[loop][1] = sourceValue;
				/* 580 */tabularData[loop][2] = resultNL;
				/* 581 */tabularData[loop][4] = resultSL;
				/* 582 */tabularData[loop][5] = resultNLOfSL;
				/*      */
				/* 586 */if (resultNL.contains("Rahu"))
					/* 587 */tabularData[loop][2] = (resultNL + rahuRashiLord);
				/* 588 */if (resultSL.contains("Rahu"))
					/* 589 */tabularData[loop][4] = (resultSL + rahuRashiLord);
				/* 590 */if (resultNLOfSL.contains("Rahu"))
					/* 591 */tabularData[loop][5] = (resultNLOfSL + rahuRashiLord);
				/* 592 */if (this.planetName[loop].equalsIgnoreCase("Rahu")) {
					/* 593 */tabularData[loop][1] = (sourceValue + rahuRashiLord);
					/*      */}
				/*      */
				/* 597 */if (resultNL.contains("Ketu"))
					/* 598 */tabularData[loop][2] = (resultNL + ketuRashiLord);
				/* 599 */if (resultSL.contains("Ketu"))
					/* 600 */tabularData[loop][4] = (resultSL + ketuRashiLord);
				/* 601 */if (resultNLOfSL.contains("Ketu"))
					/* 602 */tabularData[loop][5] = (resultNLOfSL + ketuRashiLord);
				/* 603 */if (this.planetName[loop].equalsIgnoreCase("Ketu")) {
					/* 604 */tabularData[loop][1] = (sourceValue + ketuRashiLord);
					/*      */}
				/*      */
				/*      */}
			/*      */
			/* 611 */natalStrength = new AstroBean();
			/*      */if (logger.isDebugEnabled())
				/* 613 */logger.debug("Before Getting Person detail of user ");
			/* 614 */getPersonalDetails(natalStrength);
			/*      */if (logger.isDebugEnabled())
				/* 617 */logger
						.debug(" Tabular Format Data inserted in 2D array ");
			/* 618 */for (int outer = 0; outer < tabularData.length; outer++)
			/*      */{
				/* 620 */for (int inner = 0; inner < tabularData[outer].length; inner++)
				/*      */{
					if (logger.isDebugEnabled())
						/* 623 */logger.debug("     "
								+ tabularData[outer][inner]);
					/*      */}
				/*      */

				/*      */}
			/*      */
			/* 631 */natalStrength.setBirthKundli(birthKundli);
			/* 632 */natalStrength.setCuspKundli(cuspKundli);
			/* 633 */natalStrength.setNatalStrengthChart(tabularData);
			/*      */if (logger.isDebugEnabled())
				/* 635 */logger.debug("Generate  Rahu/Ketu planets effect ");
			/*      */
			/* 639 */getRahuKetuShadowEffect(natalStrength,
					this.birthPlanetHashTable, this.birthHouseHashTable);
			/*      */if (logger.isDebugEnabled())
				/* 641 */logger
						.debug("Store HashTable Cache into AstroBean Object");
			/*      */
			/* 645 */natalStrength
					.setBirthHouseHashTable(this.birthHouseHashTable);
			/* 646 */natalStrength
					.setBirthPlanetHashTable(this.birthPlanetHashTable);
			/*      */
			/* 650 */natalStrength
					.setCuspHouseHashTable(this.cuspHouseHashTable);
			/* 651 */natalStrength
					.setCuspPlanetHashTable(this.cuspPlanetHashTable);
			/*      */
			/* 655 */natalStrength
					.setPlanetDetailHashTable(this.planetDetailHashTable);
			/*      */
			/* 659 */natalStrength
					.setHouseDetailHashTable(this.houseDetailHashTable);
			/*      */
			/* 663 */natalStrength
					.setSookshmadashaDetailHashTable(this.sookshmadashaDetailHashTable);
			/* 664 */natalStrength
					.setAntardashaDetailHashTable(this.antardashaDetailHashTable);
			/*      */
				natalStrength.setKundliData(this.kundliData); 
			if(logger.isDebugEnabled()){
				logger.debug(" Successfully Store HashTable Cache into AstroBean Object");
			}
		}
		/*      */catch (Exception ex)
		/*      */{
			/* 671 */ex.printStackTrace();
			/* 672 */return null;
			/*      */}
		/*      */
		/* 675 */return natalStrength;
		/*      */}

	/*      */
	/*      */public int getBirthCuspChartData(String query, Kundli kundliData)
	/*      */throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	/*      */{
		/*      */try
		/*      */{
			/* 686 */XPathFactory xFactory = XPathFactory.newInstance();
			/*      */
			/* 690 */XPath xpath = xFactory.newXPath();
			/*      */
			/* 692 */if (query.equalsIgnoreCase("BIRTH"))
				/* 693 */this.expr = xpath
						.compile("/ASTROCHART/BIRTHCHART/HOUSE//@NAME");
			/* 694 */else if (query.equalsIgnoreCase("CUSP"))
				/* 695 */this.expr = xpath
						.compile("/ASTROCHART/CUSPCHART/HOUSE//@NAME");
			/*      */else {
				if (logger.isDebugEnabled())
					/* 697 */logger.debug("Please select Birth Or Cusp");
				/*      */}
			/*      */
			/* 700 */Object result = this.expr.evaluate(this.doc,
					XPathConstants.NODESET);
			/*      */
			/* 703 */NodeList nodes = (NodeList) result;
			/* 704 */int counter = 1;
			/* 705 */for (int i = 0; i < nodes.getLength();)
			/*      */{
				/* 707 */StringBuffer birthData = new StringBuffer();
				/* 708 */Attr planetAttr = (Attr) nodes.item(i + 1);
				/*      */
				/* 710 */Attr signAttr = (Attr) nodes.item(i + 2);
				/* 711 */birthData.append(signAttr.getValue());
				/* 712 */birthData.append("_");
				/* 713 */birthData.append(planetAttr.getValue());
				if (logger.isDebugEnabled())
					/* 714 */logger.debug("Planet Name and   Sign Value  || "
							+ birthData.toString());
				/* 716 */kundliData.setHouseData(counter, birthData.toString());
				/*      */
				/* 718 */i += 3;
				/* 719 */counter++;
				/*      */}
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 724 */ex.printStackTrace();
			/* 725 */return -1;
			/*      */}
		/* 727 */return 0;
		/*      */}

	/*      */
	/*      */public String getRahuKetuEffectPlanet(String rahuAndKetu,
			Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable)
	/*      */{
		/* 733 */
		if(logger.isDebugEnabled()){
			logger.debug("Inside Function function getRahuKetuEffectPlanet for getting rahu/ketu effeted planets  || "
					+ rahuAndKetu);
		}	
		/*      */try
		/*      */{
			/* 738 */if (birthPlanetHashTable.containsKey(rahuAndKetu))
			/*      */{
				/* 741 */KundliHouseBean obj = (KundliHouseBean) birthPlanetHashTable
						.get(rahuAndKetu);
				/*      */
				/* 747 */int houseNumber = Integer.parseInt(obj
						.getHouseNumber());
				/*      */
				if (logger.isDebugEnabled())
					/* 749 */logger.debug("My Original HouseNumber for planet "
							+ rahuAndKetu + " house number" + houseNumber);
				/*      */
				/* 751 */houseNumber -= 6;
				/* 752 */if (houseNumber <= 0) {
					/* 753 */houseNumber += 12;
					/*      */}
				if (logger.isDebugEnabled())
					/* 755 */logger
							.debug("After Move 6th position HouseNumber for planet "
									+ rahuAndKetu
									+ " house number"
									+ houseNumber);
				/*      */
				/* 757 */String houseNo = Integer.toString(houseNumber);
				/*      */
				/* 759 */if (birthHouseHashTable.containsKey(houseNo))
				/*      */{
					/* 763 */KundliHouseBean objBean = (KundliHouseBean) birthHouseHashTable
							.get(houseNo);
					if (logger.isDebugEnabled())
						/* 764 */logger
								.debug("Planets name return from  getRahuKetuEffectPlanet function || "
										+ objBean.getPlanetName());
					/* 765 */return objBean.getPlanetName();
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				if (logger.isDebugEnabled())
					/* 773 */logger
							.debug("Hash Table does not contain this planet "
									+ rahuAndKetu);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 779 */ex.printStackTrace();
			/*      */}
		/* 781 */return "unsuccess";
		/*      */}

	/*      */
	/*      */public void getRahuKetuShadowEffect(AstroBean natalStrength,
			Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable)
	/*      */{
				if (logger.isDebugEnabled()){
		/* 786 */logger.info("Inside Function  getRahuKetuShadowEffect for generate rahu/ketu effect ");
				}
		/*      */try
		/*      */{
			/* 792 */for (String str : this.rahuAndKetu)
			/*      */{
				/* 798 */String rahuKetuSource = str;
				/*      */
				/* 802 */String rahuAndKetuData = getRahuKetuEffectPlanet(str,
						birthPlanetHashTable, birthHouseHashTable);
				if (logger.isDebugEnabled())
					/* 803 */logger
							.debug("Rahu/Ketu Effected Planets lists [ "
									+ rahuAndKetuData + " ] for planet [ "
									+ str + " ]");
				/* 804 */String[] atr = rahuAndKetuData.split(" ");
				/* 805 */for (String planetEffec : atr)
				/*      */{
					/* 807 */if ((planetEffec.equalsIgnoreCase("unsuccess"))
							|| (planetEffec.equalsIgnoreCase("Rahu"))
							|| (planetEffec.equalsIgnoreCase("Ketu")))
					/*      */{
						if (logger.isDebugEnabled())
							/* 809 */logger
									.debug(" Inside unsuccess and Rahu ketu Find Data");
						/*      */}
					/*      */else
					/*      */{
						if (logger.isDebugEnabled())
							/* 815 */logger
									.debug("Obtain Effect Of this planet "
											+ planetEffec);
						/*      */
						/* 817 */if (StringUtils.isBlank(planetEffec))
							/*      */continue;
						/* 819 */planetEffec = planetEffec
								+ searchPlanetLocation(planetEffec.trim(),
										this.cuspPlanetHashTable,
										this.cuspHouseHashTable);
						/* 820 */if (str.equalsIgnoreCase("Rahu"))
							/* 821 */natalStrength.setAspect(Planet.Rahu,
									planetEffec);
						/* 822 */else if (str.equalsIgnoreCase("Ketu"))
							/* 823 */natalStrength.setAspect(Planet.Ketu,
									planetEffec);
						/*      */else {
							if (logger.isDebugEnabled())
								/* 825 */logger
										.debug("No any planet define plante name || "
												+ str);
							/*      */}
						/*      */
						/*      */}
					/*      */
					/*      */}
				/*      */
				/* 833 */for (int loop = 0; loop < this.planetEffect.length; loop++)
				/*      */{
					/* 835 */boolean isEffected = false;
					/* 836 */Planet p = Planet
							.toPlanets(this.planetEffect[loop]);
					/* 837 */String ptr = this.planetEffect[loop];
					/*      */
					/* 839 */switch (loop)
					/*      */{
					/*      */case 0:
						/* 844 */
						isEffected = getJupiterSaturnMarsEffect(
								this.planetEffect[loop], str,
								p.getFirstAspect(), p.getSecondAspect(),
								birthPlanetHashTable, birthHouseHashTable);
						/* 845 */break;
					/*      */case 1:
						/* 848 */
						isEffected = getJupiterSaturnMarsEffect(
								this.planetEffect[loop], str,
								p.getFirstAspect(), p.getSecondAspect(),
								birthPlanetHashTable, birthHouseHashTable);
						/* 849 */break;
					/*      */case 2:
						/* 852 */
						isEffected = getJupiterSaturnMarsEffect(
								this.planetEffect[loop], str,
								p.getFirstAspect(), p.getSecondAspect(),
								birthPlanetHashTable, birthHouseHashTable);
						/*      */
					}
					/*      */
					/* 856 */if (isEffected)
					/*      */{
						if (logger.isDebugEnabled())
							/* 858 */logger.debug("There is aspect of [ " + str
									+ " ] into [ " + this.planetEffect[loop]
									+ " ]");
						/* 859 */ptr = ptr
								+ searchPlanetLocation(ptr,
										this.cuspPlanetHashTable,
										this.cuspHouseHashTable);
						/* 860 */if (str.equalsIgnoreCase("Rahu"))
							/* 861 */natalStrength.setAspect(Planet.Rahu, ptr);
						/* 862 */else if (str.equalsIgnoreCase("Ketu"))
							/* 863 */natalStrength.setAspect(Planet.Ketu, ptr);
						/*      */else {
							if (logger.isDebugEnabled())
								/* 865 */logger
										.debug("No any planet define plante name || "
												+ str);
							/*      */}
						/*      */
						/*      */}
					/*      */else
					/*      */{
						if (logger.isDebugEnabled())
							/* 871 */logger
									.debug("There is no any aspect of [ " + str
											+ " ] into "
											+ this.planetEffect[loop]);
						/*      */}
					/*      */
					/*      */}
				/*      */
				/* 881 */String conjPlanetLists = getConjPlanetList(str,
						this.planetDetailHashTable);
				if (logger.isDebugEnabled())
					/* 882 */logger.debug("Conjuction  Planet Lists  || "
							+ conjPlanetLists);
				/*      */
				/* 884 */if (!StringUtils.isBlank(conjPlanetLists))
				/*      */{
					/* 886 */String[] conj = conjPlanetLists.split("_");
					/* 887 */String rahuKetuConj = "";
					/* 888 */for (String conjPlanet : conj)
					/*      */{
						/* 890 */rahuKetuConj = conjPlanet;
						/*      */
						/* 892 */if (conjPlanet.equalsIgnoreCase(str))
							/*      */continue;
						/* 894 */rahuKetuConj = rahuKetuConj
								+ searchPlanetLocation(conjPlanet,
										this.cuspPlanetHashTable,
										this.cuspHouseHashTable);
						/*      */
						/* 896 */if (str.equalsIgnoreCase("Rahu"))
							/* 897 */natalStrength.setConjuction(Planet.Rahu,
									rahuKetuConj);
						/* 898 */else if (str.equalsIgnoreCase("Ketu"))
							/* 899 */natalStrength.setConjuction(Planet.Ketu,
									rahuKetuConj);
						/*      */else {
							if (logger.isDebugEnabled())
								/* 901 */logger
										.debug("No any planet define plante name || "
												+ str);
							/*      */}
						/*      */if (logger.isDebugEnabled())
							/* 904 */logger
									.debug(" Conjuction  of this planets   [ "
											+ rahuKetuConj + " ] for [ " + str
											+ " ]");
						/*      */}
					/*      */
					/*      */}
				/*      */if (logger.isDebugEnabled())
					/* 914 */logger
							.debug("Getting  Sign Loard and Rashi loard  ");
				/*      */
				/* 917 */String rahuKetuSignLord = "";
				String rahuKetuRashiLord = "";
				/* 918 */rahuKetuSignLord = getNLAndSL("NL", str,
						this.planetDetailHashTable);
				/* 919 */if (!StringUtils.isBlank(rahuKetuSignLord))
				/*      */{
					/* 921 */rahuKetuSignLord = rahuKetuSignLord
							+ searchPlanetLocation(rahuKetuSignLord,
									this.cuspPlanetHashTable,
									this.cuspHouseHashTable);
					/* 922 */if (str.equalsIgnoreCase("Rahu"))
						/* 923 */natalStrength.setSignLoard(Planet.Rahu,
								rahuKetuSignLord);
					/* 924 */else if (str.equalsIgnoreCase("Ketu")) {
						/* 925 */natalStrength.setSignLoard(Planet.Ketu,
								rahuKetuSignLord);
						/*      */}
					/*      */}
				/*      */
				/* 929 */rahuKetuRashiLord = getNLAndSL("RL", str,
						this.planetDetailHashTable);
				/* 930 */if (!StringUtils.isBlank(rahuKetuRashiLord))
				/*      */{
					/* 932 */rahuKetuRashiLord = rahuKetuRashiLord
							+ searchPlanetLocation(rahuKetuRashiLord,
									this.cuspPlanetHashTable,
									this.cuspHouseHashTable);
					/* 933 */if (str.equalsIgnoreCase("Rahu"))
						/* 934 */natalStrength.setRashiLoard(Planet.Rahu,
								rahuKetuRashiLord);
					/* 935 */else if (str.equalsIgnoreCase("Ketu")) {
						/* 936 */natalStrength.setRashiLoard(Planet.Ketu,
								rahuKetuRashiLord);
						/*      */}
					/*      */}
				/*      */if (logger.isDebugEnabled())
					/* 940 */logger
							.debug(" SignLord and RashiLord for planet name [ "
									+ str + "] [S] " + rahuKetuSignLord
									+ " [R] " + rahuKetuRashiLord);
				/*      */
				/* 944 */rahuKetuSource = searchPlanetLocation(rahuKetuSource,
						this.cuspPlanetHashTable, this.cuspHouseHashTable);
				/*      */
				/* 950 */rahuKetuSource = rahuKetuSource
						+ rahuKetuRashiLord.substring(
								rahuKetuRashiLord.indexOf("/") + 1,
								rahuKetuRashiLord.length());
				/* 951 */if (str.equalsIgnoreCase("Rahu"))
					/* 952 */natalStrength.setRahuSource(rahuKetuSource);
				/* 953 */else if (str.equalsIgnoreCase("Ketu"))
					/* 954 */natalStrength.setKetuSource(rahuKetuSource);
				/*      */else {
					if (logger.isDebugEnabled())
						/* 956 */logger
								.debug("No any planet define plante name || "
										+ str);
					/*      */}
				/*      */
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 967 */ex.printStackTrace();
			/*      */}
		/*      */}

	/*      */
	/*      */public boolean getJupiterSaturnMarsEffect(String planetEffect,
			String planetName, int first, int second,
			Hashtable birthPlanetHashTable, Hashtable birthHouseHashTable)
	/*      */{
		/* 977 */boolean retVal = false;
		/*      */if (logger.isDebugEnabled())
		/* 979 */logger
				.debug("Inside function getJupiterSaturnMarsEffect for getting rahu/ketu planet effect on  Ju/Sa/Ma || "
						+ planetEffect);
		/*      */try
		/*      */{
			/* 984 */if (birthPlanetHashTable.containsKey(planetEffect))
			/*      */{
				/* 987 */KundliHouseBean obj = (KundliHouseBean) birthPlanetHashTable
						.get(planetEffect);
				/*      */
				if (logger.isDebugEnabled())
					/* 989 */logger.debug("Planet Name Contains "
							+ planetEffect + "House Number "
							+ obj.getHouseNumber());
				/*      */
				/* 992 */int houseNumber = Integer.parseInt(obj
						.getHouseNumber());
				/* 993 */int firstHouse = houseNumber + first;
				/* 994 */int secondHouse = houseNumber + second;
				/*      */
				/* 996 */if (firstHouse > 12)
					/* 997 */firstHouse -= 12;
				/* 998 */if (secondHouse > 12) {
					/* 999 */secondHouse -= 12;
					/*      */}
				if (logger.isDebugEnabled())
					/* 1001 */logger.debug("My New House Numbers FirstHouse "
							+ firstHouse + " SecondHouse " + secondHouse);
				/* 1002 */String[] houseArray = { Integer.toString(firstHouse),
						Integer.toString(secondHouse) };
				/* 1003 */for (int loop = 0; loop < houseArray.length; loop++)
				/*      */{
					/* 1005 */if (!birthHouseHashTable
							.containsKey(houseArray[loop]))
						/*      */continue;
					if (logger.isDebugEnabled())
						/* 1007 */logger
								.debug("My hash Table contain house Number for "
										+ houseArray[loop]);
					/* 1008 */KundliHouseBean beanObj = (KundliHouseBean) birthHouseHashTable
							.get(houseArray[loop]);
					if (logger.isDebugEnabled())
						/* 1009 */logger
								.debug("My planets name ||||||||||||||||||||||||"
										+ beanObj.getPlanetName());
					/*      */
					/* 1011 */if (StringUtils.isBlank(beanObj.getPlanetName())) {
						/*      */continue;
						/*      */}
					/* 1014 */String[] str = beanObj.getPlanetName().split(" ");
					if (logger.isDebugEnabled())
						/* 1015 */logger
								.debug("Planet Name Contains house number "
										+ houseArray[loop] + "Planet Name"
										+ beanObj.getPlanetName()
										+ " Planet Array "
										+ Arrays.toString(str));
					/* 1016 */for (String planetNm : str)
					/*      */{
						/* 1018 */if (!planetName.equalsIgnoreCase(planetNm
								.trim()))
							/*      */continue;
						if (logger.isDebugEnabled())
							/* 1020 */logger.debug("Effect of " + planetName
									+ " for planet " + planetEffect);
						/* 1021 */return true;
						/*      */}
					/*      */
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				/* 1033 */retVal = false;
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 1040 */ex.printStackTrace();
			/* 1041 */return false;
			/*      */}
		/*      */
		/* 1044 */return retVal;
		/*      */}

	/*      */
	/*      */public String getConjPlanetList(String rahuAndKetu,
			LinkedHashMap planetDetailHashTable)
	/*      */{
		      if (logger.isDebugEnabled())
		/* 1049 */logger
				.debug("Inside Function getConjPlanetList for getting conjection planet list of || "
						+ rahuAndKetu);
		/*      */
		/* 1051 */StringBuffer planetList = new StringBuffer();
		/*      */try
		/*      */{
			/* 1056 */int plusDegree = 0;
			int minusDegree = 0;
			/* 1057 */boolean isForword = false;
			boolean isBackword = false;
			boolean insertStatus = false;
			/* 1058 */if (planetDetailHashTable.containsKey(rahuAndKetu))
			/*      */{
				if (logger.isDebugEnabled())
					/* 1060 */logger
							.debug("My conjHashTable contain key for planet || "
									+ rahuAndKetu);
				/*      */
				/* 1062 */PlanetDetailBean rashiBean = (PlanetDetailBean) planetDetailHashTable
						.get(rahuAndKetu);
				/*      */if (logger.isDebugEnabled())
					/* 1064 */logger
							.debug("Degree Value of planet [" + rahuAndKetu
									+ "] [" + rashiBean.getDegree() + "]");
				/*      */
				/* 1066 */plusDegree = Integer.parseInt(rashiBean.getDegree()
						.substring(0, 2).trim()) + 4;
				/* 1067 */minusDegree = Integer.parseInt(rashiBean.getDegree()
						.substring(0, 2).trim()) - 4;
				/*      */if (logger.isDebugEnabled())
					/* 1069 */logger.debug(" Before PlusDegree [" + plusDegree
							+ "] MinusDegree [" + minusDegree
							+ "] Rashi Name [ " + rashiBean.getSignNumber());
				/* 1070 */if (plusDegree > 30)
				/*      */{
					/* 1072 */isForword = true;
					/* 1073 */plusDegree -= 30;
					/*      */}
				/* 1075 */if (minusDegree < 0)
				/*      */{
					/* 1077 */isBackword = true;
					/* 1078 */minusDegree += 30;
					/*      */}
				/*      */if (logger.isDebugEnabled())
					/* 1081 */logger.debug("After PlusDegree [" + plusDegree
							+ "] MinusDegree [" + minusDegree
							+ "] Forward Flag [" + isForword
							+ "] Backword Flag [" + isBackword + "]");
				/*      */
				/* 1085 */Iterator it = planetDetailHashTable.values()
						.iterator();
				/* 1086 */while (it.hasNext())
				/*      */{
					/* 1088 */insertStatus = false;
					/*      */
					/* 1090 */PlanetDetailBean obj = (PlanetDetailBean) it
							.next();
					/*      */
					/* 1092 */if (obj.getSignName().equals(
							rashiBean.getSignName()))
					/*      */{
						if (logger.isDebugEnabled())
							/* 1094 */logger
									.debug("Sign conjuction aries at planetName "
											+ obj.getPlanetName()
											+ " and sign name "
											+ obj.getSignName()
											+ "Both planet name || "
											+ obj.getPlanetName()
											+ " and "
											+ rashiBean.getPlanetName());
						/* 1095 */if (isForword)
						/*      */{
							/* 1097 */if (minusDegree <= Integer.parseInt(obj
									.getDegree().substring(0, 2).trim()))
								/* 1098 */insertStatus = true;
							if (logger.isDebugEnabled())
								/* 1099 */logger
										.debug("Is Forword flag is true ");
							/*      */}
						/* 1101 */if (isBackword)
						/*      */{
							/* 1103 */if (plusDegree <= Integer.parseInt(obj
									.getDegree().substring(0, 2).trim()))
								/* 1104 */insertStatus = true;
							if (logger.isDebugEnabled())
								/* 1105 */logger
										.debug("Is Backword flag is true ");
							/*      */}
						/* 1107 */if ((!isForword) && (!isBackword))
						/*      */{
							/* 1109 */if ((plusDegree <= Integer.parseInt(obj
									.getDegree().substring(0, 2).trim()))
									|| (minusDegree <= Integer
											.parseInt(obj.getDegree()
													.substring(0, 2).trim()))) {
								/* 1110 */insertStatus = true;
								/*      */}
							/*      */}
						/*      */
						/*      */}
					/*      */
					/* 1116 */if (!insertStatus)
						/*      */continue;
					/* 1118 */planetList.append(obj.getPlanetName());
					/* 1119 */planetList.append("_");
					/*      */}
				/*      */
				if (logger.isDebugEnabled())
					/* 1124 */logger.debug("Data Insert into planetList "
							+ planetList.toString());
				/*      */
				/* 1128 */String forBackSignName = "";
				/* 1129 */int nextPrevSignNumber = -1;
				/* 1130 */Rashi myrashi = Rashi
						.toRashi(rashiBean.getSignName());
				/*      */if (logger.isDebugEnabled())
					/* 1132 */logger.debug("My Previous Rashi Name || "
							+ rashiBean.getSignName());
				/* 1133 */insertStatus = false;
				/* 1134 */if (isForword)
				/*      */{
					/* 1136 */nextPrevSignNumber = myrashi.ordinal() + 1;
					/*      */
					/* 1138 */if (nextPrevSignNumber == 12)
						/* 1139 */nextPrevSignNumber = 0;
					/*      */}
				/* 1141 */if (isBackword)
				/*      */{
					/* 1143 */nextPrevSignNumber = myrashi.ordinal() - 1;
					/* 1144 */if (nextPrevSignNumber == -1)
						/* 1145 */nextPrevSignNumber = 0;
					/*      */}
				/* 1147 */if (nextPrevSignNumber >= 0)
				/*      */{
					/* 1149 */forBackSignName = myrashi.fromOrdinal(
							nextPrevSignNumber).toString();
					if (logger.isDebugEnabled())
						/* 1150 */logger
								.debug("My PreviousORNext  Rashi Name || "
										+ forBackSignName);
					/* 1151 */insertStatus = true;
					/*      */}
				/*      */
				/* 1154 */if (insertStatus)
				/*      */{
					if (logger.isDebugEnabled())
						/* 1156 */logger
								.debug("Checking for forword/backword sign");
					/* 1157 */Iterator itr = planetDetailHashTable.values()
							.iterator();
					/* 1158 */while (itr.hasNext())
					/*      */{
						/* 1160 */insertStatus = false;
						/*      */
						/* 1162 */PlanetDetailBean objBen = (PlanetDetailBean) itr
								.next();
						/*      */
						/* 1164 */if (objBen.getSignName().equals(
								rashiBean.getSignName()))
						/*      */{
							if (logger.isDebugEnabled())
								/* 1166 */logger
										.debug("Sign conjuction aries at planetName "
												+ objBen.getPlanetName()
												+ " and sign name "
												+ objBen.getSignName());
							/* 1167 */if (isForword)
							/*      */{
								/* 1169 */if (minusDegree <= Integer
										.parseInt(objBen.getDegree()
												.substring(0, 2).trim()))
									/* 1170 */insertStatus = true;
								if (logger.isDebugEnabled())
									/* 1171 */logger
											.debug("Is Forword flag is true ");
								/*      */}
							/* 1173 */if (isBackword)
							/*      */{
								/* 1175 */if (plusDegree <= Integer
										.parseInt(objBen.getDegree()
												.substring(0, 2).trim()))
									/* 1176 */insertStatus = true;
								if (logger.isDebugEnabled())
									/* 1177 */logger
											.debug("Is Backword flag is true ");
								/*      */}
							/*      */}
						/*      */
						/* 1181 */if (!insertStatus)
							/*      */continue;
						/* 1183 */planetList.append(objBen.getPlanetName());
						/* 1184 */planetList.append("_");
						/*      */}
					/*      */
					/*      */}
				/*      */else
				/*      */{
					if (logger.isDebugEnabled())
						/* 1191 */logger
								.debug("There is no any foword/backword planet for this sign "
										+ rashiBean.getSignNumber());
					/*      */}
				/*      */
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 1199 */ex.printStackTrace();
			/*      */}
		/*      */
		/* 1203 */return planetList.toString();
		/*      */}

	/*      */
	/*      */public void getPersonalDetails(AstroBean personInfo)
	/*      */throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	/*      */{
				if (logger.isDebugEnabled())
		/* 1210 */logger
				.debug("Inside function getPersonalDetails for getting client NAME/DOB/POB");
		/*      */try
		/*      */{
			/* 1213 */XPathFactory xFactory = XPathFactory.newInstance();
			/*      */
			/* 1216 */XPath xpath = xFactory.newXPath();
			/*      */
			/* 1220 */for (int loop = 0; loop < 3; loop++)
			/*      */{
				/* 1222 */if (loop == 0)
				/*      */{
					/* 1226 */this.expr = xpath
							.compile("/ASTROCHART/NAME/text()");
					/* 1227 */Object result = this.expr.evaluate(this.doc,
							XPathConstants.NODESET);
					/* 1228 */NodeList nodes = (NodeList) result;
					/*      */
					/* 1230 */personInfo.setName(this.personName.trim());
					if (logger.isDebugEnabled())
						/* 1231 */logger.debug("Name of User || "
								+ nodes.item(0).getNodeValue());
					/*      */}
				/* 1234 */else if (loop == 1)
				/*      */{
					/* 1238 */this.expr = xpath.compile("/ASTROCHART/DOB/@*");
					/* 1239 */Object result = this.expr.evaluate(this.doc,
							XPathConstants.NODESET);
					/* 1240 */NodeList nodes = (NodeList) result;
					/* 1241 */
					/*      */
					/* 1243 */Attr dobAttr = (Attr) nodes.item(0);
					/* 1244 */personInfo.setDOB(dobAttr.getValue());
					if (logger.isDebugEnabled())
						/* 1245 */logger.debug("DOB of User || "
								+ dobAttr.getValue());
					/*      */}
				/* 1249 */else if (loop == 2)
				/*      */{
					/* 1254 */this.expr = xpath.compile("/ASTROCHART/POB/@*");
					/* 1255 */Object result = this.expr.evaluate(this.doc,
							XPathConstants.NODESET);
					/* 1256 */NodeList nodes = (NodeList) result;
					/* 1257 */
					/*      */
					/* 1259 */Attr cityAttr = (Attr) nodes.item(0);
					/* 1260 */Attr countryAttr = (Attr) nodes.item(1);
					/* 1261 */Attr stateAttr = (Attr) nodes.item(2);
					/*      */
					/* 1263 */personInfo.setPOB(cityAttr.getValue());
					/*      */if (logger.isDebugEnabled())
						/* 1265 */logger.debug(" City  [  "
								+ cityAttr.getValue() + " ]  Country [ "
								+ countryAttr.getValue() + " ] State [ "
								+ stateAttr.getValue() + " ]");
					/*      */}
				/*      */else
				/*      */{
							  if (logger.isDebugEnabled())
					/* 1270 */logger.debug(" There is no any case match ");
					/*      */}
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 1277 */ex.printStackTrace();
			/*      */}
		/*      */}

	/*      */
	/*      */public void getAntardashaSookshmadasha(String fetchQuery,
			LinkedHashMap antardashaSookshmadasha)
	/*      */throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException
	/*      */{
		     if (logger.isDebugEnabled())
		     logger.debug("Inside function getAntardashaSookshmadasha for getting ANTARDASHA/SOOKSHMADASHA Fetch Query is || "
						+ fetchQuery);
		/*      */try
		/*      */{
			/* 1288 */XPathFactory xFactory = XPathFactory.newInstance();
			/*      */
			/* 1291 */XPath xpath = xFactory.newXPath();
			/*      */
			/* 1294 */String query = "";
			String previousYear = "0";
			String currentYear = "";
			/* 1295 */boolean first = true;
			/* 1296 */String[] antardashaArray = getAntarDashaSequence();
			/* 1297 */if (fetchQuery.equalsIgnoreCase("ANTARDASHA"))
			/*      */{
				/* 1300 */for (int loop = 0; loop < antardashaArray.length; loop++)
				/*      */{


						logger.info("antardashaArray value >> loop>>"+antardashaArray[loop] +" >> "+loop);
					/* 1303 */StringBuffer displayData = new StringBuffer();
					/* 1304 */displayData.append(antardashaArray[loop]);
					/* 1305 */query = "";
					/* 1306 */query = "/ASTROCHART/DASHAS/DASHA[@NAME='"
							+ antardashaArray[loop] + "']/ANTARDASHA/@*";
					/* 1307 */this.expr = xpath.compile(query);
					/* 1308 */Object result = this.expr.evaluate(this.doc,
							XPathConstants.NODESET);
					/*      */
					/* 1310 */NodeList nodes = (NodeList) result;
					/*      */
					/* 1312 */Vector data = new Vector();
					/*      */
					/* 1315 */for (int i = 0; i < 27;)
					/*      */{
						/* 1317 */MahaDashaBean obj = new MahaDashaBean();
						/* 1318 */Attr endAttr = (Attr) nodes.item(i);
						/* 1319 */Attr nameAttr = (Attr) nodes.item(i + 1);
						/* 1320 */Attr startAttr = (Attr) nodes.item(i + 2);
						/*      */
						/* 1325 */obj.setPlanetName(nameAttr.getValue().trim());
						/* 1326 */obj.setStartTime(startAttr.getValue().trim());
						/* 1327 */obj.setEndTime(endAttr.getValue().trim());
						/* 1328 */obj.setParent(antardashaArray[loop].trim());
						/* 1329 */data.add(obj);
						/* 1330 */i += 3;
						/*      */}
					/*      */
							String[] startDate = new String[3];
                                                	String[] startYear = new String[2];
                                                	String[] endDate = new String[3];
                                                	String[] endYear = new String[2];

					/* 1333 */	for (int l = 0; l < data.size(); l++)
					/*      */	{
						/* 1336 */	if (StringUtils.isBlank(((MahaDashaBean) data
								.get(l)).getStartTime())) {
							/*      */	continue;
							/*      */}
						/* 1339 */currentYear = calculateDateDifference(
								((MahaDashaBean) data.get(l)).getStartTime(),
								((MahaDashaBean) data.get(data.size() - 1)).getEndTime());
								startDate =(((MahaDashaBean) data.get(l)).getStartTime()).split("-");
                                                        	startYear = startDate[2].split("  ");
								endDate = (((MahaDashaBean) data.get(data.size() - 1)).getEndTime()).split("-");
                                                        	endYear = endDate[2].split("  ");
								int diff = (Integer.parseInt(endYear[0]))-(Integer.parseInt(startYear[0]));
								

						/*      */
						/* 1342 */if (first)
						/*      */{
							/* 1344 */
							/* 1345 */first = false;
							/* 1346 */displayData.append(" (");
							/* 1347 */displayData.append(currentYear.substring(
									0, currentYear.lastIndexOf("y")));
							/* 1348 */displayData.append("y)");
							/* 1349 */displayData.append("_");
							/* 1350 */displayData.append("From 0y to");
							/* 1351 */displayData.append(currentYear);
							/* 1352 */previousYear = currentYear;
							break;
							/*      */}
						/*      */
						/* 1363 */int sumOfYear = Integer.parseInt(previousYear
								.substring(0, previousYear.lastIndexOf("y")))
								+ Integer.parseInt(currentYear.substring(0,
										currentYear.lastIndexOf("y")));
						/*      */
						/* 1366 */displayData.append(" (");
						/* 1367 */
						//        displayData.append(currentYear.substring(0,currentYear.lastIndexOf("y")));
							  displayData.append(Integer.toString(diff));
						
						/* 1368 */displayData.append("y)");
						/* 1369 */displayData.append("_");
						/* 1370 */displayData.append("From ");
						/* 1371 */displayData.append(previousYear);
						/* 1372 */displayData.append(" to ");
						/* 1373 */displayData.append(sumOfYear);
						/* 1374 */displayData.append(currentYear
								.substring(currentYear.lastIndexOf("y")));
						/* 1375 */previousYear = Integer.toString(sumOfYear)
								+ currentYear.substring(currentYear
										.lastIndexOf("y"));
						/*      */
						/* 1381 */break;
						/*      */}
					/*      */
					/* 1386 */((MahaDashaBean) data.get(0)).setYear(displayData
							.toString());
					if (logger.isDebugEnabled())
					/* 1387 */logger.debug(" **** For Planet Name [ "
							+ antardashaArray[loop]
							+ " ] Display Year Period [ "
							+ displayData.toString() + " ] Previous Year [ "
							+ previousYear + " ] Current Year [ " + currentYear
							+ " ] Year Store in Bean [ "
							+ ((MahaDashaBean) data.get(0)).getYear() + " ]");
					/*      */
					/* 1389 */antardashaSookshmadasha.put(
							antardashaArray[loop].trim(), data);
					/*      */}
				/*      */
				/*      */}
			/* 1395 */else if (fetchQuery.equalsIgnoreCase("SOOKSHMADASHA"))
			/*      */{
				/* 1398 */boolean insertInHash = false;
				/* 1399 */for (int dasha = 0; dasha < this.planetName.length; dasha++)
				/*      */{
					/* 1403 */insertInHash = false;
					/* 1404 */for (int antardasha = 0; antardasha < this.planetName.length; antardasha++)
					/*      */{
						/* 1407 */insertInHash = false;
						/* 1408 */for (int pratyantardasha = 0; pratyantardasha < this.planetName.length; pratyantardasha++)
						/*      */{
							/* 1410 */insertInHash = false;
							/*      */
							/* 1412 */query = "";
							/* 1413 */query = "/ASTROCHART/DASHAS/DASHA[@NAME='"
									+ this.planetName[dasha]
									+ "']/ANTARDASHA[@NAME='"
									+ this.planetName[antardasha]
									+ "']/PRATYANTARDASHA[@NAME='"
									+ this.planetName[pratyantardasha]
									+ "']/SOOKSHMADASHA/@*";
							/* 1414 */this.expr = xpath.compile(query);
							/* 1415 */Object result = this.expr.evaluate(
									this.doc, XPathConstants.NODESET);
							/* 1416 */NodeList nodes = (NodeList) result;
							/*      */
							/* 1418 */Vector data = new Vector();
							/*      */
							/* 1420 */if (nodes.getLength() > 0)
							/*      */{
								/* 1422 */insertInHash = true;
								if (logger.isDebugEnabled())
									/* 1423 */logger
											.debug("My Insert Flag Is true  and executed query is ||  "
													+ query);
								/*      */}
							/*      */
							/* 1426 */for (int i = 0; i < nodes.getLength();)
							/*      */{
								/* 1428 */MahaDashaBean obj = new MahaDashaBean();
								/* 1429 */Attr endAttr = (Attr) nodes.item(i);
								/* 1430 */Attr nameAttr = (Attr) nodes
										.item(i + 1);
								/* 1431 */Attr startAttr = (Attr) nodes
										.item(i + 2);
								/*      */
								/* 1435 */obj.setPlanetName(nameAttr.getValue()
										.trim());
								/* 1436 */obj.setStartTime(startAttr.getValue()
										.trim());
								/* 1437 */obj.setEndTime(endAttr.getValue()
										.trim());
								/* 1438 */obj.setParent(this.planetName[dasha]
										.trim());
								/* 1439 */obj
										.setChild(this.planetName[antardasha]
												.trim());
								/* 1440 */obj
										.setSubChild(this.planetName[pratyantardasha]
												.trim());
								/*      */
								/* 1442 */data.add(obj);
								/*      */
								/* 1444 */i += 3;
								/*      */}
							/*      */
							/* 1448 */if (!insertInHash)
								/*      */continue;
							if (logger.isDebugEnabled())
								/* 1450 */logger
										.debug("Insert Into Hash Table Planets Keys || "
												+ ((MahaDashaBean) data.get(0))
														.getStartTime().trim());
							/* 1451 */antardashaSookshmadasha.put(
									((MahaDashaBean) data.get(0))
											.getStartTime().trim(), data);
							/*      */}
						/*      */
						/*      */}
					/*      */
					/*      */}
				/*      */if (logger.isDebugEnabled())
				/* 1458 */logger
						.debug("My hash table data  for SOOKSHMADASHA ||  "
								+ antardashaSookshmadasha.toString());
				/*      */}
			/*      */else
			/*      */{
						  if (logger.isDebugEnabled())
				/* 1462 */logger.debug("There is No any correct query || "
						+ fetchQuery);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 1470 */ex.printStackTrace();
			/*      */}
		/*      */}

	/*      */
	/*      */public void getVacantHouseList(Kundli cuspKundli,
			LinkedHashMap vacantHouseHashTable,
			LinkedHashMap planetDetailHashTable)
	/*      */{
		         if (logger.isDebugEnabled())
		/* 1479 */logger
				.debug("Inside Function getVacantHouseList For Getting Vacant HashTable ");
		/*      */
		/* 1482 */for (int planet = 0; planet < this.planetName.length; planet++)
		/*      */{
			/* 1484 */vacantHouseHashTable.put(this.planetName[planet],
					new StringBuffer());
			/*      */}
		/*      */
		/*      */try
		/*      */{
			/* 1489 */for (int houseNo = 1; houseNo <= 12; houseNo++)
			/*      */{
				if (logger.isDebugEnabled())
					/* 1491 */logger
							.debug("******************************Check For House No [ "
									+ houseNo
									+ " ] Value Of House [ "
									+ cuspKundli.getHouseData(houseNo)
									+ " ] ****************");
				/*      */
				/* 1494 */String[] houseDetail = cuspKundli.getHouseData(
						houseNo).split("_");
				/*      */
				/* 1496 */if (houseDetail.length != 1)
					/*      */continue;
				if (logger.isDebugEnabled())
					/* 1498 */logger.debug("My House Number [ "
							+ houseNo
							+ " ] is Vacant and Sign Number [ "
							+ houseDetail[0]
							+ " ] Planet Name For This Sign Number"
							+ Planet.fromOrdinal(Integer
									.parseInt(houseDetail[0])) + " ] ");
				/*      */
				/* 1501 */if (!StringUtils.isBlank(Planet.fromOrdinal(
						Integer.parseInt(houseDetail[0])).toString())) {
					/* 1502 */insertVacantHouseEntry(houseNo, Planet
							.fromOrdinal(Integer.parseInt(houseDetail[0]))
							.toString(), vacantHouseHashTable,
							planetDetailHashTable);
					/*      */}
				/*      */
				/*      */}
			/*      */if (logger.isDebugEnabled())
				/* 1507 */logger.debug(" VacantHouse HashTable  Values  ||  "
						+ vacantHouseHashTable.toString());
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 1512 */ex.printStackTrace();
			/*      */}
		/*      */}

	/*      */
	/*      */public void insertVacantHouseEntry(int houseNumber, String planetName,
			LinkedHashMap vacantHouseHashTable,
			LinkedHashMap planetDetailHashTable)
	/*      */{
				  if (logger.isDebugEnabled())
		/* 1518 */logger.debug("Inside Function insertVacantHouseEntry For Search Planet Name "
						+ planetName);
		/* 1519 */boolean checkForSL = true;
		/* 1520 */boolean checkForSS = true;
		/* 1521 */StringBuffer vacantHouseList = new StringBuffer();
		/*      */try
		/*      */{
			/* 1524 */Iterator nlIterator = planetDetailHashTable.values()
					.iterator();
			/*      */if (logger.isDebugEnabled())
				/* 1528 */logger.debug("Check For NL in Planets List");
			/* 1529 */while (nlIterator.hasNext())
			/*      */{
				/* 1531 */PlanetDetailBean planetBean = (PlanetDetailBean) nlIterator
						.next();
				/*      */
				/* 1535 */if ((planetBean.getPlanetName()
						.equalsIgnoreCase("Uranus"))
						|| (planetBean.getPlanetName()
								.equalsIgnoreCase("Neptune"))
						|| (planetBean.getPlanetName()
								.equalsIgnoreCase("Pluto"))
						|| (planetBean.getPlanetName()
								.equalsIgnoreCase("Lagna")))
				/*      */{
					if (logger.isDebugEnabled())
						/* 1538 */logger
								.debug("NL :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  "
										+ planetBean.getPlanetName() + " ]");
					/*      */}
				/*      */else
				/*      */{
					/* 1546 */if (!planetBean.getNL().equalsIgnoreCase(
							planetName))
						/*      */continue;
					/* 1548 */checkForSL = false;
					if (logger.isDebugEnabled())
						/* 1549 */logger.debug(" NL [ " + planetBean.getNL()
								+ " Planet Name [ "
								+ planetBean.getPlanetName() + " ]");
					/* 1550 */break;
					/*      */}
				/*      */
				/*      */}
			/*      */// checkForSL=false;//override by rips at 19-08-2013 as per
					// requirement of mahavstu (Mr. nitin) .There is no need of
					// SL che cking
			/* 1559 */if (checkForSL)
			/*      */{
				if (logger.isDebugEnabled())
					/* 1561 */logger
							.debug("Inside Check For SL in Planet List [Default Entry For Planet ] [ "
									+ planetName
									+ " ] House No. [ "
									+ houseNumber + " ]");
				/* 1562 */vacantHouseList.append(planetName);
				/* 1563 */vacantHouseList.append("-");
				/* 1564 */vacantHouseList.append(Integer.toString(houseNumber));
				/*      */
				/* 1566 */Iterator slIterator = planetDetailHashTable.values()
						.iterator();
				/*      */
				if (logger.isDebugEnabled())
					/* 1568 */logger.debug("Check For SL in Planets List");
				/* 1569 */while (slIterator.hasNext())
				/*      */{
					/* 1571 */PlanetDetailBean planetBean = (PlanetDetailBean) slIterator
							.next();
					/*      */if (logger.isDebugEnabled())
						/* 1573 */logger.debug(" SL [ " + planetBean.getSL()
								+ " ]  Planet Name [ "
								+ planetBean.getPlanetName() + " ]");
					/*      */
					/* 1575 */if ((planetBean.getPlanetName()
							.equalsIgnoreCase("Uranus"))
							|| (planetBean.getPlanetName()
									.equalsIgnoreCase("Neptune"))
							|| (planetBean.getPlanetName()
									.equalsIgnoreCase("Pluto"))
							|| (planetBean.getPlanetName()
									.equalsIgnoreCase("Lagna")))
					/*      */{
						if (logger.isDebugEnabled())
							/* 1578 */logger
									.debug("SL :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  "
											+ planetBean.getPlanetName() + " ]");
						/*      */}
					/*      */else
					/*      */{
						/* 1583 */if (!planetBean.getSL().equalsIgnoreCase(
								planetName))
							/*      */continue;
						/* 1585 */checkForSS = false;
						/* 1586 */// rips vacantHouseList.append("_");
						/* 1587 */// rips
									// vacantHouseList.append(planetBean.getPlanetName());
						/* 1588 */// rips vacantHouseList.append("-");
						/* 1589 */// rips
									// vacantHouseList.append(Integer.toString(houseNumber));
						/*      */}
					/*      */
					/*      */}
				/*      */
				if (logger.isDebugEnabled())
				/* 1595 */logger
						.debug("After SL iteration Vacant Planet Lists || "
								+ vacantHouseList.toString());
				/*      */}
			/*      */
			/* 1600 */if ((checkForSS) && (checkForSL))
			/*      */{
				if (logger.isDebugEnabled())
					/* 1602 */logger
							.debug("Inside Check For SS in Planet List  [ "
									+ planetName + " ] House No. [ "
									+ houseNumber + " ]");
				/*      */
				/* 1604 */Iterator ssIterator = planetDetailHashTable.values()
						.iterator();
				/*      */if (logger.isDebugEnabled())
					/* 1606 */logger.debug("Check For SL in Planets List");
				/* 1607 */while (ssIterator.hasNext())
				/*      */{
					/* 1609 */PlanetDetailBean planetBean = (PlanetDetailBean) ssIterator
							.next();
					/*      */
					/* 1611 */if ((planetBean.getPlanetName()
							.equalsIgnoreCase("Uranus"))
							|| (planetBean.getPlanetName()
									.equalsIgnoreCase("Neptune"))
							|| (planetBean.getPlanetName()
									.equalsIgnoreCase("Pluto"))
							|| (planetBean.getPlanetName()
									.equalsIgnoreCase("Lagna")))
					/*      */{
						if (logger.isDebugEnabled())
							/* 1613 */logger
									.debug("SS :: Inside Uranus/Neptune/Pluto flag is true  Planet Name  skip for planet [  "
											+ planetBean.getPlanetName() + " ]");
						/*      */}
					/*      */else
					/*      */{
						/* 1619 */if (!planetBean.getSS().equalsIgnoreCase(
								planetName))
						/*      */{
							/*      */continue;
							/*      */}
						/*      */
						/* 1624 */// rips vacantHouseList.append("_");
						/* 1625 */// rips
									// vacantHouseList.append(planetBean.getPlanetName());
						/* 1626 */// rips vacantHouseList.append("-");
						/* 1627 */// rips
									// vacantHouseList.append(Integer.toString(houseNumber));
						/*      */}
					/*      */
					/*      */}
				/*      */if (logger.isDebugEnabled())
					/* 1633 */logger
							.debug("After SS iteration Vacant Planet Lists || "
									+ vacantHouseList.toString());
				/*      */}
			/*      */
			/* 1638 */if (!StringUtils.isBlank(vacantHouseList.toString()))
			/*      */{
				if (logger.isDebugEnabled())
					/* 1640 */logger.debug("Put String Values in HashTable || "
							+ vacantHouseList.toString());
				/* 1641 */String[] outer = vacantHouseList.toString()
						.split("_");
				/* 1642 */for (String str : outer)
				/*      */{
					if (logger.isDebugEnabled())
						/* 1644 */logger
								.debug("Check For String Iteration ||||||| "
										+ str);
					/* 1645 */String[] inner = str.toString().split("-");
					/*      */
					/* 1648 */if (!vacantHouseHashTable.containsKey(inner[0]))
						/*      */continue;
					if (logger.isDebugEnabled())
						/* 1650 */logger.debug("Insert Data For Planet "
								+ inner[0]);
					/* 1651 */StringBuffer data = (StringBuffer) vacantHouseHashTable
							.get(inner[0]);
					/* 1652 */data.append(inner[1]);
					/* 1653 */data.append(",");
					/*      */}
				/*      */
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 1665 */ex.printStackTrace();
			/*      */}
		/*      */}

	/*      */
	/*      */public String getVacantHouse(String planetName)
	/*      */{
		/* 1671 */String vacanetHouse = null;
		/* 1672 */if (this.vacantHouseHashTable.containsKey(planetName))
		/*      */{
			/* 1674 */vacanetHouse = ((StringBuffer) this.vacantHouseHashTable
					.get(planetName)).toString();
			/* 1675 */if (!StringUtils.isBlank(vacanetHouse))
			/*      */{
				/* 1677 */if (vacanetHouse.endsWith(","))
				/*      */{
					/* 1679 */return vacanetHouse.substring(0,
							vacanetHouse.length() - 1);
					/*      */}
				/*      */
				/*      */}
			/*      */else
			/*      */{
				/* 1685 */return vacanetHouse;
				/*      */}
			/*      */}
		/*      */
		/* 1689 */return vacanetHouse;
		/*      */}

	/*      */
	/*      */public String[] getAntarDashaSequence()
	/*      */{
        if(logger.isDebugEnabled()){
        	logger.debug("Inside Function getAntarDashaSequence() function ");
        }
		/* 1701 */String[] planetArr = new String[9];
		/*      */try
		/*      */{
			/* 1705 */XPath xpath = XPathFactory.newInstance().newXPath();
			/* 1706 */XPathExpression expr = xpath.compile("//DASHAS/DASHA/@*");
			/* 1707 */Object result = expr.evaluate(this.doc,
					XPathConstants.NODESET);
			/* 1708 */NodeList nodes = (NodeList) result;
			/*      */
			/* 1710 */for (int i = 0; i < 9; i++)
			/*      */{
				/* 1712 */Attr endAttr = (Attr) nodes.item(i);
				/* 1713 */String str = endAttr.getValue();
				/* 1714 */planetArr[i] = str;
				/*      */}
			/*      */
			/*      */}
		/*      */catch (Exception e)
		/*      */{
			/* 1722 */e.printStackTrace();
			/*      */}
		/*      */
		/* 1725 */return planetArr;
		/*      */}

	/*      */
	/*      */public String calculateDateDifference(String startdate, String endDate)
	/*      */{
		if (logger.isDebugEnabled()){
		logger.debug("Inside calculateDateDifference() function for get two date difference Start/End || "
						+ startdate + "/" + endDate);
		}
		/* 1733 */StringBuffer myYear = new StringBuffer();
		/*      */try
		/*      */{
			/* 1736 */SimpleDateFormat xmlFormat = new SimpleDateFormat(
					"dd-MM-yyyy");
			/* 1737 */Date st = xmlFormat.parse(startdate);
			/* 1738 */Date ed = xmlFormat.parse(endDate);
			/*      */
			/* 1744 */double diffInMonth = (ed.getTime() - st.getTime()) / 2629728000.0D;
			/*      */
			/* 1748 */myYear.append(Integer.toString((int) diffInMonth / 12));
			/* 1749 */myYear.append("y");
			/* 1750 */myYear.append(Integer.toString((int) diffInMonth % 12));
			/* 1751 */myYear.append("m");
			/*      */}
		/*      */catch (Exception ex)
		/*      */{
			/* 1756 */ex.printStackTrace();
			/*      */}
		// /* 1758 */ logger.info(" My Year Value || " + myYear.toString());
		/* 1759 */return myYear.toString();
		/*      */}
	/*      */
}

/*
 * Location: C:\Users\SUCCESS\Downloads\kundli.jar Qualified Name:
 * com.telemune.astro.GenerateBirthCuspKundli JD-Core Version: 0.6.0
 */

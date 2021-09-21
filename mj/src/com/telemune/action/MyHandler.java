package com.telemune.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.model.AstroBean;
import com.telemune.model.GenerateKundli;
import com.telemune.util.Constants;

public class MyHandler {

	private static Logger logger = Logger.getLogger(MyHandler.class);
	private Hashtable<String, String> properties = null;
	private String proPath = "";
	private String fileName = "";
	private String file = "";
	String dob = "";
	private boolean sendflag = false;;

	public MyHandler() {
		logger.info("Inside constructor of MyHandler action class.");
		proPath = Constants.PROPERTIES_PATH;
		proPath = proPath + "/kundliHttpserverNew.properties";
		properties = ReadPropertyFile.readPropery(proPath);
	}

	public AstroBean msgExtraction(GenerateKundli generateKundli, boolean sflag) {
		logger.info("Inside msgExtraction method of MyHandler action class.");

		try {
			sendflag = sflag;
			logger.info("Kundli data " + generateKundli.toString());
			String kundliUrl = getFinalUrl(generateKundli);
			String fileName = getFilePath(generateKundli.getName());

			String flag = properties.get("FLAG").trim();
			if (logger.isDebugEnabled()) {
				logger.debug("flag " + flag);
			}

			AstroBean astrobean = createPDF(kundliUrl, generateKundli, fileName, flag);
			if (logger.isDebugEnabled()) {
				logger.debug("AFTER  #######################BirthData" + astrobean.getNatalStrengthChart() + "  "
						+ astrobean.getRahuSource() + "  " + astrobean.getAspect(Planet.toPlanets("Rahu")) + "  "
						+ astrobean.getPlanetDetailHashTable());
			}
			return astrobean;
		} catch (Exception e) {
			logger.error(
					"Inside catch block. Exception at msgExtraction method of MyHandler action Class. Exception  : "
							+ e.toString());
			e.printStackTrace();
			return null;
		}

	}

	public String getFinalUrl(GenerateKundli generateKundli) {
		logger.info("Inside getFinalUrl method of MyHandler action class.");
		String astroURL = null;
		try {
			astroURL = properties.get("ASTRO_URL").trim();

			astroURL = astroURL + generateKundli.getName() + ",m," + "1" + "," + generateKundli.getDob() + ","
					+ generateKundli.getTob() + "," + generateKundli.getCityCode() + "," + generateKundli.getCity()
					+ "," + generateKundli.getState() + "," + generateKundli.getCountry();

			logger.info("Astro link " + astroURL);

		} catch (Exception e) {
			logger.error("Inside catch block. Exception at getFinalUrl method of MyHandler action Class. Exception  : "
					+ e.toString());
			e.printStackTrace();
		}
		return astroURL;

	}

	public String getFilePath(String astroName) {
		logger.info("Inside getFilePath method of MyHandler action class.");
		String ddate = "";
		Date date = new java.util.Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
		String path = System.getenv("CATALINA_HOME");
		path = null;

		// Dead Code ( need to review )
		if (path != null && path != "") {
			file = path + "/" + astroName.replaceAll("\"", "").replaceAll(" ", "_");
		} else {
			file = properties.get("PDF_FILE_PATH").trim() + "/" + astroName.replaceAll("\"", "").replaceAll(" ", "_");
		}
		ddate = dateFormat.format(date).replaceAll(" ", "");
		file = file + ddate + ".pdf";
		String fileDir = "" + file;
		fileName = fileDir;
		if (logger.isDebugEnabled()) {
			logger.debug("File Name is" + fileName);
		}
		return fileName;
	}

	public AstroBean createPDF(String url, GenerateKundli generateKundli, String filePath, String flag) {
		logger.info("Inside createPDF method of MyHandler action class.");
		AstroBean astrobean = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.info("The value in the flag is" + flag);
			}

			/*---------------------------------------------------- Astro Kundli Generation ------------------------------------------------------*/
			GenerateBirthCuspKundli displayNatalStr = new GenerateBirthCuspKundli(url); // URL
																						// String
			logger.info("DATATATATATA>> " + displayNatalStr);
			astrobean = displayNatalStr.getNatalStrength();
			astrobean.setDOB1(generateKundli.getDob());
			if (logger.isDebugEnabled()) {
				logger.debug("In the Test kundli" + astrobean.getName());
			}
			String dashaPlanetName = "CURRENT";
			GenerateDashaKundli displayDashaStrength = new GenerateDashaKundli(astrobean, dashaPlanetName);
			astrobean = displayDashaStrength.getDashaStrength();
			String currentPlanet = "";
			Vector<DashaStrength> obj = astrobean.getDashaStrength();

			if (obj.size() != 0) {
				for (int k = 0; k < obj.size(); k++) {
					DashaStrength dashaBean = (DashaStrength) obj.get(k);
					if (dashaBean.getCurrentFlag()) {
						currentPlanet = dashaBean.getDashaPlanet();
						if (logger.isDebugEnabled()) {
							logger.debug("Current palnet name is" + dashaBean.getDashaPlanet());
						}
					}
				}
			}

			if (!(flag.equalsIgnoreCase("c"))) {

				Planet[] planet = Planet.values();
				for (int j = 0; j < planet.length - 1; j++) {
					String planetname = planet[j].toString();
					if (logger.isDebugEnabled()) {
						logger.debug("*****************************Planet array is********************" + planetname);
					}

					if (!StringUtils.equalsIgnoreCase(planetname, currentPlanet)) {
						GenerateDashaKundli displayDashaStrength1 = new GenerateDashaKundli(astrobean, planetname);
						astrobean = displayDashaStrength1.getDashaStrength();

					}
				}
			}

			else {
				int j;
				int temp;
				String planetname = "";
				Planet[] planet = Planet.values();
				String[] planetArr = new String[2];
				for (j = 0; j < planet.length - 1; j++) {
					planetname = planet[j].toString();
					logger.info("*****************************Planet array is********************" + planetname);

					if (StringUtils.equalsIgnoreCase(planetname, currentPlanet)) {
						break;
					}
				}
				logger.info("=====   j is " + j);
				temp = j;

				if (j == 0)
					j = 8;
				else
					j = j - 1;
				if (logger.isDebugEnabled()) {
					logger.debug("...=====   j is " + j);
					logger.debug("*********Planet name in Next case is**********[" + planet[j].toString() + "]");
				}

				planetArr[0] = planet[j].toString(); // For Calculating Next
														// Planet

				if (temp == planet.length - 2) {
					j = temp;
					j = 0;
				} else {
					j = temp;
					j = j + 1;
				}
				if (logger.isDebugEnabled()) {
					logger.debug("=====.....   j is " + j);
				}

				// planetArr[0]planetname);
				if (logger.isDebugEnabled()) {
					logger.debug("*********Planet name in Previous case is**********[" + planet[j].toString() + "]");
				}
				planetArr[1] = planet[j].toString(); // For Calculating previous
														// Planet

				for (int i = 0; i < planetArr.length; i++) {

					GenerateDashaKundli displayDashaStrength1 = new GenerateDashaKundli(astrobean, planetArr[i]);
					astrobean = displayDashaStrength1.getDashaStrength();
				}

			}

			HouseDetail houseDetails = new HouseDetail();
			houseDetails.getHouseDetails(astrobean);
			setAspectHouseDetails(astrobean);
			setAspectPlanetDetails(astrobean);
			Set<String> keys = astrobean.getCuspHouseAspectDetails().keySet();

			if (!generateKundli.getGenerateType().equalsIgnoreCase("D")) {
				FinalKundli finalkundli = new FinalKundli();
				astrobean.setFileName(file);
				astrobean.setPOB(generateKundli.getCity());
				logger.info("KundliCircleName>> " + generateKundli.getKundliCircleName());
				// finalkundli.Kundli(astrobean, file, generateKundli.isDasha(),
				// generateKundli.isHouseDetail(),generateKundli.isCircle(),properties.get("CIRCLE_IMAGE_PATH").trim()+generateKundli.getUserId()+"_post.jpg",properties.get("CIRCLE_IMAGE_PATH").trim()+"list"+generateKundli.getUserId()+"_post.jpg",generateKundli.isAspectChart(),generateKundli.isNakshatraPadam());
				finalkundli.Kundli(astrobean, file, generateKundli.isDasha(), generateKundli.isHouseDetail(),
						generateKundli.isCircle(),
						properties.get("CIRCLE_IMAGE_PATH").trim() + generateKundli.getKundliCircleName(),
						properties.get("CIRCLE_IMAGE_PATH").trim() + "list" + generateKundli.getUserId() + "_post.jpg",
						generateKundli.isAspectChart(), generateKundli.isNakshatraPadam());
				/*---------------------------------------------------- Mail Sending ------------------------------------------------------*/

				String[] mailFileDir = { file }; // for attachment
				String[] mailTo = { generateKundli.getEmail() }; // Recipient
				String mailSubject = "MahaJyotish Kundli  " + generateKundli.getName();

				if (sendflag) {
					KundliMail kundliMail = new KundliMail();
					kundliMail.kundliMailSending(mailSubject, mailTo, mailFileDir, generateKundli.getName());
				}

				/*---------------------------------------------------- Generated PDF File Deletion ------------------------------------------------------*/
				if (logger.isDebugEnabled()) {
					logger.debug("PDF File Dir  :" + filePath);
				}
			}

		} catch (Exception ex) {

			logger.error("Inside catch block. Exception at createPDF method of MyHandler action Class. Exception  : "
					+ ex.toString());
			ex.printStackTrace();
		}
		return astrobean;
	}

	public void sendMessage(String msisdn, String message, Connection con) {
		logger.info("Inside sendMessage method of MyHandler action class.");
		if (con != null) {
			logger.info("Message sending service is currently deactivated......");
			logger.info("msisdn number :" + msisdn + " and message : " + message);

			try {
				// query="insert into gmat_message_store
				// values(1,1,'+918377039276+',\'"+msisdn+"\',\'"+message+"\',sysdate,1,1,'1',1,1,1,1,1,'R',sysdate+1,1,'1',1)";
				String query = "insert into gmat_message_store (RESPONSE_ID,REQUEST_ID,ORIGINATING_NUMBER,DESTINATION_NUMBER,MESSAGE_TEXT,SUBMIT_TIME,MESSAGE_TYPE,LANGUAGE,UDH,STATUS_REPORT,DATA_CODING_SCHEME,PROTOCOL_IDENTIFIER,PRIORITY,CHARGING_CODE,STATUS,VALIDITY_PERIOD,MESSAGE_ID,INTERFACE_ID,INTERFACE_TYPE) values (gmat_response_id_seq.nextval,gmat_response_id_seq.nextval,?,?,?,sysdate,1,1,'1',1,1,1,1,1,?,1,1,'1',1)";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, "918377039276");
				pstmt.setString(2, msisdn);
				pstmt.setString(3, message);
				pstmt.setString(4, "R");

				pstmt.executeUpdate();
				pstmt.close();
				logger.info("Message Sent To :" + msisdn);
			} catch (Exception e) {

				e.printStackTrace();
				logger.error(" GMATE ERROR : " + e.toString());
			}
		} else {
			logger.info("********************Unable To get GMAT CONNECTION");
		}
	}

	public void setAspectHouseDetails(AstroBean astroBean) {
		logger.info("Inside setAspectHouseDetails method of MyHandler action class.");
		int count = 1;
		try {
			String[] aspectHouse = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
			String planetNme[] = { "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury" };
			String astroTable = "<table id=" + '"' + "dataTable" + '"' + " width=" + '"' + "100%" + '"' + " border="
					+ '"' + "0" + '"' + " cellspacing=" + '"' + "0" + '"' + " cellpadding=" + '"' + "0" + '"' + "class="
					+ '"' + "ui-table ui-responsive" + '"' + "><thead><tr>";
			astroTable = astroTable + "<th " + "scope=" + '"' + "col" + '"' + " width=" + "'" + "8%" + "'" + ">"
					+ "Planet" + "</th>";
			for (int i = 0; i < aspectHouse.length; i++) {
				astroTable = astroTable + "<th " + "scope=" + '"' + "col" + '"' + " width=" + "'" + "8%" + "' id=house0"
						+ aspectHouse[i] + ">" + aspectHouse[i] + "</th>";
			}
			astroTable = astroTable + "</tr></thead>";
			astroTable = astroTable + "<tbody>";
			for (int j = 0; j < planetNme.length; j++) {
				if (!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")) {
					astroTable = astroTable + "<tr>";
					astroTable = astroTable + "<td id=tdHouseAspect" + count + "0>" + planetNme[j] + "</td>";
					for (int num = 0; num < aspectHouse.length; num++) {
						HashMap<String, String> hashMap = astroBean.getCuspHouseAspectDetails().get(aspectHouse[num]);
						if (hashMap.get(planetNme[j]) != null) {
							astroTable = astroTable + "<td id=tdHouseAspect" + count + aspectHouse[num] + ">"
									+ hashMap.get(planetNme[j]) + "</td>";
						} else {
							astroTable = astroTable + "<td id=tdHouseAspect" + count + aspectHouse[num] + "></td>";
						}
					}
					astroTable = astroTable + "</tr>";
					count++;
				}
			}
			astroTable = astroTable + "</tbody></table>";
			astroBean.setAstroHouseTable(astroTable);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setAspectPlanetDetails(AstroBean astroBean) {
		logger.info("Inside setAspectPlanetDetails method of MyHandler action class.");
		int count = 2;
		String ketuAspect = "";
		String rahuAspect = "";

		try {
			String planetNme[] = { "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury" };
			String astroTable = "<table id=" + '"' + "dataTable" + '"' + " width=" + '"' + "100%" + '"' + " border="
					+ '"' + "0" + '"' + " cellspacing=" + '"' + "0" + '"' + " cellpadding=" + '"' + "0" + '"' + "class="
					+ '"' + "ui-table ui-responsive" + '"' + "><thead><tr>";
			astroTable = astroTable + "<th " + "scope=" + '"' + "col" + '"' + " width=" + "'" + "10%" + "'" + ">"
					+ "Planet" + "</th>";
			for (int i = 0; i < planetNme.length; i++) {
				astroTable = astroTable + "<th " + "scope=" + '"' + "col" + '"' + " width=" + "'" + "10%" + "'"
						+ " id=planet1" + planetNme[i] + ">" + planetNme[i] + "</th>";
			}
			astroTable = astroTable + "</tr></thead>";
			astroTable = astroTable + "<tbody>";
			for (int j = 0; j < planetNme.length; j++) {
				if (!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")) {
					astroTable = astroTable + "<tr>";
					astroTable = astroTable + "<td id=tdPlanetAspect" + count + "0>" + planetNme[j] + "</td>";
				}
				for (int num = 0; num < planetNme.length; num++) {
					HashMap<String, String> hashMap = astroBean.getPlanetHouseAspectDetails().get(planetNme[num]);
					if (hashMap.get(planetNme[j]) != null) {
						if (num == 0) {
							if (!planetNme[j].equals("Rahu")) {
								ketuAspect = ketuAspect + planetNme[j] + "(" + hashMap.get(planetNme[j]) + ")" + " ";
							}
						}
						if (num == 5) {
							if (!planetNme[j].equals("Ketu")) {
								rahuAspect = rahuAspect + planetNme[j] + "(" + hashMap.get(planetNme[j]) + ")" + " ";
							}
						}
						if (!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")) {
							astroTable = astroTable + "<td id=tdPlanetAspect" + count + planetNme[num] + ">"
									+ hashMap.get(planetNme[j]) + "</td>";
						}
					} else {
						if (!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")) {
							astroTable = astroTable + "<td id=tdPlanetAspect" + count + planetNme[num] + "></td>";
						}
					}
				}
				if (!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")) {
					astroTable = astroTable + "</tr>";
					count++;
				}
			}
			astroTable = astroTable + "</tbody></table>";
			astroBean.setAstroPlanetTable(astroTable);
			if (ketuAspect.isEmpty()) {
				ketuAspect = "NA";
			}
			if (rahuAspect.isEmpty()) {
				rahuAspect = "NA";
			}
			astroBean.setAspectRahu(rahuAspect);
			astroBean.setAspectKetu(ketuAspect);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

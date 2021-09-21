package com.telemune.mobileAstro;



/*import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
*/
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.model.GenerateKundli;
import com.telemune.util.Constants;
public class MyHandler {

	private static Logger logger = Logger.getLogger("MyHandler");
	private Hashtable<String, String> properties = null;
	private String proPath = "";
	private String fileName = "";
	private String file = "";
	private String dob = "";
	private boolean sendflag = false;
	private boolean isNakshatraKB = false;
	private Map<String, NakshatraKB> nakshatraKB = null;
	private String[] nakshatraArray = {"Ashwini","Bharani","Krittika","Rohini","Mrigashira","Ardra","Punarvasu","Pushya","Ashlesha","Magha","Purva Phalg.","Uttara Phalg.","Hasta","Chitra","Swati","Vishakha","Anuradha","Jyeshtha","Moola","Purvashadha","Uttarashadha","Shravana","Dhanishta","Shatabhisha","Purvabhadra","Uttarabhadra","Revati"};

	public MyHandler() {
		proPath = Constants.PROPERTIES_PATH;
		proPath = proPath + "/kundliHttpserverNew.properties";
		//proPath = proPath + "/kundliHttpserverTest.properties";
		properties = ReadPropertyFile.readPropery(proPath);
	}

	public AstroBean msgExtraction(GenerateKundli generateKundli, boolean sflag, boolean nakshatraFlag) {
		isNakshatraKB=nakshatraFlag;
		sendflag = sflag;
		logger.info("Kundli data " + generateKundli.toString());
		String kundliUrl = getFinalUrl(generateKundli);
		String fileName = getFilePath(generateKundli.getName());
		/*String subMailSubject = generateKundli.getName() + "," + dob + ","
				+ generateKundli.getTob() + "," + generateKundli.getCity();
		if (logger.isDebugEnabled()) {
			logger.debug("Mailing subject " + subMailSubject);
		}*/

		String flag = properties.get("FLAG").trim();
		if (logger.isDebugEnabled()) {
			logger.debug("flag " + flag);
		}

//		AstroBean astrobean = createPDF(kundliUrl, generateKundli.getEmail(),
//				fileName, generateKundli.getName(), flag, generateKundli.getDob(), generateKundli.getTob(),
//				generateKundli.isDasha(), generateKundli.isHouseDetail(),
//				generateKundli.getGenerateType(), generateKundli.getCity(),generateKundli.getUserId());


		AstroBean astrobean = createPDF(kundliUrl,generateKundli,fileName,flag);
/*		String jsonStr= new Gson().toJson(astrobean);
		UserProfileDaoImpl userDao=new UserProfileDaoImpl();
		int result=userDao.addkundliData(generateKundli,jsonStr);
*/		
			
		if (logger.isDebugEnabled()) {
			logger.debug("AFTER  #######################BirthData"
					+ astrobean.getNatalStrengthChart() + "  "
					+ astrobean.getRahuSource() + "  "
					+ astrobean.getAspect(Planet.toPlanets("Rahu")) + "  "
					+ astrobean.getPlanetDetailHashTable());
		}
		
		return astrobean;
	}
       

	public String getFinalUrl(GenerateKundli generateKundli) {
		String astroURL = null;
		try {
			astroURL = properties.get("ASTRO_URL").trim();
			if(generateKundli.getIsTransitKundli()==1 && generateKundli.getTransitLocation()==0 && generateKundli.isTranistKundli()) 
			{
				logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				astroURL = astroURL + generateKundli.getName() + ",m," + "1" + "," + generateKundli.getTransitDob() + ","
						+ generateKundli.getTransitTob() + "," + generateKundli.getCity() + "," + generateKundli.getState() + ","
						+ generateKundli.getCountry() + "," + generateKundli.getLongitude() + ","
						+ generateKundli.getLatitude();
			}
			else if(generateKundli.getTransitLocation()==1 && generateKundli.isTranistKundli())
			{
				logger.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
				astroURL = astroURL + generateKundli.getName() + ",m," + "1" + "," + generateKundli.getTransitDob() + ","
						+ generateKundli.getTransitTob() + "," + generateKundli.getTransitCity() + "," + generateKundli.getTransitState() + ","
						+ generateKundli.getTransitCountry() + "," + generateKundli.getTransitLongitude() + ","
						+ generateKundli.getTransitLatitude();
			}
			else 
			{
			   logger.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
			   astroURL = astroURL + generateKundli.getName() + ",m," + "1" + ","
                                        + generateKundli.getDob() + ","
                                        + generateKundli.getTob()+","
                                        + generateKundli.getCity() + ","
                                        + generateKundli.getState() + ","
                                        + generateKundli.getCountry()+","
                                        + generateKundli.getLongitude()+","
                                        + generateKundli.getLatitude();
			}
			generateKundli.setTranistKundli(false);
			logger.info("Astro link to hit parasharlight server to generate kundli" + astroURL);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return astroURL;

	}

	public String getFilePath(String astroName) {
		String ddate = "";
		Date date = new java.util.Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
		String path = System.getenv("CATALINA_HOME");
		path = null;
		if (path != null && path != "") {
			file = path + "/"
					+ astroName.replaceAll("\"", "").replaceAll(" ", "_");
		} else {
			/*
			 * file = properties.get("PDF_FILE_PATH").trim() + "/" +
			 * astroName.replaceAll("\"", "").replaceAll(" ", "_");
			 */
			file = properties.get("PDF_FILE_PATH").trim()
					+ astroName.replaceAll("\"", "").replaceAll(" ", "_");
		}
		ddate = dateFormat.format(date).replaceAll(" ", "");
		file = file + ddate + ".pdf";
		String fileDir = "" + file;
		fileName = fileDir;
		if (logger.isDebugEnabled()) {
			logger.debug("File Name is===>" + fileName);
		}
		return fileName;
	}


		public AstroBean createPDF(String url,GenerateKundli generateKundli,String filePath,String flag)
	/*415*/	{
		AstroBean astrobean = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.info("The value in the flag is" + flag);
			}
			// char i=flag;

			/*---------------------------------------------------- Astro Kundli Generation ------------------------------------------------------*/
			GenerateBirthCuspKundli displayNatalStr = new GenerateBirthCuspKundli(url); // URL String
			logger.info("DATATATATATA>> "+displayNatalStr);
			astrobean = displayNatalStr.getNatalStrength();
			astrobean.setDOB1(generateKundli.getDob());
			// astrobean.setTOB(tob);
		//	astrobean.setTOB(generateKundli.getTob());
			if (logger.isDebugEnabled()) {
				logger.debug("In the Test kundli" + astrobean.getName());
			}
			String dashaPlanetName = "CURRENT";
			GenerateDashaKundli displayDashaStrength = new GenerateDashaKundli(
					astrobean, dashaPlanetName);
			astrobean = displayDashaStrength.getDashaStrength();
			String currentPlanet = "";
			Vector<DashaStrength> obj = astrobean.getDashaStrength();

			if (obj.size() != 0) {
				for (int k = 0; k < obj.size(); k++) {
					DashaStrength dashaBean = (DashaStrength) obj.get(k);
					if (dashaBean.getCurrentFlag()) {
						currentPlanet = dashaBean.getDashaPlanet();
						if (logger.isDebugEnabled()) {
							logger.debug("Current planet name is"
									+ dashaBean.getDashaPlanet());
						}
					}
				}
			}

			if (!(flag.equalsIgnoreCase("c"))) {

				Planet[] planet = Planet.values();
				for (int j = 0; j < planet.length - 1; j++) {
					String planetname = planet[j].toString();
					if (logger.isDebugEnabled()) {
						logger.debug("*****************************Planet array is********************"
								+ planetname);
					}

					if (!StringUtils
							.equalsIgnoreCase(planetname, currentPlanet)) {
						GenerateDashaKundli displayDashaStrength1 = new GenerateDashaKundli(
								astrobean, planetname);
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
					logger.info("*****************************Planet array is********************"
							+ planetname);

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
					logger.debug("*********Planet name in Next case is**********["
							+ planet[j].toString() + "]");
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
					logger.debug("*********Planet name in Previous case is**********["
							+ planet[j].toString() + "]");
				}
				planetArr[1] = planet[j].toString(); // For Calculating previous
														// Planet

				for (int i = 0; i < planetArr.length; i++) {

					GenerateDashaKundli displayDashaStrength1 = new GenerateDashaKundli(
							astrobean, planetArr[i]);
					astrobean = displayDashaStrength1.getDashaStrength();
				}

			}

				HouseDetail houseDetails = new HouseDetail();
                                houseDetails.getHouseDetails(astrobean);

				HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
				fillAspectScoringMap(scoreMap);
			
				
                                setAspectHouseDetails(astrobean,scoreMap,generateKundli.isAspectScoring());
                                setAspectPlanetDetails(astrobean,scoreMap,generateKundli.isAspectScoring());
			
                                Set<String> keys =  astrobean.getCuspHouseAspectDetails().keySet();
//==============================================  NAKSHATRA_KB       ADDED BY BHARTI ON 03/08/2018================>>

			logger.info("isNakshatraKB>> " + isNakshatraKB);

			if (isNakshatraKB) {
				nakshatraKB = new HashMap<String, NakshatraKB>();
				fillNakshatraKBMap(nakshatraKB);
				logger.info("nakshatraKB size> " + nakshatraKB.size());
				LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable = astrobean.getPlanetDetailHashTable();
				LinkedHashMap<String, HouseDetailBean> houseDetailHashTable = astrobean.getHouseDetailHashTable();
				LinkedHashMap<String, String> tempPlanetDetailHashTable = new LinkedHashMap<String, String>();
				LinkedHashMap<String, String> tempHouseDetailHashTable = new LinkedHashMap<String, String>();

//========================FOR PLANETS==========================================>>
				String padamVal="";
				

                               	for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet())
                                {
					for (Map.Entry<String, NakshatraKB> entry1 : nakshatraKB.entrySet())
                              		{
						if(entry1.getValue().getSignName().equalsIgnoreCase(entry.getValue().getSignName()))
                                      		{
							if(convertTimeInDegree(entry.getValue().getDegree())>=entry1.getValue().getStartDeg() && convertTimeInDegree(entry.getValue().getDegree())<=entry1.getValue().getEndDeg()){
								int padam=0;
							//here padam calculation
				logger.info("Planet > "+entry.getKey()+"    ,Nakshatra>> "+entry1.getKey()+"     ,Padam Val> "+entry1.getValue().getPadam());
								String[] padamValArr=entry1.getValue().getPadam().split("_");
				logger.info("LENGTH padamValArr>  "+padamValArr.length);
								double tmpVal=3.2;
								double tempStartDeg=3.2;
								for(int i=1;i<=padamValArr.length;i++)	
								{
									if(i>1){
										tmpVal=convertDegree(tmpVal)+3.2;
									}
									//tempStartDeg=entry1.getValue().getStartDeg()+convertDegree(tmpVal);
									tempStartDeg=entry1.getValue().getStartDeg()+tmpVal;
									tempStartDeg=convertDegree(tempStartDeg);									
	
									logger.info("tempStartDeg> "+tempStartDeg);
									if(convertTimeInDegree(entry.getValue().getDegree())>=entry1.getValue().getStartDeg() && convertTimeInDegree(entry.getValue().getDegree())<=tempStartDeg){
										padam=Integer.valueOf(padamValArr[i-1]);
										break;
									}
								}
								logger.info("padam>> "+padam);

							//padam calculation ends here

								tempPlanetDetailHashTable.put(entry.getKey(),entry1.getKey().split("_")[0]+"_"+entry1.getValue().getKeyword()+"_"+entry1.getValue().getNL()+"_"+padam);	
			
							}
						}	
					}
				}
				logger.info("Size of tempPlanetDetailHashTable>> "+tempPlanetDetailHashTable.size());
				PlanetDetailBean planetDetailBean=null;
				for (Map.Entry<String, String> entry : tempPlanetDetailHashTable.entrySet())
                                {
					if(planetDetailHashTable.containsKey(entry.getKey())){
						planetDetailBean=planetDetailHashTable.get(entry.getKey());
						planetDetailBean.setNakshatra(entry.getValue().split("_")[0]);
						planetDetailBean.setKeyword(entry.getValue().split("_")[1]);
                                                planetDetailBean.setNL(entry.getValue().split("_")[2]);
                                                planetDetailBean.setPadam(entry.getValue().split("_")[3]);
						planetDetailHashTable.put(entry.getKey(),planetDetailBean);	
					}	
				}
//FOR HOUSE=====================================================

					
				for (Map.Entry<String, HouseDetailBean> entry : houseDetailHashTable.entrySet())
                                {
					logger.debug("key House: "+entry.getKey()+"   value: "+entry.getValue());
                                        for (Map.Entry<String, NakshatraKB> entry1 : nakshatraKB.entrySet())
                                        {
                                                if(entry1.getValue().getSignName().equalsIgnoreCase(entry.getValue().getSignName()))
                                                {
                                                        if(convertTimeInDegree(entry.getValue().getDegree())>=entry1.getValue().getStartDeg() && convertTimeInDegree(entry.getValue().getDegree())<=entry1.getValue().getEndDeg()){
                                                                tempHouseDetailHashTable.put(entry.getKey(),entry1.getKey().split("_")[0]+"_"+entry1.getValue().getKeyword()+"_"+entry1.getValue().getNL());

                                                        }
                                                }
                                        }
                                }
                                logger.info("Size of tempHouseDetailHashTable>> "+tempHouseDetailHashTable.size());
                                HouseDetailBean houseDetailBean=null;
                                for (Map.Entry<String, String> entry : tempHouseDetailHashTable.entrySet())
                                {
                                        if(houseDetailHashTable.containsKey(entry.getKey()))
                                        {
                                           houseDetailBean=houseDetailHashTable.get(entry.getKey());
                                           houseDetailBean.setNakshatra(entry.getValue().split("_")[0]);
                                           houseDetailBean.setKeyword(entry.getValue().split("_")[1]);
                                           houseDetailBean.setNL(entry.getValue().split("_")[2]);
                                           houseDetailHashTable.put(entry.getKey(),houseDetailBean);
                                        }
                                }

				}

//============================================  NAKSHATRA_KB ENDS HERE====================================================>>

		/*	if (!generateKundli.getGenerateType().equalsIgnoreCase("D")) {
				FinalKundli finalkundli = new FinalKundli();
				astrobean.setFileName(file);
				astrobean.setPOB(generateKundli.getCity());
				logger.info("KundliCircleName>> "+generateKundli.getKundliCircleName());
			//	finalkundli.Kundli(astrobean, file, generateKundli.isDasha(), generateKundli.isHouseDetail(),generateKundli.isCircle(),properties.get("CIRCLE_IMAGE_PATH").trim()+generateKundli.getUserId()+"_post.jpg",properties.get("CIRCLE_IMAGE_PATH").trim()+"list"+generateKundli.getUserId()+"_post.jpg",generateKundli.isAspectChart(),generateKundli.isNakshatraPadam());
				finalkundli.Kundli(astrobean, file, generateKundli.isDasha(), generateKundli.isHouseDetail(),generateKundli.isCircle(),properties.get("CIRCLE_IMAGE_PATH").trim()+generateKundli.getKundliCircleName(),properties.get("CIRCLE_IMAGE_PATH").trim()+"list"+generateKundli.getUserId()+"_post.jpg",generateKundli.isAspectChart(),generateKundli.isNakshatraPadam(),generateKundli.isCuspName(),generateKundli.isAspectScoring());
				//---------------------------------------------------- Mail Sending ------------------------------------------------------

				String[] mailFileDir = { file }; // for attachment
				String[] mailTo = { generateKundli.getEmail() }; // Recipient
				String mailSubject = "MahaJyotish Kundli  "+generateKundli.getName();

				if (sendflag)
                                {
                                     KundliMail kundliMail = new KundliMail();
                                     kundliMail.kundliMailSending(mailSubject, mailTo, mailFileDir,generateKundli.getName());
                                } 

				if (logger.isDebugEnabled()) {
					logger.debug("PDF File Dir  :" + filePath);
				}
			}*/

		} catch (Exception ex) {

			logger.error("Exception " + ex.toString());
			ex.printStackTrace();
		}
		return astrobean;
	}

	public void sendMessage(String msisdn, String message, Connection con) {
		if (con != null) {
			logger.info("Message sending service is currently deactivated......");// done
																					// by
																					// pankaj
																					// on
																					// 16_10_2013

			logger.info("msisdn number :" + msisdn + " and message : "
					+ message);

			try {
				// String
				// query="insert into gmat_message_store values(1,1,'+918377039276+',\'"+msisdn+"\',\'"+message+"\',sysdate,1,1,'1',1,1,1,1,1,'R',sysdate+1,1,'1',1)";
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
        //version 4.4
	public void fillAspectScoringMap(HashMap<String, Integer> map){
                logger.info("Inside fillAspectScoringMap");

                map.put("0",+9);
                map.put("30",+3);
                map.put("45",-3);
                map.put("60",+6);
                map.put("90",-6);
                map.put("120",+9);
                map.put("135",-3);
                map.put("150",+3);
                map.put("180",-9);

        }	//Ends


        public void setAspectHouseDetails(AstroBean astroBean,HashMap<String, Integer> scoreMap, boolean aspectScore){
		int count=1;
             	String[] aspectHouse = {"1","2","3","4","5","6","7","8","9","10","11","12"};
             	String planetNme[] ={"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};
             	String astroTable = "<table id="+'"'+"dataTable"+'"'+" width="+'"'+"100%"+'"'+" border="+'"'+"0"+'"'+" cellspacing="+'"'+"0"+'"'+" cellpadding="+'"'+"0"+'"'+"class="+'"'+"ui-table ui-responsive"+'"'+"><thead><tr>";
             	astroTable = astroTable+"<th "+"scope="+'"'+"col"+'"'+" width="+"'"+"8%"+"'"+">"+"Planet"+"</th>";
             	for(int i=0; i<aspectHouse.length; i++){
                	astroTable = astroTable+"<th "+"scope="+'"'+"col"+'"'+" width="+"'"+"8%"+"' id=house0"+aspectHouse[i]+">"+aspectHouse[i]+"</th>";
             	}
             	astroTable = astroTable+"</tr></thead>";
             	astroTable = astroTable+"<tbody>";
		for(int j=0; j<planetNme.length; j++)
		{
			if(!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")){
                		astroTable = astroTable+"<tr>";
                		astroTable = astroTable+"<td id=tdHouseAspect"+count+"0>"+planetNme[j]+"</td>";
                		for(int num=0; num<aspectHouse.length; num++)
                		{   
                    			HashMap<String, String> hashMap = astroBean.getCuspHouseAspectDetails().get(aspectHouse[num]);
                    			if(hashMap.get(planetNme[j])!=null){
						if(aspectScore && scoreMap.get(hashMap.get(planetNme[j]))!=null){
							if(scoreMap.get(hashMap.get(planetNme[j]))>0)
								astroTable = astroTable+"<td id=tdHouseAspect"+count+aspectHouse[num]+">"+hashMap.get(planetNme[j])+"(+"+scoreMap.get(hashMap.get(planetNme[j]))+")</td>";
							else
								astroTable = astroTable+"<td id=tdHouseAspect"+count+aspectHouse[num]+">"+hashMap.get(planetNme[j])+"("+scoreMap.get(hashMap.get(planetNme[j]))+")</td>";
									
						}
						else{
                      					astroTable = astroTable+"<td id=tdHouseAspect"+count+aspectHouse[num]+">"+hashMap.get(planetNme[j])+"</td>";
						}
                    			}
                    			else{
                      				astroTable = astroTable+"<td id=tdHouseAspect"+count+aspectHouse[num]+"></td>";
                    			}
                		}
               			astroTable = astroTable+"</tr>";
				count++;
			}
            }
	    int totalScore=0;
	    if(aspectScore){
		astroTable = astroTable+"<tr>";
		astroTable = astroTable+"<td id=tdHouseAspect"+count+"0></td>";
        	for(int k=1;k<=12;k++)
        	{
                	for (Map.Entry<String, String> entry : astroBean.getCuspHouseAspectDetails().get(k+"").entrySet())
                	{
                        	if(!entry.getKey().equalsIgnoreCase("Ketu") && !entry.getKey().equalsIgnoreCase("Rahu")) {
                                	totalScore=totalScore+scoreMap.get(entry.getValue());
                        	}	
                	}
                	if(totalScore>0){
				astroTable = astroTable+"<td id=tdHouseAspect"+count+k+">+"+totalScore+"</td>";
			}
                	else if(totalScore<0){
				astroTable = astroTable+"<td id=tdHouseAspect"+count+k+">"+totalScore+"</td>";	
			}
                	else{
				astroTable = astroTable+"<td id=tdHouseAspect"+count+k+">N</td>";
			}
                	totalScore=0;
        	}
		astroTable = astroTable+"</tr>";
	   }	    
            	
            astroTable = astroTable+"</tbody></table>";
            astroBean.setAstroHouseTable(astroTable);
             
        }
        
        public void setAspectPlanetDetails(AstroBean astroBean,HashMap<String, Integer> scoreMap, boolean aspectScore){
	     int count=2;
             String ketuAspect="";
             String rahuAspect="";
             String planetNme[] ={"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};
             String astroTable = "<table id="+'"'+"dataTable"+'"'+" width="+'"'+"100%"+'"'+" border="+'"'+"0"+'"'+" cellspacing="+'"'+"0"+'"'+" cellpadding="+'"'+"0"+'"'+"class="+'"'+"ui-table ui-responsive"+'"'+"><thead><tr>";
             astroTable = astroTable+"<th "+"scope="+'"'+"col"+'"'+" width="+"'"+"10%"+"'"+">"+"Planet"+"</th>";
             for(int i=0; i<planetNme.length; i++){
                astroTable = astroTable+"<th "+"scope="+'"'+"col"+'"'+" width="+"'"+"10%"+"'"+" id=planet1"+planetNme[i]+">"+planetNme[i]+"</th>";
             }
             astroTable = astroTable+"</tr></thead>";
             astroTable = astroTable+"<tbody>";
             for(int j=0; j<planetNme.length; j++){
		if(!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")){	
                astroTable = astroTable+"<tr>";
                astroTable = astroTable+"<td id=tdPlanetAspect"+count+"0>"+planetNme[j]+"</td>";
		}
                for(int num=0; num<planetNme.length; num++)
                {
                    HashMap<String, String> hashMap = astroBean.getPlanetHouseAspectDetails().get(planetNme[num]);
                    if(hashMap.get(planetNme[j])!=null){
                      if(num==0){
                         if(!planetNme[j].equals("Rahu")){
                                ketuAspect =ketuAspect+planetNme[j]+"("+hashMap.get(planetNme[j])+")"+" ";
                           }
                      }
                      if(num==5){
                            if(!planetNme[j].equals("Ketu")){
                                rahuAspect =rahuAspect+planetNme[j]+"("+hashMap.get(planetNme[j])+")"+" ";
                           }
                      }
			if(!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")){
				if(aspectScore && scoreMap.get(hashMap.get(planetNme[j]))!=null){
                              		if(scoreMap.get(hashMap.get(planetNme[j]))>0)
						astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[num]+">"+hashMap.get(planetNme[j])+"(+"+scoreMap.get(hashMap.get(planetNme[j]))+")</td>";
                                    	else
						astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[num]+">"+hashMap.get(planetNme[j])+"("+scoreMap.get(hashMap.get(planetNme[j]))+")</td>";

                              	}
                           	else{
					astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[num]+">"+hashMap.get(planetNme[j])+"</td>";
                          	}
                      	//	astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[num]+">"+hashMap.get(planetNme[j])+"</td>";
			}
                    }
                    else{
			if(!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")){
                      		astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[num]+"></td>";
			}
                    }
                }
		if(!planetNme[j].equals("Ketu") && !planetNme[j].equals("Rahu")){
               		astroTable = astroTable+"</tr>";
			count++;
		}
            }

		int totalScore=0;
            	if(aspectScore){
                astroTable = astroTable+"<tr>";
                astroTable = astroTable+"<td id=tdPlanetAspect"+count+"0></td>";
                for(int k=0;k<planetNme.length;k++)
                {
                        for (Map.Entry<String, String> entry : astroBean.getPlanetHouseAspectDetails().get(planetNme[k]).entrySet())
                        {
                                if(!entry.getKey().equalsIgnoreCase("Ketu") && !entry.getKey().equalsIgnoreCase("Rahu")) {
                                        totalScore=totalScore+scoreMap.get(entry.getValue());
                                }
                        }
                        if(totalScore>0){
                                astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[k]+">+"+totalScore+"</td>";
			}
                        else if(totalScore<0){
                                astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[k]+">"+totalScore+"</td>";
			}
                        else{
                                astroTable = astroTable+"<td id=tdPlanetAspect"+count+planetNme[k]+">N</td>";
			}
                        totalScore=0;
                }
                astroTable = astroTable+"</tr>";
           }


            astroTable = astroTable+"</tbody></table>";
            astroBean.setAstroPlanetTable(astroTable);
            if(ketuAspect.isEmpty()){
              ketuAspect = "NA";
            }
            if(rahuAspect.isEmpty()){
               rahuAspect = "NA";
            }
            astroBean.setAspectRahu(rahuAspect);
            astroBean.setAspectKetu(ketuAspect);
        }

	
	public void fillNakshatraKBMap(Map<String, NakshatraKB> map){
		logger.info("Inside fillNakshatraKBMap in MyHandler");
		String nakshatra="";
		try{
			NakshatraKB nakshatraKbBean=new NakshatraKB();
			nakshatra="Ashwini_1";
			nakshatraKbBean.setSignName("Aries");
			nakshatraKbBean.setKeyword("Solution Provider");
			nakshatraKbBean.setNL("Ketu");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(13.2);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra,nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Bharani_1";
			nakshatraKbBean.setSignName("Aries");
			nakshatraKbBean.setKeyword("Implementor");
			nakshatraKbBean.setNL("Venus");
			// nakshatraKbBean.setKeyword("Enforcement");
			nakshatraKbBean.setStartDeg(13.2);
			nakshatraKbBean.setEndDeg(20.0);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
            nakshatraKbBean=new NakshatraKB();
			nakshatra = "Krittika_1";
			nakshatraKbBean.setSignName("Aries");
			nakshatraKbBean.setKeyword("Researcher");
			nakshatraKbBean.setNL("Sun");
			nakshatraKbBean.setStartDeg(20.0);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2_3");
			map.put(nakshatra, nakshatraKbBean);
           	nakshatraKbBean = new NakshatraKB();
			nakshatra = "Krittika_2";
			nakshatraKbBean.setSignName("Taurus");
			nakshatraKbBean.setKeyword("Researcher");
			nakshatraKbBean.setNL("Sun");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(3.2);
			nakshatraKbBean.setPadam("4");
			map.put(nakshatra, nakshatraKbBean);
           	nakshatraKbBean = new NakshatraKB();
			nakshatra = "Rohini_1";
			nakshatraKbBean.setSignName("Taurus");
			nakshatraKbBean.setKeyword("Farmer");
			nakshatraKbBean.setNL("Moon");
			nakshatraKbBean.setStartDeg(3.2);
			nakshatraKbBean.setEndDeg(23.2);
			nakshatraKbBean.setPadam("1_2_3_4_5_6");
			map.put(nakshatra, nakshatraKbBean);
            nakshatraKbBean = new NakshatraKB();
			nakshatra = "Mrigashira_1";
			nakshatraKbBean.setSignName("Taurus");
			nakshatraKbBean.setKeyword("Motivator");
			nakshatraKbBean.setNL("Mars");
			nakshatraKbBean.setStartDeg(23.2);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Mrigashira_2";
			nakshatraKbBean.setSignName("Gemini");
			nakshatraKbBean.setKeyword("Motivator");
			nakshatraKbBean.setNL("Mars");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(6.4);
			nakshatraKbBean.setPadam("3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Ardra_1";
			nakshatraKbBean.setSignName("Gemini");
			nakshatraKbBean.setKeyword("Team Leader");
			nakshatraKbBean.setNL("Rahu");
			nakshatraKbBean.setStartDeg(6.4);
			nakshatraKbBean.setEndDeg(13.2);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Punarvasu_1";
			nakshatraKbBean.setSignName("Gemini");
			nakshatraKbBean.setKeyword("Developer");
			nakshatraKbBean.setNL("Jupiter");
			nakshatraKbBean.setStartDeg(13.2);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2_3_4_5");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Punarvasu_2";
			nakshatraKbBean.setSignName("Cancer");
			nakshatraKbBean.setKeyword("Developer");
			nakshatraKbBean.setNL("Jupiter");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(3.2);
			nakshatraKbBean.setPadam("6");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Pushya_1";
			nakshatraKbBean.setSignName("Cancer");
			nakshatraKbBean.setKeyword("Instructor");
			nakshatraKbBean.setNL("Saturn");
			nakshatraKbBean.setStartDeg(3.2);
			nakshatraKbBean.setEndDeg(16.4);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Ashlesha_1";
			nakshatraKbBean.setSignName("Cancer");
			nakshatraKbBean.setKeyword("Elated");
			nakshatraKbBean.setNL("Mercury");
			nakshatraKbBean.setStartDeg(16.4);
			nakshatraKbBean.setEndDeg(23.2);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Magha_1";
			nakshatraKbBean.setSignName("Cancer");
			nakshatraKbBean.setKeyword("Helper");
			nakshatraKbBean.setNL("Ketu");
			nakshatraKbBean.setStartDeg(23.2);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Magha_2";
			nakshatraKbBean.setSignName("Leo");
			nakshatraKbBean.setKeyword("Helper");
			nakshatraKbBean.setNL("Ketu");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(6.4);
			nakshatraKbBean.setPadam("3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Purva Phalg._1";
			nakshatraKbBean.setSignName("Leo");
			nakshatraKbBean.setKeyword("Branding");
			nakshatraKbBean.setNL("Venus");
			nakshatraKbBean.setStartDeg(6.4);
			nakshatraKbBean.setEndDeg(20.0);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Uttara Phalg._1";
			nakshatraKbBean.setSignName("Leo");
			nakshatraKbBean.setKeyword("Match Maker, Facilitator");
			nakshatraKbBean.setNL("Sun");
			nakshatraKbBean.setStartDeg(20.0);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2_3");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Uttara Phalg._2";
			nakshatraKbBean.setSignName("Virgo");
			nakshatraKbBean.setKeyword("Match Maker, Facilitator");
			nakshatraKbBean.setNL("Sun");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(10.0);
			nakshatraKbBean.setPadam("4_5_6");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Hasta_1";
			nakshatraKbBean.setSignName("Virgo");
			nakshatraKbBean.setKeyword("Angle Investor");
			nakshatraKbBean.setNL("Moon");
			nakshatraKbBean.setStartDeg(10.0);
			nakshatraKbBean.setEndDeg(23.2);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Chitra_1";
			nakshatraKbBean.setSignName("Virgo");
			nakshatraKbBean.setKeyword("Maker");
			nakshatraKbBean.setNL("Mars");
			nakshatraKbBean.setStartDeg(23.2);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Chitra_2";
			nakshatraKbBean.setSignName("Libra");
			nakshatraKbBean.setKeyword("Maker");
			nakshatraKbBean.setNL("Mars");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(6.4);
			nakshatraKbBean.setPadam("3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Swati_1";
			nakshatraKbBean.setSignName("Libra");
			nakshatraKbBean.setKeyword("Media");
			nakshatraKbBean.setNL("Rahu");
			nakshatraKbBean.setStartDeg(6.4);
			nakshatraKbBean.setEndDeg(13.2);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Vishakha_1";
			nakshatraKbBean.setSignName("Libra");
			nakshatraKbBean.setKeyword("Executor");
			nakshatraKbBean.setNL("Jupiter");
			nakshatraKbBean.setStartDeg(13.2);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2_3_4_5");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Vishakha_2";
			nakshatraKbBean.setSignName("Scorpio");
			nakshatraKbBean.setKeyword("Executor");
			nakshatraKbBean.setNL("Jupiter");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(3.2);
			nakshatraKbBean.setPadam("6");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Anuradha_1";
			nakshatraKbBean.setSignName("Scorpio");
			nakshatraKbBean.setKeyword("Negotiator,Lawyer");
			nakshatraKbBean.setNL("Saturn");
			nakshatraKbBean.setStartDeg(3.2);
			nakshatraKbBean.setEndDeg(16.4);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Jyeshtha_1";
			nakshatraKbBean.setSignName("Scorpio");
			nakshatraKbBean.setKeyword("King");
			nakshatraKbBean.setNL("Mercury");
			nakshatraKbBean.setStartDeg(16.4);
			nakshatraKbBean.setEndDeg(23.2);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Moola_1";
			nakshatraKbBean.setSignName("Scorpio");
			nakshatraKbBean.setKeyword("Networker");
			nakshatraKbBean.setNL("Ketu");
			nakshatraKbBean.setStartDeg(23.2);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Moola_2";
			nakshatraKbBean.setSignName("Sagittarius");
			nakshatraKbBean.setKeyword("Networker");
			nakshatraKbBean.setNL("Ketu");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(6.4);
			nakshatraKbBean.setPadam("3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Purvashadha_1";
			nakshatraKbBean.setSignName("Sagittarius");
			nakshatraKbBean.setKeyword("Performer");
			nakshatraKbBean.setNL("Venus");
			nakshatraKbBean.setStartDeg(6.4);
			nakshatraKbBean.setEndDeg(20.0);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Uttarashadha_1";
			nakshatraKbBean.setSignName("Sagittarius");
			nakshatraKbBean.setKeyword("Team Maker");
			nakshatraKbBean.setNL("Sun");
			nakshatraKbBean.setStartDeg(20.0);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2_3");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Uttarashadha_2";
			nakshatraKbBean.setSignName("Capricorn");
			nakshatraKbBean.setKeyword("Team Maker");
			nakshatraKbBean.setNL("Sun");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(10.0);
			nakshatraKbBean.setPadam("4_5_6");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Shravana_1";
			nakshatraKbBean.setSignName("Capricorn");
			nakshatraKbBean.setKeyword("Mapper");
			nakshatraKbBean.setNL("Moon");
			nakshatraKbBean.setStartDeg(10.0);
			nakshatraKbBean.setEndDeg(23.2);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Dhanishta_1";
			nakshatraKbBean.setSignName("Capricorn");
			nakshatraKbBean.setKeyword("Investor");
			nakshatraKbBean.setNL("Mars");
			nakshatraKbBean.setStartDeg(23.2);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
			nakshatraKbBean = new NakshatraKB();
			nakshatra = "Dhanishta_2";
			nakshatraKbBean.setSignName("Aquarius");
			nakshatraKbBean.setKeyword("Investor");
			nakshatraKbBean.setNL("Mars");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(6.4);
			nakshatraKbBean.setPadam("3_4");
			map.put(nakshatra, nakshatraKbBean);
          	nakshatraKbBean = new NakshatraKB();
			nakshatra = "Shatabhisha_1";
			nakshatraKbBean.setSignName("Aquarius");
			nakshatraKbBean.setKeyword("Observer");
			nakshatraKbBean.setNL("Rahu");
			nakshatraKbBean.setStartDeg(6.4);
			nakshatraKbBean.setEndDeg(13.2);
			nakshatraKbBean.setPadam("1_2");
			map.put(nakshatra, nakshatraKbBean);
          	nakshatraKbBean = new NakshatraKB();
			nakshatra = "Purvabhadra_1";
			nakshatraKbBean.setSignName("Aquarius");
			nakshatraKbBean.setKeyword("Insights");
			nakshatraKbBean.setNL("Jupiter");
			nakshatraKbBean.setStartDeg(13.2);
			nakshatraKbBean.setEndDeg(26.4);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
           	nakshatraKbBean = new NakshatraKB();
			nakshatra = "Uttarabhadra_1";
			nakshatraKbBean.setSignName("Aquarius");
			nakshatraKbBean.setKeyword("Sea Diver");
			nakshatraKbBean.setNL("Saturn");
			nakshatraKbBean.setStartDeg(26.4);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1");
			map.put(nakshatra, nakshatraKbBean);
            nakshatraKbBean = new NakshatraKB();
			nakshatra = "Uttarabhadra_2";
			nakshatraKbBean.setSignName("Pisces");
			nakshatraKbBean.setKeyword("Sea Diver");
			nakshatraKbBean.setNL("Saturn");
			nakshatraKbBean.setStartDeg(0.0);
			nakshatraKbBean.setEndDeg(16.4);
			nakshatraKbBean.setPadam("2_3_4_5_6");
			map.put(nakshatra, nakshatraKbBean);
           	nakshatraKbBean = new NakshatraKB();
			nakshatra = "Revati_1";
			nakshatraKbBean.setSignName("Pisces");
			nakshatraKbBean.setKeyword("Navigator");
			nakshatraKbBean.setNL("Mercury");
			nakshatraKbBean.setStartDeg(16.4);
			nakshatraKbBean.setEndDeg(30.0);
			nakshatraKbBean.setPadam("1_2_3_4");
			map.put(nakshatra, nakshatraKbBean);
		
		}
		catch(Exception e){
			logger.info("Exception in fillNakshatraKBMap");
		}
		

	}

	public double convertTimeInDegree(String time) {
                logger.debug("inside convertTimeInDegree "+time);
                double decDegrees = 0;
                try{
                String[] degree = time.split(":");
                double sec = 0;
                double min = 0;
                double deg = 0;
                min=Float.valueOf(degree[1]);
                min=(min * (1.0 / 60.0));

                sec=Float.valueOf(degree[2]);
                sec=(sec * (1.0 / 3600.0));


                deg=Float.valueOf(degree[0]);
                logger.debug("min>> "+min+"  sec>> "+sec+"  deg>> "+deg);
                decDegrees = deg + min + sec;
                logger.debug("decDegrees>>  "+decDegrees + "min>> "+min+"  sec>> "+sec+"  deg>> "+deg + "convertTimeInDegree "+time);
                return decDegrees;
                }
                 catch (Exception e) {
                        logger.error(e.toString());
                        return decDegrees;
                }
        }

	public double convertDegree(double degree) {
                logger.info("inside convertDegree "+degree);
                double decDegrees = degree;
                try{
			DecimalFormat decimalFormat = new DecimalFormat("#.#");
		//	decDegrees=Float.valueOf(decimalFormat.format(decDegrees));
			String degStr=""+degree;
                	String[] degreeArr = degStr.split("\\.");
			degreeArr[1]=degreeArr[1].substring(0,1);
                	int deg = 0;
               	 	int min = 0;

                	deg=Integer.valueOf(degreeArr[0]);
                	min=Integer.valueOf(degreeArr[1]);
			logger.info(" before formt min>> "+min+"  deg>> "+deg);

		//	min=Float.valueOf(decimalFormat.format(min));
		//	logger.info("after min>> "+min+"  deg>> "+deg);

			if(min>=6){
				deg=deg+1;
				min=min-6;
				String temp=""+deg+"."+min;
				logger.info(" final min>> "+min+"  deg>> "+deg+"  temp > "+temp);
				
                        	decDegrees = Float.valueOf(temp);
				decDegrees=Float.valueOf(decimalFormat.format(decDegrees));
			}			

                	logger.info("decDegrees>> convertDegree "+decDegrees);
                	return decDegrees;
                }
                catch (Exception e) {
                        logger.error(e.toString());
                        return decDegrees;
                }
        }

	
	public void generatePDF(AstroBean astrobean, AstroBean transitAstrobean, GenerateKundli generateKundli,boolean emailFlag,HashMap<Integer, String> tempTransitHashTable){
                logger.info("Inside generatePDF");
                try{
                        String fileName = getFilePath(generateKundli.getName());
                        FinalKundli finalkundli = new FinalKundli();
                        astrobean.setFileName(file);
                        astrobean.setPOB(generateKundli.getCity());
                        logger.info("KundliCircleName>> "+generateKundli.getKundliCircleName() +" special Kundli ["+generateKundli.isSpecialKundli()+"]");
                        
                        if( generateKundli.isSpecialKundli() &&  generateKundli.isDasha()==false && generateKundli.isHouseDetail()==false ) {
                        	logger.info(" Inside special kundli ");
                        	FinalKundliSpecialPdf finalKundliSpecialPdf=new FinalKundliSpecialPdf();
                        	finalKundliSpecialPdf.Kundli(astrobean, transitAstrobean,file, false, false,generateKundli.isCircle(),properties.get("CIRCLE_IMAGE_PATH").trim()+generateKundli.getKundliCircleName(),properties.get("CIRCLE_IMAGE_PATH").trim()+"list"+generateKundli.getUserId()+"_post.jpg",generateKundli.isAspectChart(),generateKundli.isNakshatraPadam(),generateKundli.isCuspName(),generateKundli.isAspectScoring(),properties.get("TRANSIT_CIRCLE_IMAGE_PATH").trim()+generateKundli.getKundliCircleName(),0,tempTransitHashTable);
                        }else {
                        	logger.info(" Inside Non-special kundli ");
                        	if(transitAstrobean==null) {
                        		transitAstrobean=new AstroBean();
                        	}
                        	transitAstrobean.setTOB(generateKundli.getTransitTob());
                        	transitAstrobean.setDOB(generateKundli.getDob());
                        	transitAstrobean.setTransitState(generateKundli.getTransitState());
                        	transitAstrobean.setTransitCountry(generateKundli.getTransitCountry());
                        	transitAstrobean.setTransitCity(generateKundli.getTransitCity());                        	
                        	finalkundli.Kundli(astrobean, transitAstrobean,file, generateKundli.isDasha(), generateKundli.isHouseDetail(),generateKundli.isCircle(),properties.get("CIRCLE_IMAGE_PATH").trim()+generateKundli.getKundliCircleName(),properties.get("CIRCLE_IMAGE_PATH").trim()+"list"+generateKundli.getUserId()+"_post.jpg",generateKundli.isAspectChart(),generateKundli.isNakshatraPadam(),generateKundli.isCuspName(),generateKundli.isAspectScoring(),properties.get("TRANSIT_CIRCLE_IMAGE_PATH").trim()+generateKundli.getKundliCircleName(),generateKundli.getIsTransitKundli(),tempTransitHashTable);
                        }
                        
			if (emailFlag)
                        {
                        	String[] mailFileDir = { file };
                        	String[] mailTo = { generateKundli.getEmail() };
                        	String mailSubject = "MahaJyotish Kundli  "+generateKundli.getName();
                        	KundliMail kundliMail = new KundliMail();
                            kundliMail.kundliMailSending(mailSubject, mailTo, mailFileDir,generateKundli.getName());
                 	}
                }
                catch (Exception e) {
                        logger.error(e.toString());
                       logger.error("Exception occoured while generate PDF ",e);
                }

        }

}

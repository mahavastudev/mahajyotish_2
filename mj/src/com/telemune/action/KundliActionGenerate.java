package com.telemune.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Properties;
import java.util.Set;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.UUID;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;

import com.telemune.mobileAstro.AstroBean;
import com.telemune.mobileAstro.FinalKundli;
import com.telemune.mobileAstro.Kundli;
import com.telemune.mobileAstro.KundliHouseBean;
import com.telemune.mobileAstro.MahaDashaBean;
import com.telemune.mobileAstro.MyHandler;
import com.telemune.mobileAstro.Planet;
import com.telemune.mobileAstro.PlanetDetailBean;
import com.telemune.mobileAstro.HouseDetailBean;
import com.telemune.mobileAstro.DashaStrength;
import com.telemune.mobileAstro.CalculateVimshottari;
import com.telemune.mobileAstro.ReadPropertyFile;

import com.telemune.model.CountryData;
import com.telemune.model.GenerateKundli;
import com.telemune.model.StateData;
import com.telemune.model.UserInfo;
import com.telemune.util.Constants;
import com.telemune.model.Event;
import com.telemune.model.Generate;

public class KundliActionGenerate extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(GenerateKundliAction.class);
	private UserProfileDao userDao = null;
	private String fileName = "";
	private String message = "";
	private String kundliCircleName = "";
	private GenerateKundli generateKundli;
	private Generate generate;
	private InputStream fileInputStream;
	private AstroBean astrobean = null;
	private AstroBean astrobeanUser=null;
	private HashMap<Integer, KundliHouseBean> birthKundli = null;
	private HashMap<Integer, KundliHouseBean> cuspKundli = null;
	private LinkedHashMap<String, MahaDashaBean> vimshottari = null;
	private Map map;
	private int id = 0;
	private String genType = "";
	private LinkedHashMap<String, ArrayList<String>> antraDashamap = null;
	private MahaDashaBean mahaDashaBean = null;
	private UserInfo userInfo;
	private List<CountryData> countryLst;
	private List<StateData> stateLst;
        private AstroBean transitAstroBean;
//------------------------------------------------------------>>
	
	private String[] planets= {"Rahu","Ketu","Mercury","Saturn","Venus","Sun","Moon","Mars","Jupiter"};
        private String lordValue="";
        private String parent="";
        private String child="";
        private String subChild="";
	private String kundliLogXML;
	private Event event=null;
	private List<Event> eventLogsLst;
	private String newNatalStrengthChart[][];
	private Hashtable<String, String> properties = null;
        private String proPath = "";
	private String kundliType="";
        private String token = "";

        public KundliActionGenerate() {
                logger.info("Inside constructor of GenerateKundliAction class.");
                proPath = Constants.PROPERTIES_PATH;
               	proPath = proPath + "kundliHttpserverNew.properties";
              //	proPath = proPath + "kundliHttpserverTest.properties";
                logger.info("property file:"+proPath);
                properties = ReadPropertyFile.readPropery(proPath);
        }

	public String getToken() {
                return this.token;
        }
        public void setToken(String token) {
                this.token = token;
        }
        
        
        public void setSession(Map session) {
    		map = session;
    	}

	public Generate getGenerate() {
			return generate;
		}

		public void setGenerate(Generate generate) {
			this.generate = generate;
		}

	public String[][] getNewNatalStrengthChart() {
                return newNatalStrengthChart;
        }

        public void setNewNatalStrengthChart(String[][] newNatalStrengthChart) {
                this.newNatalStrengthChart = newNatalStrengthChart;
        }

        public List<Event> getEventLogsLst() {
                return eventLogsLst;
        }
        public void setEventLogsLst(List<Event> eventLogsLst) {
                this.eventLogsLst = eventLogsLst;
        }
	
	public Event getEvent() {
                return event;
        }
        public void setEvent(Event event) {
                this.event = event;
        }
	
	public String getKundliLogXML() {
                return kundliLogXML;
        }
        public void setKundliLogXML(String kundliLogXML) {
                this.kundliLogXML = kundliLogXML;
        }

//------------------------------------------------------------------------>>	

	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public GenerateKundli getGenerateKundli() {
		return generateKundli;
	}

	public void setGenerateKundli(GenerateKundli generateKundli) {
		this.generateKundli = generateKundli;
	}

	public AstroBean getAstrobean() {
		return astrobean;
	}

	public void setAstrobean(AstroBean astrobean) {
		this.astrobean = astrobean;
	}

	public String getGenType() {
		return genType;
	}
	public void setGenType(String genType) {
		this.genType = genType;
	}


	public String getKundliType() {
                return kundliType;
        }
        public void setKundliType(String kundliType) {
                this.kundliType = kundliType;
        }

	public String getKundliCircleName() {
                return kundliCircleName;
        }
        public void setKundliCircleName(String kundliCircleName) {
                this.kundliCircleName = kundliCircleName;
        }



	public HashMap<Integer, KundliHouseBean> getBirthKundli() {
		return birthKundli;
	}

	public void setBirthKundli(HashMap<Integer, KundliHouseBean> birthKundli) {
		this.birthKundli = birthKundli;
	}

	public HashMap<Integer, KundliHouseBean> getCuspKundli() {
		return cuspKundli;
	}

	public void setCuspKundli(HashMap<Integer, KundliHouseBean> cuspKundli) {
		this.cuspKundli = cuspKundli;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private List<GenerateKundli> kundliLogsLst;

	public List<GenerateKundli> getKundliLogsLst() {
		return kundliLogsLst;
	}

	public void setKundliLogsLst(List<GenerateKundli> kundliLogsLst) {
		this.kundliLogsLst = kundliLogsLst;
	}

	public HashMap<String, ArrayList<String>> getAntraDashamap() {
		return antraDashamap;
	}

	public void setAntraDashamap(
			LinkedHashMap<String, ArrayList<String>> antraDashamap) {
		this.antraDashamap = antraDashamap;
	}

	public LinkedHashMap<String, MahaDashaBean> getVimshottari() {
		return vimshottari;
	}

	public void setVimshottari(LinkedHashMap<String, MahaDashaBean> vimshottari) {
		this.vimshottari = vimshottari;
	}

	public MahaDashaBean getMahaDashaBean() {
		return mahaDashaBean;
	}

	public void setMahaDashaBean(MahaDashaBean mahaDashaBean) {
		this.mahaDashaBean = mahaDashaBean;
	}
	
	

	public List<CountryData> getCountryLst() {
		return countryLst;
	}

	public void setCountryLst(List<CountryData> countryLst) {
		this.countryLst = countryLst;
	}

	public List<StateData> getStateLst() {
		return stateLst;
	}

	public void setStateLst(List<StateData> stateLst) {
		this.stateLst = stateLst;
	}
         
       public void setTransitAstroBean(AstroBean transitAstroBean){
               this.transitAstroBean = transitAstroBean;
       }
      
       public AstroBean getTransitAstroBean(){
              return transitAstroBean;
       }



	public String execute() throws Exception {
		try {
					userDao = new UserProfileDaoImpl();
				countryLst = userDao.getCountryList();
				stateLst = userDao.getStateList("IN");
 				generateKundli = new GenerateKundli();
				return "SUCCESS";
	
		} catch (Exception e) {
            e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally{
			if(userDao != null){
				userDao = null;
			}
		}

	}


	
	public String populateAll() throws Exception {
		logger.info("Inside populateAll() of generateKundliAction message ["+ message + "]");
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			session.setAttribute("userName","1");
			    generate=new Generate();
				logger.info("genType>> "+genType);
				this.setGenType(genType);
				token = UUID.randomUUID().toString().replace("-", "");
				logger.info("token "+token +" genType "+genType);
				String dob = generateKundli.getYear() + "-" + generateKundli.getMonth() + "-"
						+ generateKundli.getDate();
				String tob = generateKundli.getHour() + ":" + generateKundli.getMinute() + ":"
						+ generateKundli.getSecond();
				generateKundli.setDob(dob);
				generateKundli.setTob(tob);
				String transitDob = generateKundli.getTransitYear() + "-" + generateKundli.getTransitMonth() + "-"
						+ generateKundli.getTransitDate();
				String transitTob = generateKundli.getTransitHour() + ":" + generateKundli.getTransitMinute() + ":"
						+ generateKundli.getTransitSecond();
				generateKundli.setTransitDob(transitDob);
				generateKundli.setTransitTob(transitTob);

				map.put("kundliName", generateKundli.getName());
				map.put("pob", generateKundli.getCity());
				map.put("tob", generateKundli.getTob());
				map.put("dob", generateKundli.getDob());
				map.put("lat", generateKundli.getLatitude());
				map.put("long", generateKundli.getLongitude());
				map.put("state", generateKundli.getState());
				map.put("country", generateKundli.getCountry());
				map.put("cityCode", generateKundli.getCityCode());
				map.put("isSave", generateKundli.getIsSave());
				map.put("isTransitKundli", generateKundli.getIsTransitKundli());
				map.put("transitTob", generateKundli.getTransitTob());
				map.put("transitDob", generateKundli.getTransitDob());
				map.put("requestId", generateKundli.getRequestId());
				map.put("remarks", generateKundli.getRemarks());
				map.put("occupation", generateKundli.getOccupation());
				map.put("accountId", generateKundli.getAccountId());
				map.put("contactId", generateKundli.getContactId());
				map.put("isBTR", generateKundli.getIsBTR());
				map.put("transitState", generateKundli.getTransitState());
				map.put("transitCity", generateKundli.getTransitCity());
				map.put("transitCountry", generateKundli.getTransitCountry());
				map.put("transitLatitude", generateKundli.getTransitLatitude());
				map.put("transitLongitude", generateKundli.getTransitLongitude());
				map.put("transitLocation", generateKundli.getTransitLocation());
					
				generateKundli.setUserId(Integer.parseInt((String)session.getAttribute("userName")));
				//logger.info("generateKundli>>>>>>>>> " + generateKundli);
				UserProfileDaoImpl daoImpl = null;
					return "SUCCESS";
			
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String submitdetailsForKundli() {
		logger.info("Inside submitdetailsForKundli() of generateKundliAction "+ generate);
       try {
    	   HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			
			    int retval = -1;
				MyHandler myhandler = new MyHandler();
				HashMap<String, String> planetName = new HashMap<String, String>();
				HashMap<Integer, String> rashiName = new HashMap<Integer, String>();
				HashMap<String,Integer> rashiNumber = new HashMap<String,Integer>();
				logger.info("kundliCircleName>> IN CASE DOWNLOAD>>"+kundliCircleName);
				boolean flag = false;
				boolean isNakshatraKB = false;
				generateKundli = new GenerateKundli();
				generateKundli.setName((String) session.getAttribute("kundliName"));
				generateKundli.setDob((String) session.getAttribute("dob"));
				generateKundli.setUserId(Integer.parseInt((String)session.getAttribute("userName")));
				generateKundli.setTob((String) session.getAttribute("tob"));
				logger.info("generateKundli tob==>"+generateKundli.getTob());
				logger.info("generateKundli dob==>"+generateKundli.getDob());
				generateKundli.setCity((String) session.getAttribute("pob"));
                generateKundli.setCityCode((String) session.getAttribute("cityCode"));
                generateKundli.setState((String) session.getAttribute("state"));
                generateKundli.setCountry((String) session.getAttribute("country"));
				generateKundli.setLatitude((String) session.getAttribute("lat"));
				generateKundli.setLongitude((String) session.getAttribute("long"));
                generateKundli.setTransitDob((String) session.getAttribute("transitDob"));
                generateKundli.setTransitTob((String) session.getAttribute("transitTob"));
                generateKundli.setTransitState((String) session.getAttribute("transitState"));
                generateKundli.setTransitCountry((String) session.getAttribute("transitCountry"));
                generateKundli.setTransitCity((String) session.getAttribute("transitCity"));
                generateKundli.setRemarks((String) session.getAttribute("remarks"));
                generateKundli.setOccupation((String) session.getAttribute("occupation"));
                generateKundli.setAccountId((String) session.getAttribute("accountId"));
                generateKundli.setContactId((String) session.getAttribute("contactId"));
                generateKundli.setIsTransitKundli(Integer.parseInt(session.getAttribute("isTransitKundli").toString()));
				generateKundli.setRequestId((Integer) session.getAttribute("requestId"));
				generateKundli.setKundliCircleName(kundliCircleName);
				generateKundli.setTransitLocation((Integer)session.getAttribute("transitLocation"));
				generateKundli.setTransitLatitude((String)session.getAttribute("transitLatitude"));
				generateKundli.setTransitLongitude((String)session.getAttribute("transitLongitude"));
				//generateKundli.setCcCode((String)session.getAttribute("cc_code"));
				fillPlanetMap(planetName);
				fillRashiNameMap(rashiName);
				fillRashiNumberMap(rashiNumber);
				
				//logger.info("transitPob AAAAAAAAAAAAAAAAAAAAAAAAAAAAA: "+session.getAttribute("transitPob"));
				if(userInfo !=null){
					logger.info("user email "+userInfo.getEmail() + " dashaDetail ["+userInfo.isDashaDetail()+"] HouseDetail ["+userInfo.isHouseDetail()+"] specialKundli ["+userInfo.isSpecialKundli()+"]");
					generateKundli.setEmail(userInfo.getEmail());
				 	generateKundli.setHouseDetail(userInfo.isHouseDetail());
					generateKundli.setDasha(userInfo.isDashaDetail());
					generateKundli.setSpecialKundli(userInfo.isSpecialKundli());
				}


				logger.info("genType>> SUBMIT>> "+genType+"generateKundli==>"+generateKundli);
					generateKundli.setCircle(true);
				
				                        generateKundli.setAspectChart(true);
				
				                        generateKundli.setNakshatraPadam(true);				

				                        generateKundli.setCuspName(true);
				
				//version 4.4
				                        generateKundli.setAspectScoring(true);
				//ENDS

				if (genType != null && genType != "")
					generateKundli.setGenerateType(genType);

				if (generateKundli.getGenerateType().equalsIgnoreCase("E")) {
					flag = true;
				}
				if(map.containsKey("26")){
					isNakshatraKB=true;
					generateKundli.setNakshatraPadam(true);
				}
				
				astrobean = myhandler.msgExtraction(generateKundli, flag,isNakshatraKB);
				String jsonStr= new Gson().toJson(astrobean);
				userDao=new UserProfileDaoImpl();
				int result=userDao.addkundliDetails(generateKundli,jsonStr);
				int user_id=userDao.getUserId();
				System.out.println("email==>"+generateKundli.getEmail()+" user id==>"+user_id);
				if (generateKundli.getGenerateType().equalsIgnoreCase("E")) {
					int update=userDao.updateEmail(generateKundli.getEmail(),user_id);
				}
				
				logger.info("##################### Final json to save "+result);
				
				HashMap<Integer, String> tempTransitHashTable=new HashMap<Integer, String>();
				HashMap<Integer, String> tempMap=new HashMap<Integer, String>();
				int houseCusp=0;	
                                if(generateKundli.getIsTransitKundli()==1)
                                {  
				     	            //flag=false;
				                       boolean sflag=false;
				                       generateKundli.setTranistKundli(true);
                                       generateKundli.setTransitDob(generateKundli.getTransitDob());
                                       generateKundli.setTransitTob(generateKundli.getTransitTob());
                                       transitAstroBean =myhandler.msgExtraction(generateKundli, flag,sflag);
                                       generateKundli.setTranistKundli(false);
                                       if(generateKundli.getTransitLocation()==1)
                                       {  
                                    	   generateKundli.setTranistKundli(true);
                                       }
                                       generateKundli.setTranistKundli(false);
					/*BY BHARTI 3/16/2018-------------------------------------->>*/
					
					LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable=transitAstroBean.getPlanetDetailHashTable();
					LinkedHashMap<String, HouseDetailBean> houseDetailHashTable=astrobean.getHouseDetailHashTable();
					PlanetDetailBean planetBean=null;
                             		for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet()) 
					{
						if(!entry.getKey().equalsIgnoreCase("Lagna")){
                                		/*logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue());*/
						for (Map.Entry<String, HouseDetailBean> entry1 : houseDetailHashTable.entrySet())
                                        	{
							if(entry1.getValue().getSignName().equalsIgnoreCase(entry.getValue().getSignName()))
							{
								/*logger.info("Key : " + entry1.getKey() + " Value : " + entry1.getValue());*/
								tempMap.put(Integer.parseInt(entry1.getKey()),entry1.getValue().getSignName());
								/*logger.info("tempMap> "+tempMap);*/
							}
						}
						/*logger.info("tempMap size> "+tempMap.size());*/
						if(tempMap.size()>0){
						for (Map.Entry<Integer, String> entry2 : tempMap.entrySet())
                                                {
							if(tempMap.size()>1){
								if(convertTimeInDegree(entry.getValue().getDegree())>convertTimeInDegree(houseDetailHashTable.get(entry2.getKey()+"").getDegree())){
									if(!tempTransitHashTable.containsKey(entry2.getKey())){
                                                                        	tempTransitHashTable.put(entry2.getKey(),planetName.get(entry.getKey()));
                                                                              /*  logger.info("tempTransitHashTable inside size greater than 1 if> "+tempTransitHashTable);*/
                                                                     	}
                                                                  	else{
                                                                        	String tempp=tempTransitHashTable.get(entry2.getKey())+" "+planetName.get(entry.getKey());
                                                                               	tempTransitHashTable.put(entry2.getKey(),tempp);
                                                                                /*logger.info("tempTransitHashTable inside size greater than 1 else> "+tempTransitHashTable);*/
                                                                    	}	
									break;
								}	
	
							}
							else if(tempMap.size()==1)
							{
								houseCusp=entry2.getKey();
								int tempHouseCusp=0;
								if(houseCusp==1)
									tempHouseCusp=12;
								else
									tempHouseCusp=houseCusp-1;
								/*logger.info("houseCusp>>  "+houseCusp+"   tempHouseCusp>>> "+tempHouseCusp);*/
								if(convertTimeInDegree(entry.getValue().getDegree())<convertTimeInDegree(houseDetailHashTable.get(entry2.getKey()+"").getDegree())){
						/*			logger.info("HERERE INSIDE SMALLER>>"+entry.getKey());*/

                                                                                if(!tempTransitHashTable.containsKey(tempHouseCusp)){
                                                                                        tempTransitHashTable.put(tempHouseCusp,planetName.get(entry.getKey()));
									/*	logger.info("tempTransitHashTable inside smaller if> "+tempTransitHashTable);*/

                                                                                }
                                                                                else{
                                                                                        String tempp=tempTransitHashTable.get(tempHouseCusp)+" "+planetName.get(entry.getKey());
                                                                                        tempTransitHashTable.put(tempHouseCusp,tempp);
											/*logger.info("tempTransitHashTable inside smaller else> "+tempTransitHashTable);*/
                                                                                }
                                                                }
                                                                else{
								/*	logger.info("HERERE INSIDE LARGER>>"+ entry.getKey());*/
									if(!tempTransitHashTable.containsKey(houseCusp)){
                                                                                        tempTransitHashTable.put(houseCusp,planetName.get(entry.getKey()));
										/*logger.info("tempTransitHashTable inside larger if> "+tempTransitHashTable);*/
                                                                                }
                                                                                else{
                                                                                        String tempp=tempTransitHashTable.get(houseCusp)+" "+planetName.get(entry.getKey());
                                                                                        tempTransitHashTable.put(houseCusp,tempp);
									/*logger.info("tempTransitHashTable inside larger else> "+tempTransitHashTable);*/
                                                                                }
                                                                }


							}

						}
						}
						
						else{

                                                                houseCusp=rashiNumber.get(entry.getValue().getSignName());
                                                                int tempHouseCusp=0;
                                                                if(houseCusp==1)
                                                                        tempHouseCusp=12;
                                                                else
                                                                        tempHouseCusp=houseCusp-1;

                                                                String tempRashi=rashiName.get(tempHouseCusp);
                                                              /*  logger.info("before  "+entry.getValue().getSignName()+"    after>>tempRashi>> "+tempRashi);*/
                                                                for (Map.Entry<String, HouseDetailBean> entry1 : houseDetailHashTable.entrySet())
                                                                {
                                                                        /*logger.info("Key : " + entry1.getKey() + " Value : " + entry1.getValue());*/
                                                                        if(entry1.getValue().getSignName().equalsIgnoreCase(tempRashi))
                                                                        {
                                                                                houseCusp=Integer.parseInt(entry1.getKey());
                                                                        }
                                                                }
                                                                if(!tempTransitHashTable.containsKey(houseCusp)){
                                                                        tempTransitHashTable.put(houseCusp,planetName.get(entry.getKey()));
                                                                }
                                                                else{
                                                                        String tempp=tempTransitHashTable.get(houseCusp)+" "+planetName.get(entry.getKey());
                                                                        /*logger.info("tempp>> else> "+tempp);*/
                                                                        tempTransitHashTable.put(houseCusp,tempp);
                                                                }
                                                        }



						tempMap=new HashMap<Integer, String>();
						}	
						}
			
				
				/*	logger.info("tempTransitHashTable>>  "+tempTransitHashTable);*/


                    }//isTransitKundli
                
                
                //genType="S";
				if (!genType.equalsIgnoreCase("D")) {
					System.out.println(" gen type "+genType);	
					myhandler.generatePDF(astrobean,transitAstroBean,generateKundli,flag,tempTransitHashTable);
				}
				//myhandler.generatePDF(astrobean,transitAstroBean,generateKundli,flag,tempTransitHashTable);
				//genType="D";
				retval = 1;
					
				
				if (genType.equalsIgnoreCase("EV")) {
					logger.info("inside calling for EV");

					int userId = generateKundli.getUserId();
					userDao = new UserProfileDaoImpl();
					String result1 = userDao.getUserKundli(userId);
					astrobeanUser = new Gson().fromJson(result1, AstroBean.class);
					String scope = getScope();
					generateKundli.setScope(scope);
					String xmlData = astrobean.getKundliData();
					astrobean.setKundliData("");
					 jsonStr = new Gson().toJson(astrobean);
					generateKundli.setKundliData(xmlData);
					userDao = new UserProfileDaoImpl();
					userDao.saveEventDetail(generateKundli, jsonStr, xmlData);

					
				}
				else if (genType.equalsIgnoreCase("P")) {
                                     ////   generateKundli.setKundliData(astrobean.getKundliData());
                                        String xmlData=astrobean.getKundliData();
                                        //logger.info("xml data:"+xmlData);
                                        astrobean.setKundliData("");
                                         jsonStr= new Gson().toJson(astrobean);
                                        generateKundli.setKundliData(xmlData);
                                        userDao=new UserProfileDaoImpl();
                                        int userId=generateKundli.getUserId();
                                        boolean result2=userDao.isUserExist(userId);
                                        logger.info("result from is UserExist"+result);
                                        if(result2){
                                                userDao.updateDetail(generateKundli,jsonStr,xmlData);
						}
                                        else{
                                                userDao.saveDetail(generateKundli,jsonStr,xmlData);
					
					}
                                }
					
				if (generateKundli.getGenerateType().equalsIgnoreCase("D")) {

					/*String RahuAspect[] = astrobean.getAspect(Planet
							.toPlanets("Rahu"));
					String RahuAspectstr = "";
					if (RahuAspect != null) {

						for (int i = 0; i < RahuAspect.length; i++) {

							RahuAspectstr += RahuAspect[i] + "\n";

						}

					} else {
						RahuAspectstr = "NA";
					}*/

					String[] RahuConjuction = astrobean.getConjuction(Planet.toPlanets("Rahu"));
					String RahuConjuctionstr = "";
					if (RahuConjuction != null) {

						for (int i = 0; i < RahuConjuction.length; i++) {
							RahuConjuctionstr += RahuConjuction[i] + "\n";
						}

					} else {
						RahuConjuctionstr = "NA";

					}

					String RahuSignLoard = astrobean.getSignLoard(Planet
							.toPlanets("Rahu"));
					String RahuRashiLoard = astrobean.getRashiLoard(Planet
							.toPlanets("Rahu"));

					/*String[] KetuAspect = astrobean.getAspect(Planet
							.toPlanets("Ketu"));
					String KetuAspectstr = "";

					if (KetuAspect != null) {

						for (int i = 0; i < KetuAspect.length; i++) {
							KetuAspectstr += KetuAspect[i] + "\n";
						}

					} else {
						KetuAspectstr = "NA";
					}*/

					String[] KetuConjuction = astrobean.getConjuction(Planet
							.toPlanets("Ketu"));
					String KetuConjuctionstr = "";

					if (KetuConjuction != null) {

						for (int i = 0; i < KetuConjuction.length; i++) {
							KetuConjuctionstr += KetuConjuction[i] + "\n";

						}

					} else {
						KetuConjuctionstr = "NA";
					}

					String KetuSignLoard = astrobean.getSignLoard(Planet
							.toPlanets("Ketu"));
					String KetuRashiLoard = astrobean.getRashiLoard(Planet
							.toPlanets("Ketu"));

					//astrobean.setAspectRahu(RahuAspectstr);
					//astrobean.setAspectKetu(KetuAspectstr);
					astrobean.setConjucRahu(RahuConjuctionstr);
					astrobean.setConjucKetu(KetuConjuctionstr);
					if (RahuSignLoard != null)
						astrobean.setSignLordRahu(RahuSignLoard);
					else
						astrobean.setSignLordRahu("NA");
					if (KetuSignLoard != null)
						astrobean.setSignLordKetu(KetuSignLoard);
					else
						astrobean.setSignLordKetu("NA");
					if (RahuRashiLoard != null)
						astrobean.setRashiLordRahu(RahuRashiLoard);
					else
						astrobean.setRashiLordRahu(RahuRashiLoard);
					if (KetuRashiLoard != null)
						astrobean.setRashiLordKetu(KetuRashiLoard);
					else
						astrobean.setRashiLordKetu(KetuRashiLoard);

					//fillPlanetMap(planetName);

					Kundli birth = astrobean.getBirthKundli();
					Kundli cusp = astrobean.getCuspKundli();
 
                    Kundli transitBirth=null,transitCusp=null;
                    if(generateKundli.getIsTransitKundli()==1){
                            transitBirth = transitAstroBean.getBirthKundli();
                            transitCusp = transitAstroBean.getCuspKundli();
                    }


					vimshottari = new LinkedHashMap<String, MahaDashaBean>();

					LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable = astrobean.getAntardashaDetailHashTable();

					Vector<MahaDashaBean> dasha = null;
					MahaDashaBean mahadashabean = null;
					antraDashamap = new LinkedHashMap<String, ArrayList<String>>();
					int x = 0;
					Iterator it = antardashaDetailHashTable.values().iterator();
					String start = "", end = "";
					while (it.hasNext()) {

						dasha = (Vector) it.next();

						ArrayList<String> list = new ArrayList<String>();

						for (int loop = 0; loop < dasha.size(); loop++) {
							mahadashabean = (MahaDashaBean) dasha.get(loop);
							if (mahadashabean.getStartTime() == "")
								mahadashabean.setStartTime("  ");
							if (mahadashabean.getEndTime() == "")
								mahadashabean.setEndTime("  ");
							list.add(mahadashabean.getPlanetName() + "#"
									+ mahadashabean.getStartTime() + "#"
									+ mahadashabean.getEndTime());

						}
						logger.info("HEREREREREEEE  TEST >>  "+dasha.get(0).getYear());
						antraDashamap.put(dasha.get(0).getYear(), list);
						x++;

						// for new dashaaaaaaa

						for (int loop = 0; loop < dasha.size(); loop++) {
							mahadashabean = (MahaDashaBean) dasha.get(loop);
							if (mahadashabean.getStartTime().contains("-")) {
								start = mahadashabean.getStartTime();
								break;
							}

						}
						MahaDashaBean dashaBean = new MahaDashaBean();
						dashaBean.setStartTime(start);
						dashaBean.setEndTime(dasha.get(dasha.size() - 1).getEndTime());

						vimshottari.put(mahadashabean.getParent(), dashaBean);

					}

					logger.info("####################### vimshottari map ["+vimshottari.toString()+"]");
					logger.info("####################### antraDashamap map ["+vimshottari.toString()+"]");
					String tmp = "";
					String[] tmpArr = null;
					String[] plntArr = null;

                                        String transitTmp = "";
                                        String[] transitTmpArr = null;
                                        String[] transitPlntArr = null;

					birthKundli = new HashMap<Integer, KundliHouseBean>();

					cuspKundli = new HashMap<Integer, KundliHouseBean>();
					KundliHouseBean bean = null;


					for (int i = 1; i <= 12; i++) {
						bean = new KundliHouseBean();
						tmpArr = birth.getHouseData(i).split("_");

						if (tmpArr.length > 1) {
							tmp = tmpArr[1];
							plntArr = tmp.split(" ");
							tmp = "";
							for (int j = 0; j < plntArr.length; j++) {
								tmp += " " + planetName.get(plntArr[j]);
							}
						}

						int hn = getHouse(i);
						bean.setHouseNumber(hn + "");
						bean.setPlanetName(tmp.trim());
						bean.setSignNumber(tmpArr[0]);
                                                // for transit birth chart
                                                if(generateKundli.getIsTransitKundli()==1) {
                                                        transitTmpArr = transitBirth.getHouseData(i).split("_");
                                                        if (transitTmpArr.length > 1) {
                                                                transitTmp = transitTmpArr[1];
                                                                transitPlntArr = transitTmp.split(" ");
                                                                transitTmp = "";
                                                                for (int j = 0; j < transitPlntArr.length; j++) {
                                                                        transitTmp += " " + planetName.get(transitPlntArr[j]);
                                                                }
                                                        }


                                                bean.setTransitHouseNumber(hn + "");
                                                bean.setTransitPlanetName(transitTmp.trim());
                                                bean.setTransitSignNumber(transitTmpArr[0]);
                                                }

						birthKundli.put(hn, bean);

						bean = new KundliHouseBean();
						tmp = "";
						tmpArr = cusp.getHouseData(i).split("_");
						if (tmpArr.length > 1) {
							tmp = tmpArr[1];
							plntArr = tmp.split(" ");
							tmp = "";
							for (int j = 0; j < plntArr.length; j++) {
								tmp += " " + planetName.get(plntArr[j]);
							}
						}
						bean.setHouseNumber(hn + "");
						bean.setPlanetName(tmp.trim());
						bean.setSignNumber(tmpArr[0]);
						// Change here for transit>>>>>
						if(generateKundli.getIsTransitKundli()==1) {
						if(tempTransitHashTable.containsKey(i)){
							bean.setTransitPlanetName(tempTransitHashTable.get(i));
						}
						else{
							bean.setTransitPlanetName("");
						}
						}
						//==============change ends here==============>>
						cuspKundli.put(hn, bean);
						bean=null;
						tmp = "";
                                                transitTmp="";
					}

				}

				if (generateKundli.getGenerateType().equals("E")) {

					if (retval < 0) {
						return "FAILURE";
					} else {

												return "SUCCESS";
					}
				} else {

					if (retval < 0) {
						return "FAILURE";
					} else {
						if (generateKundli.getGenerateType().equals("D")){

							return "SUCCESS1";

						}
						else if (generateKundli.getGenerateType().equals("P"))
                                                {
							
                                                        logger.info("returning success 3");
                                                        return "SUCCESS3";
                                                }

                                                else if (generateKundli.getGenerateType().equals("EV"))
                                                {
                                                        logger.info("returning success 4");
                                                        return "SUCCESS4";
                                                }
						else
							return "SUCCESS2";
					}

				}

			
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}

	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String download() {
		try {
			fileName=fileName.split("\\?")[0];
			logger.info("file "+fileName);
			File file = new File(fileName);
			fileInputStream = new FileInputStream(file);
			String[] tmp = fileName.split("/");
			setFileName(tmp[tmp.length - 1]);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}

		return "SUCCESS";

	}

	public String getKundliCircle() {
                try {
			HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();
                        if (sess == null || sess.isEmpty()) {
                                logger.info("SESSION EXPIRED !!!!!");
                                return "ERROR";
                        } else {
							logger.info("HERE CIRCLE IMAGE NAME>> "+kundliCircleName);
							String path=Constants.PROPERTIES_PATH;
			                path = path + "/kundliHttpserverNew.properties";
			                //	path = path + "/kundliHttpserverTest.properties";
							FileReader reader=new FileReader(path);
			    			Properties p=new Properties();  
			    			p.load(reader);
							path=p.getProperty("CIRCLE_IMAGE_PATH");
							//fileName=path+session.getAttribute("userName")+"_post.jpg";
							fileName=path+kundliCircleName;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.toString());
                        return "ERROR";
                }

                return "SUCCESS";

        }


	public String saveUserKundliInfo() throws Exception {

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();
			if (sess == null || sess.isEmpty())
			{
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else 
			{
				userDao = new UserProfileDaoImpl();
				generateKundli.setUserId(Integer.parseInt(sess));
				logger.info("user kundli info " + generateKundli.toString());
				int retval = userDao.saveDetails(generateKundli);
				if (retval > 0) 
				{
					message = "kundli user info has been successfully saved";
					logger.info("kundli user info has been successfully saved");
					return "SUCCESS";
				} else 
				{
					return "FAILURE";
				}

			}
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}

	public String displayKundliLogs() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				kundliLogsLst = userDao.getOldKundliLogs(Integer.parseInt(sess));
				logger.info("kundli Logs Lst size " + kundliLogsLst.size());
				return "SUCCESS";

			}
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}

	public String deleteOldKundliLog() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				int requestId = generateKundli.getRequestId();
				logger.info("RequestId " + requestId);
				int retValue = userDao.deleteKundliLog(requestId);
				if (retValue > 0) {
					return "SUCCESS";
				} else {
					return "FAILURE";
				}
			}
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}

	public String generateOldKundli() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				int requestId = generateKundli.getRequestId();
				logger.info("RequestId " + requestId+"  ,kundliType= "+kundliType);
				generateKundli = userDao.getGenerateKundliLog(requestId);
				if (generateKundli != null) {
					logger.info("Kundli Info " + generateKundli.toString());
                                        countryLst = userDao.getCountryList();
					stateLst = userDao.getStateList(generateKundli.getCountry());
					return "SUCCESS";
				} else {
					return "FAILURE";
				}
			}
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}


	public void updateKundliCircleFileName(String name) throws Exception {
                try {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
			session.setAttribute("KundliCircleName",name);
                       /* String sess = session.getAttribute("userName").toString();
                        if (sess == null || sess.isEmpty()) {
                                logger.info("SESSION EXPIRED !!!!!");
                                return "ERROR";
                        } else {*/
				logger.info("Inside updateKundliCircleFileName> "+name);
			//	map.put("KundliCircleName", name);
                       // }
                } 
		catch (Exception e) {
                         e.printStackTrace();
                        logger.error(e.toString());
		}
        }

	public String emailPdf() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				userInfo = userDao.getUserInfo(Integer.parseInt(sess));
				logger.info("user email id " + userInfo.getEmail());
				if (userInfo != null) {
					return "SUCCESS";
				} else {
					return "FAILURE";
				}

			}
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}

	private void fillPlanetMap(HashMap<String, String> map) {

		map.put("Ketu", "Ke");
		map.put("Venus", "Ve");
		map.put("Sun", "Su");
		map.put("Moon", "Mo");
		map.put("Mars", "Ma");
		map.put("Jupiter", "Ju");
		map.put("Saturn", "Sa");
		map.put("Mercury", "Me");
		map.put("Rahu", "Ra");
	}

	private void fillRashiNumberMap(HashMap<String, Integer> map) {

                map.put("Aries",1);
                map.put("Taurus",2);
                map.put("Gemini",3);
                map.put("Cancer",4);
                map.put("Leo",5);
                map.put("Virgo",6);
                map.put("Libra",7);
                map.put("Scorpio",8);
                map.put("Sagittarius",9);
                map.put("Capricorn",10);
                map.put("Aquarius",11);
                map.put("Pisces",12);
        }

	private void fillRashiNameMap(HashMap<Integer,String> map) {

                map.put(1,"Aries");
                map.put(2,"Taurus");
                map.put(3,"Gemini");
                map.put(4,"Cancer");
                map.put(5,"Leo");
                map.put(6,"Virgo");
                map.put(7,"Libra");
                map.put(8,"Scorpio");
                map.put(9,"Sagittarius");
                map.put(10,"Capricorn");
                map.put(11,"Aquarius");
                map.put(12,"Pisces");
        }	

	private String DDtoDMS(String dec, String var) {
		// Converts decimal format to DMS ( Degrees / minutes / seconds )
		String[] vars = dec.split("\\.");
		String deg = vars[0];
		Double tempma = Double.parseDouble("0." + vars[1]);

		tempma = tempma * 3600;
		Double min = Math.floor(tempma / 60);
		Double sec = tempma - (min * 60);

		return deg + "" + var + "" + min;
	}

	private int getHouse(int i) {
		int x = 0;
		switch (i) {
		case 1:
			x = 4;
			break;
		case 2:
			x = 1;
			break;
		case 3:
			x = 3;
			break;
		case 4:
			x = 6;
			break;
		case 5:
			x = 8;
			break;
		case 6:
			x = 11;
			break;
		case 7:
			x = 9;
			break;
		case 8:
			x = 12;
			break;
		case 9:
			x = 10;
			break;
		case 10:
			x = 7;
			break;
		case 11:
			x = 5;
			break;
		case 12:
			x = 2;
			break;
		default:
			break;
		}

		return x;
	}

//BY ANKUR--------------->>>
	public String getDay(){
                String day="";
                String dayLord="";
                logger.info("Inside function getDay()");
                try {
                        Date date = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        logger.info(">>HERERERE  DOB>> "+generateKundli.getDob());
                        date = sdf.parse(generateKundli.getDob());
                        sdf = new SimpleDateFormat("EEEE");
                        day=sdf.format(date);
                        logger.info("day>> "+day);
                        switch(day)
                        {
                                case "Sunday":
                                        dayLord="Sun";
                                        break;

                                 case "Monday":
                                        dayLord="Moon";
                                        break;

                                 case "Tuesday":
                                        dayLord="Mars";
                                        break;

                                 case "Wednesday":
                                        dayLord="Mercury";
                                        break;

                                 case "Thursday":
                                        dayLord="Jupiter";
                                        break;

                                case "Friday":
                                        dayLord="Venus";
                                        break;
                                 case "Saturday":
                                        dayLord="Saturn";
                                        break;

                        }
		}
                catch (Exception e) {
                        e.printStackTrace();
                }

        	return dayLord;
	}

	private String getScope() throws ParseException
        {
		String result="";
                String adlEnd1[]=null;
                String adlEnd="";
                String mdlEnd1[]=null;
                String mdlEnd="";
                logger.info("inside getScope");
                Kundli cusp = astrobean.getCuspKundli();
                String scope="";
                String[] tmpArr = null;
                int sign=-1;;
                String lord="";
                StringBuffer sb=new StringBuffer();
                tmpArr = cusp.getHouseData(1).split("_");
                logger.info("tmpArr:"+tmpArr.length);
                        if (tmpArr.length >= 1) {
                                scope=scope+"sign="+tmpArr[0]+";";
                              //  sb.append("sign="+tmpArr[0]);
                                sign=Integer.parseInt(tmpArr[0]);
				sb.append("sign="+getSignRashiMap(sign));
                        }
                        else{
                                sb.append("sign=");
                                scope=scope+"sign=;";
                        }
                        logger.info("scope after getting sign for first house>> "+scope);

                        logger.info("sookshmadasha"+astrobeanUser.getSookshmadashaDetailHashTable());

                        for (String str:planets)
                        {
                                logger.info("first rashi fot planet>> "+str+"  rashi>> "+Planet.toPlanets(str).getFirstRashi());
                                logger.info("second rashi fot planet>> "+str+"  rashi>> "+Planet.toPlanets(str).getSecondRashi());
                                if(Planet.toPlanets(str).getFirstRashi()==sign){
                                        lord=str;
                                }
                                else if(Planet.toPlanets(str).getSecondRashi()==sign){
                                        lord=str;
                                }

                        }
			sb.append(";lord="+lord);
                        scope=scope+"lord="+lord+";";
                        logger.info("scope after getting lord for first house>> "+scope);
                        LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable=astrobean.getPlanetDetailHashTable();
                        for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet()) {
                                logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue());


                                logger.info("Iterating map for lord");
                                if(entry.getKey().equals(lord))
                                {
                                        logger.info("key"+entry.getKey());
                                        PlanetDetailBean pl=entry.getValue();
                                        logger.info("planetDetailBean"+pl);
                                        sb.append(";NL="+pl.getNL());
                                }

                        logger.info("Iterating map for sun");
                        if(entry.getKey().equals("Sun"))
                        {
                                logger.info("key"+entry.getKey());
                                PlanetDetailBean pl=entry.getValue();
                                logger.info("planetDetailBean"+pl);
                                sb.append(";Sun="+pl.getRL());
                                sb.append("/");
                                sb.append(pl.getNL());
                                sb.append("/");
                                sb.append(pl.getSL());


                        }
			logger.info("Iterating map for Moon");

                                if(entry.getKey().equals("Moon"))
                                {
                                        logger.info("key"+entry.getKey());
                                        PlanetDetailBean pl=entry.getValue();
                                        logger.info("planetDetailBean"+pl);
                                        sb.append(";Moon="+pl.getRL());
                                        sb.append("/");
                                        sb.append(pl.getNL());
                                        sb.append("/");
                                        sb.append(pl.getSL());


                                }

}

                        String day=getDay();
                        sb.append(";Day Lord="+day);

                        logger.info("sb *****"+sb);
                         LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable=null;
                                                        Vector<MahaDashaBean> sookshamDashaVector=null;
                                                        Map<String,String> unsortMap=null;

                                                        sookshmadashaDetailHashTable = astrobeanUser.getSookshmadashaDetailHashTable();
                                                logger.info("sookshmadashaDetailHashTable"+sookshmadashaDetailHashTable);

                                                logger.info("sookshamDashaVector"+sookshamDashaVector);
                                                     sookshamDashaVector = new Vector<MahaDashaBean>();
                                                     unsortMap = new HashMap<String,String>();
                                                        String start="",end="";
						for(Iterator<String> itr=sookshmadashaDetailHashTable.keySet().iterator();itr.hasNext();)
                                                     {
                                                             String keyValue=itr.next();
                                                                if (keyValue.isEmpty())
                                                                continue;
                                                             logger.info("keyValue>> "+keyValue);
                                                             StringBuffer str=new StringBuffer();
                                                             str.append(keyValue.substring(6,10)+keyValue.substring(3,5)+keyValue.substring(0,2));
                                                             unsortMap.put(str.toString(),keyValue);

                                                     }
                                                     Map<String,String> sortedMap =  sortByComparator(unsortMap);

                                                        logger.info("sortedMap"+sortedMap);
                                                     for (Map.Entry entry : sortedMap.entrySet())
                                                     {


                                                             sookshamDashaVector = (Vector) sookshmadashaDetailHashTable.get(entry.getValue());
                                                                                        logger.info("sookshamDashaVector"+sookshamDashaVector);

                                                                     start=sookshamDashaVector.get(0).getStartTime().replace("  "," ");
                                                                     end=sookshamDashaVector.get(sookshamDashaVector.size() - 1).getEndTime().replace("  "," ");
                                                                        String date=generateKundli.getDob()+" "+generateKundli.getTob();


                                                                     	java.util.Date startDate1=new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").parse(date);
									
                                                                        java.util.Date currentStartDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(startDate1));

                                                                     java.util.Date startDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(start);
                                                                     java.util.Date endDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(end);
									 if((currentStartDate.compareTo(startDate)>=0)&&(currentStartDate.compareTo(endDate)<=0)){
                                                                              for (int i = 0; i < sookshamDashaVector.size(); i++)
                                                                             {
										parent = sookshamDashaVector.get(i).getParent();
                                                                                     child = sookshamDashaVector.get(i).getChild();
                                                                                     subChild = sookshamDashaVector.get(i).getSubChild();

                                                                             }
                                                                     }
                                                     }
		String mdlupto="",adlupto="",pdlupto="";
		LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable = astrobeanUser
                                                        .getAntardashaDetailHashTable();

                                        Vector<MahaDashaBean> dasha = null;
                                        MahaDashaBean mahadashabean = null;
                                        Iterator it = antardashaDetailHashTable.values().iterator();
                                        start = "";
                                        end = "";
                                        while (it.hasNext()) {

                                                dasha = (Vector) it.next();

                                                for (int loop = 0; loop < dasha.size(); loop++) {
                                                        mahadashabean = (MahaDashaBean) dasha.get(loop);
                                                        if (mahadashabean.getStartTime().contains("-")) {
                                                                start = mahadashabean.getStartTime();
                                                                break;
                                                        }
                                                }
                                                end=dasha.get(dasha.size() - 1).getEndTime();

                                        String date=generateKundli.getDob()+" "+generateKundli.getTob();

                                                                        logger.info("start"+start+"end:"+end+"date:"+date);

                                                                        java.util.Date startDate1=new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").parse(date);

                                                                        java.util.Date currentStartDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(startDate1));
                                                                     logger.info("start"+start+"end:"+end+"date:"+date+"    currentStartDate>> "+currentStartDate);

                                                                     java.util.Date startDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(start);
                                                                     java.util.Date endDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(end);
				 logger.info("currentStartDate"+currentStartDate+"startDate:"+startDate+"end:"+endDate);
                                                                        logger.info("currentStartDate.compareTo(startDate)"+currentStartDate.compareTo(startDate)+" >>currentStartDate.compareTo(endDate)  "+currentStartDate.compareTo(endDate));
                                                if((currentStartDate.compareTo(startDate)>=0)&&(currentStartDate.compareTo(endDate)<=0)){
									parent=mahadashabean.getPlanetName();
									mdlupto=end;
                                                                        scope=scope+"MDL:"+mahadashabean.getPlanetName()+";MDLUpTO:"+end;

                                                        for (int loop = 0; loop < dasha.size(); loop++) {
                                                                mahadashabean = (MahaDashaBean) dasha.get(loop);

                                                                if (!mahadashabean.getStartTime().isEmpty() && !mahadashabean.getEndTime().isEmpty())
                                                                {
                                                                        java.util.Date startDateAntar=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(mahadashabean.getStartTime());
                                                                        java.util.Date endDateAntar=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(mahadashabean.getEndTime());
                                                                        if((currentStartDate.compareTo(startDateAntar)>=0)&&(currentStartDate.compareTo(endDateAntar)<=0))
                                                                        {
                                                                                logger.info("startDateAntar>> "+startDateAntar+"endDateAntar>> "+endDateAntar);

                                                                                scope=scope+"ADL:"+mahadashabean.getPlanetName()+";ADLUpTo:"+mahadashabean.getEndTime();
//.............................................................................
										child=mahadashabean.getPlanetName();
										adlupto=mahadashabean.getEndTime();
//==================================================================================================
                                                                                CalculateVimshottari vimshottari= new CalculateVimshottari();
                                                                                String json=(vimshottari.calculate(mahadashabean.getStartTime(),mahadashabean.getEndTime(), mahadashabean.getPlanetName(),mahadashabean.getPlanetName())).toString();
                                                                                logger.info("PDL JSON>>>> "+json);


                                                                                Object obj = JSONValue.parse(json);
                                                                                JSONArray array=(JSONArray)obj;
                                                                                for(int i=0;i<array.size();i++)
                                                                                {
                                                                                        JSONObject jsonObject=(JSONObject)array.get(i);
                                                                                        String startPDL=((String)jsonObject.get("start")).trim();
                                                                                        String endPDL=((String)jsonObject.get("end")).trim();
                                                                                        if (!startPDL.isEmpty() && !endPDL.isEmpty())
                                                                                        {
                                                                                        java.util.Date startDatePDL=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(startPDL);
                                                                                        java.util.Date endDatePDL=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(endPDL);
                                                        if((currentStartDate.compareTo(startDatePDL)>=0)&&(currentStartDate.compareTo(endDatePDL)<=0)){
								scope=scope+"PDL:"+jsonObject.get("planet")+";"+"PDLUpTo:"+endPDL+";";
								subChild=(String)jsonObject.get("planet");
								pdlupto=endPDL;
								break;
							}
                                                        				}
                                                                              	}
                                                                       	}
                                                               }
                                                                }

                                                        }

                                                }


                                        logger.info("FINAL SCOPE STRINGGGGGGG>> "+scope);			









                                                sb.append(";MDL="+parent+";ADL="+child+";PDL="+subChild);
                                        logger.info("calling for rashi");

                                        if(!(parent.isEmpty() || child.isEmpty() || subChild.isEmpty())){
                                               sb.append(getDegreeAndRashi());

                                                Vector<DashaStrength> obj = astrobeanUser.getDashaStrength();
						if (obj.size() != 0) {
                                        DashaStrength dashaBean = (DashaStrength) obj.get(0);

                                                        logger.info("Current palnet name is"
                                                                        + dashaBean.getNatalStrengthChart());
                                                        String adlEndTime[][]= dashaBean.getNatalStrengthChart();
                                                           for(int i=0;i<adlEndTime.length;i++)
                                                        {



                                                                if(adlEndTime[i][0].equals(child))
                                                                {
                                                                        adlEnd=adlEndTime[i][6];
                                                                        break;
                                                                }
                                                        }

                                        }

                                                   String mdlEndTime[][] = astrobeanUser.getNatalStrengthChart();

                                                for(int i=0;i<mdlEndTime.length;i++)
                                                        {



                                                                if(mdlEndTime[i][0].equals(parent))
                                                                {
                                                                        mdlEnd=mdlEndTime[i][6];
                                                                        break;
                                                                }
                                                        }
                                                        logger.info("sb:"+sb+"  mdlEnd:"+mdlEnd);
                                                         adlEnd1=adlEnd.split(" ");

                                                         mdlEnd1=mdlEnd.split(" ");
							 logger.info("sb:"+sb+"  mdlEnd:"+mdlEnd);
                                               // sb.append(";mdlUpto="+mdlEnd1[0]);
                                               // sb.append(";adlUpto="+adlEnd1[0]);
                                                 logger.info("sb:"+sb+"  mdlEnd:"+mdlEnd);
						

						mdlupto=mdlupto.replace("  "," ");
						adlupto=adlupto.replace("  "," ");
						pdlupto=pdlupto.replace("  "," ");
						sb.append(";mdlUpto="+mdlupto);
						sb.append(";adlUpto="+adlupto);
						sb.append(";pdlUpto="+pdlupto);
                                        }



		 LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable1=astrobean.getPlanetDetailHashTable();
                        for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable1.entrySet()) {
                                logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue());


                        logger.info("Iterating map for mdlNLSL");
                        if(entry.getKey().equals(parent))
                        {
                                PlanetDetailBean pl=entry.getValue();
                                logger.info("key>>>"+entry.getKey()+"NL>> "+pl.getNL()+"   SL>> "+pl.getSL());
                               // logger.info("planetDetailBean"+pl);
                                sb.append(";mdlNLSL="+pl.getNL());
                                sb.append("/");
                                sb.append(pl.getSL());
                        }

                        logger.info("Iterating map for adlNLSL");
                        if(entry.getKey().equals(child))
                        {
                                PlanetDetailBean pl=entry.getValue();
                                logger.info("key>>>"+entry.getKey()+"NL>> "+pl.getNL()+"   SL>> "+pl.getSL());
				
                              //  logger.info("planetDetailBean"+pl);
                                sb.append(";adlNLSL="+pl.getNL());
                                sb.append("/");
                                sb.append(pl.getSL());
                        }
                        logger.info("Iterating map for pdlNLSL");
                        if(entry.getKey().equals(subChild))
                        {
                                PlanetDetailBean pl=entry.getValue();
                                logger.info("key>>>"+entry.getKey()+"NL>> "+pl.getNL()+"   SL>> "+pl.getSL());
                                sb.append(";pdlNLSL="+pl.getNL());
                                sb.append("/");
                                sb.append(pl.getSL());
                        }

                }








//logger.info("finalsb:"+sb);
return result=sb.toString();
        }

	public String getSignRashiMap(int sign)
        {
                String value="";
                Map<Integer,String> map= new HashMap<Integer,String>();
                map.put(1,"Aries");
                map.put(2,"Tauras");
                map.put(3,"Gemini");
                map.put(4,"Cancer");
                map.put(5,"Leo");
                map.put(6,"Virgo");
                map.put(7,"Libra");
                map.put(8,"Scorpio");
                map.put(9,"Sagittarius");
                map.put(10,"Capricorn");
                map.put(11,"Aquaris");
                map.put(12,"Pisces");

                for (Integer key : map.keySet()) {
                if(key==sign){
                 value = map.get(key);
                logger.info("Key = " + key + ", Value = " + value);
                }

                }
                return value;
        }

          public static Map sortByComparator(Map unsortMap)
                                             {

                                                     List list = new LinkedList(unsortMap.entrySet());

                                                     Collections.sort(list, new Comparator()
                                                     {
                                                        public int compare(Object o1, Object o2)
                                                             {
                                                                     return ((Comparable) ((Map.Entry) (o1)).getKey()).compareTo(((Map.Entry) (o2)).getKey());
                                                             }
                                                     });

                                                     Map sortedMap = new LinkedHashMap();
                                                     for (Iterator it = list.iterator(); it.hasNext();)
                                                     {
                                                             Map.Entry entry = (Map.Entry)it.next();
                                                             sortedMap.put(entry.getKey(), entry.getValue());
                                                     }

                                                     return sortedMap;
                                             }


		
	public String getDegreeAndRashi()
        {
                        String str="";
                        StringBuffer sb1=new StringBuffer();

                        LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable=astrobeanUser.getPlanetDetailHashTable();
                             for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet()) {
                                logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue());


                                logger.info("Iterating map for mdl");
                                if(entry.getKey().equals(parent))
                                {
                                        logger.info("key"+entry.getKey());
                                        PlanetDetailBean pl=entry.getValue();
                                        logger.info("planetDetailBean"+pl);
                                        sb1.append(";mdlDegSign="+pl.getDegree());
                                        sb1.append("/"+pl.getSignName());

                                }


                                logger.info("Iterating map for adl");
                                if(entry.getKey().equals(child))
                                {
                                        logger.info("key"+entry.getKey());
                                        PlanetDetailBean pl=entry.getValue();
                                        logger.info("planetDetailBean"+pl);
                                        sb1.append(";adlDegSign="+pl.getDegree());
                                        sb1.append("/"+pl.getSignName());
                                }

                                logger.info("Iterating map for pdl");
                                if(entry.getKey().equals(subChild))
                                {
                                        logger.info("key"+entry.getKey());
                                        PlanetDetailBean pl=entry.getValue();
                                        logger.info("planetDetailBean"+pl);
                                        sb1.append(";pdlDegSign="+pl.getDegree());
                                        sb1.append("/"+pl.getSignName());

                                }
 	       }
		return str=sb1.toString();
        }


	public String displayEventList() throws Exception {
                try {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();

                        if (sess == null || sess.isEmpty()) {
                                logger.info("SESSION EXPIRED !!!!!");
                                return "ERROR";
                        } else 
                        {
				             int userId=Integer.parseInt(sess);
                             String action=properties.get("CLICK");
                             String remark="View Event List";
                             String detail = request.getRemoteAddr();
                             userDao = new UserProfileDaoImpl();
                             eventLogsLst=userDao.getEventLogs(Integer.parseInt(sess));
                             logger.info("eventlist:"+this.getEventLogsLst());
				             userDao.userClickedLink(userId,action,remark,detail);
                             userDao.writeLogToFile(userId, action, remark, detail);
                             return "SUCCESS";

                        }
                } catch (Exception e) {
                         e.printStackTrace();
                        logger.error(e.toString());
                        return "ERROR";
                } finally {
                        if (userDao != null) {
                                userDao = null;
                        }
                }
        }	
	

	public String showEventLog() throws Exception {
                 try {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();
                        if (sess == null || sess.isEmpty()) {
                                logger.info("SESSION EXPIRED !!!!!");
                                return "ERROR";
                        } else {
                                userDao = new UserProfileDaoImpl();
				                newNatalStrengthChart =new String[9][6];
				                int userId=Integer.parseInt(sess);
                                String result=userDao.getUserKundli(userId);
                            	astrobeanUser=new Gson().fromJson(result, AstroBean.class);
                            	String arr[][]= astrobeanUser.getNatalStrengthChart();
                               	for(int i=0; i<arr.length; i++) 
				                  {
                            		for(int j=0; j<arr[i].length-1; j++) 
					                   {
                                        	newNatalStrengthChart[i][j]=arr[i][j];
                                         	logger.info("Values at newNatalStrengthChart["+i+"]["+j+"] is "+newNatalStrengthChart[i][j]);

                                        }
                             	  }

                                int eventId = event.getEventId();
                                logger.info("eventId " + eventId);
                                event = userDao.getGenerateEventLog(eventId);
                                if (event != null) 
                                {
                                        logger.info("event value");
					                     String action=properties.get("CLICK");
                                         String remark="Viewed Event For Event ID = "+eventId;
                                         String detail = request.getRemoteAddr();
                                         userDao.userClickedLink(userId,action,remark,detail);
                                         userDao.writeLogToFile(userId, action, remark, detail);
                                        return "SUCCESS";
                                } 
                                else 
                                {
                                        return "FAILURE";
                                }
                        }
                } catch (Exception e) {
                         e.printStackTrace();
                        logger.error(e.toString());
                        return "ERROR";
                } finally {
                        if (userDao != null) {
                                userDao = null;
                        }
                }
        }


	public String deleteOldEventLog() throws Exception {
                try {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();

                        if (sess == null || sess.isEmpty()) {
                                logger.info("SESSION EXPIRED !!!!!");
                                return "ERROR";
                        } else {
                                userDao = new UserProfileDaoImpl();
                                int eventId = event.getEventId();
                                logger.info("eventId " + eventId);
                                int retValue = userDao.deleteEventLog(eventId);
                                if (retValue > 0) {
					int userId=Integer.parseInt(sess);
                                                        String action=properties.get("CLICK");
                                                        String remark="Delete Event For Event ID = "+eventId;
                                                        String detail = request.getRemoteAddr();
                                                        userDao.userClickedLink(userId,action,remark,detail);
                                                        userDao.writeLogToFile(userId, action, remark, detail);
                                        return "SUCCESS";
                                } else {
                                        return "FAILURE";
                                }
                        }
                } catch (Exception e) {
                        logger.error(e.toString());
                        return "ERROR";
                } finally {
                        if (userDao != null) {
                                userDao = null;
                        }
                }
        }
	
	public String generateOldDetail() throws Exception {
                try {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();
                        if (sess == null || sess.isEmpty()) {
                                logger.info("SESSION EXPIRED !!!!!");
                                return "ERROR";
                        } else {
                                userDao = new UserProfileDaoImpl();
                                int userId=Integer.parseInt(sess);
				String action=properties.get("CLICK");
                                String remark="View Log-Book Profile";
                                String detail = request.getRemoteAddr();

                                logger.info("UserId " + userId);
                                generateKundli = userDao.getGenerateLogBook(userId);
                                if (generateKundli != null) {
                                        logger.info("Kundli Info " + generateKundli.toString());
                                        countryLst = userDao.getCountryList();
                                        stateLst = userDao.getStateList(generateKundli.getCountry());
                                        logger.info("returning success from generate Old detail");
					userDao.userClickedLink(userId,action,remark,detail);
                                        userDao.writeLogToFile(userId, action, remark, detail);
                                        return "SUCCESS";
                                } else {
                                        return "FAILURE";
                                }
                        }
                } catch (Exception e) {
                        logger.error(e.toString());
                        return "ERROR";
                } finally {
                        if (userDao != null) {
                                userDao = null;
                        }
                }
        }

	public String getCountryState()
        {
                          HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession(false);
                        String state1 = session.getAttribute("state").toString();
                                                        String country1 = session.getAttribute("country").toString();

        	logger.info("inside get Country state");

                userDao=new UserProfileDaoImpl();

                String state=userDao.getState(state1,country1);

                logger.info("state name **"+state);


                 String country=userDao.getCountry(country1);
                        session.setAttribute("state",state);
                                                session.setAttribute("country",country);

                                logger.info("country name **"+country);
                return "SUCCESS";
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
		logger.debug("decDegrees>>  "+decDegrees);
		return decDegrees;
		}
		 catch (Exception e) {
                        logger.error(e.toString());
			return decDegrees;
                }
	}

}
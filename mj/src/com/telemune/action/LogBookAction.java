package com.telemune.action;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
//import com.telemune.mobileAstro.MyHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.telemune.dao.LogBookProfileDao;
import com.telemune.action.MyHandler;
import com.telemune.dao.LogBookProfileDaoImpl;
/*import com.telemune.mobileAstro.AstroBean;
import com.telemune.mobileAstro.CalculateVimshottari;
import com.telemune.mobileAstro.DashaStrength;
import com.telemune.mobileAstro.Kundli;
import com.telemune.mobileAstro.KundliHouseBean;
import com.telemune.mobileAstro.MahaDashaBean;
import com.telemune.mobileAstro.Planet;
import com.telemune.mobileAstro.PlanetDetailBean;*/
import com.telemune.model.CountryData;
import com.telemune.model.Country;
import com.telemune.model.*;
import com.telemune.model.Event;
import com.telemune.model.GenerateKundli;
import com.telemune.model.StateData;
import com.telemune.model.UserInfo;

public class LogBookAction  extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(LogBookAction.class);
	private LogBookProfileDao logBookDao = null;
	private String fileName = "";
	private String message = "";
	private String kundliCircleName = "";
	private GenerateKundli generateKundli;
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

	public void setSession(Map session) {
		map = session;
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
	
	
	public void logBook()
	{
			
                logger.info("Inside logBook  method of LogBookAction action class.");
		try{
		   HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();

		boolean flag = false;
                MyHandler myhandler = new MyHandler();
		    generateKundli = new GenerateKundli();
                                generateKundli.setName((String) session.getAttribute("kundliName"));
                                generateKundli.setDob((String) session.getAttribute("dob"));
                                generateKundli.setUserId((Integer) session.getAttribute("userName"));
                                generateKundli.setTob((String) session.getAttribute("tob"));
                                generateKundli.setCity((String) session.getAttribute("pob"));
                                generateKundli.setCityCode((String) session.getAttribute("cityCode"));
                                generateKundli.setState((String) session.getAttribute("state"));
                                generateKundli.setCountry((String) session.getAttribute("country"));
                                generateKundli.setLatitude((String) session.getAttribute("lat"));
                                generateKundli.setLongitude((String) session.getAttribute("long"));
                                generateKundli.setTransitDob((String) session.getAttribute("transitDob"));
                                generateKundli.setTransitTob((String) session.getAttribute("transitTob"));
                                generateKundli.setRemarks((String) session.getAttribute("remarks"));
                                generateKundli.setIsTransitKundli(Integer.parseInt(session.getAttribute("isTransitKundli").toString()));
                                generateKundli.setRequestId((Integer) session.getAttribute("requestId"));
                                generateKundli.setKundliCircleName(kundliCircleName);
                                if(userInfo !=null){
                                        logger.info("user email "+userInfo.getEmail());
                                        generateKundli.setEmail(userInfo.getEmail());
                                        generateKundli.setHouseDetail(userInfo.isHouseDetail());
                                        generateKundli.setDasha(userInfo.isDashaDetail());
                                }


                                logger.info("genType>> SUBMIT>> "+genType);
                                if ((String) session.getAttribute("16") != null)
                                        generateKundli.setCircle(true);

                                if ((String) session.getAttribute("17") != null)
                                        generateKundli.setAspectChart(true);

                                if ((String) session.getAttribute("20") != null)
                                        generateKundli.setNakshatraPadam(true);

                                if (genType != null && genType != "")
                                        generateKundli.setGenerateType(genType);

                                if (generateKundli.getGenerateType().equalsIgnoreCase("E")) {
                                        flag = true;
				}
                logger.info("inside log Details state"+sess);
		astrobean = myhandler.msgExtraction(generateKundli, flag);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info(e.toString());
		}
		}

		public String logDetails()
		{
			logger.info("inside logDetails  method of LogBook action");

		logBook();
    HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();

	
		String xmlData=astrobean.getKundliData();
		astrobean.setKundliData("");

		String jsonStr= new Gson().toJson(astrobean);
		generateKundli.setKundliData(xmlData);
		logBookDao=new LogBookProfileDaoImpl();
		 int userId=Integer.parseInt(sess);
		boolean result=logBookDao.isUserExist(userId);
		logger.info("result from is UserExist"+result);
		if(result)
			logBookDao.updateDetail(generateKundli,jsonStr,xmlData);
		else
			logBookDao.saveDetail(generateKundli,jsonStr,xmlData);
		return "SUCCESS";
	}

	public String getCountryState()
	{
		logger.info("inside getCountryState method of LogBook action class");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		String state1 = session.getAttribute("state").toString();
		String country1 = session.getAttribute("country").toString();

		logger.info("inside getCountry state");

		logBookDao=new LogBookProfileDaoImpl();

		String state=logBookDao.getState(state1,country1);

		logger.info("state name **"+state);


		String country=logBookDao.getCountry(country1);
		session.setAttribute("state1",state);
		session.setAttribute("country1",country);

		logger.info("country name **"+country);
		return "SUCCESS";
	}

	public String displayEventList() throws Exception {
		logger.info("inside displayEventList method of LogBook action class");
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				logBookDao = new LogBookProfileDaoImpl();
				eventLogsLst=logBookDao.getEventLogs(Integer.parseInt(sess));
				logger.info("eventlist:"+this.getEventLogsLst());
				return "SUCCESS";

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (logBookDao != null) {
				logBookDao = null;
			}
		}
	}	

	public String showEventLog() throws Exception {
		try {
			logger.info("inside showEventLog  method of LogBook action");
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				logBookDao = new LogBookProfileDaoImpl();
				newNatalStrengthChart =new String[9][6];
				int userId=Integer.parseInt(sess);
				String result=logBookDao.getUserKundli(userId);
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
				event = logBookDao.getGenerateEventLog(eventId);
				if (event != null) {
					logger.info("event value");
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
			if (logBookDao != null) {
				logBookDao = null;
			}
		}
	}


	public String deleteOldEventLog() throws Exception {
		try {
			logger.info("inside displayOldEvent log  method of LogBook action");
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				logBookDao = new LogBookProfileDaoImpl();
				int eventId = event.getEventId();
				logger.info("eventId " + eventId);
				int retValue = logBookDao.deleteEventLog(eventId);
				if (retValue > 0) {
					return "SUCCESS";
				} else {
					return "FAILURE";
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (logBookDao != null) {
				logBookDao = null;
			}
		}
	}

	public String generateOldDetail() throws Exception {
		logger.info("inside display generateOldDetail method of LogBook action");

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				logBookDao = new LogBookProfileDaoImpl();
				int userId=Integer.parseInt(sess);
				logger.info("UserId " + userId);
				generateKundli = logBookDao.getGenerateLogBook(userId);
				if (generateKundli != null) {
					logger.info("Kundli Info " + generateKundli.toString());
					countryLst = logBookDao.getCountryList();
					stateLst = logBookDao.getStateList(generateKundli.getCountry());
					logger.info("returning success from generate Old detail");
					return "SUCCESS";
				} else {
					return "FAILURE";
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (logBookDao != null) {
				logBookDao = null;
			}
		}
	}

	public String allEventDetails() throws ParseException
	{
	
	logBook();
	  logger.info("inside allEventDetails method of LogBook action class");

                   HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = session.getAttribute("userName").toString();
                                                      int userId=Integer.parseInt(sess);

                                        logBookDao=new LogBookProfileDaoImpl();
                                        String result=logBookDao.getUserKundli(userId);
                                        astrobeanUser=new Gson().fromJson(result, AstroBean.class);
                                        String scope=getScope();
                                        generateKundli.setScope(scope);
                                        String xmlData=astrobean.getKundliData();
                                        astrobean.setKundliData("");
                                        String jsonStr= new Gson().toJson(astrobean);
                                        generateKundli.setKundliData(xmlData);
                                        logBookDao=new LogBookProfileDaoImpl();
                                       	logBookDao.saveEventDetail(generateKundli,jsonStr,xmlData);

	return "SUCCESS";
	
	}

	private String getScope() throws ParseException
    	{
	logger.info("inside getScope method of LogBook action class");
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
                    if (tmpArr.length >= 1) {
                            scope=scope+"sign="+tmpArr[0]+";";
                            sign=Integer.parseInt(tmpArr[0]);
			sb.append("sign="+getSignRashiMap(sign));
                    }
                    else{
                            sb.append("sign=");
                            scope=scope+"sign=;";
                    }


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
		sb.append(";Sun="+getSunRLNLSL());
                sb.append(";Moon="+getMoonRLNLSL());
                sb.append(";NL="+getLordNL(lord));
	
                    scope=scope+"lord="+lord+";";
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

                                                                    logger.info("start"+start+" end:"+end+" date:"+date+" antardashaDetailHashTable:"+antardashaDetailHashTable);

                                                                    java.util.Date startDate1=new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").parse(date);

                                                                    java.util.Date currentStartDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(startDate1));
                                                                 logger.info("start"+start+"end:"+end+"date:"+date+"    currentStartDate>> "+currentStartDate);

                                                                 java.util.Date startDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(start);
                                                                 java.util.Date endDate=new SimpleDateFormat("dd-MM-yyy HH_mm_ss").parse(end);
//			 logger.info("currentStartDate"+currentStartDate+"startDate:"+startDate+"end:"+endDate);
                                                                    logger.info("currentStartDate.compareTo(startDate)"+currentStartDate.compareTo(startDate)+" >>currentStartDate.compareTo(endDate)  "+currentStartDate.compareTo(endDate));
                                            if((currentStartDate.compareTo(startDate)>=0)&&(currentStartDate.compareTo(endDate)<=0)){
								parent = mahadashabean.getPlanetName();

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
//                                                                            logger.info("startDateAntar>> "+startDateAntar+"endDateAntar>> "+endDateAntar);

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
					if(!(parent.isEmpty())) 
					{
					mdlupto=mdlupto.replace("  "," ");
                                        String[] mdlDegSign=getDegreeAndRashi(parent).split("/");
                                        String[] mdlNLSL=getNLSL(parent).split("/");
					sb.append(";MDL="+parent+";mdlUpto="+mdlupto+";mdlDeg="+mdlDegSign[0]+";mdlSign="+mdlDegSign[1]+";mdlNL="+mdlNLSL[0]+";mdlSL="+mdlNLSL[1]);

					}
					else
					sb.append(";MDL=NA;mdlUpto=NA;mdlDeg=NA;mdlSign=NA;mdlNL=NA;mdlSL=NA");
					
					 if(!(child.isEmpty()))
					{
                                        String[] adlDegSign=getDegreeAndRashi(child).split("/");
                                        String[] adlNLSL=getNLSL(child).split("/");
                                        adlupto=adlupto.replace("  "," ");
					sb.append(";ADL="+child+";adlUpto="+adlupto+";adlDeg="+adlDegSign[0]+";adlSign="+adlDegSign[1]+";adlNL="+adlNLSL[0]+";adlSL="+adlNLSL[1]);
					
					}
					else
                                        sb.append(";ADL=NA;adlUpto=NA;adlDeg=NA;adlSign=NA;adlNL=NA;adlSL=NA");

					if(!(subChild.isEmpty())){
                                        pdlupto=pdlupto.replace("  "," ");
					String[] pdlDegSign=getDegreeAndRashi(subChild).split("/");
					String[] pdlNLSL=getNLSL(subChild).split("/");
                                        sb.append(";PDL="+subChild+";pdlUpto="+pdlupto+";pdlDeg="+pdlDegSign[0]+";pdlSign="+pdlDegSign[1]+";pdlNL="+pdlNLSL[0]+";pdlSL="+pdlNLSL[1]);

					}
					else
                                        sb.append(";PDL=NA;pdlUpto=NA;pdlDeg=NA;pdlSign=NA;pdlNL=NA;pdlSL=NA");
	logger.info("finalsb:"+sb);
	return result=sb.toString();

	}	

		public String getLordNL(String lord)
		{

		StringBuffer sb1=new StringBuffer();
                        String str="";

		     LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable=astrobean.getPlanetDetailHashTable();
                    for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet()) {
                            logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue());


                            logger.info("Iterating map for lord");
                            if(entry.getKey().equals(lord))
                            {
                                    logger.info("key"+entry.getKey());
                                    PlanetDetailBean pl=entry.getValue();
                                    logger.info("planetDetailBean"+pl);
                                    str=pl.getNL();
					break;
                            }
	
		
		}
			return str;
		}
		
		public String getSunRLNLSL()
		{
			logger.info("inside getSunRLNLSL method of LogBook action");

			 StringBuffer sb1=new StringBuffer();
                        String str="";

		    LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable=astrobean.getPlanetDetailHashTable();
                    for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet()) {

			 if(entry.getKey().equals("Sun"))
                    {
                            logger.info("key"+entry.getKey());
                            PlanetDetailBean pl=entry.getValue();
                            logger.info("planetDetailBean"+pl);
                            sb1.append(pl.getRL());
                            sb1.append("/");
                            sb1.append(pl.getNL());
                            sb1.append("/");
                            sb1.append(pl.getSL());
			    break;
			
                    }

		}
		return str=sb1.toString();
	 }
		
		public String getMoonRLNLSL()
		{
		          logger.info("inside getMoonRLNLSL method of LogBook action class");
	
			  StringBuffer sb1=new StringBuffer();
                        String str="";
			 LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable=astrobean.getPlanetDetailHashTable();
                    for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet()) {

                         if(entry.getKey().equals("Moon"))
                    {
                            logger.info("key"+entry.getKey());
                            PlanetDetailBean pl=entry.getValue();
                            logger.info("planetDetailBean"+pl);
                            sb1.append(pl.getRL());
                            sb1.append("/");
                            sb1.append(pl.getNL());
                            sb1.append("/");
                            sb1.append(pl.getSL());
			   break;

                    }

                }
			return str=sb1.toString();	
		}

		public String getNLSL(String nlsl)
		{
			logger.info("Inside getNLSL method of LogBook action class");
			StringBuffer sb1=new StringBuffer();
			String str="";
		logger.info("planet>>>"+nlsl);	
	 LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable1=astrobean.getPlanetDetailHashTable();
                    for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable1.entrySet()) {
                            logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue());


                    logger.info("Iterating map for mdlNLSL");
                    if(entry.getKey().equals(nlsl))
                    {
                            PlanetDetailBean pl=entry.getValue();
                            sb1.append(pl.getNL());
                            sb1.append("/");
                            sb1.append(pl.getSL());
				break;
                    }
		str=sb1.toString();
            }

	return str=sb1.toString();
    }
	
	
	
	public String getDay(){
        String day="";
        String dayLord="";
        logger.info("Inside  getDay() method of LogBook action class");
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
	
	public String getSignRashiMap(int sign)
    	{
        logger.info("Inside  getSignRashiMap  method of LogBook action class");

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
	
	public String getDegreeAndRashi(String dl)
    	{
	        logger.info("Inside  getDegreeAndRashi  method of LogBook action class");

                    String str="";
	           int count=0;
                    StringBuffer sb1=new StringBuffer();
			boolean found =false;
                    LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable=astrobeanUser.getPlanetDetailHashTable();
                         for (Map.Entry<String, PlanetDetailBean> entry : planetDetailHashTable.entrySet()) {
                            logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue());


                            logger.info("Iterating map for mdl");
                            if(entry.getKey().equals(dl))
                            {
				count++;
				   logger.info("found "+dl);
				    logger.info("key"+entry.getKey());
                                    PlanetDetailBean pl=entry.getValue();
                                    logger.info("planetDetailBean"+pl);
                                    sb1.append(pl.getDegree());
                                    sb1.append("/"+pl.getSignName());
					break;			
                            }
				
                        }

	logger.info("Before returning:"+sb1+"  count:"+count);
	return str=sb1.toString();
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
	
	
	}

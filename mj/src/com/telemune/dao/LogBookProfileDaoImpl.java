package com.telemune.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.CountryData;
import com.telemune.model.GenerateKundli;
import com.telemune.model.HttpLinks;
import com.telemune.model.RoleInfo;
import com.telemune.model.StateData;
import com.telemune.model.UserInfo;
import com.telemune.model.UserLinkDescription;
import com.telemune.model.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LogBookProfileDaoImpl implements LogBookProfileDao {

	private static final Logger logger = Logger.getLogger(LogBookProfileDaoImpl.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;



	


	@Override
	public List<CountryData> getCountryList() {
		List<CountryData> countryLst = new ArrayList<CountryData>();
		try {
			logger.info("inside, getting country lst from DB");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from country";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CountryData countryData = new CountryData();
				countryData.setName(rs.getString("name"));
				countryData.setCode(rs.getString("code"));
				countryData.setIsStateLst(rs.getInt("is_state_list"));
				countryLst.add(countryData);
			}
		} catch (Exception e) {
			logger.error("catch block of reading country lst from DB "
					+ e.toString());
			return countryLst;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					TSSJavaUtil.instance().freeConnection(con);
				}
			} catch (Exception e) {
				logger.error("finally block of reading country lst from DB "
						+ e.toString());
				return countryLst;
			}
		}
		return countryLst;
	}

	public List<StateData> getStateList(String contryCode) {
		logger.info("inside getting state list from DB where contryCode "
				+ contryCode);
		List<StateData> stateLst = new ArrayList<StateData>();
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from state where coun_code=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, contryCode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StateData stateData = new StateData();
				stateData.setSname(rs.getString("name"));
				stateData.setScode(rs.getString("code"));
				stateData.setCntryCode(rs.getString("coun_code"));
				stateLst.add(stateData);
			}
			return stateLst;
		} catch (Exception e) {
			logger.error("catch block of getting state list  " + e.toString());
			return stateLst;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("finally block of getting state list  "
						+ e.toString());
				return stateLst;
			}
		}
	}
        


	public String getUserKundli(int uId) {
                String result="";
                logger.info("inside getUserKundli according to userId[" + uId + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select kundli_data_json from user_profile where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, uId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                result=rs.getString("kundli_data_json");
                        }
                } catch (Exception e) {
                        logger.error("catch block of getting user kundli  " + e.toString());
                        return result;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of getting user kundli  "
                                                + e.toString());
                        }
                }
                return result;
        }

	
	@Override
        public Event getGenerateEventLog(int eventId) {
                Event event = new Event();
                try {
                        logger.info("Inside reading old event log by eventId "
                                        + eventId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from event_data where event_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, eventId);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                event.setUserId(rs.getInt("USER_ID"));
                                event.setEventId(rs.getInt("event_id"));
                                event.setEventName(rs.getString("event_name"));
                               String[] dob = rs.getString("event_date").split("-");

                                event.setEventDate(dob[2]+"-"+dob[1]+"-"+dob[0]);
                                logger.info("eventDate:"+event.getEventDate());
                                String input=rs.getString("event_time");
                                DateFormat inputFormat = new SimpleDateFormat("HH_mm_ss");
                                DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
                                event.setEventTime(outputFormat.format(inputFormat.parse(input)));
				
                                    String[] scope=rs.getString("scope").split(";");
                                        for(String str:scope)
                                        logger.info("str:"+str);
                                        String[] sign= scope[0].split("=");
                                        String[] lord= scope[1].split("=");
                                        String[] sun= scope[2].split("=");

                                        String[] moon= scope[3].split("=");
                                        String[] NL= scope[4].split("=");
                                        String[] dayLord= scope[5].split("=");

                                        String[] MDL= scope[6].split("=");
                                        String[] mdlUpto= scope[7].split("=");
                                        String[] mdlDeg= scope[8].split("=");
                                        String[] mdlSign= scope[9].split("=");
                                        String[] mdlNL= scope[10].split("=");
                                        String[] mdlSL= scope[11].split("=");

                                        String[] ADL= scope[12].split("=");
                                        String[] adlUpto= scope[13].split("=");
                                        String[] adlDeg= scope[14].split("=");
                                        String[] adlSign= scope[15].split("=");
                                        String[] adlNL= scope[16].split("=");
                                        String[] adlSL= scope[17].split("=");

                                        String[] PDL= scope[18].split("=");
                                        String[] pdlUpto= scope[19].split("=");
                                        String[] pdlDeg= scope[20].split("=");
                                        String[] pdlSign= scope[21].split("=");
                                        String[] pdlNL= scope[22].split("=");
                                        String[] pdlSL= scope[23].split("=");

                                        String[] sunRlNlSl=sun[1].split("/");
                                        String[] moonRlNlSl=moon[1].split("/");

                                        event.setSign(sign[1]);
                                        event.setLord(lord[1]);
                                        
					 event.setSunRL(sunRlNlSl[0]);
                                        event.setSunNL(sunRlNlSl[1]);
                                        event.setSunSL(sunRlNlSl[2]);
					
					event.setMoonRL(moonRlNlSl[0]);
                                        event.setMoonNL(moonRlNlSl[1]);
                                        event.setMoonSL(moonRlNlSl[2]);

                                        event.setNL(NL[1]);
                                        event.setDayLord(dayLord[1]);

                                        event.setMdl(MDL[1]);
                                        event.setMdlEndTime(mdlUpto[1]);
                                        event.setMdlDegree(mdlDeg[1]);
                                        event.setMdlRashi(mdlSign[1]);
                                        event.setMdlNL(mdlNL[1]);
                                        event.setMdlSL(mdlSL[1]);

                                        event.setAdl(ADL[1]);
                                        event.setAdlEndTime(adlUpto[1]);
                                        event.setAdlDegree(adlDeg[1]);
                                        event.setAdlRashi(adlSign[1]);
                                        event.setAdlNL(adlNL[1]);
                                        event.setAdlSL(adlSL[1]);

                                        event.setPdl(PDL[1]);
                                        event.setPdlEndTime(pdlUpto[1]);
                                        event.setPdlDegree(pdlDeg[1]);
                                        event.setPdlRashi(pdlSign[1]);
                                        event.setPdlNL(pdlNL[1]);
                                        event.setPdlSL(pdlSL[1]);

			
					 event.setCity(rs.getString("CITY"));
                                	String country1=getCountry(rs.getString("COUNTRY"));
                                	logger.info("country1:"+country1);
                                	event.setCountry(country1);
                                	logger.info("event.getCountry"+event.getCountry());
		                        String state=getState(rs.getString("STATE"),rs.getString("COUNTRY"));
                	               event.setState(state);

                        	        logger.info("inside if");
                                	event.setLatitude(rs.getString("latitude"));
	                                event.setLongitude(rs.getString("longitude"));
        	                        event.setCityCode(rs.getString("CITY_CODE"));
                	                event.setDescription(rs.getString("description"));		
                     
				


                        }
                        return event;
                  } catch (Exception e) {
                        logger.error("catch block of reading old event log "
                                        + e.toString());
                        return null;
                } finally {
                        try {
                                if (pstmt != null) {
                                        pstmt.close();
                                }
                                if (rs != null) {
                                        rs.close();
                                }
                                if (con != null) {
                                        TSSJavaUtil.instance().freeConnection(con);
                                }
                        } catch (Exception e) {
                                logger.error("finally block of reading  old event log "
                                                + e.toString());
                        }
                }
        }
	
	
	public GenerateKundli getGenerateLogBook(int userId) {
                GenerateKundli generateKundli = new GenerateKundli();
                try {
                        logger.info("Inside reading old log by userId"
                                        + userId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from USER_PROFILE where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, userId);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                generateKundli.setUserId(rs.getInt("USER_ID"));
                                generateKundli.setName(rs.getString("NAME"));
                                generateKundli.setDob(rs.getString("DOB"));
                                String[] dob = rs.getString("DOB").split("-");
                                generateKundli.setYear(dob[0]);
                                generateKundli.setMonth(dob[1]);
                                generateKundli.setDate(dob[2]);
                                generateKundli.setTob(rs.getString("tob"));
                                String[] tob = rs.getString("tob").split(":");
                                generateKundli.setHour(tob[0]);
                                generateKundli.setMinute(tob[1]);
                                generateKundli.setSecond(tob[2]);
                                generateKundli.setCity(rs.getString("CITY"));
                                generateKundli.setCountry(rs.getString("COUNTRY"));
                                generateKundli.setState(rs.getString("STATE"));
                                generateKundli.setLatitude(rs.getString("latitude"));
                                generateKundli.setLongitude(rs.getString("longitude"));


                        }
                        return generateKundli;
                } catch (Exception e) {
                        logger.error("catch block of reading old kundli log "
                                        + e.toString());
                        return null;
                }finally {
                        try {
                                if (pstmt != null) {
                                        pstmt.close();
                                }
                                if (rs != null) {
                                        rs.close();
                                }
                                if (con != null) {
                                        TSSJavaUtil.instance().freeConnection(con);
                                }
                        } catch (Exception e) {
                                logger.error("finally block of reading old kundli log "
                                                + e.toString());
                        }
                }
        }


	public int saveEventDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, saveEventDetail [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "insert into event_data(user_id,event_name,description,event_date,event_time,longitude,latitude,city_code,event_data_json,event_data_xml,scope,city,state,country) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(2, generateKundli.getName().trim());
                        pstmt.setString(4, generateKundli.getDob());
                        pstmt.setString(5, generateKundli.getTob());
                        pstmt.setString(6, generateKundli.getLongitude());
                        pstmt.setString(7, generateKundli.getLatitude());
                        logger.info("getScope:"+generateKundli.getScope());
                        pstmt.setString(11,generateKundli.getScope());
                        pstmt.setString(8, generateKundli.getCityCode());
                        pstmt.setInt(1, generateKundli.getUserId());
                        pstmt.setString(3, generateKundli.getRemarks());
                        pstmt.setString(9, json);
                        pstmt.setString(10,xml);
                        pstmt.setString(12, generateKundli.getCity());
                        pstmt.setString(13, generateKundli.getState());
                        pstmt.setString(14, generateKundli.getCountry());
                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of saveEventDetails " + e.toString());
                        return 0;

                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of saveDetails" + e.toString());
                        }
                }
                return 1;
	}



	public List<Event> getEventLogs(int userId) {
                List<Event> eventLogsLst = new ArrayList<Event>();
                try {
                        logger.info("Inside getting event logs list:"+userId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from event_data where USER_ID=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, userId);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                Event event= new Event();
                                event.setEventId(rs.getInt("event_id"));

                                event.setEventName(rs.getString("event_name"));
                                String[] dob = rs.getString("event_date").split("-");

                                event.setEventDate(dob[2]+"/"+dob[1]+"/"+dob[0]);
                        logger.info("eventDate:"+event.getEventDate());
                String input=rs.getString("event_time");

                DateFormat inputFormat = new SimpleDateFormat("HH_mm_ss");
                DateFormat outputFormat = new SimpleDateFormat("KK:mm a");

                event.setEventTime(outputFormat.format(inputFormat.parse(input)));
                    logger.info("eventDate:"+event.getEventDate()+"eventTime:"+event.getEventTime());

                                logger.info("SCOPE: -------->>>>>>   ["+rs.getString("scope")+"].");
                                String[] scope=rs.getString("scope").split(";");
                                        for(String str:scope)
                                        logger.info("str:"+str);
						  String[] sign= scope[0].split("=");
                                        String[] lord= scope[1].split("=");
                                        String[] sun= scope[2].split("=");

                                        String[] moon= scope[3].split("=");
                                        String[] NL= scope[4].split("=");
                                        String[] dayLord= scope[5].split("=");
					
					  String[] sunRlNlSl=sun[1].split("/");
                                        String[] moonRlNlSl=moon[1].split("/");

					 for(String str1:sunRlNlSl)
                                        logger.info("str1:"+str1);

					 for(String str2:moonRlNlSl)
                                        logger.info("str2:"+str2);

					 event.setSign(sign[1]);
                                        event.setLord(lord[1]);
                                         event.setSunRL(sunRlNlSl[0]);
                                        event.setSunNL(sunRlNlSl[1]);
                                        event.setSunSL(sunRlNlSl[2]);

                                          event.setMoonRL(moonRlNlSl[0]);
                                        event.setMoonNL(moonRlNlSl[1]);
                                        event.setMoonSL(moonRlNlSl[2]);

                                        event.setNL(NL[1]);
                                        event.setDayLord(dayLord[1]);

                                eventLogsLst.add(event);
                        }
                        logger.info("eventListfrom:"+eventLogsLst);
                        return eventLogsLst;
                } catch (Exception e) {
                        logger.error("catch block of reading user old kundli logs list "
                                        + e.toString());
                        return null;
                }
		finally {
                        try {
                                if (pstmt != null) {
                                        pstmt.close();
                                }
                                if (rs != null) {
                                        rs.close();
                                }
                                if (con != null) {
                                        TSSJavaUtil.instance().freeConnection(con);
                                }
                        } catch (Exception e) {
                                logger.error("finally block of reading user old kundli logs list "
                                                + e.toString());
                        }
                }
        }





public int saveDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, saveDetail [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "insert into user_profile(user_id,name,state,city,country,dob,tob,longitude,latitude,city_code,kundli_data_json,kundli_data_xml) values(?,?,?,?,?,?,?,?,?,?,?,?)";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(2, generateKundli.getName().trim());
                        pstmt.setString(6, generateKundli.getDob());
                        pstmt.setString(7, generateKundli.getTob());
                        pstmt.setString(8, generateKundli.getLongitude());
                        pstmt.setString(9, generateKundli.getLatitude());
                        pstmt.setString(4, generateKundli.getCity());
                        pstmt.setString(10, generateKundli.getCityCode());
                        pstmt.setInt(1, generateKundli.getUserId());
                        pstmt.setString(5, generateKundli.getCountry());
                        pstmt.setString(3, generateKundli.getState());
                        pstmt.setString(11, json);
                        pstmt.setString(12,xml);
                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of saveDetails " + e.toString());
                        return 0;

                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of saveDetails" + e.toString());
                        }
                }
                return 1;
}

		public int updateEventDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, updateEventDetails [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "update event_data set name=?,state=?,city=?,country=?,dob=?,tob=? where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, generateKundli.getName().trim());
                        pstmt.setString(5, generateKundli.getDob());
                        pstmt.setString(6, generateKundli.getTob());


                        pstmt.setString(3, generateKundli.getCity());

                        pstmt.setInt(7, generateKundli.getUserId());
                        pstmt.setString(4, generateKundli.getCountry());
                        pstmt.setString(2, generateKundli.getState());


                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of saveDetails " + e.toString());
                        return 0;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of saveDetails" + e.toString());
                        }
                }
return 1;
}


public int updateDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, update Details [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "update user_profile set name=?,state=?,city=?,country=?,dob=?,tob=?,kundli_data_json=?,kundli_data_xml=? where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, generateKundli.getName().trim());
                        pstmt.setString(2, generateKundli.getState());
                        pstmt.setString(3, generateKundli.getCity());
                        pstmt.setString(4, generateKundli.getCountry());
                        pstmt.setString(5, generateKundli.getDob());
                        pstmt.setString(6, generateKundli.getTob());
                        pstmt.setString(7, json);
                        pstmt.setString(8, xml);
                        pstmt.setInt(9, generateKundli.getUserId());


                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of saveDetails " + e.toString());
                        return 0;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of saveDetails" + e.toString());
                        }
                }
return 1;
}

public String getCountry(String countryCode)
        {
                ResultSet rs1=null;
                String country="";
                logger.info("inside getting country  from DB where country code "
                                + countryCode);
                                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from country where code=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, countryCode);
                         rs1 = pstmt.executeQuery();
                        if(rs1.next()) {
                                 country=rs1.getString("name");
                        }
                        } catch (Exception e) {
                        logger.error("catch block of getting country  " + e.toString());
                        } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs1 != null)
                                        rs1.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of getting state"
                                                + e.toString());
                                }
                }
                return country;
        }

	public String getState(String stateCode,String counCode) {
                String state="";
 ResultSet rs1=null;
                logger.info("inside getting state  from DB where state code "
                                + stateCode);
                                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from state where code=? and coun_code=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, stateCode);

                        pstmt.setString(2, counCode);
                         rs1 = pstmt.executeQuery();
                        if(rs1.next()) {
                                state=rs1.getString("name");
                        }
                        } catch (Exception e) {
                        logger.error("catch block of getting state  " + e.toString());
                        } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs1 != null)
                                        rs1.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of getting state"
                                                + e.toString());
                                }
                }
                return state;
        }


	public boolean isUserExist(int userId) {
                logger.info("inside is user exist user[" + userId + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from user_profile where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, userId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                return true;
                        }
                } catch (Exception e) {
                        logger.error("catch block of Authenticate  " + e.toString());
                        return false;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of Authenticate  " + e.toString());
                        }
                }
                return false;
        }

	
	 public int deleteEventLog(int eventId) {
                try {
                        logger.info("inside,delete event log by requestId " + eventId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "delete from EVENT_DATA  where EVENT_ID=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, eventId);
                        int result = pstmt.executeUpdate();
                        if (result > 0) {
                                return 1;
                        }
                } catch (Exception e) {
                        logger.error("catch block of delete event log " + e.toString());
                        return 0;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of event kundli log "
                                                + e.toString());
                        }
                }
                return 0;
        }


}

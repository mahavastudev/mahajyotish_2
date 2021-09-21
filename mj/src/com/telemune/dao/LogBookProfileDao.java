package com.telemune.dao;

import java.util.List;

import com.telemune.model.Country;
import com.telemune.model.CountryData;
import com.telemune.model.GenerateKundli;
import com.telemune.model.HttpLinks;
import com.telemune.model.RoleInfo;
import com.telemune.model.StateData;
import com.telemune.model.UserInfo;
import com.telemune.model.UserLinkDescription;
import com.telemune.model.Event;

public interface LogBookProfileDao {
	
	
	
	
	
	public List<CountryData> getCountryList(); //
	
	public List<StateData> getStateList(String contryCode); //




	
	boolean isUserExist(int userId);

        public GenerateKundli getGenerateLogBook(int userId); //

	public String getUserKundli(int uId); //

        public int saveEventDetail(GenerateKundli generateKundli,String json,String xml);//

        public int saveDetail(GenerateKundli generateKundli,String json,String xml);
        public int updateDetail(GenerateKundli generateKundli,String json,String xml);

        public int updateEventDetail(GenerateKundli generateKundli,String json,String xml);
        public String getState(String stateCode,String code); //
        public String  getCountry(String countryCode); //
	public List<Event> getEventLogs(int userId);//
	public int deleteEventLog(int eventId);//
	public Event getGenerateEventLog(int eventId);		//
}

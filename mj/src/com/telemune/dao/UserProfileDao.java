package com.telemune.dao;

import java.util.List;

import com.telemune.mobileAstro.AstroBean;
import com.telemune.mobileAstro.Country;
import com.telemune.model.CountryData;
import com.telemune.model.GenerateKundli;
import com.telemune.model.HttpLinks;
import com.telemune.model.RoleInfo;
import com.telemune.model.StateData;
import com.telemune.model.UserInfo;
import com.telemune.model.UserLinkDescription;
import com.telemune.model.Event;

public interface UserProfileDao {
	
	public List<UserLinkDescription> getUserRoleList(int roleId);
	
	public int authenticateCredentials(UserLinkDescription userDetail);

	public int authenticateRegistration(UserLinkDescription userDetail);
	
	public int updateEmail(String email,int user_id);
	
	public int addUser(UserInfo userInfo);
	
	public int modifyUserInfo(UserInfo userInfo);
	
	public int addkundliDetails(GenerateKundli generateKundli,String kundliData);	
	
	public int deleteUser(String userId);
	
	public int getUserId();
	
	public List<UserInfo> getUserList();
	
	public List<UserInfo> getUserList(String roleId);
	
	public List<RoleInfo> getRoleList();
	
	public UserInfo getUserInfo(int uId);
	
	public UserInfo getUserInfo(String email);
	
	public int addUserRoles(RoleInfo roleInfo);
	
	public RoleInfo getRole(int roleId);
	
	public int modifyUserRole(RoleInfo roleInfo);
	
	public int deleteUserRole(int roleId);
	
	public boolean isUserExist(String userEmail);
	
	public boolean isRoleExist(String roleName);
	
	public List<HttpLinks> getHttpLinks();
	
	public int getMaxRoleId();
	
	public int addUserAccessLink(int roleId, String accessValue);
	//public int modifyUserAccessLink(int roleId, String accessValue);
	
	public int deleteUserAccessLink(int roleId);
	
	public int changePassword(int userId, String newPassword);
	
	public List<GenerateKundli> getOldKundliLogs(int userId);
	
	public int deleteKundliLog(int requestId);
	
	public GenerateKundli getGenerateKundliLog(int requestId);
	
	public int saveDetails(GenerateKundli generateKundli);

	public int updateKundliDetails(GenerateKundli generateKundli);
	
	public List<CountryData> getCountryList();
	
	public List<StateData> getStateList(String contryCode);

        public boolean isUserKundliInfoExist(int requestId);

	public int registerRequest(UserLinkDescription userDetail,String access);

	public int checkRequest(UserLinkDescription userDetail);
	
	public boolean validateActivation(UserInfo userInfo);

	public int addkundliData(GenerateKundli generateKundli,String kundliData);
	
	boolean isUserExist(int userId);
        public String getKundliDetail(int uId);

        public GenerateKundli getGenerateLogBook(int userId);

	public String getUserKundli(int uId);

        public int saveEventDetail(GenerateKundli generateKundli,String json,String xml);

        public int saveDetail(GenerateKundli generateKundli,String json,String xml);
        public int updateDetail(GenerateKundli generateKundli,String json,String xml);

        public int updateEventDetail(GenerateKundli generateKundli,String json,String xml);
        public String getState(String stateCode,String code);
        public String  getCountry(String countryCode);
	public List<Event> getEventLogs(int userId);
	public int deleteEventLog(int eventId);
	public Event getGenerateEventLog(int eventId);

	public void writeLogToFile(int id,String action,String remark,String detail);
        public int userClickedLink(int id,String action,String remark,String detail);	



	public int checkSubscription(UserLinkDescription userDetail);
	public int saveCityDetails(GenerateKundli generateKundli,String country,String state);
	public boolean isCityInfoExist(String state,String city);
	public String getCityList(String country,String state,String city);

	public int addHoroscopeData(GenerateKundli generateKundli, AstroBean astrobean);

	public int checkSubscriptionForMultipleRoles(UserLinkDescription userDetail);

	public int updateUserRole(UserLinkDescription userDetail);	
}

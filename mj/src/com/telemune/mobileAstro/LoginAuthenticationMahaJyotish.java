package com.telemune.mobileAstro;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.model.CountryData;
import com.telemune.model.GenerateKundli;
import com.telemune.model.StateData;
import com.telemune.model.UserLinkDescription;
import com.telemune.util.Constants;

public class LoginAuthenticationMahaJyotish extends ActionSupport implements SessionAware {

	private String userId, password, message, jsonmetadata, identity;
	private String emailId = "";
	private String customerCode = "";
	private String clientName = "";
	private String isMobile = "N";
	private Map map;
	private UserProfileDao userDao = null;
	private static Logger logger = Logger.getLogger(LoginAuthenticationMahaJyotish.class);
	private List<CountryData> countryLst;
	private List<StateData> stateLst;
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	private String countryCode = null;
	private GenerateKundli generateKundli;
	private String deviceType = "B";
	private int sessionId = -1;
	private int activationFlag = 0;
	private String access = "NORMALKUNDLI";

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getIsMobile() {
		return this.isMobile;
	}

	public void setIsMobile(String isMobile) {
		this.isMobile = isMobile;
	}

	public int getActivationFlag() {
		return activationFlag;
	}

	public void setActivationFlag(int activationFlag) {
		this.activationFlag = activationFlag;
	}

	public String getAccess() {
		return this.access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
	private Hashtable<String, String> properties = null;
    private String proPath = "";
	public LoginAuthenticationMahaJyotish() {
		proPath = Constants.PROPERTIES_PATH;
        proPath = proPath + "/kundliHttpserverNew.properties";
        properties = ReadPropertyFile.readPropery(proPath);	
	}

	@Override
	public String execute() throws Exception {

		try {
			if (userId == null && password == null && sessionId != -1) {
				return "SUCCESS";
			}
			logger.info("inside execute where email [" + userId + "], password [" + password+ "], role id [" + identity + "]");
			UserLinkDescription userDetail = new UserLinkDescription();
			userDetail.setUserNme(userId.trim());
			userDetail.setPassword(password);
			userDao = new UserProfileDaoImpl();
			int authResult = userDao.authenticateCredentials(userDetail);
			logger.info("authentication result  " + authResult);
			this.setMessage(this.getUserId());
			if (authResult > 0) {
				/**
				 * code to check multiple login roles for users*/
				if( ! identity.equalsIgnoreCase(userDetail.getRoleId()+""))
					return "FAILURE_ROLE";
				
				List<UserLinkDescription> userLinkLst = userDao.getUserRoleList(userDetail.getRoleId());
				setDataInSession(userLinkLst);
				this.setSession(map);
				map.put("userName", userDetail.getUserId());
				map.put("emailId", userId.trim());
				map.put("ccCode", userDetail.getCcCode());
				sessionId = userDetail.getUserId();
				map.put(deviceType, deviceType);
				countryLst = userDao.getCountryList();
				stateLst = userDao.getStateList("IN");
				generateKundli = new GenerateKundli();
				HttpServletRequest request = ServletActionContext.getRequest();
				String remark = "User Logged In";
				String detail = request.getRemoteAddr();
				userDao = new UserProfileDaoImpl();
				userDao.userClickedLink(userDetail.getUserId(), "LOGIN", remark, detail);
				userDao.writeLogToFile(userDetail.getUserId(), "LOGIN", remark, detail);

				return "SUCCESS";
			} else {
				addActionError("Invalid user id");
				message = "Invalid User Name or Password";
				return "FAILURE";
			}

		} catch (Exception e) {
			logger.error("Inside catch block execute() of RegisAction   " + e.toString());
			throw e;
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}

	public String adminLogin(){
		return "SUCCESS";
	}
	public String loginAMRC() throws Exception {

		try {
			if (userId == null && password == null && sessionId != -1) {
				return "SUCCESS";
			}
			logger.info("loginAMRC >>>> where email [" + userId + "], password [" + password+ "]");
			UserLinkDescription userDetail = new UserLinkDescription();
			userDetail.setUserNme(userId.trim());
			userDetail.setPassword(password);
			userDao = new UserProfileDaoImpl();
			int authResult = userDao.authenticateCredentials(userDetail);
			logger.info("authentication result  " + authResult);
			this.setMessage(this.getUserId());
			if (authResult > 0) {
				/**
				 * code to check multiple login roles for users*/
				//getting LOGIN_AMRC_ROLE role from property file to check with db
				String LOGIN_AMRC_ROLE=properties.get("LOGIN_AMRC_ROLE");
				logger.info("LOGIN_AMRC_ROLE.equalsIgnoreCase(userDetail.getRoleId()) "+LOGIN_AMRC_ROLE.equalsIgnoreCase(userDetail.getRoleId()+""));
				logger.info("LOGIN_AMRC_ROLE.equalsIgnoreCase(userDetail.getRoleId2() "+LOGIN_AMRC_ROLE.equalsIgnoreCase(userDetail.getRoleId2()+""));
				if( LOGIN_AMRC_ROLE.equalsIgnoreCase(userDetail.getRoleId()+"") || LOGIN_AMRC_ROLE.equalsIgnoreCase(userDetail.getRoleId2()+"")){
					logger.info("here when LOGIN_AMRC_ROLE["+LOGIN_AMRC_ROLE+"] is matched with role_id ["+userDetail.getRoleId()+"] || role_id_2 ["+userDetail.getRoleId2()+"]");
					List<UserLinkDescription> userLinkLst = userDao.getUserRoleList(Integer.parseInt(LOGIN_AMRC_ROLE));
					setDataInSession(userLinkLst);
					this.setSession(map);
					map.put("userName", userDetail.getUserId());
					map.put("emailId", userId.trim());
					map.put("ccCode", userDetail.getCcCode());
					sessionId = userDetail.getUserId();
					map.put(deviceType, deviceType);
					countryLst = userDao.getCountryList();
					stateLst = userDao.getStateList("IN");
					generateKundli = new GenerateKundli();
					HttpServletRequest request = ServletActionContext.getRequest();
					String remark = "User Logged In";
					String detail = request.getRemoteAddr();
					userDao = new UserProfileDaoImpl();
					userDao.userClickedLink(userDetail.getUserId(), "LOGIN", remark, detail);
					userDao.writeLogToFile(userDetail.getUserId(), "LOGIN", remark, detail);
				}else{
					logger.info("here when LOGIN_AMRC_ROLE["+LOGIN_AMRC_ROLE+"] did not matched with role_id ["+userDetail.getRoleId()+"] || role_id_2 ["+userDetail.getRoleId2()+"]");
					message = properties.get("LOGIN_AMRC_FAILED_MSG");
					return "FAILURE_ROLE";
				}
				return "SUCCESS";
			} else {
				addActionError("Invalid user id");
				message = "Invalid User Name or Password";
				return "FAILURE";
			}

		} catch (Exception e) {
			logger.error("Inside catch block execute() of RegisAction   " + e.toString());
			throw e;
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}
	
	public String loginMahajyotish() throws Exception {

		try {
			if (userId == null && password == null && sessionId != -1) {
				return "SUCCESS";
			}
			logger.info("loginMahajyotish >>>>>>>>> where email [" + userId + "], password [" + password+ "]");
			UserLinkDescription userDetail = new UserLinkDescription();
			userDetail.setUserNme(userId.trim());
			userDetail.setPassword(password);
			userDao = new UserProfileDaoImpl();
			int authResult = userDao.authenticateCredentials(userDetail);
			logger.info("authentication result  " + authResult);
			this.setMessage(this.getUserId());
			if (authResult > 0) {
				/**
				 * code to check multiple login roles for users*/
				//getting LOGIN_AMRC_ROLE role from property file to check with db
				String LOGIN_MAHAJYOTISH_ROLE=properties.get("LOGIN_MAHAJYOTISH_ROLE");
				List<String> LOGIN_MAHAJYOTISH_ROLE_LIST=Stream.of(LOGIN_MAHAJYOTISH_ROLE.split(",")).collect(Collectors.toList());
				logger.info("list of admin ids "+LOGIN_MAHAJYOTISH_ROLE_LIST.toString());
				
				logger.info("LOGIN_MAHAJYOTISH_ROLE_LIST.contains(userDetail.getRoleId()+\"\") "+LOGIN_MAHAJYOTISH_ROLE_LIST.contains(userDetail.getRoleId()+""));
				logger.info("LOGIN_MAHAJYOTISH_ROLE_LIST.contains(userDetail.getRoleId2()+\"\") "+LOGIN_MAHAJYOTISH_ROLE_LIST.contains(userDetail.getRoleId2()+""));
				//if( LOGIN_MAHAJYOTISH_ROLE_LIST.equalsIgnoreCase(userDetail.getRoleId()+"") || LOGIN_MAHAJYOTISH_ROLE_LIST.equalsIgnoreCase(userDetail.getRoleId2()+"")){
				if(  LOGIN_MAHAJYOTISH_ROLE_LIST.contains(userDetail.getRoleId()+"") || LOGIN_MAHAJYOTISH_ROLE_LIST.contains(userDetail.getRoleId2()+"")){
					LOGIN_MAHAJYOTISH_ROLE = LOGIN_MAHAJYOTISH_ROLE_LIST.contains(userDetail.getRoleId()+"") ? userDetail.getRoleId()+"" : userDetail.getRoleId2()+"";
					
					logger.info("here when LOGIN_AMRC_ROLE["+LOGIN_MAHAJYOTISH_ROLE+"] is matched with role_id ["+userDetail.getRoleId()+"] || role_id_2 ["+userDetail.getRoleId2()+"]");
					
					logger.info("setting role id to login with ["+LOGIN_MAHAJYOTISH_ROLE+"]");
					List<UserLinkDescription> userLinkLst = userDao.getUserRoleList(Integer.parseInt(LOGIN_MAHAJYOTISH_ROLE));
					setDataInSession(userLinkLst);
					this.setSession(map);
					map.put("userName", userDetail.getUserId());
					map.put("emailId", userId.trim());
					map.put("ccCode", userDetail.getCcCode());
					sessionId = userDetail.getUserId();
					map.put(deviceType, deviceType);
					countryLst = userDao.getCountryList();
					stateLst = userDao.getStateList("IN");
					generateKundli = new GenerateKundli();
					HttpServletRequest request = ServletActionContext.getRequest();
					String remark = "User Logged In";
					String detail = request.getRemoteAddr();
					userDao = new UserProfileDaoImpl();
					userDao.userClickedLink(userDetail.getUserId(), "LOGIN", remark, detail);
					userDao.writeLogToFile(userDetail.getUserId(), "LOGIN", remark, detail);
				}else{
					logger.info("here when LOGIN_MAHAJYOTISH_ROLE["+LOGIN_MAHAJYOTISH_ROLE+"] did not matched with role_id ["+userDetail.getRoleId()+"] || role_id_2 ["+userDetail.getRoleId2()+"]");
					message = properties.get("LOGIN_MAHAJYOTISH_FAILED_MSG");
					return "FAILURE_ROLE";
				}
				return "SUCCESS";
			} else {
				addActionError("Invalid user id");
				message = "Invalid User Name or Password";
				return "FAILURE";
			}

		} catch (Exception e) {
			logger.error("Inside catch block execute() of RegisAction   " + e.toString());
			throw e;
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}
	public String register() {
		logger.info("Inside register of RegisAction");
		return "SUCCESS";
	}

	public String registerNewUser() {

		try {
			logger.info("Inside registerNewUser of RegisAction CUSTOMERCODE>> " + customerCode + "  ,EMAILID>> "
					+ emailId + "    activationFlag:[" + activationFlag + "].      isMobile>> " + isMobile
					+ "  access>> " + access);
			int authResult = 0;
			logger.info("clientName==>" + clientName + "access==>" + access);
			String mailSubject = "Activation Link for MahaJyotish App";
			KundliMail kundliMail = new KundliMail();
			UserLinkDescription userDetail = new UserLinkDescription();
			userDetail.setUserNme(emailId.trim());
			// userDetail.setUserNme("neha.gupta@telemune.com");
			userDetail.setCustomerCode(customerCode);
			userDetail.setClientName(clientName);
			userDao = new UserProfileDaoImpl();
			authResult = userDao.checkSubscription(userDetail);
			logger.info("authResult>> adminlogn" + authResult);
			if (authResult == 1) {
				message = "You are already a subscriber. Please Login with your Registered Email, if any trouble you can click on trouble in login";
				return "SUCCESS4";
			}
			authResult = userDao.checkRequest(userDetail);
			int atIndex = emailId.indexOf("@");
			String tempEmailId = emailId.substring(0, atIndex);
			String tempEmailDomain = emailId.substring(atIndex + 1, emailId.length());
			logger.info("tempEmailId [" + tempEmailId + "] tempEmailDomain[" + tempEmailDomain + "]");

			char[] charEmail = tempEmailId.toCharArray();
			logger.info("length>>> " + charEmail.length);
			for (int i = 0; i < charEmail.length; i++) {
				if (i % 2 == 0) {
					logger.info("character is [" + charEmail[i] + "]");
					charEmail[i] = '*';
				}

			}

			logger.info("final email id [" + new String(charEmail) + "]");

			logger.info("authResult from checkRequest>> " + authResult);
			if (authResult == 1) {
				logger.info("SUCCESS1");
				kundliMail.activationLinkMail(userDetail,mailSubject, emailId.trim(), userDetail.getTokenId());
				message = "Your activation Link has been sent to your registered Email Id [" + new String(charEmail)
						+ "@" + tempEmailDomain + "].";
				if (isMobile.equalsIgnoreCase("Y"))
					return "SUCCESS3";
				else
					return "SUCCESS1";
			} else {
				logger.info("activationFlag>> " + activationFlag);
				if (activationFlag == 102) {
					int result = userDao.registerRequest(userDetail, access);
					if (result > 0) {
						kundliMail.activationLinkMail(userDetail,mailSubject, emailId.trim(), userDetail.getTokenId());

						message = "Your activation Link has been sent to your registered Email Id["
								+ new String(charEmail) + "@" + tempEmailDomain + "].";
						if (isMobile.equalsIgnoreCase("Y"))
							return "SUCCESS3";
						else
							return "SUCCESS1";
					} else {
						message = "Some Error Occured. Please try Again later";
						if (isMobile.equalsIgnoreCase("Y"))
							return "SUCCESS3";
						else
							return "SUCCESS2";
					}

				} else if (activationFlag == 101) {
					message = "You Are Not Authorised.";
					if (isMobile.equalsIgnoreCase("Y"))
						return "SUCCESS3";
					else
						return "SUCCESS2";
				} else if (activationFlag == 100) {
					// message="No Entry Found.";
					message = "No Information Found. Try with another EmailId.";
					if (isMobile.equalsIgnoreCase("Y"))
						return "SUCCESS3";
					else
						return "SUCCESS2";
				}
			}
			if (isMobile.equalsIgnoreCase("Y"))
				return "SUCCESS3";
			else
				return "SUCCESS2";
		} catch (Exception e) {
			logger.error("Inside catch block registerNewUser() of RegisAction   " + e.toString());
			message = "Some Error Occured. Please try Again later";
			if (isMobile.equalsIgnoreCase("Y"))
				return "SUCCESS3";
			else
				return "ERROR";
		}

	}

	public String SendActivationLink() {
		try {

		} catch (Exception e) {
			logger.error("Inside catch block SendActivationLink() of RegisAction   " + e.toString());
			return "ERROR";
		}
		return "SUCCESS";

	}

	public String responseMob() throws Exception {

		try {
			return "SUCCESS";

		} catch (Exception e) {
			logger.error("Inside catch block execute() of RegisAction   " + e.toString());
			return "FAILURE";
		}

	}

	public String executeMob() throws Exception {

		try {
			logger.info("user name -->" + getUserId());
			logger.info("user name -->" + getUserId());
			HttpServletResponse response = ServletActionContext.getResponse();
			UserLinkDescription userDetail = new UserLinkDescription();
			userDetail.setUserNme(userId);
			userDetail.setPassword(password);
			userDao = new UserProfileDaoImpl();
			int authResult = userDao.authenticateCredentials(userDetail);
			logger.info("Return value  " + authResult);
			this.setMessage(this.getUserId());

			if (authResult == 1) {
				map.put("userName", userDetail.getUserId());
				logger.info("session variable in regisAction  " + map.get("userName"));
				setUserId(userId);
				setPassword(password);

				// setJsonmetadata("{\"response\":\"yes\",\"userId\":\""+userId+"\",\"password\":\""+password+"\"}");
				setJsonmetadata("?userId=" + userId + "&password=" + password + "&deviceType=A");
			} else {
				setJsonmetadata("NA");
				// setJsonmetadata("{\"response\":\"no\",\"userId\":\""+userId+"\",\"password\":\""+password+"\"}");
			}
			return "SUCCESS";

		} catch (Exception e) {
			logger.error("Inside catch block execute() of RegisAction   " + e.toString());
			throw e;
		} finally {
			if (userDao != null) {
				userDao = null;
			}
		}
	}

	public void setDataInSession(List<UserLinkDescription> list) {
		if (!list.isEmpty()) {
			for (UserLinkDescription userRole : list) {
				map.put(userRole.getLinkId(), userRole.getLinkName());
				System.out.println(userRole.getLinkId() + "   " + userRole.getLinkName());
			}
		}
	}

	/*
	 * public String findStateList() throws Exception{ try { HttpServletRequest
	 * request = ServletActionContext.getRequest(); HttpSession session =
	 * request.getSession(); String sess =
	 * session.getAttribute("userName").toString(); if (sess == null ||
	 * sess.isEmpty()) { logger.info("SESSION EXPIRED !!!!!"); return "ERROR"; }
	 * else { userDao = new UserProfileDaoImpl();
	 * logger.info("Country Code "+countryCode); stateLst
	 * =userDao.getStateList(countryCode); if (!stateLst.isEmpty()) {
	 * logger.info("state list successfully found "); return "SUCCESS"; } else {
	 * return "FAILURE"; } } } catch (Exception e) { logger.error(e.toString());
	 * return "ERROR"; } finally { if (userDao != null) { userDao = null; } } }
	 */

	public String logout() throws Exception {
		try {
			logger.info("inside logout() of RegisAction ");
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpSession session = request.getSession();
			if (map == null || map.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				this.message = "Your Session Expired, Please Login Again !!";
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				response.setDateHeader("Expires", 0);
				if (map instanceof SessionMap) {
					((SessionMap) map).invalidate();
				}
				return "SUCCESS";
			}
			if (message.equals("1")) {
				this.message = "You Are Successfully Logged Out !";
			} else {
				this.message = "Your Session Expired, Please Login Again !!";
			}
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0);
			if (map instanceof SessionMap) {
				((SessionMap) map).invalidate();

			}

			return "SUCCESS";

		} catch (Exception e) {
			logger.error("inside catch block of logout() of RegisAction " + e.toString());
			throw e;
		}
	}

	public void setSession(Map session) {
		map = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJsonmetadata() {
		return jsonmetadata;
	}

	public void setJsonmetadata(String jsonmetadata) {
		this.jsonmetadata = jsonmetadata;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public GenerateKundli getGenerateKundli() {
		return generateKundli;
	}

	public void setGenerateKundli(GenerateKundli generateKundli) {
		this.generateKundli = generateKundli;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}

package com.telemune.action;

import java.util.List;
import java.util.Map;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONObject;
import com.telemune.model.GenerateKundli;
import com.opensymphony.xwork2.ActionSupport;
import com.telemune.dao.AuthenticationManager;
import com.telemune.dao.LocationManager;
import com.telemune.dao.RequestManager;
import com.telemune.dao.RoleManager;
import com.telemune.model.CountryData;
import com.telemune.model.StateData;
import com.telemune.model.UserLinkDescription;

public class RegisAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RegisAction.class);

	private String userId, password, message, jsonmetadata;
	private String emailId = "";
	private String customerCode = "";
	private String clientName = "";
	private Map map;
	private AuthenticationManager authenticationManager = null;
	private LocationManager locationManager = null;
	private RoleManager roleManager = null;
	private RequestManager requestManager = null;
	private List<CountryData> countryLst;
	private List<StateData> stateLst;
	private String countryCode = null;
	private GenerateKundli generateKundli;
	private String deviceType = "B";
	private int sessionId = -1;
	private int activationFlag = 0;

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getActivationFlag() {
		return activationFlag;
	}

	public void setActivationFlag(int activationFlag) {
		this.activationFlag = activationFlag;
	}

	@Override
	public String execute() throws Exception {
		logger.info("Inside execute method of RegisAction action class.");

		try {
			if (userId == null && password == null && sessionId != -1) {
				return "SUCCESS";
			}
			UserLinkDescription userDetail = new UserLinkDescription();
			authenticationManager = new AuthenticationManager();
			locationManager = new LocationManager();
			roleManager = new RoleManager();
			userDetail.setUserNme(userId.trim());
			userDetail.setPassword(password);
			int authResult = authenticationManager.authenticateCredentials(userDetail);
			logger.info("Return value  " + authResult);
			this.setMessage(this.getUserId());
			if (authResult > 0) {
				List<UserLinkDescription> userLinkLst = roleManager.getUserRoleList(userDetail.getRoleId());
				setDataInSession(userLinkLst);
				this.setSession(map);
				map.put("userName", userDetail.getUserId());
				map.put("emailId", userId.trim());
				sessionId = userDetail.getUserId();
				map.put(deviceType, deviceType);
				countryLst = locationManager.getCountryList();
				stateLst = locationManager.getStateList("IN");
				generateKundli = new GenerateKundli();
				return "SUCCESS";
			} else {
				addActionError("Invalid user id");
				message = "Invalid User Name or Password";
				return "FAILURE";
			}

		} catch (Exception e) {
			logger.error("Inside catch block. Exception at execute method of RegisAction action Class. Exception  : " + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			if (authenticationManager != null) {
				authenticationManager = null;
			}
			if (locationManager != null) {
				locationManager = null;
			}
			if (roleManager != null) {
				roleManager = null;
			}
		}
	}

	public String register() {
		logger.info("Inside register method of RegisAction action class.");
		return "SUCCESS";
	}

	public String registerNewUser() {

		try {
			logger.info("Inside registerNewUser method of RegisAction actio class with CUSTOMERCODE>> " + customerCode
					+ "  ,EMAILID>> " + emailId + "    activationFlag:[" + activationFlag + "].");
			int authResult = 0;
			String mailSubject = "Activation Link for MahaJyotish App";
			KundliMail kundliMail = new KundliMail();
			UserLinkDescription userDetail = new UserLinkDescription();
			requestManager = new RequestManager();

			userDetail.setUserNme(emailId.trim());
			userDetail.setCustomerCode(customerCode);
			userDetail.setClientName(clientName);
			authResult = requestManager.checkRequest(userDetail);
			logger.info("authResult from checkRequest>> " + authResult);
			if (authResult == 1) {
				kundliMail.activationLinkMail(userDetail,mailSubject, emailId.trim(), userDetail.getTokenId());
				message = "Activation Link has been sent to your registered Email Id.";
				return "SUCCESS1";
			} else {
				logger.info("activationFlag>> " + activationFlag);
				if (activationFlag == 102) {
					int result = requestManager.registerRequest(userDetail);
					if (result > 0) {
						kundliMail.activationLinkMail(userDetail,mailSubject, emailId.trim(), userDetail.getTokenId());
						message = "Activation Link has been sent to your registered Email Id.";
						return "SUCCESS1";
					} else {
						message = "Some Error Occured. Please try Again later";
						return "SUCCESS2";
					}

				} else if (activationFlag == 101) {
					message = "You Are Not Authorised.";
					return "SUCCESS2";
				} else if (activationFlag == 100) {
					message = "No Entry Found.";
					return "SUCCESS2";
				}
			}
			return "SUCCESS2";
		} catch (Exception e) {
			logger.error("Inside catch block. Exception at registerNewUser method of RegisAction action Class. Exception  : " + e.toString());
			e.printStackTrace();
			return "ERROR";
		}

		finally {
			if (requestManager != null) {
				requestManager = null;
			}
		}

	}

	public String SendActivationLink() {
		logger.info("Inside SendActivationLink method of RegisAction action class.");
		try {

		} catch (Exception e) {
			logger.error(
					"Inside catch block of SendActivationLink() of RegisAction action class. Exception : "
							+ e.toString());
			e.printStackTrace();
			return "ERROR";
		}
		return "SUCCESS";

	}

	public String responseMob() throws Exception {
		logger.info("Inside responseMob method of RegisAction action class.");
		try {
			return "SUCCESS";

		} catch (Exception e) {
			logger.error("Inside catch block responseMob method of RegisAction action class. Exception : " + e.toString());
			e.printStackTrace();
			return "FAILURE";
		}

	}

	public String executeMob() throws Exception {
		logger.info("Inside executeMob method of RegisAction action class.");

		try {
			logger.info("user name -->" + getUserId());
			HttpServletResponse response = ServletActionContext.getResponse();
			UserLinkDescription userDetail = new UserLinkDescription();
			userDetail.setUserNme(userId);
			userDetail.setPassword(password);
			authenticationManager = new AuthenticationManager();
			int authResult = authenticationManager.authenticateCredentials(userDetail);
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
			logger.error("Inside catch block executeMob() of RegisAction   " + e.toString());
			e.printStackTrace();
			throw e;
		} finally {
			if (authenticationManager != null) {
				authenticationManager = null;
			}
		}
	}

	public void setDataInSession(List<UserLinkDescription> list) {
		logger.info("Inside setDataInSession method of RegisAction action class.");
		if (!list.isEmpty()) {
			for (UserLinkDescription userRole : list) {
				map.put(userRole.getLinkId(), userRole.getLinkName());
				System.out.println(userRole.getLinkId() + "   " + userRole.getLinkName());
			}
		}
	}

	public String logout() throws Exception {
		logger.info("Inside logout method of RegisAction action class.");
		try {
			logger.info("inside logout() of RegisAction ");
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpSession session = request.getSession();
			if (map == null || map.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				this.message = "Your Session Expired, Please Login Again !!";
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																							// 1.1.
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
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																						// 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0);
			if (map instanceof SessionMap) {
				((SessionMap) map).invalidate();

			}

			return "SUCCESS";

		} catch (Exception e) {
			logger.error("inside catch block of logout method of RegisAction action class. Exception : " + e.toString());
			e.printStackTrace();
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

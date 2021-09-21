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

public class RegisAction extends ActionSupport implements SessionAware {

	private String userId, password, message, jsonmetadata;
	private String emailId="";
	private String customerCode="";
	private String clientName="";
	private String isMobile="N";
	private Map map;
	private UserProfileDao userDao=null;
	private static Logger logger = Logger.getLogger(RegisAction.class);
    private List<CountryData> countryLst;
    private List<StateData> stateLst;
    private String countryCode=null;
    private GenerateKundli generateKundli;
    private String deviceType = "B";
	private int sessionId=-1;
	private int activationFlag = 0;
	private String access="NORMALKUNDLI";
	
	public  String getClientName(){
                return this.clientName;
        }
        public void setClientName(String clientName){
                this.clientName = clientName;
        }
	
	public  String getIsMobile(){
                return this.isMobile;
        }
        public void setIsMobile(String isMobile){
                this.isMobile = isMobile;
        }

	public int getActivationFlag(){
                return activationFlag;
        }
        public void setActivationFlag(int activationFlag){
                this.activationFlag = activationFlag;
        }

	public  String getAccess(){
                return this.access;
        }
        public void setAccess(String access){
                this.access = access;
        }
	
        private Hashtable<String, String> properties = null;
        private String proPath = "";
    	public RegisAction() {
    		proPath = Constants.PROPERTIES_PATH;
            proPath = proPath + "/kundliHttpserverNew.properties";
            properties = ReadPropertyFile.readPropery(proPath);	
    	}
	@Override
	public String execute() throws Exception {
		logger.info("adminLogin >>>> where email [" + userId + "], password [" + password+ "]");
		try {
			if(userId==null && password==null && sessionId!=-1)
			{
				return "SUCCESS";
			}
			UserLinkDescription userDetail=new UserLinkDescription();
			userDetail.setUserNme(userId.trim());
			userDetail.setPassword(password);
			userDao = new UserProfileDaoImpl();
			int authResult = userDao.authenticateCredentials(userDetail);
			this.setMessage(this.getUserId());
			if (authResult>0) {
				logger.info("userDetail  " + userDetail.toString());
				//if(userDetail.getUserId()==1){
				String ADMIN_IDS=properties.getOrDefault("ADMIN_ID", "1");
				List<String> ADMIN_IDS_LIST=Stream.of(ADMIN_IDS.split(",")).collect(Collectors.toList());
				//String ADMIN_IDS_ARR[]=ADMIN_IDS.split(",");
				logger.info("list of admin ids "+ADMIN_IDS_LIST.toString());
				if(ADMIN_IDS_LIST.contains(userDetail.getUserId()+"")) {
					List<UserLinkDescription> userLinkLst = userDao.getUserRoleList(userDetail.getRoleId());
					setDataInSession(userLinkLst);
					this.setSession(map);
					map.put("userName",userDetail.getUserId());
	                map.put("emailId",userId.trim());
	                map.put("ccCode",userDetail.getCcCode());
					sessionId=userDetail.getUserId();
					map.put(deviceType, deviceType);
					countryLst = userDao.getCountryList();
	                stateLst = userDao.getStateList("IN");
					generateKundli = new GenerateKundli();
					HttpServletRequest request = ServletActionContext.getRequest();
	                String remark="User Logged In";
	                String detail = request.getRemoteAddr();
	                userDao = new UserProfileDaoImpl();
	                userDao.userClickedLink(userDetail.getUserId(), "LOGIN", remark, detail);
	                userDao.writeLogToFile(userDetail.getUserId(),"LOGIN",remark,detail);
	    			return "SUCCESS";
				}else{
					message=properties.get("ONLY_ADMIN_ALLOW_FAIL_MSG");
					return "FAILURE";
				}
		
	
			} else {
				addActionError("Invalid user id");
				message="Invalid User Name or Password";
				return "FAILURE";
			}

		} catch (Exception e) {
			logger.error("Inside catch block execute() of RegisAction   "
					+ e.toString());
			throw e;
		}
		finally{
			if(userDao != null){
				userDao =null;
			}
		}
	}

	public String register(){
		logger.info("Inside register of RegisAction");
		return "SUCCESS";
	}


	public String registerNewUser()
        {

        try {
			logger.info("Inside registerNewUser of RegisAction CUSTOMERCODE>> "+customerCode+"  ,EMAILID>> "+emailId+"    activationFlag:["+activationFlag+"].      isMobile>> "+isMobile+"  access>> "+access);
			int authResult=0;
			logger.info("clientName==>"+clientName+"access==>"+access);
			String mailSubject="Activation Link for MahaJyotish App";
			KundliMail kundliMail=new KundliMail();
			UserLinkDescription userDetail=new UserLinkDescription();
            userDetail.setUserNme(emailId.trim());
			//userDetail.setUserNme("neha.gupta@telemune.com");
            userDetail.setCustomerCode(customerCode);
            userDetail.setClientName(clientName);
            userDao = new UserProfileDaoImpl();
			authResult=userDao.checkSubscription(userDetail);
			logger.info("authResult>> adminlogn"+authResult);	
              if(authResult==1)
              {
                  message = "You are already a subscriber. Please Login with your Registered Email, if any trouble you can click on trouble in login";
                  return "SUCCESS4";
              }
            authResult = userDao.checkRequest(userDetail);
			int atIndex = emailId.indexOf("@");
			String tempEmailId = emailId.substring(0,atIndex);
			String tempEmailDomain = emailId.substring(atIndex+1,emailId.length());
			logger.info("tempEmailId ["+tempEmailId+"] tempEmailDomain["+tempEmailDomain+"]");
			
			char[] charEmail = tempEmailId.toCharArray();
			logger.info("length>>> "+charEmail.length);	
			for(int i=0;i<charEmail.length;i++)
			{
				if(i%2==0)
				{
					logger.info("character is ["+charEmail[i]+"]");
					charEmail[i]='*';
				}
				
			}
			
			logger.info("final email id ["+new String(charEmail)+"]");
		
			logger.info("authResult from checkRequest>> "+authResult);
			if(authResult==1){
				logger.info("SUCCESS1");
                                kundliMail.activationLinkMail(userDetail,mailSubject, emailId.trim(),userDetail.getTokenId());
				message="Your activation Link has been sent to your registered Email Id ["+new String(charEmail)+"@"+tempEmailDomain+"].";
				if(isMobile.equalsIgnoreCase("Y"))
					return "SUCCESS3";
				else
					return "SUCCESS1";
                        }
                        else{
				logger.info("activationFlag>> "+activationFlag);	
				if(activationFlag == 102){
					int result=userDao.registerRequest(userDetail,access);
					if(result>0){
						kundliMail.activationLinkMail(userDetail,mailSubject, emailId.trim(),userDetail.getTokenId());
						
						
						message="Your activation Link has been sent to your registered Email Id["+new String(charEmail)+"@"+tempEmailDomain+"].";
						if(isMobile.equalsIgnoreCase("Y"))
                                        		return "SUCCESS3";
						else
							return "SUCCESS1";
					}
					else{
						message="Some Error Occured. Please try Again later";
						if(isMobile.equalsIgnoreCase("Y"))
                                        		return "SUCCESS3";
						else
							return "SUCCESS2";
					}

				}
				else if(activationFlag == 101){
					message="You Are Not Authorised.";
					if(isMobile.equalsIgnoreCase("Y"))
                                        	return "SUCCESS3";
					else
						return "SUCCESS2";
				}
				else if(activationFlag == 100){
				//	message="No Entry Found.";
					message="No Information Found. Try with another EmailId.";
					if(isMobile.equalsIgnoreCase("Y"))
                                        	return "SUCCESS3";
					else
						return "SUCCESS2";
				}	
                        }
			if(isMobile.equalsIgnoreCase("Y"))
                        	return "SUCCESS3";
			else	
				return "SUCCESS2";
                }
                catch (Exception e) {
                        logger.error("Inside catch block registerNewUser() of RegisAction   "+ e.toString());
			message="Some Error Occured. Please try Again later";
			if(isMobile.equalsIgnoreCase("Y"))
                        	return "SUCCESS3";
			else
                        	return "ERROR";
                }

        }
	
	public String registerUserForMahaJyotish() {
		try {
			logger.info("MahaJyotish registration  :: CUSTOMERCODE>> " + customerCode + "  ,EMAILID>> " + emailId
					+ "    activationFlag:[" + activationFlag + "].      isMobile>> " + isMobile + "  access>> "
					+ access);
			int authResult = 0;
			logger.info("clientName==>" + clientName + ", access==>" + access);
			String mailSubject = "Activation Link for MahaJyotish App";
			KundliMail kundliMail = new KundliMail();
			UserLinkDescription userDetail = new UserLinkDescription();
			userDetail.setUserNme(emailId.trim());
			// userDetail.setUserNme("neha.gupta@telemune.com");
			userDetail.setCustomerCode(customerCode);
			userDetail.setClientName(clientName);
			userDao = new UserProfileDaoImpl();
			// authResult=userDao.checkSubscription(userDetail);
			authResult = userDao.checkSubscriptionForMultipleRoles(userDetail);
			logger.info("authResult>> adminlogn " + authResult);
			logger.info(">>>>>>>>> userDetail " + userDetail.toString());
			String LOGIN_MAHAJYOTISH_ROLE = properties.getOrDefault("LOGIN_MAHAJYOTISH_ROLE","7");
			logger.info("LOGIN_MAHAJYOTISH_ROLE is ["+LOGIN_MAHAJYOTISH_ROLE+"]");
			if (authResult == 1) {
				/**
				 * code to check multiple login roles for users
				 */
				// getting LOGIN_MAHAJYOTISH_ROLE role from property file to check with
				// db
				
				logger.info("LOGIN_MAHAJYOTISH_ROLE.equalsIgnoreCase(userDetail.getRoleId()) "
						+ LOGIN_MAHAJYOTISH_ROLE.equalsIgnoreCase(userDetail.getRoleId() + ""));
				logger.info("LOGIN_MAHAJYOTISH_ROLE.equalsIgnoreCase(userDetail.getRoleId2() "
						+ LOGIN_MAHAJYOTISH_ROLE.equalsIgnoreCase(userDetail.getRoleId2() + ""));
				if (LOGIN_MAHAJYOTISH_ROLE.equalsIgnoreCase(userDetail.getRoleId() + "")
						|| LOGIN_MAHAJYOTISH_ROLE.equalsIgnoreCase(userDetail.getRoleId2() + "")) {
					message = properties.get("MJ_ALREDY_REGISTER_MSG");
					return "SUCCESS4";
				}else{
					//code here to update roles for user
					/*if(!LOGIN_MAHAJYOTISH_ROLE.equalsIgnoreCase(userDetail.getRoleId()+""))
						userDetail.setRoleId(userDetail.getRoleId());*/
					userDetail.setRoleId2(Integer.parseInt(LOGIN_MAHAJYOTISH_ROLE));
					logger.info(">>>> new data to updated "+userDetail.toString());
					int i= userDao.updateUserRole(userDetail);
					if(i>0){
						logger.info(">>>> Successfully updated "+userDetail.toString());
						message = properties.get("MJ_REGISTRATION_SUCCESS_MSG");
						return "SUCCESS5";
					}else{
						logger.info(">>>> Error in updating record "+userDetail.toString());
						message = properties.get("UPDATION_FAILED_MSG");
						return "SUCCESS5";
					}
				}

			}
			userDetail.setRoleId(Integer.parseInt(LOGIN_MAHAJYOTISH_ROLE));
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
			logger.info(">>>>>>>>>>> " + userDetail.toString());
			
			userDetail.setActivationLink(properties.get("MJ_ACTIVATION_LINK"));
			
            userDetail.setRoleId(Integer.parseInt(LOGIN_MAHAJYOTISH_ROLE));
			if (authResult == 1) {
				logger.info("SUCCESS1");
				kundliMail.activationLinkMail(userDetail, mailSubject, emailId.trim(), userDetail.getTokenId());
				message = "Your activation Link has been sent to your registered Email Id [" + new String(charEmail)
						+ "@" + tempEmailDomain + "].";
				if (isMobile.equalsIgnoreCase("Y"))
					return "SUCCESS3";
				else
					return "SUCCESS1";
			} else {
				logger.info("activationFlag>> " + activationFlag);
				if (activationFlag == 102) {
					userDetail.setRoleId(Integer.parseInt(LOGIN_MAHAJYOTISH_ROLE));
					int result = userDao.registerRequest(userDetail, access);
					if (result > 0) {
						
						kundliMail.activationLinkMail(userDetail, mailSubject, emailId.trim(), userDetail.getTokenId());

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
	
	
	
	

	public String SendActivationLink(){
		try{
				
		}
		catch (Exception e) {
                        logger.error("Inside catch block SendActivationLink() of RegisAction   "+ e.toString());
                        return "ERROR";
                }
		return "SUCCESS";	




	}
	
	public String responseMob() throws Exception
	{

        	try {
			return "SUCCESS";

                } 
		catch (Exception e) {
                        logger.error("Inside catch block execute() of RegisAction   "+ e.toString());
                        return "FAILURE";
                }

        }

	public String executeMob() throws Exception {

		try {
			logger.info("user name -->" + getUserId());
			logger.info("user name -->" + getUserId());
			HttpServletResponse response = ServletActionContext.getResponse();
			UserLinkDescription userDetail=new UserLinkDescription();
			userDetail.setUserNme(userId);
			userDetail.setPassword(password);
			userDao = new UserProfileDaoImpl();
			int authResult = userDao.authenticateCredentials(userDetail);
			logger.info("Return value  " + authResult);
			this.setMessage(this.getUserId());
			
			if (authResult == 1) {
				map.put("userName", userDetail.getUserId());
				logger.info("session variable in regisAction  "
						+ map.get("userName"));
				setUserId(userId);
				setPassword(password);
				
		//	setJsonmetadata("{\"response\":\"yes\",\"userId\":\""+userId+"\",\"password\":\""+password+"\"}");
			setJsonmetadata("?userId="+userId+"&password="+password+"&deviceType=A");
                        } else {
			 setJsonmetadata("NA");
                  //              setJsonmetadata("{\"response\":\"no\",\"userId\":\""+userId+"\",\"password\":\""+password+"\"}");
                        }
return "SUCCESS";

		} catch (Exception e) {
			logger.error("Inside catch block execute() of RegisAction   "
					+ e.toString());
			throw e;
		}
		finally{
			if(userDao != null){
				userDao =null;
			}
		}
	}

	public void setDataInSession(List<UserLinkDescription> list){
		if(!list.isEmpty()){
			for(UserLinkDescription userRole: list){
				map.put(userRole.getLinkId(), userRole.getLinkName());
				System.out.println(userRole.getLinkId()+"   "+ userRole.getLinkName());
			}
		}
	}
	
	/*public String findStateList() throws Exception{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = session.getAttribute("userName").toString();
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				logger.info("Country Code "+countryCode);
				stateLst =userDao.getStateList(countryCode);
				if (!stateLst.isEmpty()) {
					logger.info("state list successfully found ");
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
	}*/
	
	public String logout() throws Exception {
		try {
			logger.info("inside logout() of RegisAction ");
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpSession session = request.getSession();
			if (map == null || map.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				this.message = "Your Session Expired, Please Login Again !!";
				response.setHeader("Cache-Control",
						"no-cache, no-store, must-revalidate"); // HTTP 1.1.
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				response.setDateHeader("Expires", 0);
				if (map instanceof SessionMap) {
					((SessionMap) map).invalidate();
				}
				return "SUCCESS";
			}
            if(message.equals("1")){
            	this.message = "You Are Successfully Logged Out !";
            }
            else
            {
            	this.message = "Your Session Expired, Please Login Again !!";
            }
			response.setHeader("Cache-Control",
					"no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0);
			if (map instanceof SessionMap) {
				((SessionMap) map).invalidate();

			}

			return "SUCCESS";

		} catch (Exception e) {
			logger.error("inside catch block of logout() of RegisAction "
					+ e.toString());
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

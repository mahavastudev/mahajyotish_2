package com.telemune.action;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.mobileAstro.KundliMail;
import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.model.RoleInfo;
import com.telemune.model.UserInfo;
import com.telemune.util.Constants;

public class UserProfileAction extends ActionSupport implements SessionAware {

	private static Logger logger = Logger.getLogger(UserProfileAction.class);
	private UserInfo userInfo;
	private UserProfileDao userDao = null;
	private List<UserInfo> userLst;
	private List<RoleInfo> rolelist;
	private String delCheckList;
	private String password;
	private String message;
	private Map map;
        private KundliMail kundliMail = null;
	private Hashtable<String, String> properties = null;
        private String proPath = "";

	 public UserProfileAction() {
                logger.info("Inside constructor of UserProfileAction action class.");
                proPath = Constants.PROPERTIES_PATH;
                proPath = proPath + "kundliHttpserverNew.properties";
                logger.info("property file:"+proPath);
                properties = ReadPropertyFile.readPropery(proPath);
                }


	public String userMgt() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";
			int userId=(Integer) session.getAttribute("userName");
                        String action=properties.get("CLICK");
                        String remark="User Clicked on User Management";
                        String detail = request.getRemoteAddr();
                        userDao=new UserProfileDaoImpl();
                        userDao.userClickedLink(userId,action,remark,detail);
                        userDao.writeLogToFile(userId, action, remark, detail);
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				return "SUCCESS";
			}
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
	}

	public String execute() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				rolelist = userDao.getRoleList();
				logger.info("Role List size " + rolelist.size());
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

	public String addUser() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				if (userDao.isUserExist(userInfo.getEmail())) {
					String message = "User " + "'" + userInfo.getEmail()
							+ "'" + " is already exist.";
					map.put("msg", message);
					logger.info("message " + message);
					return "SUCCESS";
				} else {
					/*BY ANKUR*/
					int userId=(Integer) session.getAttribute("userName");
                                        String action=properties.get("CREATE");
                                        String remark="Created A New User";
                                        String detail = request.getRemoteAddr();					
					/*ENDS HERE*/
					int retval = userDao.addUser(userInfo);
					if (retval > 0) {
					/*BY ANKUR*/
						userDao.userClickedLink(userId,action,remark,detail);
                                                userDao.writeLogToFile(userId, action, remark, detail);
					/*ENDS HERE*/
						String message = "user has been successfully added";
						map.put("msg", message);
                                                kundliMail = new KundliMail();
                                                String mailSub = "Login Credentials for Mahajyotish kundli";
                                                String[] mailTo ={userInfo.getEmail()};
                                                kundliMail.loginInfoMailSending(mailSub, mailTo, userInfo);
						logger.info("message " + message);
						return "SUCCESS";
					} else {
						return "FAILURE";
					}
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
                        if(kundliMail != null){
                           kundliMail = null;
                        }
		}

	}

	public String changePassword() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";
			
			int userId=(Integer) session.getAttribute("userName");
                        String action=properties.get("CLICK");
                        String remark="User Clicked on Change Password";
                        String detail = request.getRemoteAddr();
                        userDao = new UserProfileDaoImpl();
			userDao.userClickedLink(userId,action,remark,detail);
                        userDao.writeLogToFile(userId, action, remark, detail);
			

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				return "SUCCESS";
				/*
				 * userDao = new UserProfileDaoImpl(); userInfo =
				 * userDao.getUserInfo(Integer.parseInt(sess)); if (userInfo !=
				 * null) { logger.info("user data " + userInfo.toString());
				 * return "SUCCESS"; } else { return "FAILURE"; }
				 */
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

	public String changePasswordExecute() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				int result = userDao.changePassword(Integer.parseInt(sess),
						password);
				if (result > 0) {
					message = "password has been changed successfully";
					
					int userId=Integer.parseInt(sess);
                                        String action=properties.get("UPDATE");
                                        String remark="User Changed Password";
                                        String detail = request.getRemoteAddr();
                                        userDao=new UserProfileDaoImpl();
                                        userDao.userClickedLink(userId,action,remark,detail);
                                        userDao.writeLogToFile(userId, action, remark, detail);					

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

	public String forgotPasswordAction() throws Exception {
		try {
			userDao = new UserProfileDaoImpl();
			UserInfo uInfo = userDao.getUserInfo(userInfo.getEmail());
			if (uInfo != null) {
                                kundliMail = new KundliMail();
				String mailSub = "Mahajyotish Kundli Password Info";
				String[] mailTo ={uInfo.getEmail()};
				String msg = "your password is "+uInfo.getUpassword();
				kundliMail.mailSending(mailSub, mailTo, uInfo);
				message = "Password has been sent to your mail";
			} else {
				message = "your username or email is wrong ";
			}
			logger.info(message);
			return "SUCCESS";
		} catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		} finally {
			if (userDao != null) {
				userDao = null;
			}
                        if(kundliMail != null){
                             kundliMail = null;
                        }
		}
	}



/*	public String registerNewUser()
        {

                try {
                        logger.info("Inside registerNewUser of RegisAction CUSTOMERCODE>> "+customerCode+"  ,EMAILID>> "+emailId);
                        UserLinkDescription userDetail=new UserLinkDescription();
                        userDetail.setUserNme(emailId.trim());
                        userDetail.setCustomerCode(customerCode);
                        userDao = new UserProfileDaoImpl();
                        int authResult = userDao.authenticateRegistration(userDetail);
                        logger.info("Return value  " + authResult);
                        if(authResult==1){

                        }
                        else{
                                kundliMail=new KundliMail();
                                String mailSubject="Activation Link for MahaJyotish App";
                                kundliMail.activationLinkMail(mailSubject, emailId.trim());
                        }
                        return "SUCCESS";
                }
                catch (Exception e) {
                        logger.error("Inside catch block registerNewUser() of RegisAction   "+ e.toString());
                        return "ERROR";
                }

        }*/

	public String viewUser() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {


				userDao = new UserProfileDaoImpl();
				userLst = userDao.getUserList();
				logger.info("userLst "+userLst.toString());
				setMessage((String) session.getAttribute("msg"));
				session.setAttribute("msg", null);
				logger.info("User List size " + userLst.size());

				int id=Integer.parseInt(sess);
                                String action=properties.get("VIEW");
                                String remark="Viewed  User's List";
                                String detail = request.getRemoteAddr();
				userDao.userClickedLink(id, action, remark, detail);
                                userDao.writeLogToFile(id, action, remark, detail);

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

	public String modifyUser() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				userInfo = userDao.getUserInfo(userInfo.getuId());
				if (userInfo != null) {
					int id=Integer.parseInt(sess);
                                String action=properties.get("UPDATE");
                                String remark="Modify User with id = "+userInfo.getuId()+" and Name = "+userInfo.getUname();
                                String detail = request.getRemoteAddr();
					userDao.userClickedLink(id, action, remark, detail);
                                        userDao.writeLogToFile(id, action, remark, detail);


					rolelist = userDao.getRoleList();
					logger.info("Role List size " + rolelist.size());
					logger.info("user data " + userInfo.toString());
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

	public String modifyUserExecution() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";
			logger.info("modifying user >>>>> "+userInfo.toString());
			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
			     userDao = new UserProfileDaoImpl();
                             int result = userDao.modifyUserInfo(userInfo);
                             if (result > 0) {
                                 String message = "user info has been successfully modified";
                                 map.put("msg", message);
                                 logger.info(message);
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

	public String deleteUser() throws Exception {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sess = (Integer) session.getAttribute("userName") + "";

			if (sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");
				return "ERROR";
			} else {
				userDao = new UserProfileDaoImpl();
				logger.info("delete user list " + delCheckList);
				int result = userDao.deleteUser(delCheckList);
				if (result > 0) {
					int userId=(Integer) session.getAttribute("userName");
                                        String action=properties.get("DELETE");
                                        String remark="Delete User with id = "+delCheckList;
                                        String detail = request.getRemoteAddr();

					String message = "selected user has been successfully deleted";
					map.put("msg", message);
					logger.info(message);
			
					userDao.userClickedLink(userId, action, remark, detail);
                                        userDao.writeLogToFile(userId,action,remark,detail);

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
	
	
        public String emailPdf() throws Exception {
                try {
                        HttpServletRequest request = ServletActionContext.getRequest();
                        HttpSession session = request.getSession();
                        String sess = (Integer) session.getAttribute("userName") + "";

                        if (sess == null || sess.isEmpty()) {
                                logger.info("SESSION EXPIRED !!!!!");
                                return "ERROR";
                        } else {
                                userDao = new UserProfileDaoImpl();
                                logger.info("delete user list " + delCheckList);
                                int result = userDao.deleteUser(delCheckList);
                                if (result > 0) {
                                        String message = "selected user has been successfully deleted";
                                        map.put("msg", message);
                                        logger.info(message);
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


	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public List<RoleInfo> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<RoleInfo> rolelist) {
		this.rolelist = rolelist;
	}

	public List<UserInfo> getUserLst() {
		return userLst;
	}

	public void setUserLst(List<UserInfo> userLst) {
		this.userLst = userLst;
	}

	public String getDelCheckList() {
		return delCheckList;
	}

	public void setDelCheckList(String delCheckList) {
		this.delCheckList = delCheckList;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setSession(Map session) {
		map = session;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

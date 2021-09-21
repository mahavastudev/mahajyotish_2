package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;

import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.mobileAstro.KundliMail;
import com.telemune.model.UserLinkDescription;
import com.telemune.model.UserInfo;

/**
 * Servlet implementation class SaveKundli
 */
//@WebServlet("validateActivation")
public class ValidateActivation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ValidateActivation.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateActivation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("INSIDE ValidateActivation GETTT");
		UserProfileDao userDao = null;
		UserInfo userInfo = null;
		UserLinkDescription userDetail = null;
		PrintWriter out = null;
		RequestDispatcher requestDispatcher = null;
		  try{
                        String tokenId=(String)request.getParameter("tokenId");
                        logger.info("tokenId>> "+tokenId);
                        userDao= new UserProfileDaoImpl();
                        userInfo= new UserInfo();
                        userInfo.setTokenId(tokenId);
                        out = response.getWriter();
                        if(userDao.validateActivation(userInfo)){
                        	logger.info("Access>>  "+userInfo.getAccess());
                        	userInfo.setUpassword("mahavastu");
				if(userInfo.getAccess().equals("MAHAJYOTISH")){
                                	userInfo.setRoleId("7");
				}
				else if(userInfo.getAccess().equals("NORMALKUNDLI")){
					userInfo.setRoleId("2");
				}
				

				
				if (!userDao.isUserExist(userInfo.getEmail())) {
				/*if (!userDao.isUserExist("neha.gupta@telemune.com")) {*/
                                	int retval = userDao.addUser(userInfo);
                                	if (retval > 0) {
                                        	KundliMail kundliMail = new KundliMail();
                                        	String mailSub = "Login Credentials for Mahajyotish kundli";
						                     String[] mailTo ={userInfo.getEmail()};
                                        	//String[] mailTo ={"neha.gupta@telemune.com"};
                                        	kundliMail.loginInfoMailSending(mailSub, mailTo, userInfo);
                                        //	requestDispatcher = request.getRequestDispatcher("/activation.jsp");
                                        	requestDispatcher = request.getRequestDispatcher("/jsp/index.jsp");
                                        	request.setAttribute("message","Your login Id and password has been sent to your registered email.");
                                        	requestDispatcher.forward(request, response);
                                	} 
					else {
                                        	requestDispatcher = request.getRequestDispatcher("/jsp//activationResult.jsp");
                                        	request.setAttribute("message","Activation Request cant be completed. please try Again Later");
                                        	requestDispatcher.forward(request, response);
                                        	logger.info("Error in ADDING USER");
                                	}
				}
				else{
					requestDispatcher = request.getRequestDispatcher("/jsp/index.jsp");
									request.setAttribute("message","Your Account is already exists. please Sign In with ur previously provided Credientails.");
                                    requestDispatcher.forward(request, response);
                                	logger.info("USER ALREADY EXISTS");
				}
                        }
                        else{
                                requestDispatcher = request.getRequestDispatcher("/jsp/activationResult.jsp");
                                request.setAttribute("message","Activaion for MahaJyotish Account Failed. Please Register Again.");
                                requestDispatcher.forward(request, response);
                                logger.info("VALIDATION FAILED");
                        }
                }
                catch (Exception e) {
                        logger.error("Exception in ValidateActivation "+e.toString());
                }
                finally{
                        if(userDao != null){
                                userDao = null;
                        }
                        if(out != null){
                                out.close();
                        }
                }	
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*	UserProfileDao userDao = null;
		UserInfo userInfo = null;
		UserLinkDescription userDetail = null;
		PrintWriter out = null;
		try{
			logger.info("INSIDE ValidateActivation");
			String tokenId=(String)request.getParameter("tokenId"); 
			logger.info("tokenId>> "+tokenId);
		    	userDao= new UserProfileDaoImpl();
		    	userInfo= new UserInfo();
			userInfo.setTokenId(tokenId);
		    	out = response.getWriter();
		    	if(userDao.validateActivation(userInfo)){
				userInfo.setUpassword("mahavastu");
				userInfo.setRoleId("2");
				int retval = userDao.addUser(userInfo);
                                if (retval > 0) {
					KundliMail kundliMail = new KundliMail();
                                     	String mailSub = "Login Credentials for Mahajyotish kundli";
                                     	String[] mailTo ={userInfo.getEmail()};
                                      	kundliMail.loginInfoMailSending(mailSub, mailTo, userInfo);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/activation.jsp");
            				requestDispatcher.forward(request, response);
                                } else {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                                        requestDispatcher.forward(request, response);
                                        logger.error("Error in Update kundli info");
                                }
		    	}
			else{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                                requestDispatcher.forward(request, response);
				logger.error("error in save kundli info");
			}
		}
		catch (Exception e) {
			logger.error("Exception in save kundli info "+e.toString());
		}
		finally{
			if(userDao != null){
				userDao = null;
			}
			if(out != null){
				out.close();
			}
		}	*/
	}

}

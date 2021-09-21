package com.telemune.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class SessionValidation {

	Logger logger = Logger.getLogger(SessionValidation.class);

	public String checkSession() {
		String returnString = "";
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String sessionCheck = session.getAttribute("userName") + "";
			logger.info("sessionCheck : ["+sessionCheck+"]");
			
			if (sessionCheck == null || sessionCheck.isEmpty()) {
				returnString = "error";
			} else
				returnString = "success";
		} catch (Exception e) {
			logger.error("Inside catch block of checkSession method of SessionValidation calss.");
		} finally {
			try {

			} catch (Exception e) {
				logger.error("Inside finally block of checkSession method of SessionValidation calss. Exception : "
						+ e.toString());
				e.printStackTrace();
			}
		}
		return returnString;
	}
}

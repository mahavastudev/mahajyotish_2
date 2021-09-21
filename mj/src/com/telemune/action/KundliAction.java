package com.telemune.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.telemune.mobileAstro.AstroBean;
import com.telemune.mobileAstro.Planet;

public class KundliAction extends ActionSupport {
	
	private static Logger logger = Logger.getLogger(KundliAction.class);
	private AstroBean astroBean;
	private String[] ketuAspect;
	private String[] ketuConjuction;
	
	public String displayLagnaChart() throws Exception{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";
			if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				ActionContext actionContext = ActionContext.getContext();
				astroBean = (AstroBean) actionContext.get("astroData");
				ketuConjuction=astroBean.getConjuction(Planet.toPlanets("Ketu"));
				logger.info("KetuConjuction "+ketuConjuction);
				logger.info("*******Object "+astroBean);
				ketuAspect = astroBean.getAspect(Planet.toPlanets("Ketu"));
				logger.info("ketuAspect "+ketuAspect);
				return "SUCCESS";
			}
		}
		catch (Exception e) {
                        e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
	}
	
	public String displayCuspDetails() throws Exception{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";
			if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				return "SUCCESS";
			}
		}
		catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
	}
	
	public String displayPlanetryDetails() throws Exception{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";
			if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				return "SUCCESS";
			}
		}
		catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
	}
	
	public String displayDashaChart() throws Exception{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";
			if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				return "SUCCESS";
			}
		}
		catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
	}
	
//	public String kundliCircle() throws Exception{
//		try{
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpSession session = request.getSession();
//		String sess = session.getAttribute("userName").toString();
//		if(sess == null || sess.isEmpty()){
//			logger.info("SESSION EXPIRED !!!!!");
//			return "ERROR";
//		}
//		else {
//			return "SUCCESS";
//		}
//		}
//		catch (Exception e) {
//			logger.error(e.toString());
//			return "ERROR";
//		}
//	}

	public AstroBean getAstroBean() {
		return astroBean;
	}

	public void setAstroBean(AstroBean astroBean) {
		this.astroBean = astroBean;
	}

	public String[] getKetuAspect() {
		return ketuAspect;
	}

	public void setKetuAspect(String[] ketuAspect) {
		this.ketuAspect = ketuAspect;
	}

	public String[] getKetuConjuction() {
		return ketuConjuction;
	}

	public void setKetuConjuction(String[] ketuConjuction) {
		this.ketuConjuction = ketuConjuction;
	}

}

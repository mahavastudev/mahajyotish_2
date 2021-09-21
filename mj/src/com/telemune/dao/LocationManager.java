package com.telemune.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.CountryData;
import com.telemune.model.StateData;

public class LocationManager {

	private static final Logger logger = Logger.getLogger(LocationManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	
	public List<CountryData> getCountryList() {
		List<CountryData> countryLst = new ArrayList<CountryData>();
		try {
			logger.info("Inside geCountryList method of LocationManager dao class.");
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
			logger.error("Inside catch block of getCountryList method of LocationManager dao class. Exception : " + e.toString());
			e.printStackTrace();
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
				logger.error("Inside finally block of getCountryList method of LocationManager dao class. Exception : " + e.toString());
				return countryLst;
			}
		}
		return countryLst;
	}
	
	
	public List<StateData> getStateList(String contryCode) {
		logger.info("Inside getStateList of LocationManager dao class with countryCode :[" + contryCode+"].");
		List<StateData> stateLst = new ArrayList<StateData>();
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from state where coun_code=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, contryCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				StateData stateData = new StateData();
				stateData.setSname(rs.getString("name"));
				stateData.setScode(rs.getString("code"));
				stateData.setCntryCode(rs.getString("coun_code"));
				stateLst.add(stateData);
			}
			return stateLst;
		} catch (Exception e) {
			logger.error("Inside catch block of getSateList method of LocationManager dao class. Exception : " + e.toString());
			e.printStackTrace();
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
				logger.error("Inside finally block of getSateList method of LocationManager dao class. Exception : " + e.toString());
				return stateLst;
			}
		}
	}


}

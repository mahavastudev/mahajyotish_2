package com.telemune.dao;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.GenerateKundli;

public class KundliManager {

	private static final Logger logger = Logger.getLogger(KundliManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public int saveDetails(GenerateKundli generateKundli) {
		logger.info("Inside saveDetails method of KundliManager dao class with generateKundli:[" + generateKundli + "]");
		int requestId = -1;
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into  ASTRO_REQUEST_LOG(NAME,DOB,tob,longitude,latitude,CITY,CITY_CODE,USER_ID,COUNTRY,STATE,transit_kundli,transit_dob,transit_tob,remarks) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, generateKundli.getName().trim());
			pstmt.setString(2, generateKundli.getDob());
			pstmt.setString(3, generateKundli.getTob());
			pstmt.setString(4, generateKundli.getLongitude());
			pstmt.setString(5, generateKundli.getLatitude());
			pstmt.setString(6, generateKundli.getCity());
			pstmt.setString(7, generateKundli.getCityCode());
			pstmt.setInt(8, generateKundli.getUserId());
			pstmt.setString(9, generateKundli.getCountry());
			pstmt.setString(10, generateKundli.getState());
			pstmt.setInt(11, generateKundli.getIsTransitKundli());
			pstmt.setString(12, generateKundli.getTransitDob());
			pstmt.setString(13, generateKundli.getTransitTob());
			pstmt.setString(14, generateKundli.getRemarks());
			int result = pstmt.executeUpdate();
			pstmt.close();

			query = "select max(REQUEST_ID) as reqId from ASTRO_REQUEST_LOG";

			if (result > 0) {
				logger.info("QUERY:: " + query);
				pstmt = con.prepareStatement(query);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					requestId = rs.getInt("reqId");
				}
				return requestId;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of saveDetails method of KundliManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of saveDetails method of KundliManager dao class. Exception : " + e.toString());
			}
		}
		return requestId;
	}

	public int updateKundliDetails(GenerateKundli generateKundli) {
		logger.info("Inside updateKundliDetails method of KundliManager dao class with generateKundli(requestId):[" + generateKundli.getRequestId() + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "update ASTRO_REQUEST_LOG set NAME=?,DOB=?,tob=?,longitude=?,latitude=?,CITY=?,CITY_CODE=?,COUNTRY=?,STATE=?,transit_kundli=?,transit_dob=?,transit_tob=?,remarks=? where REQUEST_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, generateKundli.getName().trim());
			pstmt.setString(2, generateKundli.getDob());
			pstmt.setString(3, generateKundli.getTob());
			pstmt.setString(4, generateKundli.getLongitude());
			pstmt.setString(5, generateKundli.getLatitude());
			pstmt.setString(6, generateKundli.getCity());
			pstmt.setString(7, generateKundli.getCityCode());
			pstmt.setString(8, generateKundli.getCountry());
			pstmt.setString(9, generateKundli.getState());
			pstmt.setInt(10, generateKundli.getIsTransitKundli());
			pstmt.setString(11, generateKundli.getTransitDob());
			pstmt.setString(12, generateKundli.getTransitTob());
			pstmt.setString(13, generateKundli.getRemarks());
			pstmt.setInt(14, generateKundli.getRequestId());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of updateKundliDetails method of KundliManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of updateKundliDetails method of KundliManager dao class. Exception : " + e.toString());
			}
		}
		return 0;
	}

	public boolean isUserKundliInfoExist(int requestId) {
		logger.info("Inside isUserKundliInfoExist method of KundliManager dao class with requestId:[" + requestId + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from astro_request_log where REQUEST_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, requestId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of isUserKundliInfoExist method of KundliManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of isUserKundliInfoExist method of KundliManager dao class. Exception : " + e.toString());
			}
		}
		return false;
	}

	public int addkundliData(GenerateKundli generateKundli, String kundliData) {
		logger.info("Inside addkundliData method of KundliManager dao class with generateKundli:[" + generateKundli + "]");
		int result = -1;
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into  kundli_metadata(name,state,city,country,dob,tob,user_id,create_date,kundli_data,remarks) values(?,?,?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, generateKundli.getName().trim());
			pstmt.setString(2, generateKundli.getState());
			pstmt.setString(3, generateKundli.getCity());
			pstmt.setString(4, generateKundli.getCountry());
			pstmt.setString(5, generateKundli.getDob());
			pstmt.setString(6, generateKundli.getTob());
			pstmt.setInt(7, generateKundli.getUserId());
			pstmt.setString(8, kundliData);
			pstmt.setString(9, generateKundli.getRemarks());
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			logger.error("Inside catch block of addkundliData method of KundliManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of addkundliData method of KundliManager dao class. Exception : " + e.toString());
			}
		}
		return result;

	}

}

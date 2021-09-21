package com.telemune.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.GenerateKundli;

public class KundliLogsManager {

	private static final Logger logger = Logger.getLogger(KundliLogsManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public List<GenerateKundli> getOldKundliLogs(int userId) {
		List<GenerateKundli> kundliLogsLst = new ArrayList<GenerateKundli>();
		logger.info("Inside getOldKundliLogs method of KundliLogsManager dao class.");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from ASTRO_REQUEST_LOG where USER_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GenerateKundli generateKundli = new GenerateKundli();
				generateKundli.setUserId(rs.getInt("USER_ID"));
				generateKundli.setRequestId(rs.getInt("REQUEST_ID"));
				generateKundli.setName(rs.getString("NAME"));
				String[] dob = rs.getString("DOB").split("-");
				generateKundli.setYear(dob[0]);
				generateKundli.setMonth(dob[1]);
				generateKundli.setDate(dob[2]);
				generateKundli.setDob(rs.getString("DOB"));
				String[] tob = rs.getString("tob").split(":");
				generateKundli.setHour(tob[0]);
				generateKundli.setMinute(tob[1]);
				generateKundli.setSecond(tob[2]);
				generateKundli.setTob(rs.getString("tob"));
				generateKundli.setCity(rs.getString("CITY"));
				generateKundli.setCountry(rs.getString("COUNTRY"));
				generateKundli.setState(rs.getString("STATE"));
				generateKundli.setLatitude(rs.getString("latitude"));
				generateKundli.setLongitude(rs.getString("longitude"));
				generateKundli.setRemarks(rs.getString("remarks"));
				kundliLogsLst.add(generateKundli);
			}
			return kundliLogsLst;
		} catch (Exception e) {
			logger.error("Inside catch block of getOldKundliLogs method of KundliLogsManager dao class. Exception : "
					+ e.toString());
			e.printStackTrace();
			return null;
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
				logger.error(
						"Inside finally block of getOldKundliLogs method of KundliLogsManager dao class. Exception : "
								+ e.toString());
			}
		}
	}

	public int deleteKundliLog(int requestId) {
		logger.info("Inside deleteKundliLog method of KundliLogsManager dao class.");
		try {
			logger.info("inside,delete kundli log by requestId " + requestId);
			con = TSSJavaUtil.instance().getconnection();
			String query = "delete from ASTRO_REQUEST_LOG where REQUEST_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, requestId);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of deleteKundliLog method of KundliLogsManager dao class. Exception : "
					+ e.toString());
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
				logger.error("Inside finally block of deleteKundliLog method of KundliLogsManager dao class. Exception : " + e.toString());
			}
		}
		return 0;
	}

	public GenerateKundli getGenerateKundliLog(int requestId) {
		GenerateKundli generateKundli = new GenerateKundli();
		logger.info("Inside getGenerateKundliLog method of KundliLogsManager dao class with requestId:" + requestId);
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from ASTRO_REQUEST_LOG where REQUEST_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, requestId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				generateKundli.setUserId(rs.getInt("USER_ID"));
				generateKundli.setRequestId(rs.getInt("REQUEST_ID"));
				generateKundli.setName(rs.getString("NAME"));
				generateKundli.setDob(rs.getString("DOB"));
				String[] dob = rs.getString("DOB").split("-");
				generateKundli.setYear(dob[0]);
				generateKundli.setMonth(dob[1]);
				generateKundli.setDate(dob[2]);
				generateKundli.setTob(rs.getString("tob"));
				String[] tob = rs.getString("tob").split(":");
				generateKundli.setHour(tob[0]);
				generateKundli.setMinute(tob[1]);
				generateKundli.setSecond(tob[2]);
				generateKundli.setCity(rs.getString("CITY"));
				generateKundli.setCountry(rs.getString("COUNTRY"));
				generateKundli.setState(rs.getString("STATE"));
				generateKundli.setLatitude(rs.getString("latitude"));
				generateKundli.setLongitude(rs.getString("longitude"));
				generateKundli.setCityCode(rs.getString("CITY_CODE"));
				generateKundli.setRemarks(rs.getString("remarks"));
				int isTransitKundli = rs.getInt("transit_kundli");
				if (isTransitKundli == 1) {
					generateKundli.setIsTransitKundli(rs.getInt("transit_kundli"));
					generateKundli.setTransitDob(rs.getString("transit_dob"));
					String[] transitDob = rs.getString("transit_dob").split("-");
					generateKundli.setTransitYear(transitDob[0]);
					generateKundli.setTransitMonth(transitDob[1]);
					generateKundli.setTransitDate(transitDob[2]);
					generateKundli.setTransitTob(rs.getString("transit_tob"));
					String[] transitTob = rs.getString("transit_tob").split(":");
					generateKundli.setTransitHour(transitTob[0]);
					generateKundli.setTransitMinute(transitTob[1]);
					generateKundli.setTransitSecond(transitTob[2]);
				}

			}
			return generateKundli;
		} catch (Exception e) {
			logger.error("Inside catch block of getGenerateKundliLog method of KundliLogsManager dao class. Exception : "
					+ e.toString());
			e.printStackTrace();
			return null;
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
				logger.error("Inside finally block of getGenerateKundliLog method of KundliLogsManager dao class. Exception : " + e.toString());
			}
		}
	}

}

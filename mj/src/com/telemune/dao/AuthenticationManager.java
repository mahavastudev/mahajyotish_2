package com.telemune.dao;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.UserInfo;
import com.telemune.model.UserLinkDescription;

public class AuthenticationManager {

	private static final Logger logger = Logger.getLogger(AuthenticationManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public int authenticateCredentials(UserLinkDescription userDetail) {
		logger.info("Inside authenticateCredentials method of AuthenticationManager dao class with username["
				+ userDetail.getUserNme() + "] pass   [" + userDetail.getPassword() + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where LOWER(EMAIL)=? and PASSWORD=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userDetail.getUserNme().toLowerCase());
			pstmt.setString(2, userDetail.getPassword());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDetail.setUserId(rs.getInt("ID"));
				userDetail.setRoleId(rs.getInt("ROLE_ID"));
				return 1;
			}
			return 0;
		} catch (Exception e) {
			logger.error(
					"Indie catch block of authenticateCredentials method of AuthenticationManager dao class. Exception : "
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
				logger.error(
						"Indie finally block of authenticateCredentials method of AuthenticationManager dao class. Exception : "
								+ e.toString());
			}
		}
	}

	public int authenticateRegistration(UserLinkDescription userDetail) {
		logger.info("Inside authenticateRegistration method of AuthenticationManager dao class with EMAIL ID["
				+ userDetail.getUserNme() + "] CUSTOMER CODE   [" + userDetail.getCustomerCode() + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where LOWER(EMAIL)=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userDetail.getUserNme().toLowerCase());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return 1;
			}
			return 0;
		} catch (Exception e) {
			logger.error(
					"Indie catch block of authenticateRegistration method of AuthenticationManager dao class. Exception : "
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
				logger.error(
						"Indie finally block of authenticateRegistration method of AuthenticationManager dao class. Exception : "
								+ e.toString());

			}
		}

	}

	public boolean validateActivation(UserInfo userInfo) {
		logger.info("Inside validateActivation method of AuthenticationManager dao class with tokenId [" + userInfo.getTokenId() + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from register_request where token_id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userInfo.getTokenId());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userInfo.setEmail(rs.getString("email"));
				userInfo.setUname(rs.getString("client_name"));
				return true;
			}
		} catch (Exception e) {
			logger.error("Indie catch block of validateActivation method of AuthenticationManager dao class. Exception : " + e.toString());
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
				logger.error("Indie finally of block validateActivation method of AuthenticationManager dao class. Exception : " + e.toString());
			}
		}
		return false;
	}

}

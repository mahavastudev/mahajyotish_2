package com.telemune.dao;

import java.sql.ResultSet;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.UserLinkDescription;

public class RequestManager {

	private static final Logger logger = Logger.getLogger(RequestManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public int checkRequest(UserLinkDescription userDetail) {
		logger.info("Inside checkRequest method of RequestManager dao class with EMAIL ID:[" + userDetail.getUserNme() + "] CUSTOMER CODE   ["
				+ userDetail.getCustomerCode() + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from register_request where LOWER(email)=? or cust_code=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userDetail.getUserNme().toLowerCase());
			pstmt.setString(2, userDetail.getCustomerCode());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDetail.setTokenId(rs.getString("token_id"));
				return 1;
			}
			return 0;
		} catch (Exception e) {
			logger.error("Inside catch block of checkRequest method of RequestManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of checkRequest method of RequestManager dao class. Exception : " + e.toString());

			}
		}

	}

	public int registerRequest(UserLinkDescription userDetail) {
		logger.info("Inside registerRequest method of RequestManager dao class with EMAIL_ID:[" + userDetail.getUserNme() + "] and CUSTOMER_CODE:["
				+ userDetail.getCustomerCode() + "].");
		int result = -1;
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into register_request(token_id,email,cust_code,register_date,expiry_date,client_name) values(?,?,?,now(),now(),?)";
			pstmt = con.prepareStatement(query);
			String token = UUID.randomUUID().toString().replace("-", "");
			userDetail.setTokenId(token);
			logger.info("token>> " + token);
			pstmt.setString(1, token);
			pstmt.setString(2, userDetail.getUserNme().toLowerCase());
			pstmt.setString(3, userDetail.getCustomerCode());
			pstmt.setString(4, userDetail.getClientName());
			result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			logger.error("Inside catch block of registerRequest method of RequestManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return result;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of registerRequest method of RequestManager dao class. Exception : " + e.toString());

			}
		}

	}

}

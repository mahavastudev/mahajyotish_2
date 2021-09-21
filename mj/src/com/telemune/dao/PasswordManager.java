package com.telemune.dao;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;

public class PasswordManager {
	
	private static final Logger logger = Logger.getLogger(PasswordManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	
	public int changePassword(int userId, String newPassword) {
		try {
			logger.info("Inside changePassword method of PasswordManager dao class with password:[" + newPassword + "].");
			con = TSSJavaUtil.instance().getconnection();
			String query = "update adminlogin set " + "PASSWORD=? "
					+ "where ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, newPassword);
			pstmt.setInt(2, userId);
			int result = pstmt.executeUpdate();
			logger.info("change user password result " + result);
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of changePassword method of PasswordManager dao class. Exception : " + e.toString());
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
				logger.error("Inside finally block of changePassword method of PasswordManager dao class. Exception : " + e.toString());
				e.printStackTrace();
			}
		}
		return 0;
	}


}

package com.telemune.dao;

import java.util.List;
import org.apache.log4j.Logger;
import com.telemune.dbutilities.*;
import java.sql.ResultSet;
import com.telemune.mobileAstro.TSSJavaUtil;

public class Adminlogin {

	private static final Logger logger = Logger.getLogger(Adminlogin.class);
	// property constants

	Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public int Authenticate(String username, String password) {
		logger.info("inside Authenticate method of Adminlogin dao class with username[" + username + "] pass   ["
				+ password + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from ADMINLOGIN where EMAIL=? and PASSWORD=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("ROLE_ID");
			}
			return 0;
		} catch (Exception e) {
			logger.error(
					"Inside catch block of Authenticate method of Adminlogin dao class. Exception : " + e.toString());
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
				logger.error("Inside finally block of Authenticate method of Adminlogin dao class. Exception : "
						+ e.toString());
			}
		}

	}
}

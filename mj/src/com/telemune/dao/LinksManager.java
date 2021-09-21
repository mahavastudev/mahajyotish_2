package com.telemune.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.HttpLinks;

public class LinksManager {
	
	private static final Logger logger = Logger.getLogger(LinksManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	
	public List<HttpLinks> getHttpLinks() {
		List<HttpLinks> httpLst = new ArrayList<HttpLinks>();
		logger.info("Inside getHttpLinks method of LinksManager dao class.");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from HTTP_LINKS";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HttpLinks httpLinks = new HttpLinks();
				httpLinks.setLinkId(rs.getInt("LINK_ID"));
				httpLinks.setLinkDesc(rs.getString("DESCRIPTION"));
				httpLst.add(httpLinks);
			}
		} catch (Exception e) {
			logger.error("Inside catch block of getHttpLinks method of LinksManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return httpLst;
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
				logger.error("Inside finally block of getHttpLinks method of LinksManager dao class. Exception : "
						+ e.toString());
			}
		}
		return httpLst;
	}

	
	public int addUserAccessLink(int roleId, String accessValue) {
		logger.info("Inside addUserAccessLink method of LinksManager dao class.");
		try {
			logger.info("RoleId " + roleId + " Access Link " + accessValue);
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into WEB_ACCESS"
					+ "(LINK_ID,ROLE_ID) values" + "(?,?)";
			pstmt = con.prepareStatement(query);
			String[] linkVlaue = accessValue.split(",");
			for (String linkId : linkVlaue) {
				pstmt.setInt(1, Integer.parseInt(linkId.trim()));
				pstmt.setInt(2, roleId);
				pstmt.addBatch();
			}
			int[] result = pstmt.executeBatch();
			if (result.length > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of addUserAccessLink method of LinksManager dao class. Exception : "
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
				logger.error("Inside finally block of addUserAccessLink method of LinksManager dao class. Exception : "
						+ e.toString());
			}
		}
		return 0;
	}

	
	public int deleteUserAccessLink(int roleId) {
		logger.info("Inside deleteUserAccessLink method of LinksManager dao class.");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "delete from WEB_ACCESS where ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			int result = pstmt.executeUpdate();
			logger.info("delete user access link " + result);
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of deleteUserAccessLink method of LinksManager dao class. Exception : "
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
				logger.error("Inside finally block of deleteUserAccessLink method of LinksManager dao class. Exception : "
						+ e.toString());
			}
		}
		return 0;

	}


}

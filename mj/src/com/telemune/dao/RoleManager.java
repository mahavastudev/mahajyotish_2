package com.telemune.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.RoleInfo;
import com.telemune.model.UserLinkDescription;

public class RoleManager {
	
	private static final Logger logger = Logger.getLogger(RoleManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public List<UserLinkDescription> getUserRoleList(int roleId) {
		List<UserLinkDescription> list = new ArrayList<UserLinkDescription>();
		try {
			logger.info("Inside getUserRoleList of RoleManager dao class with roleId:["+roleId+"].");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select a.ROLE_ID,a.LINK_ID,b.DESCRIPTION from WEB_ACCESS a, HTTP_LINKS b where a.LINK_ID = b.LINK_ID and a.ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserLinkDescription userLink = new UserLinkDescription();
				logger.info("Role Description   " + rs.getString("DESCRIPTION"));
				userLink.setLinkId(rs.getInt("LINK_ID"));
				userLink.setRoleId(rs.getInt("ROLE_ID"));
				userLink.setLinkName(rs.getString("DESCRIPTION"));
				list.add(userLink);
			}
		} catch (Exception e) {
			logger.error("Inside catch block of getUserRoleList of RoleManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return list;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of getUserRoleList of RoleManager dao class. Exception :  "
						+ e.toString());
			}
		}
		return list;
	}

	
	public List<RoleInfo> getRoleList() {
		List<RoleInfo> roleLst = new ArrayList<RoleInfo>();
		try {
			logger.info("Inside getRoleList method of RoleManager dao class.");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from ROLES_MASTER";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RoleInfo roleInfo = new RoleInfo();
				roleInfo.setRoleId(rs.getInt("ROLE_ID"));
				roleInfo.setRoleName(rs.getString("ROLE_NAME"));
				roleInfo.setRoleDesc(rs.getString("DESCRIPTION"));
				roleLst.add(roleInfo);
			}
		} catch (Exception e) {
			logger.error("Inside catch block of getRoleList method of RoleManager dao class. Exception : "
					+ e.toString());
			return roleLst;
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
				logger.error("Inside catch block of getRoleList method of RoleManager dao class. Exception : "
						+ e.toString());
				e.printStackTrace();
			}
		}
		return roleLst;
	}


	public int addUserRoles(RoleInfo roleInfo) {
		logger.info("inside, add user rule");
		try {
			logger.info("Inside addUserRoles method of RoleManager dao class with roleInfo : " + roleInfo.toString());
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into ROLES_MASTER"
					+ "(role_id,ROLE_NAME,DESCRIPTION) values" + "(?,?,?)";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleInfo.getRoleId());
			pstmt.setString(2, roleInfo.getRoleName());
			pstmt.setString(3, roleInfo.getRoleDesc());
			int result = pstmt.executeUpdate();
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of addUserRoles method of RoleManager dao class. Exception : " + e.toString());
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
				logger.error("Inside finally block of addUserRoles method of RoleManager dao class. Exception : " + e.toString());
			}
		}
		return 0;
	}

	
	public int modifyUserRole(RoleInfo roleInfo) {
		try {
			logger.info("Inside modifyUserRole method of RoleManager dao class.");
			con = TSSJavaUtil.instance().getconnection();
			logger.info(roleInfo.toString());
			String query = "update ROLES_MASTER set "
					+ "ROLE_NAME=?,DESCRIPTION=? " + "where ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, roleInfo.getRoleName());
			pstmt.setString(2, roleInfo.getRoleDesc());
			pstmt.setInt(3, roleInfo.getRoleId());
			int result = pstmt.executeUpdate();
			logger.info("modification user role result " + result);
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of modifyUserRole method of RoleManager dao class. Exception : " + e.toString());
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
				logger.error("Inside finally block of modifyUserRole method of RoleManager dao class. Exception : " + e.toString());
			}
		}
		return 0;
	}

	
	public int deleteUserRole(int roleId) {
		try {
			logger.info("Inside deleteUserRole method of RoleManager dao class where roleId:["+roleId+"].");
			con = TSSJavaUtil.instance().getconnection();
			String query = "delete from ROLES_MASTER where ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			int result = pstmt.executeUpdate();
			logger.info("delete user role result " + result);
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of deleteUserRole method of RoleManager dao class. Exception : " + e.toString());
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
				logger.error("Inside finally block of deleteUserRole method of RoleManager dao class. Exception : " + e.toString());
			}
		}
		return 0;
	}

	public RoleInfo getRole(int roleId) {
		logger.info("Inside getRole method of RoleManager dao class with roleId:[" + roleId
				+ "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select a.ROLE_ID,a.ROLE_NAME,a.DESCRIPTION,b.LINK_ID from ROLES_MASTER a, WEB_ACCESS b where a.ROLE_ID = b.ROLE_ID and a.ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			RoleInfo roleInfo = new RoleInfo();
			List<Integer> linkLst = new ArrayList<Integer>();
			while (rs.next()) {
				System.out.println(rs.getInt("LINK_ID"));
				linkLst.add(rs.getInt("LINK_ID"));
				roleInfo.setRoleId(rs.getInt("ROLE_ID"));
				roleInfo.setRoleName(rs.getString("ROLE_NAME"));
				roleInfo.setRoleDesc(rs.getString("DESCRIPTION"));
				roleInfo.setAccessIdLst(linkLst);
			}
			System.out.println("linkList size " + linkLst.size());
			return roleInfo;
		} catch (Exception e) {
			logger.error("Inside catch block of getRole method of RoleManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of getRole method of RoleManager dao class. Exception : " + e.toString());
			}
		}
	}

	
	public boolean isRoleExist(String roleName) {
		logger.info("Inside isRoleExist method of RoleManager dao class where rolename:[" + roleName + "].");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from ROLES_MASTER where ROLE_NAME=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, roleName);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of isRoleExist method of RoleManager dao class. Exception : " + e.toString());
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
				logger.error("Inside finally block of isRoleExist method of RoleManager dao class. Exception : " + e.toString());
			}
		}
		return false;
	}

	
	public int getMaxRoleId() {
		logger.info("Inside getMaxRoleId method of RoleManager dao class.");
		int maxId = 0;
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select max(ROLE_ID) AS ROLE_ID from ROLES_MASTER";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxId = rs.getInt("ROLE_ID");
				System.out.println("maxId " + maxId);
				return maxId;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of getMaxRoleId method of RoleManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return maxId;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("Inside finally block of getMaxRoleId method of RoleManager dao class. Exception : " + e.toString());
			}
		}
		return maxId;
	}

	

}

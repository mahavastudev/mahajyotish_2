package com.telemune.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.UserInfo;

public class UserManager {

	private static final Logger logger = Logger.getLogger(UserManager.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public int addUser(UserInfo userInfo) {
		logger.info("Inside addUser method of UserManager dao class. userInfo:[" + userInfo.toString() + "].");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into adminlogin" + "(NAME,PASSWORD,EMAIL,MOBILE_NO,ROLE_ID,create_date) values"
					+ "(?,?,?,?,?,now())";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userInfo.getUname().trim());
			pstmt.setString(2, userInfo.getUpassword());
			pstmt.setString(3, userInfo.getEmail());
			pstmt.setString(4, userInfo.getMobileNo());
			pstmt.setString(5, userInfo.getRoleId());
			int result = pstmt.executeUpdate();
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of addUser method of UserManager dao class. Exception : " + e.toString());
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
						"Inside finally block of addUser method of UserManager dao class. Exception : " + e.toString());
			}
		}
		return 0;
	}

	public int modifyUserInfo(UserInfo userInfo) {
		logger.info("Inside modifyUserInfo method of UserManager dao class.");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "update adminlogin set " + "NAME=?,PASSWORD=?,EMAIL=?,MOBILE_NO=?,ROLE_ID=? " + "where ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userInfo.getUname().trim());
			pstmt.setString(2, userInfo.getUpassword());
			pstmt.setString(3, userInfo.getEmail());
			pstmt.setString(4, userInfo.getMobileNo());
			pstmt.setString(5, userInfo.getRoleId());
			pstmt.setInt(6, userInfo.getuId());
			int result = pstmt.executeUpdate();
			logger.info("user modify info result " + result);
			if (result > 0) {
				return 1;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of modifyUserInfo method of UserManager dao class. Exception : " + e.toString());
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
						"Inside finally block of modifyUserInfo method of UserManager dao class. Exception : " + e.toString());
			}
		}
		return 0;
	}

	public int deleteUser(String userId) {
		logger.info("Inside deleteUser method of UserManager dao class.");
		int result = 0;
		try {
			logger.info("inside,delete user info " + userId.toString());
			String[] id = userId.split(",");
			for (String uId : id) {
				con = TSSJavaUtil.instance().getconnection();
				String query = "delete from adminlogin where ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(uId.trim()));
				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("Inside catch block of deleteUser method of UserManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return result;
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
						"Inside finally block of deleteUser method of UserManager dao class. Exception : " + e.toString());
			}
		}
		return result;
	}

	public List<UserInfo> getUserList() {
		logger.info("Inside getUserList method of UserManager dao class.");
		List<UserInfo> userLst = new ArrayList<UserInfo>();
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select a.ID,a.NAME,a.MOBILE_NO,a.EMAIL,a.PASSWORD,a.create_date,a.ROLE_ID,b.ROLE_NAME from adminlogin a ,ROLES_MASTER b where a.ROLE_ID=b.ROLE_ID";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("ID"));
				userInfo.setUname(rs.getString("NAME"));
				userInfo.setMobileNo(rs.getString("MOBILE_NO"));
				userInfo.setEmail(rs.getString("EMAIL"));
				userInfo.setUpassword(rs.getString("PASSWORD"));
				userInfo.setCreateDate(rs.getString("create_date"));
				userInfo.setRoleId(rs.getString("ROLE_ID"));
				userInfo.setRoleName(rs.getString("ROLE_NAME"));
				userLst.add(userInfo);
			}
		} catch (Exception e) {
			logger.error("Inside catch block of getUserList method of UserManager dao class. Exception : " + e.toString());
			e.printStackTrace();
			return userLst;
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
						"Inside finally block of getUserList method of UserManager dao class. Exception : " + e.toString());
			}
		}
		return userLst;
	}

	public UserInfo getUserInfo(int uId) {
		logger.info("Inside getUserInfo method of UserManager dao class with userId:["+uId+"].");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, uId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("ID"));
				userInfo.setUname(rs.getString("NAME"));
				userInfo.setMobileNo(rs.getString("MOBILE_NO"));
				userInfo.setEmail(rs.getString("EMAIL"));
				userInfo.setUpassword(rs.getString("PASSWORD"));
				userInfo.setCreateDate(rs.getString("create_date"));
				userInfo.setRoleId(rs.getString("ROLE_ID"));
				return userInfo;
			}
			return null;
		} catch (Exception e) {
			logger.error("Inside catch block of getUserInfo method of UserManager dao class. Exception : " + e.toString());
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
				logger.error(
						"Inside finally block of getUserInfo method of UserManager dao class. Exception : " + e.toString());
			}
		}
	}

	public List<UserInfo> getUserList(String roleId) {
		logger.info("Inside getUserList method of UserManager dao class.");
		List<UserInfo> userLst = new ArrayList<UserInfo>();
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("ID"));
				userInfo.setUname(rs.getString("NAME"));
				userInfo.setMobileNo(rs.getString("MOBILE_NO"));
				userInfo.setEmail(rs.getString("EMAIL"));
				userInfo.setUpassword(rs.getString("PASSWORD"));
				userInfo.setCreateDate(rs.getString("create_date"));
				userInfo.setRoleId(rs.getString("ROLE_ID"));
				userLst.add(userInfo);
			}
			return userLst;
		} catch (Exception e) {
			logger.error("Inside catch block of getUserList method of UserManager dao class. Exception : " + e.toString());
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
						"Inside finally block of getUserList method of UserManager dao class. Exception : " + e.toString());
			}
		}
	}

	public boolean isUserExist(String userEmail) {
		logger.info("Inside isUserExist method of UserManager dao class where userEmail:["+userEmail+"].");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where email=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userEmail);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Inside catch block of isUserExist method of UserManager dao class. Exception : " + e.toString());
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
				logger.error(
						"Inside finally block of isUserExist method of UserManager dao class. Exception : " + e.toString());
			}
		}
		return false;
	}

	public UserInfo getUserInfo(String email) {
		logger.info("Inside getUserInfo method of UserManager dao class. where email:["+email+"].");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where EMAIL=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("ID"));
				userInfo.setUname(rs.getString("NAME"));
				userInfo.setMobileNo(rs.getString("MOBILE_NO"));
				userInfo.setEmail(rs.getString("EMAIL"));
				userInfo.setUpassword(rs.getString("PASSWORD"));
				userInfo.setCreateDate(rs.getString("create_date"));
				userInfo.setRoleId(rs.getString("ROLE_ID"));
				return userInfo;
			}
			return null;
		} catch (Exception e) {
			logger.error("Inside catch block of getUserInfo method of UserManager dao class. Exception : " + e.toString());
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
				logger.error(
						"Inside finally block of getUserInfo method of UserManager dao class. Exception : " + e.toString());
			}
		}
	}

}

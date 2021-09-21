package com.telemune.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.telemune.dbutilities.Connection;
import com.telemune.dbutilities.PreparedStatement;
import com.telemune.mobileAstro.AstroBean;
import com.telemune.mobileAstro.HouseDetailBean;
import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.mobileAstro.TSSJavaUtil;
import com.telemune.model.CountryData;
import com.telemune.model.Event;
import com.telemune.model.GenerateKundli;
import com.telemune.model.HttpLinks;
import com.telemune.model.RoleInfo;
import com.telemune.model.StateData;
import com.telemune.model.UserInfo;
import com.telemune.model.UserLinkDescription;
import com.telemune.util.Constants;

public class UserProfileDaoImpl implements UserProfileDao {

	private static final Logger logger = Logger
			.getLogger(UserProfileDaoImpl.class);
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;


	private Hashtable<String, String> properties = null;
        private String proPath = "";
        public UserProfileDaoImpl() {
                logger.info("Inside constructor of UserProfileDaoImpl dao class.");
                proPath = Constants.PROPERTIES_PATH;
                proPath = proPath + "/kundliHttpserverNew.properties";
                logger.info("property file:"+proPath);
                properties = ReadPropertyFile.readPropery(proPath);
        }

	@Override
	public int authenticateCredentials(UserLinkDescription userDetail) {
		logger.info("inside authenticateCredentials where username[" + userDetail.getUserNme()+ "] pass   [" + userDetail.getPassword() + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where LOWER(EMAIL)=? and PASSWORD=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userDetail.getUserNme().toLowerCase());
			pstmt.setString(2, userDetail.getPassword());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDetail.setUserId(rs.getInt("USER_ID"));
				userDetail.setRoleId(rs.getInt("ROLE_ID"));
				userDetail.setRoleId2(rs.getInt("role_id_2"));
				userDetail.setCcCode(rs.getString("cc_code"));
				return 1;
			}
			return 0;
		} catch (Exception e) {
			logger.error("catch block of Authenticate  " + e.toString());
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
				logger.error("finally block of Authenticate  " + e.toString());
			}
		}
	}
	public int authenticateRegistration(UserLinkDescription userDetail)
	{
		logger.info("inside AuthenticateRegistration   EMAIL ID[" + userDetail.getUserNme()
                                + "] CUSTOMER CODE   [" + userDetail.getCustomerCode() + "]");
                try 
                {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from adminlogin where LOWER(EMAIL)=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, userDetail.getUserNme().toLowerCase());
                        rs = pstmt.executeQuery();
                        if (rs.next()) 
                        {
                                return 1;
                        }
                        return 0;
                } 
                catch (Exception e) 
                {
                        logger.error("catch block of Authenticate  " + e.toString());
                        return 0;
                } 
                finally 
                {
                	  try 
                	  {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                       } 
                	  catch (Exception e)
                	  {
                         logger.error("finally block of Authenticate  " + e.toString());                 
			          }
                }

	}
	

	public int checkRequest(UserLinkDescription userDetail)
	{
       logger.info("inside checkRequest   EMAIL ID[" + userDetail.getUserNme()
                                + "] CUSTOMER CODE   [" + userDetail.getCustomerCode() + "]");
         try
         {
          con = TSSJavaUtil.instance().getconnection();
          String query = "select * from register_request where LOWER(email)=? or cust_code=?";
          pstmt = con.prepareStatement(query);
          pstmt.setString(1, userDetail.getUserNme().toLowerCase());
          pstmt.setString(2, userDetail.getCustomerCode());
          rs = pstmt.executeQuery();
          if (rs.next())
           {
			userDetail.setTokenId(rs.getString("token_id"));
            return 1;
           }
           return 0;
           }
           catch (Exception e)
           {
             logger.error("catch block of checkRequest  " + e.toString());
             return 0;
           }
         finally
           {
             try
             {
				if (rs != null)
                rs.close();
                if (pstmt != null)
                pstmt.close();
                if (con != null)
                TSSJavaUtil.instance().freeConnection(con);
              }
             catch (Exception e)
             {
              logger.error("finally block of checkRequest  " + e.toString());
             }
           }
        }
	

	public int registerRequest(UserLinkDescription userDetail,String access)
	{
        logger.info("inside registerRequest   EMAIL ID[" + userDetail.getUserNme()
                                + "] CUSTOMER CODE   [" + userDetail.getCustomerCode() + "]"+"  Access: "+access);
		int result=-1;
                try 
                {
                  con = TSSJavaUtil.instance().getconnection();
                  String query = "insert into register_request(token_id,email,cust_code,register_date,expiry_date,client_name,access,role_Id) values(?,?,?,now(),now(),?,?,?)";
                  pstmt = con.prepareStatement(query);
			      String token = UUID.randomUUID().toString().replace("-", "");
			      userDetail.setTokenId(token);
			      logger.info("token>> "+token);
			      pstmt.setString(1,token);
                  pstmt.setString(2, userDetail.getUserNme().toLowerCase());
                  pstmt.setString(3, userDetail.getCustomerCode());
                  pstmt.setString(4, userDetail.getClientName());
                  pstmt.setString(5, access);
                  pstmt.setInt(6,userDetail.getRoleId() );
                  result = pstmt.executeUpdate();
                  return result;
                } 
                catch (Exception e) 
                {
                  logger.error("catch block of checkRequest  " + e.toString());
                  return result;
                } 
                finally 
                {
                  try  
                  {
                    if (rs != null)
                    rs.close();
                    if (pstmt != null)
                    pstmt.close();
                    if (con != null)
                    TSSJavaUtil.instance().freeConnection(con);
                  } 
                  catch (Exception e)
                  {
                    logger.error("finally block of checkRequest  " + e.toString());

                  }
                }
        }

	@Override
	public List<UserLinkDescription> getUserRoleList(int roleId) 
	{
		List<UserLinkDescription> list = new ArrayList<UserLinkDescription>();
		try 
		{
			logger.info("Inside getting user role list according to roleId ["
					+ roleId + "]");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select a.ROLE_ID,a.LINK_ID,b.DESCRIPTION from WEB_ACCESS a, HTTP_LINKS b where a.LINK_ID = b.LINK_ID and a.ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				UserLinkDescription userLink = new UserLinkDescription();
				//logger.info("Role Description   " + rs.getString("DESCRIPTION"));
				userLink.setLinkId(rs.getInt("LINK_ID"));
				userLink.setRoleId(rs.getInt("ROLE_ID"));
				userLink.setLinkName(rs.getString("DESCRIPTION"));
				list.add(userLink);
			}
		} 
		catch (Exception e) 
		{
			logger.error("catch block of get user role list  " + e.toString());
			return list;
		} 
		finally 
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e) 
			{
				logger.error("finally block of get user role list  "
						+ e.toString());
			}
		}
		return list;
	}

	@Override
	public int addUser(UserInfo userInfo)
	{
		try
		{
			logger.info("inside insert new user info");
			logger.info("user input data " + userInfo.toString());
			con = TSSJavaUtil.instance().getconnection();
			//updated by gaurav Sharma  23rd Sept 2020, adding mv code from weeeeeeeeeeeeeeeeeeeeeeeeeb while adding user
			
			//String query = "insert into adminlogin"	+ "(NAME,PASSWORD,EMAIL,MOBILE_NO,ROLE_ID,create_date,kundli_prefix) values"+ "(?,?,?,?,?,now(),?)";
			String query = "insert into adminlogin"	+ "(NAME,PASSWORD,EMAIL,MOBILE_NO,ROLE_ID,create_date,kundli_prefix,cc_code) values"+ "(?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(query);
			//pstmt.setInt(1, userInfo.getuId());
			pstmt.setString(1, userInfo.getUname().trim());
			pstmt.setString(2, userInfo.getUpassword());
			 pstmt.setString(3, userInfo.getEmail()); 
			/* pstmt.setString(3, "neha.gupta@telemune.com"); */
			pstmt.setString(4, userInfo.getMobileNo());
			pstmt.setString(5, userInfo.getRoleId());
			pstmt.setString(6, userInfo.getPrefix());
			pstmt.setString(7, userInfo.getCcCode());
			int result = pstmt.executeUpdate();
			if (result > 0)
			{
				return 1;
			}
		} 
		catch (Exception e) 
		{
			logger.error("catch block of add new user  " + e.toString());
			return 0;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			}
			catch (Exception e) 
			{
				logger.error("finally block of add new user   " + e.toString());
			}
		}
		return 0;
	}

	@Override
	public int modifyUserInfo(UserInfo userInfo) 
	{
		try
		{
			logger.info("inside,modify user info");
			con = TSSJavaUtil.instance().getconnection();
			/**
			 * condition handle by Gaurav Sharma on 25th Aug 2020 11:36 pm 
			 * updating ROLE_ID_2 */
			//String query = "update adminlogin set "+ "NAME=?,PASSWORD=?,EMAIL=?,MOBILE_NO=?,ROLE_ID=?,kundli_prefix=?"+ "where USER_ID=?";
			String query = "update adminlogin set "+ "NAME=?,PASSWORD=?,EMAIL=?,MOBILE_NO=?,ROLE_ID=?,ROLE_ID_2=?,kundli_prefix=?"+ "where USER_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userInfo.getUname().trim());
			pstmt.setString(2, userInfo.getUpassword());
			pstmt.setString(3, userInfo.getEmail());
			pstmt.setString(4, userInfo.getMobileNo());
			pstmt.setString(5, userInfo.getRoleId());
			pstmt.setString(6, userInfo.getRoleId2());
			pstmt.setString(7, userInfo.getPrefix());
			pstmt.setInt(8, userInfo.getuId());
			int result = pstmt.executeUpdate();
			logger.info("user modify info result " + result);
			if (result > 0)
			{
				return 1;
			}
		} 
		catch (Exception e)
		{
			logger.error("catch block of modify user info " + e.toString());
			return 0;
		}
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e)
			{
				logger.error("finally block of modify user info "
						+ e.toString());
			}
		}
		return 0;
	}

	@Override
	public int deleteUser(String userId)
	{
		int result = 0;
		try
		{
			logger.info("inside,delete user info " + userId.toString());
			String[] id = userId.split(",");
			for (String uId : id) 
			{
				con = TSSJavaUtil.instance().getconnection();
				String query = "delete from adminlogin where USER_ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(uId.trim()));
				result = pstmt.executeUpdate();
			}
		} 
		catch (Exception e) 
		{
			logger.error("catch block of delete user " + e.toString());
			return result;
		} 
		finally 
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			}
			catch (Exception e) 
			{
				logger.error("finally block of delete user " + e.toString());
			}
		}
		return result;
	}

	@Override
	public List<UserInfo> getUserList()
	{
		List<UserInfo> userLst = new ArrayList<UserInfo>();
		try
         {
			logger.info("inside, getting users Info");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select a.USER_ID,a.NAME,a.cc_code,a.MOBILE_NO,a.EMAIL,a.PASSWORD,a.create_date,a.ROLE_ID,a.ROLE_ID_2,b.ROLE_NAME from adminlogin a ,ROLES_MASTER b where a.ROLE_ID=b.ROLE_ID";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) 
			{
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("USER_ID"));
				userInfo.setUname(rs.getString("NAME"));
				userInfo.setMobileNo(rs.getString("MOBILE_NO"));
				userInfo.setEmail(rs.getString("EMAIL"));
				userInfo.setUpassword(rs.getString("PASSWORD"));
				userInfo.setCreateDate(rs.getString("create_date"));
				userInfo.setRoleId(rs.getString("ROLE_ID"));
				userInfo.setRoleId2(rs.getString("ROLE_ID_2"));
				userInfo.setRoleName(rs.getString("ROLE_NAME"));
				userInfo.setCcCode(rs.getString("cc_code"));
				
				
				String q="select role_name from roles_master where role_id=?";
				PreparedStatement pstmt1 = con.prepareStatement(q);
				pstmt1.setString(1, userInfo.getRoleId2());
				ResultSet rs1 = pstmt1.executeQuery();
				if (rs1.next()) 
				{
					userInfo.setRoleName2(rs1.getString("role_name"));
				}
				
				
				userLst.add(userInfo);
				
				
				
			}
			
			
			
			
		} 
		catch (Exception e)
		{
			logger.error("catch block of reading user info " + e.toString());
			return userLst;
		} 
		finally 
		{
			try 
			{
				if (pstmt != null) 
				pstmt.close();				
				if (rs != null) 
				rs.close();				
				if (con != null)
				TSSJavaUtil.instance().freeConnection(con);				
			 }
			  catch (Exception e) 
			   {
				logger.error("finally block of reading user info "+ e.toString());
			   }
		}
		return userLst;
	}

	@Override
	public List<RoleInfo> getRoleList() {
		List<RoleInfo> roleLst = new ArrayList<RoleInfo>();
		try {
			logger.info("inside, getting role Info");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from ROLES_MASTER";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) 
			{
				RoleInfo roleInfo = new RoleInfo();
				roleInfo.setRoleId(rs.getInt("ROLE_ID"));
				roleInfo.setRoleName(rs.getString("ROLE_NAME"));
				roleInfo.setRoleDesc(rs.getString("DESCRIPTION"));
				roleLst.add(roleInfo);
			}
		} catch (Exception e) {
			logger.error("catch block of reading user role info "
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
				logger.error("finally block of reading user role info "
						+ e.toString());
			}
		}
		return roleLst;
	}

	@Override
	public UserInfo getUserInfo(int uId) {
		logger.info("inside getting user Info according to userId[" + uId + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where USER_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, uId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("USER_ID"));
				userInfo.setUname(rs.getString("NAME"));
				userInfo.setMobileNo(rs.getString("MOBILE_NO"));
				userInfo.setEmail(rs.getString("EMAIL"));
				userInfo.setUpassword(rs.getString("PASSWORD"));
				userInfo.setCreateDate(rs.getString("create_date"));
				userInfo.setRoleId(rs.getString("ROLE_ID"));
				//handling for second role by Gaurav Sharma on 25 Aug 2020 11:25 pm
				userInfo.setRoleId2(rs.getString("ROLE_ID_2"));
				userInfo.setPrefix(rs.getString("kundli_prefix"));
				return userInfo;
			}
			return null;
		} catch (Exception e) {
			logger.error("catch block of getting user info  "+e.toString());
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
				logger.error("finally block of getting user info  "
						+ e.toString());
			}
		}
	}

	@Override
	public int addUserRoles(RoleInfo roleInfo) {
		logger.info("inside, add user rule");
		try {
			logger.info("user role input data " + roleInfo.toString());
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
			logger.error("catch block of add user role  " + e.toString());
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
				logger.error("finally block of add user role " + e.toString());
			}
		}
		return 0;
	}

	@Override
	public int modifyUserRole(RoleInfo roleInfo) {
		try {
			logger.info("inside,modify user role info");
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
			logger.error("catch block of modify user role " + e.toString());
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
				logger.error("finally block of modify user role "
						+ e.toString());
			}
		}
		return 0;
	}

	@Override
	public int deleteUserRole(int roleId) 
	{
		try
		{
			logger.info("inside,delete user role");
			con = TSSJavaUtil.instance().getconnection();
			String query = "delete from ROLES_MASTER where ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			int result = pstmt.executeUpdate();
			logger.info("delete user role result " + result);
			if (result > 0) 
			{
				return 1;
			}
		} 
		catch (Exception e) 
		{
			logger.error("catch block of delete user role " + e.toString());
			return 0;
		} 
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e) 
			{
				logger.error("finally block of delete user role "
						+ e.toString());
			}
		}
		return 0;
	}

	@Override
	public RoleInfo getRole(int roleId) 
	{
		logger.info("inside getting role Info according to roleId[" + roleId
				+ "]");
		try 
		{
			con = TSSJavaUtil.instance().getconnection();
			String query = "select a.ROLE_ID,a.ROLE_NAME,a.DESCRIPTION,b.LINK_ID from ROLES_MASTER a, WEB_ACCESS b where a.ROLE_ID = b.ROLE_ID and a.ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			rs = pstmt.executeQuery();
			RoleInfo roleInfo = new RoleInfo();
			List<Integer> linkLst = new ArrayList<Integer>();
			while (rs.next()) 
			{
				System.out.println(rs.getInt("LINK_ID"));
				linkLst.add(rs.getInt("LINK_ID"));
				roleInfo.setRoleId(rs.getInt("ROLE_ID"));
				roleInfo.setRoleName(rs.getString("ROLE_NAME"));
				roleInfo.setRoleDesc(rs.getString("DESCRIPTION"));
				roleInfo.setAccessIdLst(linkLst);
			}
			System.out.println("linkList size " + linkLst.size());
			return roleInfo;
		}
		catch (Exception e)
		{
			logger.error("catch block of getting role info  " + e.toString());
			return null;
		} 
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e) 
			{
				logger.error("finally block of getting role info  "
						+ e.toString());
			}
		}
	}

	@Override
	public List<UserInfo> getUserList(String roleId) {
		List<UserInfo> userLst = new ArrayList<UserInfo>();
		try {
			logger.info("inside, getting users Info");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("USER_ID"));
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
			logger.error("catch block of reading user info " + e.toString());
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
				logger.error("finally block of reading user info "
						+ e.toString());
			}
		}
	}

	@Override
	public boolean isUserExist(String userEmail) {
		logger.info("inside is user exist user[" + userEmail + "]");
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
			logger.error("catch block of Authenticate  " + e.toString());
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
				logger.error("finally block of Authenticate  " + e.toString());
			}
		}
		return false;
	}

	@Override
	public boolean isRoleExist(String roleName)
	{
		logger.info("inside is role exist as rolename[" + roleName + "]");
		try 
		{
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from ROLES_MASTER where ROLE_NAME=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, roleName);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				return true;
			}
		} 
		catch (Exception e)
		{
			logger.error("catch block of Authenticate roleName  "+ e.toString());
			return false;
		}
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			}
			catch (Exception e)
			{
				logger.error("finally block of Authenticate roleName  "+ e.toString());
			}
		}
		return false;
	}

	public List<HttpLinks> getHttpLinks() {
		List<HttpLinks> httpLst = new ArrayList<HttpLinks>();
		try {
			logger.info("inside, getting all httpLinks");
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
		} 
		catch (Exception e) 
		{
			logger.error("catch block of getting all httpLinks " + e.toString());
			return httpLst;
		}
		finally
		{
			try
			{
				if (pstmt != null) 
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null) 
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("finally block of getting all httpLinks "
						+ e.toString());
			}
		}
		return httpLst;
	}

	@Override
	public int getMaxRoleId() 
	{
		logger.info("inside getting max roleId");
		int maxId = 0;
		try 
		{
			con = TSSJavaUtil.instance().getconnection();
			String query = "select max(ROLE_ID) AS ROLE_ID from ROLES_MASTER";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				maxId = rs.getInt("ROLE_ID");
				System.out.println("maxId " + maxId);
				return maxId;
		    }
		} 
		catch (Exception e)
		{
			logger.error("catch block of getting max roleId " + e.toString());
			return maxId;
		}
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e) 
			{
				logger.error("finally block of getting max roleId  "+ e.toString());
			}
		}
		return maxId;
	}

	@Override
	public int addUserAccessLink(int roleId, String accessValue) 
	{
		try 
		{

			logger.info("inside, insert user access link");
			logger.info("RoleId " + roleId + " Access Link " + accessValue);
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into WEB_ACCESS"
					+ "(LINK_ID,ROLE_ID) values" + "(?,?)";
			pstmt = con.prepareStatement(query);
			String[] linkVlaue = accessValue.split(",");
			for (String linkId : linkVlaue) 
			{
				pstmt.setInt(1, Integer.parseInt(linkId.trim()));
				pstmt.setInt(2, roleId);
				pstmt.addBatch();
			}
			int[] result = pstmt.executeBatch();
			if (result.length > 0) 
			{
				return 1;
			}
		}
		catch (Exception e)
		{
			logger.error("catch block of insert user access link  "+ e.toString());
			return 0;
		} 
		finally 
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e) 
			{
				logger.error("finally block of insert user access link "
						+ e.toString());
			}
		}
		return 0;
	}

	/*
	 * @Override public int modifyUserAccessLink(int roleId, String accessValue)
	 * { try{ logger.info("inside, modify user access link"); con =
	 * TSSJavaUtil.instance().getconnection(); String query =
	 * "update WEB_ACCESS set "+ "LINK_ID=? "+ "where ROLE_ID=?"; pstmt =
	 * con.prepareStatement (query); String[] linkVlaue =
	 * accessValue.split(","); for(String linkId: linkVlaue){ pstmt.setInt(1,
	 * Integer.parseInt(linkId.trim())); pstmt.setInt(2, roleId);
	 * pstmt.executeUpdate(); } int[] result = pstmt.executeBatch();
	 * if(result.length>0){ return 1; } } catch (Exception e) {
	 * logger.error("catch block of modify user access link  "+e.toString());
	 * return 0; } finally { try { if (pstmt != null) pstmt.close(); if (rs !=
	 * null) rs.close(); if (con != null)
	 * TSSJavaUtil.instance().freeConnection(con); } catch (Exception e) {
	 * logger.error("finally block of modify user access link " + e.toString());
	 * } } return 0; }
	 */

	@Override
	public int deleteUserAccessLink(int roleId)
	{
		try
		{
			logger.info("inside,delete user access link");
			con = TSSJavaUtil.instance().getconnection();
			String query = "delete from WEB_ACCESS where ROLE_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, roleId);
			int result = pstmt.executeUpdate();
			logger.info("delete user access link " + result);
			if (result > 0)
			{
				return 1;
			}
		}
		catch (Exception e)
		{
			logger.error("catch block of delete user access link "
					+ e.toString());
			return 0;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			}
			catch (Exception e)
			{
				logger.error("finally block of delete user access link "
						+ e.toString());
			}
		}
		return 0;

	}

	@Override
	public int changePassword(int userId, String newPassword) 
	{
		try 
		{
			logger.info("inside,change user password [" + newPassword + "]");
			con = TSSJavaUtil.instance().getconnection();
			String query = "update adminlogin set " + "PASSWORD=? "
					+ "where USER_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, newPassword);
			pstmt.setInt(2, userId);
			int result = pstmt.executeUpdate();
			logger.info("change user password result " + result);
			if (result > 0) {
				return 1;
			}
		} 
		catch (Exception e)
		{
			logger.error("catch block of cahnge user password " + e.toString());
			return 0;
		} 
		finally
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("finally block of change user password "
						+ e.toString());
			}
		}
		return 0;
	}

	public int saveDetails(GenerateKundli generateKundli) 
	{
		int requestId=-1;
		try
		{
			logger.info("inside, saveDetails [" + generateKundli + "]");
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into ASTRO_REQUEST_LOG(NAME,DOB,tob,longitude,latitude,CITY,CITY_CODE,USER_ID,COUNTRY,STATE,transit_kundli,transit_dob,transit_tob,remarks,occupation,account_id,contact_id,TRANSIT_LOCATION,TRANSIT_COUNTRY_CODE,TRANSIT_STATE_CODE,TRANSIT_CITY,TRANSIT_LATITUDE,TRANSIT_LONGITUDE,TIMESTAMP,CC_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
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
            pstmt.setString(15, generateKundli.getOccupation());
            pstmt.setString(16, generateKundli.getAccountId());
            pstmt.setString(17, generateKundli.getContactId());
            pstmt.setInt(18, generateKundli.getTransitLocation());
            pstmt.setString(19, generateKundli.getTransitCountry());
            pstmt.setString(20, generateKundli.getTransitState());
            pstmt.setString(21, generateKundli.getTransitCity());
            pstmt.setString(22, generateKundli.getTransitLatitude());
            pstmt.setString(23, generateKundli.getTransitLongitude());
            pstmt.setString(24,generateKundli.getCcCode());
			int result = pstmt.executeUpdate();
			pstmt.close();
			
			query="select max(REQUEST_ID) as reqId from ASTRO_REQUEST_LOG";

			if (result > 0) 
			{
				logger.info("QUERY:: "+query);
				pstmt = con.prepareStatement(query);
				rs=pstmt.executeQuery();
				while(rs.next())
                 {
                   requestId = rs.getInt("reqId");
                 }	
				return requestId;
			}
		} 
		catch (Exception e) 
		{
			logger.error("catch block of saveDetails " + e.toString());
			return 0;
		}
		finally
		{
			try 
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e) 
			{
				logger.error("finally block of saveDetails" + e.toString());
			}
		}
		return requestId;
	}


	public int updateKundliDetails(GenerateKundli generateKundli)
	{
                try 
                {
                        logger.info("inside, updateKundliDetails [" + generateKundli.getRequestId() + "]");
                        logger.info("inside, updateKundliDetails==>"+generateKundli);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "update ASTRO_REQUEST_LOG set NAME=?,DOB=?,tob=?,longitude=?,latitude=?,CITY=?,CITY_CODE=?,COUNTRY=?,STATE=?,transit_kundli=?,transit_dob=?,transit_tob=?,remarks=?,occupation=?,account_id=?,contact_id=?,TRANSIT_LOCATION=?,TRANSIT_COUNTRY_CODE=?,TRANSIT_STATE_CODE=?,TRANSIT_CITY=?,TRANSIT_LATITUDE=?,TRANSIT_LONGITUDE=?, CC_CODE=? where REQUEST_ID=?";
                            pstmt = con.prepareStatement(query);
                            pstmt.setString(1, generateKundli.getName().trim());
                            pstmt.setString(2, generateKundli.getDob());
                            pstmt.setString(3, generateKundli.getTob());
                            pstmt.setString(4, generateKundli.getLongitude());
                            pstmt.setString(5, generateKundli.getLatitude());
                            pstmt.setString(6, generateKundli.getCity());
                            pstmt.setString(7, generateKundli.getCityCode());
                          //  pstmt.setInt(8, generateKundli.getUserId());
                            pstmt.setString(8, generateKundli.getCountry());
                            pstmt.setString(9, generateKundli.getState());
                            pstmt.setInt(10, generateKundli.getIsTransitKundli());
                            pstmt.setString(11, generateKundli.getTransitDob());
                            pstmt.setString(12, generateKundli.getTransitTob());
                            pstmt.setString(13, generateKundli.getRemarks());
                            pstmt.setString(14, generateKundli.getOccupation());
                            pstmt.setString(15, generateKundli.getAccountId());
                            pstmt.setString(16, generateKundli.getContactId());
                            pstmt.setInt(17, generateKundli.getTransitLocation());
                      	    pstmt.setString(18, generateKundli.getTransitCountry());
                            pstmt.setString(19, generateKundli.getTransitState());
                            pstmt.setString(20, generateKundli.getTransitCity());
                            pstmt.setString(21, generateKundli.getTransitLatitude());
                            pstmt.setString(22, generateKundli.getTransitLongitude());
                            pstmt.setString(23,generateKundli.getCcCode());
    			            pstmt.setInt(24, generateKundli.getRequestId());
                       
                        int result = pstmt.executeUpdate();
                        if (result > 0) 
                        {
                          return 1;
                        }
                } 
                catch (Exception e) 
                {
                  logger.error("catch block of updateKundliDetails " + e.toString());
                  return 0;
                } 
                finally 
                {
                        try 
                        {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } 
                        catch (Exception e)
                        {
                                logger.error("finally block of updateKundliDetails" + e.toString());
                        }
                }
                return 0;
        }
	

	@Override
	public List<GenerateKundli> getOldKundliLogs(int userId) 
	{
		List<GenerateKundli> kundliLogsLst = new ArrayList<GenerateKundli>();
		try 
		{
			logger.info("Inside getting old kundli logs list");
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from astro_request_log where USER_ID=?";
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
				generateKundli.setOccupation(rs.getString("occupation"));
				kundliLogsLst.add(generateKundli);
			}
			return kundliLogsLst;
		}
		catch (Exception e)
		{
			logger.error("catch block of reading user old kundli logs list "
					+ e.toString());
			return null;
		} 
		finally
		{
			try 
			{
				if (pstmt != null)
				{
					pstmt.close();
				}
				if (rs != null) 
				{
					rs.close();
				}
				if (con != null) 
				{
					TSSJavaUtil.instance().freeConnection(con);
				}
			} 
			catch (Exception e)
			{
				logger.error("finally block of reading user old kundli logs list "
						+ e.toString());
			}
		}
	}

	@Override
	public int deleteKundliLog(int requestId) 
	{
		try 
		{
			logger.info("inside,delete kundli log by requestId " + requestId);
			con = TSSJavaUtil.instance().getconnection();
			String query = "delete from ASTRO_REQUEST_LOG where REQUEST_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, requestId);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				return 1;
			}
		} 
		catch (Exception e)
		{
			logger.error("catch block of delete kundli log " + e.toString());
			return 0;
		} 
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} catch (Exception e) {
				logger.error("finally block of delete kundli log "
						+ e.toString());
			}
		}
		return 0;
	}

	@Override
	public GenerateKundli getGenerateKundliLog(int requestId) {
		GenerateKundli generateKundli = new GenerateKundli();
		try {
			logger.info("Inside reading old kundli log by requestId "
					+ requestId);
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
                                generateKundli.setOccupation(rs.getString("occupation"));
                                generateKundli.setAccountId(rs.getString("account_id"));
                                generateKundli.setContactId(rs.getString("contact_id"));
                                int isTransitKundli =rs.getInt("transit_kundli");
                                if(isTransitKundli==1)
                                {
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
                                int isTransitLocation= rs.getInt("TRANSIT_LOCATION");
                                if(isTransitLocation==1)
                                {
                                 generateKundli.setTransitLocation(rs.getInt("TRANSIT_LOCATION"));
                                 generateKundli.setTransitCountry(rs.getString("TRANSIT_COUNTRY_CODE"));
                                 generateKundli.setTransitState(rs.getString("TRANSIT_STATE_CODE"));
                                 generateKundli.setTransitCity(rs.getString("TRANSIT_CITY"));
                                 generateKundli.setTransitLatitude(rs.getString("TRANSIT_LATITUDE"));
                                 generateKundli.setTransitLongitude(rs.getString("TRANSIT_LONGITUDE"));
                                 }

			}
			return generateKundli;
		} catch (Exception e) {
			logger.error("catch block of reading old kundli log "
					+ e.toString());
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
				logger.error("finally block of reading old kundli log "
						+ e.toString());
			}
		}
	}

	@Override
	public UserInfo getUserInfo(String email) {
		logger.info("inside getting user Info by userEmail[" + email + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where EMAIL=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setuId(rs.getInt("USER_ID"));
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
			logger.error("catch block of getting user info  " + e.toString());
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
				logger.error("finally block of getting user info  "
						+ e.toString());
			}
		}
	}

	@Override
	public List<CountryData> getCountryList() {
		List<CountryData> countryLst = new ArrayList<CountryData>();
		try {
			logger.info("inside, getting country lst from DB");
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
			logger.error("catch block of reading country lst from DB "
					+ e.toString());
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
				logger.error("finally block of reading country lst from DB "
						+ e.toString());
				return countryLst;
			}
		}
		return countryLst;
	}

	public List<StateData> getStateList(String contryCode) {
		logger.info("inside getting state list from DB where contryCode "
				+ contryCode);
		List<StateData> stateLst = new ArrayList<StateData>();
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from state where coun_code=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, contryCode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StateData stateData = new StateData();
				stateData.setSname(rs.getString("name"));
				stateData.setScode(rs.getString("code"));
				stateData.setCntryCode(rs.getString("coun_code"));
				stateLst.add(stateData);
			}
			return stateLst;
		} catch (Exception e) {
			logger.error("catch block of getting state list  " + e.toString());
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
				logger.error("finally block of getting state list  "
						+ e.toString());
				return stateLst;
			}
		}
	}
        
/*        @Override
	public boolean isUserKundliInfoExist(String userName,int userId) {
		logger.info("inside is user kundli info exist username [" + userName + "]"+" userId["+userId+"]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from astro_request_log where NAME=? and USER_ID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userName);
                        pstmt.setInt(2,userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			logger.error("catch block of is user kundli info exist  "
					+ e.toString());
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
				logger.error("finally block of is user kundli info exist  "
						+ e.toString());
			}
		}
		return false;
	}*/
	
	public boolean isUserKundliInfoExist(int requestId) {
                logger.info("inside is user kundli info exist requestId [" + requestId + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from astro_request_log where REQUEST_ID=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1,requestId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                return true;
                        }
                } catch (Exception e) {
                        logger.error("catch block of is user kundli info exist  "
                                        + e.toString());
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
                                logger.error("finally block of is user kundli info exist  "
                                                + e.toString());
                        }
                }
                return false;
        }
	

	public boolean validateActivation(UserInfo userInfo) {
                logger.info("inside  validateActivation tokenId [" + userInfo.getTokenId() + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from register_request where token_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1,userInfo.getTokenId());
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
				userInfo.setEmail(rs.getString("email"));
				userInfo.setUname(rs.getString("client_name"));
				userInfo.setAccess(rs.getString("access"));
				userInfo.setRoleId(rs.getInt("role_id")+"");
				logger.info("================================"+userInfo.toString());
                                return true;
                        }
                } catch (Exception e) {
                        logger.error("catch block of is user kundli info exist  "
                                        + e.toString());
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
                                logger.error("finally block of is user kundli info exist  "
                                                + e.toString());
                        }
                }
                return false;
        }


	public int addkundliData(GenerateKundli generateKundli,String kundliData){
		logger.info("inside, addkundliData [" + generateKundli + "]");
		int result=-1;
		try {
                        logger.info("inside, addkundliData [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "insert into  kundli_metadata(name,state,city,country,dob,tob,user_id,create_date,kundli_data,remarks,occupation) values(?,?,?,?,?,?,?,now(),?,?,?)";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, generateKundli.getName().trim());
			pstmt.setString(2, generateKundli.getState());
			pstmt.setString(3, generateKundli.getCity());
			pstmt.setString(4, generateKundli.getCountry());
			pstmt.setString(5, generateKundli.getDob());
			pstmt.setString(6, generateKundli.getTob());
			pstmt.setInt(7, generateKundli.getUserId());
			pstmt.setString(8, "");			
			pstmt.setString(9, generateKundli.getRemarks());			
			pstmt.setString(10, generateKundli.getOccupation());			
                        result = pstmt.executeUpdate();
                        pstmt.close();		
		}catch (Exception e) {
                        logger.error("catch block of addkundliData " + e.toString());
                        return 0;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of addkundliData" + e.toString());
                        }
                } 
                return result;
			

	}

	
	public int getUserId()
	{

		logger.info("inside getUserId method");
		int used_id = 0;
		try 
		{
			con = TSSJavaUtil.instance().getconnection();
			String query = "select max(user_id) as  user_id from generate_kundli";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				used_id = rs.getInt("user_id");
				System.out.println("used_id " + used_id);
				return used_id;
		    }
		} 
		catch (Exception e)
		{
			logger.error("catch block of getting max user id " + e.toString());
			return used_id;
		}
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e) 
			{
				logger.error("finally block of getting max user id  "+ e.toString());
			}
		}
		return used_id;
	
		
	}
	
	public int updateEmail(String email,int user_id) {

		try
		{
			logger.info("inside updateEmail method ");
			con = TSSJavaUtil.instance().getconnection();
			String query = "update generate_kundli set email=? where user_id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, email.trim());
			pstmt.setInt(2, user_id);
			int result = pstmt.executeUpdate();
			logger.info("email modify info result " + result);
			if (result > 0)
			{
				return 1;
			}
		} 
		catch (Exception e)
		{
			logger.error("catch block of updateEmail  " + e.toString());
			return 0;
		}
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e)
			{
				logger.error("finally block of updateEmail "
						+ e.toString());
			}
		}
		return 0;
	
		
	}
	
	
	/**Method created by Gaurav Sharma on 26 Aug 2020 at 12:07 
	 * wait......
	 * its my birthday today Happy Birthday to me. */
	public int addHoroscopeData(GenerateKundli generateKundli,AstroBean astroBean){
		logger.info("hreeeeeeeeeeeeeeeeee inside, addHoroscopeData "+generateKundli.toString());
		int result=-1;
		try {
			Map<String, String> signSortName=new HashMap<>();
			signSortName.put("Aries","Ar");
			signSortName.put("Taurus","Ta");
			signSortName.put("Gemini","Ge");
			signSortName.put("Cancer","Cn");
			signSortName.put("Leo","Le");
			signSortName.put("Virgo","Vi");
			signSortName.put("Libra","Li");
			signSortName.put("Scorpio","Sc");
			signSortName.put("Sagittarius","Sa");
			signSortName.put("Capricorn","Cp");
			signSortName.put("Aquarius","Aq");
			signSortName.put("Pisces","Pi");
			
					//Map<String, HouseDetailBean> planetDetailList= astroBean.getPlanetDetailHashTable();	
					//HouseDetailBean houseDetailbean ;
					
					Map<String, ArrayList<HouseDetailBean>> occupantTableMap = astroBean.getHouseOccupantHashTable();
                    logger.info("inside, addkundliData [" + generateKundli + "]");
                    con = TSSJavaUtil.instance().getconnection();
                    int count=1;
                    String query="insert into horoscope_metadata (user_id,country,state ,city ,name, dob ,tob , "
                    		+ "1S ,1D ,1O ,2S ,2D ,2O ,3S ,3D ,3O ,4S ,4D ,4O ,5S ,5D ,5O ,6S ,6D ,6O ,7S ,7D ,7O ,"
                    		+ "8S ,8D ,8O ,9S ,9D ,9O ,10S ,10D ,10O ,11S ,11D ,11O ,12S ,12D ,12O ,KeS , KeD ,VeS ,"
                    		+ " VeD ,SuS , SuD ,MooS , MooD ,MaS , MaD ,RaS , RaD ,JuS , JuD ,SaS , SaD ,MeS , MeD ) "
                    		+ "values(?,?,? ,? ,?, ? ,? , ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,"
                    		+ "? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , ? ,? , ? ,? , ? ,? , ? ,? , ? ,? "
                    		+ ", ? ,? , ? ,? , ? ,? , ? )";
                    
                    pstmt = con.prepareStatement(query);
                    
                    
                    pstmt.setInt(count, generateKundli.getUserId());
                    count++;
                    pstmt.setString(count, generateKundli.getCountry());
                    count++;
                    pstmt.setString(count, generateKundli.getState());
                    count++;
                    pstmt.setString(count, generateKundli.getCity());
                    count++;
                    pstmt.setString(count, generateKundli.getName().trim());
                    count++;
					pstmt.setString(count, generateKundli.getDob());
					count++;
					pstmt.setString(count, generateKundli.getTob());
					count++;
					
					for (Entry<String, ArrayList<HouseDetailBean>> entry : occupantTableMap.entrySet()) {
						ArrayList<HouseDetailBean> occupantList=entry.getValue();
						String k=entry.getKey();
						//logger.info("hreeeeeeeeeeeeeeeeee k["+k+"] size of list ["+occupantList.size()+"]");
						pstmt.setString(count, signSortName.get(astroBean.getHouseDetailHashTable().get(k).getSignName()));
						count++;
						pstmt.setString(count, astroBean.getHouseDetailHashTable().get(k).getDegree());
						count++;
						if(occupantList.isEmpty())
							pstmt.setString(count, "");
						else{
							//pick short planet name
							String occupant=occupantList.stream().map(z->z.getPlanetName().substring(0,2)).collect(Collectors.joining(","));
							pstmt.setString(count, occupant);
						}
						count++;
					}
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Ketu").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Ketu").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Venus").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Venus").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Sun").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Sun").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Moon").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Moon").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Mars").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Mars").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Rahu").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Rahu").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Jupiter").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Jupiter").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Saturn").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Saturn").getDegree());
					count++;
					
					pstmt.setString(count, signSortName.get(astroBean.getPlanetDetailHashTable().get("Mercury").getSignName()));
					count++;
					pstmt.setString(count, astroBean.getPlanetDetailHashTable().get("Mercury").getDegree());
					
					logger.info("hreeeeeeeeeeeeeeeeee final count ["+count+"]");
					
					result = pstmt.executeUpdate();
                    pstmt.close(); 
						
		}catch (Exception e) {
                        logger.error("catch block of addkundliData " + e.toString());
                        e.printStackTrace();
                        return 0;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of addkundliData" + e.toString());
                        }
                } 
                return result;
			

	}
	
	public int addkundliDetails(GenerateKundli generateKundli,String kundliData)
	{
		
		logger.info("inside addkundliDetails [" + generateKundli + "]");
		int result=-1;
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "insert into  generate_kundli(name,state,city,country,dob,tob,remark,occupation,account,contact,transit_kundli,transit_location,transit_country,transit_state,transit_city,transit_dob,transit_tob,transit_latitude,transit_longitude,latitude,longitude,city_code,transit_city_code,kundli_data,create_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, generateKundli.getName().trim());
			pstmt.setString(2, generateKundli.getState());
			pstmt.setString(3, generateKundli.getCity());
			pstmt.setString(4, generateKundli.getCountry());
			pstmt.setString(5, generateKundli.getDob());
			pstmt.setString(6, generateKundli.getTob());
			pstmt.setString(7, generateKundli.getRemarks());
			pstmt.setString(8, generateKundli.getOccupation());
			pstmt.setString(9, generateKundli.getAccountId());
			pstmt.setString(10, generateKundli.getContactId());
			pstmt.setInt(11, generateKundli.getIsTransitKundli());
			pstmt.setInt(12, generateKundli.getTransitLocation());
			pstmt.setString(13, generateKundli.getTransitCountry());
			pstmt.setString(14, generateKundli.getTransitState());
			pstmt.setString(15, generateKundli.getTransitCity());
			pstmt.setString(16, generateKundli.getTransitDob());
			pstmt.setString(17, generateKundli.getTransitTob());
			pstmt.setString(18, generateKundli.getTransitLatitude());
			pstmt.setString(19, generateKundli.getTransitLongitude());
			pstmt.setString(20, generateKundli.getLatitude());
			pstmt.setString(21, generateKundli.getLongitude());
			pstmt.setString(22, generateKundli.getCityCode());
			pstmt.setString(23, generateKundli.getTransitCityCodes());
			pstmt.setString(24, kundliData);
            result = pstmt.executeUpdate();
                        pstmt.close();		
		}catch (Exception e) {
                        logger.error("catch block of addkundliData " + e.toString());
                        return 0;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of addkundliData" + e.toString());
                        }
                } 
                return result;

	}
	

	public String getUserKundli(int uId) {
                String result="";
                logger.info("inside getUserKundli according to userId[" + uId + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select kundli_data_json from user_profile where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, uId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                result=rs.getString("kundli_data_json");
                        }
                } catch (Exception e) {
                        logger.error("catch block of getting user kundli  " + e.toString());
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
                                logger.error("finally block of getting user kundli  "
                                                + e.toString());
                        }
                }
                return result;
        }

	
	@Override
    public Event getGenerateEventLog(int eventId) {
                Event event = new Event();
                try {
                        logger.info("Inside reading old event log by eventId "
                                        + eventId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from event_data where event_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, eventId);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                event.setUserId(rs.getInt("USER_ID"));
                                event.setEventId(rs.getInt("event_id"));
                                event.setEventName(rs.getString("event_name"));
                                String[] dob = rs.getString("event_date").split("-");

                                event.setEventDate(dob[2]+"-"+dob[1]+"-"+dob[0]);
                                logger.info("eventDate:"+event.getEventDate());
                                String input=rs.getString("event_time");
                                DateFormat inputFormat = new SimpleDateFormat("HH_mm_ss");
                                DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
                                event.setEventTime(outputFormat.format(inputFormat.parse(input)));

                                    String[] sc=rs.getString("scope").split(";");
                                        for(String str:sc)
                                        logger.info("str:"+str);
                                        String[] sc1=sc[0].split("=");
                                         String[] sc2=sc[1].split("=");
                                        String[] sc3=sc[2].split("=");
                                        if(sc3[0].equals("Sun"))
                                        {
                                        String[] sc16=sc3[1].split("/");
                                        event.setSunRL(sc16[0]);
                                        event.setSunNL(sc16[1]);
                                        event.setSunSL(sc16[2]);

                                        }
					if(sc3[0].equals("Moon"))
                                        {
                                          String[] sc17=sc3[1].split("/");
                                        event.setMoonRL(sc17[0]);
                                        event.setMoonNL(sc17[1]);
                                        event.setMoonSL(sc17[2]);

                                        }

                                          if(sc3[0].equals("NL"))
                                        {
                                        event.setNL(sc3[1]);
                                        }

                                        String sc4[]=sc[3].split("=");


                                         if(sc4[0].equals("Sun"))
                                        {

                                        String[] sc16=sc4[1].split("/");
                                        event.setSunRL(sc16[0]);
                                        event.setSunNL(sc16[1]);
                                        event.setSunSL(sc16[2]);

                                        }
                                        if(sc4[0].equals("Moon"))
                                        {
                                          String[] sc17=sc4[1].split("/");
                                        event.setMoonRL(sc17[0]);
                                        event.setMoonNL(sc17[1]);
                                        event.setMoonSL(sc17[2]);

                                        }

                                          if(sc4[0].equals("NL"))
                                        {
                                        event.setNL(sc4[1]);
                                        }
					String[] sc5=sc[4].split("=");

                                      if(sc5[0].equals("Sun"))
                                        {

                                        String[] sc16=sc5[1].split("/");
                                        event.setSunRL(sc16[0]);
                                        event.setSunNL(sc16[1]);
                                        event.setSunSL(sc16[2]);

                                        }
                                        if(sc5[0].equals("Moon"))
                                        {
                                          String[] sc17=sc5[1].split("/");
                                        event.setMoonRL(sc17[0]);
                                        event.setMoonNL(sc17[1]);
                                        event.setMoonSL(sc17[2]);

                                        }

                                          if(sc5[0].equals("NL"))
                                        {
                                        event.setNL(sc5[1]);
                                        }



                                        String[] sc6=sc[5].split("=");
                                        String[] sc7=sc[6].split("=");

                                        String[] sc8=sc[7].split("=");
                                        String[] sc9=sc[8].split("=");
                                        logger.info("lenght:"+sc.length);
                                        if (sc.length>9){
                                        String sc10[]=sc[9].split("=");
                                        String sc11[]=sc[10].split("=");
                                        String sc12[]=sc[11].split("=");
                                        String sc16[]=sc[12].split("=");
                                        String sc17[]=sc[13].split("=");
                                        String scPDLEnd[]=sc[14].split("=");
					String sc18[]=sc[15].split("=");
                                       // String sc21[]=sc18[1].split("/");
                                      //  event.setMdlNL(sc21[0]);
                                      //  event.setMdlSL(sc21[1]);

                                        String sc19[]=sc[16].split("=");
                                      //  String sc22[]=sc19[1].split("/");
                                       // event.setAdlNL(sc22[0]);
                                       // event.setAdlSL(sc22[1]);


                                        String sc20[]=sc[17].split("=");
                                      //  String sc23[]=sc20[1].split("/");
                                       // event.setPdlNL(sc23[0]);
                                       // event.setPdlSL(sc23[1]);
                                        event.setAdlEndTime(sc17[1]);

                                        event.setMdlEndTime(sc16[1]);
					event.setPdlEndTime(scPDLEnd[1]);
//-=========================================>>
					

					if(sc18[0].equals("mdlNLSL"))
                                        {

                                        String sc21[]=sc18[1].split("/");
                                        event.setMdlNL(sc21[0]);
                                        event.setMdlSL(sc21[1]);
                                        }
                                         if(sc18[0].equals("adlNLSL"))
                                        {
                                        String sc21[]=sc18[1].split("/");
                                        event.setAdlNL(sc21[0]);
                                        event.setAdlSL(sc21[1]);
                                        }
                                         if(sc18[0].equals("pdlNLSL"))
                                        {
                                        String sc21[]=sc18[1].split("/");
                                        event.setPdlNL(sc21[0]);
                                        event.setPdlSL(sc21[1]);
                                        }

                                         if(sc19[0].equals("pdlNLSL"))
                                        {

                                        String sc22[]=sc19[1].split("/");
                                        event.setPdlNL(sc22[0]);
                                        event.setPdlSL(sc22[1]);
                                        }

                                         if(sc19[0].equals("mdlNLSL"))
                                        {

                                        String sc22[]=sc19[1].split("/");
                                        event.setMdlNL(sc22[0]);
                                        event.setMdlSL(sc22[1]);
                                        }
					if(sc19[0].equals("adlNLSL"))
                                        {

                                        String sc22[]=sc19[1].split("/");
                                        event.setAdlNL(sc22[0]);
                                        event.setAdlSL(sc22[1]);
                                        }

                                         if(sc20[0].equals("pdlNLSL"))
                                        {

                                        String sc23[]=sc20[1].split("/");
                                        event.setPdlNL(sc23[0]);
                                        event.setPdlSL(sc23[1]);
                                        }
                                         if(sc20[0].equals("adlNLSL"))
                                        {

                                        String sc23[]=sc20[1].split("/");
                                        event.setAdlNL(sc23[0]);
                                        event.setAdlSL(sc23[1]);
                                        }
                                         if(sc20[0].equals("mdlNLSL"))
                                        {

                                        String sc23[]=sc20[1].split("/");
                                        event.setMdlNL(sc23[0]);
                                        event.setMdlSL(sc23[1]);
                                        }
                                        logger.info("sc[15]>>"+" "+sc[15]+" "+sc[16]+" "+sc[17]+"***** "+ event.getMdlNL()+
                                        event.getMdlSL()+event.getAdlNL()+
                                        event.getAdlSL()+event.getPdlNL()+
                                        event.getPdlSL());

//==========================================================>>


					if(sc10[0].equals("mdlDegSign"))
                                        {

                                                String sc13[]=sc10[1].split("/");
                                                event.setMdlDegree(sc13[0]);
                                                event.setMdlRashi(sc13[1]);
                                                logger.info("mdlDegree:"+event.getMdlDegree()+"mdlRashi:"+event.getMdlRashi());
                                        }
                                        if(sc10[0].equals("adlDegSign"))
                                        {
                                                String sc13[]=sc10[1].split("/");
                                                event.setAdlDegree(sc13[0]);
                                                event.setAdlRashi(sc13[1]);
                                                logger.info("adlDegree:"+event.getAdlDegree()+"adlRashi:"+event.getAdlRashi());

                                        }
                                       if(sc10[0].equals("pdlDegSign"))
                                        {
                                                String sc13[]=sc10[1].split("/");
                                                event.setPdlDegree(sc13[0]);
                                                event.setPdlRashi(sc13[1]);
                                                logger.info("pdlDegree:"+event.getPdlDegree()+"pdlRashi:"+event.getPdlRashi());

                                        }

                                         if(sc11[0].equals("mdlDegSign"))
                                        {
                                                String sc14[]=sc11[1].split("/");
                                                event.setMdlDegree(sc14[0]);
                                                event.setMdlRashi(sc14[1]);
                                                logger.info("mdlDegree:"+event.getMdlDegree()+"mdlRashi:"+event.getMdlRashi());

                                        }
                                        if(sc11[0].equals("adlDegSign"))
                                        {
                                                String sc14[]=sc11[1].split("/");
                                                event.setAdlDegree(sc14[0]);
                                                event.setAdlRashi(sc14[1]);
                                                logger.info("adlDegree:"+event.getAdlDegree()+"adlRashi:"+event.getAdlRashi());

                                        }
					if(sc11[0].equals("pdlDegSign"))
                                        {
                                                String sc14[]=sc11[1].split("/");
                                                event.setPdlDegree(sc14[0]);
                                                event.setPdlRashi(sc14[1]);
                                                logger.info("pdlDegree:"+event.getPdlDegree()+"pdlRashi:"+event.getPdlRashi());

                                        }

                                         if(sc12[0].equals("mdlDegSign"))
                                        {
                                                String sc15[]=sc12[1].split("/");
                                                event.setMdlDegree(sc15[0]);
                                                event.setMdlRashi(sc15[1]);
                                                logger.info("mdlDegree:"+event.getMdlDegree()+"mdlRashi:"+event.getMdlRashi());

                                        }
                                        if(sc12[0].equals("adlDegSign"))
                                        {
                                                String sc15[]=sc12[1].split("/");
                                                event.setAdlDegree(sc15[0]);
                                                event.setAdlRashi(sc15[1]);
                                                logger.info("adlDegree:"+event.getAdlDegree()+"adlRashi:"+event.getAdlRashi());

                                        }
                                       if(sc12[0].equals("pdlDegSign"))
                                        {
                                                String sc15[]=sc12[1].split("/");
                                               event.setPdlDegree(sc15[0]);
                                                event.setPdlRashi(sc15[1]);
                                                logger.info("pdlDegree:"+event.getPdlDegree()+"pdlRashi:"+event.getPdlRashi());

                                        }


                                        }
					else{

                                                event.setMdlDegree("NA");
                                                event.setMdlRashi("NA");
                                                event.setMdlEndTime("NA");

                                                event.setAdlDegree("NA");
                                                event.setAdlRashi("NA");
                                                event.setAdlEndTime("NA");

                                                event.setPdlDegree("NA");
                                                event.setPdlRashi("NA");
                                                event.setPdlEndTime("NA");


                                        }

                                        event.setSign(sc1[1]);
                                        event.setLord(sc2[1]);

                                        event.setDayLord(sc6[1]);

                                        if(!(sc7.length==1))
                                        event.setMdl(sc7[sc7.length-1]);
                                        else
                                        event.setMdl("NA");
                                         if(!(sc8.length==1))
                                        event.setAdl(sc8[sc8.length-1]);
                                        else
                                        event.setAdl("NA");
                                        if(!(sc9.length==1))
                                        event.setPdl(sc9[sc9.length-1]);
                                     	else
                                        event.setPdl("NA");
					                    event.setCity(rs.getString("CITY"));
                                String country1=getCountry(rs.getString("COUNTRY"));
                                logger.info("country1:"+country1);
                                event.setCountry(country1);
                                logger.info("event.getCountry"+event.getCountry());
                        String state=getState(rs.getString("STATE"),rs.getString("COUNTRY"));
                               event.setState(state);

                                logger.info("inside if");
                                event.setLatitude(rs.getString("latitude"));
                                event.setLongitude(rs.getString("longitude"));
                                event.setCityCode(rs.getString("CITY_CODE"));
                                event.setDescription(rs.getString("description"));

                        }
                        return event;
                  } catch (Exception e) {
                        logger.error("catch block of reading old event log "
                                        + e.toString());
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
                                logger.error("finally block of reading  old event log "
                                                + e.toString());
                        }
                }
        }
	
	
	public GenerateKundli getGenerateLogBook(int userId) {
                GenerateKundli generateKundli = new GenerateKundli();
                try {
                        logger.info("Inside reading old log by userId"
                                        + userId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from USER_PROFILE where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, userId);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                generateKundli.setUserId(rs.getInt("USER_ID"));
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


                        }
                        return generateKundli;
                } catch (Exception e) {
                        logger.error("catch block of reading old kundli log "
                                        + e.toString());
                        return null;
                }finally {
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
                                logger.error("finally block of reading old kundli log "
                                                + e.toString());
                        }
                }
        }


	public int saveEventDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, saveEventDetail [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "insert into event_data(user_id,event_name,description,event_date,event_time,longitude,latitude,city_code,event_data_json,event_data_xml,scope,city,state,country) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(2, generateKundli.getName().trim());
                        pstmt.setString(4, generateKundli.getDob());
                        pstmt.setString(5, generateKundli.getTob());
                        pstmt.setString(6, generateKundli.getLongitude());
                        pstmt.setString(7, generateKundli.getLatitude());
                        logger.info("getScope:"+generateKundli.getScope());
                        pstmt.setString(11,generateKundli.getScope());
                        pstmt.setString(8, generateKundli.getCityCode());
                        pstmt.setInt(1, generateKundli.getUserId());
                        pstmt.setString(3, generateKundli.getRemarks());
                        pstmt.setString(9, json);
                        pstmt.setString(10,xml);
                        pstmt.setString(12, generateKundli.getCity());
                        pstmt.setString(13, generateKundli.getState());
                        pstmt.setString(14, generateKundli.getCountry());
                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of saveEventDetails " + e.toString());
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
                                logger.error("finally block of saveEventDetails" + e.toString());
                        }
                }
                return 1;
	}



	public List<Event> getEventLogs(int userId) {
                List<Event> eventLogsLst = new ArrayList<Event>();
                try {
                        logger.info("Inside getting event logs list:"+userId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from event_data where USER_ID=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, userId);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                Event event= new Event();
                                event.setEventId(rs.getInt("event_id"));

                                event.setEventName(rs.getString("event_name"));
                                String[] dob = rs.getString("event_date").split("-");

                                event.setEventDate(dob[2]+"/"+dob[1]+"/"+dob[0]);
                        logger.info("eventDate:"+event.getEventDate());
                String input=rs.getString("event_time");

                DateFormat inputFormat = new SimpleDateFormat("HH_mm_ss");
                DateFormat outputFormat = new SimpleDateFormat("KK:mm a");

                event.setEventTime(outputFormat.format(inputFormat.parse(input)));
                    logger.info("eventDate:"+event.getEventDate()+"eventTime:"+event.getEventTime());

                                logger.info("SCOPE: -------->>>>>>   ["+rs.getString("scope")+"].");
                                String[] sc=rs.getString("scope").split(";");
                                        for(String str:sc)
                                        logger.info("str:"+str);
                                        String[] sc1=sc[0].split("=");
                                         String[] sc2=sc[1].split("=");
                                        String[] sc3=sc[2].split("=");
                                        String[] sc4=sc[3].split("=");
                                        String[] sc5=sc[4].split("=");

                                        if(sc3[0].equals("Sun")){
                                             event.setSun(sc3[1]);
                                        }
                                          if(sc3[0].equals("Moon")){
                                             event.setMoon(sc3[1]);
                                        }
					if(sc3[0].equals("NL")){
                                             event.setNL(sc3[1]);
                                        }
                                          if(sc4[0].equals("Sun")){
                                             event.setSun(sc4[1]);
                                        }
                                          if(sc4[0].equals("Moon")){
                                             event.setMoon(sc4[1]);
                                        }
                                          if(sc4[0].equals("NL")){
                                             event.setNL(sc4[1]);
                                        }
                                          if(sc5[0].equals("Sun")){
                                             event.setSun(sc5[1]);
                                        }
                                          if(sc5[0].equals("Moon")){
                                             event.setMoon(sc5[1]);
                                        }
                                          if(sc5[0].equals("NL")){
                                             event.setNL(sc5[1]);
                                        }


                                        String[] sc6=sc[5].split("=");

                                        event.setSign(sc1[1]);
                                        event.setLord(sc2[1]);
                                        if(sc6.length < 2){
                                                event.setDayLord("");
                                                }
                                        else{
                                                event.setDayLord(sc6[1]);
                                                }
                                eventLogsLst.add(event);
                        }
                        logger.info("eventListfrom:"+eventLogsLst);
                        return eventLogsLst;
                } catch (Exception e) {
                        logger.error("catch block of reading user old kundli logs list "
                                        + e.toString());
                        return null;
                }
		finally {
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
                                logger.error("finally block of reading user old kundli logs list "
                                                + e.toString());
                        }
                }
        }





public int saveDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, saveDetail [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "insert into user_profile(user_id,name,state,city,country,dob,tob,longitude,latitude,city_code,kundli_data_json,kundli_data_xml) values(?,?,?,?,?,?,?,?,?,?,?,?)";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(2, generateKundli.getName().trim());
                        pstmt.setString(6, generateKundli.getDob());
                        pstmt.setString(7, generateKundli.getTob());
                        pstmt.setString(8, generateKundli.getLongitude());
                        pstmt.setString(9, generateKundli.getLatitude());
                        pstmt.setString(4, generateKundli.getCity());
                        pstmt.setString(10, generateKundli.getCityCode());
                        pstmt.setInt(1, generateKundli.getUserId());
                        pstmt.setString(5, generateKundli.getCountry());
                        pstmt.setString(3, generateKundli.getState());
                        pstmt.setString(11, json);
                        pstmt.setString(12,xml);
                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of saveDetail " + e.toString());
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
                                logger.error("finally block of saveDetail" + e.toString());
                        }
                }
                return 1;
}

		public int updateEventDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, updateEventDetails [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "update event_data set name=?,state=?,city=?,country=?,dob=?,tob=? where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, generateKundli.getName().trim());
                        pstmt.setString(5, generateKundli.getDob());
                        pstmt.setString(6, generateKundli.getTob());


                        pstmt.setString(3, generateKundli.getCity());

                        pstmt.setInt(7, generateKundli.getUserId());
                        pstmt.setString(4, generateKundli.getCountry());
                        pstmt.setString(2, generateKundli.getState());


                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of updateEventDetail " + e.toString());
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
                                logger.error("finally block of updateEventDetail" + e.toString());
                        }
                }
return 1;
}


public int updateDetail(GenerateKundli generateKundli,String json,String xml) {
                try {
                        logger.info("inside, update Details [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "update user_profile set name=?,state=?,city=?,country=?,dob=?,tob=?,kundli_data_json=?,kundli_data_xml=? where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, generateKundli.getName().trim());
                        pstmt.setString(2, generateKundli.getState());
                        pstmt.setString(3, generateKundli.getCity());
                        pstmt.setString(4, generateKundli.getCountry());
                        pstmt.setString(5, generateKundli.getDob());
                        pstmt.setString(6, generateKundli.getTob());
                        pstmt.setString(7, json);
                        pstmt.setString(8, xml);
                        pstmt.setInt(9, generateKundli.getUserId());


                        int result = pstmt.executeUpdate();
                        pstmt.close();
                            } catch (Exception e) {
                        logger.error("catch block of updateDetail " + e.toString());
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
                                logger.error("finally block of updateDetail" + e.toString());
                        }
                }
return 1;
}

public String getCountry(String countryCode)
        {
                ResultSet rs1=null;
                String country="";
                logger.info("inside getting country  from DB where country code "
                                + countryCode);
                                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from country where code=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, countryCode);
                         rs1 = pstmt.executeQuery();
                        if(rs1.next()) {
                                 country=rs1.getString("name");
                        }
                        } catch (Exception e) {
                        logger.error("catch block of getting country  " + e.toString());
                        } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs1 != null)
                                        rs1.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of getting state"
                                                + e.toString());
                                }
                }
                return country;
        }

	public String getState(String stateCode,String counCode) {
                String state="";
 ResultSet rs1=null;
                logger.info("inside getting state  from DB where state code "
                                + stateCode);
                                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from state where code=? and coun_code=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, stateCode);

                        pstmt.setString(2, counCode);
                         rs1 = pstmt.executeQuery();
                        if(rs1.next()) {
                                state=rs1.getString("name");
                        }
                        } catch (Exception e) {
                        logger.error("catch block of getting state  " + e.toString());
                        } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs1 != null)
                                        rs1.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of getting state"
                                                + e.toString());
                                }
                }
                return state;
        }


	public boolean isExist(int userId)
        {
                boolean result=false;
                logger.info("inside isExist Info by userID[" + userId + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from user_profile where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, userId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                result=true;
                        }
                } catch (Exception e) {
                        logger.error("catch block of getting user info  " + e.toString());

                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of getting user info  "
                                                + e.toString());
                        }
                }
                return result;
        }


	public boolean isUserExist(int userId) {
                logger.info("inside is user exist user[" + userId + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from user_profile where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, userId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                return true;
                        }
                } catch (Exception e) {
                        logger.error("catch block of Authenticate  " + e.toString());
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
                                logger.error("finally block of Authenticate  " + e.toString());
                        }
                }
                return false;
        }

	
	 public int deleteEventLog(int eventId) {
                try {
                        logger.info("inside,delete event log by requestId " + eventId);
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "delete from EVENT_DATA  where EVENT_ID=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, eventId);
                        int result = pstmt.executeUpdate();
                        if (result > 0) {
                                return 1;
                        }
                } catch (Exception e) {
                        logger.error("catch block of delete event log " + e.toString());
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
                                logger.error("finally block of event kundli log "
                                                + e.toString());
                        }
                }
                return 0;
        }

	public String getKundliDetail(int uId) {
                String result="";
                logger.info("inside getKundliDetail  Info according to userId[" + uId + "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from kundli_metadata where user_id=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, uId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                result=rs.getString("kundli_data");
                                return result;
                        }
                        return null;
                } catch (Exception e) {
                        logger.error("catch block of getting user info  " + e.toString());
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
                                logger.error("finally block of getting user info  "
                                                + e.toString());
                        }
                }
        }

	public int userClickedLink(int id,String action,String remark,String detail)
        {
                logger.info("Inside userClickedLink method of UserManager dao class. where userId:["+id+"].");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "insert into user_log(user_id,action,updated_date,remark,detail) values(?,?,now(),?,?)";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, id);
                        pstmt.setString(2,action);
                        pstmt.setString(3,remark);
                        pstmt.setString(4, detail);
                        int result = pstmt.executeUpdate();
                        if (result > 0) {
                                return 1;
                        }
                } catch (Exception e) {
                        logger.error("Inside catch block of userClickedLink method of UserManager dao class. Exception : " + e.toString());
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
                                                "Inside finally block of userClickedLink method of UserManager dao class. Exception : " + e.toString());
                        }
                }
                return 0;

        }

	public void writeLogToFile(int id,String action,String remark,String detail)
        {
                logger.info("Inside writeLogToFile method of UserManager dao class. where userId:["+id+"].");



                BufferedWriter bw = null;
                FileWriter fw = null;

                try {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH_mm_ss");
                        Date date1 = new Date();
                        String dateTime=dateFormat.format(date1);
                        String fileDate=dateTime.split(" ")[0];
			            String path=properties.get("KUNDLI_LOG_FILE_PATH");
                        String FILENAME= path+"/"+fileDate+".txt";
                        String data = "\t"+id+"\t"+action+"\t"+dateTime+"\t"+remark+"\t"+detail+"\n";
                        File file = new File(FILENAME);

                        if (!file.exists()) {

                                logger.info("entring if");
                                file.createNewFile();
                                fw = new FileWriter(file.getAbsoluteFile(), true);
                                bw = new BufferedWriter(fw);
                                bw.write(String.format("%-10s %-10s %-25s %-60s %-40s%n","USER ID", "ACTION","UPDATED DATE","REMARK","DETAIL\n"));
                                bw.write(String.format("%-10s %-10s %-25s %-60s %-40s%n",id,action,dateTime,remark,detail));

                                logger.info("exit if");
                        }

                        else {
                                fw = new FileWriter(file.getAbsoluteFile(), true);
                                bw = new BufferedWriter(fw);

                                bw.write(String.format("%-10s %-10s %-25s %-60s %-40s%n",id,action,dateTime,remark,detail));
                                logger.info("exit try");
                        }

                }catch (IOException e) {
                        logger.error("Inside catch block of writeLogToFile method of UserManager dao class. Exception : " + e.toString());

                        e.printStackTrace();

                } finally {

                        try {

                                if (bw != null)
                                        bw.close();

                                if (fw != null)
                                        fw.close();

                        } catch (IOException ex) {
                                logger.error(
                                                "Inside finally block of writeLogToFile method of UserManager dao class. Exception : " + ex.toString());
                                ex.printStackTrace();

                        }
                }

        }
		
	
	
	public int checkSubscription(UserLinkDescription userDetail){
                logger.info("inside checkSubscription   EMAIL ID[" + userDetail.getUserNme()+ "]");
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from adminlogin where LOWER(email)=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, userDetail.getUserNme().toLowerCase());
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                return 1;
                        }
                        return 0;
                } catch (Exception e) {
                        logger.error("catch block of checkSubscription  " + e.toString());
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
                                logger.error("finally block of checkSubscription  " + e.toString());

                        }
                }

        }

	public int checkSubscriptionForMultipleRoles(UserLinkDescription userDetail) {
		logger.info("inside checkSubscription   EMAIL ID[" + userDetail.getUserNme() + "]");
		try {
			con = TSSJavaUtil.instance().getconnection();
			String query = "select * from adminlogin where LOWER(email)=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, userDetail.getUserNme().toLowerCase());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDetail.setUserId(rs.getInt("USER_ID"));
				userDetail.setRoleId(rs.getInt("ROLE_ID"));
				userDetail.setRoleId2(rs.getInt("role_id_2"));
				userDetail.setCcCode(rs.getString("cc_code"));
				return 1;
			}
			return 0;
		} catch (Exception e) {
			logger.error("catch block of checkSubscription  " + e.toString());
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
				logger.error("finally block of checkSubscription  " + e.toString());

			}
		}

	}
	
	public int saveCityDetails(GenerateKundli generateKundli,String country,String state) {
                try {
                        logger.info("inside, saveCityDetails [" + generateKundli + "]");
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "insert into  kundli_citylist(country,state,city,longitude,latitude,country_code,city_code) values(?,?,?,?,?,?,?)";
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, country);
                        pstmt.setString(2, state);
                        pstmt.setString(3, generateKundli.getCity());
                        pstmt.setString(4, generateKundli.getLongitude());
                        pstmt.setString(5, generateKundli.getLatitude());
			pstmt.setString(6, generateKundli.getCountry());
			pstmt.setString(7, generateKundli.getCityCode());
                        int result = pstmt.executeUpdate();

                        if (result > 0) {
                                return 1;
                        }
                } catch (Exception e) {
                        logger.error("catch block of saveCityDetails " + e.toString());
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
                                logger.error("finally block of saveCityDetails" + e.toString());
                        }
                }
                return 0;
        }


	public boolean isCityInfoExist(String state,String city) {
                logger.info("inside  isCityInfoExist  method state >>>"+state+"  city>>>>"+city);
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from kundli_citylist where state=? and city=?" ;
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, state);
                        pstmt.setString(2, city);

                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                return true;
                        }
                } catch (Exception e) {
                        logger.error("catch block of isCityInfoExist  "
                                        + e.toString());
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
                                logger.error("finally block of isCityInfoExist "
                                                + e.toString());
                        }
                }
                return false;
        }
	
	public String getCityList(String country,String state,String city) {
                logger.info("inside  getCityList  method state >>>"+state+"  city>>>>"+city);
		String cityList="";
                try {
                        con = TSSJavaUtil.instance().getconnection();
                        String query = "select * from kundli_citylist where state=? and country=? and lower(city) like '%"+city.toLowerCase()+"%'" ;
			logger.info("QUERY::::  "+query);
                        pstmt = con.prepareStatement(query);
                        pstmt.setString(1, state);
                        pstmt.setString(2, country);

                        rs = pstmt.executeQuery();
                        if (rs.next()) {
				cityList=cityList+rs.getString("city_code")+","+rs.getString("city")+","+rs.getString("state")+","+rs.getString("longitude")+","+rs.getString("latitude")+";";
                        }
			logger.info("cityList>> "+cityList);
                } catch (Exception e) {
                        logger.error("catch block of getCityList  "
                                        + e.toString());
                        return cityList;
                } finally {
                        try {
                                if (pstmt != null)
                                        pstmt.close();
                                if (rs != null)
                                        rs.close();
                                if (con != null)
                                        TSSJavaUtil.instance().freeConnection(con);
                        } catch (Exception e) {
                                logger.error("finally block of getCityList "
                                                + e.toString());
                        }
                }
                return cityList;
        }

	/**
	 * method created by Gaurav Sharma  27th Aug 2020 11:36 pm 
	 * updating ROLE_ID_2 and ROLE_ID */
	@Override
	public int updateUserRole(UserLinkDescription userDetail) {
		try
		{
			logger.info("inside,modify user info"+userDetail);
			con = TSSJavaUtil.instance().getconnection();
			
			//String query = "update adminlogin set "+ "NAME=?,PASSWORD=?,EMAIL=?,MOBILE_NO=?,ROLE_ID=?,kundli_prefix=?"+ "where USER_ID=?";
			String query = "update adminlogin set "+ "ROLE_ID=?,ROLE_ID_2=?"+ " where USER_ID=?";
			logger.info("query : "+"update adminlogin set "+ "ROLE_ID="+userDetail.getRoleId()+",ROLE_ID_2="+userDetail.getRoleId2()+""+ " where USER_ID="+userDetail.getUserId()+"");
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userDetail.getRoleId());
			pstmt.setInt(2, userDetail.getRoleId2());
			pstmt.setInt(3, userDetail.getUserId());
			int result = pstmt.executeUpdate();
			logger.info("user modify info result " + result);
			if (result > 0)
			{
				return 1;
			}
		} 
		catch (Exception e)
		{
			logger.error("catch block of modify user info " + e.toString());
			return 0;
		}
		finally 
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (con != null)
					TSSJavaUtil.instance().freeConnection(con);
			} 
			catch (Exception e)
			{
				logger.error("finally block of modify user info "
						+ e.toString());
			}
		}
		return 0;
	}	


}

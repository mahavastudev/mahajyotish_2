package com.telemune.action;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.model.HttpLinks;
import com.telemune.model.RoleInfo;
import com.telemune.model.UserInfo;
import com.telemune.util.Constants;

public class RoleMgtAction extends ActionSupport implements SessionAware {
	
	private static Logger logger = Logger.getLogger(RoleMgtAction.class);
	private RoleInfo roleInfo;
	private UserProfileDao userDao = null;
	private List<RoleInfo> rolelist;
	private List<UserInfo> userLst;
	private List<HttpLinks> httpLinKLst;
	private String accessLink;
	private String message;
	private Map map;
	private Hashtable<String, String> properties = null;
        private String proPath = "";
        public RoleMgtAction() 
        {
                logger.info("Inside constructor of RoleMgtAction action class.");
                proPath = Constants.PROPERTIES_PATH;
                proPath = proPath + "kundliHttpserverNew.properties";
                //proPath = proPath + "kundliHttpserverTest.properties";
                logger.info("property file:"+proPath);
                properties = ReadPropertyFile.readPropery(proPath);
        }
	
	
	public String roleMgt() throws Exception
	{
		try
		{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";
		   if ( sess == null || sess.isEmpty()) 
		    {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else
			{
				userDao = new UserProfileDaoImpl();
                int userId=(Integer) session.getAttribute("userName");
                String action=properties.get("CLICK");
                String remark="User Clicked on Role Management";
                String detail = request.getRemoteAddr();
                userDao.userClickedLink(userId,action,remark,detail);
                userDao.writeLogToFile(userId,action,remark,detail);
				return "SUCCESS";
			}
		}
		catch (Exception e) 
		{
            e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
	}
	
	public String execute() throws Exception
	{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";

		if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				userDao = new UserProfileDaoImpl();
				httpLinKLst = userDao.getHttpLinks();
				logger.info("read httpLink list size "+httpLinKLst.size());
				return "SUCCESS";
			}
		}
		catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally
		{
			if(userDao != null){
				userDao=null;
			}	
		}

	}
	
	public String addRoleExecute() throws Exception
	{
		try
		{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";

		  if ( sess == null || sess.isEmpty()) 
		    {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
		  else
			{
				userDao=new UserProfileDaoImpl();
				if(userDao.isRoleExist(roleInfo.getRoleName())){
					String message = "role name already exist";
					map.put("msg", message);
					logger.info("role Name "+roleInfo.getRoleName()+" already exist");
					return "SUCCESS1";
			}
		  else
		  {
            int userId=(Integer) session.getAttribute("userName");
            String action=properties.get("CLICK");
            String remark="Created a new Role with Role Name = "+roleInfo.getRoleName();
            String detail = request.getRemoteAddr();
            userDao.userClickedLink(userId,action,remark,detail);
            userDao.writeLogToFile(userId,action,remark,detail);
			int roleId = userDao.getMaxRoleId()+1;
			roleInfo.setRoleId(roleId);
			logger.info("access link id "+accessLink);
			int retval=userDao.addUserRoles(roleInfo);
			if(retval>0)
			 {
				userDao.addUserAccessLink(roleId, accessLink);
				String message = "role has been successfully added";
				map.put("msg", message);
				logger.info("role has been successfully added");
				return "SUCCESS";
			 }
			else
			{
				return "FAILURE";
			}	
		   }						
		}
	}
	catch (Exception e)
		{
            e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally
		{
			if(userDao != null)
			{
			 userDao=null;
			}	
		}

	}
	
	public String viewRole() throws Exception
	{
		try
		{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";

		   if ( sess == null || sess.isEmpty()) 
		    {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else
			{
				userDao=new UserProfileDaoImpl();
				rolelist = userDao.getRoleList();
				int userId=(Integer) session.getAttribute("userName");
                String action=properties.get("CLICK");
                String remark="View Role List";
                String detail = request.getRemoteAddr();
                userDao.userClickedLink(userId,action,remark,detail);
                userDao.writeLogToFile(userId,action,remark,detail);
				logger.info("Role List size "+rolelist.size());
				setMessage((String)session.getAttribute("msg"));
				session.setAttribute("msg",null);
				if(!rolelist.isEmpty())
				{
					return "SUCCESS";
				}
				else
				{
					return "FAILURE";
				}
			}
		}
		catch (Exception e)
		{
            e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally
		{
			if(userDao != null)
			{
				userDao=null;
			}	
		}

	}
	
	public String modifyRole() throws Exception{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";

		 if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				userDao=new UserProfileDaoImpl();
				roleInfo = userDao.getRole(roleInfo.getRoleId());
				if(roleInfo !=null)
				{
					httpLinKLst = userDao.getHttpLinks();
					logger.info("read httpLink list size "+httpLinKLst.size());
					return "SUCCESS";
				}
				else{
					return "FAILURE";
				}
			}
		}
		catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally
		{
			if(userDao != null){
				userDao=null;
			}	
		}
	}
	
	public String modifyRoleExecute()throws Exception{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";

		if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				userDao=new UserProfileDaoImpl();
				int retValue = userDao.modifyUserRole(roleInfo);
				if(retValue>0)
				{
					userDao.deleteUserAccessLink(roleInfo.getRoleId());
					userDao.addUserAccessLink(roleInfo.getRoleId(), accessLink);
					String message = "role info has been successfully modified";
					map.put("msg", message);
					logger.info("role info has been successfully modified");		
					int userId=(Integer) session.getAttribute("userName");
                    String action=properties.get("UPDATE");
                    String remark="Modify Role For Role ID = "+roleInfo.getRoleId();
                    String detail = request.getRemoteAddr();
                    userDao.userClickedLink(userId,action,remark,detail);
                    userDao.writeLogToFile(userId,action,remark,detail);
					return "SUCCESS";
				}
				else{
					return "FAILURE";
				}
			}
		}
		catch (Exception e) 
		{
            e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally
		{
			if(userDao != null)
			{
				userDao=null;
			}	
		}
	}
	
	public String deleteRole() throws Exception
	{
		try
		{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";

		if ( sess == null || sess.isEmpty()) 
		{
		  logger.info("SESSION EXPIRED !!!!!");			
		  return "ERROR";
		}
		else
		{
				userDao=new UserProfileDaoImpl();
				userLst = userDao.getUserList(String.valueOf(roleInfo.getRoleId()));
				if(userLst !=null)
				{
					if(userLst.isEmpty())
					{
						int retVal = userDao.deleteUserRole(roleInfo.getRoleId());
						if(retVal>0)
						{
							userDao.deleteUserAccessLink(roleInfo.getRoleId());
							String message = "Role has been successfully deleted.";
							map.put("msg", message);
							int userId=(Integer) session.getAttribute("userName");
                            String action=properties.get("DELETE");
                            String remark="Delete Role With Role ID = "+roleInfo.getRoleId() ;
                            String detail = request.getRemoteAddr();
                            userDao.userClickedLink(userId, action, remark, detail);
                            userDao.writeLogToFile(userId,action,remark,detail);
							return "SUCCESS";
						}
						else
						{
							return "FAILURE";
						}
					}
					message = "This Role could'nt be deleted.";
					map.put("msg", message);
					return "SUCCESS";
				}
				else{
					return "FAILURE";
				}
			}
		}
		catch (Exception e) 
		{
            e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally
		{
			if(userDao != null)
			{
				userDao=null;
			}	
		}
	}
	
	public String viewAccessLink()throws Exception{
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			String sess=(Integer)session.getAttribute("userName")+"";

		if ( sess == null || sess.isEmpty()) {
				logger.info("SESSION EXPIRED !!!!!");			
				return "ERROR";
			}
			else{
				userDao=new UserProfileDaoImpl();
				roleInfo = userDao.getRole(roleInfo.getRoleId());
				if(roleInfo !=null){

					int userId=(Integer) session.getAttribute("userName");
                    String action=properties.get("VIEW");
                    String remark="Viewed Access Link For Role ID = "+roleInfo.getRoleId()+" And Role Name = "+roleInfo.getRoleName();
                    String detail = request.getRemoteAddr();
					userDao.userClickedLink(userId, action, remark, detail);
                    userDao.writeLogToFile(userId,action,remark,detail);
					httpLinKLst = userDao.getHttpLinks();
					logger.info("read httpLink list size "+httpLinKLst.size());					
					return "SUCCESS";
				}
				else{
					return "FAILURE";
				}
			}
		}
		catch (Exception e) {
                         e.printStackTrace();
			logger.error(e.toString());
			return "ERROR";
		}
		finally
		{
			if(userDao != null){
				userDao=null;
			}	
		}
	}
	
	public RoleInfo getRoleInfo() {
		return roleInfo;
	}
	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}
	public UserProfileDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserProfileDao userDao) {
		this.userDao = userDao;
	}
	public List<RoleInfo> getRolelist() {
		return rolelist;
	}
	public void setRolelist(List<RoleInfo> rolelist) {
		this.rolelist = rolelist;
	}

	public List<HttpLinks> getHttpLinKLst() {
		return httpLinKLst;
	}

	public void setHttpLinKLst(List<HttpLinks> httpLinKLst) {
		this.httpLinKLst = httpLinKLst;
	}

	public String getAccessLink() {
		return accessLink;
	}

	public void setAccessLink(String accessLink) {
		this.accessLink = accessLink;
	}

	@Override
	public void setSession(Map session) {
		map = session;
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

package com.telemune.mobileAstro;

import java.util.List;
import org.apache.log4j.Logger;
import com.telemune.dbutilities.*;
import java.sql.ResultSet;


public class Adminlogin  {

	private static final Logger logger = Logger.getLogger(Adminlogin.class);
	// property constants

	Connection con=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;	
	
	public int Authenticate(String username,String password)
	{
		logger.info("inside Authenticate   username["+username+"] pass   ["+password+"]");
		try{
			con=TSSJavaUtil.instance().getconnection();
			String query = "select * from ADMINLOGIN where EMAIL=? and PASSWORD=?";
			pstmt = con.prepareStatement (query);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			rs = pstmt.executeQuery ();
			if(rs.next())
			{
				return rs.getInt("ROLE_ID");
			}
			return 0;
		}catch (Exception e) {
				logger.error("catch block of Authenticate  "+e.toString());
				return 0;
		}
		finally
		{
			try{
			if(pstmt!=null) pstmt.close();
			if(rs!=null) rs.close();
			if(con!=null)
				TSSJavaUtil.instance().freeConnection(con);	
			}
			catch(Exception e)
			{
				logger.error("finally block of Authenticate  "+e.toString());
			}
		}
		
	}
/*	
	public int changePassword(String name,String oldpassword,String newpassword)
	{
		logger.info("inside change password name = "+name+" oldpass = "+oldpassword+"newpass = "+newpassword);
		try
		{
			session=getSession("ORACLE");
			Query qry = session.createQuery("from Adminlogin where name=:nm and password=:pass ");
			System.out.println(oldpassword);
			qry.setParameter("nm",name);
			qry.setParameter("pass",oldpassword);
			List results=qry.list();
			
			if(results.size()==1)
			{
				System.out.println("update pass ");
				Query query = session.createQuery("update Adminlogin set password = :newpass where name = :nm and password=:pass");
				query.setParameter("pass", oldpassword);
				query.setParameter("newpass", newpassword);
				query.setParameter("nm",name);
				int r=query.executeUpdate();
				if(r==1)
					logger.info("Password is successfull updated");
				return 1;
			}
			else
			{
				return 0;
			}
		}
		catch (Exception e) {
			logger.error("Inside catch block of AdminloginDao   "+e.toString());
		return -1;
		}
		finally{
			
			if(session!=null)
			{
				try{session.flush();}catch(Exception e ){e.printStackTrace();logger.error("session flush exception");}
				try{session.close();}catch(Exception e ){e.printStackTrace();logger.error("session close exception");}
			}
		}
	}*/
}

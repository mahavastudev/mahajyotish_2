package com.telemune.mobileAstro;

import java.sql.*;
import java.io.*;
import java.util.*;

import org.apache.log4j.*;

import com.telemune.dbutilities.ConnectionPool;


public class TSSJavaUtil
{
	private static Logger logger=Logger.getLogger(TSSJavaUtil.class);
	private static TSSJavaUtil instance_ = null;
	private static Properties properties = null;
	private static Properties propFile = null;
	private static com.telemune.dbutilities.ConnectionPool conPool=null;
	private static long time=0;

	private static String propfilePath="";
	private static String currentPath="";

	public static synchronized TSSJavaUtil instance(String _currentPath)
	{

		long newtime = (new java.util.Date()).getTime();

		if (instance_ == null||((newtime-time)>1000*60*60))
		{
			
			logger.info("This is the  time || ["+time+"] >1000*60*60 Or when Chache made fist time ");

			System.out.println("---------------------------------> Getting current Path as  : "+_currentPath);
			currentPath=_currentPath;
			propfilePath=currentPath+"../../bin/";


			instance_ = new TSSJavaUtil();
			time=newtime;
		}
		logger.info("This is the  time || ["+time+"] when not reload ");
		return instance_;
	}


	public static synchronized TSSJavaUtil instance()
	{

		long newtime = (new java.util.Date()).getTime();

		if (instance_ == null||((newtime-time)>1000*60*60))
		{
			logger.info("This is the  time || ["+time+"] >1000*60*60 Or when Chache made fist time ");
			instance_ = new TSSJavaUtil();
			time=newtime;
		}
		logger.info("This is the  time || ["+time+"] when not reload ");
		return instance_;
	}
	// this is connection pool object
	public com.telemune.dbutilities.ConnectionPool getconnectionPool()
	{
		if(conPool==null)
		{
			conPool= new ConnectionPool();
			logger.info("This is new conPool object in TSSJavaUtil..." +conPool.hashCode());
			return conPool;
		}
		logger.info("This is old conPool object in TSSJavaUtil. " +conPool.hashCode());
		return conPool;
	}

	// this is the single object of connection

	public com.telemune.dbutilities.Connection getconnection()
	{
		com.telemune.dbutilities.Connection con=null;	
		con=getconnectionPool().getConnection();
		logger.info("This is the con object..|| "+con.hashCode());

		return con;
	}

	public void freeConnection(com.telemune.dbutilities.Connection con)
	{
		try {
		getconnectionPool().free(con);
		}
		catch(Exception e)
		{
			
		}
	}
	

}

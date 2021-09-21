package com.telemune.dbutilities;
// Copyright (c) Telemune Software Solutions Pvt. Ltd.
//File: DataSourceThread.java 
/**
 * @Author                            Kalpesh Balar
 * @Version                           $Revision: 1.1 $
 * Creation-Date                       Thu May 05 17:25:36 IST 2005
 * Classes
 *
 */
/*
 * Development History
 * ####################
 * Date                                 Author                        Description
 * ------------------------------------------------------------------------------
 * Thu May 05 17:25:36 IST 2005        Kalpesh Balar                       Created.
 *
 */

import java.sql.*;
import javax.sql.*;

public class DataSourceThread extends Thread 
{
	private DataSource ds = null;
	private java.sql.Connection con = null;
	private long accessTime = 0;

	public long getLastAccessTime()
	{
		return this.accessTime;
	}

	public DataSourceThread(DataSource ds)
	{
		this.ds = ds;
		this.accessTime = System.currentTimeMillis();
	}

	public java.sql.Connection getConnection()
	{
		return this.con;
	}

	public void setDataSource(DataSource ds)
	{
		this.ds = ds;	
	}

	public void run()
	{
		try 
		{
			con = ds.getConnection();
		//	logger.debug(con);
		} 
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
//			logger.warn(sqle.getMessage());
			return; 
		}
	}
}

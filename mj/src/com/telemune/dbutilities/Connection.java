package com.telemune.dbutilities;
// Copyright (c) Telemune Software Solutions Pvt. Ltd.
//File: Connection.java 
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

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Connection
{
	private java.sql.Connection con = null;
	ArrayList queryList = null;
	boolean replicate = false;

	public Connection (java.sql.Connection con, boolean rep)
	{
		this.con = con;
		this.replicate = rep;
	//	queryList = new ArrayList();
	}

	public boolean isReplicate()
	{
		return this.replicate;
	}

	public void close() throws SQLException
	{
		con.close();
	}

	public java.sql.Connection getConnection()
	{
		return con;
	}

	public void commit() throws SQLException
	{
		if (con.getAutoCommit())
		{
		}
		else if (queryList != null)
		{
			for (int i=0; i<queryList.size(); i++)
			{
				String query = (String) queryList.get(i);
				//replicateQuery(query);
			}
			queryList.clear();
		}
		con.commit();
	}

	public com.telemune.dbutilities.Statement createStatement() throws SQLException
	{
		java.sql.Statement stmt = con.createStatement();
		com.telemune.dbutilities.Statement tstmt = new com.telemune.dbutilities.Statement(this, stmt);
		return tstmt;
	}

	public boolean getAutoCommit() throws SQLException
	{
		return con.getAutoCommit();
	}

	public com.telemune.dbutilities.PreparedStatement prepareStatement(String sql) throws SQLException
	{
		java.sql.PreparedStatement pstmt = con.prepareStatement(sql);
		com.telemune.dbutilities.PreparedStatement tpstmt = new com.telemune.dbutilities.PreparedStatement(this, pstmt, sql);
		return tpstmt;
	}

	public com.telemune.dbutilities.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		java.sql.PreparedStatement pstmt = con.prepareStatement(sql, resultSetType, resultSetConcurrency);
		com.telemune.dbutilities.PreparedStatement tpstmt = new com.telemune.dbutilities.PreparedStatement(this, pstmt, sql);
		return tpstmt;
	}
	public com.telemune.dbutilities.CallableStatement prepareCall(String sql)  throws SQLException
	{
		java.sql.CallableStatement cstmt=con.prepareCall(sql);
		com.telemune.dbutilities.CallableStatement tcstmt=new com.telemune.dbutilities.CallableStatement(this,cstmt,sql);
		return tcstmt;
	} 
	public com.telemune.dbutilities.CallableStatement prepareCall(String sql,int resultSetType, int resultSetConcurrency)  throws SQLException
        {
                java.sql.CallableStatement cstmt=con.prepareCall(sql,resultSetType,resultSetConcurrency);
                com.telemune.dbutilities.CallableStatement tcstmt=new com.telemune.dbutilities.CallableStatement(this,cstmt,sql);
                return tcstmt;
        }

	
	public void rollback() throws SQLException
	{
		if (!con.getAutoCommit())
		{
			if (queryList != null) queryList.clear();
			con.rollback();
		}
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		con.setAutoCommit(autoCommit);
	}
	
	public void replicate(String query)
	{
		try
		{
			if (con.getAutoCommit())
			{
				//replicateQuery(query);
			}
			else
			{
				if (queryList == null)
				{
					queryList = new ArrayList();
				}
				queryList.add(query);	
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	private void replicateQuery(String query)
	{
		int len = query.length();
		File file = new File("/usr/local/tomcat/queries.txt");
		try
		{
			String length = ""+len;
			if (len < 10)
			{
				length = "00"+len;
			}
			else if (len < 100)
			{
				length = "0"+len;
			}
			file.createNewFile();
	
			FileWriter writer = new FileWriter(file, true);
			PrintWriter out = new PrintWriter(writer, true);
			out.print(length);
			out.println(query);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}

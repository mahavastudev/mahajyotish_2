package com.telemune.dbutilities;
// Copyright (c) Telemune Software Solutions Pvt. Ltd.
//File: PreparedStatement.java 
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

public class Statement 
{
	private String query = null;
//	private QueryParser queryParser = null;
	private java.sql.Statement stmt = null;
	private com.telemune.dbutilities.Connection tcon = null;

	public Statement(com.telemune.dbutilities.Connection tcon, java.sql.Statement stmt)
	{
		this.tcon = tcon;
		this.stmt = stmt;
//		this.query = query.toLowerCase();
//		if ((!this.query.startsWith("select")) && (tcon.isReplicate()))
//	  	this.queryParser = new QueryParser(query);
	}

/*	public void clearParameters() throws SQLException
	{
		pstmt.clearParameters();
	}

	public boolean execute() throws SQLException
	{
		return pstmt.execute();
	}*/

	public ResultSet executeQuery(String query) throws SQLException
	{
		return stmt.executeQuery(query);
	}

	public int executeUpdate(String query) throws SQLException
	{
		this.query = query.toLowerCase();
		if ((!this.query.startsWith("select")) && (tcon.isReplicate()))
			tcon.replicate(query);	
		return stmt.executeUpdate(query);
	}

	public void close() throws SQLException
	{
		stmt.close();
	}

/*	public void setBytes(int parameterIndex, byte[] x) throws SQLException
	{		
		if (queryParser !=null)
			queryParser.setBytes(parameterIndex, x);
		pstmt.setBytes(parameterIndex, x);
	}

	public void setFloat(int parameterIndex, float x) throws SQLException
	{		
		if (queryParser !=null)
//			queryParser.setBytes(parameterIndex, x);
		pstmt.setFloat(parameterIndex, x);
	}

	public void setInt(int parameterIndex, int x) throws SQLException
	{
		if (queryParser !=null)
			queryParser.setInt(parameterIndex, x);
		pstmt.setInt(parameterIndex, x);
	}

	public void setLong(int parameterIndex, long x) throws SQLException
	{
		if (queryParser !=null)
			queryParser.setLong(parameterIndex, x);
		pstmt.setLong(parameterIndex, x);
	}

	public void setString(int parameterIndex, String x) throws SQLException
	{
		if (queryParser !=null)
			queryParser.setString(parameterIndex, x);
		pstmt.setString(parameterIndex, x);
	}

	public void setDate(int parameterIndex, Date x) throws SQLException
	{
		if (queryParser !=null)
			queryParser.setDate(parameterIndex, x);
		pstmt.setDate(parameterIndex, x);
	}*/
}

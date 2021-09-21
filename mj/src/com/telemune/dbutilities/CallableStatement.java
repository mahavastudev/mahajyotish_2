package com.telemune.dbutilities;
// Copyright (c) Telemune Software Solutions Pvt. Ltd.
//File: CallableStatement.java 
/**
 * @Author                            Kartikey Gupta
 * @Version                           $Revision: 1.1 $
 * Creation-Date                      Tue Oct 16 13:38:36 IST 2012
 * Classes
 *
 */
/*
 * Development History
 * ####################
 * Date                                 Author                        Description
 * ------------------------------------------------------------------------------
 * Tue Oct 16 13:38:36 IST 2012        Kartikey Gupta                       Created.
 *
 */

import java.sql.*;

public class CallableStatement
{
	private String query = null;
	private QueryParser queryParser = null;
	private java.sql.CallableStatement cstmt = null;
	private com.telemune.dbutilities.Connection tcon = null;

public CallableStatement(com.telemune.dbutilities.Connection tcon, java.sql.CallableStatement cstmt, String query)
	{
		this.tcon = tcon;
		this.cstmt = cstmt;
		this.query = query.toLowerCase();
		if ((!this.query.startsWith("select")) && (tcon.isReplicate()))
	  	this.queryParser = new QueryParser(query);
	}

	public void clearParameters() throws SQLException
	{
		cstmt.clearParameters();
	}

	public boolean execute() throws SQLException
	{
		return cstmt.execute();
	}

	public ResultSet executeQuery() throws SQLException
	{
		return cstmt.executeQuery();
	}

	public int executeUpdate() throws SQLException
	{
		int ret = cstmt.executeUpdate();
		if ((queryParser != null) && (ret > 0))
			tcon.replicate(queryParser.getSql());	
		return ret;
//		if (queryParser != null)
//			tcon.replicate(queryParser.getSql());	
//		return cstmt.executeUpdate();
	}

	public void close() throws SQLException
	{
		cstmt.close();
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException
	{		
		if (queryParser !=null)
			queryParser.setBytes(parameterIndex, x);
		cstmt.setBytes(parameterIndex, x);
	}

	public void setFloat(int parameterIndex, float x) throws SQLException
	{		
		if (queryParser !=null)
			queryParser.setFloat(parameterIndex, x);
		cstmt.setFloat(parameterIndex, x);
	}

	public void setInt(int parameterIndex, int x) throws SQLException
	{
		if (queryParser !=null)
			queryParser.setInt(parameterIndex, x);
		cstmt.setInt(parameterIndex, x);
	}

	public void setLong(int parameterIndex, long x) throws SQLException
	{
		if (queryParser !=null)
			queryParser.setLong(parameterIndex, x);
		cstmt.setLong(parameterIndex, x);
	}

	public void setString(int parameterIndex, String x) throws SQLException
	{
		if (queryParser !=null)
			queryParser.setString(parameterIndex, x);
		cstmt.setString(parameterIndex, x);
	}
	
	public Object getObject(int parameterIndex) throws SQLException
	{
		  if (queryParser !=null)
                        return queryParser.getObject(parameterIndex);
              return cstmt.getObject(parameterIndex);

	}
	public int getInt(int parameterIndex) throws SQLException
        {
                  if (queryParser !=null)
                       return queryParser.getInt(parameterIndex);
               return cstmt.getInt(parameterIndex);

        }
	public Long getLong(int parameterIndex) throws SQLException
        {
                  if (queryParser !=null)
                       return queryParser.getLong(parameterIndex);
                return cstmt.getLong(parameterIndex);

        }
	public String getString(int parameterIndex) throws SQLException
        {
                  if (queryParser !=null)
                       return queryParser.getString(parameterIndex);
                return cstmt.getString(parameterIndex);

        }
	
	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException
	{
		cstmt.registerOutParameter(parameterIndex,sqlType);
	}
	public void registerOutParameter(String parameterName, int sqlType) throws SQLException
	{
	cstmt.registerOutParameter(parameterName,sqlType);
	}
	
	

}

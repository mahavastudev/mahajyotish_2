package com.telemune.dbutilities;
// Copyright (c) Telemune Software Solutions Pvt. Ltd.
//File: ConnectionPool.java 
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
import javax.naming.*;
import org.apache.log4j.*;

public class ConnectionPool 
{
	private Context initContext = null;
	private Context envContext = null;
	private DataSource ds = null;
	private com.telemune.dbutilities.Connection con = null;

	private boolean useA = true;

	Logger logger = Logger.getLogger("com.telemune.dbutilities.ConnectionPool");

	public ConnectionPool() 
	{
		logger.debug("Creating ConnectionPool");
	}

	// Returns the Connection from Poll
	public com.telemune.dbutilities.Connection getConnection() 
	{
		int count = 0;
		while (count++ <= 10)
		{
			java.sql.Connection conn = null;
			if (useA)
			{
				try 
				{
					initContext = new InitialContext();
					logger.debug("Created Initial Context: "+initContext);

					envContext  = (Context)initContext.lookup("java:/comp/env/");
					logger.debug("Created Environment Context: "+envContext);

					ds = (DataSource)envContext.lookup("jdbc/kundliA");
					logger.debug("Created DataSource A" + ds);

					DataSourceThread dsTh = new DataSourceThread(ds);
					dsTh.start();


					for (int i=0; i<1000; i++)
					{
						logger.info("Custare A : "+i);
						conn = dsTh.getConnection();
						if (conn != null)
							break;

						try
						{
							Thread.sleep(50);
						}
						catch (Exception exp)
						{
							exp.printStackTrace();
						}
					}

					if (conn == null)
					{
						useA = false;
					}

					else
					{
						con = new com.telemune.dbutilities.Connection(conn,false);
						break;
					}
				}
				catch(NamingException nameEx)
				{
					nameEx.printStackTrace();
					logger.warn(nameEx.getMessage());
				}
			}

			if (!useA)
			{
				try 
				{
					initContext = new InitialContext();
					logger.debug("Created Initial Context: "+initContext);

					envContext  = (Context)initContext.lookup("java:/comp/env/");
					logger.debug("Created Environment Context: "+envContext);

					ds = (DataSource)envContext.lookup("jdbc/kundliB");
					logger.debug("Created DataSource B" + ds);

					DataSourceThread dsTh = new DataSourceThread(ds);
					dsTh.start();

					for (int i=0; i<1000; i++)
					{						
						logger.info("Custcare B : "+i);
						conn =  dsTh.getConnection();
						if (conn != null)
							break;

						try
						{
							Thread.sleep(50);
						}
						catch (Exception exp)
						{
							exp.printStackTrace();
						}
					}

					if (conn == null)
					{
						useA = true;
					}

					else
					{
						con = new com.telemune.dbutilities.Connection(conn,false);
						break;
					}
				}
				catch(NamingException nameEx)
				{
					nameEx.printStackTrace();
					logger.warn(nameEx.getMessage());
				}
			}
		}
		return con;
	}

	// Receives the connection back to ConnectionPool
	public void free(java.sql.Connection conn)
	{
		try
		{
			conn.close();
		}
		catch(SQLException sqlExp)
		{
			logger.debug("custcare: ConnectionPool.. Could not free connection");
			sqlExp.printStackTrace();
			logger.warn(sqlExp.getMessage());
		}
	}

	// Receives the connection back to ConnectionPool
	public void free(com.telemune.dbutilities.Connection conn)
	{
		java.sql.Connection con = conn.getConnection();
		free(con);
	}	
}

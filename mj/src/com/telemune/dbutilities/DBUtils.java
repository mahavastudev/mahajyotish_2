package com.telemune.dbutilities;

// Copyright (c) Telemune Software Solutions Pvt. Ltd.
//File: DBUtils.java
/**
 * @Author                            Kartikey Gupta
 * @Version                           $Revision: 1.1 $
 * Creation-Date                      Tue Oct 17 14:55:36 IST 2012
 * Classes
 *
 */
/*
 * Development History
 * ####################
 * Date                                 Author                        Description
 * ------------------------------------------------------------------------------
 * Tue Oct 17 14:55:36 IST 2012        Kartikey Gupta                       Created.
 *
 */

import java.sql.*;

public class DBUtils
{
public static void close(com.telemune.dbutilities.CallableStatement cstmt)
	{
		if(cstmt!=null)
		{
		try {
	            cstmt.close();
	        } catch (SQLException e) {e.printStackTrace();}
		}
	}
public static void close(com.telemune.dbutilities.PreparedStatement pstmt)
        {
                if(pstmt!=null)
                {
                try {
                    pstmt.close();
                } catch (SQLException e) {e.printStackTrace();}
                }
        }

public static void close(java.sql.ResultSet rs)
        {
                if(rs!=null)
                {
                try {
                    rs.close();
                } catch (SQLException e) {e.printStackTrace();}
                }
        }
public static void close(com.telemune.dbutilities.ConnectionPool conPool,com.telemune.dbutilities.Connection con)
	{
	if(con!=null)
		{
		  try {
			conPool.free(con);                    
               } catch (Exception e) {e.printStackTrace();}

		}
	}
}


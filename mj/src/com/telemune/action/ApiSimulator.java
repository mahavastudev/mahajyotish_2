package com.telemune.action;
import java.io.*;
import org.apache.log4j.Logger;
public class ApiSimulator
{
	private static Logger logger=Logger.getLogger(ApiSimulator.class);


	public static BufferedReader getCountryBuffer()
	{
		BufferedReader countryBuffer=null;
		try
		{ 
			logger.info("Inside getCountryBuffer method of ApiSimulator action Class.");
			countryBuffer=new BufferedReader( new InputStreamReader(new FileInputStream( System.getProperty("user.dir")+"/src/countrylist.txt"),"UTF8"));
		}
		catch(Exception e)
		{
			logger.error("Inside catch block. Exception at getCountryBuffer method of ApiSimulator action Class. Exception  : "+e.toString());
			e.printStackTrace();

		}


		return countryBuffer;

	}

	public static BufferedReader getStateBuffer()
	{
		BufferedReader stateBuffer=null;
		try
		{
			logger.info("Inside getStateBuffer method of ApiSimulator action Class.");
			stateBuffer=new BufferedReader( new InputStreamReader(new FileInputStream( System.getProperty("user.dir")+"/src/statelist.txt"),"UTF8"));
		}
		catch(Exception e)
		{
			logger.error("Inside catch block. Exception at getStateBuffer method of ApiSimulator action Class. Exception : "+e.toString());
			e.printStackTrace();

		}

		return stateBuffer;

	}



} 

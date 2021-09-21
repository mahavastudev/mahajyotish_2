
package com.telemune.mobileAstro;
import java.io.*;
import org.apache.log4j.Logger;
public class ApiSimulator
{
	private static Logger logger=Logger.getLogger("ApiSimulator");


	public static BufferedReader getCountryBuffer()
	{
		BufferedReader countryBuffer=null;
		try
		{ 
			logger.info("Inside getCountryBuffer() ");
			countryBuffer=new BufferedReader( new InputStreamReader(new FileInputStream( System.getProperty("user.dir")+"/src/countrylist.txt"),"UTF8"));
		}
		catch(Exception e)
		{
			logger.error("exception at getCountryBuffer() "+e.toString());

		}


		return countryBuffer;

	}

	public static BufferedReader getStateBuffer()
	{
		BufferedReader stateBuffer=null;
		try
		{
			logger.info("Inside getStateBuffer()");
			stateBuffer=new BufferedReader( new InputStreamReader(new FileInputStream( System.getProperty("user.dir")+"/src/statelist.txt"),"UTF8"));
		}
		catch(Exception e)
		{
			logger.error("exception at getStateBuffer() "+e.toString());

		}

		return stateBuffer;

	}



} 

package com.telemune.mobileAstro;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;



import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UrlHit
{
String data="";
HttpURLConnection con=null;
private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UrlHit.class);
private static HashMap<String, String> hashMap=null;
private static HashMap<String, String> stateHashMap=null;
	URL url =null;
	
	
	public HashMap<String, String> getCountryList()
	{
	try
	{
	if(hashMap==null)
	{
		hashMap=new HashMap<String, String>();
		
		System.out.println("inside urlHit");
//       String urlAddress=TSSJavaUtil.instance().getOptusURL();
		String urlAddress="http://www.vedicreports.com/ParasharasLight?customerId=543249&subservice=3";
		
        url = new java.net.URL(urlAddress);

        con=(HttpURLConnection)url.openConnection();
        con.setRequestProperty("Content-type", "text/html");
        con.connect();
        logger.info("Content Type [ "+con.getContentType()+" ] Content "+con.getContentLength());
        
        
        int responseCode = con.getResponseCode();
        logger.info("response code is -------->"+responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		System.out.println("getting response");
		StringBuffer responseString = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			responseString.append(inputLine);
		}
		String[] temp=responseString.toString().split(";");
		
		for(int i=0;i<temp.length;i++)
		{
			String[] t=temp[i].split(",");
			if(!t[0].equalsIgnoreCase("United Kingdom"))
			{
				hashMap.put(temp[i].replaceAll(" ","_"),t[0]);
			}
		}
		
		in.close();
		
		//print result
		logger.info("response is --->>>"+responseString.toString());
		logger.info("HashMap--->>>"+hashMap);
		if(responseCode==200)
		{
//			return true;
			logger.info("success");
		}
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		url=null;
		if(con!=null)
			con.disconnect();
	}
		return hashMap;
	}



		public String getState(String val)
		{
		StringBuffer responseString = new StringBuffer();
		try
        	{

                System.out.println("inside getState  "+val);
//       String urlAddress=TSSJavaUtil.instance().getOptusURL();
                String urlAddress="http://www.vedicreports.com/ParasharasLight?customerId=543249&subservice=4&country="+val.trim();

	        url = new java.net.URL(urlAddress);

        	con=(HttpURLConnection)url.openConnection();
	        con.setRequestProperty("Content-type", "text/html");
        	con.connect();
	        logger.info("Content Type [ "+con.getContentType()+" ] Content "+con.getContentLength());


        	int responseCode = con.getResponseCode();
	        logger.info("response code is -------->"+responseCode);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                System.out.println("getting response");

                while ((inputLine = in.readLine()) != null) {
                        responseString.append(inputLine);
                }


                in.close();

                //print result
                logger.info("response is --->>>"+responseString.toString());
                if(responseCode==200)
                {
//                      return true;
                        logger.info("success");
                }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
        finally
        {
                url=null;
                if(con!=null)
			con.disconnect();
        }
                return responseString.toString();
        }




public HashMap<String, String> getStateList(String val)
        {
        try
        {
        if(stateHashMap==null)
        {
                stateHashMap=new HashMap<String, String>();

                System.out.println("inside urlHit StateList");
//       String urlAddress=TSSJavaUtil.instance().getOptusURL();
                String urlAddress="http://www.vedicreports.com/ParasharasLight?customerId=543249&subservice=4&country="+val.trim();

        url = new java.net.URL(urlAddress);

        con=(HttpURLConnection)url.openConnection();
        con.setRequestProperty("Content-type", "text/html");
        con.connect();
        logger.info("Content Type [ "+con.getContentType()+" ] Content "+con.getContentLength());


        int responseCode = con.getResponseCode();
        logger.info("response code is -------->"+responseCode);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                System.out.println("getting response");
                StringBuffer responseString = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                        responseString.append(inputLine);
                }
                String[] temp=responseString.toString().split(";");

                for(int i=0;i<temp.length;i++)
	     	{
			String[] t=temp[i].split(",");
                        stateHashMap.put(t[0],t[1]);
                }

                in.close();

                //print result
                logger.info("StateList response is --->>>"+responseString.toString());
                logger.info("StateList HashMap--->>>"+stateHashMap);
                if(responseCode==200)
                {
//                      return true;
                        logger.info("success");
                }
        }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
        finally
        {
                url=null;
                if(con!=null)
			con.disconnect();
        }
                return stateHashMap;
        }



	

	
}

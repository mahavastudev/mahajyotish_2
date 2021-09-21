package com.telemune.mobileAstro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


public class CalculateVimshottari {

	private static Logger logger = Logger.getLogger(CalculateVimshottari.class);
	public static HashMap<String,Integer> map=new HashMap<String, Integer>();
	public static String[] p={"Ketu","Venus","Sun","Moon","Mars","Rahu","Jupiter","Saturn","Mercury"};
	public static Double[] per={5.83333333,16.6666666,5.0,8.33333333,5.83333333,15.0,13.33333333,15.8333333,14.16666666};
	
	static ArrayList<String> planets= new  ArrayList<String>(Arrays.asList(p));
	static ArrayList<Double> percntg= new  ArrayList<Double>(Arrays.asList(per));
	
	
	static{
	 	map.put("Ketu",7);
		map.put("Venus",20);
		map.put("Sun",6);
		map.put("Moon", 10);
		map.put("Mars",7);
		map.put("Rahu",18);
		map.put("Jupiter",16);
		map.put("Saturn",19);
		map.put("Mercury",17);
	
	}
	
	
	public static JSONArray calculate(String startDate,String endDate,String Planet,String startPlanet)
	{
		logger.info("planets list "+planets.toString());
		logger.info("parcentage list "+percntg.toString());
		logger.info("planet map is "+map.toString());
		logger.info("inside calculate where startDate ["+startDate+"]   endDate ["+endDate+"]  Planet ["+Planet+"]   startPlanet ["+startPlanet+"]");
		//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH_mm_ss",Locale.ENGLISH);
		Calendar cal =null;
		Date today =null;
		Calendar cal1 =null;
		Calendar cal2= null; 
		JSONArray array=new JSONArray();
		JSONObject  object=null;
		
		try
		{
		//formatter = new SimpleDateFormat("dd-MM-yyyy");	
		formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");
		cal =new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		today = cal.getTime();
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		logger.info("After converting start date ==> "+startDate+" , end date ==> "+endDate);
		today = formatter.parse(startDate);
		Date end= formatter.parse(endDate);
		logger.info("Getting date after conversion start date ==> "+today+" , end date ==> "+end);
		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();
		cal1.setTime(today);
	    cal2.setTime(end);
		long milliSec1 = cal1.getTimeInMillis();
		long milliSec2 = cal2.getTimeInMillis();
		logger.info("After converting to millis start date in mills ==> "+milliSec1+" end date in millis ==> "+milliSec2);
		long diff=milliSec2-milliSec1; 
		String parent=Planet;
		logger.info("time diif in millis ["+diff+"]");
		int index=planets.indexOf(Planet.trim());
		int startIndex=0;
		logger.info(">>>>  getting index from planets list is ["+ index+"]");
		if(!startPlanet.equalsIgnoreCase(Planet))
		{
		   logger.info("inside if start planet ["+startPlanet+"] is not equal to planet ["+Planet+"]");	
		   while(!Planet.equalsIgnoreCase(startPlanet)){
				if(index==planets.size())
					index=0;
				Planet=planets.get(index);
				logger.info(Planet+"    "+startPlanet);
				if(Planet.equalsIgnoreCase(startPlanet))
					break;
				object=new JSONObject();
				object.put("planet",Planet);
				object.put("start","");
				object.put("end","");
				array.put(startIndex,object);
				startIndex++;
				
				index++;
			}
		}
		logger.info("######### loop starts ############");
		for(int i=startIndex;i<9;i++){
	      
			if(index==planets.size())
				index=0;
			Planet=planets.get(index);
			milliSec1 = cal1.getTimeInMillis();
			logger.info("index ["+index+"] planet ["+Planet+"], startdate mills ["+milliSec1+"], parcentage ["+percntg.get(index)+"]");
			
			long d=(long)((diff*percntg.get(index))/100);
			
			logger.info("calculated logic value  (long)((diff*percntg.get(index))/100) ["+d+"] ");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(milliSec1);
			logger.info("pick current calander instanse and set startdate millis["+milliSec1+"] to it ");
			while(d>0)
			{
				logger.info("while loop starts (d > (long)Integer.MAX_VALUE) "+(d > (long)Integer.MAX_VALUE));
				if ( d > (long)Integer.MAX_VALUE ) {
					d=d-(long)Integer.MAX_VALUE;
					calendar.add(Calendar.MILLISECOND, Integer.MAX_VALUE);
					logger.info("while inside if, adding millis to calander current instance ["+d+"]");
				}
				else
				{
					logger.info("while inside else, adding millis to calander current instance ["+d+"]");
					calendar.add(Calendar.MILLISECOND, (int)d);
					d=0;
				}
			}
			
			
			index++;
			logger.info("index value after ++ ["+index+"]");
			object=new JSONObject();
			object.put("planet",Planet);
			object.put("start",formatter.format(today));
			if(i==8)
			{
				object.put("end",endDate);
			}
			else{
				object.put("end",formatter.format(calendar.getTime()));
			}
			
			
			today = formatter.parse(formatter.format(calendar.getTime()));
			logger.info("planet ["+Planet+" ], today date [ "+today+" ] , calander gettime [ "+formatter.format(calendar.getTime())+"]");
			cal1.setTime(today);
			  
			array.put(i,object);
	
		}
			
		
	}
	catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
	}
	finally
	{
		formatter=null;
		cal =null;
		today =null;
	
		cal1 =null;
		cal2= null; 
	}
	logger.info("final response >>>>>>>>> "+array.toString());
	return array;
}
	

	
	
	
	
	
	
	public static JSONObject calculateBack(String startDate,String startingPlanet,String Planet)
	{
		logger.info("inside calculateBack");
		DateFormat formatter=null;
		Calendar cal =null;
		Date today =null;
		Calendar cal1 =null;
		Calendar cal2= null; 
		JSONArray array=new JSONArray();
		JSONObject  object=null;
		
		try
		{
		formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");	
		
		cal =new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		today = cal.getTime();
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		today = formatter.parse(startDate);
		
		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();
		cal1.setTime(today);
		cal2.setTime(today);
//		logger.info(">>>>  map.get(startingPlanet  "+map.get(startingPlanet));
		cal2.add(Calendar.YEAR, -map.get(startingPlanet));
		
		long milliSec1 = cal1.getTimeInMillis();
		long milliSec2 = cal2.getTimeInMillis();
		
		long diff=milliSec1-milliSec2; 
//		logger.info("diffn n   "+diff+",strt"+formatter.format(cal1.getTime())+", end"+formatter.format(cal2.getTime()));
		String parent=Planet;
		
		int index=planets.indexOf(Planet.trim());
		object=new JSONObject();
	
		for(int i=0;i<9;i++){
	        // logger.info(key  +" :: "+ pmap.get(key));
	      
			index--;
		if(index<0)
			index=planets.size()-1;
		Planet=planets.get(index);


		
		milliSec1 = cal1.getTimeInMillis();
		
//		logger.info(Planet+" percntg.get(index)  "+percntg.get(index));
		long d=(long)((diff*percntg.get(index))/100);
		
//		logger.info("d :::::::  "+d);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSec1);
		
		while(d!=0)
		{
			if ( d > (long)Integer.MAX_VALUE ) {
				d=d-(long)Integer.MAX_VALUE;
				calendar.add(Calendar.MILLISECOND, -Integer.MAX_VALUE);
			}
			else
			{
				calendar.add(Calendar.MILLISECOND, -(int)d);
				d=0;
			}
		}
		
		
//		timeDifSeconds=(long) ((timeDifSeconds*percntg.get(index))/100);
		//logger.info(diff);
				
		//logger.info(formatter.format(calendar.getTime()));
		
		
		
		
		
		
		
	//	calendar.add(Calendar.MILLISECOND, (int)d);
		
		object.put("planet",Planet);
		object.put("end",formatter.format(today));
		object.put("start",formatter.format(calendar.getTime()));
		
//		logger.info(Planet+"   "+formatter.format(today)+"  "+formatter.format(calendar.getTime()));
		today = formatter.parse(formatter.format(calendar.getTime()));
		  cal1.setTime(today);
		  
		  

		  if(Planet.equalsIgnoreCase(startingPlanet))
			  break;
		  
		 }
		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			formatter=null;
			cal =null;
			today =null;
			cal1 =null;
			cal2= null; 
		}
		return object;
	}
	

	
	
	
	
	
	public static JSONArray mainDasha(String startDate,String Planet)
	{
		logger.info("inside mainDasha");
		DateFormat formatter=null;
		Calendar cal =null;
		Date today =null;
		Calendar cal1 =null;
		Calendar cal2= null; 
		JSONArray array=new JSONArray();
		JSONObject  object=null;
		
		try
		{
		formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");	
		
		cal =new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		today = cal.getTime();
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		today = formatter.parse(startDate);
		
		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();
		cal1.setTime(today);
		cal2.setTime(today);
		long milliSec1 = cal1.getTimeInMillis();
		
		int index=planets.indexOf(Planet.trim());
	
	
		for(int i=0;i<9;i++){
	        // logger.info(key  +" :: "+ pmap.get(key));
	      
		if(index<0)
			index=planets.size()-1;
		if(index==planets.size())
			index=0;
		
		Planet=planets.get(index);


		
		milliSec1 = cal1.getTimeInMillis();
		cal2.add(Calendar.YEAR,map.get(Planet));
		
		long milliSec2 = cal2.getTimeInMillis();
		long d=milliSec2-milliSec1; 
		logger.info(">>>"+Planet+"  "+map.get(Planet)+"   "+d);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSec1);
		object=new JSONObject();
		
		while(d>0)
		{
			if ( d > (long)Integer.MAX_VALUE ) {
				d=d-(long)Integer.MAX_VALUE;
				calendar.add(Calendar.MILLISECOND, Integer.MAX_VALUE);
			}
			else
			{
				calendar.add(Calendar.MILLISECOND, (int)d);
				d=0;
			}
		}
		
		
//		timeDifSeconds=(long) ((timeDifSeconds*percntg.get(index))/100);
		//logger.info(diff);
				
		//logger.info(formatter.format(calendar.getTime()));
		
		
		
		
		
		
		
	//	calendar.add(Calendar.MILLISECOND, (int)d);
		object.put("planet",Planet);
		object.put("start",formatter.format(today));
		object.put("end",formatter.format(calendar.getTime()));
		
		logger.info(Planet+"   "+formatter.format(today)+"  "+formatter.format(calendar.getTime()));
		today = formatter.parse(formatter.format(calendar.getTime()));
		
		cal1.setTime(today);
		  index++;
		  array.put(i,object);
		 }
		
		  

		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			formatter=null;
			cal =null;
			today =null;
			cal1 =null;
			cal2= null; 
		}
		return array;
		
	}
	

	
	
	
	
	
	public static JSONObject calculateMainDasha(String startDate,String startingPlanet,String Planet)
	{
		logger.info("inside calculateMainDasha");
		DateFormat formatter=null;
		Calendar cal =null;
		Date today =null;
		Calendar cal1 =null;
		Calendar cal2= null; 
		JSONArray array=new JSONArray();
		JSONObject  object=null;
		
		try
		{
		formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");	
		
		cal =new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		today = cal.getTime();
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		today = formatter.parse(startDate);
		
		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();
		cal1.setTime(today);
		
		long milliSec1 = cal1.getTimeInMillis();
		
		String parent=Planet;
		
		int index=planets.indexOf(Planet.trim());
	
	
		for(int i=0;i<9;i++){
	        // logger.info(key  +" :: "+ pmap.get(key));
	      
		cal2.setTime(today);
			index--;
		if(index<0)
			index=planets.size()-1;
		Planet=planets.get(index);


		
		milliSec1 = cal1.getTimeInMillis();
		cal2.add(Calendar.YEAR, -map.get(Planet));
		long milliSec2 = cal2.getTimeInMillis();
		long d=milliSec1-milliSec2; 
		
		
//		logger.info(Planet+" percntg.get(index)  "+percntg.get(index));
//		long d=(long)((diff*percntg.get(index))/100);
		
		
		//logger.info("d :::::::  "+d);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSec1);
		object=new JSONObject();
		
		while(d!=0)
		{
			if ( d > (long)Integer.MAX_VALUE ) {
				d=d-(long)Integer.MAX_VALUE;
				calendar.add(Calendar.MILLISECOND, -Integer.MAX_VALUE);
			}
			else
			{
				calendar.add(Calendar.MILLISECOND, -(int)d);
				d=0;
			}
		}
		
		
//		timeDifSeconds=(long) ((timeDifSeconds*percntg.get(index))/100);
		//logger.info(diff);
				
		//logger.info(formatter.format(calendar.getTime()));
		
		
		
		
		
		
		
	//	calendar.add(Calendar.MILLISECOND, (int)d);
		object.put("planet",Planet);
		object.put("end",formatter.format(today));
		object.put("start",formatter.format(calendar.getTime()));
		
		logger.info(Planet+"   "+formatter.format(today)+"  "+formatter.format(calendar.getTime()));
		today = formatter.parse(formatter.format(calendar.getTime()));
		  cal1.setTime(today);
		  
		
		  if(Planet.equalsIgnoreCase(startingPlanet))
			  break;
		  
		 }
		
//		  array.put(i,object);

	
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			formatter=null;
			cal =null;
			today =null;
			cal1 =null;
			cal2= null; 
		}
		return object;
		
	}
	
	
	
//	
//	public static void main(String[] args) {
//		logger.info(CalculateVimshottari.calculate("17-12-2013 10:13:04", "29-08-2016 13:54:41", "rahu"));
//	}
	
}

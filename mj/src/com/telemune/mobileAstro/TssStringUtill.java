//Dev:rips
package com.telemune.mobileAstro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.regex.*;
import java.text.*;

public class TssStringUtill
{
	private static final String DATE_PATTERN ="(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)";
	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:?[0-5]?[0-9]?";
	private static final String EMAIL_PATTERN="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private  static final String STRING_PATTERN ="[ a-zA-Z]*";

	public static ArrayList  isValidEnglishTextA(String ... texts)
	{
		ArrayList invalidSymbols=new ArrayList(); 
		try
		{

			int ch=0;
			char _ch='\n';
			for(String text : texts)
			{
				for(int i=0;i<text.length();i++)
				{
					ch=(int)(text.charAt(i));
					if( !( (ch>=32) && (ch<=127) ))
					{
						_ch=text.charAt(i);
						System.out.println("Invalid Character "+text.charAt(i) +"  "+(int)_ch);
						invalidSymbols.add("&#"+(int)(_ch)+";");
					}
				}
			}

			return invalidSymbols;
		}
		catch(Exception e)
		{
			System.out.println("Exception in isValidEnglishTextA "+e.toString());
			return null;
		}

	}



	static public String byteToHex(byte b) {
		char hexDigit[] = {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};
		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	static public String charToHex(char c) {
		byte hi = (byte) (c >>> 8);
		byte lo = (byte) (c & 0xff);
		return byteToHex(hi) + byteToHex(lo);
	}




	public static boolean  isValidEnglishTextB(String ... texts)
	{
		boolean valid=true;
		try
		{
			int ch=0;
			for(String text : texts)
			{
				for(int i=0;i<text.length();i++)
				{
					ch=(int)(text.charAt(i));
					System.out.println("ch"+ch);
					if( !( (ch>=32) && (ch<=127) ))
					{
						valid=false;
						System.out.println("Invalid Character "+text.charAt(i));
					}
				}
			}

			return valid;
		}
		catch(Exception e)
		{
			System.out.println("Exception in isValidEnglishTextB "+e.toString());
			return false;
		}

	}

	public static String frenchToEnglish(String frenchText)
	{
		StringBuffer finalText=new StringBuffer();
		int ch=0;
		for(int i=0;i<frenchText.length();i++)
		{

			ch=(int)(frenchText.charAt(i));
			if(ch==192||ch==194||ch==198)
				finalText.append("A");
			else if(ch==224||ch==226||ch==230||ch==257)
				finalText.append("a");

			else if(ch==199||ch==140)
				finalText.append("C");
			else if(ch==231||ch==156)
				finalText.append("c");
			else if(ch==200||ch==201||ch==202||ch==203)
				finalText.append("E");
			else if(ch==232||ch==233||ch==234||ch==235)
				finalText.append("e");

			else if(ch==206||ch==207)
				finalText.append("I");
			else if(ch==238||ch==239 || ch==299)
				finalText.append("i");

			else if(ch==212)
				finalText.append("O");
			else if(ch==244)
				finalText.append("o");

			else if(ch==217||ch==219||ch==220)
				finalText.append("U");
			else if(ch==249||ch==251||ch==252)
				finalText.append("u");
			else if(ch==255)
				finalText.append("Y");
			else if(ch==339)
				finalText.append("ce");
			else
				finalText.append(frenchText.charAt(i));


		}


		return finalText.toString();
	}


	public static Hashtable<String,String> readPropery(String path)
	{
		FileInputStream fis1 =null;
		Hashtable<String,String> propList=new Hashtable<String,String>();
		Properties properties = new Properties();
		try
		{
			String key="NA",value="NA";
			fis1 = new FileInputStream(path); 
			properties.load(fis1);
			Set KEYS=properties.keySet();
			Iterator it=KEYS.iterator();
			while(it.hasNext())
			{
				key=(String)it.next();
				value=properties.getProperty(key);
				System.out.println(key+"=="+value);
				propList.put(key,value);
			}
			return propList;
		}
		catch(Exception e)
		{
			System.out.println("Exception in readProperty "+e.toString());
			return null;
		}
		finally
		{
			try{
				fis1.close();
			}
			catch(Exception fin) 
			{
				System.out.println("Exception in closing fis1 "+fin.toString());
			}
		}

	}
	public static  boolean ValidateParams(String ... Params)
	{
		boolean result=true;
		for(String params : Params)
		{
			if(params==null||params.equals(null)||params.trim().equals("")||params.length()==0)
			{
				result =false;
			}
		}
		return result;

	}



	public static URLConnection getURLConnection (String urlstring)
	{
		try
		{
			URL url = new URL (urlstring);
			URLConnection conn = url.openConnection ();

			conn.setRequestProperty ("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.1.4) Gecko/20091016 Firefox/3.5.4"); // API Hack
			conn.setRequestProperty ("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty ("Accept-Language", "en-gb,en;q=0.5");
			conn.addRequestProperty ("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");

			return conn;
		}
		catch (MalformedURLException ex)
		{

			return null;
		}
		catch (IOException ex)
		{

			return null;
		}
	}

	public static String getSource (URLConnection conn)
	{
		StringBuffer sb = new StringBuffer ("");

		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader (conn.getInputStream (), "UTF8"));
			String line;

			while ((line = in.readLine ()) != null)
			{
				sb.append (line);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace ();
		}

		return sb.toString ();
	}
	public static String readUrl(String url)
	{
		return  getSource(getURLConnection(url)); 

	}




	/** 
	  @srcTemplate ::Template Message containing single/multiple  tags like $(tag_name).
	  @tag_name :: 
	 */
	public  static String CreateTemplate(String srcTemplate,Hashtable<String,String>  tagMaps)
	{
		String _key="";
		Enumeration keys = tagMaps.keys();
		while(keys.hasMoreElements())
		{
			_key=(String)keys.nextElement();

			srcTemplate=srcTemplate.replace(_key,(String)tagMaps.get(_key));
		}
		return srcTemplate;
	}

	/*public static boolean intersect(T[] first, T[] second) {
	  boolean ret=false;
	// initialize a return set for intersections
	Set<T> intsIntersect = new HashSet<T>();

	// load first array to a hash
	HashSet<T> array1ToHash = new HashSet<T>();
	for (int i = 0; i < first.length; i++) {
	array1ToHash.add(first[i]);
	}


	// check second array for matches within the hash
	for (int i = 0; i < second.length; i++) {
	if (array1ToHash.contains(second[i])) 
	{
	ret=true;

	break;
	}
	}

	return ret;

	}*/
	public static  boolean validateDate(final String date){

		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(DATE_PATTERN);
		matcher = pattern.matcher(date);

		if(matcher.matches()){

			matcher.reset();

			if(matcher.find()){

				String day = matcher.group(1);
				String month = matcher.group(2);
				int year = Integer.parseInt(matcher.group(3));

				if (day.equals("31") && 
						(month.equals("4") || month .equals("6") || month.equals("9") ||
						 month.equals("11") || month.equals("04") || month .equals("06") ||
						 month.equals("09"))) {
					return false; // only 1,3,5,7,8,10,12 has 31 days
				} else if (month.equals("2") || month.equals("02")) {
					//leap year
					if(year % 4==0){
						if(day.equals("30") || day.equals("31")){
							return false;
						}else{
							return true;
						}
					}else{
						if(day.equals("29")||day.equals("30")||day.equals("31")){
							return false;
						}else{
							return true;
						}
					}
				}else{				 
					return true;				 
				}
			}else{
				return false;
			}		  
		}else{
			return false;
		}			    
	}
	public static boolean validateTime(final String time){
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(TIME24HOURS_PATTERN); 
		matcher = pattern.matcher(time);
		return matcher.matches();

	}

	public static boolean ValidateEmail(final String email){
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public static boolean ValidateString(final String string){
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(STRING_PATTERN);
		matcher = pattern.matcher(string);
		return matcher.matches();

	}

	public static boolean checkDateFormat(String DOB)
	{
		try
		{
			SimpleDateFormat parseFormat=new SimpleDateFormat("dd-MM-yy");
			java.util.Date FDOB=parseFormat.parse(DOB);
			java.util.Date currentDate =new java.util.Date();
			currentDate=parseFormat.parse(parseFormat.format(currentDate));

			System.out.println("User's DOB [ "+FDOB+" ] Current Date [ "+currentDate+"]");
			Calendar FDOBCal=Calendar.getInstance();
			FDOBCal.setTime(FDOB);

			Calendar cCal=Calendar.getInstance();
			cCal.setTime(currentDate);



			if( (FDOBCal.getTimeInMillis() <= cCal.getTimeInMillis() ))
				return true;
			else
				//return false;  //rips
				return true;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}


}

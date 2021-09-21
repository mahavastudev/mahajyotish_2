package servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.util.Constants;

/**
 * Servlet implementation class ZohoAuthentication
 */
@WebServlet("/customerQueries")
public class CustomerQueries extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Hashtable<String, String> properties = null;
	private String proPath = "";
	static Logger logger = Logger.getLogger(CustomerQueries.class);

	/**
	 * Default constructor.
	 */
	public CustomerQueries() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * logger.info("Inside doPost() method of CustomerQueries Servlet"); try {
	 * DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a"); Date date
	 * = new Date(); proPath = Constants.PROPERTIES_PATH; proPath =
	 * proPath + "kundliHttpserverNew.properties"; properties =
	 * ReadPropertyFile.readPropery(proPath); String currentDateTime =
	 * dateFormat.format(date); String email = request.getParameter("email"); String
	 * name = request.getParameter("name"); String courseDate =
	 * request.getParameter("date"); String mobile = request.getParameter("mobile");
	 * String description = request.getParameter("description"); String queries =
	 * request.getParameter("queries"); String contactCode =
	 * request.getParameter("mvCode"); String postResponse = "";
	 * logger.info("Entered Email ["+email+"] contactCode ["+contactCode+"] name ["
	 * +name+"] courseDate ["+courseDate+"] mobile ["+mobile+"] description ["
	 * +description+"] queries ["+queries+"]");
	 * 
	 * String authtoken = "3e4763ed37f611e49cdeeb8d2bfcd674"; String scope =
	 * "crmapi"; String criteria = ""; String xmlData =
	 * "<?xml version='1.0' encoding='UTF-8' ?><CustomModule9><row no='1'><FL val='Customer Query Name'>"
	 * +name+"</FL><FL val='MV Code'>"+contactCode+"</FL><FL val='Name'>"
	 * +name+"</FL><FL val='Email'>"+email+"</FL><FL val='Contact Number'>"
	 * +mobile+"</FL><FL val='Category'>MAHAJYOTISH</FL><FL val='Query'>"
	 * +queries+"</FL><FL val='Status'>New</FL><FL val='Query Description'>"
	 * +description+"</FL><FL val='4 Day Course Date'>"
	 * +courseDate+"</FL><FL val='Date And Time'>"+currentDateTime+
	 * "</FL></row></CustomModule9>";
	 * 
	 * String selectColumns =
	 * "Contacts(First Name,Last Name,Email,Contact Code,Access)";
	 * 
	 * if (!contactCode.equals("") && contactCode != null){ criteria =
	 * "(Contact Code:"+contactCode+")"; }
	 * 
	 * else if(!email.equals("") && email != null ){ criteria = "(Email:"+email+")";
	 * }
	 * 
	 * 
	 * // if(!criteria.equals("") && criteria != null){ String newFormat = "1";
	 * String fromIndex = "1"; String toIndex = "50"; criteria =
	 * "(Email:"+email+")"; // String targetURL =
	 * "https://crm.zoho.com/crm/private/json/Contacts/searchRecords"; // String
	 * targetURL =
	 * "https://crm.zoho.com/crm/private/xml/CustomModule9/insertRecords"; String
	 * targetURL=properties.get("targetURL"); // String targetURL =
	 * "https://crm.zoho.com/crm/private/xml/CustomModule9/searchRecords"; String
	 * paramname = "content";
	 * 
	 * PostMethod post = new PostMethod(targetURL);
	 * post.setParameter("authtoken",authtoken); post.setParameter("scope",scope);
	 * post.setParameter("xmlData",xmlData);
	 * 
	 * // post.setParameter("fromIndex",fromIndex); //
	 * post.setParameter("toIndex",toIndex); // post.setParameter("criteria",id);
	 * post.setParameter("newFormat",newFormat);
	 * post.setParameter("wfTrigger","true"); //
	 * post.setParameter("selectColumns",selectColumns);
	 * 
	 * HttpClient httpclient = new HttpClient();
	 * 
	 * 
	 * //Execute http request long t1 = System.currentTimeMillis(); int result =
	 * httpclient.executeMethod(post); logger.info("HTTP Response status code: " +
	 * result); logger.info(">> Time taken " + (System.currentTimeMillis() - t1));
	 * 
	 * postResponse = post.getResponseBodyAsString();
	 * logger.info("Post Response ::: ===>>> ["+postResponse+"]."); // }
	 * 
	 * else{ postResponse = "Input Value Error"; }
	 * 
	 * response.setContentType("text/plain");
	 * response.setCharacterEncoding("UTF-8");
	 * response.getWriter().write(postResponse);
	 * 
	 * 
	 * } catch(Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Inside doPost() method of CustomerQueries Servlet");
		URL url=null;
		HttpURLConnection conn = null;
		BufferedReader in =null;
		StringBuffer response1=new StringBuffer();
		proPath = Constants.PROPERTIES_PATH;
		proPath = proPath + "kundliHttpserverNew.properties";
		properties = ReadPropertyFile.readPropery(proPath);
		String tokenFile=properties.get("tokenFile");
		String targetURL=properties.get("targetURL");
		String currentDateTime,email,name,lastName,courseDate,mobile,description,queries,contactCode,postResponse,jsonData=null;
		try
           {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			Date date = new Date();
			 currentDateTime = dateFormat.format(date);
			 email = request.getParameter("email");
			 name = request.getParameter("name");
			 String getLastName[]=name.split(" ");
			 
			 lastName=getLastName[1];
	         courseDate = request.getParameter("date");
		     mobile = request.getParameter("mobile");
			 description = request.getParameter("description");
			 queries = request.getParameter("queries");
			 contactCode = request.getParameter("mvCode");
			 postResponse = "";
			logger.info("Entered Email ["+email+"] contactCode ["+contactCode+"] name ["+name+"] last name ["+lastName+"] courseDate ["+courseDate+"] mobile ["+mobile+"] description ["+description+"] queries ["+queries+"] currentDateTime ["+currentDateTime+"]");
			jsonData ="{'data':[{'Customer_Query_Name':'"+name+"','MV_Code':'"+contactCode+"','Name':'"+name+"','Last_Name':'"+lastName+"','Email':'"+email+"','Contact_Number':'"+mobile+"','Category':'MAHAJYOTISH','Query':'"+queries+"','Status':'New','Query_Description':'"+description+"','4 Day Course Date':'"+courseDate+"','Date_And_Time':'"+currentDateTime+"'}],'trigger':['approval','workflow','blueprint']}";
			System.out.println("jsonData==>"+jsonData); 
			url = new URL(targetURL);
		    conn = (HttpURLConnection) url.openConnection();
		    conn.setDoOutput(true);
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tokenFile)));
				String strLine;
				while ((strLine = br.readLine()) != null)   {
					System.out.println("strLine===>"+strLine);
					conn.setRequestProperty("Authorization","Zoho-oauthtoken "+strLine);
				}
				br.close(); 
				conn.setRequestProperty("Content-Type","application/json");
			     conn.setRequestMethod("POST");
			     OutputStream os = conn.getOutputStream();
			        os.write(jsonData.getBytes());
			        os.flush();

			     in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			     String output;
			     while ((output = in.readLine()) != null) {
			            response1.append(output);
			        }
			response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(response1.toString());
		    in.close();
            logger.info("Response after hit the url ["+response1+"]");
		}
        catch(Exception exception)
        {
        	exception.printStackTrace();
        	int status=conn.getResponseCode();
      	    System.out.println("response code==>"+status);
      	  try {
        	  if(status==401) {
        		  String freshTokenHit=properties.get("freshTokenHit");
        		  String freshTokenAuthorization=properties.get("freshTokenAuthorization"); 
        		  url = new URL(freshTokenHit);
				  conn = (HttpURLConnection) url.openConnection();
				  conn.setDoOutput(true);
 			      conn.setRequestProperty("Authorization","Zoho-oauthtoken "+freshTokenAuthorization);
				  conn.setRequestProperty("Content-Type","application/json");
				  conn.setRequestMethod("POST");
				  OutputStream os = conn.getOutputStream();
			        os.write(jsonData.getBytes());
			        os.flush();

				  in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				  String output;
				  while ((output = in.readLine()) != null) {
				           response1.append(output);
				        }
				}
			  in.close();
			  System.out.println("Response after fresh token hit the url ["+response1+"]");
              JSONObject obj=new JSONObject(response1.toString());
              String access_token = (String) obj.get("access_token");
              FileWriter fw=new FileWriter(tokenFile);    
              fw.write(access_token);    
              fw.close();
              StringBuffer responseFresh=new StringBuffer(); 
              url = new URL(targetURL);
  		    conn = (HttpURLConnection) url.openConnection();
  		    conn.setDoOutput(true);
  		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tokenFile)));
  				String strLine;
  				while ((strLine = br.readLine()) != null)   {
  					System.out.println("strLine===>"+strLine);
  					conn.setRequestProperty("Authorization","Zoho-oauthtoken "+strLine);
  				}
  				br.close(); 
  				conn.setRequestProperty("Content-Type","application/json");
  			     conn.setRequestMethod("POST");
  			     OutputStream os = conn.getOutputStream();
  			        os.write(jsonData.getBytes());
  			        os.flush();

  			     in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
  			     String output;
  			     while ((output = in.readLine()) != null) {
  			            response1.append(output);
  			        }
  			response.setContentType("text/plain");
              response.setCharacterEncoding("UTF-8");
              response.getWriter().write(response1.toString());
  		    in.close();
              logger.info("Response after hit the url ["+response1+"]");
        }catch(Exception exception1)
      	  {
        	exception1.printStackTrace();
      	  }

        }
	}

}

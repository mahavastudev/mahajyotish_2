package servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

/*//**
 Servlet implementation class ZohoAuthentication
//*
*/
@WebServlet("/ZohoAuthentication")
public class ZohoAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Hashtable<String, String> properties = null;
	 private String proPath = "";
	static Logger logger = Logger.getLogger(ZohoAuthentication.class);

	/*
	*//**
		 * Default constructor.
		 */
	/*
	*/    public ZohoAuthentication() {
        // TODO Auto-generated constructor stub
    }

	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		logger.info("Inside doPost() method of ZohoAuthentication Servlet");

		 try
           {
            String email = request.getParameter("email");
			String contactCode = request.getParameter("contactCode");
			String postResponse = "";

			logger.info("Entered Email :: "+email+"  ,,   contactCode:"+contactCode);

			String authtoken = "3e4763ed37f611e49cdeeb8d2bfcd674";
			String scope = "crmapi";
			String criteria = "";

			String selectColumns = "Contacts(First Name,Last Name,Email,Contact Code,Access)";
			proPath = Constants.PROPERTIES_PATH;
            proPath = proPath + "kundliHttpserverNew.properties";
        	properties = ReadPropertyFile.readPropery(proPath);

			if (!contactCode.equals("") && contactCode != null){
				criteria = "(Contact Code:"+contactCode+")";
			}

			else if(!email.equals("") && email != null ){
				criteria = "(Email:"+email+")";
			}


			if(!criteria.equals("") && criteria != null){
                        String newFormat = "1";
			String fromIndex = "1";
			String toIndex = "50";

                       // String targetURL = "https://crm.zoho.com/crm/private/json/Contacts/searchRecords";
                        String targetURL=properties.get("zohoAuthenticationTargetURL");
                        String paramname = "content";

                        PostMethod post = new PostMethod(targetURL);
                        post.setParameter("authtoken",authtoken);
                        post.setParameter("scope",scope);
			post.setParameter("fromIndex",fromIndex);
			post.setParameter("criteria",criteria);
			post.setParameter("toIndex",toIndex);
                        post.setParameter("newFormat",newFormat);
			post.setParameter("selectColumns",selectColumns);

                        HttpClient httpclient = new HttpClient();


			//Execute http request
                        long t1 = System.currentTimeMillis();
                        int result = httpclient.executeMethod(post);
                        logger.info("HTTP Response status code: " + result);
                        logger.info(">> Time taken " + (System.currentTimeMillis() - t1));

                        postResponse = post.getResponseBodyAsString();
                        logger.info("Post Response ::: ===>>> ["+postResponse+"].");
			}

			else{
				postResponse = "Input Value Error";
			}
			
			response.setContentType("text/plain");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(postResponse);
			

			}
        catch(Exception e)
        {
            e.printStackTrace();
        }


	}

*/
	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Inside doPost() method of ZohoAuthentication Servlet");
		URL url=null;
		HttpURLConnection conn = null;
		BufferedReader in =null;
		StringBuffer response1=new StringBuffer();
		proPath = Constants.PROPERTIES_PATH;
        proPath = proPath + "kundliHttpserverNew.properties";
    	properties = ReadPropertyFile.readPropery(proPath);
    	String tokenFile=properties.get("tokenFile");
    	String email = request.getParameter("email");
		String contactCode = request.getParameter("contactCode");
		 try
          {
			    logger.info("Entered Email :: "+email+"  ,,   contactCode:"+contactCode);
				String criteria = "";
	        	if (!contactCode.equals("") && contactCode != null){
					criteria = "((Contact_Code:equals:"+contactCode+"))";
				}
				else if(!email.equals("") && email != null ){
					criteria = "((Email:equals:"+email+"))";
				}
				if(!criteria.equals("") && criteria != null){
					
					String targetURL=properties.get("zohoAuthenticationTargetURL")+criteria;
					logger.info("Target url ["+targetURL+"]");
					 url = new URL(targetURL);
				     conn = (HttpURLConnection) url.openConnection();
				     BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tokenFile)));
						String strLine;
						while ((strLine = br.readLine()) != null)   {
							System.out.println("strLine===>"+strLine);
							conn.setRequestProperty("Authorization","Zoho-oauthtoken "+strLine);
						}
						br.close(); 
					//conn.setRequestProperty("Authorization","Zoho-oauthtoken "+"1000.a11453866y2c3ebad095497ce2c8076a.79697f0f1026e72b4195e6b344a490ad");
				     conn.setRequestProperty("Content-Type","application/json");
				     conn.setRequestMethod("GET");
				     in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				     String output;
				     while ((output = in.readLine()) != null) {
				            response1.append(output);
				        }
				}				
				 response.setContentType("text/plain");
                 response.setCharacterEncoding("UTF-8");
                 response.getWriter().write(response1.toString());
			        in.close();
                 logger.info("Response after hit the url ["+response1+"]");

          } catch(Exception exception)
	        {
        	  exception.printStackTrace();
        	  int status=conn.getResponseCode();
        	  //System.out.println("response code==>"+status);
        	  try {
        	  if(status==401) {
        		  String freshTokenHit=properties.get("freshTokenHit");
        		  String freshTokenAuthorization=properties.get("freshTokenAuthorization"); 
        		  url = new URL(freshTokenHit);
				  conn = (HttpURLConnection) url.openConnection();
 			      conn.setRequestProperty("Authorization","Zoho-oauthtoken "+freshTokenAuthorization);
				  conn.setRequestProperty("Content-Type","application/json");
				  conn.setRequestMethod("POST");
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
              String criteria = "";
	        	if (!contactCode.equals("") && contactCode != null){
					criteria = "((Contact_Code:equals:"+contactCode+"))";
				}
				else if(!email.equals("") && email != null ){
					criteria = "((Email:equals:"+email+"))";
				}
	        	if(!criteria.equals("") && criteria != null){
					String targetURL=properties.get("zohoAuthenticationTargetURL")+criteria;
					logger.info("Target url ["+targetURL+"]");
					 url = new URL(targetURL);
				     conn = (HttpURLConnection) url.openConnection();
    			     conn.setRequestProperty("Authorization","Zoho-oauthtoken "+access_token);
				     conn.setRequestProperty("Content-Type","application/json");
				     conn.setRequestMethod("GET");
				     in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				     String output;
				     while ((output = in.readLine()) != null) {
				    	 responseFresh.append(output);
				        }
				}				
				 response.setContentType("text/plain");
                 response.setCharacterEncoding("UTF-8");
                 response.getWriter().write(responseFresh.toString());
			        in.close();
                 logger.info("Response after hit the url ["+responseFresh+"]");
                 System.out.println("response after fresh token ==>"+responseFresh);
  
	        }catch(Exception exception1)
        	  {
	        	exception1.printStackTrace();
        	  }
        	  }
        	 }
	}































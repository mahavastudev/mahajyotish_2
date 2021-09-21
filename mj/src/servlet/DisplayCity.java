package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.util.Constants;

/**
 * Servlet implementation class DisplayCity
 */
public class DisplayCity extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DisplayCity.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayCity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("inside doPost of DisplayCity class");
		UserProfileDao userDao = null;
		String countryCode = request.getParameter("countryCode");
		String stateCode = request.getParameter("stateCode");
                if(stateCode == null){
		    stateCode = "";
		}
		String cityCode = request.getParameter("cityCode").replaceAll(" ","");
		String proPath = Constants.PROPERTIES_PATH;
		proPath = proPath + "/kundliHttpserverNew.properties";
		Hashtable<String, String> properties = ReadPropertyFile.readPropery(proPath);
		String url=properties.getOrDefault("CITY_URL", "http://www.vedicreports.com/ParasharasLight?customerID=543249&validate=true&location=")+cityCode+","+stateCode+","+countryCode;
		String result = callURL(url);
                if(result.contains("error")){
			result = "";
		}
                if(result.contains("similarlist:")){
                    result = result.replace("similarlist:","");
                }
                if(result.contains("singlecity:")){
                    result = result.replace("singlecity:","");
                }
                if(result.contains("matchlist:")){
                    result = result.replace("matchlist:","");
                }
		PrintWriter out = null;
		try {


			userDao=new UserProfileDaoImpl();
			String country=userDao.getCountry(countryCode);
            String state=userDao.getState(stateCode,countryCode);
			String cityList=userDao.getCityList(country,state,cityCode);
			logger.info("cityList>> "+cityList+" result==>"+result);
			result=result+cityList;
			out = response.getWriter();
			out.println(result);
		} catch (Exception e) {
                        out.println("");  
			logger.error(e.toString());
		} finally {
			if(out != null){
				out.close();
			}
		}
	}

	public String callURL(String myURL) {
		logger.info("Requeted city URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		HttpURLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn =(HttpURLConnection) url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {

						sb.append((char) cp);

					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
		finally
		{
			try{
			if(in!=null)
				in.close();
			if(urlConn!=null)
				urlConn.disconnect();
			}catch(Exception e)
			{
					e.printStackTrace();
			}
		}

		return sb.toString();
	}
}

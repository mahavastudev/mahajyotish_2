package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.model.GenerateKundli;

/**
 * Servlet implementation class SaveKundli
 */
@WebServlet("/saveCity")
public class SaveCity extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SaveCity.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserProfileDao userDao = null;
		PrintWriter out = null;
		try{
			logger.info("inside do post");
			GenerateKundli generateKundli=new GenerateKundli();
			generateKundli.setCityCode(request.getParameter("cityCode"));
			generateKundli.setCity(request.getParameter("city"));
			generateKundli.setState(request.getParameter("state"));
			generateKundli.setCountry(request.getParameter("country"));
			generateKundli.setLatitude(request.getParameter("latitude"));
			generateKundli.setLongitude(request.getParameter("longitude"));
			logger.info("user kundli info " + generateKundli.toString());
		    userDao= new UserProfileDaoImpl();
		    out = response.getWriter();
		    String country=userDao.getCountry(generateKundli.getCountry());
		    String state=userDao.getState(generateKundli.getState(),generateKundli.getCountry());
		    if(userDao.isCityInfoExist(state,generateKundli.getCity())){
		    	out.println("This City  name already exist.");	
			}
			else{
				int retval = userDao.saveCityDetails(generateKundli,country,state);
				if (retval > 0) {
					out.println("City has been successfully saved");	
				} else {
					logger.error("error in save City info");
				}
			}
		}
		catch (Exception e) {
			logger.error("Exception in City  info "+e.toString());
		}
		finally{
			if(userDao != null){
				userDao = null;
			}
			if(out != null){
				out = null;
			}
		}	
	}

}


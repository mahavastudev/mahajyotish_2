package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.model.GenerateKundli;

/**
 * Servlet implementation class SaveKundli
 */
public class SaveKundli extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SaveKundli.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveKundli() {
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
			HttpSession session = request.getSession();
			int requestId=Integer.parseInt(request.getParameter("requestId")); 
			GenerateKundli generateKundli = new GenerateKundli();
			generateKundli.setName(request.getParameter("name"));
			generateKundli.setCity(request.getParameter("city"));
			generateKundli.setState(request.getParameter("state"));
			generateKundli.setCountry(request.getParameter("country"));
			generateKundli.setLatitude(request.getParameter("latitude"));
			generateKundli.setLongitude(request.getParameter("longitude"));
			generateKundli.setDob(request.getParameter("dob"));
			generateKundli.setTob(request.getParameter("tob"));
			generateKundli.setCityCode(request.getParameter("cityCode"));
			generateKundli.setTransitDob(request.getParameter("transitDob"));
			generateKundli.setTransitTob(request.getParameter("transitTob"));
			generateKundli.setIsTransitKundli(Integer.parseInt(request.getParameter("isTransitKundli")));
			generateKundli.setRemarks(request.getParameter("remarks"));
			generateKundli.setOccupation(request.getParameter("occupation"));
			generateKundli.setAccountId(request.getParameter("accountId"));
			generateKundli.setContactId(request.getParameter("contactId"));
			/* Added By Neha */
			generateKundli.setTransitLocation( Integer.parseInt(request.getParameter("transitLocation") == null ? "-1" : request.getParameter("transitLocation") ) );
			generateKundli.setTransitCountry(request.getParameter("transitCountryCode"));
			generateKundli.setTransitState(request.getParameter("transitStateCode"));
			generateKundli.setTransitCity(request.getParameter("transitCity"));
			generateKundli.setTransitLatitude(request.getParameter("transitLatitude"));
			generateKundli.setTransitLongitude(request.getParameter("transitLongitude"));
			
			logger.info(" country==>"+request.getParameter("transitCountryCode")+" state==>"+request.getParameter("transitStateCode")+" city=="+
					request.getParameter("transitCity")+" lat==>"+request.getParameter("transitLatitude")+" long==>"+request.getParameter("transitLongitude"));
			/* Added By Neha */
			generateKundli.setUserId(Integer.parseInt(request.getParameter("userId")));
			generateKundli.setRequestId(Integer.parseInt(request.getParameter("requestId")));
			generateKundli.setCcCode((String)session.getAttribute("ccCode"));
			logger.info("user kundli info " + generateKundli.toString());
		    	userDao= new UserProfileDaoImpl();
		    	out = response.getWriter();
		    	if(userDao.isUserKundliInfoExist(requestId)){
				
				int retval = userDao.updateKundliDetails(generateKundli);
                                if (retval > 0) {
                                        out.println("Kundli has been successfully Updated,"+requestId);
                                } else {
                                        logger.error("Error in Update kundli info");
                                }
		    	}
			else{
				int retval = userDao.saveDetails(generateKundli);
				if (retval > 0) {
					out.println("Kundli has been successfully saved,"+retval);	
				} else {
					logger.error("error in save kundli info");
				}
			}
		}
		catch (Exception e) {
			logger.error("Exception in save kundli info "+e.toString());
		}
		finally{
			if(userDao != null){
				userDao = null;
			}
			if(out != null){
				out.close();
			}
		}	
	}

}

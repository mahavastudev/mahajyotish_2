package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.telemune.dao.UserProfileDao;
import com.telemune.dao.UserProfileDaoImpl;
import com.telemune.model.StateData;

/**
 * Servlet implementation class DisplayState
 */

public class DisplayState extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DisplayState.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayState() {
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
		String stateName ="<select id='state' class='form-control' name='generateKundli.state'>"+
		"<option value='-1'>select</option>";
               // String stateName = "<s:select id='state' headerKey='-1' headerValue='select' list='stateLst' name='generateKundli.state'/>";
		String countryCode = request.getParameter("countryCode");
		UserProfileDao userDao = new UserProfileDaoImpl();
		logger.info("Country Code "+countryCode);
		List<StateData> stateLst =userDao.getStateList(countryCode);	
		if(!stateLst.isEmpty()){
			for(StateData stateData: stateLst){
				stateName = stateName+"<option value='"+stateData.getScode()+"'>"+stateData.getSname()+"</option>";
			}
			stateName = stateName+"</select>";
		}
                else{
                    stateName = "NA";
                }
		PrintWriter out = null;
		try {
			 out = response.getWriter();
			 out.println(stateName);
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if(out != null){
				out.close();
			}
			userDao = null;
		}
	}

}

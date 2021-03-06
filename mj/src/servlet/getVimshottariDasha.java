package servlet;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telemune.mobileAstro.CalculateVimshottari;

/**
 * Servlet implementation class displayPdf
 */
// @WebServlet("/getVimshottariDasha")
public class getVimshottariDasha extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getVimshottariDasha() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//String url = TSSJavaUtil.instance().getPropertyValue("FAQ_PATH");
		String strt = request.getParameter("startTime");
		String end = request.getParameter("endTime");
		String planet = request.getParameter("parent");
		String startPlanet = request.getParameter("planet");
		PrintWriter out = null;
	     
		CalculateVimshottari vimshottari= null;
		try {
			out = response.getWriter();
			vimshottari = new CalculateVimshottari();
			String json = (vimshottari.calculate(strt, end, planet, startPlanet)).toString();
			out.println(json);
	 		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
                   if(out != null){
                      out.close();
                   }
		}

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

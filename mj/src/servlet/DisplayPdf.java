package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class displayPdf
 */
// @WebServlet("/displayPdf")
public class DisplayPdf extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayPdf() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println(">>>>>>>>>>>>>>>  Inside DoGet");
		//String url = TSSJavaUtil.instance().getPropertyValue("FAQ_PATH");
		String url = request.getParameter("fileName");
		System.out.println(">>>>>>>>>>>>>>>  Inside DoGet" + url);
		ServletOutputStream out = null;
		try {

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			out = response.getOutputStream();
			FileInputStream in = new FileInputStream(url);
			int size = in.available();
			byte[] content = new byte[size];

			// for download

			/*
			 * String filename="Faq"; String headerKey = "Content-Disposition";
			 * String headerValue = String.format("attachment; filename=\"%s\"",
			 * filename); response.setHeader(headerKey, headerValue); //allow
			 * only for download
			 */
			//

			in.read(content);
			out.write(content);
			in.close();
			out.close();
			System.out.println("End of File");
		} catch (Exception e) {
			System.out.println("Exception inside save image servlet ");
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception in Writting Image On Web");
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

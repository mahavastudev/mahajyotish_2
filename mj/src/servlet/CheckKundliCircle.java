package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;


/**
 * Servlet implementation class SaveKundli
 */
public class CheckKundliCircle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CheckKundliCircle.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckKundliCircle() {
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
		PrintWriter out = null;
		try{
			logger.info("Inside CheckKundliCircle servlet ");
			out=response.getWriter();
			int userId =Integer.parseInt(request.getParameter("userId")); 
                        logger.info("userId>> "+request.getParameter("userId"));
			String fileName= getServletContext().getRealPath("/")+"tmpImages/"+userId+"_post.jpg";
			File file =new File(fileName);
			if(file.exists()){
				out.println("Y");
			}
			else{
				out.println("N");	
			}
		}
		catch (Exception e) {
			logger.error("Exception in CheckKundliCircle "+e.toString());
		}
		finally{
			if(out != null){
				out.close();
			}
		}	
	}

}

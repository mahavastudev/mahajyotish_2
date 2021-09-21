package servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Log4JInitServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(Log4JInitServlet.class);
        private static final long serialVersionUID = 1L;

        public void init(ServletConfig config) throws ServletException {
        	System.out.println("Log4JInitServlet is initializing log4j");
                String log4jLocation = config.getInitParameter("log4j-properties-location");

                ServletContext sc = config.getServletContext();

                if (log4jLocation == null) {
                	logger.info("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
                        BasicConfigurator.configure();
                } else {
                        String webAppPath = sc.getRealPath("/");
                        String log4jProp = webAppPath + log4jLocation;
                        File log4jFile = new File(log4jProp);
                        if (log4jFile.exists()) {
                        	logger.info("Initializing log4j with: " + log4jProp);
                                PropertyConfigurator.configure(log4jProp);
                        } else {
                        	logger.info("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
                                BasicConfigurator.configure();
                        }
                }
                super.init(config);
        }
}

/*
 * Main.java
 *
 * Created on June 18, 2010, 8:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.telemune.mobileAstro;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import org.apache.log4j.PropertyConfigurator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.*;
import org.apache.log4j.Logger;
import sendemail.*;
public class Main{

        private  static Logger logger = Logger.getLogger(Main.class);
	/** Creates a new instance of Main */

	public Main() {
	}

	/**
	 * @param args the command line arguments
	 */
	static int server_port=1;
	public static void main(String[] args) {
                 Hashtable<String,String> properties=ReadPropertyFile.readPropery("properties/httpserver.properties");

                     try{
                                Class.forName(properties.get("DRIVER").trim());
                        }
                        catch(Exception e)
                        {
                                e.printStackTrace();
                        }

			try
		       { 
			server_port=Integer.parseInt(properties.get("SERVER_PORT"));

			logger.info("Getting Server Port ||"+server_port);
		       }
			catch(Exception expset)
			{
			logger.info("ReadExce:"+expset.toString());
			}
			try{
/*
			String filePath = properties.get("FILE_PATH");
			PropertyConfigurator.configure("properties/astro_log4j.properties");

			InetSocketAddress addr = new InetSocketAddress(server_port);//Create socket add
			HttpServer server = HttpServer.create(addr, 0);//create server instance with queue size 0 with incoming connection
			//server.bind(addr,0);
			HttpContext context=server.createContext("/", new MyHandler()); //All req handle at that path
			//context.getFilters().add(new ParameterFilter());
			server.setExecutor(Executors.newCachedThreadPool());
			server.start();
			logger.info("Server is listening on port "+server_port );*/
			}
			catch(Exception serverex)
			{
			serverex.printStackTrace();
			}

		}	

}

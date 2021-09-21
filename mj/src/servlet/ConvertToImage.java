package servlet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.util.Constants;

public class ConvertToImage extends HttpServlet
{

	private Hashtable<String, String> properties = null;
	private String proPath = "";

	public ConvertToImage() {

		logger.info("Inside Constructor of ConvertToImage action Class.");
		proPath = Constants.PROPERTIES_PATH;
		proPath = proPath + "/kundliHttpserverNew.properties";
		properties = ReadPropertyFile.readPropery(proPath);
	}
	
	static Logger logger = Logger.getLogger(ConvertToImage.class);
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		FileOutputStream imageOutFile=null;
		BufferedImage bufferedImage;
		PrintWriter out = null;
		String path="";
		String transitImagePath="";			
		try
		{
			out=resp.getWriter();
			/*
			 * String imagePath= getServletContext().getRealPath("/");
			 * path=imagePath+"tmpImages/"; transitImagePath=imagePath+"tmpTransitImages/";
			 */
			path=properties.get("CIRCLE_IMAGE_PATH").trim();
			transitImagePath = properties.get("TRANSIT_CIRCLE_IMAGE_PATH").trim();
			int isTransitKundli=Integer.parseInt(req.getParameter("isTransitKundli"));

			logger.info("Inside ConvertToImage path :: transitImagePath :: "+path+" :: "+transitImagePath +" "+isTransitKundli);

			String imageDataString=req.getParameter("baseImage");
			String transitImageDataString=req.getParameter("baseTransitImage");
			//String imageName=req.getParameter("imageName");
			String kundliCircleName=req.getParameter("kundliCir");

			logger.info("kundliCircleName>> :: "+kundliCircleName);
	
			// Converting a Base64 String into Image byte array
			byte[] imageByteArray = decodeImage(imageDataString);
			             
			// Write a image byte array into file system
			imageOutFile = new FileOutputStream(path+kundliCircleName);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
			            
			//read image file
		        bufferedImage = ImageIO.read(new File(path+kundliCircleName));
					
			// create a blank, RGB, same width and height, and a white background
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,Color.WHITE,null);
					        	
			// write to jpeg file
	/*		Date date = new java.util.Date();
                	DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
			String ddate = dateFormat.format(date).replaceAll(" ", "");*/
			ImageIO.write(newBufferedImage, "jpg", new File(path+kundliCircleName));
			
			//Transit Image
			if(isTransitKundli==1) {
			imageByteArray = decodeImage(transitImageDataString);
			imageOutFile = new FileOutputStream(transitImagePath+kundliCircleName);
                        imageOutFile.write(imageByteArray);
                      	imageOutFile.close();
                      	bufferedImage = ImageIO.read(new File(transitImagePath+kundliCircleName));
                       	newBufferedImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                       	newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
                       	ImageIO.write(newBufferedImage, "jpg", new File(transitImagePath+kundliCircleName));

			}
			logger.info("image is write ["+(transitImagePath+kundliCircleName)+"]");
		}    
		catch (Exception e) {
                	e.printStackTrace();
              	}
		finally{
			 imageOutFile=null;
			
		}
	}
/*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        }	
*/	
	
	 public static String encodeImage(byte[] imageByteArray) {
	        return Base64.encodeBase64URLSafeString(imageByteArray);
	    }
	     
	    /**
	     * Decodes the base64 string into byte array
	     *
	     * @param imageDataString - a {@link java.lang.String}
	     * @return byte array
	     */
	    public static byte[] decodeImage(String imageDataString) {
	        return Base64.decodeBase64(imageDataString);
	    }
}
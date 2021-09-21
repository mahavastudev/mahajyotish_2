package com.telemune.mobileAstro;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.telemune.model.UserInfo;
import com.telemune.model.UserLinkDescription;
import com.telemune.util.Constants;

public class KundliMail {

	private static Logger logger = Logger.getLogger("KundliMail");
        private Hashtable<String, String> properties = null;
        private String proPath = "";
        String mailFrom ="";
        String mailPassword="";
        
        String port="";
        String host="";
        Session session=null;
        Properties props=null;
         
	public KundliMail() {
        	proPath = Constants.PROPERTIES_PATH;
                proPath = proPath + "/kundliHttpserverNew.properties";
                properties = ReadPropertyFile.readPropery(proPath);

                mailFrom = properties.get("MAIL_USERNAME").trim();
                mailPassword = properties.get("MAIL_PASSWORD").trim();
                port = properties.get("PORT").trim();
                host= properties.get("MAIL_HOST_SERVER").trim();
                
                props = new Properties();  
          	   props.put("mail.smtp.host",host);  
          	   props.put("mail.smtp.auth", "true");
          	 session = Session.getDefaultInstance(props,  
             	    new javax.mail.Authenticator() {  
             	      protected PasswordAuthentication getPasswordAuthentication() {  
             	    return new PasswordAuthentication(mailFrom,mailPassword);  
             	      }  
             	    });
        }

	public void loginInfoMailSending(String mailSubject, String[] mailTo, UserInfo userInfo)
        {

		/*
		 * MailContent mailcontent = new MailContent();
		 * mailcontent.setMailFrom(mailFrom); mailcontent.setMailPassword(mailPassword);
		 * mailcontent.setMailTo(mailTo); mailcontent.setMailSubject(mailSubject);
		 * 
		 * 
		 * if( port!=null) {
		 * 
		 * mailcontent.setSmtpPort( port ); }
		 * 
		 * String msgContent = properties.get("LOGIN_INFO_TEXT_CONTENT");
		 * msgContent=msgContent.replaceAll("USER", userInfo.getUname());
		 * msgContent=msgContent.replaceAll("PASSWORD", userInfo.getUpassword());
		 * msgContent=msgContent.replaceAll("LOGINNAME",userInfo.getEmail());
		 * mailcontent.setMailHtmlText(msgContent); MailSender mailsender = new
		 * MailSender(mailcontent); mailsender.sendMail();
		 */                
                
		try
		{
            	logger.info("Sending Login Mail Info");
    			
   		     MimeMessage message = new MimeMessage(session);  
   		     message.setFrom(new InternetAddress(mailFrom));  
   		    
   		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo[0]));  
   		     message.setSubject(mailSubject);  
   		    
   		     String msgContent = properties.get("LOGIN_INFO_TEXT_CONTENT");
                
                msgContent=msgContent.replaceAll("USER", userInfo.getUname());
       		  msgContent=msgContent.replaceAll("PASSWORD", userInfo.getUpassword());
       		  msgContent=msgContent.replaceAll("LOGINNAME",userInfo.getEmail());   
   		     //message.setText(msgContent);
              //  String[] bits = mailFileDir[0].split("/");
   		    // String lastOne = bits[bits.length-1];
   		    // String   file=mailFileDir[0];
   		     BodyPart messageBodyPart1 = new MimeBodyPart();  
   		    // messageBodyPart1.setText(msgContent);    
   		  logger.info("msgContent : "+msgContent);
   		     messageBodyPart1.setContent(msgContent, "text/html");//setText(msgContent);
   		     MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
   		     
   		     
   		       
   		     //DataSource source = new FileDataSource(file);  
   		     //messageBodyPart2.setDataHandler(new DataHandler(source));  
   		       
   		    // messageBodyPart2.setFileName(lastOne);
   		     Multipart multipart = new MimeMultipart();  
   		     multipart.addBodyPart(messageBodyPart1);  
   		    // multipart.addBodyPart(messageBodyPart2);  
   		   
   		     message.setContent(multipart );  
   		     
   		     Transport.send(message);  
   		     System.out.println("message sent successfully...");  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
            
        }

	public void kundliMailSending(String mailSubject, String[] mailTo,String[] mailFileDir,String uNAme)
        {
		try
		{
			logger.info("Sending Mail using new Auth");
			
		     MimeMessage message = new MimeMessage(session);  
		     message.setFrom(new InternetAddress(mailFrom));  
		    
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo[0]));  
		     message.setSubject(mailSubject);  
		    
		     String msgContent = properties.get("kUNDLI_TEXT_CONTENT");
             msgContent=msgContent.replaceAll("USER", uNAme);
             msgContent=msgContent.replaceAll("Kunldi Name", uNAme);
             
		     //message.setText(msgContent);
             String[] bits = mailFileDir[0].split("/");
		     String lastOne = bits[bits.length-1];
		     String   file=mailFileDir[0];
		     BodyPart messageBodyPart1 = new MimeBodyPart();  
		    // messageBodyPart1.setText(msgContent);    
		     
		     messageBodyPart1.setContent(msgContent, "text/html");//setText(msgContent);
		     MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		     
		     
		     logger.info("msgContent : "+msgContent);
               
		     DataSource source = new FileDataSource(file);  
		     messageBodyPart2.setDataHandler(new DataHandler(source));  
		       
		     messageBodyPart2.setFileName(lastOne);
		     Multipart multipart = new MimeMultipart();  
		     multipart.addBodyPart(messageBodyPart1);  
		     multipart.addBodyPart(messageBodyPart2);  
		   
		     message.setContent(multipart );  
		     
		     Transport.send(message);  
		     System.out.println("message sent successfully...");  
		   
         
                logger.info("mail sent to user");
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error("unable to send email to user"+Arrays.toString(mailTo));
		}
        }
	
	public void kundliMailSending(String mailSubject, String[] mailTo,String[] mailFileDir,String uNAme,boolean withOutLoginFloag)
    {
	try
	{
		logger.info("Sending Mail using new Auth");	
	    MimeMessage message = new MimeMessage(session);  
	     message.setFrom(new InternetAddress(mailFrom));  
			mailSubject=properties.get("withoutlogin.mail.subject").trim()+" "+uNAme;
	     message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo[0]));  
	     message.setSubject(mailSubject);  
	    
	     String msgContent = properties.get("withoutlogin.mail.CONTENT");
        logger.info("msgContent : "+msgContent);
        msgContent=msgContent.replaceAll("USER", uNAme);
        msgContent=msgContent.replaceAll("Kunldi Name", uNAme);
        
        
        String[] bits = mailFileDir[0].split("/");
	     String fileName = bits[bits.length-1];
	     String   file=mailFileDir[0];
	     //message.setContent("msgContent", "text/html");//msgContent);  
	     BodyPart messageBodyPart1 = new MimeBodyPart();  
	     messageBodyPart1.setContent(msgContent, "text/html");//setText(msgContent);    
	    
	     MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
	     
	       
	     DataSource source = new FileDataSource(file);  
	     messageBodyPart2.setDataHandler(new DataHandler(source));  
	     messageBodyPart2.setFileName(fileName);  
	     
	     Multipart multipart = new MimeMultipart();  
	     multipart.addBodyPart(messageBodyPart1);  
	     multipart.addBodyPart(messageBodyPart2);  
	   
	     message.setContent(multipart );  
	     
	     Transport.send(message);  
	     System.out.println("message sent successfully...");  
	 
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		
			mailSubject=properties.get("withoutlogin.mail.subject").trim()+" "+uNAme;
            logger.info("################# :: "+mailSubject+"   "+Arrays.toString(mailTo)+"    "+Arrays.toString(mailFileDir)+"   "+uNAme);
            String mailFrom = properties.get("MAIL_USERNAME").trim();
            String mailPassword = properties.get("MAIL_PASSWORD").trim();
            logger.info("mailFrom : "+mailFrom);
            logger.info("mailPassword : "+mailPassword);
            MailContent mailcontent = new MailContent();
            mailcontent.setMailFrom(mailFrom);
            mailcontent.setMailPassword(mailPassword);
            mailcontent.setMailTo(mailTo);                
            mailcontent.setMailSubject(mailSubject);

            if( properties.get("PORT")!=null)
    	    {
            	logger.info("port : "+properties.get("PORT"));
    		    mailcontent.setSmtpPort( properties.get( "PORT" ).trim() );
    	    }

            String msgContent = properties.get("withoutlogin.mail.CONTENT");
            logger.info("msgContent : "+msgContent);
            msgContent=msgContent.replaceAll("USER", uNAme);
            msgContent=msgContent.replaceAll("Kunldi Name", uNAme);
            mailcontent.setMailHtmlText(msgContent);
            mailcontent.setMailFileDir(mailFileDir);
            MailSender mailsender = new MailSender(mailcontent);
            mailsender.sendMail();
            */
            logger.info("################# :: mail sent to user");
	}catch(Exception e)
	{
		e.printStackTrace();
		logger.error("unable to send email to user"+Arrays.toString(mailTo));
	}
    }
	
	public void mailSending(String mailSubject, String[] mailTo, UserInfo userInfo)
        {

		/*
		 * String mailFrom = properties.get("MAIL_USERNAME").trim(); String mailPassword
		 * = properties.get("MAIL_PASSWORD").trim(); MailContent mailcontent = new
		 * MailContent(); mailcontent.setMailFrom(mailFrom);
		 * mailcontent.setMailPassword(mailPassword); mailcontent.setMailTo(mailTo);
		 * mailcontent.setMailSubject(mailSubject);
		 * 
		 * if( properties.get("PORT")!=null) { mailcontent.setSmtpPort( properties.get(
		 * "PORT" ).trim() ); }
		 * 
		 * String msgContent = properties.get("FORGOT_PASS_TEXT_CONTENT");
		 * msgContent=msgContent.replaceAll("USER", userInfo.getUname());
		 * msgContent=msgContent.replaceAll("PASSWORD", userInfo.getUpassword());
		 * mailcontent.setMailHtmlText(msgContent); MailSender mailsender = new
		 * MailSender(mailcontent); mailsender.sendMail();
		 */
                
                
                try
        		{
                    	logger.info("Sending Login Mail Info");
            			
           		     MimeMessage message = new MimeMessage(session);  
           		     message.setFrom(new InternetAddress(mailFrom));  
           		    
           		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo[0]));  
           		     message.setSubject(mailSubject);  
           		    
           		     String msgContent = properties.get("FORGOT_PASS_TEXT_CONTENT");
                        
                        msgContent=msgContent.replaceAll("USER", userInfo.getUname());
               		  msgContent=msgContent.replaceAll("PASSWORD", userInfo.getUpassword());
               		  BodyPart messageBodyPart1 = new MimeBodyPart();  
               		logger.info("msgContent : "+msgContent);
           		     
           		     messageBodyPart1.setContent(msgContent, "text/html");//setText(msgContent);
           		     MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
           		     
           		     
           		     
           		     Multipart multipart = new MimeMultipart();  
           		     multipart.addBodyPart(messageBodyPart1);  
           		      
           		   
           		     message.setContent(multipart );  
           		     
           		     Transport.send(message);  
           		     System.out.println("message sent successfully...");  
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
                    

                
                
        }
	
	public void activationLinkMail(UserLinkDescription userDetail,String mailSubject, String mailTo,String tokenId)
        {

		/*
		 * final String username = "no-reply@mahavastu.com"; final String password =
		 * "patanahi.com";
		 * 
		 * Properties props = new Properties(); props.put("mail.smtp.auth", "true");
		 * props.put("mail.smtp.starttls.enable", "true"); props.put("mail.smtp.host",
		 * "smtp.gmail.com"); props.put("mail.smtp.port", "587");
		 * 
		 * 
		 * 
		 * Session session = Session.getDefaultInstance(props, new Authenticator() {
		 * protected PasswordAuthentication getPasswordAuthentication() { return new
		 * PasswordAuthentication(username,password); } }); try {
		 * 
		 * Message mimeMessage = new MimeMessage(session); mimeMessage.setFrom(new
		 * InternetAddress(username));
		 * mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(
		 * mailTo)); mimeMessage.setSubject(mailSubject); String msgContent =
		 * properties.get("ACTIVATE_USER_TEXT_CONTENT"); String activationLink =
		 * properties.get("USER_ACTIVATION_LINK");
		 * activationLink=activationLink+tokenId;
		 * msgContent=msgContent.replaceAll("LINK", activationLink);
		 * mimeMessage.setContent(msgContent, "text/html"); Transport.send(mimeMessage);
		 * logger.info("Email Sent Successfully to - "+mailTo);
		 * 
		 * 
		 * 
		 * 
		 * } catch (MessagingException e) { e.printStackTrace(); }
		 */
		try
        		{
                    	logger.info("Sending Login Mail Info");
            			
           		     MimeMessage message = new MimeMessage(session);  
           		     message.setFrom(new InternetAddress(mailFrom));  
           		    
           		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));  
           		     message.setSubject(mailSubject);  
           		    
           		     String msgContent = properties.get("ACTIVATE_USER_TEXT_CONTENT");
                       
                        
                        String activationLink = userDetail.getActivationLink();
                         activationLink=activationLink+tokenId;
                      msgContent=msgContent.replaceAll("LINK", activationLink);
                      msgContent=msgContent.replaceAll("USER", userDetail.getClientName());
               		  BodyPart messageBodyPart1 = new MimeBodyPart();  
               		 logger.info("msgContent : "+msgContent);	
           		     
           		     messageBodyPart1.setContent(msgContent, "text/html");//setText(msgContent);
           		     MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
           		     
           		     
           		     
           		     Multipart multipart = new MimeMultipart();  
           		     multipart.addBodyPart(messageBodyPart1);  
           		      
           		   
           		     message.setContent(multipart );  
           		     
           		     Transport.send(message);  
           		     System.out.println("message sent successfully...");  
        		}
        		catch(Exception e)
        		{
        			e.printStackTrace();
        		}
                
        }

}


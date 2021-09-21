package com.telemune.action;

import java.util.Hashtable;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.telemune.mobileAstro.ReadPropertyFile;
import com.telemune.model.UserInfo;
import com.telemune.util.Constants;

import sendemail.MailContent;
import sendemail.MailSender;

public class KundliMail_bkp {

	private static Logger logger = Logger.getLogger(KundliMail_bkp.class);
	private Hashtable<String, String> properties = null;
	private String proPath = "";

	public KundliMail_bkp() {

		logger.info("Inside Constructor of KundliMail action Class.");
		proPath = Constants.PROPERTIES_PATH;
		proPath = proPath + "/kundliHttpserverNew.properties";
		properties = ReadPropertyFile.readPropery(proPath);
	}

	public void loginInfoMailSending(String mailSubject, String[] mailTo, UserInfo userInfo) {

		logger.info("Inside loginInfoMailSending method of KundliMail action class.");

		try {
			String mailFrom = properties.get("MAIL_USERNAME").trim();
			String mailPassword = properties.get("MAIL_PASSWORD").trim();

			MailContent mailcontent = new MailContent();
			mailcontent.setMailFrom(mailFrom);
			mailcontent.setMailPassword(mailPassword);
			mailcontent.setMailTo(mailTo);
			mailcontent.setMailSubject(mailSubject);
			logger.info("PORT------>>>>" + properties.get("PORT"));

			if (properties.get("PORT") != null) {

				mailcontent.setSmtpPort(properties.get("PORT").trim());
			}

			String msgContent = properties.get("LOGIN_INFO_TEXT_CONTENT");
			msgContent = msgContent.replaceAll("USER", userInfo.getUname());
			msgContent = msgContent.replaceAll("PASSWORD", userInfo.getUpassword());
			msgContent = msgContent.replaceAll("LOGINNAME", userInfo.getEmail());
			mailcontent.setMailHtmlText(msgContent);
			MailSender mailsender = new MailSender(mailcontent);
			mailsender.sendMail();
		} catch (Exception e) {
			logger.error(
					"Inside catch block. Exception at loginInfoMailSending method of KundliMail action Class. Exception  : "
							+ e.toString());
			e.printStackTrace();
		}
	}

	public void kundliMailSending(String mailSubject, String[] mailTo, String[] mailFileDir, String uNAme) {
		logger.info("Inside kundliMailSending method of KundliMail action class with mailSubject:[" + mailSubject
				+ "],  mailTo:[" + mailTo + "],  mailFileDir:[" + mailFileDir + "],  uName:[" + uNAme + "].");
		try {
			String mailFrom = properties.get("MAIL_USERNAME").trim();
			String mailPassword = properties.get("MAIL_PASSWORD").trim();
			MailContent mailcontent = new MailContent();
			mailcontent.setMailFrom(mailFrom);
			mailcontent.setMailPassword(mailPassword);
			mailcontent.setMailTo(mailTo);
			mailcontent.setMailSubject(mailSubject);

			if (properties.get("PORT") != null) {
				mailcontent.setSmtpPort(properties.get("PORT").trim());
			}

			String msgContent = properties.get("kUNDLI_TEXT_CONTENT");
			msgContent = msgContent.replaceAll("USER", uNAme);
			msgContent = msgContent.replaceAll("Kunldi Name", uNAme);
			mailcontent.setMailHtmlText(msgContent);
			mailcontent.setMailFileDir(mailFileDir);
			MailSender mailsender = new MailSender(mailcontent);
			mailsender.sendMail();
		} catch (Exception e) {
			logger.error(
					"Inside catch block. Exception at kundliMailSending method of KundliMail action Class. Exception  : "
							+ e.toString());
			e.printStackTrace();
		}
	}

	public void mailSending(String mailSubject, String[] mailTo, UserInfo userInfo) {

		logger.info("Inside mailSending method of KundliMail action class.");
		try {
			String mailFrom = properties.get("MAIL_USERNAME").trim();
			String mailPassword = properties.get("MAIL_PASSWORD").trim();
			MailContent mailcontent = new MailContent();
			mailcontent.setMailFrom(mailFrom);
			mailcontent.setMailPassword(mailPassword);
			mailcontent.setMailTo(mailTo);
			mailcontent.setMailSubject(mailSubject);

			if (properties.get("PORT") != null) {
				mailcontent.setSmtpPort(properties.get("PORT").trim());
			}

			String msgContent = properties.get("FORGOT_PASS_TEXT_CONTENT");
			msgContent = msgContent.replaceAll("USER", userInfo.getUname());
			msgContent = msgContent.replaceAll("PASSWORD", userInfo.getUpassword());
			mailcontent.setMailHtmlText(msgContent);
			MailSender mailsender = new MailSender(mailcontent);
			mailsender.sendMail();
		} catch (Exception e) {
			logger.error(
					"Inside catch block. Exception at mailSending method of KundliMail action Class. Exception  : "
							+ e.toString());
			e.printStackTrace();
		}
	}

	public void activationLinkMail(String mailSubject, String mailTo, String tokenId) {

		logger.info("Inside activationLinkMail method of KundliMail action class.");
		final String username = "noreply@mahavastu.com";
		final String password = "mahavastu";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {

			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(username));
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			mimeMessage.setSubject(mailSubject);
			String msgContent = properties.get("ACTIVATE_USER_TEXT_CONTENT");
			String activationLink = properties.get("USER_ACTIVATION_LINK");
			activationLink = activationLink + tokenId;
			msgContent = msgContent.replaceAll("LINK", activationLink);
			mimeMessage.setContent(msgContent, "text/html");
			Transport.send(mimeMessage);
			logger.info("Email Sent Successfully to - " + mailTo);

		} catch (MessagingException e) {
			logger.error(
					"Inside catch block. Exception at activationLinkMail method of KundliMail action Class. Exception  : "
							+ e.toString());
			e.printStackTrace();
		}

	}

}

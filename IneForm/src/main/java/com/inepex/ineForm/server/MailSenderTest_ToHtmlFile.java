package com.inepex.ineForm.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

public class MailSenderTest_ToHtmlFile {
		
	private static int emailCounter = 0;
	
	private DecimalFormat threeNums = new DecimalFormat("000");
	
	final Properties mailProps;
	
	public MailSenderTest_ToHtmlFile(Properties mailProps) {
		this.mailProps = mailProps;
	}

	public void sendMail(String message, String subject, boolean isHtml,
			String emailAddress) {
		
		try {
			String fileName = (String) mailProps.get("output.folder") 
					+ (String) mailProps.get("email.prefix") 
					+ threeNums.format(emailCounter++) 
					+ " - " + subject.replace("/", "[per]")
					+ ".html";
			FileWriter fw = new FileWriter(new File(fileName));
			
			fw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
					"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"> " +
					"<html xmlns=\"http://www.w3.org/1999/xhtml\"> " +
					"<head>" +
					"<title>");
				
			fw.write("To: " + emailAddress);
			
			fw.write("</title>" +
					"<meta http-equiv=\"content-type\"" +
					"content=\"text/html;charset=utf-8\" />" +
					"</head>" +
					"<body>");
			
			fw.write("To: " + emailAddress);
			fw.write("<br> Subject: " + subject + "<hr>");
				
			
			fw.write(message);
			
			fw.write("</body>" +
					"</html>");
			

			fw.flush();
			fw.close();
			
			System.out.println("Message to " + emailAddress + " saved to file '" + fileName + "'" );
						
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

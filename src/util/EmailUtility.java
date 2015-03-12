package util;

import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * A utility class for sending e-mail messages
 * @author www.codejava.net
 *
 */
public class EmailUtility {
    public static void sendEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            
            MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
      
		//SSL verison
        properties.put("mail.smtp.host", host); //SMTP Host
        properties.put("mail.smtp.socketFactory.port", port); //SSL Port
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        properties.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        properties.put("mail.smtp.port", port); //SMTP Port
        
        //TLS version
        /* properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        for sockets ad the code below
        properties.put("mail.smtp.socks.host", "192.168.1.250");
        properties.put("mail.smtp.socks.port", "465");*/
       
        
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
       
 
        Session session = Session.getInstance(properties, auth);
        session.setDebug(true);
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        //msg.setText(message);
        msg.setContent(message, "text/html");
 
        // sends the e-mail
        Transport.send(msg);
 
    }
}
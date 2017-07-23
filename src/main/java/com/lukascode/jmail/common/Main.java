package com.lukascode.jmail.common;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;

import com.lukascode.jmail.common.dao.JMailDatabaseCreator;
import com.lukascode.jmail.views.MessageViewer;
import com.lukascode.jmail.views.StartFrame;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Store;

class MailHelper {
	public MailHelper() {
		
	}
	
	public void sendEmail() {
		final String username = "admin@gmail.com";
		//final String password = "pass";
		final String host = "localhost";

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		//props.put("mail.smtp.auth", "true");
		//TLS
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "587");
//		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		//SSL
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "465");
//		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		

//		Session session = Session.getInstance(props,
//		  new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(username, password);
//			}
//		  });
		Session session = Session.getDefaultInstance(props);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@example.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("mark@example.com"));
			message.setSubject("Testing Subject, message from VirtualBox");
			message.setText("Dear Mr Lukas4x witthout SSL, \nThis is only test message generated automatically.\nPlease do not answer.");

			Transport.send(message);
			
			
			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveEmailPOP(String pop3Host, String storeType, String user, String password) {
		try {
			//get the session object
			Properties props = new Properties();
			props.put("mail.store.protocol", "pop3s");
			props.put("mail.pop3.ssl.enable", "true");
//			props.put("mail.pop3.host", pop3Host);
//			props.put("mail.pop3.port", "995");
//			props.put("mail.pop3.auth", "true");
			//props.put("mail.pop3.starttls.enable", "true");
			//props.put("mail.smtp.ssl.trust", "pop.gmail.com");
			Session emailSession = Session.getDefaultInstance(props);
			
			//create the pop3 store object and connect with the pop server 
			POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);
			emailStore.connect(pop3Host, user, password);
			
			System.out.println("connected");
			
			//create the folder object and open it 
			Folder emailFolder = emailStore.getDefaultFolder().getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
			//retrieve the messages from the folder in an array and print it 
			Message[] messages = emailFolder.getMessages();
			for(int i=0; i<messages.length; ++i) {
				Message message = messages[i];
				System.out.println("---------------------------------");  
			    System.out.println("Email Number " + (i + 1));  
			    System.out.println("Subject: " + message.getSubject());  
			    System.out.println("From: " + message.getFrom()[0]);  
			    System.out.println("Text: " + message.getContent().toString());
			}
			
			//close the store and folder objects
			emailFolder.close(false);
			emailStore.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private MessageViewer msgViewer = null;
	private JFrame frame = null;
	public void receiveEmailIMAP(String user, String password) {
		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");
			props.put("mail.pop3.ssl.enable", "true");
			
			
			Session emailSession = Session.getInstance(props);
			
			//also works with it 
//			Session emailSession = Session.getInstance(props,
//					  new javax.mail.Authenticator() {
//						protected PasswordAuthentication getPasswordAuthentication() {
//							return new PasswordAuthentication(user, password);
//						}
//					  });
			
			Store store = emailSession.getStore("imaps");
			store.connect("imap.gmail.com", user, password);
			IMAPFolder folder = (IMAPFolder) store.getFolder("[Gmail]");
			Folder[] folders = folder.list();
			for(int i=0; i<folders.length; ++i) {
				System.out.println(folders[i].getName());
			}
			
			if(true) return;
			
			folder.open(Folder.READ_ONLY);
			Message[] messages = folder.getMessages();
		
			for(int i=0; i<messages.length; ++i) {
					System.out.println("*****************************************************************************");
		            System.out.println("MESSAGE " + (i + 1) + ":");
		            Message msg =  messages[i];
		            String subject = msg.getSubject();

		            System.out.println("Subject: " + subject);
		            System.out.println("From: " + msg.getFrom()[0]);
		            System.out.println("To: "+msg.getAllRecipients()[0]);
		            System.out.println("Date: "+msg.getReceivedDate());
		            System.out.println("Size: "+msg.getSize());
		            System.out.println(msg.getFlags());
		            System.out.println("Body: \n"+ msg.getContent());
		            System.out.println(msg.getContentType());
			}
			//close the store and folder objects
			folder.close(false);
			store.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	
}

public class Main {
	
	public static Logger logger;
	
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", 
				"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");
		logger = Logger.getLogger("com.lukascode.jmail");
		logger.setLevel(Level.INFO);
		logger.setUseParentHandlers(false);
		Handler handler = null;
		try {
			handler = new FileHandler("%h/jmail.log", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		handler.setLevel(Level.INFO);
		handler.setFormatter(new SimpleFormatter());
		logger.addHandler(handler);
		
		handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		
		logger.addHandler(handler);
		
	}
	
	

	public static void main(String[] args) {
		logger.log(Level.INFO, "NEW APPLICATION LAUNCH");
		JMailDatabaseCreator.createInstance("jmail.db");
		JMailDatabaseCreator.getInstance().createDatabase();
		EventQueue.invokeLater(()->{
			StartFrame.main(args);
		});
		//new JMailDatabaseCreator("test.db");
		//new MailHelper().receiveEmailIMAP("***REMOVED***", "pass");
//		AccountConfiguration ac = new AccountConfiguration();
//		AccountConfigurationDAO acdao = new AccountConfigurationDAO();
//		ac.setId(0);
//		ac.setEmail("alamakota@gmail.com");
//		ac.setPassword("password");
//		ac.setSavePassword(true);
//		ac.setSmtpServerName("smtp.gmail.com");
//		ac.setSmtpServerPort("465");
//		ac.setSmtpServerSSL(true);
//		ac.setImapServerTLS(false);
//		ac.setImapServerName("imap.gmail.com");
//		ac.setImapServerPort("993");
//		ac.setImapServerSSL(true);
//		ac.setImapServerTLS(false);
//		ac.setLastLogin(LocalDateTime.now());
//		if(acdao.insert(ac))
//			logger.log(Level.INFO, "inserted");
			
		//MailHelper mh = new MailHelper();
		//mh.receiveEmailIMAP("***REMOVED***", "***REMOVED***");

			
	}

}

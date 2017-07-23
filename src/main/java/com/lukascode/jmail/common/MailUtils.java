package com.lukascode.jmail.common;



import java.util.Properties;

import javax.mail.*;

import com.sun.mail.imap.IMAPFolder;

public class MailUtils {
	
	private AccountConfiguration ac = null;
	
	public MailUtils(AccountConfiguration ac) {
		this.ac = ac;
	}
	
	public Folder getFolder() {
		Folder folder = null;
		try {
			Session session = getIMAPSession();
			Store store = null;
			if(ac.isImapServerSSL())
				store = session.getStore("imaps");
			else store = session.getStore("imap");
			store.connect(ac.getImapServerName(), ac.getEmail(), ac.getPassword());
			folder = store.getFolder("");
		} catch(Exception e) {
			Main.logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return folder;
	}
	
	private Session getSMTPSession() {
		Session session = null;
		Properties props = new Properties();
		props.put("mail.smtp.host", ac.getSmtpServerName());
		props.put("mail.smtp.port", ac.getSmtpServerPort());
		if(ac.isSmtpServerSSL()) {
			props.put("mail.smtp.ssl.trust", ac.getSmtpServerName());
			props.put("mail.smtp.socketFactory.port", ac.getSmtpServerPort());
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		}
		if(ac.isSmtpServerTLS())
			props.put("mail.smtp.starttls.enable", "true");
		
		if(ac.isImapServerSSL() || ac.isImapServerTLS() || ac.isSmtpServerSSL() || ac.isSmtpServerTLS()) {
			props.put("mail.smtp.auth", "true");
			session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(ac.getEmail(), ac.getPassword());
				}
			});
		} else session = Session.getDefaultInstance(props);
		return session;
	}
	
	private Session getIMAPSession() {
		Session session = null;
		Properties props = new Properties();
		if(ac.isImapServerSSL()) {
			props.put("mail.store.protocol", "imaps");
			props.put("mail.pop3.ssl.enable", "true");
		} else props.put("mail.store.protocol", "imap");
		session = Session.getInstance(props);
		return session;
	}
	
}

package com.lukascode.jmail.common;



import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import com.lukascode.jmail.common.StringTree.Node;
import com.lukascode.jmail.common.dao.EMessage;
import com.sun.mail.imap.IMAPFolder;

public class MailUtils {
	
	private AccountConfiguration ac = null;
	
	public MailUtils(AccountConfiguration ac) {
		this.ac = ac;
	}
	
	public StringTree getFolders() {
		StringTree stree = null;
		try {
			Session session = getIMAPSession();
			Store store = null;
			if(ac.isImapServerSSL())
				store = session.getStore("imaps");
			else store = session.getStore("imap");
			store.connect(ac.getImapServerName(), ac.getEmail(), ac.getPassword());
			Folder folder = store.getDefaultFolder();
			stree = new StringTree(ac.getEmail());
			buildFoldersTree(stree.root, folder);
		} catch(Exception e) {
			Main.logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return stree;
	}
	
	public List<EMessage> getMessages(String folder) {
		List<EMessage> messages = new ArrayList<>();
		Session session = getIMAPSession();
		Store store = null;
		IMAPFolder _folder = null;
		try {
			if(ac.isImapServerSSL())
				store = session.getStore("imaps");
			else store = session.getStore("imap");
			store.connect(ac.getImapServerName(), ac.getEmail(), ac.getPassword());
			 _folder = (IMAPFolder) store.getFolder(folder);
			_folder.open(Folder.READ_WRITE);
			Message[] msgs = _folder.getMessages();
			for(Message m: msgs) messages.add(createEMessage(m));
		} catch(Exception e) {
			Main.logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(_folder != null) _folder.close(false);
				if(store != null) store.close();
			} catch(Exception e) {
				Main.logger.severe(e.getMessage());
				e.printStackTrace();
			}
		}
		return messages;
	}
	
	private EMessage createEMessage(Message m) {
		EMessage result = new EMessage();
		try {
			result.setSubject(m.getSubject());
			result.setDate(m.getReceivedDate());
			Address[] addrs = null;
			addrs = m.getAllRecipients();
			String email = addrs == null ? null : ((InternetAddress) addrs[0]).getAddress();
			result.setTo(email);
			addrs = m.getFrom();
			email = addrs == null ? null : ((InternetAddress) addrs[0]).getAddress();
			result.setFrom(email);
		} catch(Exception e) {
			Main.logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	private void buildFoldersTree(Node root, Folder folder) throws MessagingException {
		Folder[] folders = folder.list();
		for(Folder f: folders) {
			Node node = root.addChild(f.getName());
			buildFoldersTree(node, f);
		}
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
			props.put("mail.imap.ssl.enable", "true");
			//props.put("mail.imaps.ssl.trust", ac.getSmtpServerName()); 
			props.put("mail.imaps.ssl.trust", "*"); 
		} else props.put("mail.store.protocol", "imap");
		if(ac.isImapServerTLS())
			props.put("mail.imap.starttls.enable", "true");
		session = Session.getInstance(props);
		return session;
	}
	
}

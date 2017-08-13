package com.lukascode.jmail.common;



import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
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
	
	public boolean credentialsCorrect() {
		boolean result = true;
		Session session = getIMAPSession();
		Store store = null;
		try {
			if(ac.isImapServerSSL())
				store = session.getStore("imaps");
			else store = session.getStore("imap");
			store.connect(ac.getImapServerName(), ac.getEmail(), ac.getPassword());
		} catch(MessagingException e) {
			result = false;
		} finally {
			if(store != null)
				try {
					store.close();
				} catch (MessagingException e) {
					Main.logger.log(Level.SEVERE, "exception was thrown", e);
				}
		}
		return result;
	}
	
	public StringTree getFolders() {
		StringTree stree = null;
		Store store = null;
		try {
			Session session = getIMAPSession();
			if(ac.isImapServerSSL())
				store = session.getStore("imaps");
			else store = session.getStore("imap");
			store.connect(ac.getImapServerName(), ac.getEmail(), ac.getPassword());
			Folder folder = store.getDefaultFolder();
			stree = new StringTree(ac.getEmail());
			buildFoldersTree(stree.root, folder);
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, "exception was thrown", e);
			e.printStackTrace();
			throw new JMailException("Error while collecting data from server (folder tree)\n" + e.getMessage());
		} finally {
			if(store != null)
				try {
					store.close();
				} catch (MessagingException e) {
					Main.logger.log(Level.SEVERE, "exception was thrown", e);
				}
		}
		return stree;
	}
	
	@SuppressWarnings("unchecked")
	public List<EMessage> getMessages(String folder) {
		Runnable<Object> runnable = new Runnable<Object>() {
			@Override
			public Object run(Object arg1, Object arg2) throws Exception {
				List<EMessage> messages = new ArrayList<>();
				Message[] msgs = ((IMAPFolder) arg1).getMessages();
				for(Message m: msgs) { 
					messages.add(new EMessage(m));
				}
				return messages;
			}
		};
		return (List<EMessage>) prepareForImap(runnable, folder, null);
	}
	
	public void removeMessage2(Message m) {
		
		Runnable<Object> runnable = new Runnable<Object>() {
			@Override
			public Object run(Object arg1, Object arg2) throws Exception {
				Message m = (Message)arg2;
				if(!m.getFolder().isOpen()) m.getFolder().open(Folder.READ_WRITE);
				m.setFlag(Flags.Flag.DELETED, true);
				return null;
			}
		};
		prepareForImap(runnable, m.getFolder().getFullName(), m);
	}
	
	public void removeMessage(Message m) throws MessagingException {
		if(!m.getFolder().isOpen()) m.getFolder().open(Folder.READ_WRITE);
			m.setFlag(Flags.Flag.DELETED, true);
			m.getFolder().close(true);
	}
	
	public boolean isSeen(Message m) throws MessagingException {
		if(!m.getFolder().isOpen()) m.getFolder().open(Folder.READ_WRITE);
		boolean result = m.isSet(Flags.Flag.SEEN);
		m.getFolder().close(false);
		return result;
	}
	
	private Object prepareForImap(Runnable<Object> runnable, String folder, Object additional) {
		Object result = null;
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
			result = runnable.run(_folder, additional);
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, "exception was thrown", e);
			throw new JMailException("Can not retrieve messages from server\n" + e.getMessage(), e);
		} finally {
			try {
				if(_folder != null && _folder.isOpen()) _folder.close(false);
				if(store != null) store.close();
			} catch(Exception e) {
				Main.logger.log(Level.SEVERE, "exception was thrown", e);
			}
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

@FunctionalInterface
interface Runnable<T> {
	T run(T arg1, T arg2)  throws Exception;
}

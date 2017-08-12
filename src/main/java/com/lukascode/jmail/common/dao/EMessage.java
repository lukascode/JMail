package com.lukascode.jmail.common.dao;

import java.util.Date;
import java.util.logging.Level;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import com.lukascode.jmail.common.Main;

public class EMessage {
	
	public EMessage(Message m) {
		message = m;
		createEMessage();
	}
	
	private Message message;
	
	private String from;
	private String to;
	private String subject;
	private Date date;
	
	private void createEMessage() {
		try {
			subject = message.getSubject();
			date = message.getReceivedDate();
			Address[] addrs = null;
			addrs = message.getAllRecipients();
			String email = addrs == null ? null : ((InternetAddress) addrs[0]).getAddress();
			to = email;
			addrs = message.getFrom();
			email = addrs == null ? null : ((InternetAddress) addrs[0]).getAddress();
			from = email;
		} catch(Exception e) {
			Main.logger.log(Level.SEVERE, "exception was thrown", e);
		}
	}
	
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		try {
			return message.getSubject();
		} catch (MessagingException e) {
			Main.logger.log(Level.SEVERE, "Emessage.getSubject error", e);
		}
		return "";
		//return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}

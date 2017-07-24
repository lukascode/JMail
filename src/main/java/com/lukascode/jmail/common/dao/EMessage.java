package com.lukascode.jmail.common.dao;

import java.util.Date;

public class EMessage {
	
	public EMessage() {
		
	}
	
	private String from;
	private String to;
	private String subject;
	private Date date;
	
	
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
		return subject;
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

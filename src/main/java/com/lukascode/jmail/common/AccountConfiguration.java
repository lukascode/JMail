package com.lukascode.jmail.common;

import java.time.LocalDateTime;

public class AccountConfiguration {
	
	private int id;
	private String email;
	private String password;
	private boolean savePassword;
	private String smtpServerName;
	private String smtpServerPort;
	private boolean smtpServerSSL;
	private boolean smtpServerTLS;
	private String imapServerName;
	private String imapServerPort;
	private boolean imapServerSSL;
	private boolean imapServerTLS;
	private LocalDateTime lastLogin;
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public boolean isSavePassword() {
		return savePassword;
	}



	public void setSavePassword(boolean savePassword) {
		this.savePassword = savePassword;
	}



	public String getSmtpServerName() {
		return smtpServerName;
	}



	public void setSmtpServerName(String smtpServerName) {
		this.smtpServerName = smtpServerName;
	}



	public String getSmtpServerPort() {
		return smtpServerPort;
	}



	public void setSmtpServerPort(String smtpServerPort) {
		this.smtpServerPort = smtpServerPort;
	}



	public boolean isSmtpServerSSL() {
		return smtpServerSSL;
	}



	public void setSmtpServerSSL(boolean smtpServerSSL) {
		this.smtpServerSSL = smtpServerSSL;
	}



	public boolean isSmtpServerTLS() {
		return smtpServerTLS;
	}



	public void setSmtpServerTLS(boolean smtpServerTLS) {
		this.smtpServerTLS = smtpServerTLS;
	}



	public String getImapServerName() {
		return imapServerName;
	}



	public void setImapServerName(String imapServerName) {
		this.imapServerName = imapServerName;
	}



	public String getImapServerPort() {
		return imapServerPort;
	}



	public void setImapServerPort(String imapServerPort) {
		this.imapServerPort = imapServerPort;
	}



	public boolean isImapServerSSL() {
		return imapServerSSL;
	}



	public void setImapServerSSL(boolean imapServerSSL) {
		this.imapServerSSL = imapServerSSL;
	}



	public boolean isImapServerTLS() {
		return imapServerTLS;
	}



	public void setImapServerTLS(boolean imapServerTLS) {
		this.imapServerTLS = imapServerTLS;
	}



	public LocalDateTime getLastLogin() {
		return lastLogin;
	}



	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public String toString() {
		return id + ", " + email + ", " + password + ", passSave(" + savePassword + ") lastLogin(" + lastLogin.toString() + ")"; 
	}


	public AccountConfiguration() {
		lastLogin = LocalDateTime.now();
	}
}

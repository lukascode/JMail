package com.lukascode.jmail.common.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lukascode.jmail.common.AccountConfiguration;
import com.lukascode.jmail.common.Main;

public class AccountConfigurationDAO {
	
	public AccountConfigurationDAO() {
		
	}
	
	public List<AccountConfiguration> getAccounts() {
		List<AccountConfiguration> acs = new ArrayList<>();
		String sql = "SELECT * FROM account_configuration";
		try(Connection conn = JMailDatabaseCreator.getInstance().connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
				while(rs.next()) {
					acs.add(getAC(rs));
				}
		} catch(SQLException e) {
			Main.logger.severe(e.getMessage());
		}
		return acs;
	}
	
	public AccountConfiguration get(int id) {
		AccountConfiguration ac = null;
		String sql = "SELECT * FROM account_configuration WHERE id=" + id;
		try(Connection conn = JMailDatabaseCreator.getInstance().connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
				if(rs.next()) ac = getAC(rs);
		} catch(SQLException e) {
			Main.logger.severe(e.getMessage());
		}
		return ac;
	}
	
	public boolean insert(AccountConfiguration ac) {
		boolean result = true;
		String sql =  "INSERT INTO account_configuration"
					+ "(id, email, password, save_password, smtp_server_name, smtp_server_port, "
					+ "smtp_server_ssl, smtp_server_tls, imap_server_name, imap_server_port, "
					+ "imap_server_ssl, imap_server_tls, last_login) "
					+ "VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try(Connection conn = JMailDatabaseCreator.getInstance().connect(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, ac.getEmail());
			pstmt.setString(2, ac.getPassword());
			pstmt.setInt(3, (ac.isSavePassword()?1:0));
			pstmt.setString(4, ac.getSmtpServerName());
			pstmt.setString(5, ac.getSmtpServerPort());
			pstmt.setInt(6, (ac.isSmtpServerSSL()?1:0));
			pstmt.setInt(7, (ac.isSmtpServerTLS()?1:0));
			pstmt.setString(8, ac.getImapServerName());
			pstmt.setString(9, ac.getImapServerPort());
			pstmt.setInt(10, (ac.isImapServerSSL()?1:0));
			pstmt.setInt(11, (ac.isImapServerTLS()?1:0));
			pstmt.setString(12, ac.getLastLoginFormated());
			int i = pstmt.executeUpdate();
			if(i <= 0) result = false;
			else {
				ResultSet rs = pstmt.getGeneratedKeys();
				ac.setId(rs.getInt(1));
			}
		} catch(SQLException e) {
			Main.logger.severe(e.getMessage());
			result = false;
		}
		return result;
	}
	
	public boolean update(AccountConfiguration ac) {
		if(ac == null) return false;
		boolean result = true;
		String sql =   "UPDATE account_configuration SET email = ?, "
					 + "password = ?, save_password = ?, smtp_server_name = ?, "
					 + "smtp_server_port = ?, smtp_server_ssl = ?, smtp_server_tls = ?, "
					 + "imap_server_name = ?, imap_server_port = ?, imap_server_ssl = ?, "
					 + "imap_server_tls = ?, "
					 + "last_login = ? "
					 + "WHERE id = ?";
		try(Connection conn = JMailDatabaseCreator.getInstance().connect(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, ac.getEmail());
			pstmt.setString(2, ac.getPassword());
			pstmt.setInt(3, (ac.isSavePassword()?1:0));
			pstmt.setString(4, ac.getSmtpServerName());
			pstmt.setString(5, ac.getSmtpServerPort());
			pstmt.setInt(6, (ac.isSmtpServerSSL()?1:0));
			pstmt.setInt(7, (ac.isSmtpServerTLS()?1:0));
			pstmt.setString(8, ac.getImapServerName());
			pstmt.setString(9, ac.getImapServerPort());
			pstmt.setInt(10, (ac.isImapServerSSL()?1:0));
			pstmt.setInt(11, (ac.isImapServerTLS()?1:0));
			pstmt.setString(12, ac.getLastLoginFormated());
			pstmt.setInt(13, ac.getId());
			int i = pstmt.executeUpdate();
			if(i <= 0) result = false;
		} catch(SQLException e) {
			Main.logger.severe(e.getMessage());
			result = false;
		}
		return result;
	}
	
	public boolean delete(int id) {
		boolean result = true;
		String sql = "DELETE FROM account_configuration WHERE id = ?";
		try(Connection conn = JMailDatabaseCreator.getInstance().connect(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			int i = pstmt.executeUpdate();
			if(i <= 0) result = false;
		} catch(SQLException e) {
			Main.logger.severe(e.getMessage());
			result = false;
		}
		return result;
	}
	
	private AccountConfiguration getAC(ResultSet rs) throws SQLException {
		if(rs == null) return null;
		AccountConfiguration ac = new AccountConfiguration();
		ac.setId(rs.getInt("id"));
		ac.setEmail(rs.getString("email"));
		ac.setPassword(rs.getString("password"));
		ac.setSavePassword((rs.getInt("save_password")==1)?true:false);
		ac.setSmtpServerName(rs.getString("smtp_server_name"));
		ac.setSmtpServerPort(rs.getString("smtp_server_port"));
		ac.setSmtpServerSSL((rs.getInt("smtp_server_ssl")==1)?true:false);
		ac.setSmtpServerTLS((rs.getInt("smtp_server_tls")==1)?true:false);
		ac.setImapServerName(rs.getString("imap_server_name"));
		ac.setImapServerPort(rs.getString("imap_server_port"));
		ac.setImapServerSSL((rs.getInt("imap_server_ssl")==1)?true:false);
		ac.setImapServerTLS((rs.getInt("imap_server_tls")==1)?true:false);
		ac.setLastLoginFormated(rs.getString("last_login"));
		return ac;
	}
	
}

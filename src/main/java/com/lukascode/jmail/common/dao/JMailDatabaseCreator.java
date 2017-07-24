package com.lukascode.jmail.common.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import com.lukascode.jmail.common.Main;

public class JMailDatabaseCreator {
	
	private String fileName;
	private static JMailDatabaseCreator instance = null;
	
	private JMailDatabaseCreator(String _fileName) {
		fileName = _fileName;
	}
	
	public static JMailDatabaseCreator createInstance(String _fileName) {
		instance = new JMailDatabaseCreator(_fileName);
		return instance;
	}
	
	public static JMailDatabaseCreator getInstance() {
		return instance;
	}
	
	public boolean createDatabase() {
		boolean result = false;
		String url = "jdbc:sqlite:" + Main.APP_FOLDER + "\\" + fileName;
		String sql = getSQLCreator();
		try (Connection conn = connect();
				Statement stmt = conn.createStatement()) {
	        stmt.execute(sql);
	        result = true;
	    } catch (SQLException e) {
	    	Main.logger.severe("Database creation error. " + e.getMessage());
	    }
		return result;
	}
	
	public Connection connect() {
		String url = "jdbc:sqlite:" + Main.APP_FOLDER + "\\" + fileName;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch(SQLException e) {
			Main.logger.severe(e.getMessage());
		}
		return conn;
	}
	
	private static String getSQLCreator() {
		String sql = ""
				+ "CREATE TABLE IF NOT EXISTS account_configuration\n"
				+ "("
				+ "   id INTEGER PRIMARY KEY," 
				+ "   email TEXT NOT NULL,"
				+ "   password TEXT," 
				+ "   save_password INTEGER NOT NULL DEFAULT 0,"
				+ "   smtp_server_name TEXT NOT NULL,"
				+ "   smtp_server_port TEXT NOT NULL,"
				+ "   smtp_server_ssl INTEGER NOT NULL DEFAULT 1,"
				+ "   smtp_server_tls INTEGER NOT NULL DEFAULT 0,"
				+ "   imap_server_name TEXT NOT NULL,"
				+ "   imap_server_port TEXT NOT NULL,"
				+ "   imap_server_ssl INTEGER NOT NULL DEFAULT 1,"
				+ "   imap_server_tls INTEGER NOT NULL DEFAULT 0,"
				+ "    last_login TEXT"
				+ ");";
		return sql;
	}

}
	


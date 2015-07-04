package com.db.logic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.db.modal.Sentence;

public class SentenceDTO {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/dbprime";
	Connection conn = null;
	PreparedStatement stmt = null;
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public SentenceDTO() {
		try {
			connect();
			String sql="INSERT INTO `dbprime`.`sentence`"+
					"(`sentenceId`,"+
					"`text`,"+
					"`structure`,"+
					"`psgId`)"+
					" VALUES "+
					"(?,"+
					"?,"+
					"?,"+
					"?);";

			stmt = conn.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(List<Sentence> rows) {
		for(Sentence row : rows)
			insert(row);
	}
	
	public void insert(Sentence row) {

		try {
			stmt.setString(3, row.getStructure());
			stmt.setString(1, row.getSentenceId());
			stmt.setString(2, row.getSentence());
			stmt.setString(4, "kkk");

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		try {
			if (stmt != null) 
				stmt.close();

			if (conn != null) 
				conn.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
}//end JDBCExample
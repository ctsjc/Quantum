package com.db.logic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.db.modal.RagnarRw;

public class JDBCExample {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/dbprime";
	Connection conn = null;
	PreparedStatement stmt = null;
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public JDBCExample() {
		try {
			connect();
			

			String sql="INSERT INTO `dbprime`.`ragnar`"+
			"(`tbl`,"+
			"`row`,"+
			"`val`,"+
			"`ref`,"+
			"`type`)"+
			"VALUES"+
			"(?,"+
			"?,"+
			"?,"+
			"?,"+
			"?)";
			stmt = conn.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(List<RagnarRw> rows) {
		for(RagnarRw row : rows)
			insert(row);
	}
	
	public void insert(RagnarRw row) {

		try {
			stmt.setString(1, row.getTable());
			stmt.setString(2, row.getRow());
			stmt.setString(3, row.getVal());
			stmt.setString(4, row.getRefTable());
			stmt.setString(5, row.getD().toString());
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
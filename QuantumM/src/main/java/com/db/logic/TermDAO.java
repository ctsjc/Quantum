package com.db.logic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.db.modal.RagnarRw;
import com.db.modal.T;

public class TermDAO {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/dbprime";
	Connection conn = null;
	PreparedStatement insertStTerm = null;
	PreparedStatement insertStQues = null;
	PreparedStatement insertStTQ = null;

	PreparedStatement selectStTerm = null;
	PreparedStatement selectStQues = null;
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public TermDAO() {
		try {
			connect();
			
			String sql="INSERT INTO `dbprime`.`terms` VALUES  (?,?)";
			insertStTerm = conn.prepareStatement(sql);
			
			sql="INSERT INTO `dbprime`.`questions` VALUES  (?,?)";
			insertStQues = conn.prepareStatement(sql);
			
			sql="INSERT INTO `dbprime`.`term_question` VALUES  (?,?)";
			insertStTQ = conn.prepareStatement(sql);
			
			sql="SELECT * FROM `dbprime`.`terms` WHERE val=?";
			selectStTerm = conn.prepareStatement(sql);
			
			sql="SELECT * FROM `dbprime`.`questions` WHERE val=?";
			selectStQues = conn.prepareStatement(sql);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TermDAO dao=new TermDAO();
		T term = new T("gfgfg");
		System.out.println(dao.isPresent(term));
		//dao.insert(term);
		System.out.println(dao.getTermId(term));
	}
	public void insert(List<T> rows) {
		for(T row : rows)
			insert(row);
	}
	
	public boolean isPresent(T term){
		ResultSet rs;
		try {
			
			selectStTerm.setString(1, term.getVal());
			rs=selectStTerm.executeQuery();
			while(rs.next()){
				rs.getString("id");
				rs.getString("val");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getTermId(T term){
		ResultSet rs;
		try {
			
			selectStTerm.setString(1, term.getVal());
			rs=selectStTerm.executeQuery();
			while(rs.next()){
				rs.getString("id");
				rs.getString("val");
				return rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void insert(T row) {

		try {
			insertStTerm.setString(1, row.getId());
			insertStTerm.setString(2, row.getVal());
			insertStTerm.executeUpdate();
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
			if (insertStTerm != null) 
				insertStTerm.close();

			if (conn != null) 
				conn.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
}//end JDBCExample
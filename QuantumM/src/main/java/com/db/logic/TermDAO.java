package com.db.logic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.db.modal.T;

public class TermDAO {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/dbprime";
	static Connection conn = null;
	private PreparedStatement insertStTerm = null;
	private PreparedStatement insertStQues = null;
	private PreparedStatement insertStTQ = null;

	private PreparedStatement selectStTerm = null;
	private PreparedStatement selectStQues = null;
	private PreparedStatement slTerm_QuesStmt = null;
	private PreparedStatement slFindlTerm_QuesStmt = null;

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";

	static{
		try {
			connect();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public TermDAO() {
		try {
			
			String sql="INSERT INTO `dbprime`.`terms` VALUES  (?,?)";
			insertStTerm = conn.prepareStatement(sql);
			
			sql="INSERT INTO `dbprime`.`questions` VALUES  (?,?)";
			insertStQues = conn.prepareStatement(sql);
			
			sql="INSERT INTO `dbprime`.`term_question` VALUES  (?,?,?)";
			insertStTQ = conn.prepareStatement(sql);
			
			sql="SELECT * FROM `dbprime`.`terms` WHERE val=?";
			selectStTerm = conn.prepareStatement(sql);
			
			sql="SELECT * FROM `dbprime`.`questions` WHERE val=?";
			selectStQues = conn.prepareStatement(sql);
			
			sql="SELECT * FROM `dbprime`.`term_question` WHERE termId=? AND questionId=?";
			slTerm_QuesStmt = conn.prepareStatement(sql);
			
			sql="SELECT  "
					+ "q.val as ques "
					+ "FROM dbprime.terms t,  dbprime.questions q, dbprime.term_question tq"+
					" WHERE "
					+ "t.id=tq.termId "+
					" AND "
					+"q.id= tq.questionId "
					+" AND "
					+"t.val=?";
			
			slFindlTerm_QuesStmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TermDAO dao=new TermDAO();
		T term = new T("AA");
		System.out.println(dao.isTermPresent(term));
		//dao.insert(term);
		System.out.println(dao.getTermQuestions(term.getVal()));
	}
	public void insert(List<T> rows) {
		for(T row : rows)
			insertTerm(row);
	}
	
	public boolean isTermPresent(T term){
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
	
	public boolean isQuestionPresent(T term){
		ResultSet rs;
		try {
			
			selectStQues.setString(1, term.getVal());
			rs=selectStQues.executeQuery();
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
	
	public String getQuesId(T term){
		ResultSet rs;
		try {
			
			selectStQues.setString(1, term.getVal());
			rs=selectStQues.executeQuery();
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
	
	public void insertTerm(T row) {

		try {
			insertStTerm.setString(1, row.getId());
			insertStTerm.setString(2, row.getVal());
			insertStTerm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertQues(T row) {

		try {
			insertStQues.setString(1, row.getId());
			insertStQues.setString(2, row.getVal());
			insertStQues.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		/*try {
			if (insertStTerm != null) 
				insertStTerm.close();

			if (conn != null) 
				conn.close();
		} catch (SQLException e) {e.printStackTrace();}*/
	}

	public void addWitiwik(String termId, String quesId) {
		if (!isTerm_QuesPairPresent(termId, quesId)) {
			try {

				insertStTQ.setString(1, UUID.randomUUID().toString());
				insertStTQ.setString(2, termId);
				insertStTQ.setString(3, quesId);
				insertStTQ.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isTerm_QuesPairPresent(String termId, String quesId){
		ResultSet rs;
		try {
			
			slTerm_QuesStmt.setString(1,termId );
			slTerm_QuesStmt.setString(2,quesId );

			rs=slTerm_QuesStmt.executeQuery();
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Map<String, List<String>> getTermQuestions(String term){
		Map<String, List<String>> map =new HashMap<String, List<String>>();
		 List<String> ques=new ArrayList<String>();
		 map.put(term, ques);
		ResultSet rs;
		try {
			slFindlTerm_QuesStmt.setString(1, term);
			rs=slFindlTerm_QuesStmt.executeQuery();
			while(rs.next()){
				ques.add(rs.getString("ques"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
}//end JDBCExample
package com.db.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.db.modal.D;
import com.db.modal.RagnarRw;
import com.db.modal.T;
import com.db.modal.Witwicky;
import com.model.parser.JTable;
import com.statement.logic.Parser;

public class TermExtractor {
	
	/***
	 * I want all questions related to the term
	 * term
	 * questions*
	 * 
	 */
	public static void main(String[] args) {
		
		String jsn=null;
		jsn="{map:{table:1000,a:{tbl:1000,row:1001,val:{table:1018,a:{tbl:1018,row:1019,val: AA},b:{tbl:1018,row:1022,val:AB },c:{tbl:1018,row:1022,val:AC }}},b:{tbl:1000,row:1004,val:B},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val: CA},b:{tbl:1009,row:1013,val:CB},c:{tbl:1009,row:1013,val:{table:1027,a:{tbl:1027,row:1028,val:{table:1045,a:{tbl:1045,row:1046,val:{table:1054,a:{tbl:1054,row:1055,val: CCA},b:{tbl:1054,row:1058,val: CCB},c:{tbl:1054,row:1058,val: CCC}}},b:{tbl:1045,row:1049,val: CCD},c:{tbl:1045,row:1049,val:{table:1063,a:{tbl:1063,row:1064,val: CCEA},b:{tbl:1063,row:1067,val: CCEB},c:{tbl:1063,row:1067,val: CCEC},r1_1072:{tbl:1063,row:1072,val: CCED},r2_1072:{tbl:1063,row:1072,val: CCEE},r1_1077:{tbl:1063,row:1077,val: CCEF},r2_1077:{tbl:1063,row:1077,val: CCEG},r1_1082:{tbl:1063,row:1082,val: CCEH},r2_1082:{tbl:1063,row:1082,val: CCEI}}}}},b:{tbl:1027,row:1031,val: CCF},c:{tbl:1027,row:1031,val:{table:1036,a:{tbl:1036,row:1037,val: CCGA},b:{tbl:1036,row:1040,val: CCGB},c:{tbl:1036,row:1040,val: CCGC}}}}}}}}}";

		Parser p =new Parser(jsn);
		JTable jTable = (JTable) p.getTable();
		List<RagnarRw> rows=new ArrayList<RagnarRw>();
		RowGenerator gen=new RowGenerator();
		gen.parse(jTable, rows,UUID.randomUUID().toString());
		
		TermExtractor dExtractor=new TermExtractor();
		dExtractor.insert(rows);
		dExtractor.termsAndQuestionsFromDatabase(rows );//end of for		
	}//end of main

	public void insert(List<RagnarRw> rows) {
		HashMap<String, List<String>> tq=this.getTermQuestions(rows);
		List<Witwicky> witwickies=new ArrayList<Witwicky>();
		List<T> terms=new ArrayList<T>();
		List<T> quess=new ArrayList<T>();
		for(Entry<String, List<String>> entry:tq.entrySet()){
			T term=new T(entry.getKey());
			terms.add(term);
			for(String q :entry.getValue()){
				T ques=new T(q);
				quess.add(ques);
				witwickies.add(new Witwicky(term.getId(), ques.getId()));
			}
		}//end of for
		
		TermDAO dao=new TermDAO();
		String termId=null;
		for(T tr:terms){
			System.out.println(tr.getVal()+"\t"+tq.get(tr.getVal()));
			System.out.println(">>> "+dao.getTermQuestions(tr.getVal()).get(tr.getVal()));
			// check term in
			termId= dao.getTermId(tr);
			if(dao.isTermPresent(tr)){
				this.addQuestions(termId,tq.get(tr.getVal()));
			}else{
				dao.insertTerm(tr);
				termId=dao.getTermId(tr);
				this.addQuestions(termId,tq.get(tr.getVal()));
			}//end of else
		}
	}
	
	private void addQuestions(String termId, List<String> questions) {
			TermDAO dao=new TermDAO();
			for(String ques:questions){
				
				T q = new T(ques);
				if(dao.isQuestionPresent(q)){
					dao.addWitiwik(termId,dao.getQuesId(q));
				}
				else{
					dao.insertQues(q);
					dao.addWitiwik(termId,dao.getQuesId(q));
				}//end of else
			}//end of for
	}//end of method

	public HashMap<String, List<String>> getTermQuestions(List<RagnarRw> rows){
		HashMap<String, List<String>> tq=new HashMap<String, List<String>>();
		String term;
		String table;
		List<String> questions;
		for(RagnarRw row: rows){
			if(row.getD() == D.TERM && row.getVal() != null){
				term=row.getVal();
				table=row.getTable();
				questions=findQuestions(rows,table);
				tq.put(term, questions);
			}//end of if
		}//end of for
		return tq;
	}

	private List<String> findQuestions(List<RagnarRw> rows, String table) {
		List<String> questions=new ArrayList<String>();
		for(RagnarRw row: rows){
			if(row.getTable().equals(table) && row.getD() == D.QUES){
				questions.add(row.getVal());
			}
		}//end of for
		return questions;
	}//end of method
	
	public List<Map<String, List<String>>> termsAndQuestionsFromDatabase(List<RagnarRw> rows){
		List<Map<String, List<String>>> list=new ArrayList<Map<String,List<String>>>();
		TermDAO dao=new TermDAO();
		//iterate and find rows
		for(RagnarRw row:rows){
			if(row.getD() == D.TERM){
				list.add(dao.getTermQuestions(row.getVal()));
			}
		}//end of for
		
		return list;
	}
}//end of class

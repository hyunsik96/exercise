package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.BoardDao;
import com.kh.model.vo.Board;

public class BoardService {

	public ArrayList<Board> selectAll() {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드 호출
		ArrayList<Board> list = new BoardDao().selectAll(conn);
		
		// 3) 트랜잭션 처리 => 패스
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return list;
	}

	public int insertBoard(Board b) {
		

		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드 호출
		int result = new BoardDao().insertBoard(conn, b);
		
		// 3) 트랜잭션 처리
		if(result > 0) {
			
			JDBCTemplate.commit(conn);
		}
		else {
			
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return result;
	}

}

package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

// Service : 기존의 DAO 의 역할 분담
// 			 Connection 객체를 이용하는 코드들을 Service 단으로 이관시킬 예정
//			 => Service 를 추가함으로써 DAO 에는 SQL 문 실행과 관련된 코드들만 남겨질 것
public class MemberService {

	public int insertMember(Member m) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드를 호출 (Connection, 전달값 둘 다 매개변수로 넘기기)
		int result = new MemberDao().insertMember(conn, m);
		
		// 3) 트랜잭션 처리
		if(result > 0) { // 성공 => COMMIT
			
			JDBCTemplate.commit(conn);
		}
		else { // 실패 => ROLLBACK
			
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과값을 Controller 로 리턴
		return result;
	}
	
	public ArrayList<Member> selectAll() {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드를 호출 (Connection, 전달값 을 매개변수로 넘기기)
		ArrayList<Member> list = new MemberDao().selectAll(conn);
		
		// 3) 트랜잭션 처리 => SELECT 문의 경우는 생략
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return list;
	}
	
	public Member selectByUserId(String userId) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드 호출 (Connection, 전달값을 매개변수로 보내기)
		Member m = new MemberDao().selectByUserId(conn, userId);
		
		// 3) 트랜잭션 처리 => SELECT 문을 실행했기 때문에 패스
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return m;
	}
	
	public ArrayList<Member> selectByUserName(String keyword) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드 호출 (Connection, 전달값을 매개변수로 넘김)
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		// 3) 트랜잭션 처리 => SELECT 문 실행 시 패스
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return list;
	}
	
	public int updateMember(Member m) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 의 메소드를 호출 (Connection, 전달값을 매개변수로 넘김)
		int result = new MemberDao().updateMember(conn, m);
		
		// 3) 트랜잭션 처리
		if(result > 0) { // 성공 => COMMIT
			
			JDBCTemplate.commit(conn);
		}
		else { // 실패 => ROLLBACK
			
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return result;
	}
	
	public int deleteMember(String userId) {
	
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드 호출 (매개변수 꼭 넘기기!)
		int result = new MemberDao().deleteMember(conn, userId);
		
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

















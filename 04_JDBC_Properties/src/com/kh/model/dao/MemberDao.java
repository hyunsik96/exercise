package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

/*
 * * DAO (Data Access Object)
 * Controller 로 부터 전달받은 요청 기능을 수행하기 위해서
 * DB 에 직접적으로 접근 한 후 해당 SQL 을 실행하고 결과를 받아내는 역할
 * => 실질적인 JDBC 코드 작성
 * 추가적으로 결과값을 가공하거나, 성공 실패 여부에 따라서 트랜잭션 처리
 * 결과를 Controller 로 리턴해줌
 */
public class MemberDao {
	
	/*
	 * * 기존의 방식 : DAO 클래스에 사용자가 요청할 때 마다 실행해야 하는 SQL 문을
	 * 			      자바 소스코드 내에 직접 명시적으로 작성함 => 정적 코딩 방식
	 * - 문제점 : SQL 구문을 수정해야 할 경우 자바 소스코드를 수정하는 셈
	 * 		       즉, 수정된 구문을 반영시키고자 할 경우에는 프로그램을 재구동 해야 함!!
	 * - 해결방식 : SQL 구문들을 별도로 관리하는 외부 파일 (.xml) 로 만들어서
	 * 			  실시간으로 이 파일에 기록된 SQL 문들을 동적으로 읽어들여서 실행되게끔 => 동적 코딩 방식
	 */
	
	private Properties prop = new Properties();
	
	// new MemberDao().xxx(); 할 때마다 MemberDao 객체가 생성되면서 기본생성자가 호출됨
	public MemberDao() {
		
		try {
			
//			prop.loadFromXML(new FileInputStream("resources/query.xml"));
			
//			https://github.com/hyunsik96/exercise.git///
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 사용자가 회원 추가 요청 시 입력했던 값들을 가지고 INSERT 문을 실행하는 메소드
	public int insertMember(Connection conn, Member m) { // INSERT 문 => 처리된 행의 갯수 => 트랜잭션 처리
		
		// 0) JDBC 처리를 하기 전에 우선적으로 필요한 변수들 먼저 셋팅
		int result = 0; // 처리된 결과 (처리된 행의 갯수) 를 담아줄 변수
		
		PreparedStatement pstmt = null; // SQL 문을 실행 후 결과를 받기 위한 변수
										// Statement 와 역할은 똑같지만 사용법은 다름!!
		
		// 실행할 SQL 문 (미완성된 형태로) => 반드시 세미콜론은 떼고 넣어줄 것
		// INSERT INTO MEMBER
		// VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)
		
		String sql = prop.getProperty("insertMember");
		
		// String sql = "INSERT INTO MEMBER "
		//		      + "VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
		
		try {
			
			// 3_1) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql); // 미리 sql 을 넘기는 꼴
			// stmt = conn.createStatement();
			
			// 3_2) 내가 담은 SQL 문이 미완성된 상태라면 값을 채워넣기
			//      (pstmt.setXXX(위치홀더의순번, 매꿀값) 메소드 호출)
			// 		=> pstmt.setString(위치홀더의순번, 매꿀값); : '매꿀값'
			// 		=> pstmt.setInt(위치홀더의순번, 매꿀값); : 매꿀값
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			// PreparedStatement 의 최대 장점 : 쿼리 작성하기 간편해짐, 가독성 증가
			// PreparedStatement 의 최대 단점 : 구멍 매꾸기가 귀찮아짐, 완성된 SQL 문의 형태를 확인 불가
			
			// 4, 5) DB 에 완성된 SQL 문을 실행 후 결과를 받기
			result = pstmt.executeUpdate(); // INSERT => int (처리된 행의 갯수)
			// result = stmt.executeUpdate(sql); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) pstmt 객체 반납
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과 반환
		return result; // 처리된 행의 갯수
	}
	
	// 사용자의 회원 전체 조회 요청시 SELECT 문을 실행할 메소드
	public ArrayList<Member> selectAll(Connection conn) { // SELECT => ResultSet 객체 (여러 행 조회)
		
		// 0) 필요한 변수들 셋팅
		// 조회된 결과를 뽑아서 담아줄 ArrayList 생성 (현재 텅 빈 리스트)
		ArrayList<Member> list = new ArrayList<>(); // 조회된 회원들이 담김
		
		PreparedStatement pstmt = null; // SQL 문 실행 후 결과를 받기 위한 변수
		ResultSet rset = null; // SELECT 문이 실행된 조회결과값들이 처음에 실질적으로 담길 변수
		
		// SELECT 문의 경우는 굳이 PreparedStatement 를 사용하지 않아도 되지만
		// PreparedStatement 사용 시 완성된 쿼리문을 사용할 수도 있기 때문에 연습삼아 해보자
		
		// 실행할 SQL 문 (완성된 형태로, 세미콜론 X)
		// SELECT * FROM MEMBER
		
		String sql = prop.getProperty("selectAll");
		
		// String sql = "SELECT * FROM MEMBER";
		
		try {
			
			// 3_1) PreparedStatement 객체 생성 (SQL 문 전달을 여기서 진행)
			pstmt = conn.prepareStatement(sql);
			// stmt = conn.createStatement();
			
			// 3_2) 미완성된 SQL 문 완성 단계
			// => 쿼리문이 완성된 형태로 넘어갔기 때문에 생략 가능
			
			// 4, 5) SQL 문 (SELECT) 을 실행 후 결과 받기
			rset = pstmt.executeQuery();
			// rset = stmt.executeQuery(sql);
			
			// 6_1) 현재 조회 결과가 담긴 ResultSet 에서 한행씩 뽑아서 VO 객체에 담기
			// => 커서를 한 행 한 행 씩 아래로 옮겨서 현재 행의 위치를 나타낸다.
			// => 이 때 커서는 rset.next() 메소드로 다음 줄로 넘겨버린다.
			// rset.next() : 커서를 한 행 밑으로 움직여 주고 해당 행이 존재할 경우에는 true 
			//											      존재하지 않을 경우에는 false
			while(rset.next()) { // 더이상 뽑아낼 게 없을 때 까지 반복을 돌리겠다.
				
				// 뽑아낼 게 있다면
				// 모든 컬럼에 대해서 값을 일일이 다 뽑아야 한다!!
				// Member 테이블에는 11개의 컬럼이 있음
				
				// 현재 rset 의 커서가 가리키고 있는 해당 행의 데이터를
				// 하나씩 뽑아서 Member 객체에 담는다.
				Member m = new Member();
				
				// rset 으로부터 어떤 컬럼에 해당하는 값을 뽑을건지 제시
				// rset.getInt(컬럼명 또는 컬럼의순번) : int 형 값으로 값을 뽑아줌
				// rset.getString(컬럼명 또는 컬럼의순번) : String 형 값으로 값을 뽑아줌
				// rset.getDate(컬럼명 또는 컬럼의순번) : Date 형 값으로 값을 뽑아줌
				// => 권장사항 : 컬럼명으로 꼭 쓸것! 컬럼명 작성 시 대문자로 작성 (소문자도 가능)
				
				// 뽑자마자 setter 로 필드에 값을 담아주기
				m.setUserNo(rset.getInt("USERNO")); // rset.getInt(1)
				m.setUserId(rset.getString("USERID")); // rset.getString(2)
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 한 행에 대한 모든 데이터값들을
				// 하나의 Member 객체에 옮겨담기 끝!
				
				// 리스트에 해당 Member 객체를 add
				list.add(m);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 자원반납 (역순)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과 반환
		return list;
	}
	
	// 사용자의 아이디로 회원 검색 요청시 SELECT 문을 실행할 메소드
	public Member selectByUserId(Connection conn, String userId) { // SELECT 문 => ResultSet 객체 (한 행 조회)
		
		// 0) 필요한 변수들 셋팅
		// 조회된 한 회원에 대한 정보를 담을 변수
		Member m = null;
		
		PreparedStatement pstmt = null; // SQL 문 실행 후 결과를 받기 위한 변수
		ResultSet rset = null; // SELECT 문이 실행된 조회결과값들이 처음에 실질적으로 담길 변수
		
		// 실행할 SQL 문 (미완성된 형태, 세미콜론 X)
		// SELECT * FROM MEMBER WHERE USERID = ?
		
		String sql = prop.getProperty("selectByUserId");
		
		// String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			
			// 3_1) PreparedStatement 객체 생성 (SQL 문을 이 시점에서 미리 보내두기)
			pstmt = conn.prepareStatement(sql);
			// stmt = conn.createStatement();
			
			// 3_2) 미완성된 SQL 문을 매꾸는 작업
			pstmt.setString(1, userId);
			
			// 4, 5) SQL 문 (SELECT) 을 실행 후 결과를 받기
			rset = pstmt.executeQuery();
			// rset = stmt.executeQuery(sql);
			
			// 6_1) 현재 조회 결과가 담긴 ResultSet 에서 한행씩 뽑아서 VO 객체에 담기
			// 만약 next() 메소드 실행 후 뽑아낼게 있다면 true 반환
			if(rset.next()) { // 어차피 unique 제약조건에 의해 최대 1개의 행만 조회될것이기 때문에 if 를 씀
				
				// 조회된 한 행에 대한 데이터값들을 뽑아서
				// 하나의 Member 객체에 담기
				m = new Member(rset.getInt("USERNO"), 
							   rset.getString("USERID"), 
							   rset.getString("USERPWD"), 
							   rset.getString("USERNAME"), 
							   rset.getString("GENDER"), 
							   rset.getInt("AGE"), 
							   rset.getString("EMAIL"), 
							   rset.getString("PHONE"), 
							   rset.getString("ADDRESS"), 
							   rset.getString("HOBBY"), 
							   rset.getDate("ENROLLDATE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 자원반납 (역순)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과 반환
		return m; // 조회된 한명의 회원의 정보
	}
	
	// 회원 이름 키워드 검색 기능을 SELECT 구문을 이용해서 처리할 메소드
	public ArrayList<Member> selectByUserName(Connection conn, String keyword) { // SELECT 문 => ResultSet 객체 (여러 행 조회)
		
		// 0) 필요한 변수들 셋팅
		ArrayList<Member> list = new ArrayList<>(); // 나중에 조회될 회원들의 정보를 차곡차곡 담을 예정
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 (미완성된 형태로, 세미콜론 X)
		// SELECT * FROM MEMBER WHERE USERNAME LIKE '%XXX%'
		// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'";
		// 정상적으로 수행이 되지 않을것임! 왜? 문자열의 경우는 매꿔질 때  홑따옴표가 자동으로 들어가기 때문에 '%'?'%' 이 모양이 될 것임 (문법 오류)
		
		// 방법1) 오라클의 연결연산자를 이용한 방법
		// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
		// 정상적으로 수행이 될것임! 왜? 문자열의 경우는 매꿔질 때 홑따옴표가 자동으로 들어가기 때문에 '%' || '?' || '%' 이 모양이 될 것임
		
		// 방법2)
		
		String sql = prop.getProperty("selectByUserName");
		
		// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		// 단, 구멍을 매꾸는 과정에서 양쪽에 % 를 붙여서 매꿔줘야 함!
		
		try {
			
			// 3_1) PreparedStatement 객체 생성 (SQL 문을 이 시점에서 넘길 예정)
			pstmt = conn.prepareStatement(sql);
			// stmt = conn.createStatement();
			
			// 3_2) 미완성된 SQL 문을 완성시키기
			
			// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'";
			// 정상적으로 수행이 안되는 케이스
			// pstmt.setString(1, keyword); // '%'keyword'%' => 문법오류
			
			// 방법1) 오라클의 연결연산자를 이용한 방법
			// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
			// 정상적으로 수행이 되는 케이스
			// pstmt.setString(1, keyword); // '%' || 'keyword' || '%' => 잘 실행됨
			
			// 방법2) 
			// String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
			// 단, 구멍을 매꾸는 과정에서 양쪽에 % 를 붙여서 매꿔줘야 함!
			pstmt.setString(1, "%" + keyword + "%"); // '%keyword%' => 잘 실행됨
			
			// 4, 5) SQL 문 실행 후 결과 받기
			rset = pstmt.executeQuery();
			// rset = stmt.executeQuery(sql);
			
			// 6_1) ResultSet 에 담겨있는 결과를 VO 객체로 옮겨담기
			// 우선적으로 커서를 한 칸 내린 후 뽑을 값이 있나 검사 => rset.next();
			// 여러 행이 조회될 가능성이 높을 경우 반복적으로 검사가 진행되야 함
			while(rset.next()) {
				
				// 뽑을 값이 있을경우
				// 한 행에 들어있던 데이터들을 한개의 Member 객체에 옮겨담기 => 컬럼 갯수만큼 다 뽑기
				// rset.getString(컬럼명 또는 컬럼순번)
				// rset.getInt(컬럼명 또는 컬럼순번)
				// rset.getDate(컬럼명 또는 컬럼순번)
				/*
				Member m = new Member(rset.getInt("USERNO"),
									  rset.getString("USERID"), 
									  rset.getString("USERPWD"),
									  rset.getString("USERNAME"), 
									  rset.getString("GENDER"),
									  rset.getInt("AGE"),
									  rset.getString("EMAIL"),
									  rset.getString("PHONE"),
									  rset.getString("ADDRESS"),
									  rset.getString("HOBBY"),
									  rset.getDate("ENROLLDATE"));
				
				// ArrayList 에 추가
				list.add(m);
				*/
				
				list.add(new Member(rset.getInt("USERNO"),
									rset.getString("USERID"), 
									rset.getString("USERPWD"),
									rset.getString("USERNAME"), 
									rset.getString("GENDER"),
									rset.getInt("AGE"),
									rset.getString("EMAIL"),
									rset.getString("PHONE"),
									rset.getString("ADDRESS"),
									rset.getString("HOBBY"),
									rset.getDate("ENROLLDATE")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 자원 반납 (역순)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과 반환
		return list;
	}
	
	// 회원 변경 요청이 들어왔을 때 UPDATE 구문을 실행할 메소드
	public int updateMember(Connection conn, Member m) { // UPDATE 문 => int (처리된 행의 갯수) => 트랜잭션 처리
		
		// 0) 필요한 변수들 셋팅
		int result = 0; // 최종적으로 SQL 문을 실행할 결과를 담을 변수
		
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문 (미완성된 형태로, 세미콜론 X)
		/*
		 * UPDATE MEMBER
		 *    SET USERPWD = ?
		 *      , EMAIL = ?
		 *      , PHONE = ?
		 *      , ADDRESS = ?
		 *  WHERE USERID = ?
		 */
		
		String sql = prop.getProperty("updateMember");
		
		/*
		String sql = "UPDATE MEMBER "
				      + "SET USERPWD = ?"
				        + ", EMAIL = ?"
				        + ", PHONE = ?"
				        + ", ADDRESS = ? "
				    + "WHERE USERID = ?";
		*/
		
		try {
			
			// 3_1) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// stmt = conn.createStatement();
			
			// 3_2) 미완성된 SQL 문을 완성시키는 단계
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			// 4, 5) SQL 문을 실행 후 결과 받기
			result = pstmt.executeUpdate();
			// result = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 자원 반납 (역순)
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과 반환
		return result; // UPDATE 된 행의 갯수
	}
	
	// 회원 탈퇴 요청 시 DELETE 구문을 실행하는 메소드
	public int deleteMember(Connection conn, String userId) { // DELETE 문 => int (처리된 행의 갯수) => 트랜잭션 처리
		
		// 0) 필요한 변수 셋팅
		int result = 0; // 결과를 담을 용도의 변수
		
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문 (미완성된 형태로, 세미콜론 X)
		/*
		 * DELETE FROM MEMBER
		 * WHERE USERID = ?
		 */
		
		String sql = prop.getProperty("deleteMember");
		
		/*
		String sql = "DELETE FROM MEMBER "
				   + "WHERE USERID = ?";
		*/
		
		try {
			
			// 3_1) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// stmt = conn.createStatement();
			
			// 3_2) 미완성된 SQL 문을 완성시키는 단계
			pstmt.setString(1, userId);
			
			// 4, 5) SQL 문 실행 후 결과 받기
			result = pstmt.executeUpdate();
			// result = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 자원반납 (역순)
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과 리턴
		return result; // 삭제된 행의 갯수
	}
	
}













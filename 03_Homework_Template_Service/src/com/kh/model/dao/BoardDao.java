package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Board;

public class BoardDao {

	public ArrayList<Board> selectAll(Connection conn) {
		
		// 0) 필요한 변수 셋팅
		ArrayList<Board> list = new ArrayList<>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		/*
		 * SELECT BNO, TITLE, CREATE_DATE, USERID
		 * FROM BOARD, MEMBER
		 * WHERE WRITER = USERNO -- 연결고리에 대한 조건
		 *   AND DELETE_YN = 'N' -- 삭제가 되지 않은 게시글만 보이게끔
		 * ORDER BY CREATE_DATE DESC -- 최신글 순으로 보이게끔 
		 */
		String sql = "SELECT BNO, TITLE, CREATE_DATE, USERID "
				   + "FROM BOARD, MEMBER "
				   + "WHERE WRITER = USERNO "
				     + "AND DELETE_YN = 'N' "
				   + "ORDER BY CREATE_DATE DESC";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				list.add(new Board(rset.getInt("BNO"), 
								   rset.getString("TITLE"),
								   rset.getDate("CREATE_DATE"), 
								   rset.getString("USERID")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 자원반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public int insertBoard(Connection conn, Board b) {

		// 0) 필요한 변수 셋팅
		int result = 0;

		PreparedStatement pstmt = null;
		
		/*
		 * INSERT INTO BOARD
		 * VALUES(SEQ_BOARD.NEXTVAL, ?, ?, DEFAULT, ?, DEFAULT)
		 */
		String sql = "INSERT INTO BOARD "
				   + "VALUES(SEQ_BOARD.NEXTVAL, ?, ?, DEFAULT, ?, DEFAULT)";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getContent());
			pstmt.setInt(3, Integer.parseInt(b.getWriter()));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

}

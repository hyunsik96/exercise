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
import com.kh.model.vo.Product;

public class ProductDao {
	
	Properties prop = new Properties();
	
	// 기본 생성자
	public ProductDao() {

		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

// 사용자 요청의 의해 전체조회 => SELECT 문
	public ArrayList<Product> selectAll(Connection conn){
		
		ArrayList<Product> list = new ArrayList<>(); // 조회된 회원들이 담길 곳
		
		PreparedStatement pstmt = null; // SQL 실행 후 결과를 받기 위한 변수
		ResultSet rset = null; // SELECT 문 실행된 조회결과값 담길 변수
		
		// 실행 SQL 문 완성시 세미콜론 X
		// SELECT *
		// FROM PRODUCT
		// String sql = "SELECT * FROM PRODUCT"; // 이부파일 형식으로 경로 지정해줘야함. 가속성이 좋아짐
		String sql = prop.getProperty("selectAll");
		
		try {
			
			pstmt = conn.prepareStatement(sql); //PreparedStatement 객체 생성
			
			rset = pstmt.executeQuery(); // SQL SELECT 을 실행 후 결과 받기
			
			while(rset.next()) {
				
				Product p = new Product();
				
				p = new Product(rset.getString("PRODUCT_ID"),	
								rset.getString("PRODUCT_NAME"),
								rset.getInt("PRICE"),
								rset.getString("DESCRIPTION"),
								rset.getInt("STOCK"));
				
				list.add(p); // 리스트 해당 객체에 add
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 객체 반납 (역순)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list; // 결과반납
	}
	
	// 입력받은 내용을 INSERT구문을 이용해서 처리할 메소드
	public int insertProduct(Connection conn, Product p) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertProduct");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getProductId());
			pstmt.setString(2, p.getProductName());
			pstmt.setInt(3, p.getPrice());
			pstmt.setString(4, p.getDescription());
			pstmt.setInt(5, p.getStock());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}		
	
	
	

	// 상품명을 키워드 검색 요청 기능을 SELECT 구문을 이용해서 처리할 메소드
	public ArrayList<Product> selectByProductName(Connection conn, String keyword) { 
		
		// 0) 필요한 변수들 셋팅
		ArrayList<Product> list = new ArrayList<>(); 
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 방법2) 
		
		String sql = prop.getProperty("selectByProductName");
		
		
		try {
			
			// 3_1) PreparedStatement 객체 생성 (SQL 문을 이 시점에서 넘길 예정)
			pstmt = conn.prepareStatement(sql);
			
			// 3_2) 미완성된 SQL 문을 완성시키기
			pstmt.setString(1, "%" + keyword + "%"); // '%keyword%' => 잘 실행됨
			
			// 4, 5) SQL 문 실행 후 결과 받기
			rset = pstmt.executeQuery();
			
			// 6_1) ResultSet 에 담겨있는 결과를 VO 객체로 옮겨담기
			while(rset.next()) {
				
				/*
				Product p = new Product(rset.getString("PRODUCT_ID"),
									  rset.getString("PRODUCT_NAME"), 
									  rset.getInt("PRICE"),
									  rset.getString("DESCRIPTION"), 
									  rset.getInt("STOCK"));
				
				// ArrayList 에 추가
				list.add(p);
				*/
				
				list.add(new Product(rset.getString("PRODUCT_ID"),
									rset.getString("PRODUCT_NAME"), 
									rset.getInt("PRICE"),
									rset.getString("DESCRIPTION"), 
									rset.getInt("STOCK")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 자원 반납
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		// 8) 결과 반환
		return list;
	}	
	
	// 상품 정보 수정 요청이 들어왔을 때 UPDATE 구문을 실행하는 메소드
	public int updateProduct(Connection conn, Product p) { // UPDATE문
		
		// 변수 담기
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateProduct");
		/*
		 	UPDATE PRODUCT
			SET PRICE = ?,
				DESCRIPTION = ?,
				STOCK = ?
			WHERE PRODUCT_ID = ?
		 */
		
	
		try {
			// PreparedStatement 객체 생성, 쿼리 준비
			pstmt = conn.prepareStatement(sql);
			
			// 미완성 쿼리문 완성
			pstmt.setInt(1, p.getPrice());
			pstmt.setString(2, p.getDescription());
			pstmt.setInt(3, p.getStock());
			pstmt.setString(4, p.getProductId());
			
			// sql 실행 및 결과값 담기
			result = pstmt.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// PreparedStatement 반환
			JDBCTemplate.close(pstmt);
		}
		
		return result;
		
	}
	
	public int deleteProduct(Connection conn, String productId) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, productId);

			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}	
	
	//6. 상품상세정보로 검색하기 (키워드 x)
	public ArrayList<Product> selectByDescription(Connection conn,String description) {
		//0)필요한 변수들 셋팅
		//조회된 한 정보에 대한 정보를 담을 변수
		ArrayList<Product> list = new ArrayList<>();
		PreparedStatement pstmt = null; //sql문 실행 후 결과를 받기 위한 변수
		ResultSet rset = null; //select문이 실행된 조회결과값들이 처음에 실질적으로 담길 변수
		
		//1)실행할 sql문
		//SELECT * FROM PRODUCT WHERE DESCRIPTION = ?
		String sql = prop.getProperty("selctByDescription");
			
		try {
			//2)PreparedStatement 객체 생성(sql문을 이 시점에서 미리 보내두기)
			pstmt = conn.prepareStatement(sql);
			//3) 미완성된 sql문을 값을 넣는 작업
			pstmt.setString(1, description);
			//4) sql문(select문 실행 후 결과를 받기)
			rset = pstmt.executeQuery();
			//5) 현재 조회 결과가 담긴 ResultSet에서 한행씩 뽑아서 vo 객체에 담기
			//만약 next()메소드 실행 후 뽑아낼 게 있다면 true 반환
			while(rset.next()) { 
				Product p = new Product();
				   p = new Product(rset.getString("PRODUCT_ID"),
						           rset.getString("PRODUCT_NAME"),
						           rset.getInt("PRICE"),
						           rset.getString("DESCRIPTION"),
						           rset.getInt("STOCK"));
				   list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{ //6)자원 반납(역순)
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);			
		}//7)결과 반환
		return list;
	}
	
	
	
}

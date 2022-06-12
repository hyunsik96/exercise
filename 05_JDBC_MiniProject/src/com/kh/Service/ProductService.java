package com.kh.Service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;


public class ProductService {
	
	public ArrayList<Product> selectAll(){
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Product> list = new ProductDao().selectAll(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	public int insertProduct(Product p) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드 호출 (Connection, 전달값 둘 다 매개변수로 넘기기)
		int result = new ProductDao().insertProduct(conn, p);
		
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
	
	public ArrayList<Product> selectByProductName(String keyword) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 메소드를 호출 (Connection을 매개변수로 넘기기)
		ArrayList<Product> list = new ProductDao().selectByProductName(conn, keyword);
		
		// 3) 트랜잭션 처리
		
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return list;
		
	}

	public int updateProduct(Product p) {
		// 1. Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. dao 호출
		int result = new ProductDao().updateProduct(conn, p);
		
		// 3. 트랜잭션 처리 - update
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		// 4. Connection 객체 반납
		JDBCTemplate.close(conn);

		// 5. Controller 클래스로 반환
		return result;
		
	}

	public int deleteProduct(String productId) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new ProductDao().deleteProduct(conn, productId);
		
		if(result > 0) {
			
			JDBCTemplate.commit(conn);
		}
		else {
			
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}	
	
	//6. 상품상세정보로 검색하기 (키워드 x)
	public ArrayList<Product> selectByDescription(String description) {
		//1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		//2) DAO 메소드 호출 (Connection 을 매개변수로 넘기기)
		ArrayList<Product> list = new ProductDao().selectByDescription(conn,description);
		//3) 트랜잭션 처리 =>select 문은 생략
		//4) connection 객체 반납
		JDBCTemplate.close(conn);
		//5) 결과값을 Controller로 리턴~~
		return list;
	}	
	
}


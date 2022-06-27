package com.kh.controller;

import java.util.ArrayList;

import com.kh.Service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductView;

public class ProductController {

	public void selectAll() {
		ArrayList<Product> list = new ProductService().selectAll();
		
		if(list.isEmpty()) {
			
			new ProductView().displayNodata("전체 조회 결과가 없습니다.");
		}
		else {
			
			new ProductView().displayList(list);
		}
	}
	
	// 상품 추가 요청을 처리해주는 메소드 ㅋㅋㅋㅋㅋ
	public void insertProduct(String productId, String productName,
			  				  int price, String description, int stock) {
		
		// 1) 전달된 데이터들을 Product 객체에 담기 (Product 객체 생성)
		Product p = new Product(productId, productName, price, description, stock);
		
		// 2) Service 의 메소드 호출 (이때, 위에서 가공된 Product 객체를 매개변수로 넘기겠다)
		int result = new ProductService().insertProduct(p);
		
		// 3) 성공 / 실패에 따라 응답 화면을 지정 (View 의 메소드 호출)
		if(result > 0) { // 성공
			
			// 상품 추가 성공 문구를 띄어주는 화면 단으로 메소드 호출
			new ProductView().displaySuccess("상품 추가 성공");
		}
		else { // 실패
			
			// 실패 문구를 띄워주는 화면 단으로 메소드 호출
			new ProductView().displayFail("상품 추가 실패");
		}
	}
	
	// 상품명을 키워드 검색 요청시 처리해주는 메소드
	public void selectByProductName(String keyword) {
		
		// VO객체로 가공할 필요가 없음
		
		// 2) Service로 요청
		ArrayList<Product> list = new ProductService().selectByProductName(keyword);
		
		// 3) 검색결과가 있는지 없는지 여부에 따라 뷰 지정
		if(list.isEmpty()) { // 검색결과가 없는 경우
			
			new ProductView().displayNodata(keyword + "에 대한 검색 결과가 없습니다.");
			
		}
		else { // 검색 결과가 있는 경우
			
			new ProductView().displayList(list);
			
		}		
	}
	
	// 상품명을 받아 수정하는 메소드
	public void updateProduct(String productId, int newPrice, String newDescription, int newStock) {
		
		// 받은 매개변수를 VO객체로 가공해야하나? - yes
		// Product에 해당 매개변수만을 받는 생성자가 없으므로 추가해줘야한다. 
		
		Product p = new Product(productId, newPrice, newDescription, newStock);
		
		// Service 클래스 updateProduct() 호출 매개변수 보냄
		int result = new ProductService().updateProduct(p);
		
		// 성공 / 실패 여부에 따라 응답화면 보이기
		if(result > 0) { // 수정 성공
			new ProductView().displaySuccess("상품 정보 수정 완료");
		} 
		else {
			new ProductView().displayFail("상품 정보 수정 실패");
		}
	}
	
	public void deleteProduct(String productId) {
		
		int result = new ProductService().deleteProduct(productId);
		
		if(result > 0) {
			new ProductView().displaySuccess("상품 삭제 성공");
		}
		else {
			new ProductView().displayFail("상품 삭제 실패");
		}
	}
	
	//6. 상품상세정보로 검색하기 (키워드 x)
	public void selectByDescription(String description) {
		//1) DAO 메소드 호출=> 같은 정보가 나올수 있기 때문에 list로 만든다. 
		ArrayList<Product> list= new ProductService().selectByDescription(description);
		//2) 조회된 결과가 있는지 응답화면 지정
		if(list.isEmpty()) { //조회 결과가 없는 경우
			new ProductView().displayNodata(description+"에 해당하는 검색결과가 없습니다");
		}else { // 조회 결과가 있는 경우
			new ProductView().displayList(list);
		}	
	}
	
	
}



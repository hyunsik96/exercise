package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Product;

public class ProductView {

	private Scanner sc = new Scanner(System.in);
	
	// 어느 메소드에든 간에 바로 ProductController의 메소드로 요청할 수 있도록 객체 생성(전역변수)
	private ProductController pc = new ProductController();
	
	// 메인 화면
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("******** 당산전자마트 제품관리 프로그램 ********");
			System.out.println("1. 상품 전체 조회하기");
			System.out.println("2. 상품 추가 하기");
			System.out.println("3. 상품명 검색 하기");
			System.out.println("4. 상품 정보 수정하기");
			System.out.println("5. 상품 삭제 하기");
			System.out.println("6. 상품 상세정보로 검색 하기");
			System.out.println("0. 프로그램 종료하기");
			
			System.out.println("------------------------------------");
			System.out.print("이용할 메뉴 선택: ");
			
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
				case 1 :
						selectAll();
						break;
				case 2 :
						insertProduct();
						break;
				case 3 :
						selectByProductName();
						break;
				case 4 :
						updateProduct();
						break;
				case 5 :
						deleteProduct();
						break;
				case 6 :
						selectByDescription();
						break;
				case 0 :
						System.out.println("프로그램을 종료합니다.");
						return;
				default :
						System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
		
	}

	// 1. 상품 전체 조회하기
	public void selectAll() {
		
		System.out.println("***** 상품 전체 조회 *****");
		
		pc.selectAll();
	}
	
	// 2. 상품 추가 하기
	public void insertProduct() {
		
		System.out.println("***** 상품 추가 *****");
		
		System.out.print("상품아이디: ");
		String productId = sc.nextLine();
		
		System.out.print("상품명: ");
		String productName = sc.nextLine();
		
		System.out.print("상품가격: ");
		int price = sc.nextInt();
		sc.nextLine();
		
		System.out.print("상품상세정보: ");
		String description = sc.nextLine();
		
		System.out.print("재고: ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		pc.insertProduct(productId, productName, price, description, stock);
			
	}
	
	// 3. 상품명 검색 하기
	public void selectByProductName() {
		System.out.println("***** 상품 이름 키워드 검색 *****");
		
		// 필요한 데이터 : 검색을 원하는 아이디 한 개
		System.out.print("상품 이름 키워드 입력: ");
		String keyword = sc.nextLine();
		
		// keyword 변수를 MemberController의 어떤 메소드한테 대신 검색해달라고 넘겨줌
		pc.selectByProductName(keyword);
	}
	
	// 4. 상품 정보 수정하기
	public void updateProduct() {
		// 받아야하는 값 - 상품 아이디, 가격, 상품상세정보, 수량
		System.out.println("***** 상품 정보 수정하기 *****");
		System.out.print("수정을 원하는 상품 아이디: ");
		String productId = sc.nextLine();
		
		System.out.print("상품 가격을 얼마로 수정하시겠습니까?: ");
		int newPrice = sc.nextInt();
		sc.nextLine();
		
		System.out.print("상품 상세 정보를 어떻게 수정하시겠습니까?: ");
		String newDescription = sc.nextLine();
		
		System.out.print("재고를 몇 개로 수정하겠습니까?: ");
		int newStock = sc.nextInt();
		sc.nextLine();
		
		// Controller 메소드 호출하여 매개변수 전달
		pc.updateProduct(productId, newPrice, newDescription, newStock);
		
		
	}
	
	// 5. 상품 삭제하기
	public void deleteProduct() {
		
		System.out.println("***** 상품 삭제 *****");
		
		System.out.print("삭제할 상품 아이디: ");
		String productId = sc.nextLine();
		
		pc.deleteProduct(productId);		
	}
	
	// 6. 상품 상세정보로 검색
	public void selectByDescription() {
	   System.out.println("***** 상품 상세정보로 검색 *****");
	   
	   //필요한 데이터 : 검색을 원하는 상품 상세정보 한개
	   System.out.print("검색할 상품 상세정보: ");
	   String description = sc.nextLine();
	   
	   //ProductController 의 메소드를 호출해서 상세 정보 요청
	   pc.selectByDescription(description);
	}	
	
	//======= 응답화면 =======
	
	// 성공 메세지 출력화면 메소드
	public void displaySuccess(String message) {
		
		System.out.println("서비스 요청 성공: " + message + "\n");
	}
	
	// 실패 메세지 출력화면 메소드
	public void displayFail(String message) {
		
		System.out.println("서비스 요청 실패: " + message + "\n");
	}
	
	// 조회서비스 요청 시 조회결과가 없을 때 보게 될 화면을 만드는 메소드
	public void displayNodata(String message) {

		System.out.println(message + "\n");
	}
	
	// 조회 서비스 요청 시 여러 행이 조회된 결과를 보게 될 화면을 만드는 메소드
	public void displayList(ArrayList<Product> list) {
		
		System.out.println("조회된 데이터는 총" + list.size() + "건 입니다.");
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		System.out.println();
	}
	
	// 조회 서비스 요청 시 조회된 결과를 받아서 보게 될 화면을 만든 메소드
	public void displayOne(Product p) {
		
		System.out.println("조회된 데이터는 다음과 같습니다.");
		
		System.out.println(p + "\n");
	}
	
}

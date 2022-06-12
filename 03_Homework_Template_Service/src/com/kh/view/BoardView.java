package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.BoardController;
import com.kh.model.vo.Board;

public class BoardView {
	
	private Scanner sc = new Scanner(System.in);
	private BoardController bc = new BoardController();

	// 메인화면
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("********** 자유게시판 **********");
			selectAll();
			
			System.out.println("\n-------------------------------------------------------");
			System.out.println("1. 게시글 작성하기");
			System.out.println("2. 게시글 수정하기");
			System.out.println("3. 게시글 삭제하기");
			System.out.println("4. 게시글 제목 키워드 검색");
			System.out.println("5. 게시글 작성자 검색");
			System.out.println("0. 자유게시판 종료");
			System.out.print("메뉴 입력 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 : insertBoard(); break;
			case 2 : break;
			case 3 : break;
			case 4 : break;
			case 5 : break;
			case 0 : System.out.println("자유게시판을 종료합니다."); return;
			default : System.out.println("잘못된 메뉴입니다. 다시 입력해주세요.");
			}
		}
	}

	// ------------ 요청화면 ------------
	// 게시글 전체 조회 화면
	private void selectAll() {
		
		ArrayList<Board> list = bc.selectAll();
		
		if(list.isEmpty()) {
			System.out.println("게시글이 없습니다.");
		}
		else {

			for(Board b : list) {
				System.out.println(b);
			}
		}
	}
	
	// 게시글 작성 화면
	private void insertBoard() {
		
		System.out.println("----- 게시글 작성하기 -----");
		System.out.print("작성자 번호 : ");
		String writerNo = sc.nextLine();
		System.out.print("게시글 제목 : ");
		String title = sc.nextLine();
		System.out.print("게시글 내용 : ");
		String content = sc.nextLine();
		
		bc.insertBoard(writerNo, title, content);
	}
	
	// ------------ 응답화면 ------------

	public void displaySuccess(String message) {
		System.out.println("서비스 요청 성공 : " + message);
	}

	public void displayFail(String message) {
		System.out.println("서비스 요청 실패 : " + message);
		
	}

}

package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.BoardService;
import com.kh.model.vo.Board;
import com.kh.view.BoardView;

public class BoardController {

	public ArrayList<Board> selectAll() {
		
		// 2) Service 호출
		ArrayList<Board> list = new BoardService().selectAll();
		
		// 3) 응답뷰 지정 대신 결과 반환
		return list;
	}

	public void insertBoard(String writerNo, String title, String content) {

		// 1) VO 가공
		Board b = new Board();
		b.setWriter(writerNo);
		b.setTitle(title);
		b.setContent(content);
		
		// 2) Service 호출
		int result = new BoardService().insertBoard(b);
		
		// 3) 응답뷰지정
		if(result > 0) {
			new BoardView().displaySuccess("게시글 등록 성공!");
		}
		else {
			new BoardView().displayFail("게시글 등록 실패!");
		}
	}

}

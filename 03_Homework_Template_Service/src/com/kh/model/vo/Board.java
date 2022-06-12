package com.kh.model.vo;

import java.sql.Date;

public class Board {

	// 필드부
    private int bno; // BNO NUMBER PRIMARY KEY, -- 게시글 번호
    private String title; // TITLE VARCHAR2(50) NOT NULL, -- 게시글 제목
    private String content; // CONTENT VARCHAR2(500) NOT NULL, -- 게시글 내용
    private Date createDate; // CREATE_DATE DATE DEFAULT SYSDATE, -- 작성일
    private String writer; // WRITER NUMBER, -- 작성자 (회원번호) "1" / "admin"
    private String deleteYN; // DELETE_YN CHAR(2) DEFAULT 'N', -- 게시글 삭제 여부 ('N' : 삭제가 안된상태 / 'Y' : 삭제가 된 상태)
	
	// 생성자부
    public Board() {
		super();
	}

	public Board(int bno, String title, String content, Date createDate, String writer, String deleteYN) {
		super();
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.createDate = createDate;
		this.writer = writer;
		this.deleteYN = deleteYN;
	}
	
	// 게시글 리스트 전체 조회용 생성자
	public Board(int bno, String title, Date createDate, String writer) {
		super();
		this.bno = bno;
		this.title = title;
		this.createDate = createDate;
		this.writer = writer;
	}

	// 메소드부
	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getDeleteYN() {
		return deleteYN;
	}

	public void setDeleteYN(String deleteYN) {
		this.deleteYN = deleteYN;
	}

	@Override
	public String toString() {
		return "Board [글번호=" + bno + ", 제목=" + title + ", 작성자=" + writer + ", 작성일=" + createDate + "]";
	}
}

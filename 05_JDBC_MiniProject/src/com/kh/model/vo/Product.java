package com.kh.model.vo;

public class Product {

	/*
	PRODUCT_ID VARCHAR2(15) CONSTRAINT P_ID_PK PRIMARY KEY, -- 상품아이디
    PRODUCT_NAME VARCHAR2(30) CONSTRAINT P_NAME_NN NOT NULL, -- 상품명
    PRICE NUMBER CONSTRAINT P_PRICE_NN NOT NULL, -- 상품가격
    DESCRIPTION VARCHAR2(20), -- 상품상세정보 --windows10, XCODE4
    STOCK NUMBER CONSTRAINT P_STOCK_NN NOT NULL
	 */
	
	// 필드부
	private String productId;
	private String productName;
	private int price;
	private String description;
	private int stock;
	
	// 생성자부
	public Product() {
		
	}
	
	public Product(String productId, String productName, int price, String description, int stock) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.stock = stock;
	}
	
	public Product(String productId, int price, String description, int stock) {
		super();
		this.productId = productId;
		this.price = price;
		this.description = description;
		this.stock = stock;
	}

	// 메서드부 
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "상품 [상품아이디=" + productId + ", 상품명=" + productName + ", 상품가격=" + price
				+ ", 상품상세정보=" + description + ", 재고=" + stock + "]";
	}
}

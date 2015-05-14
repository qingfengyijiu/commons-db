package com.zhangjx.commons.db.enumeration;

public enum Operation {
	EQUAL(" = ", 0),
	NOT_EQUAL(" != ", 1),
	LT(" < ", 2),
	GT(" > ", 3),
	LE(" <= ", 4),
	GE(" >= ", 5),
	IS_NULL(" IS NULL ", 6),
	IS_NOT_NULL(" IS NOT NULL ", 7),
	IN(" IN ", 8);
	
	private String symbol;
	
	private int index;
	
	Operation(String symbol, int index) {
		this.symbol = symbol;
		this.index = index;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public boolean equals(Operation operation) {
		return this.index == operation.getIndex();
	}
}
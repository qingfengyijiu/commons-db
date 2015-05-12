package com.zhangjx.commons.db.enumeration;

public enum Order {
		
	ASC("ASC", 1),
	DESC("DESC", 0);
	
	private String name;
	
	private int value;
	
	Order(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
		
	public boolean equals(Order order) {
		return this.value == order.getValue();
	}
		
}

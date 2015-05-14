package com.zhangjx.commons.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface DynamicParameter {

	Operation operation() default Operation.EQUAL;
	
	public enum Operation {
		EQUAL(" = ", 0),
		NOT_EQUAL(" != ", 1),
		LT(" < ", 2),
		GT(" > ", 3),
		LE(" <= ", 4),
		GE(" >= ", 5),
		IS_NULL(" IS NULL ", 6),
		IS_NOT_NULL(" IS NOT NULL ", 7);
		
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
	
}

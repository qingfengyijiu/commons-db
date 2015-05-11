package com.zhangjx.commons.db.builder.sql;

public class WhereBuilder extends WherePartBuilder {
	
	public static WhereBuilder start() {
		return new WhereBuilder();
	}
	
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder();
		sb.append(WHERE);
		sb.append(WORD_SEPARATOR);
		sb.append(super.build());
		return sb.toString();
	}
	
}

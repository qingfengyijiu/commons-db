package com.zhangjx.commons.db.builder;

import com.zhangjx.commons.db.esapi.ESAPI;

public interface IWherePartBuilder {

	public abstract IWherePartBuilder esapi(ESAPI esapi);

	public abstract IWherePartBuilder add(String sql, Object[] values);

	public abstract IWherePartBuilder add(String sql, Object value);

	public abstract String build();

}
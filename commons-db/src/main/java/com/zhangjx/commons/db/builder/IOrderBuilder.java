package com.zhangjx.commons.db.builder;

import com.zhangjx.commons.db.builder.sql.OrderBuilder.Order;
import com.zhangjx.commons.db.esapi.ESAPI;

public interface IOrderBuilder {

	public abstract IOrderBuilder esapi(ESAPI esapi);

	public abstract IOrderBuilder add(String columnName, Order order);

	public abstract String build();

}
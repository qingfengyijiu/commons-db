package com.zhangjx.commons.db.test;

import org.junit.Assert;
import org.junit.Test;

import com.zhangjx.commons.db.builder.sql.OrderBuilder;
import com.zhangjx.commons.db.builder.sql.OrderBuilder.Order;
import com.zhangjx.commons.db.esapi.ESAPI;
import com.zhangjx.commons.db.esapi.ESAPIFactory;
import com.zhangjx.commons.db.esapi.ESAPIFactory.Database;

public class TestOrderBuilder {

	@Test
	public void test() {
		String real = OrderBuilder.start().add("a", Order.ASC).add("b", Order.DESC).add("c", Order.DESC).build();
		String expect = "ORDER BY a ASC, b DESC, c DESC ";
		Assert.assertEquals(expect, real);
	}
	
	@Test
	public void testESAPI() {
		ESAPI esapi = ESAPIFactory.getESAPI(Database.ORACLE);
		String real = OrderBuilder.start().esapi(esapi).add("'a", Order.ASC).add("b", Order.DESC).add("c", Order.DESC).build();
		String expect = "order by 'a asc, b desc, c desc ";
		Assert.assertNotEquals(expect, real);
	}
}

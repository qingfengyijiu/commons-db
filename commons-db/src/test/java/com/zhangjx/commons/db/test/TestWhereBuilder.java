package com.zhangjx.commons.db.test;


import org.junit.Assert;
import org.junit.Test;

import com.zhangjx.commons.db.builder.sql.WhereBuilder;
import com.zhangjx.commons.db.esapi.ESAPI;
import com.zhangjx.commons.db.esapi.ESAPIFactory;
import com.zhangjx.commons.db.esapi.ESAPIFactory.Database;

public class TestWhereBuilder {

	@Test
	public void test() {
		String real = WhereBuilder.start().add("a = %s", "ff").add("b > %d", 2).build();
		String expected = "WHERE a = ff AND b > 2 ";
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void testESAPI() {
		ESAPI esapi = ESAPIFactory.getESAPI(Database.ORACLE);
		String real = WhereBuilder.start().esapi(esapi).add("a = %s", "1fsdf21'").add("b > %d", 2).build();
		String expected = "WHERE a = 1fsdf21' AND b > 2 ";
		Assert.assertNotEquals(expected, real);
	}
	
}

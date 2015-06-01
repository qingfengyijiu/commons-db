package com.zhangjx.commons.db.test;

import org.junit.Assert;
import org.junit.Test;

import com.zhangjx.commons.db.builder.sql.WherePartBuilder;
import com.zhangjx.commons.db.enumeration.Database;
import com.zhangjx.commons.db.esapi.ESAPI;
import com.zhangjx.commons.db.esapi.ESAPIFactory;

public class TestWherePartBuilder {

	@Test
	public void test() {
		String real = WherePartBuilder.start().add("a = %s", "ff").add("b > %d", 2).build();
		String expected = "a = ff AND b > 2 ";
		Assert.assertEquals(expected, real);
	}
	
	@Test
	public void testESAPI() {
		ESAPI esapi = ESAPIFactory.getESAPI(Database.ORACLE);
		String real = WherePartBuilder.start().esapi(esapi).add("a = %s", "1fsdf21").add("b > %d", 2).build();
		String expected = "a = 1fsdf21' AND b > 2 ";
		Assert.assertNotEquals(expected, real);
	}
	
}

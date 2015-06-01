package com.zhangjx.commons.db.test.esapi;

import org.junit.Assert;
import org.junit.Test;

import com.zhangjx.commons.db.enumeration.Database;
import com.zhangjx.commons.db.esapi.ESAPI;
import com.zhangjx.commons.db.esapi.ESAPIFactory;

public class TestESAPI {

	@Test
	public void testEncodeForSQL() {
		ESAPI esapi = ESAPIFactory.getESAPI(Database.ORACLE);
		String sql = "order by 'a asc, b desc, c desc ";
		String real = esapi.encodeForSQL(sql);
		String expect = sql;
		Assert.assertNotEquals(expect, real);
	}
}

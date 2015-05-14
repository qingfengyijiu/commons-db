package com.zhangjx.commons.db.test.utils;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import com.zhangjx.commons.db.utils.AnnotationUtils;

public class TestAnnotationUtils {

	@Test
	public void testGetTableName() {
		String tableName = AnnotationUtils.getTableName(User.class);
		String expected = "USER";
		Assert.assertEquals(expected, tableName);
	}
	
	@Test
	public void testGetTableName1() {
		String tableName = AnnotationUtils.getTableName(VipUser.class);
		String expected = "VipUser";
		Assert.assertEquals(expected, tableName);
	}
	
	@Test
	public void testGetTableName2() {
		String tableName = AnnotationUtils.getTableName(VipUser.class, true);
		String expected = "VIP_USER";
		Assert.assertEquals(expected, tableName);
	}
	
	@Test
	public void testGetTableName3() {
		String tableName = AnnotationUtils.getTableName(Address.class);
		String expected = "Address";
		Assert.assertEquals(expected, tableName);
	}
	
	@Test
	public void testGetColumnName() {
		try {
			Field field = VipUser.class.getDeclaredField("address");
			String columnName = AnnotationUtils.getColumnName(field);
			String expected = "address";
			Assert.assertEquals(expected,columnName);
		} catch (Exception e) {
			// ignore
		}
	}
	
	@Test
	public void testGetColumnName1() {
		try {
			Field field = Address.class.getDeclaredField("city");
			String columnName = AnnotationUtils.getColumnName(field);
			String expected = "city";
			Assert.assertEquals(expected,columnName);
		} catch (Exception e) {
			// ignore
		}
	}
	
	@Test
	public void testGetColumnName2() {
		try {
			Field field = VipUser.class.getDeclaredField("address");
			String columnName = AnnotationUtils.getColumnName(field, true);
			String expected = "ADDRESS";
			Assert.assertEquals(expected,columnName);
		} catch (Exception e) {
			// ignore
		}
	}
	
	@Test
	public void testMapCamelCaseToUnderscore() {
		String origin = "testHelloWorld";
		String mapped = AnnotationUtils.mapCamelCaseToUnderscore(origin);
		String expected = "TEST_HELLO_WORLD";
		Assert.assertEquals(expected, mapped);
	}
	
	@Test
	public void testMapUnderscoreToCamelCase() {
		String origin = "TEST_HELLO_WORLD";
		String mapped = AnnotationUtils.mapUnderscoreToCamelCase(origin);
		String expected = "testHelloWorld";
		Assert.assertEquals(expected, mapped);
	}
	
	@Test
	public void testGetSetSql() {
		User user = new User();
		user.setName("test");
		Address address = new Address();
		user.setAddress(address);
		String sql = AnnotationUtils.getSetSql(User.class, user);
		String expected = "SET NAME = 'test', ADDRESS = '2d'";
		Assert.assertEquals(expected, sql);
	}
	
}

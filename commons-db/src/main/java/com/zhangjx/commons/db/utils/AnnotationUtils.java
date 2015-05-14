package com.zhangjx.commons.db.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zhangjx.commons.db.annotation.DynamicParameter;
import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.enumeration.Operation;

public class AnnotationUtils {
	
	private static final String KEY_WHERE = "WHERE";
	
	private static final String KEY_ORDER = "ORDER";
	
	private static final String UNDERSCORE = "_";

	private AnnotationUtils() {
		
	}
	
	public static String getTableName(Class<?> entityClass) {
		return getTableName(entityClass, false);
	}
	
	public static String getTableName(Class<?> entityClass, boolean mapCamelCaseToUnderScore) {
		String tableName = null;
		Table table = entityClass.getAnnotation(Table.class);
		if(table != null) {
			tableName = table.name();
		}
		if(StringUtils.isBlank(tableName)) {
			tableName = entityClass.getSimpleName();
			if(mapCamelCaseToUnderScore) {
				tableName = mapCamelCaseToUnderscore(tableName);
			}
		}
		return tableName;
	}
	
	public static String getColumnName(Field field) {
		return getColumnName(field, false);
	}
	
	public static String getColumnName(Field field, boolean mapCamelCaseToUnderScore) {
		String columnName = null;
		Column column = field.getAnnotation(Column.class);
		if(column != null) {
			columnName = column.name();
		}
		if(columnName == null) {
			JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
			if(joinColumn != null) {
				columnName = joinColumn.name();
				if(mapCamelCaseToUnderScore) {
					columnName = mapCamelCaseToUnderscore(columnName);
				}
			}
		}
		if(StringUtils.isBlank(columnName)) {
			columnName = field.getName();
			if(mapCamelCaseToUnderScore) {
				columnName = mapCamelCaseToUnderscore(columnName);
			}
		}
		return columnName;
	}
	
	public static String mapUnderscoreToCamelCase(String origin) {
		StringTokenizer st = new StringTokenizer(origin, UNDERSCORE);
		List<String> parts = new ArrayList<String>();
		while(st.hasMoreTokens()) {
			String part = st.nextToken().toLowerCase();
			if(parts.size() > 0) {
				part = StringUtils.capitalize(part);
			}
			parts.add(part);
		}
		StringBuffer sb = new StringBuffer();
		for(String part : parts) {
			sb.append(part);
		}
		return sb.toString();
	}
	
	public static boolean isUppercase(char data) {
		Pattern pattern = Pattern.compile("[A-Z]");
		Matcher matcher = pattern.matcher(new String(new char[]{data}));
		return matcher.matches();
	}
	
	public static String mapCamelCaseToUnderscore(String origin) {
		List<String> parts = new ArrayList<String>();
		int index = 0;
		for(int i = 0; i < origin.length(); i++) {
			if(isUppercase(origin.charAt(i)) && i > 0) {
				String temp = origin.substring(index, i);
				parts.add(temp);
				index = i;
			} else if(i == origin.length() - 1 && index < i) {
				String temp = origin.substring(index, origin.length());
				parts.add(temp);
			}
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < parts.size(); i++) {
			sb.append(parts.get(i).toUpperCase());
			if(i < parts.size() - 1) {
				sb.append(UNDERSCORE);
			}
		}
		return sb.toString();
	}
	
	public static Method getGetterMethod(Class<?> clazz, Field field) {
		try {
			String fieldName = field.getName();
			String getterMethodName = "get" + StringUtils.capitalize(fieldName);
			Method method = clazz.getDeclaredMethod(getterMethodName, new Class[]{});
			return method;
		} catch (Exception e) {
			// ignore
		} 
		return null;
	}
	
	public static String getSetSql(Class<? extends BaseEntity> entityClass, BaseEntity t) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = entityClass.getDeclaredFields();
		for(Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			JoinColumn joinColumn = null;
			if(column == null) {
				joinColumn = field.getAnnotation(JoinColumn.class);
			}
			if(column != null || joinColumn != null) {
				String columnName = getColumnName(field, true);
				Method method = getGetterMethod(entityClass, field);
				Object value = null;
				try {
					value = method.invoke(t, new Object[]{});
				} catch (Exception e) {
					System.out.println(e);
					// ignore
				} 
				if(value != null) {
					if(value instanceof BaseEntity) {
						value = ((BaseEntity) value).getId();
					}
					sb.append(columnName);
					sb.append(" = ");
					if(value instanceof String) {
						sb.append("'");
						sb.append(value);
						sb.append("'");
					} else {
						sb.append(value);
					}
					sb.append(", ");
				}
			}
		}
		String sql = sb.toString();
		sql = sql.trim();
		if(StringUtils.isNotBlank(sql)) {
			sql = "SET " + sql;
			sql = sql.substring(0, sql.length() - 1);
		}
		return sql;
	}
	
	public static String getBaseQuery(Class<? extends BaseEntity> clazz) {
		String tableName = getTableName(clazz, true);
		String sql = "SELECT * FROM %s";
		return String.format(sql, new Object[]{tableName});
	}
	
	public static String getParamSql(Class<? extends BaseEntity> entityClass, Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = entityClass.getDeclaredFields();
		int count = 0;
		for(int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			DynamicParameter dp = field.getAnnotation(DynamicParameter.class);
			if(dp != null && params.containsKey(fieldName)) {
				Operation operation = dp.operation();
				count++;
				String columnName = getColumnName(field);
				sb.append(" AND ");
				sb.append(columnName);
				sb.append(operation.getSymbol());
				if(!operation.equals(Operation.IS_NULL) && !operation.equals(Operation.IS_NOT_NULL)) {
					Object value = params.get(fieldName);
					if(value instanceof String) {
						sb.append("'");
						sb.append(value);
						sb.append("'");
					}
				}
			}
		}
		if(params.containsKey(KEY_WHERE)) {
			String value = (String) params.get(KEY_WHERE);
			if(count > 0) {
				sb.append(" AND ");
			}
			sb.append(value);
		}
		if(params.containsKey(KEY_ORDER)) {
			String value = (String) params.get(KEY_ORDER);
			sb.append(value);
		}
		String sql = sb.toString();
		sql = StringUtils.trim(sql);
		sql = sql.substring("AND".length(), sql.length());
		return sql;
	}
	
}

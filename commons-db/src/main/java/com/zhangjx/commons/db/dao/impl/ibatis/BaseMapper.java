package com.zhangjx.commons.db.dao.impl.ibatis;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.zhangjx.commons.db.dao.BaseDao;
import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.pager.Pager;
import com.zhangjx.commons.db.utils.AnnotationUtils;
import com.zhangjx.commons.io.Resources;

import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.SqlSessionTemplate;

public class BaseMapper<T extends BaseEntity> implements BaseDao<T> {
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private Class<? extends BaseEntity> entityClass;
	
	public BaseMapper(Class<T> t) {
		this.entityClass = t;
	}
	
	@SuppressWarnings("unchecked")
	public BaseMapper(String className) {
		try {
			this.entityClass = (Class<? extends BaseEntity>) Resources.classForName(className);
		} catch (ClassNotFoundException e) {
		}
	}
	
	public Class<? extends BaseEntity> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<? extends BaseEntity> entityClass) {
		this.entityClass = entityClass;
	}
	
	protected String _getTableName() {
		return AnnotationUtils.getTableName(this.entityClass, true);
	}
	
	protected String _getColumnName(Field field) {
		return AnnotationUtils.getColumnName(field, true);
	}
	
	
	public String getBaseQuery() {
		return AnnotationUtils.getBaseQuery(entityClass);
	}
	
	public String getParamSql(Map<String, Object> params) {
		return AnnotationUtils.getParamSql(entityClass, params);
	}

	public void insert(T t) {
		Connection connection = this.sqlSessionTemplate.getConnection();
		Transaction tx = this.sqlSessionTemplate.getConfiguration().getEnvironment().getTransactionFactory().newTransaction(connection);
		
	}

	public void update(T t) {
		// TODO Auto-generated method stub
		
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	public T selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public T selectByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> listByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> listByParam(Map<String, Object> params, Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	public void batchUpdate(Map<String, Object> params,
			Map<String, Object> updates) {
		// TODO Auto-generated method stub
		
	}

	public void batchDelete(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}


}

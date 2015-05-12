package com.zhangjx.commons.db.dao.impl.jpql;

import java.util.List;
import java.util.Map;

import com.zhangjx.commons.db.dao.BaseDao;
import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.pager.Pager;

public abstract class AbstractJpqlBaseDao<T extends BaseEntity> implements BaseDao<T> {

	
	public void insert(T t) {
	}

	public void update(T t) {
		// TODO Auto-generated method stub
		
	}

	public void merge(T t) {
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

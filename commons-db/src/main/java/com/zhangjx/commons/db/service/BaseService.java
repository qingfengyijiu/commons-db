package com.zhangjx.commons.db.service;

import java.util.List;
import java.util.Map;

import com.zhangjx.commons.db.dao.BaseDao;
import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.pager.Pager;

public interface BaseService<T extends BaseEntity, D extends BaseDao<T>> {
	
	D getDao();

	void insert(T t);
	
	void update(T t);
	
	void delete(String id);
	
	T selectById(String id);
	
	T selectByParam(Map<String, Object> params);
	
	List<T> listByParam(Map<String, Object> params);
	
	List<T> listByParam(Map<String, Object> params, Pager pager);
	
	void batchUpdate(Map<String, Object> params, Map<String, Object> updates);
	
	void batchDelete(Map<String, Object> params);
	
}

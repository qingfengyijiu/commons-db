package com.zhangjx.commons.db.dao;

import java.util.List;
import java.util.Map;

import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.pager.Pager;

public interface BaseDao<T extends BaseEntity> {

	void insert(T t);
	
	void update(T t);
	
	void merge(T t);
	
	void delete(String id);
	
	T selectById(String id);
	
	T selectByParam(Map<String, Object> params);
	
	List<T> listByParam(Map<String, Object> params);
	
	List<T> listByParam(Map<String, Object> params, Pager pager);
	
	void batchUpdate(Map<String, Object> params, Map<String, Object> updates);
	
	void batchDelete(Map<String, Object> params);
	
}

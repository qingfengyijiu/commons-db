package com.zhangjx.commons.db.dao;

import com.zhangjx.commons.db.entity.BaseEntity;

public interface BaseDao<T extends BaseEntity> {

	void insert(T t);
	
	void update(T t);
	
	void delete(T t);
	
	T find(T t);
	
}

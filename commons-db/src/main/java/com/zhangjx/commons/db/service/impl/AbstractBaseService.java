package com.zhangjx.commons.db.service.impl;

import com.zhangjx.commons.db.dao.BaseDao;
import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.service.BaseService;

public abstract class AbstractBaseService<T extends BaseEntity, D extends BaseDao<T>> implements BaseService<T, D> {


	public void insert(T t) {
		this.getDao().insert(t);
	}

	public void update(T t) {
		this.getDao().update(t);
	}

	public void delete(T t) {
		this.getDao().delete(t);
	}
	
	public T find(T t) {
		return this.getDao().find(t);
	}


}

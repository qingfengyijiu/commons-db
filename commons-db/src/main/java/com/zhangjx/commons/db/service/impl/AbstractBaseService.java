package com.zhangjx.commons.db.service.impl;

import java.util.List;
import java.util.Map;

import com.zhangjx.commons.db.dao.BaseDao;
import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.pager.Pager;
import com.zhangjx.commons.db.service.BaseService;

public abstract class AbstractBaseService<T extends BaseEntity, D extends BaseDao<T>> implements BaseService<T, D> {


	public void insert(T t) {
		this.getDao().insert(t);
	}

	public void update(T t) {
		this.getDao().update(t);
	}

	public void merge(T t) {
		this.getDao().merge(t);
	}

	public void delete(String id) {
		this.getDao().delete(id);
	}

	public T selectById(String id) {
		return this.getDao().selectById(id);
	}

	public T selectByParam(Map<String, Object> params) {
		return this.getDao().selectByParam(params);
	}

	public List<T> listByParam(Map<String, Object> params) {
		return this.getDao().listByParam(params);
	}

	public List<T> listByParam(Map<String, Object> params, Pager pager) {
		return this.getDao().listByParam(params, pager);
	}

	public void batchUpdate(Map<String, Object> params,
			Map<String, Object> updates) {
		this.getDao().batchUpdate(params, updates);
	}

	public void batchDelete(Map<String, Object> params) {
		this.getDao().batchDelete(params);
	}

}

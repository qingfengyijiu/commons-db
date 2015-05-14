package com.zhangjx.commons.db.dao.impl.jpql;

import javax.persistence.EntityManager;

import com.zhangjx.commons.db.dao.BaseDao;
import com.zhangjx.commons.db.entity.BaseEntity;

public abstract class AbstractJpqlBaseDao<T extends BaseEntity> implements BaseDao<T> {

	public abstract EntityManager getEntityManager();
	
	public abstract Class<T> getEntityClass();
	
	public void insert(T t) {
		this.getEntityManager().persist(t);
	}

	public void update(T t) {
		this.getEntityManager().merge(t);
	}

	public void delete(String id) {
		this.getEntityManager().remove(this.selectById(id));
	}

	public T selectById(String id) {
		return this.getEntityManager().find(this.getEntityClass(), id);
	}

}

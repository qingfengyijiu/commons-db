package com.zhangjx.commons.db.dao.impl.ibatis;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zhangjx.commons.db.dao.BaseDao;
import com.zhangjx.commons.db.entity.BaseEntity;
import com.zhangjx.commons.db.enumeration.Database;
import com.zhangjx.commons.db.esapi.ESAPIFactory;
import com.zhangjx.commons.db.pager.Pager;
import com.zhangjx.commons.db.utils.AnnotationUtils;
import com.zhangjx.commons.io.Resources;
import com.zhangjx.commons.logging.Log;
import com.zhangjx.commons.logging.LogFactory;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

public class BaseMapper<T extends BaseEntity> implements BaseDao<T> {
	
	private Log log = LogFactory.getLog(BaseMapper.class);
	
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	
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
	
	protected String getTableName() {
		return AnnotationUtils.getTableName(this.entityClass, true);
	}
	
	protected String getColumnName(Field field) {
		return AnnotationUtils.getColumnName(field, true);
	}
	
	protected String getBaseQuery() {
		return AnnotationUtils.getBaseQuery(entityClass);
	}
	
	protected String getParamSql(Map<String, Object> params) {
		return AnnotationUtils.getParamSql(entityClass, params);
	}
	
	protected String getSetSql(T t) {
		return AnnotationUtils.getSetSql(entityClass, t);
	}
	
	protected String getInserSql(T t) {
		String tableName = this.getTableName();
		String setSql = this.getSetSql(t);
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(tableName);
		sb.append(" ");
		sb.append(setSql);
		return sb.toString();
	}
	
	protected String getUpdateSql(T t) {
		String tableName = this.getTableName();
		String setSql = this.getSetSql(t);
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(tableName);
		sb.append(" ");
		sb.append(setSql);
		sb.append("WHERE ID = '");
		sb.append(esapi(t.getId()));
		sb.append("'");
		return sb.toString();
	}
	
	protected String getDeleteSql(String id) {
		String tableName = this.getTableName();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(tableName);
		sb.append(" WHERE ID = '");
		sb.append(esapi(id));
		sb.append("'");
		return sb.toString();
	}
	
	protected String esapi(String origin) {
		Configuration configuration = this.sqlSessionTemplate.getConfiguration();
		String databaseId = configuration.getDatabaseId().toUpperCase();
		String encoded = null;
		if(databaseId.contains(Database.ORACLE.getDbName())) {
			encoded = ESAPIFactory.getESAPI(Database.ORACLE).encodeForSQL(origin);
		} else if(databaseId.contains(Database.MYSQL_STANDART.getDbName())) {
			encoded = ESAPIFactory.getESAPI(Database.ORACLE).encodeForSQL(origin);
		} else {
			encoded = origin;
		}
		return encoded;
	}
	
	protected MappedStatement getMappedStatemnt(String id, String sql, SqlCommandType sqlCommandType) {
		return getMappedStatemnt(id, sql, sqlCommandType, null);
	}
	
	protected void SetPagerTotalInfo(String sql, Connection connection, MappedStatement mappedStatement, Pager pager) {
		 // 记录总记录数
    	if(pager == null) return;
        String countSql = "select count(0) from (" + sql + ")";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            pager.setRecordsCount(totalCount);
            if(pager.getPageSize () > 0){
            	int totalPage = totalCount / pager.getPageSize() + ((totalCount % pager.getPageSize() == 0) ? 0 : 1);
            	pager.setTotalPage(totalPage);
            }
            else{
            	pager.setTotalPage(totalCount);
            }
            

        } catch (SQLException e) {
            log.error("Ignore this exception", e);
        } finally {
            try {
                if(rs!=null) rs.close();
            } catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
            try {
            	if(countStmt != null) countStmt.close();
            } catch (SQLException e) {
                log.error("Ignore this exception", e);
            }
        }
	}
	
	protected MappedStatement getMappedStatemnt(String id, String sql, SqlCommandType sqlCommandType, Pager pager) {
		String prefix = this.entityClass.getName() + ".";
		if(id.startsWith(prefix)) {
			id = prefix + id;
		}
		Configuration configuration = this.sqlSessionTemplate.getConfiguration();
		MappedStatement mappedStatement = configuration.getMappedStatement(id);
		if(mappedStatement == null) {
			SqlSource sqlSource = new SqlSourceBuilder(configuration).parse(sql, null, null);
			MappedStatement.Builder builder = new MappedStatement.Builder(configuration, id, sqlSource, sqlCommandType);
			mappedStatement = builder.build();;
			configuration.addMappedStatement(mappedStatement);
		}
		if(pager != null) {
			// 设置总记录数、总页数信息
			Connection connection = this.sqlSessionTemplate.getConnection();
			this.SetPagerTotalInfo(sql, connection, mappedStatement, pager);
			// 重写SQL
			MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
    				DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		return mappedStatement;
	}
	
	public void insert(T t) {
		String id = "insert";
		String sql = this.getInserSql(t);
		SqlCommandType sqlCommandType = SqlCommandType.INSERT;
		MappedStatement mappedStatemnt = this.getMappedStatemnt(id, sql, sqlCommandType);
		this.sqlSessionTemplate.insert(mappedStatemnt.getId());
	}

	public void update(T t) {
		String mapId = "update";
		String sql = this.getUpdateSql(t);
		SqlCommandType sqlCommandType = SqlCommandType.UPDATE;
		MappedStatement mappedStatemnt = this.getMappedStatemnt(mapId, sql, sqlCommandType);
		this.sqlSessionTemplate.update(mappedStatemnt.getId());
	}

	public void delete(String id) {
		String mapId = "delete";
		String sql = this.getDeleteSql(id);
		SqlCommandType sqlCommandType = SqlCommandType.DELETE;
		MappedStatement mappedStatemnt = this.getMappedStatemnt(mapId, sql, sqlCommandType);
		this.sqlSessionTemplate.delete(mappedStatemnt.getId());
	}

	public T selectById(String id) {
		String mapId = "selectById";
		StringBuilder sb = new StringBuilder();
		sb.append(this.getBaseQuery());
		sb.append(" WHERE ID = '");
		sb.append(esapi(id));
		sb.append("'");
		String sql = sb.toString();
		SqlCommandType sqlCommandType = SqlCommandType.SELECT;
		MappedStatement mappedStatemnt = this.getMappedStatemnt(mapId, sql, sqlCommandType);
		return this.sqlSessionTemplate.selectOne(mappedStatemnt.getId());
	}

	public T selectByParam(Map<String, Object> params) {
		String mapId = "selectByParam";
		StringBuilder sb = new StringBuilder();
		sb.append(this.getBaseQuery());
		sb.append(this.getParamSql(params));
		String sql = sb.toString();
		SqlCommandType sqlCommandType = SqlCommandType.SELECT;
		MappedStatement mappedStatemnt = this.getMappedStatemnt(mapId, sql, sqlCommandType);
		return this.sqlSessionTemplate.selectOne(mappedStatemnt.getId());
	}

	public List<T> listByParam(Map<String, Object> params) {
		String mapId = "listByParam";
		StringBuilder sb = new StringBuilder();
		sb.append(this.getBaseQuery());
		sb.append(this.getParamSql(params));
		String sql = sb.toString();
		SqlCommandType sqlCommandType = SqlCommandType.SELECT;
		MappedStatement mappedStatemnt = this.getMappedStatemnt(mapId, sql, sqlCommandType);
		return this.sqlSessionTemplate.selectList(mappedStatemnt.getId());
	}

	public List<T> pagerListByParam(Map<String, Object> params, Pager pager) {
		String mapId = "pagerListByParam";
		StringBuilder sb = new StringBuilder();
		sb.append(this.getBaseQuery());
		sb.append(this.getParamSql(params));
		String sql = sb.toString();
		SqlCommandType sqlCommandType = SqlCommandType.SELECT;
		MappedStatement mappedStatemnt = this.getMappedStatemnt(mapId, sql, sqlCommandType);
		mappedStatement.
		return this.sqlSessionTemplate.selectList(mappedStatemnt.getId());
	}

	public void batchUpdate(Map<String, Object> params,
			Map<String, Object> updates) {
		// TODO Auto-generated method stub
		
	}

	public void batchDelete(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

}

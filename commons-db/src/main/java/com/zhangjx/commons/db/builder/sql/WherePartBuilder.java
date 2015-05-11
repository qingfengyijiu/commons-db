package com.zhangjx.commons.db.builder.sql;

import java.util.ArrayList;
import java.util.List;

import com.zhangjx.commons.db.builder.Constants;
import com.zhangjx.commons.db.builder.IWherePartBuilder;
import com.zhangjx.commons.db.esapi.ESAPI;

public class WherePartBuilder implements Constants, IWherePartBuilder {
	
	private ESAPI api;
	
	private List<WherePartItem> items = new ArrayList<WherePartItem>();
	
	protected WherePartBuilder() {
		
	}
	
	public static IWherePartBuilder start() {
		return new WherePartBuilder();
	}
	
	public static String oneBuild(String sql, Object[] values) {
		return start().add(sql, values).build();
	}
	
	public static String oneBuild(String sql, Object[] values, ESAPI esapi) {
		return start().esapi(esapi).add(sql, values).build();
	}
	
	/* (non-Javadoc)
	 * @see com.zhangjx.commons.db.sql.IWherePartBuilder#esapi(com.zhangjx.commons.db.esapi.ESAPI)
	 */
	public IWherePartBuilder esapi(ESAPI esapi) {
		this.api = esapi;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.zhangjx.commons.db.sql.IWherePartBuilder#add(java.lang.String, java.lang.Object[])
	 */
	public IWherePartBuilder add(String sql, Object[] values) {
		WherePartItem item = new WherePartItem();
		item.setSql(sql);
		item.setValues(values);
		this.items.add(item);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.zhangjx.commons.db.sql.IWherePartBuilder#add(java.lang.String, java.lang.Object)
	 */
	public IWherePartBuilder add(String sql, Object value) {
		return add(sql, new Object[]{value});
	}
	
	/* (non-Javadoc)
	 * @see com.zhangjx.commons.db.sql.IWherePartBuilder#build()
	 */
	public String build() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < items.size(); i++) {
			WherePartItem item = items.get(i);
			if(i != 0) {
				sb.append(AND);
				sb.append(WORD_SEPARATOR);
			}
			sb.append(item.build(api));
			sb.append(WORD_SEPARATOR);
		}
		return sb.toString();
	}
	
	class WherePartItem {
		
		String sql;
		
		Object[] values;
		
		String getSql() {
			return sql;
		}

		void setSql(String sql) {
			this.sql = sql;
		}

		Object[] getValues() {
			return values;
		}

		void setValues(Object[] values) {
			this.values = values;
		}

		String build(ESAPI esapi) {
			List<Object> translateValues = this._translateValues(esapi);
			return String.format(sql, translateValues.toArray());
		}
		
		private List<Object> _translateValues(ESAPI esapi) {
			List<Object> translateValues = new ArrayList<Object>();
			for(Object value : values) {
				if(value instanceof String && esapi != null) {
					translateValues.add(esapi.encodeForSQL((String)value));
				} else {
					translateValues.add(value);
				}
			}
			return translateValues;
		}
	}
	
}

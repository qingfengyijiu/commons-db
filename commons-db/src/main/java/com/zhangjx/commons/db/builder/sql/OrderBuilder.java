package com.zhangjx.commons.db.builder.sql;

import java.util.ArrayList;
import java.util.List;

import com.zhangjx.commons.db.builder.Constants;
import com.zhangjx.commons.db.builder.IOrderBuilder;
import com.zhangjx.commons.db.enumeration.Order;
import com.zhangjx.commons.db.esapi.ESAPI;

public class OrderBuilder implements Constants, IOrderBuilder {
	
	private ESAPI api;
	
	List<OrderItem> items = new ArrayList<OrderItem>();
	
	private OrderBuilder() {
	}
	
	public static IOrderBuilder start() {
		return new OrderBuilder();
	}
	
	public static String oneBuild(String columnName, Order order) {
		return start().add(columnName, order).build();
	}
	
	/* (non-Javadoc)
	 * @see com.zhangjx.commons.db.sql.IOrderBuilder#esapi(com.zhangjx.commons.db.esapi.ESAPI)
	 */
	public IOrderBuilder esapi(ESAPI esapi) {
		this.api = esapi;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.zhangjx.commons.db.sql.IOrderBuilder#add(java.lang.String, com.zhangjx.commons.db.sql.OrderBuilder.Order)
	 */
	public IOrderBuilder add(String columnName, Order order) {
		OrderItem item = new OrderItem();
		item.setColumnName(columnName);
		item.setOrder(order);
		this.items.add(item);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.zhangjx.commons.db.sql.IOrderBuilder#build()
	 */
	public String build() {
		StringBuilder sb = new StringBuilder();
		if(!items.isEmpty()) {
			sb.append("ORDER BY ");
			for(int i = 0; i < items.size(); i++) {
				OrderItem item = items.get(i);
				sb.append(item.build(api));
				if(i != items.size() - 1) {
					sb.append(CLAUSE_SEPARATOR);
				}
				sb.append(WORD_SEPARATOR);
			}
		}
		return sb.toString();
	}

	class OrderItem {
		
		String columnName;
		
		Order order;
		
		String build(ESAPI esapi) {
			if(esapi != null) {
				return esapi.encodeForSQL(columnName) + WORD_SEPARATOR + order.getName();
			} else {
				return columnName + WORD_SEPARATOR + order.getName();
			}
			
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String colomnName) {
			this.columnName = colomnName;
		}

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}
		
	}
	
}

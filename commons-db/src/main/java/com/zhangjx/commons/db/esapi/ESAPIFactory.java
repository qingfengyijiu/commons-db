package com.zhangjx.commons.db.esapi;

import java.util.HashMap;
import java.util.Map;

import com.zhangjx.commons.db.esapi.mysql.MysqlESAPI;
import com.zhangjx.commons.db.esapi.mysql.MysqlESAPI.Mode;
import com.zhangjx.commons.db.esapi.oracle.OracleESAPI;
import com.zhangjx.commons.db.exception.NotSupportedDatabaseException;
import com.zhangjx.commons.logging.Log;
import com.zhangjx.commons.logging.LogFactory;

public class ESAPIFactory {
	
	private static final Log log = LogFactory.getLog(ESAPIFactory.class);
	
	private ESAPIFactory() {
		
	}
	
	private static Map<Database, ESAPI> esapiMap = new HashMap<Database, ESAPI>();
	
	@SuppressWarnings("static-access")
	public static ESAPI getESAPI(Database db) {
		log.debug("Start to create ESAPI adaptee for Database[" + db.getDbName() + "]");
		ESAPI esapi;
		if(db.ORACLE.equals(db)) {
			esapi = getOracleESAPI();
		} else if(db.MYSQL_STANDART.equals(db)) {
			esapi = getMysqlESAPI();
		} else if(db.MYSQL_ANSI.equals(db)) {
			esapi = getMysqlESAPI(db.getMode());
		} else {
			log.error("Create ESAPI fail. The database[" + db.getDbName() + "] is not supported now.");
			throw new NotSupportedDatabaseException("The database[" + db.getDbName() + "] is not supported now.");
		}
		log.debug("Finish to create ESAPI adaptee for Database[" + db.getDbName() + "] successfully");
		return esapi;
	}
	
	public static ESAPI getOracleESAPI() {
		if(esapiMap.containsKey(Database.ORACLE)) {
			return esapiMap.get(Database.ORACLE);
		} else {
			ESAPI esapi = new OracleESAPI();
			esapiMap.put(Database.ORACLE, esapi);
			return esapi;
		}
	}
	
	public static ESAPI getMysqlESAPI(Mode mode) {
		Database key;
		if(Mode.STANDARD.equals(mode)) {
			key = Database.MYSQL_STANDART;
		} else {
			key = Database.MYSQL_ANSI;
		}
		if(esapiMap.containsKey(key)) {
			return esapiMap.get(key);
		} else {
			ESAPI esapi = new MysqlESAPI(mode);
			esapiMap.put(key, esapi);
			return esapi;
		}
	}
	
	public static ESAPI getMysqlESAPI() {
		return getMysqlESAPI(Mode.STANDARD);
	}
	
	public enum Database {
		ORACLE("ORACLE", 0, Mode.NULL),
		MYSQL_STANDART("MYSQL", 1, Mode.STANDARD),
		MYSQL_ANSI("MYSQL", 2, Mode.ANSI);
		
		private String dbName;
		
		private int value;
		
		private Mode mode;
		
		Database(String dbName, int value, Mode mode) {
			this.dbName = dbName;
			this.value = value;
			this.mode = mode;
		}

		public String getDbName() {
			return dbName;
		}

		public int getValue() {
			return value;
		}
		
		public Mode getMode() {
			return mode;
		}
		
		public boolean equals(Database db) {
			return this.getValue() == db.getValue();
		}
		
	}
	
}

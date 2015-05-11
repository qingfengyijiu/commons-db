package com.zhangjx.commons.db.esapi;

import java.util.HashMap;
import java.util.Map;

import com.zhangjx.commons.db.esapi.mysql.MysqlESAPI;
import com.zhangjx.commons.db.esapi.mysql.MysqlESAPI.Mode;
import com.zhangjx.commons.db.esapi.oracle.OracleESAPI;
import com.zhangjx.commons.db.exception.NotSupportedDatabaseException;

public class ESAPIFactory {
	
	private ESAPIFactory() {
		
	}
	
	private static Map<Database, ESAPI> esapiMap = new HashMap<Database, ESAPI>();
	
	@SuppressWarnings("static-access")
	public static ESAPI getESAPI(Database db) {
		if(db.ORACLE.equals(db)) {
			return getOracleESAPI();
		} else if(db.MYSQL_STANDART.equals(db)) {
			return getMysqlESAPI();
		} else if(db.MYSQL_ANSI.equals(db)) {
			return getMysqlESAPI(db.getMode());
		} else {
			throw new NotSupportedDatabaseException("The database[" + db.getDbName() + "] is not supported now.");
		}
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

package com.zhangjx.commons.db.enumeration;

import com.zhangjx.commons.db.esapi.mysql.MysqlESAPI.Mode;

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

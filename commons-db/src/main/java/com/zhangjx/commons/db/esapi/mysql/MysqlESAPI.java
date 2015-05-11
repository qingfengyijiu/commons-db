package com.zhangjx.commons.db.esapi.mysql;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

public class MysqlESAPI implements com.zhangjx.commons.db.esapi.ESAPI {
	
	private MySQLCodec codec;
	
	private Mode mode;
	
	public MysqlESAPI() {
		this(Mode.STANDARD);
	}
	
	public MysqlESAPI(Mode mode) {
		if(mode.getValue() == 1) {
			this.mode = Mode.ANSI;
		} else {
			this.mode = Mode.STANDARD;
		}
		this.codec = new MySQLCodec(_translateMode(mode));
	}

	public String encodeForSQL(String sql) {
		return ESAPI.encoder().encodeForSQL(codec, sql);
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	private org.owasp.esapi.codecs.MySQLCodec.Mode _translateMode(Mode mode) {
		org.owasp.esapi.codecs.MySQLCodec.Mode translate;
		if(mode.getValue() == 1) {
			translate = org.owasp.esapi.codecs.MySQLCodec.Mode.ANSI;
		} else {
			translate = org.owasp.esapi.codecs.MySQLCodec.Mode.STANDARD;
		}
		return translate;
	}
	
	public enum Mode {
		NULL("null", -1),
		STANDARD("standard", 0),
		ANSI("ansi", 1);
		String name;
		int value;
		
		Mode(String name, int value) {
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			return name;
		}
		
		public int getValue() {
			return value;
		}
		
		public boolean equals(Mode mode) {
			return this.getValue() == mode.getValue();
		}
	}

}

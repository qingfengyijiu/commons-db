package com.zhangjx.commons.db.esapi.oracle;

import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.ESAPI;

public class OracleESAPI implements com.zhangjx.commons.db.esapi.ESAPI {
	
	private static final OracleCodec codec = new OracleCodec();

	public String encodeForSQL(String sql) {
		return ESAPI.encoder().encodeForSQL(codec, sql);
	}

}

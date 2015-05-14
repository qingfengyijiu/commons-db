package com.zhangjx.commons.db.test.utils;

import javax.persistence.Column;
import javax.persistence.OneToOne;

class VipUser extends User {
	
	@Column(name="name")
	private String name;
	
	@OneToOne
	private Address address;
}
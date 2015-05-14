package com.zhangjx.commons.db.test.utils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.zhangjx.commons.db.entity.BaseEntity;

@Table(name="USER")
public class User implements BaseEntity {
	
	@Column(name="NAME")
	private String name;
	
	@OneToOne
	@JoinColumn(name="address")
	private Address address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getId() {
		return "1f";
	}

	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}
	
}

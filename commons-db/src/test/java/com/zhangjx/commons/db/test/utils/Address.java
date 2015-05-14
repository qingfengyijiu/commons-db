package com.zhangjx.commons.db.test.utils;

import com.zhangjx.commons.db.entity.BaseEntity;

public class Address implements BaseEntity {
	
	private String province;
	
	private String city;
	
	private String road;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getId() {
		return "2d";
	}

	public void setId(String id) {
		
	}
	
}

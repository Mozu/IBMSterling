package com.mozu.sterling.model;

import java.io.Serializable;


public class MozuShippingCarrier implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
	private String description;
	private String code;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}

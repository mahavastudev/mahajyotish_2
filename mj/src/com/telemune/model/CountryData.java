package com.telemune.model;

public class CountryData {
	
	private String name;
	private String code;
	private int isStateLst;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIsStateLst() {
		return isStateLst;
	}
	public void setIsStateLst(int isStateLst) {
		this.isStateLst = isStateLst;
	}
	@Override
	public String toString() {
		return "Country [name=" + name + ", code=" + code + ", isStateLst="
				+ isStateLst + "]";
	}

}

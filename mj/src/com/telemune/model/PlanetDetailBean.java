package com.telemune.model;

public class PlanetDetailBean extends HouseDetailBean {
	private String RC;

	public PlanetDetailBean() {
		this.RC = null;
	}

	public String getRC() {
		return this.RC;
	}

	public void setRC(String rC) {
		this.RC = rC;
	}
}

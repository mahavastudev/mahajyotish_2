package com.telemune.model;

import java.util.Arrays;

public class Kundli {
	private String kundliType;
	private String[] housesDetail;

	public Kundli(String kundliType) {
		this.kundliType = kundliType;
		this.housesDetail = new String[12];
	}

	public void setType(String _type) {
		if (_type.equals("BIRTH"))
			this.kundliType = "BIRTH";
		else if (_type.equals("CUSP"))
			this.kundliType = "CUSP";
	}

	public void setHouseData(int houseNumber, String houseData) {
		this.housesDetail[(houseNumber - 1)] = houseData;
	}

	public String getHouseData(int houseNumber) {
		return this.housesDetail[(houseNumber - 1)];
	}

	public String getType() {
		return this.kundliType;
	}

	public String toString() {
		return Arrays.toString(this.housesDetail);
	}
}

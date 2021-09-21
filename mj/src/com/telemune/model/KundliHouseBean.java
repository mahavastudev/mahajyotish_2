package com.telemune.model;

public class KundliHouseBean {
	private String planetName;
	private String houseNumber;
	private String signNumber;
	private String transitPlanetName;
	private String transitHouseNumber;
	private String transitSignNumber;

	public KundliHouseBean() {
		this.planetName = null;
		this.houseNumber = null;
		this.signNumber = null;
		this.transitPlanetName = null;
		this.transitHouseNumber = null;
		this.transitSignNumber = null;

	}

	public String getPlanetName() {
		return this.planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getSignNumber() {
		return this.signNumber;
	}

	public void setSignNumber(String signNumber) {
		this.signNumber = signNumber;
	}

	public String getTransitPlanetName() {
		return this.transitPlanetName;
	}

	public void setTransitPlanetName(String transitPlanetName) {
		this.transitPlanetName = transitPlanetName;
	}

	public String getTransitHouseNumber() {
		return this.transitHouseNumber;
	}

	public void setTransitHouseNumber(String transitHouseNumber) {
		this.transitHouseNumber = transitHouseNumber;
	}

	public String getTransitSignNumber() {
		return this.transitSignNumber;
	}

	public void setTransitSignNumber(String transitSignNumber) {
		this.transitSignNumber = transitSignNumber;
	}

	@Override
	public String toString() {
		return "KundliHouseBean [houseNumber=" + houseNumber + ", planetName=" + planetName + ", signNumber="
				+ signNumber + ", transitHouseNumber" + transitHouseNumber + ",transitPlanetName" + transitPlanetName
				+ ",transitSignNumber" + transitSignNumber + "]";
	}
}

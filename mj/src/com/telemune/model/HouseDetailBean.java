package com.telemune.model;

public class HouseDetailBean extends KundliHouseBean {
	private String degree;
	private String signName;
	private String nakshatra;
	private String padam;
	private String RL;
	private String NL;
	private String SL;
	private String SS;
	private String NLSL;

	public HouseDetailBean() {
		this.degree = null;
		this.signName = null;
		this.nakshatra = null;
		this.padam = null;
		this.RL = null;
		this.NL = null;
		this.SL = null;
		this.SS = null;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSignName() {
		return this.signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getNakshatra() {
		return this.nakshatra;
	}

	public void setNakshatra(String nakshatra) {
		this.nakshatra = nakshatra;
	}

	public String getPadam() {
		return this.padam;
	}

	public void setPadam(String padam) {
		this.padam = padam;
	}

	public String getRL() {
		return this.RL;
	}

	public void setRL(String rL) {
		this.RL = rL;
	}

	public String getNL() {
		return this.NL;
	}

	public void setNL(String nL) {
		this.NL = nL;
	}

	public String getSL() {
		return this.SL;
	}

	public void setSL(String sL) {
		this.SL = sL;
	}

	public String getNLSL() {
		return this.NLSL;
	}

	public void setNLSL(String NLsL) {
		this.NLSL = NLsL;
	}

	public String getSS() {
		return this.SS;
	}

	public void setSS(String sS) {
		this.SS = sS;
	}

	public String toString() {
		return "degree  " + this.degree + " ,signName  : " + this.signName + ", nakshatra:  " + this.nakshatra
				+ ", padam:  " + this.padam + ", RL: " + this.RL + ", " + this.NL + ", SL:" + this.SL + ", SS: "
				+ this.SS;
	}

}

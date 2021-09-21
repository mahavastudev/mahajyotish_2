package com.telemune.pojo;

import com.itextpdf.text.Phrase;

public class AstroLifeContexts {
	private String planetDetailHit;
	private String planetDetailRC;
	private String planetDetailHouse;
	private String planetDetailPlanet;
	private Phrase planetDetailPlanetPhrase;
	
	private String planetDetailDegree;
	private String sign;
	private String enviornment="NA";
	private String cuspDetailDegree;
	private String cuspDetailLife;
	private Phrase cuspDetailLifePhrase;
	public Phrase getPlanetDetailPlanetPhrase() {
		return planetDetailPlanetPhrase;
	}

	public void setPlanetDetailPlanetPhrase(Phrase planetDetailPlanetPhrase) {
		this.planetDetailPlanetPhrase = planetDetailPlanetPhrase;
	}

	public Phrase getCuspDetailLifePhrase() {
		return cuspDetailLifePhrase;
	}

	public void setCuspDetailLifePhrase(Phrase cuspDetailLifePhrase) {
		this.cuspDetailLifePhrase = cuspDetailLifePhrase;
	}
	private String cuspDetailHitFrom;

	public String getPlanetDetailHit() {
		return planetDetailHit;
	}

	public void setPlanetDetailHit(String planetDetailHit) {
		this.planetDetailHit = planetDetailHit;
	}

	public String getPlanetDetailRC() {
		return planetDetailRC;
	}

	public void setPlanetDetailRC(String planetDetailRC) {
		this.planetDetailRC = planetDetailRC;
	}

	public String getPlanetDetailPlanet() {
		return planetDetailPlanet;
	}

	public void setPlanetDetailPlanet(String planetDetailPlanet) {
		this.planetDetailPlanet = planetDetailPlanet;
	}

	public String getPlanetDetailDegree() {
		return planetDetailDegree;
	}

	public void setPlanetDetailDegree(String planetDetailDegree) {
		this.planetDetailDegree = planetDetailDegree;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getEnviornment() {
		return enviornment;
	}

	public void setEnviornment(String enviornment) {
		this.enviornment = enviornment;
	}

	public String getCuspDetailDegree() {
		return cuspDetailDegree;
	}

	public void setCuspDetailDegree(String cuspDetailDegree) {
		this.cuspDetailDegree = cuspDetailDegree;
	}

	public String getCuspDetailLife() {
		return cuspDetailLife;
	}

	public void setCuspDetailLife(String cuspDetailLife) {
		this.cuspDetailLife = cuspDetailLife;
	}

	public String getCuspDetailHitFrom() {
		return cuspDetailHitFrom;
	}

	public void setCuspDetailHitFrom(String cuspDetailHitFrom) {
		this.cuspDetailHitFrom = cuspDetailHitFrom;
	}
	public String getPlanetDetailHouse() {
		return planetDetailHouse;
	}

	public void setPlanetDetailHouse(String planetDetailHouse) {
		this.planetDetailHouse = planetDetailHouse;
	}
	@Override
	public String toString() {
		return "AstroLifeContexts [planetDetailHit=" + planetDetailHit + ", planetDetailRC=" + planetDetailRC
				+ ", planetDetailPlanet=" + planetDetailPlanet + ", planetDetailDegree=" + planetDetailDegree
				+ ", sign=" + sign + ", enviornment=" + enviornment + ", cuspDetailDegree=" + cuspDetailDegree
				+ ", cuspDetailLife=" + cuspDetailLife + ", cuspDetailHitFrom=" + cuspDetailHitFrom + "]";
	}

}

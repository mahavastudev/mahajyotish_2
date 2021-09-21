package com.telemune.model;

public class PlaDetDegree 
{
   private String deg;
   private String planets;
   private String rc;
   private String hits;
public String getDeg() {
	return deg;
}
public void setDeg(String deg) {
	this.deg = deg;
}
public String getPlanets() {
	return planets;
}
public void setPlanets(String planets) {
	this.planets = planets;
}
public String getRc() {
	return rc;
}
public void setRc(String rc) {
	this.rc = rc;
}
public String getHits() {
	return hits;
}
public void setHits(String hits) {
	this.hits = hits;
}
@Override
public String toString() {
	return "PlaDetDegree [deg=" + deg + ", planets=" + planets + ", rc=" + rc + ", hits=" + hits + "]";
}
   
   
}

package com.telemune.model;

import java.util.Comparator;

public class planetDetailHit 
{
   private String planet;
   private int num;
public String getPlanet() {
	return planet;
}
public void setPlanet(String planet) {
	this.planet = planet;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
@Override
public String toString() {
	return "planetDetailHit [planet=" + planet + ", num=" + num + "]";
}
     
}

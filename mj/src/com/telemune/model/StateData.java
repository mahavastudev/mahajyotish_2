package com.telemune.model;

public class StateData {
	
	private String sname;
	private String scode;
	private String cntryCode;
	
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getCntryCode() {
		return cntryCode;
	}
	public void setCntryCode(String cntryCode) {
		this.cntryCode = cntryCode;
	}
	@Override
	public String toString() {
		return "StateData [sname=" + sname + ", scode=" + scode
				+ ", cntryCode=" + cntryCode + "]";
	}
	
	

}

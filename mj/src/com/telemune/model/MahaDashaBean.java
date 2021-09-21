package com.telemune.model;

public class MahaDashaBean extends KundliHouseBean {
	private String startTime;
	private String endTime;
	private String parent;
	private String child;
	private String subChild;
	private String year;

	public MahaDashaBean() {
		this.startTime = null;
		this.endTime = null;
		this.parent = null;
		this.child = null;
		this.subChild = null;
		this.year = null;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getChild() {
		return this.child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getSubChild() {
		return this.subChild;
	}

	public void setSubChild(String subChild) {
		this.subChild = subChild;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "MahaDashaBean [child=" + child + ", endTime=" + endTime + ", parent=" + parent + ", startTime="
				+ startTime + ", subChild=" + subChild + ", year=" + year + "]";
	}
}

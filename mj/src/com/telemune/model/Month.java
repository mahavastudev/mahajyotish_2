package com.telemune.model;

public class Month {
	private String monthId;
	private String monthName;

	public Month(String monthId, String monthName) {
		this.monthId = monthId;
		this.monthName = monthName;
	}

	public String getMonthId() {
		return monthId;
	}

	public String getMonthName() {
		return monthName;
	}

}

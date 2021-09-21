package com.telemune.action;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.telemune.model.Kundli;
import com.telemune.model.KundliHouseBean;
import com.telemune.mobileAstro.astroException;

public class DashaStrength {
	
	private static Logger logger = Logger.getLogger(DashaStrength.class);

	private String dasaPlanet;
	private String[][] StrengthChart;
	private Kundli cuspChart = null;
	private StringBuffer Aspect_RAHU;
	private StringBuffer Conjuction_RAHU;
	private StringBuffer SignLoard_RAHU;
	private StringBuffer RashiLoard_RAHU;
	private StringBuffer Aspect_KETU;
	private StringBuffer Conjuction_KETU;
	private StringBuffer SignLoard_KETU;
	private StringBuffer RashiLoard_KETU;
	private String rahuSource;
	private String ketuSource;
	private boolean isCurrent;
	Hashtable<String, KundliHouseBean> dashaCuspPlanetHashTable = new Hashtable();

	Hashtable<String, KundliHouseBean> dashaCuspHouseHashTable = new Hashtable();
	private String dashaStrengthData;

	public DashaStrength() {
		this.dasaPlanet = "Current";
		this.isCurrent = false;
		this.StrengthChart = null;
		this.Aspect_KETU = new StringBuffer("");
		this.Conjuction_KETU = new StringBuffer("");
		this.SignLoard_KETU = new StringBuffer("");
		this.RashiLoard_KETU = new StringBuffer("");
		this.Aspect_RAHU = new StringBuffer("");
		this.Conjuction_RAHU = new StringBuffer("");
		this.SignLoard_RAHU = new StringBuffer("");
		this.RashiLoard_RAHU = new StringBuffer("");
	}

	public void setCurrentFlag(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public boolean getCurrentFlag() {
		return this.isCurrent;
	}

	public String getDashaStrengthData() {
		return this.dashaStrengthData;
	}

	public void setDashaStrengthData(String dashaStrengthData) {
		this.dashaStrengthData = dashaStrengthData;
	}

	public Hashtable<String, KundliHouseBean> getDashaCuspHouseHashTable() {
		return this.dashaCuspHouseHashTable;
	}

	public void setDashaCuspHouseHashTable(Hashtable<String, KundliHouseBean> dashaCuspHouseHashTable) {
		this.dashaCuspHouseHashTable = dashaCuspHouseHashTable;
	}

	public Hashtable<String, KundliHouseBean> getDashaCuspPlanetHashTable() {
		return this.dashaCuspPlanetHashTable;
	}

	public void setDashaCuspPlanetHashTable(Hashtable<String, KundliHouseBean> dashaCuspPlanetHashTable) {
		this.dashaCuspPlanetHashTable = dashaCuspPlanetHashTable;
	}

	public String getRahuSource() {
		return this.rahuSource;
	}

	public void setRahuSource(String rahuSource) {
		this.rahuSource = rahuSource;
	}

	public String getKetuSource() {
		return this.ketuSource;
	}

	public void setKetuSource(String ketuSource) {
		this.ketuSource = ketuSource;
	}

	public void setStrengthChart(String[][] _stChart) {
		this.StrengthChart = _stChart;
	}

	public String[][] getNatalStrengthChart() {
		return this.StrengthChart;
	}

	public void setDashaPlanet(String dashaPlanet) {
		this.dasaPlanet = dashaPlanet;
	}

	public String getDashaPlanet() {
		return this.dasaPlanet;
	}

	public Kundli getCuspKundli() {
		return this.cuspChart;
	}

	public void setCuspKundli(Kundli _cusp) {
		this.cuspChart = _cusp;
	}

	public void setAspect(Planet _planet, String _aspect) throws astroException {
		logger.info("Inside set setAspect of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			this.Aspect_RAHU.append(_aspect + "__");
		} else if (_planet == Planet.Ketu) {
			this.Aspect_KETU.append(_aspect + "__");
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting aspect");
		}
	}

	public void setConjuction(Planet _planet, String _aspect) throws astroException {
		logger.info("Inside set setConjuction of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			this.Conjuction_RAHU.append(_aspect + "__");
		} else if (_planet == Planet.Ketu) {
			this.Conjuction_KETU.append(_aspect + "__");
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting conjuction");
		}
	}

	public void setSignLoard(Planet _planet, String _aspect) throws astroException {
		logger.info("Inside set setSignLoard of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			this.SignLoard_RAHU.append(_aspect);
		} else if (_planet == Planet.Ketu) {
			this.SignLoard_KETU.append(_aspect);
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting RashiLoard");
		}
	}

	public void setRashiLoard(Planet _planet, String _aspect) throws astroException {
		logger.info("Inside set setRashiLoard of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			this.RashiLoard_RAHU.append(_aspect);
		} else if (_planet == Planet.Ketu) {
			this.RashiLoard_KETU.append(_aspect);
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting RashiLoard");
		}
	}

	public String[] getAspect(Planet _planet) {
		logger.info("Inside set getAspect of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			if (!this.Aspect_RAHU.toString().equals("")) {
				return this.Aspect_RAHU.toString().split("__");
			}
		} else if (_planet == Planet.Ketu) {
			if (!this.Aspect_KETU.toString().equals("")) {
				return this.Aspect_KETU.toString().split("__");
			}

		} else {
			return null;
		}
		return null;
	}

	public String getAspectRahu() {
		return this.Aspect_RAHU.toString();
	}

	public String[] getConjuction(Planet _planet) {
		logger.info("Inside set getConjuction of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			if (!this.Conjuction_RAHU.toString().equals("")) {
				return this.Conjuction_RAHU.toString().split("__");
			}
		} else if (_planet == Planet.Ketu) {
			if (!this.Conjuction_KETU.toString().equals("")) {
				return this.Conjuction_KETU.toString().split("__");
			}

		} else {
			return null;
		}
		return null;
	}

	public String getSignLoard(Planet _planet) {
		logger.info("Inside set getSignLoard of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			if (!this.RashiLoard_RAHU.toString().equals("")) {
				return this.SignLoard_RAHU.toString();
			}
		} else if (_planet == Planet.Ketu) {
			if (!this.RashiLoard_KETU.toString().equals("")) {
				return this.SignLoard_KETU.toString();
			}

		} else {
			return null;
		}
		return null;
	}

	public String getRashiLoard(Planet _planet) {
		logger.info("Inside set getRashiLoard of DashaStrength action Class.");
		if (_planet == Planet.Rahu) {
			if (!this.RashiLoard_RAHU.toString().equals("")) {
				return this.RashiLoard_RAHU.toString();
			}
		} else if (_planet == Planet.Ketu) {
			if (!this.RashiLoard_KETU.toString().equals("")) {
				return this.RashiLoard_KETU.toString();
			}

		} else {
			return null;
		}
		return null;
	}
}

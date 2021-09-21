package com.telemune.model;

import java.util.*;

import com.telemune.action.DashaStrength;
import com.telemune.action.Planet;
import com.telemune.mobileAstro.astroException;

public class AstroBean {
	private String name;
	private String fileName;
	private String DOB1;
	private String POB;
	private String TOB;
	private String DOB;
	private Kundli birthChart;
	private Kundli cuspChart;
	private String[][] natalStrengthChart;

	private String aspectRahu;
	private String conjucRahu;
	private String rashiLordRahu;
	private String signLordRahu;
	private String aspectKetu;
	private String conjucKetu;
	private String rashiLordKetu;
	private String signLordKetu;

	private Vector<DashaStrength> dashaStrength;
	private Hashtable<String, KundliHouseBean> cuspPlanetHashTable;
	private Hashtable<String, KundliHouseBean> cuspHouseHashTable;
	private Hashtable<String, KundliHouseBean> birthPlanetHashTable;
	private Hashtable<String, KundliHouseBean> birthHouseHashTable;
	private LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable;
	private LinkedHashMap<String, HouseDetailBean> houseDetailHashTable;

	/////// 1 oct 2015/////////////
	private LinkedHashMap<String, HouseDetailBean> houseSignDetailHashTable;
	private LinkedHashMap<String, HouseDetailBean> houseStarDetailHashTable;
	private LinkedHashMap<String, HashSet<String>> houseAspectHashTable;
	private LinkedHashMap<String, HouseDetailBean> houseSubLordHashTable;
	private LinkedHashMap<String, ArrayList<HouseDetailBean>> houseOccupantHashTable;
	private LinkedHashMap<String, HashMap<String, HashSet<String>>> houseOccAspectHashTable;
	private LinkedHashMap<String, HashMap<String, String>> cuspHouseAspectDetails;
	private LinkedHashMap<String, HashMap<String, String>> planetHouseAspectDetails;

	////////////////////////////////
	private LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable;
	private LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable;
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
	private String astroHouseTable;
	private String astroPlanetTable;
	private String kundliData;



	public AstroBean() {
		this.name = null;
		this.POB = null;
		this.TOB = null;
		this.DOB = null;
		this.birthChart = null;
		this.cuspChart = null;
		this.Aspect_KETU = new StringBuffer("");
		this.Conjuction_KETU = new StringBuffer("");
		this.SignLoard_KETU = new StringBuffer("");
		this.RashiLoard_KETU = new StringBuffer("");
		this.Aspect_RAHU = new StringBuffer("");
		this.Conjuction_RAHU = new StringBuffer("");
		this.SignLoard_RAHU = new StringBuffer("");
		this.RashiLoard_RAHU = new StringBuffer("");
		this.dashaStrength = new Vector();
		this.cuspPlanetHashTable = null;
		this.cuspHouseHashTable = null;
		this.birthPlanetHashTable = null;
		this.birthHouseHashTable = null;
		this.planetDetailHashTable = null;
		this.houseDetailHashTable = null;
		this.antardashaDetailHashTable = null;
		this.sookshmadashaDetailHashTable = null;
	}

	public String getKundliData()
	{
		return this.kundliData;
	}

	public void setKundliData(String kundliData) {
		this.kundliData = kundliData;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPOB() {
		return this.POB;
	}

	public void setPOB(String pOB) {
		this.POB = pOB;
	}

	public String getTOB() {
		return this.TOB;
	}

	public void setTOB(String tOB) {
		this.TOB = tOB;
	}

	public String getDOB1() {
		return this.DOB1;
	}

	public void setDOB1(String dOB1) {
		this.DOB1 = dOB1;
	}

	public String getDOB() {
		return this.DOB;
	}

	public void setDOB(String dOB) {
		this.DOB = dOB;
	}

	public void setBirthKundli(Kundli _birth) {
		this.birthChart = _birth;
	}

	public Kundli getBirthKundli() {
		return this.birthChart;
	}

	public Kundli getCuspKundli() {
		return this.cuspChart;
	}

	public void setCuspKundli(Kundli _cusp) {
		this.cuspChart = _cusp;
	}

	public void setNatalStrengthChart(String[][] _natalStrengthChart) {
		this.natalStrengthChart = _natalStrengthChart;
	}

	public String[][] getNatalStrengthChart() {
		return this.natalStrengthChart;
	}

	public void setAspect(Planet _planet, String _aspect) throws astroException {
		if (_planet == Planet.Rahu) {
			this.Aspect_RAHU.append(_aspect + "__");
		} else if (_planet == Planet.Ketu) {
			this.Aspect_KETU.append(_aspect + "__");
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting aspect");
		}
	}

	public void setConjuction(Planet _planet, String _aspect) throws astroException {
		if (_planet == Planet.Rahu) {
			this.Conjuction_RAHU.append(_aspect + "__");
		} else if (_planet == Planet.Ketu) {
			this.Conjuction_KETU.append(_aspect + "__");
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting conjuction");
		}
	}

	public void setSignLoard(Planet _planet, String _aspect) throws astroException {
		if (_planet == Planet.Rahu) {
			this.SignLoard_RAHU.append(_aspect);
		} else if (_planet == Planet.Ketu) {
			this.SignLoard_KETU.append(_aspect);
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting SunLoard");
		}
	}

	public void setRashiLoard(Planet _planet, String _aspect) throws astroException {
		if (_planet == Planet.Rahu) {
			this.RashiLoard_RAHU.append(_aspect);
		} else if (_planet == Planet.Ketu) {
			this.RashiLoard_KETU.append(_aspect);
		} else {
			throw new astroException("Invalid argument <" + _planet + "> while setting RashiLoard");
		}
	}

	public String[] getAspect(Planet _planet) {
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

	public String[] getConjuction(Planet _planet) {
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
		if (_planet == Planet.Rahu) {
			if (!this.SignLoard_RAHU.toString().equals("")) {
				return this.SignLoard_RAHU.toString();
			}
		} else if (_planet == Planet.Ketu) {
			if (!this.SignLoard_KETU.toString().equals("")) {
				return this.SignLoard_KETU.toString();
			}

		} else {
			return null;
		}
		return null;
	}

	public String getRashiLoard(Planet _planet) {
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

	public Hashtable<String, KundliHouseBean> getCuspPlanetHashTable() {
		return this.cuspPlanetHashTable;
	}

	public void setCuspPlanetHashTable(Hashtable<String, KundliHouseBean> cuspPlanetHashTable) {
		this.cuspPlanetHashTable = cuspPlanetHashTable;
	}

	public Hashtable<String, KundliHouseBean> getCuspHouseHashTable() {
		return this.cuspHouseHashTable;
	}

	public void setCuspHouseHashTable(Hashtable<String, KundliHouseBean> cuspHouseHashTable) {
		this.cuspHouseHashTable = cuspHouseHashTable;
	}

	public Hashtable<String, KundliHouseBean> getBirthPlanetHashTable() {
		return this.birthPlanetHashTable;
	}

	public void setBirthPlanetHashTable(Hashtable<String, KundliHouseBean> birthPlanetHashTable) {
		this.birthPlanetHashTable = birthPlanetHashTable;
	}

	public Hashtable<String, KundliHouseBean> getBirthHouseHashTable() {
		return this.birthHouseHashTable;
	}

	public void setBirthHouseHashTable(Hashtable<String, KundliHouseBean> birthHouseHashTable) {
		this.birthHouseHashTable = birthHouseHashTable;
	}

	public LinkedHashMap<String, PlanetDetailBean> getPlanetDetailHashTable() {
		return this.planetDetailHashTable;
	}

	public void setPlanetDetailHashTable(LinkedHashMap<String, PlanetDetailBean> planetDetailHashTable) {
		this.planetDetailHashTable = planetDetailHashTable;
	}

	public LinkedHashMap<String, HouseDetailBean> getHouseDetailHashTable() {
		return this.houseDetailHashTable;
	}

	public void setHouseDetailHashTable(LinkedHashMap<String, HouseDetailBean> houseDetailHashTable) {
		this.houseDetailHashTable = houseDetailHashTable;
	}

	public LinkedHashMap<String, HashSet<String>> getHouseAspectHashTable() {
		return this.houseAspectHashTable;
	}

	public void setHouseAspectHashTable(LinkedHashMap<String, HashSet<String>> houseAspectHashTable) {
		this.houseAspectHashTable = houseAspectHashTable;
	}

	public LinkedHashMap<String, HashMap<String, HashSet<String>>> getHouseOccAspectHashTable() {
		return this.houseOccAspectHashTable;
	}

	public void setHouseOccAspectHashTable(
			LinkedHashMap<String, HashMap<String, HashSet<String>>> houseOccAspectHashTable) {
		this.houseOccAspectHashTable = houseOccAspectHashTable;
	}

	public LinkedHashMap<String, HashMap<String, String>> getCuspHouseAspectDetails() {
		return this.cuspHouseAspectDetails;
	}

	public void setCuspHouseAspectDetails(LinkedHashMap<String, HashMap<String, String>> cuspHouseAspectDetails) {
		this.cuspHouseAspectDetails = cuspHouseAspectDetails;
	}

	public LinkedHashMap<String, HashMap<String, String>> getPlanetHouseAspectDetails() {
		return this.planetHouseAspectDetails;
	}

	public void setPlanetHouseAspectDetails(LinkedHashMap<String, HashMap<String, String>> planetHouseAspectDetails) {
		this.planetHouseAspectDetails = planetHouseAspectDetails;
	}

	public LinkedHashMap<String, HouseDetailBean> getHouseSignDetailHashTable() {
		return this.houseSignDetailHashTable;
	}

	public void setHouseSignDetailHashTable(LinkedHashMap<String, HouseDetailBean> houseSignDetailHashTable) {
		this.houseSignDetailHashTable = houseSignDetailHashTable;
	}

	public LinkedHashMap<String, HouseDetailBean> getHouseStarDetailHashTable() {
		return this.houseStarDetailHashTable;
	}

	public void setHouseStarDetailHashTable(LinkedHashMap<String, HouseDetailBean> houseStarDetailHashTable) {
		this.houseStarDetailHashTable = houseStarDetailHashTable;
	}

	public LinkedHashMap<String, HouseDetailBean> getHouseSubLordHashTable() {
		return this.houseSubLordHashTable;
	}

	public void setHouseSubLordHashTable(LinkedHashMap<String, HouseDetailBean> houseSubLordHashTable) {
		this.houseSubLordHashTable = houseSubLordHashTable;
	}

	public LinkedHashMap<String, ArrayList<HouseDetailBean>> getHouseOccupantHashTable() {
		return this.houseOccupantHashTable;
	}

	public void setHouseOccupantHashTable(LinkedHashMap<String, ArrayList<HouseDetailBean>> houseOccupantHashTable) {
		this.houseOccupantHashTable = houseOccupantHashTable;
	}

	public LinkedHashMap<String, Vector<MahaDashaBean>> getAntardashaDetailHashTable() {
		return this.antardashaDetailHashTable;
	}

	public void setAntardashaDetailHashTable(LinkedHashMap<String, Vector<MahaDashaBean>> antardashaDetailHashTable) {
		this.antardashaDetailHashTable = antardashaDetailHashTable;
	}

	public LinkedHashMap<String, Vector<MahaDashaBean>> getSookshmadashaDetailHashTable() {
		return this.sookshmadashaDetailHashTable;
	}

	public void setSookshmadashaDetailHashTable(LinkedHashMap<String, Vector<MahaDashaBean>> sookshmadashaDetailHashTable) {
		this.sookshmadashaDetailHashTable = sookshmadashaDetailHashTable;
	}

	public Vector<DashaStrength> getDashaStrength() {
		return this.dashaStrength;
	}

	public void setDashaStrength(DashaStrength dashaStrength) {
		this.dashaStrength.add(dashaStrength);
	}

	public String getAspectRahu() {
		return aspectRahu;
	}

	public void setAspectRahu(String aspectRahu) {
		this.aspectRahu = aspectRahu;
	}

	public String getConjucRahu() {
		return conjucRahu;
	}

	public void setConjucRahu(String conjucRahu) {
		this.conjucRahu = conjucRahu;
	}

	public String getRashiLordRahu() {
		return rashiLordRahu;
	}

	public void setRashiLordRahu(String rashiLordRahu) {
		this.rashiLordRahu = rashiLordRahu;
	}

	public String getSignLordRahu() {
		return signLordRahu;
	}

	public void setSignLordRahu(String signLordRahu) {
		this.signLordRahu = signLordRahu;
	}

	public String getAspectKetu() {
		return aspectKetu;
	}

	public void setAspectKetu(String aspectKetu) {
		this.aspectKetu = aspectKetu;
	}

	public String getConjucKetu() {
		return conjucKetu;
	}

	public void setConjucKetu(String conjucKetu) {
		this.conjucKetu = conjucKetu;
	}

	public String getRashiLordKetu() {
		return rashiLordKetu;
	}

	public void setRashiLordKetu(String rashiLordKetu) {
		this.rashiLordKetu = rashiLordKetu;
	}

	public String getSignLordKetu() {
		return signLordKetu;
	}

	public void setSignLordKetu(String signLordKetu) {
		this.signLordKetu = signLordKetu;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAstroHouseTable() {
		return astroHouseTable;
	}

	public void setAstroHouseTable(String astroHouseTable) {
		this.astroHouseTable = astroHouseTable;
	}

	public String getAstroPlanetTable() {
		return astroPlanetTable;
	}

	public void setAstroPlanetTable(String astroPlanetTable) {
		this.astroPlanetTable = astroPlanetTable;
	}

}

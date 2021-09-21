package com.telemune.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CalculateVimshottari {

	private static Logger logger = Logger.getLogger(CalculateVimshottari.class);

	static HashMap<String, Integer> map = new HashMap<String, Integer>();
	static String[] p = { "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury" };
	static Double[] per = { 5.83333333, 16.6666666, 5.0, 8.33333333, 5.83333333, 15.0, 13.33333333, 15.8333333,
			14.16666666 };

	static ArrayList<String> planets = new ArrayList<String>(Arrays.asList(p));
	static ArrayList<Double> percntg = new ArrayList<Double>(Arrays.asList(per));

	static {

		map.put("Ketu", 7);
		map.put("Venus", 20);
		map.put("Sun", 6);
		map.put("Moon", 10);
		map.put("Mars", 7);
		map.put("Rahu", 18);
		map.put("Jupiter", 16);
		map.put("Saturn", 19);
		map.put("Mercury", 17);

	}

	public static JSONArray calculate(String startDate, String endDate, String Planet, String startPlanet) {
		logger.info("Inside claculate method of CalculateVimshottari action class.");
		DateFormat formatter = null;
		Calendar cal = null;
		Date today = null;
		Calendar cal1 = null;
		Calendar cal2 = null;
		JSONArray array = new JSONArray();
		JSONObject object = null;
		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");

			cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			today = cal.getTime();
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			today = formatter.parse(startDate);
			Date end = formatter.parse(endDate);

			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
			cal1.setTime(today);
			cal2.setTime(end);

			long milliSec1 = cal1.getTimeInMillis();
			long milliSec2 = cal2.getTimeInMillis();
			long diff = milliSec2 - milliSec1;
			String parent = Planet;

			int index = planets.indexOf(Planet.trim());
			int startIndex = 0;
			if (!startPlanet.equalsIgnoreCase(Planet)) {

				while (!Planet.equalsIgnoreCase(startPlanet)) {
					if (index == planets.size())
						index = 0;
					Planet = planets.get(index);
					if (Planet.equalsIgnoreCase(startPlanet))
						break;

					object = new JSONObject();
					object.put("planet", Planet);
					object.put("start", "");
					object.put("end", "");
					array.put(startIndex, object);
					startIndex++;

					index++;
				}
			}

			for (int i = startIndex; i < 9; i++) {

				if (index == planets.size())
					index = 0;
				Planet = planets.get(index);

				milliSec1 = cal1.getTimeInMillis();

				long d = (long) ((diff * percntg.get(index)) / 100);

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(milliSec1);

				while (d > 0) {
					if (d > (long) Integer.MAX_VALUE) {
						d = d - (long) Integer.MAX_VALUE;
						calendar.add(Calendar.MILLISECOND, Integer.MAX_VALUE);
					} else {
						calendar.add(Calendar.MILLISECOND, (int) d);
						d = 0;
					}
				}

				index++;

				object = new JSONObject();
				object.put("planet", Planet);
				object.put("start", formatter.format(today));
				if (i == 8) {
					object.put("end", endDate);
				} else {
					object.put("end", formatter.format(calendar.getTime()));
				}

				today = formatter.parse(formatter.format(calendar.getTime()));
				cal1.setTime(today);

				array.put(i, object);

			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Inside catch block. Exception at claculate method of CalculateVimshottari action Class. Exception  : "+e.toString());
			e.printStackTrace();
		} finally {
			if (formatter != null)
				formatter = null;

			if (cal != null)
				cal = null;

			if (today != null)
				today = null;

			if (cal1 != null)
				cal1 = null;

			if (cal2 != null)
				cal2 = null;
		}
		logger.info("Array : " + array.toString());
		return array;
	}

	public static JSONObject calculateBack(String startDate, String startingPlanet, String Planet) {
		logger.info("Inside calculateBack method of CalculateVimshottari action class.");
		DateFormat formatter = null;
		Calendar cal = null;
		Date today = null;
		Calendar cal1 = null;
		Calendar cal2 = null;
		JSONArray array = new JSONArray();
		JSONObject object = null;

		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");

			cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			today = cal.getTime();
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			today = formatter.parse(startDate);

			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
			cal1.setTime(today);
			cal2.setTime(today);
			cal2.add(Calendar.YEAR, -map.get(startingPlanet));

			long milliSec1 = cal1.getTimeInMillis();
			long milliSec2 = cal2.getTimeInMillis();

			long diff = milliSec1 - milliSec2;
			String parent = Planet;

			int index = planets.indexOf(Planet.trim());
			object = new JSONObject();

			for (int i = 0; i < 9; i++) {

				index--;
				if (index < 0)
					index = planets.size() - 1;
				Planet = planets.get(index);

				milliSec1 = cal1.getTimeInMillis();

				long d = (long) ((diff * percntg.get(index)) / 100);

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(milliSec1);

				while (d != 0) {
					if (d > (long) Integer.MAX_VALUE) {
						d = d - (long) Integer.MAX_VALUE;
						calendar.add(Calendar.MILLISECOND, -Integer.MAX_VALUE);
					} else {
						calendar.add(Calendar.MILLISECOND, -(int) d);
						d = 0;
					}
				}

				object.put("planet", Planet);
				object.put("end", formatter.format(today));
				object.put("start", formatter.format(calendar.getTime()));
				today = formatter.parse(formatter.format(calendar.getTime()));
				cal1.setTime(today);

				if (Planet.equalsIgnoreCase(startingPlanet))
					break;

			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Inside catch block. Exception at calculateBack method of CalculateVimshottari action Class. Exception  : "+e.toString());
			e.printStackTrace();
		} finally {

			if (formatter != null)
				formatter = null;

			if (cal != null)
				cal = null;

			if (today != null)
				today = null;

			if (cal1 != null)
				cal1 = null;

			if (cal2 != null)
				cal2 = null;

		}
		return object;
	}

	public static JSONArray mainDasha(String startDate, String Planet) {
		logger.info("Inside mainDasha method of CalculateVimshottari action class.");
		DateFormat formatter = null;
		Calendar cal = null;
		Date today = null;
		Calendar cal1 = null;
		Calendar cal2 = null;
		JSONArray array = new JSONArray();
		JSONObject object = null;

		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");

			cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			today = cal.getTime();
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			today = formatter.parse(startDate);

			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
			cal1.setTime(today);
			cal2.setTime(today);
			long milliSec1 = cal1.getTimeInMillis();

			int index = planets.indexOf(Planet.trim());

			for (int i = 0; i < 9; i++) {

				if (index < 0)
					index = planets.size() - 1;
				if (index == planets.size())
					index = 0;

				Planet = planets.get(index);

				milliSec1 = cal1.getTimeInMillis();
				cal2.add(Calendar.YEAR, map.get(Planet));

				long milliSec2 = cal2.getTimeInMillis();
				long d = milliSec2 - milliSec1;

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(milliSec1);
				object = new JSONObject();

				while (d > 0) {
					if (d > (long) Integer.MAX_VALUE) {
						d = d - (long) Integer.MAX_VALUE;
						calendar.add(Calendar.MILLISECOND, Integer.MAX_VALUE);
					} else {
						calendar.add(Calendar.MILLISECOND, (int) d);
						d = 0;
					}
				}

				object.put("planet", Planet);
				object.put("start", formatter.format(today));
				object.put("end", formatter.format(calendar.getTime()));

				today = formatter.parse(formatter.format(calendar.getTime()));

				cal1.setTime(today);
				index++;
				array.put(i, object);
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Inside catch block. Exception at mainDasha method of CalculateVimshottari action Class. Exception  : "+e.toString());
			e.printStackTrace();
		} finally {
			if (formatter != null)
				formatter = null;

			if (cal != null)
				cal = null;

			if (today != null)
				today = null;

			if (cal1 != null)
				cal1 = null;

			if (cal2 != null)
				cal2 = null;

		}
		return array;

	}

	public static JSONObject calculateMainDasha(String startDate, String startingPlanet, String Planet) {
		logger.info("Inside calculateMainDasha method of CalculateVimshottari action class.");
		DateFormat formatter = null;
		Calendar cal = null;
		Date today = null;
		Calendar cal1 = null;
		Calendar cal2 = null;
		JSONArray array = new JSONArray();
		JSONObject object = null;

		try {
			formatter = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");

			cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			today = cal.getTime();
			formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
			today = formatter.parse(startDate);

			cal1 = Calendar.getInstance();
			cal2 = Calendar.getInstance();
			cal1.setTime(today);

			long milliSec1 = cal1.getTimeInMillis();

			String parent = Planet;

			int index = planets.indexOf(Planet.trim());

			for (int i = 0; i < 9; i++) {

				cal2.setTime(today);
				index--;
				if (index < 0)
					index = planets.size() - 1;
				Planet = planets.get(index);

				milliSec1 = cal1.getTimeInMillis();
				cal2.add(Calendar.YEAR, -map.get(Planet));
				long milliSec2 = cal2.getTimeInMillis();
				long d = milliSec1 - milliSec2;

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(milliSec1);
				object = new JSONObject();

				while (d != 0) {
					if (d > (long) Integer.MAX_VALUE) {
						d = d - (long) Integer.MAX_VALUE;
						calendar.add(Calendar.MILLISECOND, -Integer.MAX_VALUE);
					} else {
						calendar.add(Calendar.MILLISECOND, -(int) d);
						d = 0;
					}
				}

				object.put("planet", Planet);
				object.put("end", formatter.format(today));
				object.put("start", formatter.format(calendar.getTime()));

				today = formatter.parse(formatter.format(calendar.getTime()));
				cal1.setTime(today);

				if (Planet.equalsIgnoreCase(startingPlanet))
					break;

			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Inside catch block. Exception at calculateMainDasha method of CalculateVimshottari action Class. Exception  : "+e.toString());
			e.printStackTrace();
		} finally {

			if (formatter != null)
				formatter = null;

			if (cal != null)
				cal = null;

			if (today != null)
				today = null;

			if (cal1 != null)
				cal1 = null;

			if (cal2 != null)
				cal2 = null;

		}
		return object;

	}

}

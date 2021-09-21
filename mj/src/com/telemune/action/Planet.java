package com.telemune.action;

import java.util.EnumSet;
import java.util.Hashtable;


public enum Planet {

	Ketu(0, 0, 0, 0), Venus(2, 7, 0, 0), Sun(5, 0, 0, 0), Moon(4, 0, 0, 0), Mars(1, 8, 3, 7), Rahu(0, 0, 0,
			0), Jupiter(9, 12, 4, 8), Saturn(10, 11, 2, 9), Mercury(3, 6, 0, 0), UNKNOWN(0, 0, 0, 0);

	private static final Hashtable<Integer, Planet> lookup;
	private final int firstRashi;
	private final int secondRashi;
	private final int firstAspect;
	private final int secondAspect;

	static {
		lookup = new Hashtable();
		System.out.println("INSIDE STATIC >> ");
		for (Planet planet : EnumSet.allOf(Planet.class)) {
			lookup.put(Integer.valueOf(planet.getFirstRashi()), planet);
			if (planet.getSecondRashi() == 0)
				continue;
			lookup.put(Integer.valueOf(planet.getSecondRashi()), planet);
		}

		System.out.println("lookup>> " + lookup);

	}

	private Planet(int _firstRashi, int _secondRashi, int _firstAspect, int _secondAspect) {
		System.out.println("INSIDE PLANET CONS >>>> _firstRashi= " + _firstRashi + " ,_secondRashi=> " + _secondRashi
				+ "  ,_firstAspect=> " + _firstAspect + "  ,_secondAspect=> " + _secondAspect);
		this.firstRashi = _firstRashi;
		this.secondRashi = _secondRashi;
		this.firstAspect = _firstAspect;
		this.secondAspect = _secondAspect;
	}

	public int getFirstRashi() {
		return this.firstRashi;
	}

	public int getSecondRashi() {
		return this.secondRashi;
	}

	public int getFirstAspect() {
		return this.firstAspect;
	}

	public int getSecondAspect() {
		return this.secondAspect;
	}

	public static Planet toPlanets(String _planetString) {
		System.out.println("INSIDE TOPLANETS>> _planetString=>> " + _planetString);
		return valueOf(_planetString);
	}

	public static Planet fromOrdinal(int ordinal) {
		System.out.println("INSIDE fromOrdinal>> ordinal=>> " + ordinal);
		return (Planet) lookup.get(Integer.valueOf(ordinal));
	}
}

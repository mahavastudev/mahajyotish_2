package com.telemune.action;

import java.util.EnumSet;
import java.util.Hashtable;

public enum Rashi {
	Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces, UNKNOWN;

	private static final Hashtable<Integer, Rashi> lookup;

	static {
		lookup = new Hashtable();

		int ordinal = 0;
		for (Rashi rashi : EnumSet.allOf(Rashi.class)) {
			lookup.put(Integer.valueOf(ordinal), rashi);
			ordinal++;
		}
	}

	public static Rashi toRashi(String str) {
		try {
			return valueOf(str);
		} catch (Exception ex) {
		}
		return UNKNOWN;
	}

	public Rashi fromOrdinal(int ordinal) {
		return (Rashi) lookup.get(Integer.valueOf(ordinal));
	}
}

package com.heaptrip.util;

import java.util.Locale;

public class LanguageUtils {

	public static String getLanguageByLocale(Locale locale) {
		if (locale != null) {
			switch (locale.getCountry()) {
			// Azerbaijan
			case "AZ":
				// Armenia
			case "AM":
				// Byelorussia
			case "BY":
				// Kazakhstan
			case "KZ":
				// Kyrgyzstan
			case "KG":
				// Moldavia
			case "MD":
				// Russia
			case "RU":
				// Tajikistan
			case "TJ":
				// Turkmenistan
			case "TM":
				// Uzbekistan
			case "UZ":
				// Ukraine
			case "UA":
				return "ru";
			default:
				return "en";
			}
		} else {
			return "ru";
		}
	}

}

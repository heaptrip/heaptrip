package com.heaptrip.util;

import java.util.Locale;

public class LanguageUtils {

	public static String getLanguageByLocale(Locale locale) {
		String result = "en";
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
			result = "ru";
			break;
		}
		return result;
	}

}

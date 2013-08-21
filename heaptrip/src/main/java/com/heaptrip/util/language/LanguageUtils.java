package com.heaptrip.util.language;

import java.util.Locale;

public class LanguageUtils {

	private static final Locale ENGLISH = Locale.ENGLISH;

	private static final Locale RUSSIAN = new Locale("ru");

	public static String getLanguageByLocale(Locale locale) {
		if (locale != null && locale.getLanguage() != null) {
			switch (locale.getLanguage()) {
			// Azerbaijani
			case "az":
				// Armenian
			case "hy":
				// Byelorussia
			case "be":
				// Kazakh
			case "kk":
				// Kirghiz
			case "ky":
				// Moldavian
			case "mo":
				// Russian
			case "ru":
				// Tajik
			case "tg":
				// Turkmen
			case "tk":
				// Uzbek
			case "uz":
				// Ukrainian
			case "uk":
				return "ru";
			default:
				return "en";
			}
		} else {
			return "ru";
		}
	}

	public static Locale getEnglishLocale() {
		return ENGLISH;
	}

	public static Locale getRussianLocale() {
		return RUSSIAN;
	}

}

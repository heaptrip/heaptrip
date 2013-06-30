package com.heaptrip.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SlugUtils {

	private static SecureRandom random = new SecureRandom();

	private static final String SLUG_DATE_PATTERN = "Y.M.d.H.m.s";

	public static String generateSlug() {
		return new BigInteger(24, random).toString(32);
	}

	public static String getFullSlugBySlug(String slug) {
		final DateFormat formatter = new SimpleDateFormat(SLUG_DATE_PATTERN);
		return formatter.format(new Date()) + ":" + slug;
	}

}

package com.heaptrip.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SlugUtils {

	private static SecureRandom random = new SecureRandom();

	private static SimpleDateFormat formatter = new SimpleDateFormat("%Y.%m.%d.%H.%M.%S");

	public static String generateSlug() {
		return new BigInteger(24, random).toString(32);
	}

	public static String getFullSlugBySlug(String slug) {
		return formatter.format(new Date()) + ":" + slug;
	}

}

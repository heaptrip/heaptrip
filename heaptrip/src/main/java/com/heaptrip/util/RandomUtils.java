package com.heaptrip.util;

import java.util.Date;

public class RandomUtils {

	public static int getRandomInt(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1)) + min;
	}

	public static Date getRandomDate(Date start, Date end) {
		return new Date(start.getTime() + (long) (Math.random() * (end.getTime() - start.getTime())));
	}

}

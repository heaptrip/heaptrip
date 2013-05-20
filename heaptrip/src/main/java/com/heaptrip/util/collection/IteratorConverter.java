package com.heaptrip.util.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorConverter {
	public static <T> List<T> copyIterator(Iterator<T> iter) {
		List<T> copy = new ArrayList<T>();
		if (iter != null) {
			while (iter.hasNext()) {
				copy.add(iter.next());
			}
		}
		return copy;
	}
}

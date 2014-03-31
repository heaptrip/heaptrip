package com.heaptrip.util.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorConverter {
	public static <T> List<T> copyIterator(Iterator<T> iterator) {
		List<T> copy = new ArrayList<>();
		if (iterator != null) {
			while (iterator.hasNext()) {
				copy.add(iterator.next());
            }
        }
		return copy;
	}
}

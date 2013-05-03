package com.heaptrip.service.trip;

import java.util.Comparator;

import com.heaptrip.domain.entity.trip.TableItem;

public class TableItemComparator implements Comparator<TableItem> {
	@Override
	public int compare(TableItem o1, TableItem o2) {
		if (o2.getBegin() == null) {
			return 1;
		} else if (o1.getBegin() == null) {
			return 1;
		} else {
			return o1.getBegin().compareTo(o2.getBegin());
		}
	}
}

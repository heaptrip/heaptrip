package com.heaptrip.service.content.trip;

import java.util.Comparator;

import com.heaptrip.domain.entity.content.trip.TableItem;

public class TableItemDateEndComparator implements Comparator<TableItem> {
	@Override
	public int compare(TableItem o1, TableItem o2) {
		if (o2.getEnd() == null) {
			return -1;
		} else if (o1.getEnd() == null) {
			return 1;
		} else {
			return o1.getEnd().compareTo(o2.getEnd());
		}
	}
}
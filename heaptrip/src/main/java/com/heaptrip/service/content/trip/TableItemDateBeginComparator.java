package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.content.trip.TableItem;

import java.util.Comparator;

public class TableItemDateBeginComparator implements Comparator<TableItem> {
    @Override
    public int compare(TableItem o1, TableItem o2) {
        if (o2.getBegin() == null) {
            return -1;
        } else if (o1.getBegin() == null) {
            return 1;
        } else {
            return o1.getBegin().compareTo(o2.getBegin());
        }
    }
}

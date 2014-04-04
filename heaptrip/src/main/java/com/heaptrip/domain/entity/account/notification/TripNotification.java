package com.heaptrip.domain.entity.account.notification;

/**
 * Notafication for trips
 */
public class TripNotification extends ContentNotification {

    // table id
    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}


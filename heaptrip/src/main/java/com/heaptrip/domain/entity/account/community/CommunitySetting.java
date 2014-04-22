package com.heaptrip.domain.entity.account.community;

import com.heaptrip.domain.entity.account.Setting;

public class CommunitySetting extends Setting {

    private Boolean cap; // нет полей, нет маршалинга ;)

    public Boolean getCap() {
        return cap;
    }

    public void setCap(Boolean cap) {
        this.cap = cap;
    }
}

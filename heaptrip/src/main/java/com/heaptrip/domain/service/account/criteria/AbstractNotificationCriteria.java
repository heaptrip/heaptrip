package com.heaptrip.domain.service.account.criteria;

import com.heaptrip.domain.service.criteria.Criteria;


/**
 * Base class for notification criterias
 */
public abstract class AbstractNotificationCriteria extends Criteria {

    // notification type
    private String type;

    // notification status
    private String status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

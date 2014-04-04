package com.heaptrip.domain.service.account.criteria;

/**
 * Criteria to search communities notifications by owner id
 */
public class CommunityNotificationCriteria extends AbstractNotificationCriteria {

    // account id for communities owner
    private String ownerId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}

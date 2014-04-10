package com.heaptrip.domain.service.account.criteria.notification;


/**
 * Criteria to search notifications by account id
 */
public class AccountNotificationCriteria extends AbstractNotificationCriteria {

    // account id
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}

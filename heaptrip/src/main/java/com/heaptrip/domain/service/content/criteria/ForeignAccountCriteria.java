package com.heaptrip.domain.service.content.criteria;

/**
 * This criterion is used to find content for foreign account.
 */
public class ForeignAccountCriteria extends ContentSortCriteria {

    // foreign account id
    protected String accountId;

    // relation between the foreign account and the requested content
    protected RelationEnum relation;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public RelationEnum getRelation() {
        return relation;
    }

    public void setRelation(RelationEnum relation) {
        this.relation = relation;
    }

}

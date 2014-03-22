package com.heaptrip.domain.service.account.criteria;

import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.service.criteria.CategoryRegionCriteria;
import com.heaptrip.domain.service.criteria.IDListCriteria;

/**
 * Criterion for context search accounts
 */
public class AccountTextCriteria extends CategoryRegionCriteria {

    // account type
    private AccountEnum accountType;

    // text to search
    private String query;

    // criteria for friends
    private IDListCriteria friends;

    // criteria for publishers
    private IDListCriteria publishers;

    // criteria for owners
    private IDListCriteria owners;

    // criteria for staff
    private IDListCriteria staff;

    // criteria for members
    private IDListCriteria members;

    public AccountEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountEnum accountType) {
        this.accountType = accountType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public IDListCriteria getFriends() {
        return friends;
    }

    public void setFriends(IDListCriteria friends) {
        this.friends = friends;
    }

    public IDListCriteria getPublishers() {
        return publishers;
    }

    public void setPublishers(IDListCriteria publishers) {
        this.publishers = publishers;
    }

    public IDListCriteria getOwners() {
        return owners;
    }

    public void setOwners(IDListCriteria owners) {
        this.owners = owners;
    }

    public IDListCriteria getStaff() {
        return staff;
    }

    public void setStaff(IDListCriteria staff) {
        this.staff = staff;
    }

    public IDListCriteria getMembers() {
        return members;
    }

    public void setMembers(IDListCriteria members) {
        this.members = members;
    }

}

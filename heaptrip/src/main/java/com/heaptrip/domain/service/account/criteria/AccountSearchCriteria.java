package com.heaptrip.domain.service.account.criteria;

import java.util.Locale;

import com.heaptrip.domain.entity.account.AccountEnum;

/**
 * 
 * Criterion for context search accounts
 * 
 */
public class AccountSearchCriteria {

	// account type
	private AccountEnum accountType;

	// text to search
	private String query;

	// criteria for categories
	private IDListCriteria categories;

	// criteria for regions
	private IDListCriteria regions;

	// criteria for friends
	private IDListCriteria friends;

	// criteria for subscribers
	private IDListCriteria subscribers;

	// criteria for owners
	private IDListCriteria owners;

	// criteria for staff
	private IDListCriteria staff;

	// criteria for members
	private IDListCriteria members;

	// the number of records to skip
	private Long skip;

	// the maximum number of records
	private Long limit;

	// locale
	private Locale locale;

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

	public IDListCriteria getCategories() {
		return categories;
	}

	public void setCategories(IDListCriteria categories) {
		this.categories = categories;
	}

	public IDListCriteria getRegions() {
		return regions;
	}

	public void setRegions(IDListCriteria regions) {
		this.regions = regions;
	}

	public IDListCriteria getFriends() {
		return friends;
	}

	public void setFriends(IDListCriteria friends) {
		this.friends = friends;
	}

	public IDListCriteria getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(IDListCriteria subscribers) {
		this.subscribers = subscribers;
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

	public Long getSkip() {
		return skip;
	}

	public void setSkip(Long skip) {
		this.skip = skip;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}

package com.heaptrip.domain.entity.rating;

/**
 * 
 * Stores information about the rating of account
 * 
 */
public class AccountRating extends TotalRating {

	// correction factor for calculating rating. Default value of 1
	private int K;

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	public static AccountRating getDefaultValue() {
		AccountRating accountRating = new AccountRating();
		accountRating.setK(1);
		accountRating.setCount(0);
		accountRating.setValue(0.25);
		return accountRating;
	}

}

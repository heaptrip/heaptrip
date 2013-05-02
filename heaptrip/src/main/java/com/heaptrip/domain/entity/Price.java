package com.heaptrip.domain.entity;

import java.math.BigDecimal;

public class Price {

	private CurrencyEnum currency;

	private BigDecimal value;

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}

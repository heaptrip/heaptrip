package com.heaptrip.web.model.content;

import java.math.BigDecimal;

public class PriceModel {

	// type of currency
	private String currency;

	// amount
	private BigDecimal value;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}

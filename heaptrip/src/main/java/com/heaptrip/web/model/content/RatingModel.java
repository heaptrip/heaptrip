package com.heaptrip.web.model.content;

public class RatingModel {

	private Double value;
	private Integer count;

	public RatingModel() {

	}

	public RatingModel(double value) {
		setValue(value);
	}

	public RatingModel(double value, int cont) {
		setValue(value);
		setCount(cont);
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}

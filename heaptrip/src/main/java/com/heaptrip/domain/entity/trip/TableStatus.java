package com.heaptrip.domain.entity.trip;

public class TableStatus {

	private TableStatusEnum value;

	private String text;

	public TableStatusEnum getValue() {
		return value;
	}

	public void setValue(TableStatusEnum value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

package com.heaptrip.domain.entity.trip;

/**
 * 
 * Table status
 * 
 */
public class TableStatus {

	// status value
	private TableStatusEnum value;

	// additional description
	private String text;

	public TableStatus() {
		super();
		value = TableStatusEnum.OK;
	}

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

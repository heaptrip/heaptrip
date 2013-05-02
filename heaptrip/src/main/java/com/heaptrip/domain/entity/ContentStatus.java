package com.heaptrip.domain.entity;

public class ContentStatus {

	private ContentStatusEnum value;

	private String text;

	public ContentStatus(ContentStatusEnum value) {
		super();
		this.value = value;
	}

	public ContentStatus() {
		super();
	}

	public ContentStatusEnum getValue() {
		return value;
	}

	public void setValue(ContentStatusEnum value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

package com.heaptrip.domain.entity.mail;

public enum MailSenderEnum {
	NOREPLY("noreply@heaptrip.com");

	private String address;

	private MailSenderEnum(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

}

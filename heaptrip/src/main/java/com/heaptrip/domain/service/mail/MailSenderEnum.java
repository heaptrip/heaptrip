package com.heaptrip.domain.service.mail;

public enum MailSenderEnum {
	NOREPLY("noreply@heaptrip.com");

	private String adress;

	private MailSenderEnum(String adress) {
		this.adress = adress;
	}

	public String getAdress() {
		return adress;
	}

}

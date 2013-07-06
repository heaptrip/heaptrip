package com.heaptrip.domain.service.mail;

public interface MailService {
	public void sendNoreplyMessage(String to, String subject, String text);
}

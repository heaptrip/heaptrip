package com.heaptrip.domain.service.mail;

import javax.mail.MessagingException;

public interface MailService {
	public void sendNoreplyMessage(String to, String subject, String text)
			throws MessagingException;
}

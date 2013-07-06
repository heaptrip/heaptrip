package com.heaptrip.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.service.mail.MailSenderEnum;
import com.heaptrip.domain.service.mail.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private MailSender mailSender;

	@Override
	public void sendNoreplyMessage(String to, String subject, String text) {
		Assert.notNull(to, "destination address must not be null");
		Assert.notNull(subject, "subject must not be null");
		Assert.notNull(text, "text must not be null");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(MailSenderEnum.NOREPLY.getAdress());
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}

}

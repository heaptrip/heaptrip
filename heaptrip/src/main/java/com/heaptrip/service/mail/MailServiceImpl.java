package com.heaptrip.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.service.mail.MailSenderEnum;
import com.heaptrip.domain.service.mail.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendNoreplyMessage(String to, String subject, String text)
			throws MessagingException {
		Assert.notNull(to, "destination address must not be null");
		Assert.notNull(subject, "subject must not be null");
		Assert.notNull(text, "text must not be null");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(MailSenderEnum.NOREPLY.getAdress());
		helper.setTo(to);
		message.setSubject(subject, "UTF-8");
		message.setContent(text, "text/html; charset=utf-8");
		mailSender.send(message);
	}

}

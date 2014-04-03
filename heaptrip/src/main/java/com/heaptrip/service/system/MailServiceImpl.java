package com.heaptrip.service.system;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.mail.MailSenderEnum;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.system.MailException;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ErrorService errorService;

	@Override
    @Async
	public void sendNoreplyMessage(String to, String subject, String text) {
        Assert.notNull(to, "destination address must not be null");
		Assert.notNull(subject, "subject must not be null");
		Assert.notNull(text, "text must not be null");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(MailSenderEnum.NO_REPLY.getAddress());
			helper.setTo(to);
			message.setSubject(subject, "UTF-8");
			message.setContent(text, "text/html; charset=utf-8");
			mailSender.send(message);
		} catch (MessagingException e) {
			throw errorService.createException(MailException.class, e, ErrorEnum.ERR_SYSTEM_SEND_MAIL);
		}
	}

	@Override
    @Async
	public void sendNoreplyMessage(String[] to, String subject, String text) {
		Assert.notNull(to, "destination addresses must not be null");
		Assert.notNull(subject, "subject must not be null");
		Assert.notNull(text, "text must not be null");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(MailSenderEnum.NO_REPLY.getAddress());
			helper.setTo(to);
			message.setSubject(subject, "UTF-8");
			message.setContent(text, "text/html; charset=utf-8");
			mailSender.send(message);
		} catch (MessagingException e) {
			throw errorService.createException(MailException.class, e, ErrorEnum.ERR_SYSTEM_SEND_MAIL);
		}
	}

}

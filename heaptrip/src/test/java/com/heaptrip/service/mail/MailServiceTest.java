package com.heaptrip.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.heaptrip.domain.service.mail.MailService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class MailServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private MailService mailService;

	@Test(enabled = false, priority = 1)
	public void sendNoreplyMessage() {
		// call
		String to = "support@heaptrip.com";
		String subject = "test message";
		String text = "Hello!\nWe sent you a test message.";
		mailService.sendNoreplyMessage(to, subject, text);
	}
}
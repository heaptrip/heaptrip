package com.heaptrip.service.system;

import com.heaptrip.domain.entity.mail.MessageEnum;
import com.heaptrip.domain.entity.mail.MessageTemplate;
import com.heaptrip.domain.entity.mail.MessageTemplateStorage;
import com.heaptrip.domain.service.system.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.util.Locale;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class MailServiceTest extends AbstractTestNGSpringContextTests {

    private Locale locale = new Locale("ru");

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageTemplateStorage messageTemplateStorage;

    @Test(enabled = false, priority = 1)
    public void sendNoreplyMessage() throws MessagingException {
        String to = "support@heaptrip.com";
        MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.RESET_PASSWORD);
        mailService.sendNoreplyMessage(to, mt.getSubject(locale), mt.getText(locale));
    }
}
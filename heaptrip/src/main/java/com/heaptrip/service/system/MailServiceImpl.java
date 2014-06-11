package com.heaptrip.service.system;

import com.heaptrip.domain.entity.mail.Mail;
import com.heaptrip.domain.entity.mail.MailSenderEnum;
import com.heaptrip.domain.repository.mail.MailRepository;
import com.heaptrip.domain.service.system.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    private static final long NO_DATA_WAIT_TIME = 5000L;

    @Autowired
    private TaskExecutorAdapter taskExecutorAdapter;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender mailSender;

    private ConcurrentLinkedQueue<Mail> mailQueue = new ConcurrentLinkedQueue<>();

    private AtomicBoolean isActive = new AtomicBoolean();

    @Value("${mail.send:true}")
    private boolean isSendMail;

    @Value("${node.name:}")
    private String nodeName;

    @PostConstruct
    private void start() {
        logger.info("MailServices initialization ...");
        logger.info("node name: {}", nodeName);
        logger.info("send mail: {}", isSendMail);
        if (isSendMail) {
            mailQueue.addAll(mailRepository.findByNode(nodeName));
            isActive.set(true);
            taskExecutorAdapter.submit(new MailSender());
        }
        logger.info("MailServices successfully initialized");
    }

    @PreDestroy
    private void stop() {
        if (isSendMail) {
            isActive.set(false);
        }
    }

    @Override
    public void sendNoreplyMessage(String to, String subject, String text) {
        Assert.notNull(to, "destination address must not be null");
        Assert.notNull(subject, "subject must not be null");
        Assert.notNull(text, "text must not be null");

        sendNoreplyMessage(new String[]{to}, subject, text);
    }

    @Override
    public void sendNoreplyMessage(String[] to, String subject, String text) {
        Assert.notNull(to, "destination addresses must not be null");
        Assert.notNull(subject, "subject must not be null");
        Assert.notNull(text, "text must not be null");

        if (!isSendMail) {
            logger.debug("ignore message because isSendMail=false");
        }

        Mail mail = new Mail();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(text);
        mail.setCreated(new Date());
        mail.setNode(nodeName);

        mail = mailRepository.save(mail);
        mailQueue.add(mail);
    }

    public void sendNoReplyMail(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(MailSenderEnum.NO_REPLY.getAddress());
        helper.setTo(mail.getTo());
        message.setSubject(mail.getSubject(), "UTF-8");
        message.setContent(mail.getText(), "text/html; charset=utf-8");
        mailSender.send(message);
    }

    private class MailSender implements Runnable {

        @Override
        public void run() {
            try {
                logger.info("Thread for send emails is successfully launched");

                while (isActive.get()) {
                    try {
                        if (mailQueue.isEmpty()) {
                            Thread.sleep(NO_DATA_WAIT_TIME);
                        } else {
                            Mail mail = mailQueue.peek();
                            if (mail != null) {
                                sendNoReplyMail(mail);
                                logger.debug("send mail: {}", mail);
                                mailRepository.remove(mail);
                                mailQueue.remove();
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        try {
                            Thread.sleep(NO_DATA_WAIT_TIME);
                        } catch (InterruptedException e1) {
                            logger.error(e1.getMessage(), e1);
                        }
                    }
                }

            } finally {
                logger.info("Thread for send emails is successfully stopped");
            }
        }
    }

}


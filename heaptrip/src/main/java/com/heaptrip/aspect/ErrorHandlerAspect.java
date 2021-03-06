package com.heaptrip.aspect;

import com.heaptrip.domain.entity.mail.MailEnum;
import com.heaptrip.domain.entity.mail.MailTemplate;
import com.heaptrip.domain.entity.mail.MailTemplateStorage;
import com.heaptrip.domain.exception.Journalable;
import com.heaptrip.domain.exception.Mailable;
import com.heaptrip.domain.service.system.JournalService;
import com.heaptrip.domain.service.system.MailService;
import com.heaptrip.util.language.LanguageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Service
public class ErrorHandlerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerAspect.class);

    @Autowired
    private JournalService journalService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailTemplateStorage mailTemplateStorage;

    @Value("${error.journalables:}")
    private String errJournalables;

    @Value("${error.mailables:}")
    private String errMailables;

    @Value("${error.emails:}")
    private String errEmails;

    @Value("${node.name:}")
    private String nodeName;

    private Set<String> journalables;

    private Set<String> mailables;

    private String[] emails;

    @PostConstruct
    public void init() throws ClassNotFoundException {
        logger.info("ErrorHandlerAspect initialization ...");

        // set journalables
        if (StringUtils.isNotBlank(errJournalables)) {
            String[] arr = StringUtils.split(errJournalables, ",");
            if (ArrayUtils.isNotEmpty(arr)) {
                journalables = new HashSet<>(arr.length);
                for (String errClassName : arr) {
                    journalables.add(StringUtils.trim(errClassName));
                }
            }
        }
        if (journalables == null) {
            logger.info("journalable exceptions is not defined");
        } else {
            logger.info("journalable exceptions: {}", ArrayUtils.toString(journalables.toArray()));
        }

        // set mailables
        if (StringUtils.isNotBlank(errMailables)) {
            String[] arr = StringUtils.split(errMailables, ",");
            if (ArrayUtils.isNotEmpty(arr)) {
                mailables = new HashSet<>(arr.length);
                for (String errClassName : arr) {
                    mailables.add(StringUtils.trim(errClassName));
                }
            }
        }
        if (mailables == null) {
            logger.info("mailable exceptions is not defined");
        } else {
            logger.info("mailable exceptions: {}", ArrayUtils.toString(mailables.toArray()));
        }

        // set emails
        if (StringUtils.isNotBlank(errEmails)) {
            emails = StringUtils.split(errEmails, ",");
        }
        if (ArrayUtils.isEmpty(emails)) {
            logger.info("email addresses for send exceptions is not defined");
        } else {
            logger.info("email addresses for send exceptions: {}", ArrayUtils.toString(emails));
        }

        logger.info("ErrorHandlerAspect successfully initialized");
    }

    @Pointcut("execution(* com.heaptrip.service..*(..))")
    public void inServiceLayer() {
    }

    @AfterThrowing(pointcut = "inServiceLayer()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("An exception " + e + " has been thrown in " + joinPoint.getSignature().getName() + "()", e);

        if (e instanceof Journalable || (journalables != null && journalables.contains(e.getClass().getName()))) {
            try {
                journalService.addError(e);
                logger.debug("Add exception " + e + " to journal");
            } catch (Exception e1) {
                logger.error("Could not write exception to journal", e1);
            }
        }

        if (e instanceof Mailable || (mailables != null && mailables.contains(e.getClass().getName()))) {
            try {
                String stackTrace = StringUtils.join(ExceptionUtils.getStackFrames(e), "<br />");
                MailTemplate mailTemplate = mailTemplateStorage.getMailTemplate(MailEnum.ERROR);
                String subject = String.format(mailTemplate.getSubject(LanguageUtils.getEnglishLocale()), nodeName);
                String text = String.format(mailTemplate.getText(LanguageUtils.getEnglishLocale()), nodeName, stackTrace);
                mailService.sendNoreplyMessage(emails, subject, text);
                logger.debug("Send exception " + e + " by email");
            } catch (Exception e1) {
                logger.debug("Could not send exception message by e-mail", e1);
            }
        }
    }
}
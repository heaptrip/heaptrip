package com.heaptrip.domain.service.system;

import javax.mail.MessagingException;

/**
 * 
 * Service for send email
 * 
 */
public interface MailService {

	/**
	 * Send a message from address noreply@heaptrip.com to single address
	 * 
	 * @param to
	 *            address of the recipient
	 * @param subject
	 *            message subject
	 * @param text
	 *            message text
	 * @throws MessagingException
	 */
	public void sendNoreplyMessage(String to, String subject, String text);

	/**
	 * Send a message from address noreply@heaptrip.com to multiply addresses
	 * 
	 * @param to
	 *            addresses of the recipient
	 * @param subject
	 *            message subject
	 * @param text
	 *            message text
	 * @throws MessagingException
	 */
	public void sendNoreplyMessage(String[] to, String subject, String text);
}

package com.heaptrip.domain.entity.mail;

import java.util.Map;

public class MessageTemplateStorage {

	private Map<MessageEnum, MessageTemplate> messageTemplates;

	public Map<MessageEnum, MessageTemplate> getMessageTemplates() {
		return messageTemplates;
	}

	public void setMessageTemplates(Map<MessageEnum, MessageTemplate> messageTemplates) {
		this.messageTemplates = messageTemplates;
	}

	public MessageTemplate getMessageTemplate(MessageEnum messageType) {
		return messageTemplates.get(messageType);
	}

}

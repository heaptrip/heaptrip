package com.heaptrip.service.content.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.service.content.event.EventService;
import com.heaptrip.domain.service.content.event.EventUserService;
import com.heaptrip.util.language.LanguageUtils;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class EventUserServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private EventUserService eventUserService;

	@Autowired
	private EventService eventService;

	@Test(priority = 0)
	public void addEventMember() {
		// check members befote call test
		Event event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
		Assert.assertNotNull(event);
		Assert.assertEquals(event.getMembers(), 0);
		// call
		EventMember member = eventUserService.addEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
		// check member
		Assert.assertNotNull(member);
		Assert.assertNotNull(member.getId());
		Assert.assertNotNull(member.getContentId());
		Assert.assertEquals(member.getContentId(), EventDataProvider.CONTENT_ID);
		Assert.assertNotNull(member.getUserId());
		Assert.assertEquals(member.getUserId(), EventDataProvider.USER_ID);
		// check isEventMember
		boolean isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
		Assert.assertTrue(isEventMember);
		// check members field
		event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
		Assert.assertNotNull(event);
		Assert.assertEquals(event.getMembers(), 1);
	}

	@Test(priority = 1)
	public void getEventMembers() {
		// call without limit
		List<EventMember> members = eventUserService.getEventMembers(EventDataProvider.CONTENT_ID);
		// check
		Assert.assertNotNull(members);
		Assert.assertEquals(members.size(), 1);
		EventMember member = members.get(0);
		Assert.assertNotNull(member);
		Assert.assertNotNull(member.getId());
		Assert.assertNotNull(member.getContentId());
		Assert.assertEquals(member.getContentId(), EventDataProvider.CONTENT_ID);
		Assert.assertNotNull(member.getUserId());
		Assert.assertEquals(member.getUserId(), EventDataProvider.USER_ID);
		// call with limit
		members = eventUserService.getEventMembers(EventDataProvider.CONTENT_ID, 10);
		// check
		Assert.assertNotNull(members);
		Assert.assertEquals(members.size(), 1);
		member = members.get(0);
		Assert.assertNotNull(member);
		Assert.assertNotNull(member.getId());
		Assert.assertNotNull(member.getContentId());
		Assert.assertEquals(member.getContentId(), EventDataProvider.CONTENT_ID);
		Assert.assertNotNull(member.getUserId());
		Assert.assertEquals(member.getUserId(), EventDataProvider.USER_ID);
	}

	@Test(priority = 2)
	public void removeEventMember() {
		// call
		eventUserService.removeEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
		// check isEventMember
		boolean isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
		Assert.assertFalse(isEventMember);
		// check getEventMembers
		List<EventMember> members = eventUserService.getEventMembers(EventDataProvider.CONTENT_ID);
		Assert.assertTrue(members == null || members.isEmpty());
		// check members field
		Event event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
		Assert.assertNotNull(event);
		Assert.assertEquals(event.getMembers(), 0);
	}

	@Test(priority = 3)
	public void removeEventMembers() {
		// prepare data
		eventUserService.addEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
		boolean isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
		Assert.assertTrue(isEventMember);
		// call
		eventUserService.removeEventMembers(EventDataProvider.CONTENT_ID);
		// check isEventMember
		isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
		Assert.assertFalse(isEventMember);
		// check getEventMembers
		List<EventMember> members = eventUserService.getEventMembers(EventDataProvider.CONTENT_ID);
		Assert.assertTrue(members == null || members.isEmpty());
		// check members field
		Event event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
		Assert.assertNotNull(event);
		Assert.assertEquals(event.getMembers(), 0);
	}
}

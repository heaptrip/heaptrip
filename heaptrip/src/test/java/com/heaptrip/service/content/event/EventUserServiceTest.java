package com.heaptrip.service.content.event;

import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.service.content.event.EventService;
import com.heaptrip.domain.service.content.event.EventUserService;
import com.heaptrip.domain.service.content.event.criteria.EventMemberCriteria;
import com.heaptrip.util.language.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class EventUserServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EventUserService eventUserService;

    @Autowired
    private EventService eventService;

    @BeforeClass
    @AfterClass
    public void clean() {
        eventUserService.removeEventMembers(EventDataProvider.CONTENT_ID);
    }

    @Test(priority = 0)
    public void checkEventMemberWithoutMembers() {
        boolean isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
        Assert.assertFalse(isEventMember);
    }

    @Test(priority = 1)
    public void addEventMember() {
        // check members before call test
        Event event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getMembers(), 0);
        // call
        EventMember member = eventUserService.addEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
        // check member
        Assert.assertNotNull(member);
        Assert.assertNotNull(member.getId());
        Assert.assertEquals(member.getContentId(), EventDataProvider.CONTENT_ID);
        Assert.assertEquals(member.getUserId(), EventDataProvider.USER_ID);
        // check members field
        event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getMembers(), 1);
    }

    @Test(priority = 2)
    public void checkEventMemberWithMembers() {
        boolean isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
        Assert.assertTrue(isEventMember);
    }

    @Test(priority = 3)
    public void getMembersByCriteria() {
        // call without limit
        EventMemberCriteria memberCriteria = new EventMemberCriteria();
        memberCriteria.setEventId(EventDataProvider.CONTENT_ID);
        List<EventMember> members = eventUserService.getMembersByCriteria(memberCriteria);
        // check
        Assert.assertNotNull(members);
        Assert.assertEquals(members.size(), 1);
        EventMember member = members.get(0);
        Assert.assertNotNull(member);
        Assert.assertNotNull(member.getId());
        Assert.assertEquals(member.getContentId(), EventDataProvider.CONTENT_ID);
        Assert.assertEquals(member.getUserId(), EventDataProvider.USER_ID);
        // call with limit
        memberCriteria = new EventMemberCriteria();
        memberCriteria.setEventId(EventDataProvider.CONTENT_ID);
        memberCriteria.setSkip(0L);
        memberCriteria.setLimit(10L);
        members = eventUserService.getMembersByCriteria(memberCriteria);
        // check
        Assert.assertNotNull(members);
        Assert.assertEquals(members.size(), 1);
        member = members.get(0);
        Assert.assertNotNull(member);
        Assert.assertNotNull(member.getId());
        Assert.assertEquals(member.getContentId(), EventDataProvider.CONTENT_ID);
        Assert.assertEquals(member.getUserId(), EventDataProvider.USER_ID);
    }

    @Test(priority = 4)
    public void getCountByCriteria() {
        // call
        EventMemberCriteria memberCriteria = new EventMemberCriteria();
        memberCriteria.setEventId(EventDataProvider.CONTENT_ID);
        long count = eventUserService.getCountByCriteria(memberCriteria);
        // check
        Assert.assertEquals(count, 1L);
    }


    @Test(priority = 5)
    public void removeEventMember() {
        // call
        eventUserService.removeEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
        // check isEventMember
        boolean isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
        Assert.assertFalse(isEventMember);
        // check getCountByCriteria
        EventMemberCriteria memberCriteria = new EventMemberCriteria();
        memberCriteria.setEventId(EventDataProvider.CONTENT_ID);
        long count = eventUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 0L);
        // check members field
        Event event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getMembers(), 0);
    }

    @Test(priority = 6)
    public void removeEventMembers() {
        // prepare data
        eventUserService.addEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
        // call
        eventUserService.removeEventMembers(EventDataProvider.CONTENT_ID);
        // check isEventMember
        boolean isEventMember = eventUserService.isEventMember(EventDataProvider.CONTENT_ID, EventDataProvider.USER_ID);
        Assert.assertFalse(isEventMember);
        // check getCountByCriteria
        EventMemberCriteria memberCriteria = new EventMemberCriteria();
        memberCriteria.setEventId(EventDataProvider.CONTENT_ID);
        long count = eventUserService.getCountByCriteria(memberCriteria);
        Assert.assertEquals(count, 0L);
        // check members field
        Event event = eventService.get(EventDataProvider.CONTENT_ID, LanguageUtils.getEnglishLocale());
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getMembers(), 0);
    }
}

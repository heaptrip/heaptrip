package com.heaptrip.service.content.event;

import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.repository.content.event.EventMemberRepository;
import com.heaptrip.domain.repository.content.event.EventRepository;
import com.heaptrip.domain.service.content.event.EventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class EventUserServiceImpl implements EventUserService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMemberRepository eventMemberRepository;

    @Override
    public EventMember addEventMember(String eventId, String userId) {
        Assert.notNull(eventId, "eventId must not be null");
        Assert.notNull(userId, "userId must not be null");
        EventMember eventMember = new EventMember();
        eventMember.setContentId(eventId);
        eventMember.setUserId(userId);
        eventMemberRepository.save(eventMember);
        eventRepository.incMembers(eventId, 1);
        return eventMember;
    }

    @Override
    public List<EventMember> getEventMembers(String eventId) {
        Assert.notNull(eventId, "eventId must not be null");
        // TODO konovalov: add sort
        return eventMemberRepository.findByContentId(eventId);
    }

    @Override
    public List<EventMember> getEventMembers(String eventId, int limit) {
        Assert.notNull(eventId, "eventId must not be null");
        // TODO konovalov: add sort
        return eventMemberRepository.findByContentId(eventId, limit);
    }

    @Override
    public boolean isEventMember(String eventId, String userId) {
        Assert.notNull(eventId, "eventId must not be null");
        Assert.notNull(userId, "userId must not be null");
        return eventMemberRepository.existsByContentIdAndUserId(eventId, userId);
    }

    @Override
    public void removeEventMember(String eventId, String userId) {
        Assert.notNull(eventId, "eventId must not be null");
        Assert.notNull(userId, "userId must not be null");
        eventMemberRepository.removeByContentIdAndUserId(eventId, userId);
        eventRepository.incMembers(eventId, -1);
    }

    @Override
    public void removeEventMembers(String eventId) {
        Assert.notNull(eventId, "eventId must not be null");
        eventMemberRepository.removeByContentId(eventId);
        eventRepository.resetMembers(eventId);
    }

}

package com.heaptrip.service.content.event;

import java.util.List;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.service.content.event.EventUserService;

@Service
public class EventUserServiceImpl implements EventUserService {

	@Override
	public EventMember addEventMember(String eventId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventMember> getEventMembers(String eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventMember> getEventMembers(String eventId, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEventMember(String eventId, String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeEventMember(String eventId, String memberId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEventMembers(String eventId) {
		// TODO Auto-generated method stub

	}

}

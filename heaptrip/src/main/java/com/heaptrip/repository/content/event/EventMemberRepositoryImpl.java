package com.heaptrip.repository.content.event;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.repository.content.event.EventMemberRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

@Service
public class EventMemberRepositoryImpl extends CrudRepositoryImpl<EventMember> implements EventMemberRepository {

	@Override
	protected Class<EventMember> getCollectionClass() {
		return EventMember.class;
	}

	@Override
	protected String getCollectionName() {
		return CollectionEnum.MEMBERS.getName();
	}

}

package com.heaptrip.domain.repository.content.event;

import java.util.List;

import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.repository.CrudRepository;

public interface EventMemberRepository extends CrudRepository<EventMember> {

	public List<EventMember> findByContentId(String contentId);

	public List<EventMember> findByContentId(String contentId, int limit);

	public boolean existsByContentIdAndUserId(String contentId, String userId);

	public void removeByContentIdAndUserId(String contentId, String userId);

	public void removeByContentId(String contentId);
}

package com.heaptrip.domain.repository.content.event;

import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.content.event.criteria.EventMemberCriteria;

import java.util.List;

public interface EventMemberRepository extends CrudRepository<EventMember> {

    public List<EventMember> findByCriteria(EventMemberCriteria memberCriteria);

    public long countByCriteria(EventMemberCriteria memberCriteria);

    public boolean existsByContentIdAndUserId(String contentId, String userId);

    public void removeByContentIdAndUserId(String contentId, String userId);

    public void removeByContentId(String contentId);
}

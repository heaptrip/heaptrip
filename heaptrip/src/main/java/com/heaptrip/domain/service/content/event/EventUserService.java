package com.heaptrip.domain.service.content.event;

import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.service.content.event.criteria.EventMemberCriteria;

import java.util.List;

/**
 * Service to work with members of event
 */
public interface EventUserService {

    /**
     * Add user to member of event
     *
     * @param eventId event id
     * @param userId  user id
     * @return event member
     */
    public EventMember addEventMember(String eventId, String userId);

    /**
     * Get event members by criteria
     *
     * @param memberCriteria criteria for search event members
     * @return member list
     */
    public List<EventMember> getMembersByCriteria(EventMemberCriteria memberCriteria);


    /**
     * Get event members count by criteria
     *
     * @param memberCriteria criteria for search event members
     * @return count of members
     */
    public long getCountByCriteria(EventMemberCriteria memberCriteria);

    /**
     * Check that the user is a the event member
     *
     * @param eventId event id
     * @param userId  user id
     * @return true or false
     */
    public boolean isEventMember(String eventId, String userId);

    /**
     * Remove the user from list of event members
     *
     * @param eventId event id
     * @param userId  user id
     */
    public void removeEventMember(String eventId, String userId);

    /**
     * Remove all event members. It is recommended to use the after events tests
     * to clear test data
     *
     * @param eventId event id
     */
    public void removeEventMembers(String eventId);

}

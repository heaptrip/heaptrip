package com.heaptrip.domain.service.content.event;

import com.heaptrip.domain.entity.content.event.EventMember;

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
     * Get all members for event
     *
     * @param eventId event id
     * @return member list
     */
    public List<EventMember> getEventMembers(String eventId);

    /**
     * Get limit members for event
     *
     * @param eventId event id
     * @param limit   size of limit
     * @return members list
     */
    // TODO konovalov: add start offset and return total count (maybe total count move to separate method)
    public List<EventMember> getEventMembers(String eventId, int limit);

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

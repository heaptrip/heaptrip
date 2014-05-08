package com.heaptrip.web.model.travel.criteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 06.05.14
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class TripParticipantsCriteria {

    private String tripId;

    private List<ScheduleParticipantsCriteria> participantsCriteria;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public List<ScheduleParticipantsCriteria> getParticipantsCriteria() {
        return participantsCriteria;
    }

    public void setParticipantsCriteria(List<ScheduleParticipantsCriteria> participantsCriteria) {
        this.participantsCriteria = participantsCriteria;
    }
}

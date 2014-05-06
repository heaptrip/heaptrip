package com.heaptrip.web.model.travel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 06.05.14
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleParticipantModel   {

    ScheduleModel schedule;

    List<ParticipantModel> participants;

    public List<ParticipantModel> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantModel> participants) {
        this.participants = participants;
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModel schedule) {
        this.schedule = schedule;
    }
}

package com.heaptrip.web.model.travel.criteria;

import com.heaptrip.domain.service.criteria.Criteria;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 06.05.14
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleParticipantsCriteria extends Criteria {

    private String  scheduleId;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}

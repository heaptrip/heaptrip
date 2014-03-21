package com.heaptrip.domain.service.content.trip.criteria;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

/**
 * This criterion is used to find trip for a current user account.
 */
public class TripMyAccountCriteria extends MyAccountCriteria {

    // search period
    protected SearchPeriod period;

    public TripMyAccountCriteria() {
        super();
        contentType = ContentEnum.TRIP;
    }

    public SearchPeriod getPeriod() {
        return period;
    }

    public void setPeriod(SearchPeriod period) {
        this.period = period;
    }

}

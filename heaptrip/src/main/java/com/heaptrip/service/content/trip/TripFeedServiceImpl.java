package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TripFeedServiceImpl implements TripFeedService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public List<Trip> getContentsByFeedCriteria(TripFeedCriteria feedCriteria) {
        Assert.notNull(feedCriteria, "feedCriteria must not be null");
        Assert.notNull(feedCriteria.getContentType(), "contentType must not be null");
        return tripRepository.findByFeedCriteria(feedCriteria);
    }

    @Override
    public List<Trip> getContentsByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria) {
        Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
        Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
        Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
        return tripRepository.findByMyAccountCriteria(myAccountCriteria);
    }

    @Override
    public List<Trip> getContentsByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria) {
        Assert.notNull(foreignAccountCriteria, "foreignAccountCriteria must not be null");
        Assert.notNull(foreignAccountCriteria.getContentType(), "contentType must not be null");
        Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getAccountId()), "accountId must not be null");
        return tripRepository.findByForeignAccountCriteria(foreignAccountCriteria);
    }

    @Override
    public long getCountByFeedCriteria(TripFeedCriteria feedCriteria) {
        Assert.notNull(feedCriteria, "feedCriteria must not be null");
        Assert.notNull(feedCriteria.getContentType(), "contentType must not be null");
        return tripRepository.getCountByFeedCriteria(feedCriteria);
    }

    @Override
    public long getCountByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria) {
        Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
        Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
        Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
        return tripRepository.getCountByMyAccountCriteria(myAccountCriteria);
    }

    @Override
    public long getCountByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria) {
        Assert.notNull(foreignAccountCriteria, "foreignAccountCriteria must not be null");
        Assert.notNull(foreignAccountCriteria.getContentType(), "contentType must not be null");
        Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getAccountId()), "accountId must not be null");
        return tripRepository.getCountByForeignAccountCriteria(foreignAccountCriteria);
    }

}

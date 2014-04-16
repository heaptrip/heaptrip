package com.heaptrip.service.content;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.ContentFeedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ContentFeedServiceImpl implements ContentFeedService {

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public List<Content> getContentsByFeedCriteria(FeedCriteria feedCriteria) {
        Assert.notNull(feedCriteria, "feedCriteria must not be null");
        return contentRepository.findByFeedCriteria(feedCriteria);
    }

    @Override
    public List<Content> getContentsByMyAccountCriteria(MyAccountCriteria myAccountCriteria) {
        Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
        Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
        Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(!myAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
        Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
        return contentRepository.findByMyAccountCriteria(myAccountCriteria);
    }

    @Override
    public List<Content> getContentsByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria) {
        Assert.notNull(foreignAccountCriteria, "foreignAccountTripCriteria must not be null");
        Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(!foreignAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
        Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getAccountId()), "accountId must not be null");
        return contentRepository.findByForeignAccountCriteria(foreignAccountCriteria);
    }

    @Override
    public long getCountByFeedCriteria(FeedCriteria feedCriteria) {
        Assert.notNull(feedCriteria, "feedCriteria must not be null");
        return contentRepository.getCountByFeedCriteria(feedCriteria);
    }

    @Override
    public long getCountByMyAccountCriteria(MyAccountCriteria myAccountCriteria) {
        Assert.notNull(myAccountCriteria, "myAccountCriteria must not be null");
        Assert.notNull(myAccountCriteria.getContentType(), "contentType must not be null");
        Assert.notNull(myAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(!myAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
        Assert.isTrue(StringUtils.isNotBlank(myAccountCriteria.getUserId()), "userId must not be null");
        return contentRepository.getCountByMyAccountCriteria(myAccountCriteria);
    }

    @Override
    public long getCountByForeignAccountCriteria(ForeignAccountCriteria foreignAccountCriteria) {
        Assert.notNull(foreignAccountCriteria, "foreignAccountTripCriteria must not be null");
        Assert.notNull(foreignAccountCriteria.getRelation(), "relation must not be null");
        Assert.isTrue(!foreignAccountCriteria.getRelation().equals(RelationEnum.MEMBER), "relation must not be MEMBER");
        Assert.isTrue(StringUtils.isNotBlank(foreignAccountCriteria.getAccountId()), "accountId must not be null");
        return contentRepository.getCountByForeignAccountCriteria(foreignAccountCriteria);
    }
}

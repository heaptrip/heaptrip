package com.heaptrip.domain.service.content.feed;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

import java.util.List;

/**
 * Base interface to perform  content search
 *
 * @param <T>         content class
 * @param <Feed>      criteria to search content for feed
 * @param <MyAccount> criteria to search in my account
 * @param <Foreign>   criteria to search in foreign account
 */
public interface FeedService<T extends Content, Feed extends FeedCriteria, MyAccount extends MyAccountCriteria, Foreign extends ForeignAccountCriteria> {

    /**
     * Get contents list which suitable for search criteria
     *
     * @param feedCriteria criteria to search content for feed
     * @return contents list
     */
    public List<T> getContentsByFeedCriteria(Feed feedCriteria);

    /**
     * Get contents list which suitable for search criteria
     *
     * @param myAccountCriteria criteria to search in my account
     * @return contents list
     */
    public List<T> getContentsByMyAccountCriteria(MyAccount myAccountCriteria);

    /**
     * Get contents list which suitable for search criteria
     *
     * @param foreignAccountCriteria criteria to search in foreign account
     * @return contents list
     */
    public List<T> getContentsByForeignAccountCriteria(Foreign foreignAccountCriteria);

    /**
     * Get number of contents which suitable for search criteria
     *
     * @param feedCriteria criteria to search content for feed
     * @return number of contents
     */
    public long getCountByFeedCriteria(Feed feedCriteria);

    /**
     * Get number of contents which suitable for search criteria
     *
     * @param myAccountCriteria criteria to search in my account
     * @return number of contents
     */
    public long getCountByMyAccountCriteria(MyAccount myAccountCriteria);

    /**
     * Get number of contents which suitable for search criteria
     *
     * @param foreignAccountCriteria criteria to search in foreign account
     * @return number of contents
     */
    public long getCountByForeignAccountCriteria(Foreign foreignAccountCriteria);

}

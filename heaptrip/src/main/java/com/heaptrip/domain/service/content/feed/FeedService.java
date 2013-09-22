package com.heaptrip.domain.service.content.feed;

import java.util.List;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;

public interface FeedService<T extends Content, Feed extends FeedCriteria, MyAccount extends MyAccountCriteria, Foreign extends ForeignAccountCriteria> {

	/**
	 * Get contents list which suitable for search criteria
	 * 
	 * @param feedCriteria
	 * @return contents list
	 */
	public List<T> getContentsByFeedCriteria(Feed feedCriteria);

	/**
	 * Get contents list which suitable for search criteria
	 * 
	 * @param myAccountCriteria
	 * @return contents list
	 */
	public List<T> getContentsByMyAccountCriteria(MyAccount myAccountCriteria);

	/**
	 * Get contents list which suitable for search criteria
	 * 
	 * @param foreignAccountCriteria
	 * @return contents list
	 */
	public List<T> getContentsByForeignAccountCriteria(Foreign foreignAccountCriteria);

	/**
	 * Get number of contents which suitable for search criteria
	 * 
	 * @param feedCriteria
	 * @return number of contents
	 */
	public long getCountByFeedCriteria(Feed feedCriteria);

	/**
	 * Get number of contents which suitable for search criteria
	 * 
	 * @param myAccountCriteria
	 * @return number of contents
	 */
	public long getCountByMyAccountCriteria(MyAccount myAccountCriteria);

	/**
	 * Get number of contents which suitable for search criteria
	 * 
	 * @param foreignAccountCriteria
	 * @return number of contents
	 */
	public long getCountByForeignAccountCriteria(Foreign foreignAccountCriteria);

}

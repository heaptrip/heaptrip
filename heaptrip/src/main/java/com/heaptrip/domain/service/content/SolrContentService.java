package com.heaptrip.domain.service.content;

import java.util.List;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.SolrContentCriteria;

/**
 * 
 * Service for indexing and search documents in Apache Solr
 * 
 */
public interface SolrContentService {

	/**
	 * Add content to Apache Solr by contentId
	 * 
	 * @param contentId
	 */
	public void saveContent(String contentId);

	/**
	 * Remove content from Apache Solr by contentId
	 * 
	 * @param contentId
	 */
	public void removeContent(String contentId);

	/**
	 * Search contents by <link> SolrContentCriteria
	 * 
	 * @param SolrContentCriteria
	 *            criteria
	 * 
	 * @return list of contents
	 */
	public List<Content> findContentsBySolrContentCriteria(SolrContentCriteria criteria);
}

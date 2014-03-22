package com.heaptrip.domain.service.content;

import com.heaptrip.domain.service.content.criteria.ContentSearchResponse;
import com.heaptrip.domain.service.content.criteria.ContentTextCriteria;

/**
 * Service for indexing and search documents in Apache Solr
 */
public interface ContentSearchService {

    /**
     * Add content to Apache Solr by contentId
     *
     * @param contentId content ID
     */
    public void saveContent(String contentId);

    /**
     * Remove content from Apache Solr by contentId
     *
     * @param contentId content ID
     */
    public void removeContent(String contentId);

    /**
     * Search contents by TextSearchCriteria
     *
     * @param criteria content text search criteria
     * @return content search response
     */
    public ContentSearchResponse findContentsByTextCriteria(ContentTextCriteria criteria);
}

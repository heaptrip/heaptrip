package com.heaptrip.domain.repository.solr;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.repository.solr.entity.SolrContentSearchResponse;
import com.heaptrip.domain.service.content.criteria.ContentTextCriteria;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface SolrContentRepository {

    public void save(Content content) throws SolrServerException, IOException;

    public void remove(String contentId) throws SolrServerException, IOException;

    /**
     * Commit changed to Apache Solr.
     * Used only in tests.
     *
     * @throws IOException
     * @throws SolrServerException
     */
    public void commit() throws IOException, SolrServerException;

    public SolrContentSearchResponse findByСontextSearchCriteria(ContentTextCriteria criteria) throws SolrServerException;
}

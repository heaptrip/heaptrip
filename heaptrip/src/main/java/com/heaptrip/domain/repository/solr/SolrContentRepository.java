package com.heaptrip.domain.repository.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.repository.solr.entity.SolrSearchContentResponse;
import com.heaptrip.domain.service.content.criteria.ContextSearchCriteria;

public interface SolrContentRepository {

	public void save(Content content) throws SolrServerException, IOException;

	public void remove(String contentId) throws SolrServerException, IOException;

	public SolrSearchContentResponse findByСontextSearchCriteria(ContextSearchCriteria criteria) throws SolrServerException;
}

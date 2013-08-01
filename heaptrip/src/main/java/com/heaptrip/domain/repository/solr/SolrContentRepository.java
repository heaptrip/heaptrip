package com.heaptrip.domain.repository.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import com.heaptrip.domain.entity.content.Content;

public interface SolrContentRepository {

	public void save(Content content) throws SolrServerException, IOException;

	public void removeById(String contentId) throws SolrServerException, IOException;

	// public List<SolrRegion> findByNameOrText(String text, Long skip, Long
	// limit, Locale locale) throws SolrServerException;
}

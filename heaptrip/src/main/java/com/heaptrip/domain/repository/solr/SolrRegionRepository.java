package com.heaptrip.domain.repository.solr;

import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;

import com.heaptrip.domain.repository.solr.entity.SolrRegion;

public interface SolrRegionRepository {

	public List<SolrRegion> findByName(String name, Long skip, Long limit, Locale locale) throws SolrServerException;

}

package com.heaptrip.domain.repository.solr;

import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;

public interface RegionSolrRepository {

	public List<SolrRegion> findByName(String name, Long skip, Long limit, Locale locale) throws SolrServerException;

}

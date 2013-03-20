package com.heaptrip.domain.search;

import org.apache.solr.client.solrj.SolrServer;

public interface SolrContext {
	public static final String SERVICE_NAME = "solrContext";

	public SolrServer getGeoCore();

	public SolrServer getContentCore();
}

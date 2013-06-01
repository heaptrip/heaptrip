package com.heaptrip.domain.repository.solr;

import org.apache.solr.client.solrj.SolrServer;

public interface SolrContext {
	public static final String REGIONS_CORE = "regions";

	public static final String CONTENTS_CORE = "contents";

	public SolrServer getCore(String coreName);
}

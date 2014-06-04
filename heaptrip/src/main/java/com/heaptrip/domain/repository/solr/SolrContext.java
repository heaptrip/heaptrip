package com.heaptrip.domain.repository.solr;

import com.heaptrip.repository.solr.SolrCoreEnum;
import org.apache.solr.client.solrj.SolrServer;

public interface SolrContext {

    public SolrServer getCore(SolrCoreEnum coreType);

}

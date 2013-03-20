package com.heaptrip.search;

import java.net.MalformedURLException;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.search.SolrContext;

@Service(SolrContext.SERVICE_NAME)
public class SolrContextImpl implements SolrContext {

	private static final Logger logger = LoggerFactory.getLogger(SolrContextImpl.class);

	private static String GEO_CORE = "geo";

	private static String CONTENT_CORE = "content";

	@Value("${search.urls}")
	private String urls;

	private SolrServer geoCore = null;

	private SolrServer contentCore = null;

	@PostConstruct
	public void init() throws MalformedURLException {
		logger.info("Solr cores initialization ...");
		logger.info("solr urls: {}", urls);

		if (urls == null || urls.isEmpty()) {
			throw new RuntimeException("Solr cores not initialized: search urls not defined");
		}

		if (urls.startsWith("http")) {
			// urls store solr url
			geoCore = new HttpSolrServer(urls + "/" + GEO_CORE);
			contentCore = new HttpSolrServer(urls + "/" + CONTENT_CORE);
		} else {
			// urls store zookeeper endpoints
			geoCore = new CloudSolrServer(urls);
			((CloudSolrServer) geoCore).setDefaultCollection(GEO_CORE);
			((CloudSolrServer) geoCore).connect();

			contentCore = new CloudSolrServer(urls);
			((CloudSolrServer) contentCore).setDefaultCollection(CONTENT_CORE);
			((CloudSolrServer) contentCore).connect();
		}

		logger.info("Solr cores successfully initialized");
	}

	@Override
	public SolrServer getGeoCore() {
		return geoCore;
	}

	@Override
	public SolrServer getContentCore() {
		return contentCore;
	}

}

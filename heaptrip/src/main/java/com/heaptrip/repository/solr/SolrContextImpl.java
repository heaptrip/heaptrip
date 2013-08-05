package com.heaptrip.repository.solr;

import java.net.MalformedURLException;

import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.repository.solr.SolrContext;

@Service
public class SolrContextImpl implements SolrContext {

	private static final Logger logger = LoggerFactory.getLogger(SolrContextImpl.class);

	@Value("${search.urls}")
	private String urls;

	private SolrServer regionsCore = null;

	private SolrServer contentsCore = null;

	@PostConstruct
	public void init() throws MalformedURLException {
		logger.info("Solr cores initialization ...");
		logger.info("solr urls: {}", urls);

		if (urls == null || urls.isEmpty()) {
			throw new RuntimeException("Solr cores not initialized: search urls not defined");
		}

		if (urls.startsWith("http")) {
			// urls store solr url
			regionsCore = new HttpSolrServer(urls + "/" + REGIONS_CORE);
			contentsCore = new HttpSolrServer(urls + "/" + CONTENTS_CORE);
		} else {
			// urls store zookeeper endpoints
			regionsCore = new CloudSolrServer(urls);
			((CloudSolrServer) regionsCore).setDefaultCollection(REGIONS_CORE);
			((CloudSolrServer) regionsCore).connect();

			contentsCore = new CloudSolrServer(urls);
			((CloudSolrServer) contentsCore).setDefaultCollection(CONTENTS_CORE);
			((CloudSolrServer) contentsCore).connect();
		}

		logger.info("Solr cores successfully initialized");
	}

	@Override
	public SolrServer getCore(String coreName) {
		switch (coreName) {
		case REGIONS_CORE:
			return regionsCore;
		case CONTENTS_CORE:
			return contentsCore;
		default:
			throw new IllegalArgumentException("Unsupported core name: " + coreName);
		}
	}

}

package com.heaptrip.repository.solr;

import com.heaptrip.domain.repository.solr.SolrContext;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;

@Service
public class SolrContextImpl implements SolrContext {

    private static final Logger logger = LoggerFactory.getLogger(SolrContextImpl.class);

    @Value("${solr.urls}")
    private String urls;

    @Value("${solr.core.name.regions}")
    private String regionsCoreName;

    @Value("${solr.core.name.contents}")
    private String contentsCoreName;

    @Value("${solr.core.name.accounts}")
    private String accountsCoreName;

    private SolrServer regionsCore = null;

    private SolrServer contentsCore = null;

    private SolrServer accountsCore = null;

    @PostConstruct
    public void init() throws MalformedURLException {
        logger.info("Solr cores initialization ...");
        logger.info("solr urls: {}", urls);
        logger.info("solr regions core name: {}", regionsCoreName);
        logger.info("solr contents core name: {}", contentsCoreName);
        logger.info("solr accounts core name: {}", accountsCoreName);

        if (urls == null || urls.isEmpty()) {
            throw new RuntimeException("Solr cores not initialized: search urls not defined");
        }

        if (urls.startsWith("http")) {
            // urls store solr url
            regionsCore = new HttpSolrServer(urls + "/" + regionsCoreName);
            contentsCore = new HttpSolrServer(urls + "/" + contentsCoreName);
            accountsCore = new HttpSolrServer(urls + "/" + accountsCoreName);
        } else {
            // urls store zookeeper endpoints
            regionsCore = new CloudSolrServer(urls);
            ((CloudSolrServer) regionsCore).setDefaultCollection(regionsCoreName);
            ((CloudSolrServer) regionsCore).connect();

            contentsCore = new CloudSolrServer(urls);
            ((CloudSolrServer) contentsCore).setDefaultCollection(contentsCoreName);
            ((CloudSolrServer) contentsCore).connect();

            accountsCore = new CloudSolrServer(urls);
            ((CloudSolrServer) accountsCore).setDefaultCollection(accountsCoreName);
            ((CloudSolrServer) accountsCore).connect();
        }

        logger.info("Solr cores successfully initialized");
    }

    @Override
    public SolrServer getCore(SolrCoreEnum coreType) {
        switch (coreType) {
            case REGIONS:
                return regionsCore;
            case CONTENTS:
                return contentsCore;
            case ACCOUNTS:
                return accountsCore;
            default:
                throw new IllegalArgumentException("Unsupported core name: " + coreType);
        }
    }
}

package com.heaptrip.search.geo;

import java.util.Arrays;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.search.SolrContext;
import com.heaptrip.domain.search.geo.GeoSearch;

@Service(GeoSearch.SERVICE_NAME)
public class GeoSearchImpl implements GeoSearch {

	private static final Logger logger = LoggerFactory.getLogger(GeoSearchImpl.class);

	@Autowired
	private SolrContext solrContext;

	@Override
	public String[] searchIdsByName(String name, int start, int rows) throws SolrServerException {
		if (logger.isTraceEnabled()) {
			String msg = String.format("name: %s, start: %d, rows: %d", name, start, rows);
			logger.trace(msg);
		}

		SolrServer geoCore = solrContext.getGeoCore();

		SolrQuery parameters = new SolrQuery();
		parameters.set("q", name);
		parameters.set("start", "0");
		parameters.set("rows", "10");
		parameters.set("defType", "dismax");
		parameters.set("qf", "text_ru text_en");

		QueryResponse response = geoCore.query(parameters);
		SolrDocumentList results = response.getResults();

		if (logger.isTraceEnabled()) {
			String msg = String.format("QTime: %d, NumFound: %d, size: %d", response.getQTime(), results.getNumFound(),
					results.size());
			logger.trace(msg);
		}

		String[] result = new String[results.size()];

		for (int i = 0; i < results.size(); ++i) {
			SolrDocument doc = results.get(i);
			result[i] = (String) doc.getFieldValue("id");
		}

		if (logger.isTraceEnabled()) {
			String msg = String.format("name: %s, start: %d, rows: %d, result: %s", name, start, rows,
					Arrays.toString(result));
			logger.trace(msg);
		}

		// logger.debug("searchIdsByName: name: {}, result: {}", name, result);

		return result;
	}

}

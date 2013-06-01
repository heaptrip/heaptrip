package com.heaptrip.repository.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import com.heaptrip.domain.repository.solr.SolrContext;
import com.heaptrip.domain.repository.solr.SolrRegion;
import com.heaptrip.domain.repository.solr.SolrRegionRepository;
import com.heaptrip.util.LanguageUtils;

@Service
public class RegionSolrRepositoryImpl implements SolrRegionRepository {

	private static final Logger logger = LoggerFactory.getLogger(RegionSolrRepositoryImpl.class);

	@Autowired
	private SolrContext solrContext;

	@Override
	public List<SolrRegion> findByName(String name, Long skip, Long limit, Locale locale) throws SolrServerException {
		SolrServer core = solrContext.getCore(SolrContext.REGIONS_CORE);
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String.format("id name_%s parent type path_%s", lang, lang);
		SolrQuery query = new SolrQuery();
		query.set("q", name);
		if (skip != null) {
			query.set("start", skip.toString());
		}
		if (limit != null) {
			query.set("rows", limit.toString());
		}
		query.set("defType", "dismax");
		query.set("qf", "text_ru text_en");
		query.set("fl", fields);

		if (logger.isDebugEnabled()) {
			logger.debug("query: {}", query);
		}

		QueryResponse response = core.query(query);
		SolrDocumentList results = response.getResults();

		if (logger.isDebugEnabled()) {
			String msg = String.format("QTime: %d, NumFound: %d, size: %d", response.getQTime(), results.getNumFound(),
					results.size());
			logger.debug(msg);
		}

		List<SolrRegion> solrRegions = new ArrayList<>();
		for (int i = 0; i < results.size(); ++i) {
			SolrDocument doc = results.get(i);
			SolrRegion solrRegion = toSolrRegion(doc);
			solrRegions.add(solrRegion);
		}

		return solrRegions;
	}

	private SolrRegion toSolrRegion(SolrDocument doc) {
		SolrRegion solrRegion = new SolrRegion();
		if (doc.getFieldValue("id") != null) {
			solrRegion.setId((String) doc.getFieldValue("id"));
		}
		if (doc.getFieldValue("name_en") != null) {
			solrRegion.setNameEn((String) doc.getFieldValue("name_en"));
		}
		if (doc.getFieldValue("name_ru") != null) {
			solrRegion.setNameRu((String) doc.getFieldValue("name_ru"));
		}
		if (doc.getFieldValue("parent") != null) {
			solrRegion.setParent((String) doc.getFieldValue("parent"));
		}
		if (doc.getFieldValue("type") != null) {
			solrRegion.setType((String) doc.getFieldValue("type"));
		}
		if (doc.getFieldValue("path_en") != null) {
			solrRegion.setPathEn((String) doc.getFieldValue("path_en"));
		}
		if (doc.getFieldValue("path_ru") != null) {
			solrRegion.setPathRu((String) doc.getFieldValue("path_ru"));
		}
		return solrRegion;
	}

}

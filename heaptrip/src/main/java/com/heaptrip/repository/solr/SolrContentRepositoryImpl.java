package com.heaptrip.repository.solr;

import java.io.IOException;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.repository.solr.SolrContentRepository;
import com.heaptrip.domain.repository.solr.SolrContext;

@Service
public class SolrContentRepositoryImpl implements SolrContentRepository {

	private static final Logger logger = LoggerFactory.getLogger(SolrContentRepositoryImpl.class);

	@Autowired
	private SolrContext solrContext;

	@Override
	public void save(Content content) throws SolrServerException, IOException {

		SolrInputDocument doc = new SolrInputDocument();

		// set id
		if (StringUtils.isNotBlank(content.getId())) {
			doc.addField("id", content.getId());
		} else {
			throw new IllegalArgumentException("content id must not be null");
		}

		// set class
		if (StringUtils.isNotBlank(content.get_class())) {
			doc.addField("class", content.get_class());
		} else {
			throw new IllegalArgumentException("content class must not be null");
		}

		// set categories
		if (ArrayUtils.isNotEmpty(content.getAllCategories())) {
			doc.addField("categories", content.getAllCategories());
		}

		// set regions
		if (ArrayUtils.isNotEmpty(content.getAllRegions())) {
			doc.addField("regions", content.getAllRegions());
		}

		// set allowed
		if (ArrayUtils.isNotEmpty(content.getAllowed())) {
			doc.addField("allowed", content.getAllowed());
		}

		// set published
		if (content.getCreated() != null) {
			doc.addField("published", content.getCreated());
		}

		// set text fields
		if (ArrayUtils.isNotEmpty(content.getLangs())) {
			for (String lang : content.getLangs()) {
				Locale locale = new Locale(lang);
				// set name
				String fieldName = "name_" + lang;
				String fieldValue = content.getName().getValue(locale);
				if (StringUtils.isNotBlank(fieldValue)) {
					doc.addField(fieldName, fieldValue);
				}
				// set summary
				fieldName = "summary_" + lang;
				fieldValue = content.getSummary().getValue(locale);
				if (StringUtils.isNotBlank(fieldValue)) {
					doc.addField(fieldName, fieldValue);
				}
				// set description
				fieldName = "description_" + lang;
				fieldValue = content.getDescription().getValue(locale);
				if (StringUtils.isNotBlank(fieldValue)) {
					doc.addField(fieldName, fieldValue);
				}
			}
		}

		logger.debug("Prepare document for add to solr: {}", doc);

		SolrServer core = solrContext.getCore(SolrContext.CONTENTS_CORE);

		UpdateResponse response = core.add(doc);
		logger.debug("Response to adding a document: {}", response);

		response = core.commit();
		logger.debug("Response to committing a document: {}", response);
	}

	@Override
	public void removeById(String contentId) throws SolrServerException, IOException {
		SolrServer core = solrContext.getCore(SolrContext.CONTENTS_CORE);

		UpdateResponse response = core.deleteById(contentId);
		logger.debug("Response to removing a document with id={}: {}", contentId, response);
	}
}

package com.heaptrip.repository.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.repository.solr.SolrContentRepository;
import com.heaptrip.domain.repository.solr.SolrContext;
import com.heaptrip.domain.repository.solr.entity.SolrContent;
import com.heaptrip.domain.repository.solr.entity.SolrContentSearchResponse;
import com.heaptrip.domain.service.content.criteria.ContextSearchCriteria;

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
		doc.addField("class", content.getClass().getName());

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

		// set multi languages text fields
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
	public void remove(String contentId) throws SolrServerException, IOException {
		SolrServer core = solrContext.getCore(SolrContext.CONTENTS_CORE);

		UpdateResponse response = core.deleteById(contentId);
		logger.debug("Response to removing a document with id={}: {}", contentId, response);

		response = core.commit();
		logger.debug("Response to committing a remove: {}", response);
	}

	@Override
	public SolrContentSearchResponse findByСontextSearchCriteria(ContextSearchCriteria criteria)
			throws SolrServerException {

		SolrQuery query = new SolrQuery();
		// set q
		query.set("q", criteria.getQuery());
		// set fq
		String fq = null;
		// set fq for class
		if (criteria.getContentType() != null) {
			fq = "class:" + criteria.getContentType().getClazz();
			query.add("fq", fq);
		}
		// set fq for allowed
		fq = "allowed:(" + Content.ALLOWED_ALL_USERS;
		if (criteria.getUserId() != null) {
			fq += " OR " + criteria.getUserId();
		}
		fq += ")";
		query.add("fq", fq);
		// set fq for categories
		if (criteria.getCategoryIds() != null) {
			fq = null;
			for (String id : criteria.getCategoryIds()) {
				if (fq == null) {
					fq = "categories:(" + id;
				} else {
					fq += " OR " + id;
				}
			}
			fq += ")";
			query.add("fq", fq);
		}
		// set fq for regions
		if (criteria.getRegionIds() != null) {
			fq = null;
			for (String id : criteria.getRegionIds()) {
				if (fq == null) {
					fq = "regions:(" + id;
				} else {
					fq += " OR " + id;
				}
			}
			fq += ")";
			query.add("fq", fq);
		}
		// set start
		if (criteria.getSkip() != null) {
			query.set("start", criteria.getSkip().toString());
		}
		// set rows
		if (criteria.getLimit() != null) {
			query.set("rows", criteria.getLimit().toString());
		}
		// set fl
		query.set("fl", "id class");
		// set defType
		query.set("defType", "dismax");
		// set qf
		query.set("qf", "name_ru text_ru name_en text_en");
		// set pf for relevant search by name and text: boost name higher text
		// field
		query.set("pf", "name_ru^10 name_en^10 text_ru^5 text_en^5");
		// set bf for sorting by published date
		query.set("bf", "recip(ms(NOW/HOUR,published),3.16e-11,1,1)");
		// set highlighting
		query.set("hl", "true");
		if (criteria.getTextLength() != null) {
			query.set("hl.fragsize", criteria.getTextLength().toString());
		}
		if (criteria.getHighlight() == null || criteria.getHighlight().getPre() == null
				|| criteria.getHighlight().getPost() == null) {
			query.set("hl.simple.pre", "<em>");
			query.set("hl.simple.post", "</em>");
		} else {
			query.set("hl.simple.pre", criteria.getHighlight().getPre());
			query.set("hl.simple.post", criteria.getHighlight().getPost());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("find contents query: {}", query);
		}

		SolrServer core = solrContext.getCore(SolrContext.CONTENTS_CORE);
		QueryResponse response = core.query(query);
		SolrDocumentList results = response.getResults();

		if (logger.isDebugEnabled()) {
			String msg = String.format("QTime: %d, NumFound: %d, size: %d", response.getQTime(), results.getNumFound(),
					results.size());
			logger.debug(msg);
		}

		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

		List<SolrContent> solrContents = new ArrayList<>(results.size());

		for (int i = 0; i < results.size(); ++i) {
			// read doc
			SolrDocument doc = results.get(i);

			// convert to solrt content document
			SolrContent content = toSolrContent(doc, highlighting);

			logger.debug("find document: {}", content);

			solrContents.add(content);
		}

		SolrContentSearchResponse result = new SolrContentSearchResponse();
		result.setNumFound(results.getNumFound());
		result.setContents(solrContents);

		return result;
	}

	private SolrContent toSolrContent(SolrDocument doc, Map<String, Map<String, List<String>>> highlighting) {
		// create content
		SolrContent content = new SolrContent();
		// set id
		String id = (String) doc.getFieldValue("id");
		content.setId(id);
		// set clazz
		content.setClazz((String) doc.getFieldValue("class"));
		// set multi langiages text fields
		if (highlighting != null) {
			Map<String, List<String>> fields = highlighting.get(id);
			if (fields != null) {
				// set nameRu
				List<String> coll = fields.get("name_ru");
				if (coll != null && !coll.isEmpty()) {
					String nameRu = org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
					content.setNameRu(nameRu);
				}
				// set nameEn
				coll = fields.get("name_en");
				if (coll != null && !coll.isEmpty()) {
					String nameEn = org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
					content.setNameEn(nameEn);
				}
				// set textRu
				coll = fields.get("text_ru");
				if (coll != null && !coll.isEmpty()) {
					String textRu = org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
					content.setTextRu(textRu);
				}
				// set textEn
				coll = fields.get("text_en");
				if (coll != null && !coll.isEmpty()) {
					String textEn = org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
					content.setTextEn(textEn);
				}
			}
		}
		return content;
	}
}

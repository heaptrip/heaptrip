package com.heaptrip.repository.solr;

import com.heaptrip.domain.repository.solr.SolrContext;
import com.heaptrip.domain.repository.solr.SolrRegionRepository;
import com.heaptrip.domain.repository.solr.entity.SolrRegion;
import com.heaptrip.util.language.LanguageUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SolrRegionRepositoryImpl implements SolrRegionRepository {

    private static final Logger logger = LoggerFactory.getLogger(SolrRegionRepositoryImpl.class);

    @Autowired
    private SolrContext solrContext;

    @Override
    public List<SolrRegion> findByName(String name, Long skip, Long limit, Locale locale) throws SolrServerException {
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
        query.set("qf", "name_ru name_en");
        query.set("fl", fields);
        query.set("bf", "product(population)");

        if (logger.isDebugEnabled()) {
            logger.debug("find regions query: {}", query);
        }

        SolrServer core = solrContext.getCore(SolrCoreEnum.REGIONS);
        QueryResponse response = core.query(query);
        SolrDocumentList results = response.getResults();

        if (logger.isDebugEnabled()) {
            String msg = String.format("QTime: %d, NumFound: %d, size: %d", response.getQTime(), results.getNumFound(),
                    results.size());
            logger.debug(msg);
        }

        List<SolrRegion> solrRegions = new ArrayList<>(results.size());
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

package com.heaptrip.domain.service;

import java.util.List;
import java.util.Locale;

import org.apache.solr.client.solrj.SolrServerException;

import com.heaptrip.domain.entity.Region;

/**
 * 
 * Regin service
 * 
 */
public interface RegionService {

	/**
	 * Search regions by name
	 * 
	 * @param name
	 *            name or part of the name
	 * @param skip
	 *            start index
	 * @param limit
	 *            rows count
	 * @param locale
	 *            users locale
	 * @return list of regions
	 */
	public List<Region> getRegionsByName(String name, Long skip, Long limit, Locale locale) throws SolrServerException;

}

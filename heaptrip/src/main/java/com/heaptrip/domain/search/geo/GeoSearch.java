package com.heaptrip.domain.search.geo;

import org.apache.solr.client.solrj.SolrServerException;

public interface GeoSearch {
	public static final String SERVICE_NAME = "geoSearch";

	/**
	 * Search geo ids by part of the name
	 * 
	 * @param name
	 *            name or part of the name
	 * @param start
	 *            start index
	 * @param rows
	 *            max result count
	 * @return array of geo id
	 * 
	 * @throws SolrServerException
	 */
	public String[] searchIdsByName(String name, int start, int rows) throws SolrServerException;
}

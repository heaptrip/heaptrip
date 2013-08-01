package com.heaptrip.domain.service.content.criteria;

/**
 * 
 * Criteria searching for data in solr
 * 
 */
public class SolrContentCriteria extends ContentCriteria {

	// text to search
	private String query;

	// Specifies the length of the returned text. The default
	// value is "100".
	private Long textLength;

	// Specifies the text that should appear before and after a highlighted
	// term.
	private Highlighting highlighting;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Long getTextLength() {
		return textLength;
	}

	public void setTextLength(Long textLength) {
		this.textLength = textLength;
	}

	public Highlighting getHighlighting() {
		return highlighting;
	}

	public void setHighlighting(Highlighting highlighting) {
		this.highlighting = highlighting;
	}

}

package com.heaptrip.domain.service.content.criteria;

/**
 * 
 * Criteria for context searching data in Apache Solr
 * 
 */
public class ContextSearchCriteria extends ContentCriteria {

	// text to search
	private String query;

	// Specifies the length of the returned text. The default
	// value is "100".
	private Long textLength;

	// Specifies the text that should appear before and after a highlighted
	// term.
	private Highlight highlight;

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

	public Highlight getHighlight() {
		return highlight;
	}

	public void setHighlight(Highlight highlight) {
		this.highlight = highlight;
	}

}

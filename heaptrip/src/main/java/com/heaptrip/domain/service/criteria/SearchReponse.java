package com.heaptrip.domain.service.criteria;

import java.util.List;

/**
 * 
 * Common response object
 * 
 * @param <T>
 */
public class SearchReponse<T> {

	// The total number of object found
	private long numFound;

	// Limited list of objects
	private List<T> objects;

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public List<T> getObjects() {
		return objects;
	}

	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

}

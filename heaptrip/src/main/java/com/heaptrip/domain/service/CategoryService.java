package com.heaptrip.domain.service;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.Category;

/**
 * 
 * Category service
 * 
 */
public interface CategoryService {

	/**
	 * Get all categories
	 * 
	 * @param locale
	 * @return list of categories
	 */
	public List<Category> getCategories(Locale locale);

	/**
	 * Get a list of categories for the user
	 * 
	 * @param locale
	 * @return list of categories
	 */
	public List<Category> getCategoriesByUserId(String userId, Locale locale);

}

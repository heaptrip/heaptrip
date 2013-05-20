package com.heaptrip.domain.service;

import java.util.List;

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
	 * @return list of categories
	 */
	public List<Category> getCategories();

	/**
	 * Get a list of categories for the user
	 * 
	 * @return list of categories
	 */
	public List<Category> getCategoriesByUserId(String userId);

}

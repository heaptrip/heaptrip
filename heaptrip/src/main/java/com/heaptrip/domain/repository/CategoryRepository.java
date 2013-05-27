package com.heaptrip.domain.repository;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.Category;

public interface CategoryRepository {
	public static final String SERVICE_NAME = "categoryRepository";

	public void save(Category category);

	public void save(List<Category> categories);

	public Category findById(String Id);

	public List<Category> findAll(Locale locale);

	public void removeAll();
}

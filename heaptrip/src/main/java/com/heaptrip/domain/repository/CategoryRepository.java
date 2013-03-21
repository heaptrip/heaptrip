package com.heaptrip.domain.repository;

import java.util.List;

import com.heaptrip.domain.entity.CategoryEntity;

public interface CategoryRepository {
	public static final String SERVICE_NAME = "categoryRepository";

	public void save(CategoryEntity category);

	public void save(List<CategoryEntity> categories);

	public List<CategoryEntity> findAll();

	public void removeAll();
}

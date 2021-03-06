package com.heaptrip.domain.repository.category;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category> {

	public Category findById(String id, Locale locale);

	public List<Category> findAll(Locale locale);

	public Category getParentId(String categoryId);
}

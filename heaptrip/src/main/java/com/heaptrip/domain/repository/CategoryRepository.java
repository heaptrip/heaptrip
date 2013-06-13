package com.heaptrip.domain.repository;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.Category;

public interface CategoryRepository extends CrudRepository<Category> {

	public List<Category> findAll(Locale locale);

}

package com.heaptrip.service.category;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.service.category.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getCategories(Locale locale) {
		Assert.notNull(locale, "locale must not be null");
		return categoryRepository.findAll(locale);
	}

	@Override
	public List<Category> getCategoriesByUserId(String userId, Locale locale) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(locale, "locale must not be null");
		// TODO get categories by userId
		return categoryRepository.findAll(locale);
	}
}
package com.heaptrip.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getCategories(Locale locale) {
		Assert.notNull(locale, "locale");
		return categoryRepository.findAll(locale);
	}

	@Override
	public List<Category> getCategoriesByUserId(String userId, Locale locale) {
		Assert.notNull(userId, "userId");
		Assert.notNull(locale, "locale");
		// TODO get categories by userId
		return categoryRepository.findAll(locale);
	}
}

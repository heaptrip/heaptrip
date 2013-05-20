package com.heaptrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Category> getCategoriesByUserId(String userId) {
		// TODO get categories by userId
		return categoryRepository.findAll();
	}

}

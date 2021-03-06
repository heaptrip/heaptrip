package com.heaptrip.service.category;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public List<String> getParentsByCategoryId(String categoryId) {
        Assert.notNull(categoryId, "categoryId must not be null");
        List<String> ids = new ArrayList<>();
        Category category = categoryRepository.getParentId(categoryId);
        while (category != null && category.getParent() != null) {
            ids.add(category.getParent());
            category = categoryRepository.getParentId(category.getParent());
        }
        return ids;
    }

    @Override
    public Category getCategoryById(String categoryId, Locale locale) {
        Assert.notNull(categoryId, "categoryId must not be null");
        Assert.notNull(locale, "locale must not be null");
        return categoryRepository.findById(categoryId, locale);
    }

}

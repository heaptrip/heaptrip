package com.heaptrip.domain.service.category;

import com.heaptrip.domain.entity.category.Category;

import java.util.List;
import java.util.Locale;

/**
 * Category service
 */
public interface CategoryService {

    /**
     * Get category by id
     *
     * @param categoryId category ID
     * @param locale     requested locale
     * @return category
     */
    public Category getCategoryById(String categoryId, Locale locale);

    /**
     * Get all categories
     *
     * @param locale requested locale
     * @return list of categories
     */
    public List<Category> getCategories(Locale locale);

    /**
     * Get list of identifiers of parent categories by categoryId
     *
     * @param categoryId category ID
     * @return list of identifiers
     */
    public List<String> getParentsByCategoryId(String categoryId);

}

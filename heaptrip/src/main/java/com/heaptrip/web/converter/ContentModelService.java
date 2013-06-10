package com.heaptrip.web.converter;

import com.heaptrip.domain.entity.ContentCategory;
import com.heaptrip.web.model.content.CategoryModel;

public interface ContentModelService {

	CategoryModel convertCategoryToModel(ContentCategory category);

	CategoryModel[] convertCategoriesToModel(ContentCategory[] categories);

}

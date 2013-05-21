package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.service.CategoryService;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.web.model.filter.CategoryModel;

@Service
public class FilterModelServiceImpl implements FilterModelService {

	@Autowired
	private RequestScopeService scopeService;

	@Autowired
	private CategoryService categoryService;

	@Override
	public List<CategoryModel> getCategories() {

		List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
		List<Category> categories = categoryService.getCategories();

		for (Category category : categories) {

			CategoryModel categoryModel = new CategoryModel();
			categoryModel.setId(category.getId());
			categoryModel.setData(category.getName().getValue(scopeService.getCurrentLocale()));
			categoryModel.setChecked(false);

			if (categoryModels.size() > 0) {
				CategoryModel preCategoryModel = categoryModels.get(categoryModels.size() - 1);
				if (preCategoryModel.getId().equals(category.getParent())) {
					preCategoryModel.addChildren(categoryModel);
				} else {
					categoryModels.add(categoryModel);
				}
			} else {
				categoryModels.add(categoryModel);
			}
		}

		return categoryModels;

	}

}

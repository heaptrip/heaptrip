package com.heaptrip.web.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<Category> categories = categoryService.getCategories();
		Map<String, CategoryModel> map = new HashMap<String, CategoryModel>();
		map.put(null, new CategoryModel());
		for (Category category : categories) {
			CategoryModel categoryModel = new CategoryModel();
			categoryModel.setId(category.getId());
			categoryModel.setData(category.getName().getValue(scopeService.getCurrentLocale()));
			categoryModel.setChecked(false);
			map.put(categoryModel.getId(), categoryModel);
			map.get(category.getParent()).addChildren(map.get(categoryModel.getId()));
		}
		return map.get(null).getChildren();
	}

}

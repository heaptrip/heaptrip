package com.heaptrip.web.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.service.CategoryService;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.web.model.filter.CategoryTreeModel;

@Service
public class FilterModelServiceImpl implements FilterModelService {

	@Autowired
	private RequestScopeService scopeService;

	@Autowired
	private CategoryService categoryService;

	@Override
	public List<CategoryTreeModel> getCategories() {
		List<Category> categories = categoryService.getCategories(scopeService.getCurrentLocale());
		Map<String, CategoryTreeModel> map = new HashMap<String, CategoryTreeModel>();
		map.put(null, new CategoryTreeModel());
		for (Category category : categories) {
			CategoryTreeModel categoryModel = new CategoryTreeModel();
			categoryModel.setId(category.getId());
			categoryModel.setData(category.getName().getValue(scopeService.getCurrentLocale()));
			categoryModel.setChecked(false);
			map.put(categoryModel.getId(), categoryModel);
			map.get(category.getParent()).addChildren(map.get(categoryModel.getId()));
		}
		return map.get(null).getChildren();
	}

}

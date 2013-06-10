package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.ContentCategory;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.web.model.content.CategoryModel;

@Service
public class ContentModelServiceImpl implements ContentModelService {

	@Autowired
	private RequestScopeService scopeService;

	@Override
	public CategoryModel convertCategoryToModel(ContentCategory category) {
		CategoryModel result = null;
		if (category != null) {
			result = new CategoryModel();
			result.setData(category.getName().getValue(scopeService.getCurrentLocale()));
			result.setId(category.getId());
		}
		return result;
	}

	@Override
	public CategoryModel[] convertCategoriesToModel(ContentCategory[] categories) {
		CategoryModel[] result = null;
		if (categories != null) {
			List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
			for (ContentCategory category : categories) {
				CategoryModel model = new CategoryModel();
				model.setData(category.getName().getValue(scopeService.getCurrentLocale()));
				model.setId(category.getId());
				categoryModels.add(model);
			}
			result = categoryModels.toArray(new CategoryModel[categoryModels.size()]);
		}
		return result;
	}

}

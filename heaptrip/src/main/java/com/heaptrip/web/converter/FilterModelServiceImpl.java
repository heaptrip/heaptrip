package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.entity.Region;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.service.CategoryService;
import com.heaptrip.domain.service.RegionService;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.filter.CategoryTreeModel;

@Service
public class FilterModelServiceImpl implements FilterModelService {

	@Autowired
	private RequestScopeService scopeService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RegionService regionService;

	@Override
	public List<CategoryTreeModel> getCategories(String[] checkedCategoryIds) {
		List<Category> categories = categoryService.getCategories(scopeService.getCurrentLocale());
		Map<String, CategoryTreeModel> map = new HashMap<String, CategoryTreeModel>();
		map.put(null, new CategoryTreeModel());
		for (Category category : categories) {
			CategoryTreeModel categoryModel = new CategoryTreeModel();
			categoryModel.setId(category.getId());
			categoryModel.setData(category.getName().getValue(scopeService.getCurrentLocale()));
			map.put(categoryModel.getId(), categoryModel);
			map.get(category.getParent()).addChildren(map.get(categoryModel.getId()));
			if (isCategoriesContainsId(checkedCategoryIds, category.getId()))
				categoryModel.setChecked(true);
			else
				categoryModel.setChecked(false);
		}
		return map.get(null).getChildren();
	}

	@Override
	public List<RegionModel> searchRegionsByText(String text) {

		List<RegionModel> regionModels = new ArrayList<RegionModel>();

		try {
			List<Region> regions = regionService.getRegionsByName(text, 0L, 20L, scopeService.getCurrentLocale());
			if (regions != null) {
				for (Region region : regions) {
					RegionModel regionModel = new RegionModel();
					regionModel.setId(region.getId());
					regionModel.setData(region.getName().getValue(scopeService.getCurrentLocale()));
					regionModel.setPath(region.getPath().getValue(scopeService.getCurrentLocale()));
					regionModels.add(regionModel);
				}
			}

		} catch (SolrServerException e) {
			throw scopeService.getErrorServise().createSystemException(ErrorEnum.ERR_SYSTEM_SOLR);
		}

		return regionModels;
	}

	@Override
	public String[] getCategoriesForCurrentUser(String[] checkedCategoryIds) {
		// TODO: voronenko вычислить критерии текущего пользователя если
		// String[] categoryIds is null;
		return checkedCategoryIds;
	}

	private boolean isCategoriesContainsId(String[] checkedCategoryIds, String categoryId) {
		boolean result = false;
		if (checkedCategoryIds != null) {
			for (String checkedId : checkedCategoryIds) {
				if (checkedId.equals(categoryId))
					return true;
			}
		}
		return result;
	}

}

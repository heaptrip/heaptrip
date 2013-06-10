package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.ContentCategory;
import com.heaptrip.domain.entity.ContentOwner;
import com.heaptrip.domain.entity.ContentRegion;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentOwnerModel;
import com.heaptrip.web.model.content.RegionModel;

@Service
public class ContentModelServiceImpl implements ContentModelService {

	@Autowired
	private RequestScopeService scopeService;

	@Override
	public CategoryModel convertCategoryToModel(ContentCategory category) {
		CategoryModel result = null;
		if (category != null) {
			result = new CategoryModel();
			result.setId(category.getId());
			result.setData(category.getName().getValue(scopeService.getCurrentLocale()));
		}
		return result;
	}

	@Override
	public CategoryModel[] convertCategoriesToModel(ContentCategory[] categories) {
		CategoryModel[] result = null;
		if (categories != null) {
			List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
			for (ContentCategory category : categories) {
				categoryModels.add(convertCategoryToModel(category));
			}
			result = categoryModels.toArray(new CategoryModel[categoryModels.size()]);
		}
		return result;
	}

	@Override
	public RegionModel convertRegionToModel(ContentRegion region) {
		RegionModel result = null;
		if (region != null) {
			result = new RegionModel();
			result.setId(region.getId());
			if (region.getName() != null)
				result.setData(region.getName().getValue(scopeService.getCurrentLocale()));
			else
				result.setData("fix reg " + region.getId());
		}
		return result;
	}

	@Override
	public RegionModel[] convertRegionsToModel(ContentRegion[] regions) {
		RegionModel[] result = null;
		if (regions != null) {
			List<RegionModel> regionModels = new ArrayList<RegionModel>();
			for (ContentRegion region : regions) {
				regionModels.add(convertRegionToModel(region));
			}
			result = regionModels.toArray(new RegionModel[regionModels.size()]);
		}
		return result;
	}

	@Override
	public ContentOwnerModel convertContentOwnerToModel(ContentOwner owner) {
		ContentOwnerModel result = null;
		if (owner != null) {
			result = new ContentOwnerModel();
			result.setId(owner.getId());
			result.setName(owner.getName());
			result.setRating(owner.getRating());
		}
		return result;
	}

}

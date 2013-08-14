package com.heaptrip.web.converter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentRegion;
import com.heaptrip.service.adm.RequestScopeServiceImpl;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.ContentOwnerModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.RegionModel;

@Service
public class ContentModelServiceImpl extends RequestScopeServiceImpl implements ContentModelService {

	@Override
	public CategoryModel convertCategoryToModel(ContentCategory category) {
		CategoryModel result = null;
		if (category != null) {
			result = new CategoryModel();
			result.setId(category.getId());
			result.setData(category.getName().getValue(getCurrentLocale()));
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
				result.setData(region.getName().getValue(getCurrentLocale()));

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

	@Override
	public DateModel convertDate(Date date) {
		DateModel result = new DateModel();
		if (date != null) {
			result.setValue(date);
			result.setText(DateFormat.getDateInstance(DateFormat.SHORT, getCurrentLocale()).format(date));
		}
		return result;
	}

	protected void setContentToContentModel(ContentModel contentModel, Content contetnt) {

		if (contetnt != null) {
			contentModel.setId(contetnt.getId());
			contentModel.setCreated(convertDate(contetnt.getCreated()));
			contentModel.setImage(contetnt.getImage().getId());
			contentModel.setViews(contetnt.getViews());
			if (contetnt.getName() != null)
				contentModel.setName(contetnt.getName().getValue(getCurrentLocale()));
			contentModel.setOwner(convertContentOwnerToModel(contetnt.getOwner()));
			contentModel.setCategories(convertCategoriesToModel(contetnt.getCategories()));
			contentModel.setRegions(convertRegionsToModel(contetnt.getRegions()));
			contentModel.setLangs(contetnt.getLangs());
		}

	}
}

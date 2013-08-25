package com.heaptrip.web.converter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.service.system.RequestScopeServiceImpl;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.ContentOwnerModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.PriceModel;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.content.StatusModel;

@Service
public class ContentModelServiceImpl extends RequestScopeServiceImpl implements
		ContentModelService {

	@Override
	public CategoryModel convertCategoryToModel(SimpleCategory category) {
		CategoryModel result = null;
		if (category != null) {
			result = new CategoryModel();
			result.setId(category.getId());
			result.setData(category.getName().getValue(getCurrentLocale()));
		}
		return result;
	}

	@Override
	public CategoryModel[] convertCategoriesToModel(SimpleCategory[] categories) {
		CategoryModel[] result = null;
		if (categories != null) {
			List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
			for (SimpleCategory category : categories) {
				categoryModels.add(convertCategoryToModel(category));
			}
			result = categoryModels.toArray(new CategoryModel[categoryModels
					.size()]);
		}
		return result;
	}

	@Override
	public RegionModel convertRegionToModel(SimpleRegion region) {
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
	public RegionModel[] convertRegionsToModel(SimpleRegion[] regions) {
		RegionModel[] result = null;
		if (regions != null) {
			List<RegionModel> regionModels = new ArrayList<RegionModel>();
			for (SimpleRegion region : regions) {
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
			result.setText(DateFormat.getDateInstance(DateFormat.SHORT,
					getCurrentLocale()).format(date));
		}
		return result;
	}

	@Override
	public PriceModel convertPrice(Price price) {
		PriceModel priceModel = new PriceModel();
		if (price != null) {
			priceModel.setValue(price.getValue());
			priceModel.setCurrency(price.getCurrency().name());
		}
		return priceModel;
	}

	protected void setContentToContentModel(ContentModel contentModel,
			Content contetnt) {

		if (contetnt != null) {
			contentModel.setId(contetnt.getId());
			contentModel.setCreated(convertDate(contetnt.getCreated()));
			contentModel.setImage(contetnt.getImage().getId());
			if (contetnt.getViews() == null) {
				contentModel.setViews(0L);
			} else {
				contentModel.setViews(contetnt.getViews().getCount());
			}
			StatusModel status = new StatusModel();
			status.setValue(contetnt.getStatus().getValue().name());
			status.setText(contetnt.getStatus().getText());
			contentModel.setStatus(status);
			if (contetnt.getName() != null)
				contentModel.setName(contetnt.getName().getValue(
						getCurrentLocale()));
			contentModel.setOwner(convertContentOwnerToModel(contetnt
					.getOwner()));
			contentModel.setCategories(convertCategoriesToModel(contetnt
					.getCategories()));
			contentModel
					.setRegions(convertRegionsToModel(contetnt.getRegions()));
			contentModel.setLangs(contetnt.getLangs());
		}

	}
}

package com.heaptrip.web.converter;

import java.util.Date;
import java.util.Locale;

import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.rating.TotalRating;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentOwnerModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.PriceModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.content.RegionModel;

public interface ContentModelService {

	CategoryModel convertCategoryToModel(SimpleCategory category);

	SimpleCategory convertCategoryModelToCategory(CategoryModel categoryModel, Locale locale);

	SimpleCategory[] convertCategoriesModelsToCategories(CategoryModel[] categoryModels, Locale locale);

	String[] convertCategoriesModelsToIdsArray(CategoryModel[] categoryModels, Locale locale);

	CategoryModel[] convertCategoriesToModel(SimpleCategory[] categories);

	RegionModel convertRegionToModel(SimpleRegion region);

	RegionModel[] convertRegionsToModel(SimpleRegion[] regions);

	SimpleRegion convertRegionModelToRegion(RegionModel regionModel, Locale locale);

	SimpleRegion[] convertRegionModelsToRegions(RegionModel[] regionModels, Locale locale);

	String[] convertRegionModelsToIdsArray(RegionModel[] regionModels, Locale locale);

	ContentOwnerModel convertContentOwnerToModel(ContentOwner owner);

	DateModel convertDate(Date date);

	PriceModel convertPrice(Price price);

	Price convertPriceModel(PriceModel priceModel);

	ContentOwner getContentOwner();

	String getMultiLangTextValue(MultiLangText text, Locale locale, boolean isOnlyThisLocale);
	
	RatingModel convertRatingToModel (TotalRating rating);

	

}

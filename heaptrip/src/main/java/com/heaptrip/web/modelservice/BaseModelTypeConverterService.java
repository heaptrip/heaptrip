package com.heaptrip.web.modelservice;

import java.util.Date;
import java.util.Locale;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.TotalRating;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.web.model.content.*;

public interface BaseModelTypeConverterService {

	DateModel convertDate(Date date);

	ImageModel convertImage(Image image);

	PriceModel convertPrice(Price price);

	Price convertPriceModel(PriceModel priceModel);

	String getMultiLangTextValue(MultiLangText text, Locale locale, boolean isOnlyThisLocale);

	RatingStarsModel convertRatingToRatingModel(ContentEnum contentType, String contentId, TotalRating rating);

	ContentRatingModel convertRatingToContentRatingModel(ContentEnum contentType, String contentId, TotalRating rating);

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


}
package com.heaptrip.web.modelservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CurrencyEnum;
import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.rating.TotalRating;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.rating.RatingService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.util.language.LanguageUtils;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.PriceModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.content.StatusModel;
import com.heaptrip.web.model.user.UserModel;

@Service
public class ContentModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ContentModelService {

	@Autowired
	CategoryService categoryService;

	@Autowired
	RegionService regionService;

	@Autowired
	RatingService ratingService;

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
			result = categoryModels.toArray(new CategoryModel[categoryModels.size()]);
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
	public UserModel convertContentOwnerToModel(ContentOwner owner) {
		UserModel result = null;
		if (owner != null) {
			result = new UserModel();
			result.setId(owner.getId());
			result.setName(owner.getName());
			result.setRating(owner.getRating());
		}
		return result;
	}

	protected void setContentToContentModel(ContentModel contentModel, Content contetnt, Locale locale,
			boolean isOnlyThisLocale) {

		if (contetnt != null) {
			contentModel.setId(contetnt.getId());
			contentModel.setCreated(convertDate(contetnt.getCreated()));

			if (contetnt.getImage() != null)
				contentModel.setImage(convertImage(contetnt.getImage()));
			if (contetnt.getViews() == null) {
				contentModel.setViews(0L);
			} else {
				contentModel.setViews(contetnt.getViews().getCount());
			}

			contentModel.setRating(convertRatingToModel(contetnt.getRating()));

			StatusModel status = new StatusModel();
			status.setValue(contetnt.getStatus().getValue().name());
			status.setText(contetnt.getStatus().getText());
			contentModel.setStatus(status);
			if (contetnt.getName() != null)
				contentModel.setName(getMultiLangTextValue(contetnt.getName(), locale, isOnlyThisLocale));
			contentModel.setOwner(convertContentOwnerToModel(contetnt.getOwner()));
			contentModel.setCategories(convertCategoriesToModel(contetnt.getCategories()));
			contentModel.setRegions(convertRegionsToModel(contetnt.getRegions()));
			contentModel.setLangs(contetnt.getLangs());
		}

	}

	@Override
	public ContentOwner getContentOwner() {
		ContentOwner contentOwner = null;
		User user = getCurrentUser();
		if (user != null) {
			contentOwner = new ContentOwner();
			contentOwner.setId(user.getId());
			contentOwner.setName(user.getName());
			// TODO: getRating() from user
			contentOwner.setRating(0D);
			contentOwner.setType(user.getTypeAccount());
		}
		return contentOwner;
	}

	@Override
	public String getMultiLangTextValue(MultiLangText text, Locale locale, boolean isOnlyThisLocale) {
		String result = null;
		if (isOnlyThisLocale) {
			text.setMainLanguage(LanguageUtils.getLanguageByLocale(locale));
			result = text.getValue(locale);
		} else {
			result = text.getValue((locale != null ? locale : getCurrentLocale()));
		}
		return result;
	}

	@Override
	public SimpleCategory convertCategoryModelToCategory(CategoryModel categoryModel, Locale locale) {
		return categoryService.getCategoryById(categoryModel.getId(), locale);
	}

	@Override
	public SimpleCategory[] convertCategoriesModelsToCategories(CategoryModel[] categoryModels, Locale locale) {
		SimpleCategory[] result = null;
		if (categoryModels != null) {
			List<SimpleCategory> categories = new ArrayList<SimpleCategory>();
			for (CategoryModel categoryModel : categoryModels) {
				categories.add(convertCategoryModelToCategory(categoryModel, locale));
			}
			result = categories.toArray(new SimpleCategory[categories.size()]);
		}
		return result;
	}

	@Override
	public SimpleRegion convertRegionModelToRegion(RegionModel regionModel, Locale locale) {
		return regionService.getRegionById(regionModel.getId(), locale);
	}

	@Override
	public SimpleRegion[] convertRegionModelsToRegions(RegionModel[] regionModels, Locale locale) {
		SimpleRegion[] result = null;
		if (regionModels != null) {
			List<SimpleRegion> regions = new ArrayList<SimpleRegion>();
			for (RegionModel regionModel : regionModels) {
				regions.add(convertRegionModelToRegion(regionModel, locale));
			}
			result = regions.toArray(new SimpleRegion[regions.size()]);
		}
		return result;
	}

	@Override
	public String[] convertCategoriesModelsToIdsArray(CategoryModel[] categoryModels, Locale locale) {
		SimpleCategory[] categories = convertCategoriesModelsToCategories(categoryModels, locale);
		List<String> ids = new ArrayList<String>();
		for (SimpleCategory category : categories) {
			ids.add(category.getId());
		}
		return ids.toArray(new String[ids.size()]);
	}

	@Override
	public String[] convertRegionModelsToIdsArray(RegionModel[] regionModels, Locale locale) {
		SimpleRegion[] regions = convertRegionModelsToRegions(regionModels, locale);
		List<String> ids = new ArrayList<String>();
		for (SimpleRegion region : regions) {
			ids.add(region.getId());
		}
		return ids.toArray(new String[ids.size()]);
	}

	@Override
	public RatingModel convertRatingToModel(TotalRating rating) {
		RatingModel result = new RatingModel();
		result.setValue(0D);
		result.setCount(0);
		result.setStars(0D);
		if (rating != null) {
			result.setValue(rating.getValue());
			// TODO: переделать, когда появятся звезды 0,5
			result.setStars(new Double(Math.round(ratingService.ratingToStars(rating.getValue()))));
			result.setCount(rating.getCount());
		}
		return result;
	}

	@Override
	public PriceModel convertPrice(Price price) {
		PriceModel priceModel = new PriceModel();
		if (price != null) {
			priceModel.setValue(price.getValue());
			if (price.getCurrency() != null)
				priceModel.setCurrency(price.getCurrency().name());
		}
		return priceModel;
	}

	@Override
	public Price convertPriceModel(PriceModel priceModel) {
		Price price = null;
		if (priceModel != null) {
			price = new Price();
			price.setValue(priceModel.getValue());
			CurrencyEnum currency = null;
			for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
				if (currencyEnum.name().equals(priceModel.getValue()))
					currency = currencyEnum;
			}
			price.setCurrency(currency);
		}
		return price;
	}

}
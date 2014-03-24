package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.CurrencyEnum;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.SimpleImage;
import com.heaptrip.domain.entity.rating.TotalRating;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.rating.RatingService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.service.system.RequestScopeServiceImpl;
import com.heaptrip.util.language.LanguageUtils;
import com.heaptrip.web.model.content.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class BaseModelTypeConverterServiceImpl extends RequestScopeServiceImpl implements BaseModelTypeConverterService {


    @Autowired
    CategoryService categoryService;

    @Autowired
    RegionService regionService;

    @Autowired
    protected RatingService ratingService;


    @Override
    public DateModel convertDate(Date date) {
        DateModel result = new DateModel();
        if (date != null) {
            result.setValue(date);
            result.setText(DateFormat.getDateInstance(DateFormat.SHORT, getCurrentLocale()).format(date));
        }
        return result;
    }

    @Override
    public ImageModel convertImage(SimpleImage image) {
        ImageModel result = null;
        if (image != null) {
            result = new ImageModel();
            result.setId(image.getId());
            if (image.getRefs() != null) {
                result.setSmallId(image.getRefs().getSmall());
                result.setMediumId(image.getRefs().getMedium());
                result.setFullId(image.getRefs().getFull());
            }
            result.setName(image.getName());
        }
        return result;
    }

    @Override
    public ImageModel convertImage(Image image) {
        ImageModel result = null;
        if (image != null) {
            result = convertImage((SimpleImage) image);
            result.setText(image.getText());
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
            //

            // TODO : voronenko разобраться с павлом переписать его футкцию по добавлию рассписания
            // а пока

            if (priceModel.getCurrency() == null)
                price.setCurrency(CurrencyEnum.RUB);
            else {
                price.setCurrency(CurrencyEnum.valueOf(priceModel.getCurrency()));
            }

        }
        return price;
    }

    @Override
    public String getMultiLangTextValue(MultiLangText text, Locale locale, boolean isOnlyThisLocale) {
        String result;
        if (isOnlyThisLocale) {
            text.setMainLanguage(LanguageUtils.getLanguageByLocale(locale));
            result = text.getValue(locale);
        } else {
            result = text.getValue((locale != null ? locale : getCurrentLocale()));
        }
        return result;
    }

    @Override
    public RatingStarsModel convertRatingToRatingModel(ContentEnum contentType, String contentId, TotalRating rating) {

        RatingStarsModel result = new RatingStarsModel();

        result.setValue(0D);
        result.setCount(0);
        result.setStars("0");
        result.setLocked(false);

        User user = getCurrentUser();
        if (user != null)
            result.setLocked(!ratingService.canSetRating(contentType, contentId, user.getId()));

        if (rating != null) {
            result.setValue(rating.getValue());
            result.setStars(ratingValueToStars(rating.getValue()));
            result.setCount(rating.getCount());
        }

        return result;
    }

    @Override
    public ContentRatingModel convertRatingToContentRatingModel(ContentEnum contentType, String contentId,
                                                                TotalRating rating) {
        ContentRatingModel contentRatingModel = new ContentRatingModel();
        RatingStarsModel ratingModel = convertRatingToRatingModel(contentType, contentId, rating);
        contentRatingModel.setContentId(contentId);
        contentRatingModel.setContentType(contentType.name());
        contentRatingModel.setCount(ratingModel.getCount());
        contentRatingModel.setLocked(ratingModel.getLocked());
        contentRatingModel.setStars(ratingModel.getStars());
        contentRatingModel.setValue(ratingModel.getValue());

        return contentRatingModel;
    }

    // TODO : voronenko : переделать, когда появятся звезды 0,5
    // на GUI должны лететь : 0, 0_5, 1, 1_5, ..., 5
    protected String ratingValueToStars(Double value) {
        Double stars = new Double(Math.round(ratingService.ratingToStars(value)));
        String starsString = stars.toString();
        int index = starsString.indexOf(".0");
        if (index > 0) {
            starsString = starsString.substring(0, index);
        }
        return starsString;
    }


    @Override
    public SimpleCategory convertCategoryModelToCategory(CategoryModel categoryModel, Locale locale) {
        return categoryService.getCategoryById(categoryModel.getId(), locale);
    }

    @Override
    public SimpleCategory[] convertCategoriesModelsToCategories(CategoryModel[] categoryModels, Locale locale) {
        SimpleCategory[] result = null;
        if (categoryModels != null) {
            List<SimpleCategory> categories = new ArrayList<>();
            for (CategoryModel categoryModel : categoryModels) {
                categories.add(convertCategoryModelToCategory(categoryModel, locale));
            }
            result = categories.toArray(new SimpleCategory[categories.size()]);
        }
        return result;
    }

    @Override
    public SimpleRegion convertRegionModelToRegion(RegionModel regionModel, Locale locale) {
        SimpleRegion result = null;
        if (regionModel != null)
            result = regionService.getRegionById(regionModel.getId(), locale);
        return result;
    }

    @Override
    public SimpleRegion[] convertRegionModelsToRegions(RegionModel[] regionModels, Locale locale) {
        SimpleRegion[] result = null;
        if (regionModels != null) {
            List<SimpleRegion> regions = new ArrayList<>();
            for (RegionModel regionModel : regionModels) {
                regions.add(convertRegionModelToRegion(regionModel, locale));
            }
            result = regions.toArray(new SimpleRegion[regions.size()]);
        }
        return result;
    }

    @Override
    public String[] convertCategoriesModelsToIdsArray(CategoryModel[] categoryModels, Locale locale) {
        if (categoryModels == null) return null;
        SimpleCategory[] categories = convertCategoriesModelsToCategories(categoryModels, locale);
        List<String> ids = new ArrayList<>();
        for (SimpleCategory category : categories) {
            ids.add(category.getId());
        }
        return ids.toArray(new String[ids.size()]);
    }

    @Override
    public String[] convertRegionModelsToIdsArray(RegionModel[] regionModels, Locale locale) {
        if (regionModels == null) return null;
        SimpleRegion[] regions = convertRegionModelsToRegions(regionModels, locale);
        List<String> ids = new ArrayList<>();
        for (SimpleRegion region : regions) {
            ids.add(region.getId());
        }
        return ids.toArray(new String[ids.size()]);
    }

    @Override
    public RegionModel[] convertRegionsToModel(SimpleRegion[] regions) {
        RegionModel[] result = null;
        if (regions != null) {
            List<RegionModel> regionModels = new ArrayList<>();
            for (SimpleRegion region : regions) {
                regionModels.add(convertRegionToModel(region));
            }
            result = regionModels.toArray(new RegionModel[regionModels.size()]);
        }
        return result;
    }

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
            List<CategoryModel> categoryModels = new ArrayList<>();
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


}

package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.CurrencyEnum;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.TotalRating;
import com.heaptrip.domain.service.rating.RatingService;
import com.heaptrip.service.system.RequestScopeServiceImpl;
import com.heaptrip.util.language.LanguageUtils;
import com.heaptrip.web.model.content.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class BaseModelTypeConverterServiceImpl extends RequestScopeServiceImpl implements BaseModelTypeConverterService {


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
    public ImageModel convertImage(Image image) {
        ImageModel result = null;
        if (image != null) {
            result = new ImageModel();
            result.setId(image.getId());
            result.setName(image.getName());
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

}

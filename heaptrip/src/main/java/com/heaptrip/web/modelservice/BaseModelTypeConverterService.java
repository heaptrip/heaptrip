package com.heaptrip.web.modelservice;

import java.util.Date;
import java.util.Locale;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.TotalRating;
import com.heaptrip.web.model.content.ContentRatingModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.ImageModel;
import com.heaptrip.web.model.content.PriceModel;
import com.heaptrip.web.model.content.RatingStarsModel;

public interface BaseModelTypeConverterService {

	DateModel convertDate(Date date);

	ImageModel convertImage(Image image);

	PriceModel convertPrice(Price price);

	Price convertPriceModel(PriceModel priceModel);

	String getMultiLangTextValue(MultiLangText text, Locale locale, boolean isOnlyThisLocale);

	RatingStarsModel convertRatingToRatingModel(ContentEnum contentType, String contentId, TotalRating rating);

	ContentRatingModel convertRatingToContentRatingModel(ContentEnum contentType, String contentId, TotalRating rating);

}
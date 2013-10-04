package com.heaptrip.web.modelservice;

import com.heaptrip.web.model.content.ContentRatingModel;

public interface CountersService {

	void incViews(String contentId);

	ContentRatingModel addContentRating(ContentRatingModel ratingModel);

	
}

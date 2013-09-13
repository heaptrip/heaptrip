package com.heaptrip.service.rating;

import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.service.rating.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Override
	public boolean canSetRating(ContentEnum contentType, String contentId, String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Future<ContentRating> addContentRating(String contentId, String userId, double rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<AccountRating> addAccountRating(String accountId, String userId, double rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountRating getDefaultAccountRating() {
		AccountRating accountRating = new AccountRating();
		accountRating.setK(1);
		accountRating.setCount(0);
		accountRating.setValue(0.25);
		return accountRating;
	}

	@Override
	public ContentRating getDefaultContentRating() {
		ContentRating contentRating = new ContentRating();
		contentRating.setCount(0);
		contentRating.setValue(0.25);
		return contentRating;
	}

	@Override
	public double ratingToStars(double rating) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double starsToRating(double stars) {
		// TODO Auto-generated method stub
		return 0;
	}

}

package com.heaptrip.service.rating;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.domain.service.rating.RatingService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.TripUserService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ErrorService errorService;

	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private TripUserService tripUserService;

	@Autowired
	private TripService tripService;

	@Override
	public boolean canSetRating(ContentEnum contentType, String contentId, String userId) {
		if (contentType.equals(ContentEnum.QA)) {
			// questions can not be rated
			return false;
		}

		Rating rating = ratingRepository.findByTargetIdAndUserId(contentId, userId);
		if (rating != null) {
			// user already rated this content
			return false;
		}

		if (contentType.equals(ContentEnum.POST) || contentType.equals(ContentEnum.EVENT)) {
			// posts and events can be rated in six months
			Date created = contentRepository.getDateCreated(contentId);
			if (created != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(created);
				calendar.add(Calendar.MONTH, 6);
				if (calendar.before(new Date())) {
					return false;
				}
			}
		}

		if (contentType.equals(ContentEnum.TRIP)) {
			// trip can rated only members
			if (!tripUserService.isTableUser(contentId, userId)) {
				return false;
			}
			// trip can be rated in six months with the launch of the last
			// schedule
			TableItem item = tripService.getLatestTableItem(contentId);
			if (item != null && item.getEnd() != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(item.getEnd());
				calendar.add(Calendar.MONTH, 6);
				if (calendar.getTime().before(new Date())) {
					return false;
				}
			}
		}

		return true;
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

package com.heaptrip.service.rating;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.entity.rating.Rating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.rating.RatingException;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.rating.RatingRepository;
import com.heaptrip.domain.repository.rating.RatingSum;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.rating.RatingService;
import com.heaptrip.domain.service.system.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class RatingServiceImpl implements RatingService {

    private static final int MAX_ACOUNT_RATING_COUNT = 1000;

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

    @Autowired
    @Qualifier(ContentService.SERVICE_NAME)
    private ContentService contentService;

    @Autowired
    private UserService accountService;

    @Override
    public boolean canSetRating(ContentEnum contentType, String contentId, String userId) {
        Assert.notNull(contentType, "contentType must not be null");
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(userId, "userId must not be null");

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
                if (calendar.getTime().before(new Date())) {
                    return false;
                }
            }
        }

        if (contentType.equals(ContentEnum.TRIP)) {
            // trip can rated only accepted members
            if (!tripUserService.isTripAcceptedMember(contentId, userId)) {
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

    @Async
    @Override
    public Future<ContentRating> addContentRating(String contentId, String userId, double value) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(userId, "userId must not be null");

        ContentRating contentRating = contentService.getContentRating(contentId);
        if (contentRating == null) {
            contentRating = getDefaultContentRating();
            contentService.setContentRating(contentId, contentRating);
        }

        ContentEnum contentType = contentRepository.getContentTypeByContentId(contentId);

        if (canSetRating(contentType, contentId, userId)) {
            Rating rating = new Rating();
            rating.setCreated(new Date());
            rating.setTargetId(contentId);
            rating.setUserId(userId);
            rating.setValue(value);
            ratingRepository.save(rating);

            RatingSum ratingSum = ratingRepository.getRatingSumByTargetId(contentId);
            calcContentRating(ratingSum, contentRating);

            String ownerId = contentRepository.getOwnerId(contentId);
            if (ownerId != null) {
                try {
                    addAccountRating(ownerId, userId, value).get();
                } catch (InterruptedException | ExecutionException e) {
                    throw errorService.createException(RatingException.class, e, ErrorEnum.ERROR_RATING_CALCULATION);
                }
            }

            contentService.updateContentRatingValue(contentId, contentRating.getValue());
        }

        return new AsyncResult<>(contentRating);
    }

    @Async
    @Override
    public Future<AccountRating> addAccountRating(String accountId, String userId, double value) {
        Assert.notNull(accountId, "accountId must not be null");
        Assert.notNull(userId, "userId must not be null");

        AccountRating accountRating = accountService.getAccountRating(accountId);
        if (accountRating == null) {
            accountRating = getDefaultAccountRating();
            accountService.setAccountRating(accountId, accountRating);
        }

        Rating rating = null;
        if (accountRating.getCount() >= MAX_ACOUNT_RATING_COUNT) {
            rating = ratingRepository.findOldestByTargetId(accountId);
        }

        if (rating == null) {
            rating = new Rating();
        }

        rating.setCreated(new Date());
        rating.setTargetId(accountId);
        rating.setUserId(userId);
        rating.setValue(value);

        ratingRepository.save(rating);

        RatingSum ratingSum = ratingRepository.getRatingSumByTargetIdAndCreatedLessThenHalfYear(accountId);
        if (ratingSum == null) {
            ratingSum = new RatingSum();
            ratingSum.setCount(getDefaultAccountRating().getCount());
            ratingSum.setSum(getDefaultAccountRating().getValue());
        }
        calcAccountRating(ratingSum, accountRating);

        accountService.updateAccountRatingValue(accountId, accountRating.getValue());

        return new AsyncResult<>(accountRating);
    }

    @Override
    public AccountRating getDefaultAccountRating() {
        return AccountRating.getDefaultValue();
    }

    @Override
    public ContentRating getDefaultContentRating() {
        return ContentRating.getDefaultValue();
    }

    @Override
    public double ratingToStars(double rating) {
        return 4 * rating + 1;
    }

    @Override
    public double starsToRating(double stars) {
        return (stars - 1) / 4;
    }

    private void calcContentRating(RatingSum ratingSum, ContentRating contentRating) {
        int count = ratingSum.getCount();

        double Rq = 0.25;

        double Ru = ratingSum.getSum() / count;

        double denominator = Math.pow(count + 1, (count * 0.02) / (Ru + 0.1));

        double R = Ru - (Ru - Rq) / denominator;

        contentRating.setCount(contentRating.getCount() + 1);
        contentRating.setValue(R);
    }

    private void calcAccountRating(RatingSum ratingSum, AccountRating accountRating) {
        int count = ratingSum.getCount();

        double Rq = 0.25;

        double Ru = ratingSum.getSum() / count;

        double denominator = Math.pow(count + 1, (count * 0.02) / (Ru + 1.75));

        double R = Ru - (Ru - Rq) / denominator;
        R *= accountRating.getK();

        accountRating.setCount(accountRating.getCount() + 1);
        accountRating.setValue(R);
    }

}

package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.web.model.content.ContentRatingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.Future;

@Service
public class CountersServiceImpl extends BaseModelTypeConverterServiceImpl implements CountersService {

    @Autowired
    @Qualifier(ContentService.SERVICE_NAME)
    private ContentService contentService;

    @Override
    public void incViews(String contentId) {
        contentService.incViews(contentId, getCurrentRequestIP());
    }

    @Override
    public ContentRatingModel addContentRating(ContentRatingModel ratingModel) {
        ContentRatingModel resRatingModel = null;
        User user = getCurrentUser();
        Assert.notNull(user, "user must not be null");
        Assert.notNull(ratingModel, "ratingModel must not be null");
        Assert.notNull(ratingModel.getStars(), "stars must not be null");
        Assert.notNull(ratingModel.getContentType(), "rating model content type must not be null");
        ContentEnum contentType = ContentEnum.valueOf(ratingModel.getContentType());
        if (ratingService.canSetRating(contentType, ratingModel.getContentId(), user.getId())) {
            Future<ContentRating> res = ratingService.addContentRating(ratingModel.getContentId(), user.getId(),
                    new Double(ratingModel.getStars()));
            try {
                resRatingModel = convertRatingToContentRatingModel(contentType, ratingModel.getContentId(), res.get(), false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resRatingModel;
    }

}

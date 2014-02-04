package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.content.*;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.feed.ContentFeedService;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.content.StatusModel;
import com.heaptrip.web.model.profile.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service(ContentModelService.SERVICE_NAME)
public class ContentModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ContentModelService {

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected ContentFeedService contentFeedService;

    @Autowired
    private ContentRepository contentRepository;


    @Override
    public AccountModel convertContentOwnerToModel(ContentOwner owner) {
        AccountModel result = null;
        if (owner != null) {
            result = new AccountModel();
            result.setId(owner.getId());
            result.setName(owner.getName());
            result.setRating(new RatingModel(owner.getRating()));
        }
        return result;
    }


    protected ContentModel convertContentToContentModel(ContentEnum contentType, Content content, boolean isOnlyThisLocale) {
        ContentModel contentModel = new ContentModel();
        setContentToContentModel(contentType, contentModel, content, getCurrentLocale(), isOnlyThisLocale);
        return contentModel;
    }

    protected void setContentToContentModel(ContentEnum contentType, ContentModel contentModel, Content content,
                                            Locale locale, boolean isOnlyThisLocale) {

        if (content != null) {
            contentModel.setId(content.getId());
            contentModel.setCreated(convertDate(content.getCreated()));

            if (content.getName() != null)
                contentModel.setName(getMultiLangTextValue(content.getName(), locale, isOnlyThisLocale));

            if (content.getSummary() != null)
                contentModel.setSummary(getMultiLangTextValue(content.getSummary(), locale, isOnlyThisLocale));

            if (content.getDescription() != null)
                contentModel.setDescription(getMultiLangTextValue(content.getDescription(), locale, isOnlyThisLocale));

            contentModel.setOwner(convertContentOwnerToModel(content.getOwner()));
            contentModel.setCategories(convertCategoriesToModel(content.getCategories()));
            contentModel.setRegions(convertRegionsToModel(content.getRegions()));
            contentModel.setLangs(content.getLangs());

            if (content.getStatus() != null)
                contentModel.setStatus(convertContentStatusToModel(content.getStatus()));

            contentModel.setRating(convertRatingToRatingModel(contentType, content.getId(), content.getRating()));

            if (content.getImage() != null)
                contentModel.setImage(convertImage(content.getImage()));

            if (content.getViews() == null) {
                contentModel.setViews(0L);
            } else {
                contentModel.setViews(content.getViews().getCount());
            }

            contentModel.setComments(content.getComments());
        }
    }

    protected StatusModel convertContentStatusToModel(ContentStatus contentStatus) {
        StatusModel statusModel = new StatusModel();
        statusModel.setValue(contentStatus.getValue().name());
        statusModel.setText(contentStatus.getText());
        return statusModel;
    }

    protected ContentStatus convertContentStatusModelToContentStatus(StatusModel statusModel) {
        ContentStatus contentStatus = null;
        if (statusModel != null) {
            contentStatus = new ContentStatus();
            contentStatus.setText(statusModel.getText());
            contentStatus.setValue(ContentStatusEnum.valueOf(statusModel.getValue()));
        }
        return contentStatus;
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
    public List<ContentModel> getContentModelsByCriteria(FeedCriteria feedCriteria) {
        feedCriteria.setLocale(getCurrentLocale());
        List<Content> contents = contentFeedService.getContentsByFeedCriteria(feedCriteria);
        List<ContentModel> result = null;
        if (contents != null) {
            result = new ArrayList<>(contents.size());
            for (Content content : contents) {
                result.add(convertContentToContentModel(feedCriteria.getContentType(), content, false));
            }
        }
        return result;
    }

    @Override
    public ContentModel getContentModelByContentId(String contentId, ContentEnum contentType) {
        Content content = contentRepository.findOne(contentId);
        return (content == null) ? null : convertContentToContentModel(contentType, content, false);
    }
}

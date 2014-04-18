package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.service.content.ContentFeedService;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.StatusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service(ContentModelService.SERVICE_NAME)
public class ContentModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ContentModelService {

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected ContentFeedService contentFeedService;

    @Autowired
    protected ProfileModelService profileModelService;


    @Override
    public ContentModel convertContent(Content content, boolean isFullModel) {
        return convertContentToContentModel(content, false, isFullModel);
    }

    protected ContentModel convertContentToContentModel(Content content, boolean isOnlyThisLocale, boolean isFullModel) {
        ContentModel contentModel = new ContentModel();
        setContentToContentModel(contentModel, content, getCurrentLocale(), isOnlyThisLocale, isFullModel);
        return contentModel;
    }

    protected void setContentToContentModel(ContentModel contentModel, Content content, Locale locale, boolean isOnlyThisLocale, boolean isFullModel) {

        if (content != null) {
            contentModel.setId(content.getId());
            contentModel.setCreated(convertDate(content.getCreated()));

            if (content.getName() != null)
                contentModel.setName(getMultiLangTextValue(content.getName(), locale, isOnlyThisLocale));

            if (content.getSummary() != null)
                contentModel.setSummary(getMultiLangTextValue(content.getSummary(), locale, isOnlyThisLocale));

            if (content.getDescription() != null)
                contentModel.setDescription(getMultiLangTextValue(content.getDescription(), locale, isOnlyThisLocale));

            contentModel.setOwner(profileModelService.getAccountInformation(content.getOwnerId()));
            contentModel.setCategories(convertCategoriesToModel(content.getCategories()));
            contentModel.setRegions(convertRegionsToModel(content.getRegions()));
            contentModel.setLangs(content.getLangs());

            if (content.getStatus() != null)
                contentModel.setStatus(convertContentStatusToModel(content.getStatus()));

            contentModel.setRating(convertRatingToRatingModel(content.getContentType(), content.getId(), content.getRating(), !isFullModel));

            if (content.getViews() == null) {
                contentModel.setViews(0L);
            } else {
                contentModel.setViews(content.getViews().getCount());
            }

            contentModel.setComments(content.getComments());

            if (isFullModel) {
                contentModel.setEnableFavorite(isEnableFavorite(contentModel.getId()));
            }
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

}

package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.content.ContentFeedService;
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
    public ContentModel convertContent(Content content) {
        return convertContentToContentModel(content.getContentType(), content, false);
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

            contentModel.setOwner(profileModelService.getAccountInformation(content.getOwnerId()));
            contentModel.setCategories(convertCategoriesToModel(content.getCategories()));
            contentModel.setRegions(convertRegionsToModel(content.getRegions()));
            contentModel.setLangs(content.getLangs());

            if (content.getStatus() != null)
                contentModel.setStatus(convertContentStatusToModel(content.getStatus()));

            contentModel.setRating(convertRatingToRatingModel(contentType, content.getId(), content.getRating()));

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

}

package com.heaptrip.web.modelservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.ContentOwner;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.ContentModel;
import com.heaptrip.web.model.content.RatingModel;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.content.StatusModel;
import com.heaptrip.web.model.profile.AccountModel;

@Service
public class ContentModelServiceImpl extends BaseModelTypeConverterServiceImpl implements ContentModelService {



    @Autowired
    ContentService contentService;




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

    protected void setContentToContentModel(ContentEnum contentType, ContentModel contentModel, Content contetnt,
                                            Locale locale, boolean isOnlyThisLocale) {

        if (contetnt != null) {
            contentModel.setId(contetnt.getId());
            contentModel.setCreated(convertDate(contetnt.getCreated()));

            if (contetnt.getImage() != null)
                contentModel.setImage(convertImage(contetnt.getImage()));
            if (contetnt.getViews() == null) {
                contentModel.setViews(0L);
            } else {
                contentModel.setViews(contetnt.getViews().getCount());
            }
            contentModel.setRating(convertRatingToRatingModel(contentType, contetnt.getId(), contetnt.getRating()));
            contentModel.setStatus(convertContentStatusToModel(contetnt.getStatus()));
            if (contetnt.getName() != null)
                contentModel.setName(getMultiLangTextValue(contetnt.getName(), locale, isOnlyThisLocale));
            contentModel.setOwner(convertContentOwnerToModel(contetnt.getOwner()));
            contentModel.setCategories(convertCategoriesToModel(contetnt.getCategories()));
            contentModel.setRegions(convertRegionsToModel(contetnt.getRegions()));
            contentModel.setLangs(contetnt.getLangs());
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


}

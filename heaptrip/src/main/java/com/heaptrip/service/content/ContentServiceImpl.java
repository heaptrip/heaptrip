package com.heaptrip.service.content;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.region.RegionRepository;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.region.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

@Service(ContentService.SERVICE_NAME)
public class ContentServiceImpl implements ContentService {

    @Autowired
    protected ContentRepository contentRepository;

    @Autowired
    protected ImageService imageService;

    @Autowired
    protected ContentSearchService contentSearchService;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected CategoryService categoryService;

    @Autowired
    protected RegionRepository regionRepository;

    @Autowired
    protected RegionService regionService;

    protected void updateCategories(Content content) {
        Set<String> categoryIds = new HashSet<>();
        if (content.getCategories() != null) {
            for (SimpleCategory simpleCategory : content.getCategories()) {
                // set content categories
                Assert.notNull(simpleCategory.getId(), "category.id must not be null");
                Category category = categoryRepository.findOne(simpleCategory.getId());
                Assert.notNull(category, String.format("error category.id: %s", simpleCategory.getId()));
                simpleCategory.setName(category.getName());
                // set all categories
                categoryIds.add(simpleCategory.getId());
                categoryIds.addAll(categoryService.getParentsByCategoryId(simpleCategory.getId()));
            }
        }
        content.setCategoryIds(categoryIds.toArray(new String[categoryIds.size()]));
    }

    protected void updateRegions(Content content) {
        Set<String> regionIds = new HashSet<>();
        if (content.getRegions() != null) {
            for (SimpleRegion simpleRegion : content.getRegions()) {
                // set content regions
                Assert.notNull(simpleRegion.getId(), "region.id must not be null");
                Region region = regionRepository.findOne(simpleRegion.getId());
                Assert.notNull(region, String.format("error region.id: %s", simpleRegion.getId()));
                simpleRegion.setName(region.getName());
                // set all regions
                regionIds.add(simpleRegion.getId());
                regionIds.addAll(regionService.getParentsByRegionId(simpleRegion.getId()));
            }
        }
        content.setRegionIds(regionIds.toArray(new String[regionIds.size()]));
    }

    @Override
    public boolean isOwner(String contentId, String userId) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(userId, "userId must not be null");
        return contentRepository.isOwner(contentId, userId);
    }

    @Override
    public void remove(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        contentRepository.setDeleted(contentId);
    }

    @Override
    public void hardRemove(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        contentRepository.remove(contentId);
    }

    @Override
    public ContentStatus getStatus(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        return contentRepository.getStatus(contentId);
    }

    @Override
    public void setStatus(String contentId, ContentStatus status) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(status, "status must not be null");
        Assert.notNull(status.getValue(), "status value must not be null");

        ContentStatus oldStatus = getStatus(contentId);
        if (oldStatus != null && oldStatus.getValue() != null && oldStatus.getValue().equals(status.getValue())) {
            // status is not changed
            return;
        }

        String[] allowed;

        switch (status.getValue()) {
            case PUBLISHED_ALL:
                allowed = new String[]{Content.ALLOWED_ALL_USERS};
                break;
            case PUBLISHED_FRIENDS:
                // TODO konovalov: add owner friends
                // String ownerId = contentRepository.getOwnerId(contentId);
                allowed = new String[]{Content.ALLOWED_ALL_USERS};
                break;
            default:
                allowed = new String[0];
                break;
        }

        // save status and allowed list
        contentRepository.setStatus(contentId, status, allowed);

        // update whole content (include allowed field) to Apache Solr
        contentSearchService.saveContent(contentId);
    }

    @Override
    public void addAllowed(String ownerId, String userId) {
        Assert.notNull(ownerId, "ownerId must not be null");
        Assert.notNull(userId, "userId must not be null");
        contentRepository.addAllowed(ownerId, userId);
    }

    @Override
    public void removeAllowed(String ownerId, String userId) {
        Assert.notNull(ownerId, "ownerId must not be null");
        Assert.notNull(userId, "userId must not be null");
        contentRepository.removeAllowed(ownerId, userId);
    }

    @Async
    @Override
    public Future<Void> incViews(String contentId, String userIdOrRemoteIp) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(contentId, "userIdOrRemoteIp must not be null");
        contentRepository.incViews(contentId, userIdOrRemoteIp);
        return new AsyncResult<>(null);
    }

    @Override
    public ContentRating getContentRating(String contentId) {
        Assert.notNull(contentId, "contentId must not be null");
        return contentRepository.getRating(contentId);
    }

    @Override
    public void updateContentRatingValue(String contentId, double ratingValue) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(ratingValue, "ratingValue must not be null");
        contentRepository.updateRating(contentId, ratingValue);
    }
}

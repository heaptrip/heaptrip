package com.heaptrip.service.content;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.repository.region.RegionRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.ContentService;
import com.heaptrip.domain.service.region.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

@Service(ContentService.SERVICE_NAME)
public class ContentServiceImpl implements ContentService {

    @Autowired
    protected ContentRepository contentRepository;

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

    @Autowired
    protected AccountStoreService accountStoreService;

    @Autowired
    protected RelationRepository relationRepository;

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
                String ownerId = contentRepository.getOwnerId(contentId);
                allowed = getAllowed4FriendsByAccountId(ownerId);
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

    private String[] getAllowed4FriendsByAccountId(String accountId) {

        Account account = accountStoreService.findOne(accountId);
        Assert.notNull(account.getAccountType(), "account type must not be null");

        List<Relation> relations = null;
        HashSet ids = new HashSet();

        switch (account.getAccountType()) {
            case USER:
                String[] typeRelations = new String[1];
                typeRelations[0] = RelationTypeEnum.FRIEND.toString();

                relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(accountId, typeRelations));
                break;
            case AGENCY:
            case CLUB:
            case COMPANY:
                relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(accountId));
                break;
        }

        if (relations != null && relations.size() > 0) {
            for (Relation relation : relations) {
                if (relation.getUserIds() != null && relation.getUserIds().length > 0) {
                    Collections.addAll(ids, relation.getUserIds());
                }
            }
        }

        return (String[]) ids.toArray();
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
    public void setContentRating(String contentId, ContentRating contentRating) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(contentRating, "contentRating must not be null");
        contentRepository.setRating(contentId, contentRating);
    }

    @Override
    public void updateContentRatingValue(String contentId, double ratingValue) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(ratingValue, "ratingValue must not be null");
        contentRepository.updateRating(contentId, ratingValue);
    }

    @Override
    public boolean canEditContent(String contentId, String userId) {
        Assert.notNull(contentId, "contentId must not be null");
        Assert.notNull(userId, "userId must not be null");
        String ownerId = contentRepository.getOwnerId(contentId);
        if (ownerId.equals(userId)) {
            return true;
        } else {
            Account account = accountStoreService.findOne(ownerId);
            if (!account.getAccountType().equals(AccountEnum.CLUB)) {
                return false;
            } else {
                String[] typeRelations = new String[2];
                typeRelations[0] = RelationTypeEnum.OWNER.toString();
                typeRelations[1] = RelationTypeEnum.EMPLOYEE.toString();

                List<Relation> relations = relationRepository.findByAccountRelationCriteria(new AccountRelationCriteria(account.getId(), typeRelations));
                if (relations != null) {
                    for (Relation relation : relations) {
                        if (relation.getUserIds() != null) {
                            for (String id : relation.getUserIds()) {
                                if (id.equals(userId)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
        }
    }
}

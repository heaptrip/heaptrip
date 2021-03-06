package com.heaptrip.domain.entity.content;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.Collectionable;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.comment.Commentsable;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.entity.region.SimpleRegion;

import java.util.Date;

/**
 * Base entity for trips, posts, questions and events
 */
@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class Content extends BaseObject implements Collectionable, Commentsable {

    public static final String ALLOWED_ALL_USERS = "ALL";

    private static final String COMMENTS_NUMBER_FIELD_NAME = "comments";

    // contain a content categories set by the owner
    private SimpleCategory[] categories;

    // contain a categories IDs set by the owner, and parent for them, need to perform a search
    private String[] categoryIds;

    // contain a content regions set by the owner
    private SimpleRegion[] regions;

    // contain a regions IDs set by the owner, and parent for them, need to perform a search
    private String[] regionIds;

    // content status
    private ContentStatus status;

    // multilingual name of the content
    private MultiLangText name;

    // multilingual short description
    private MultiLangText summary;

    // multilingual description
    private MultiLangText description;

    // date created
    private Date created;

    // date of remove
    private Date deleted;

    // account id for content owner
    private String ownerId;

    // list of user IDs that are allowed to view content. ['ALL'] - allow for all users
    private String[] allowed;

    // language in which the trip was created
    private String mainLang;

    // set of languages ​​that are available for this content
    private String[] langs;

    // viewing information
    private Views views;

    // information about adding to favorites
    private Favorites favorites;

    // content rating
    private ContentRating rating;

    // number of comments
    private long comments;

    @Override
    public String getCollectionName() {
        return CollectionEnum.CONTENTS.getName();
    }

    @Override
    public String getCommentsNumberFieldName() {
        return COMMENTS_NUMBER_FIELD_NAME;
    }

    public abstract ContentEnum getContentType();

    public SimpleCategory[] getCategories() {
        return categories;
    }

    public void setCategories(SimpleCategory[] categories) {
        this.categories = categories;
    }

    public SimpleRegion[] getRegions() {
        return regions;
    }

    public void setRegions(SimpleRegion[] regions) {
        this.regions = regions;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    public MultiLangText getName() {
        return name;
    }

    public void setName(MultiLangText name) {
        this.name = name;
    }

    public MultiLangText getSummary() {
        return summary;
    }

    public void setSummary(MultiLangText summary) {
        this.summary = summary;
    }

    public MultiLangText getDescription() {
        return description;
    }

    public void setDescription(MultiLangText description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public String[] getAllowed() {
        return allowed;
    }

    public void setAllowed(String[] allowed) {
        this.allowed = allowed;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Views getViews() {
        return views;
    }

    public void setViews(Views views) {
        this.views = views;
    }

    public String getMainLang() {
        return mainLang;
    }

    public void setMainLang(String mainLang) {
        this.mainLang = mainLang;
    }

    public String[] getLangs() {
        return langs;
    }

    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public String[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String[] getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(String[] regionIds) {
        this.regionIds = regionIds;
    }

    public ContentRating getRating() {
        return rating;
    }

    public void setRating(ContentRating rating) {
        this.rating = rating;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }
}

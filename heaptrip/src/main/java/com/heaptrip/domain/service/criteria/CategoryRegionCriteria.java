package com.heaptrip.domain.service.criteria;

/**
 * Criteria for search data by categories and regions
 */
public class CategoryRegionCriteria extends LocaleCriteria {

    // criteria for categories
    private IDListCriteria categories;

    // criteria for regions
    private IDListCriteria regions;

    public IDListCriteria getCategories() {
        return categories;
    }

    public void setCategories(IDListCriteria categories) {
        this.categories = categories;
    }

    public IDListCriteria getRegions() {
        return regions;
    }

    public void setRegions(IDListCriteria regions) {
        this.regions = regions;
    }
}

package com.heaptrip.web.model.profile;

import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.RegionModel;

public class AccountProfileModel {

    private RegionModel location;

    private String[] langs;

    private String desc;

    private CategoryModel[] categories;

    private RegionModel[] regions;

    public RegionModel getLocation() {
        return location;
    }

    public void setLocation(RegionModel location) {
        this.location = location;
    }

    public String[] getLangs() {
        return langs;
    }

    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CategoryModel[] getCategories() {
        return categories;
    }

    public void setCategories(CategoryModel[] categories) {
        this.categories = categories;
    }

    public RegionModel[] getRegions() {
        return regions;
    }

    public void setRegions(RegionModel[] regions) {
        this.regions = regions;
    }
}

package com.heaptrip.web.modelservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.service.account.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.entity.region.Region;
import com.heaptrip.domain.entity.region.RegionEnum;
import com.heaptrip.domain.service.category.CategoryService;
import com.heaptrip.domain.service.region.RegionService;
import com.heaptrip.service.system.RequestScopeServiceImpl;
import com.heaptrip.util.tuple.TreObject;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.filter.CategoryTreeModel;
import org.springframework.util.Assert;

@Service
public class FilterModelServiceImpl extends RequestScopeServiceImpl implements FilterModelService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private UserService userService;

    @Override
    public List<CategoryTreeModel> getCategories() {
        List<Category> categories = categoryService.getCategories(getCurrentLocale());
        Map<String, CategoryTreeModel> map = new HashMap<String, CategoryTreeModel>();
        map.put(null, new CategoryTreeModel());
        for (Category category : categories) {
            CategoryTreeModel categoryModel = new CategoryTreeModel();
            categoryModel.setId(category.getId());
            categoryModel.setData(category.getName().getValue(getCurrentLocale()));
            map.put(categoryModel.getId(), categoryModel);
            map.get(category.getParent()).addChildren(map.get(categoryModel.getId()));
        }
        return map.get(null).getChildren();
    }

    @Override
    public List<RegionModel> searchRegionsByText(String text) {
        List<RegionModel> regionModels = new ArrayList<RegionModel>();
        List<Region> regions = regionService.getRegionsByName(text, 0L, 20L, getCurrentLocale());
        if (regions != null) {
            for (Region region : regions) {
                regionModels.add(convertRegionToModel(region));
            }
        }
        return regionModels;
    }

    @Override
    public String[] getUserCategories(String uid) {
        Assert.notNull(uid, "user id  must not be null");
        User user = userService.getUserById(uid);
        Assert.notNull(user, "user not found by uid" + uid);
        List<String> categoryIds = new ArrayList<>();
        if (user.getProfile() != null && user.getProfile().getCategories() != null) {
            for (SimpleCategory category : user.getProfile().getCategories()) {
                categoryIds.add(category.getId());
            }
        }
        return categoryIds.toArray(new String[categoryIds.size()]);
    }

    @Override
    public TreObject<RegionModel, RegionModel, RegionModel> getRegionHierarchy(String regionId) {
        Region region = regionService.getRegionById(regionId, getCurrentLocale());
        Map<RegionEnum, Region> map = new HashMap<RegionEnum, Region>();
        map.put(RegionEnum.COUNTRY, null);
        map.put(RegionEnum.AREA, null);
        map.put(RegionEnum.CITY, null);
        iterateRegions(region, map);
        return new TreObject<RegionModel, RegionModel, RegionModel>(convertRegionToModel(map.get(RegionEnum.COUNTRY)),
                convertRegionToModel(map.get(RegionEnum.AREA)), convertRegionToModel(map.get(RegionEnum.CITY)));
    }

    private void iterateRegions(Region region, Map<RegionEnum, Region> map) {
        map.put(region.getType(), region);
        if (region.getParent() != null) {
            Region regionParent = regionService.getRegionById(region.getParent(), getCurrentLocale());
            if (regionParent != null)
                iterateRegions(regionParent, map);
        }
    }

    private RegionModel convertRegionToModel(Region region) {
        RegionModel regionModel = null;
        if (region != null) {
            regionModel = new RegionModel();
            regionModel.setId(region.getId());
            regionModel.setData(region.getName().getValue(getCurrentLocale()));
            regionModel.setPath(region.getPath().getValue(getCurrentLocale()));
        }
        return regionModel;
    }
}

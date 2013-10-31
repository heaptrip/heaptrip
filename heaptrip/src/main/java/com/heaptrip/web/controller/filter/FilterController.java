package com.heaptrip.web.controller.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.system.RequestScopeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heaptrip.util.http.Ajax;
import com.heaptrip.util.tuple.TreObject;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.filter.CategoryTreeModel;
import com.heaptrip.web.modelservice.FilterModelService;

/**
 * Web контроллер, обеспечивает работу фильтров
 *
 * @author voronenko
 */
@Controller
public class FilterController extends ExceptionHandlerControler {

    @Autowired
    private FilterModelService filterModelService;

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService scopeService;

    private static final Logger LOG = LoggerFactory.getLogger(FilterController.class);

    @RequestMapping(value = "categories", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getCategories(@RequestBody String uid) {

        uid = uid.isEmpty() ? null : uid;

        try {
            List<CategoryTreeModel> categoryModels = filterModelService.getCategories();
            Map<String, Object> result = new HashMap<>();
            result.put("categories", categoryModels);

            if (uid == null) {
                User user = scopeService.getCurrentUser();
                if (user != null) {
                    uid = user.getId();
                }
            }

            if (uid != null) {
                String[] userCategories = filterModelService.getUserCategories(uid);
                if (userCategories != null && userCategories.length > 0) {
                    result.put("userCategories", userCategories);
                }
            }
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "search_regions", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> searchRegionsByText(@RequestBody String text) {

        text = text.isEmpty() ? null : text;

        try {
            List<RegionModel> regionModels = new ArrayList<>();
            if (text != null)
                regionModels = filterModelService.searchRegionsByText(text);
            LOG.trace("END searchRegionsByText");
            return Ajax.successResponse(regionModels);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }


    @RequestMapping(value = "get_user_regions", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getUserRegions(@RequestBody String uid) {
        try {
            Assert.notNull((uid.isEmpty() ? null : uid), "user id must not be null");
            List<String> result = new ArrayList<>();
            String[] regionIds = filterModelService.getUserRegions(uid);
            if (regionIds != null) {
                for (String regionId : regionIds) {
                    result.add(regionId);
                }
            }
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }


    @RequestMapping(value = "get_region_hierarchy", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getRegionHierarchy(@RequestBody String joinRegionIds) {
        try {
            Assert.notNull((joinRegionIds.isEmpty() ? null : joinRegionIds), "region ids must not be null");
            String[] regionIds = joinRegionIds.split(",");
            List<Map> result = new ArrayList<>();
            if (regionIds != null) {
                for (String regionId : regionIds) {
                    Map<String, Object> data = new HashMap<>();
                    TreObject<RegionModel, RegionModel, RegionModel> regions = filterModelService
                            .getRegionHierarchy(regionId);
                    data.put("country", regions.getUno());
                    data.put("area", regions.getDue());
                    data.put("city", regions.getTre());
                    result.add(data);
                }
            }
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

}

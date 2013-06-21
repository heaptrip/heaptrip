package com.heaptrip.web.controller.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heaptrip.util.http.Ajax;
import com.heaptrip.util.tuple.TreObject;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.converter.FilterModelService;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.filter.CategoryTreeModel;

/**
 * 
 * Web контроллер, обеспечивает работу фильтров
 * 
 * @author voronenko
 * 
 */
@Controller
public class FilterController extends ExceptionHandlerControler {

	@Autowired
	private FilterModelService filterModelService;

	private static final Logger LOG = LoggerFactory.getLogger(FilterController.class);

	@RequestMapping(value = "categories", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> getCategories() {
		LOG.trace("CALL getCategories");
		List<CategoryTreeModel> categoryModels = new ArrayList<CategoryTreeModel>();
		try {
			categoryModels = filterModelService.getCategories();
		} catch (Throwable e) {
			throw new RestException(e);
		}
		LOG.trace("END getCategoriesByCriteria");

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("categories", categoryModels);

		String[] userCategories = filterModelService.getUserCategories();

		if (userCategories != null && userCategories.length > 0) {
			result.put("userCategories", userCategories);
		}

		return Ajax.successResponse(result);
	}

	@RequestMapping(value = "search_regions", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> searchRegionsByText(@RequestBody String text) {
		LOG.trace("CALL searchRegionsByText ", text);
		List<RegionModel> regionModels = new ArrayList<RegionModel>();
		try {
			if (text != null)
				regionModels = filterModelService.searchRegionsByText(text);
		} catch (Throwable e) {
			throw new RestException(e);
		}
		LOG.trace("END searchRegionsByText");
		return Ajax.successResponse(regionModels);
	}

	@RequestMapping(value = "get_region_hierarchy", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> getRegionHierarchy(@RequestBody String regionId) {
		LOG.trace("CALL getRegionHierarchy ", regionId);

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (regionId != null) {
				TreObject<RegionModel, RegionModel, RegionModel> regions = filterModelService
						.getRegionHierarchy(regionId);

				result.put("country", regions.getUno());
				result.put("area", regions.getDue());
				result.put("city", regions.getTre());
			}
		} catch (Throwable e) {
			throw new RestException(e);
		}
		LOG.trace("END getRegionHierarchy");
		return Ajax.successResponse(result);
	}

}

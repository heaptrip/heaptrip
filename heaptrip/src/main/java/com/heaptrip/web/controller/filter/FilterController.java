package com.heaptrip.web.controller.filter;

import java.util.ArrayList;
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

import com.heaptrip.domain.service.ContentCriteria;
import com.heaptrip.util.http.Ajax;
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
	Map<String, ? extends Object> getCategoriesByCriteria(@RequestBody ContentCriteria contentCriteria) {
		LOG.trace("CALL getCategoriesByCriteria ", contentCriteria);
		List<CategoryTreeModel> categoryModels = new ArrayList<CategoryTreeModel>();
		try {
			String[] checkedCategoryIds = contentCriteria != null ? contentCriteria.getCategoryIds() : null;
			categoryModels = filterModelService.getCategories(checkedCategoryIds);
		} catch (Throwable e) {
			throw new RestException(e);
		}
		LOG.trace("END getCategoriesByCriteria");
		return Ajax.successResponse(categoryModels);
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

}

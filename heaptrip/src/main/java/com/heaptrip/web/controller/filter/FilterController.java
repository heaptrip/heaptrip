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

import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.converter.FilterModelService;
import com.heaptrip.web.model.filter.CategoryModel;

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
	Map<String, ? extends Object> getTripsByCriteria() {

		List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();

		try {
			categoryModels = filterModelService.getCategories();
		} catch (Throwable e) {
			throw new RestException(e);
		}

		return Ajax.successResponse(categoryModels);
	}
}

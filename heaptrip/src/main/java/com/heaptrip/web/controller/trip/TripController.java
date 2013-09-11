package com.heaptrip.web.controller.trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.converter.TripModelService;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;
import com.heaptrip.web.model.user.RegistrationInfoModel;

/**
 * 
 * Web контроллер, обработка данных о Путешествиях
 * 
 * @author voronenko
 * 
 */
@Controller
public class TripController extends ExceptionHandlerControler {

	@Autowired
	@Qualifier("requestScopeService")
	private RequestScopeService scopeService;

	@Autowired
	private TripService tripService;

	@Autowired
	private TripModelService tripModelService;

	private static final Logger LOG = LoggerFactory.getLogger(TripController.class);

	@RequestMapping(value = "trips", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> getTripsByCriteria(@RequestBody TripFeedCriteria tripFeedCriteria) {
		LOG.trace("CALL getTripsByCriteria ", tripFeedCriteria);
		List<TripModel> tripModels = new ArrayList<TripModel>();
		try {
			tripModels = tripModelService.getTripsModelByCriteria(tripFeedCriteria);
		} catch (Throwable e) {
			throw new RestException(e);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("trips", tripModels);
		result.put("count", tripService.getTripsCountByTripFeedCriteria(tripFeedCriteria));
		LOG.trace("END getTripsByCriteria");
		return Ajax.successResponse(result);
	}

	@RequestMapping(value = "travel_info", method = RequestMethod.GET)
	public ModelAndView getTripInfo(@RequestParam("id") String tripId) {
		ModelAndView mv = new ModelAndView();
		TripInfoModel tripModel = tripModelService.getTripInfoById(tripId);
		return mv.addObject("trip", tripModel);

	}

	@RequestMapping(value = "travel_modify_info", method = RequestMethod.GET)
	public ModelAndView getEditTripInfo(@RequestParam(value = "id", required = false) String tripId) {
		ModelAndView mv = new ModelAndView();
		TripInfoModel tripModel = null;
		if (tripId != null) {
			tripModel = tripModelService.getTripInfoById(tripId);
		}
		return mv.addObject("trip", tripModel);
	}

	@RequestMapping(value = "travel_modify_save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> saveTripInfo(@RequestBody TripInfoModel tripInfoModel) {
		try {
			tripModelService.saveTripInfo(tripInfoModel, scopeService.getCurrentLocale());
		} catch (Throwable e) {
			throw new RestException(e);
		}
		return Ajax.emptyResponse();
	}

	@RequestMapping(value = "travel_modify_update", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> updateTripInfo(@RequestBody TripInfoModel tripInfoModel) {
		try {
			tripModelService.updateTripInfo(tripInfoModel, scopeService.getCurrentLocale());
		} catch (Throwable e) {
			throw new RestException(e);
		}
		return Ajax.emptyResponse();
	}

}

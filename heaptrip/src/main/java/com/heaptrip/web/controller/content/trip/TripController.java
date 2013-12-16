package com.heaptrip.web.controller.content.trip;

import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;
import com.heaptrip.web.modelservice.CommentModelService;
import com.heaptrip.web.modelservice.CountersService;
import com.heaptrip.web.modelservice.TripModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Web контроллер, обработка данных о Путешествиях
 *
 * @author voronenko
 */
@Controller
public class TripController extends ExceptionHandlerControler {

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService scopeService;

    @Autowired
    private TripFeedService tripFeedService;

    @Autowired
    private TripModelService tripModelService;

    @Autowired
    private CommentModelService commentModelService;

    @Autowired
    private CountersService countersService;

    // private static final Logger LOG = LoggerFactory.getLogger(TripController.class);

    @RequestMapping(value = "trips", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getTripsByCriteria(@RequestBody TripFeedCriteria tripFeedCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<TripModel> tripModels = tripModelService.getTripsModelByCriteria(tripFeedCriteria);
            result.put("trips", tripModels);
            result.put("count", tripFeedService.getCountByFeedCriteria(tripFeedCriteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }

    }

    @RequestMapping(value = "travel_info", method = RequestMethod.GET)
    public ModelAndView getTripInfo(@RequestParam(value = "id", required = false) String tripId,
                                    @RequestParam(value = "ul", required = false) String userLocale) {
        return buildTripInfoResponse(tripId, userLocale);
    }

    @RequestMapping(value = "travel_maps", method = RequestMethod.GET)
    public ModelAndView getTripMaps(@RequestParam(value = "id", required = false) String tripId,
                                    @RequestParam(value = "ul", required = false) String userLocale) {
        return buildTripInfoResponse(tripId, userLocale);
    }

    private ModelAndView buildTripInfoResponse(String tripId, String userLocale) {
        ModelAndView mv = new ModelAndView();
        TripInfoModel tripModel = null;
        if (tripId != null) {
            countersService.incViews(tripId);
            if (userLocale == null) {
                tripModel = tripModelService.getTripInfoById(tripId, scopeService.getCurrentLocale(), false);
            } else {
                tripModel = tripModelService.getTripInfoById(tripId, new Locale(userLocale), false);
            }
            mv.addObject("comments", commentModelService.getComments(tripId));
        }
        return mv.addObject("trip", tripModel);
    }

    @RequestMapping(value = "travel_modify_info", method = RequestMethod.GET)
    public ModelAndView getEditTripInfo(@RequestParam(value = "id", required = false) String tripId,
                                        @RequestParam(value = "ul", required = false) String userLocale) {
        ModelAndView mv = new ModelAndView();
        TripInfoModel tripModel = null;
        if (tripId != null) {
            if (userLocale == null) {
                tripModel = tripModelService.getTripInfoById(tripId, scopeService.getCurrentLocale(), false);
            } else {
                tripModel = tripModelService.getTripInfoById(tripId, new Locale(userLocale), true);
            }
        }
        return mv.addObject("trip", tripModel);
    }

    @RequestMapping(value = "security/travel_save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> saveTripInfo(@RequestBody TripInfoModel tripInfoModel) {
        try {
            tripModelService.saveTripInfo(tripInfoModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

    @RequestMapping(value = "security/travel_update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> updateTripInfo(@RequestBody TripInfoModel tripInfoModel) {
        try {
            tripModelService.updateTripInfo(tripInfoModel);
        } catch (Throwable e) {
            throw new RestException(e);
        }
        return Ajax.emptyResponse();
    }

}

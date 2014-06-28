package com.heaptrip.web.controller.content.trip;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.travel.ScheduleParticipantModel;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;
import com.heaptrip.web.model.travel.criteria.TripParticipantsCriteria;
import com.heaptrip.web.modelservice.CommentModelService;
import com.heaptrip.web.modelservice.CountersService;
import com.heaptrip.web.modelservice.TripModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    @Autowired
    private TripUserService tripUserService;

    @Autowired
    private TripService tripService;


    @RequestMapping(value = "security/trip/set_trip_participant_organizer", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> setTripParticipantOrganizer(@RequestBody LinkedHashMap<String, String> params) {
        try {
            Assert.notNull(params, "set trip participant organizer must not be null");
            String memberId = params.get("memberId");
            Boolean isOrganizer = params.get("isOrganizer") != null && params.get("isOrganizer").equals("true");
            Assert.notNull(memberId, "set trip participant organizer memberId must not be null");
            tripUserService.setTripMemberOrganizer(memberId, isOrganizer);
            return Ajax.emptyResponse();
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "security/trip/send_invite_trip_participant", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> sendInviteTripParticipant(@RequestBody LinkedHashMap<String, String> params) {
        try {
            Assert.notNull(params, "add trip participant params must not be null");
            String tripId = params.get("tripId");
            String scheduleId = params.get("scheduleId");
            String userId = params.get("userId");
            Assert.notNull(tripId, "add trip participant tripId must not be null");
            Assert.notNull(scheduleId, "add trip participant scheduleId must not be null");
            Assert.notNull(userId, "add trip participant userId must not be null");
            tripUserService.sendInvite(tripId, scheduleId, userId);
            return Ajax.emptyResponse();
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }


    @RequestMapping(value = "security/trip/send_request_trip_participant", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> sendRequestTripParticipant(@RequestBody LinkedHashMap<String, String> params) {
        try {
            Assert.notNull(params, "add trip participant params must not be null");
            String tripId = params.get("tripId");
            String scheduleId = params.get("scheduleId");
            String userId = params.get("userId");
            Assert.notNull(tripId, "add trip participant tripId must not be null");
            Assert.notNull(scheduleId, "add trip participant scheduleId must not be null");
            Assert.notNull(userId, "add trip participant userId must not be null");
            tripUserService.sendRequest(tripId, scheduleId, userId);
            return Ajax.emptyResponse();
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }


    @RequestMapping(value = "trip/schedule_participants", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getTripScheduleParticipants(@RequestBody TripParticipantsCriteria criteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<ScheduleParticipantModel> scheduleParticipants = tripModelService.getTripScheduleParticipants(criteria);
            result.put("schedules", scheduleParticipants);
            result.put("count", scheduleParticipants.size());
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }


    @RequestMapping(value = "trips", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getTripsByFeedCriteria(@RequestBody TripFeedCriteria tripFeedCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<TripModel> tripModels = tripModelService.getTripsModelByFeedCriteria(tripFeedCriteria);
            result.put("trips", tripModels);
            result.put("count", tripFeedService.getCountByFeedCriteria(tripFeedCriteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "my/trips", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getTripsByMyAccountCriteria(@RequestBody TripMyAccountCriteria tripFeedCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<TripModel> tripModels = tripModelService.getTripsModelByMyAccountCriteria(tripFeedCriteria);
            result.put("trips", tripModels);
            result.put("count", tripFeedService.getCountByMyAccountCriteria(tripFeedCriteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "foreign/trips", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getTripsByForeignAccountCriteria(@RequestBody TripForeignAccountCriteria tripFeedCriteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<TripModel> tripModels = tripModelService.getTripsModelByForeignAccountCriteria(tripFeedCriteria);
            result.put("trips", tripModels);
            result.put("count", tripFeedService.getCountByForeignAccountCriteria(tripFeedCriteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

    @RequestMapping(value = "travel_info", method = RequestMethod.GET)
    public ModelAndView getTripInfo(@RequestParam(value = "id", required = false) String tripId,
                                    @RequestParam(value = "ul", required = false) String userLocale) {
        return buildTripInfoResponse(tripId, userLocale, true);
    }

    @RequestMapping(value = "travel_maps", method = RequestMethod.GET)
    public ModelAndView getTripMaps(@RequestParam(value = "id", required = false) String tripId,
                                    @RequestParam(value = "ul", required = false) String userLocale) {
        return buildTripInfoResponse(tripId, userLocale, true);
    }

    @RequestMapping(value = "travel_participants", method = RequestMethod.GET)
    public ModelAndView getTripParticipants(@RequestParam(value = "id", required = false) String tripId,
                                            @RequestParam(value = "ul", required = false) String userLocale) {
        return buildTripInfoResponse(tripId, userLocale, false);
    }

    private ModelAndView buildTripInfoResponse(String tripId, String userLocale, boolean withComments) {
        ModelAndView mv = new ModelAndView();
        TripInfoModel tripModel = null;
        if (tripId != null) {
            countersService.incViews(tripId);
            if (userLocale == null) {
                tripModel = tripModelService.getTripInfoById(tripId, scopeService.getCurrentLocale(), false);
            } else {
                tripModel = tripModelService.getTripInfoById(tripId, new Locale(userLocale), false);
            }
            if (withComments)
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
            Trip trip = tripModelService.saveTripInfo(tripInfoModel);
            Map<String, Object> result = new HashMap();
            result.put("tripId", trip.getId());
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }
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

    @RequestMapping(value = "security/travel_remove", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> removeTripInfo(@RequestBody String tripId) {
        try {
            tripService.remove(tripId);
            return Ajax.emptyResponse();
        } catch (Throwable e) {
            throw new RestException(e);
        }
    }

}

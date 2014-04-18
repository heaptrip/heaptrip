package com.heaptrip.web.controller.profile;

import com.heaptrip.domain.service.account.criteria.notification.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.notification.CommunityNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.notification.NotificationCriteria;
import com.heaptrip.domain.service.account.notification.NotificationService;
import com.heaptrip.util.http.Ajax;
import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.controller.base.RestException;
import com.heaptrip.web.model.profile.NotificationModel;
import com.heaptrip.web.modelservice.NotificationModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NotificationController extends ExceptionHandlerControler {


    @Autowired
    private NotificationModelService notificationModelService;

    @Autowired
    private NotificationService notificationService;


    @RequestMapping(value = "security/notification/user", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getNotificationByUserCriteria(@RequestBody AccountNotificationCriteria criteria) {
        try {
            Map<String, Object> result = new HashMap();
            List<NotificationModel> notificationModels = notificationModelService.getNotificationByUserCriteria(criteria);
            result.put("notifications", notificationModels);
            result.put("count", notificationService.countByUserNotificationCriteria(criteria));
            return Ajax.successResponse(result);
        } catch (Throwable e) {
            throw new RestException(e);
        }

    }

}

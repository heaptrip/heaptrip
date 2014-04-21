package com.heaptrip.web.modelservice;

import com.heaptrip.domain.service.account.criteria.notification.AccountNotificationCriteria;
import com.heaptrip.domain.service.account.criteria.notification.CommunityNotificationCriteria;
import com.heaptrip.web.model.profile.NotificationModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 18.04.14
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationModelService {

    List<NotificationModel> getNotificationByUserCriteria(AccountNotificationCriteria criteria);

    List<NotificationModel> getNotificationByCommunityCriteria(CommunityNotificationCriteria criteria);

}

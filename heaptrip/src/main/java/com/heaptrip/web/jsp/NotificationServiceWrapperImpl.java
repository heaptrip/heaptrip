package com.heaptrip.web.jsp;

import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.04.14
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
@Service
public class NotificationServiceWrapperImpl implements NotificationServiceWrapper {


    @Override
    public int getUnreadNotificationFromUsers() {
        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getUnreadNotificationFromCommunities() {
        return 2;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

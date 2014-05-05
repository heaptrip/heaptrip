package com.heaptrip.service.account.user;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;

public class UserDataProvider {

    public static final String IMAGE_1 = "penguins.jpg";
    public static final String IMAGE_2 = "tulips.jpg";

    public static String EMAIL_USER_ID = "email";
    public static String EMAIL_USER_NAME = "Email User";
    public static String EMAIL_USER_EMAIL = "emailUser@heaptrip.com";
    public static String EMAIL_USER_EMAIL_NEW = "noreply@heaptrip.com";
    public static String EMAIL_USER_PSWD = "Qwerty2013";
    public static String EMAIL_USER_INCORRECT_PSWD = "short";
    public static String EMAIL_USER_PSWD_NEW = "new_Qwerty2013";

    public static String NET_USER_ID = "net";
    public static String NET_USER_NAME = "Net User";
    public static String NET_USER_EMAIL = "netUser@heaptrip.com";
    public static String NET_USER_PSWD = "qwerty2014";

    public static String NOT_CONFIRMED_USER_ID = "notConfirmed";
    public static String NOT_CONFIRMED_USER_NAME = "Not Confirmed User";
    public static String NOT_CONFIRMED_USER_EMAIL = "notConfirmed@heaptrip.com";
    public static String NOT_CONFIRMED_USER_PSWD = "notConfirmed";

    public static String FAKE_USER_ID = "fake";
    public static String FAKE_USER_EMAIL = "fakeUser@heaptrip.com";
    public static String FAKE_USER_PSWD = "nopassword";

    public static String ACTIVE_USER_ID = "active";
    public static String ACTIVE_USER_PSWD = "activeUser";
    public static String ACTIVE_USER_PSWD_NEW = "activeUserNew";

//    public static String DELETED_USER_ID = "deleted";
//    public static String DELETED_USER_NAME = "Deleted User";
//    public static String DELETED_USER_EMAIL = "deletedUser@heaptrip.com";
//    public static String DELETED_USER_PSWD = "nopassword";

    public static String INCORRECT_EMAIL = "!@#$%";

    public static SocialNetwork[] getNets() {
        return new SocialNetwork[]{getVK()};
    }

    public static SocialNetwork getVK() {
        return new SocialNetwork(SocialNetworkEnum.VK, "123");
    }

    public static SocialNetwork getFB() {
        return new SocialNetwork(SocialNetworkEnum.FB, "345");
    }

    public static SocialNetwork getFakeNet() {
        return new SocialNetwork(SocialNetworkEnum.VK, "fake");
    }

    public static User getEmailUser() {
        User user = new User();
        user.setId(EMAIL_USER_ID);
        user.setName(EMAIL_USER_NAME);
        user.setEmail(EMAIL_USER_EMAIL);
        return user;
    }

    public static User getNetUser() {
        User user = new User();
        user.setId(NET_USER_ID);
        user.setName(NET_USER_NAME);
        user.setEmail(NET_USER_EMAIL);
        user.setNet(getNets());
        return user;
    }

//    public static User getFakeUser() {
//        User user = new User();
//        user.setId(FAKE_USER_ID);
//        user.setEmail(FAKE_USER_EMAIL);
//        return user;
//    }

    public static User getNotConfirmedUser() {
        User user = new User();
        user.setId(NOT_CONFIRMED_USER_ID);
        user.setName(NOT_CONFIRMED_USER_NAME);
        user.setEmail(NOT_CONFIRMED_USER_EMAIL);
        return user;
    }

    public static User getActiveUser() {
        User user = new User();
        user.setId(ACTIVE_USER_ID);
        user.setName("Active User");
        user.setEmail("ActiveUser@heaptrip.com");
        return user;
    }
}

package com.heaptrip.service.account.community;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.community.club.Club;

public class CommunityDataProvider {

    public static String COMMUNITY_ID = "clubOne";
    public static String COMMUNITY_NAME = "clubOne";
    public static String COMMUNITY_EMAIL = "clubOne@heaptrip.com";

    public static String NOT_CONFIRMED_COMMUNITY_ID = "notConfirmedClub";
    public static String NOT_CONFIRMED_COMMUNITY_NAME = "notConfirmedClub";
    public static String NOT_CONFIRMED_COMMUNITY_EMAIL = "notConfirmedClub@heaptrip.com";

    public static String FAKE_COMMUNITY_ID = "fakeClub";
    public static String FAKE_COMMUNITY_EMAIL = "fakeClub@heaptrip.com";

    public static String ACTIVE_COMMUNITY_ID = "active";
    public static String ACTIVE_COMMUNITY_EMAIL = "activeClub@heaptrip.com";

    public static String DELETED_COMMUNITY_ID = "deleted";
    public static String DELETED_COMMUNITY_EMAIL = "deletedClub@heaptrip.com";

    public static Club getClub() {
        Club club = new Club();
        club.setId(COMMUNITY_ID);
        club.setName(COMMUNITY_NAME);
        club.setEmail(COMMUNITY_EMAIL);
        return club;
    }

    public static Club getNotConfirmedClub() {
        Club club = new Club();
        club.setId(NOT_CONFIRMED_COMMUNITY_ID);
        club.setName(NOT_CONFIRMED_COMMUNITY_NAME);
        club.setEmail(NOT_CONFIRMED_COMMUNITY_EMAIL);
        return club;
    }

    public static Club getFakeClub() {
        Club club = new Club();
        club.setId(FAKE_COMMUNITY_ID);
        club.setName(FAKE_COMMUNITY_ID);
        club.setEmail(FAKE_COMMUNITY_EMAIL);
        return club;
    }

    public static Club getActiveClub() {
        Club club = new Club();
        club.setId(ACTIVE_COMMUNITY_ID);
        club.setName(ACTIVE_COMMUNITY_ID);
        club.setEmail(ACTIVE_COMMUNITY_EMAIL);
        club.setStatus(AccountStatusEnum.ACTIVE);
        return club;
    }

    public static Club getDeletedClub() {
        Club club = new Club();
        club.setId(DELETED_COMMUNITY_ID);
        club.setName(DELETED_COMMUNITY_ID);
        club.setEmail(DELETED_COMMUNITY_EMAIL);
        club.setStatus(AccountStatusEnum.DELETED);
        return club;
    }
}

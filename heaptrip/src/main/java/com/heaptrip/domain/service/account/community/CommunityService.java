package com.heaptrip.domain.service.account.community;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.service.account.AccountService;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public interface CommunityService extends AccountService {

    /**
     * Community registration
     *
     * @param community
     * @param locale
     * @return community
     */
    Community registration(Community community, Locale locale);
}

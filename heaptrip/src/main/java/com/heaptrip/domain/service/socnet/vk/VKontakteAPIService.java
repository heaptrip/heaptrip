package com.heaptrip.domain.service.socnet.vk;

import com.heaptrip.domain.entity.socnet.vk.VKAccessToken;
import com.heaptrip.domain.entity.socnet.vk.VKUser;

public interface VKontakteAPIService {
	
	public final String SOC_NET_NAME = "VK";

	VKAccessToken getAccessTokenByClientCode(String code, String redirectUrl);

	VKUser getUser(VKAccessToken accessToken);

}

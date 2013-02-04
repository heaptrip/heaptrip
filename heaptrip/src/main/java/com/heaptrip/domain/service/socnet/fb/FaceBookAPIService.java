package com.heaptrip.domain.service.socnet.fb;

import com.heaptrip.domain.entity.socnet.fb.FBAccessToken;
import com.heaptrip.domain.entity.socnet.fb.FBUser;

public interface FaceBookAPIService {

	FBAccessToken getAccessTokenByClientCode(String code, String redirectUrl);

	FBUser getUser(FBAccessToken accessToken);

}

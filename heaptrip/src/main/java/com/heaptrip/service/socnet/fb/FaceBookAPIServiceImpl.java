package com.heaptrip.service.socnet.fb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.socnet.fb.FBAccessToken;
import com.heaptrip.domain.entity.socnet.fb.FBErrorResponse;
import com.heaptrip.domain.entity.socnet.fb.FBUser;
import com.heaptrip.domain.service.socnet.fb.FaceBookAPIService;
import com.heaptrip.util.http.HttpClient;
import com.heaptrip.util.json.JsonConverter;

/**
 * Documentation
 * 
 * http://developers.facebook.com/docs/guides/web/
 */
@Service
public class FaceBookAPIServiceImpl implements FaceBookAPIService {

	private static final Logger LOG = LoggerFactory.getLogger(FaceBookAPIServiceImpl.class);

	@Value("${socnet.fb.client_id}")
	private String CLIENT_ID;

	@Value("${socnet.fb.client_secret}")
	private String CLIENT_SECRET;

	@Value("${socnet.fb.access_token_url}")
	private String URL_ACCESS_TOKEN;

	@Value("${socnet.fb.method_url}")
	private String URL_METHOD;

	@Override
	public FBAccessToken getAccessTokenByClientCode(String code, String redirectUrl) {

		FBAccessToken token = new FBAccessToken();

		StringBuilder url = new StringBuilder();

		url.append(URL_ACCESS_TOKEN);
		url.append("?");
		url.append("client_id").append("=").append(CLIENT_ID);
		url.append("&");
		url.append("client_secret").append("=").append(CLIENT_SECRET);
		url.append("&");
		url.append("code").append("=").append(code);
		url.append("&");
		url.append("redirect_uri").append("=").append(redirectUrl);

		String responseStr = executeMethod(url.toString());

		// parse responseStr
		// access_token=AAAGyxc7cwpMBAD8TOOJXhqIXSDNbobFWD07Ar35bh7aIKcALL34JlHQ0zZBiDZCAKQVccw0B7hGCRdtQFzB4KqwJZBI072NDBeOZAibcrwZDZD&expires=5183999

		String[] tokenResponseArr = responseStr.split("&");

		for (String tokenResponse : tokenResponseArr) {

			String[] param = tokenResponse.split("=");

			String paramName = param[0];
			String paramValue = param[1];

			if (paramName.equals("access_token")) {
				token.setAccess_token(paramValue);
			} else if (paramName.equals("expires")) {
				token.setExpires(new Long(paramValue));
			}

		}

		return token;

	}

	@Override
	public FBUser getUser(FBAccessToken accessToken) {

		FBUser user = null;

		StringBuilder url = new StringBuilder();

		url.append(URL_METHOD).append("/me");
		url.append("?");
		url.append("access_token").append("=").append(accessToken.getAccess_token());

		String responseJson = executeMethod(url.toString());

		user = new JsonConverter().JSONToObject(responseJson, FBUser.class);

		user.setPicture(URL_METHOD + "/" + user.getId() + "/picture");
		user.setPicture_large(URL_METHOD + "/" + user.getId() + "/picture?type=large");

		return user;

	}

	private String executeMethod(String url) {

		String responseStr = new HttpClient().doStringGet(url.toString());

		if (responseStr == null) {
			return null;
		}

		if (responseStr.indexOf("error") > 0 && responseStr.indexOf("code") > 0) {
			FBErrorResponse errorResponse = new JsonConverter().JSONToObject(responseStr, FBErrorResponse.class);
			throw new RuntimeException("FaceBook request error : " + errorResponse.getError().getCode() + " "
					+ errorResponse.getError().getMessage());
		}

		return responseStr;

	}

}

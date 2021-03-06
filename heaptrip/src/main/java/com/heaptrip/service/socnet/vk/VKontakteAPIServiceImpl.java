package com.heaptrip.service.socnet.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.socnet.vk.VKAccessToken;
import com.heaptrip.domain.entity.socnet.vk.VKUser;
import com.heaptrip.domain.entity.socnet.vk.VKUsersResponse;
import com.heaptrip.domain.service.socnet.vk.VKontakteAPIService;
import com.heaptrip.util.http.HttpClient;
import com.heaptrip.util.json.JsonConverter;

/**
 * Documentation
 * <p/>
 * http://vk.com/pages?oid=-1&p=%D0%90%D0%B2%D1%82%D0%BE%D1%80%D0%B8%D0%B7%D0%B0
 * %D1%86%D0%B8%D1%8F_%D1%81%D0%B0%D0%B9%D1%82%D0%BE%D0%B2
 */
@Service
public class VKontakteAPIServiceImpl implements VKontakteAPIService {


    private static final Logger logger = LoggerFactory.getLogger(VKontakteAPIServiceImpl.class);


    @Value("${socnet.vk.client_id}")
    private String CLIENT_ID;

    @Value("${socnet.vk.client_secret}")
    private String CLIENT_SECRET;

    @Value("${socnet.vk.access_token_url}")
    private String URL_ACCESS_TOKEN;

    @Value("${socnet.vk.method_url}")
    private String URL_METHOD;

    @Override
    public VKAccessToken getAccessTokenByClientCode(String code, String redirectUrl) {

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

        String accessTokenJson = executeMethod(url.toString());

        return new JsonConverter().JSONToObject(accessTokenJson, VKAccessToken.class);

    }

    @Override
    public VKUser getUser(VKAccessToken accessToken) {

        VKUser result = null;

        StringBuilder url = new StringBuilder();

        url.append(URL_METHOD).append("/users.get");
        url.append("?");
        url.append("uids").append("=").append(accessToken.getUser_id());
        url.append("&");
        url.append("access_token").append("=").append(accessToken.getAccess_token());
        url.append("&");
        url.append("fields").append("=");
        url.append("uid").append(",");
        url.append("first_name").append(",");
        url.append("last_name").append(",");
        url.append("sex").append(",");
        url.append("bdate").append(",");
        url.append("country").append(",");
        url.append("city").append(",");
        url.append("timezone").append(",");
        url.append("photo").append(",");
        url.append("photo_medium").append(",");
        url.append("photo_big").append(",");
        url.append("photo_rec");

        VKUsersResponse usersResponse = new JsonConverter().JSONToObject(executeMethod(url.toString()),
                VKUsersResponse.class);

        result = usersResponse.getResponse()[0];

        return result;

    }

    private String executeMethod(String url) {

        String responseJson = new HttpClient().doStringGet(url.toString());

        logger.debug("VKontakte request url : " + url + "\n VKontakte responseJson : " + responseJson);

        if (responseJson == null) {
            return null;
        }

        if (responseJson.indexOf("error_description") > 0 || responseJson.indexOf("error_code") > 0) {
            throw new RuntimeException("VKontakte request error : " + responseJson);
        }

        return responseJson;

    }

}

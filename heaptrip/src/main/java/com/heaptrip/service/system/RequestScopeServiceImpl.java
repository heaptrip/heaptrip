package com.heaptrip.service.system;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.LocaleService;
import com.heaptrip.domain.service.system.RequestScopeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Service("requestScopeService")
public class RequestScopeServiceImpl implements RequestScopeService {

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private LocaleService localeService;

    @Autowired
    private ErrorService errorService;

    @Override
    public User getCurrentUser() {
        User result = null;
        try {
            result = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Throwable e) {
        }
        return result;
    }

    @Override
    public String getCurrentRequestUrl(boolean isWithParameters) {

        String url = null;

        if (request != null) {
            url = getCurrentContextPath() + request.getServletPath();
            if (isWithParameters) {
                url = "?" + request.getQueryString();
            }
        }

        return url;
    }

    @Override
    public String getCurrentContextPath() {
        return (request != null) ? request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() : "http://apptest.heaptrip.com";
    }

    @Override
    public ErrorService getErrorServise() {
        return errorService;
    }

    @Override
    public HttpServletRequest getCurrentRequest() {
        return request;
    }

    @Override
    public Locale getCurrentLocale() {
        return localeService.getCurrentLocale();
    }

    @Override
    public String getMessage(String key) {
        return localeService.getMessage(key);
    }

    @Override
    public String getCurrentRequestIP() {
        String realIP = getCurrentRequest().getHeader("X-Real-IP");
        return StringUtils.isEmpty(realIP) ? getCurrentRequest().getRemoteAddr() : realIP;
    }
}

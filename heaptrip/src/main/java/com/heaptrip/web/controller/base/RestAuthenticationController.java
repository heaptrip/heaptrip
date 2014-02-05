package com.heaptrip.web.controller.base;

import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.service.system.LocaleService;
import com.heaptrip.util.json.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestAuthenticationController {

    @Autowired
    private LocaleService localeService;

    public HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    @RequestMapping(value = "/login-page", method = RequestMethod.GET)
    public ResponseEntity<String> apiLoginPage() {
        return new ResponseEntity<>(localeService.getMessage(ErrorEnum.UNAUTHORIZED.KEY), getJsonHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/authentication-failure", method = RequestMethod.GET)
    public ResponseEntity<String> apiAuthenticationFailure() {
        // return HttpStatus.OK to let your front-end know the request completed (no 401, it will cause you to go back to login again, loops, not good)
        // include some message code to indicate unsuccessful login
        return new ResponseEntity<>("{\"success\" : false, \"message\" : \"authentication-failure\"}", getJsonHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/default-target", method = RequestMethod.GET)
    public ResponseEntity<String> apiDefaultTarget() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userJson = new JsonConverter().objectToJSON(authentication);
        return new ResponseEntity<>(userJson, getJsonHeaders(), HttpStatus.OK);
    }
}
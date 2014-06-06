package com.heaptrip.web.controller.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.util.http.Ajax;

@Controller
public class ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerControler.class);

	@Autowired
	@Qualifier("requestScopeService")
	protected RequestScopeService scopeService;

	@ExceptionHandler(RestException.class)
	public @ResponseBody
	Map<String, ? extends Object> handleRestException(RestException exception) {
		LOG.error("", exception);
		return Ajax.errorResponse(exception);
	}

	@ExceptionHandler(Exception.class)
	public RedirectView handleException(Exception exception) {

		RedirectView redirectView = new RedirectView(scopeService.getCurrentContextPath() + "/ct/error");

		String message = null;

		try {
			message = URLEncoder.encode(exception.getLocalizedMessage(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			message = e.getMessage();
		}

		LOG.error("", exception);
		redirectView.addStaticAttribute("message", message);

		return redirectView;
	}

}

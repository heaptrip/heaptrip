package com.heaptrip.web.controller.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.heaptrip.util.http.Ajax;

@Controller
public class ExceptionHandlerControler {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerControler.class);

	@ExceptionHandler(RestException.class)
	public @ResponseBody
	Map<String, ? extends Object> handleRestException(RestException e) {
		return Ajax.errorResponse(e);
	}

	@ExceptionHandler(Exception.class)
	public RedirectView handleException(Exception ex) {

		RedirectView redirectView = new RedirectView("error.html");

		String message = null;

		try {
			message = URLEncoder.encode(ex.getMessage(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			message = e.getMessage();
		}

		redirectView.addStaticAttribute("message", message);

		return redirectView;
	}

}

package com.heaptrip.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.heaptrip.domain.exception.RestException;
import com.heaptrip.util.http.Ajax;

@Controller
public class BaseControler {

	private static final Logger LOG = LoggerFactory.getLogger(BaseControler.class);

	@ExceptionHandler(RestException.class)
	public @ResponseBody
	Map<String, ? extends Object> handleBusinessException(RestException e) {
		LOG.error("Business error", e);
		return Ajax.errorResponse(e);
	}

	@ExceptionHandler(Exception.class)
	public RedirectView handleException(Exception ex) {
		RedirectView redirectView = new RedirectView("error.html");
		redirectView.addStaticAttribute("errorMessage", ex.getMessage());
		return redirectView;
	}



}

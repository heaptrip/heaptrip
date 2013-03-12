package com.heaptrip.web.controller.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorHandlerControler extends ExceptionHandlerControler{

	private static final Logger LOG = LoggerFactory.getLogger(ErrorHandlerControler.class);

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@RequestMapping(value = "error", method = RequestMethod.GET)
	public ModelAndView errorRedirectPage(@RequestParam("message") String message) {

		ModelAndView model = new ModelAndView();

		try {
			message = URLDecoder.decode(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			message = e.getMessage();
		}

		model.addObject("message", message);

		return model;

	}

}

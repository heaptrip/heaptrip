package com.heaptrip.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heaptrip.web.controller.base.ExceptionHandlerControler;
import com.heaptrip.web.model.post.PostView;
import com.heaptrip.web.model.tiding.TidingView;

@Controller
public class DellTidingController extends ExceptionHandlerControler {

	@RequestMapping(value = "tidings", method = RequestMethod.GET)
	public @ModelAttribute("tidings")
	Collection<TidingView> getTidingsPage() {

		Collection<TidingView> tidings = new ArrayList<TidingView>();

		for (int i = 0; i < 50; i++) {
			PostView post = new PostView();
			post.setName("Пост " + i);
			tidings.add(post);

		}

		return tidings;
	}

}

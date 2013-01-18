package com.heaptrip.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heaptrip.web.model.post.PostView;
import com.heaptrip.web.model.tiding.TidingView;
import com.heaptrip.web.model.travel.TravelView;

@Controller
public class TidingController {

	@RequestMapping(value = "tidings", method = RequestMethod.GET)
	public @ModelAttribute("tidings")
	Collection<TidingView> getTidingsPage() {

		Collection<TidingView> tidings = new ArrayList<TidingView>();

		for (int i = 0; i < 50; i++) {

			PostView post = new PostView();
			post.setName("Пост " + i);
			tidings.add(post);

			TravelView travel = new TravelView();
			travel.setName("Путешествие " + i);
			tidings.add(travel);

		}

		return tidings;
	}

}

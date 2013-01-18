package com.heaptrip.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heaptrip.web.model.travel.TravelView;

@Controller
public class TravelController {

	@RequestMapping(value = "travels", method = RequestMethod.GET)
	public @ModelAttribute("travels")
	Collection<TravelView> getTravelPage() {

		Collection<TravelView> travels = new ArrayList<TravelView>();

		for (int i = 0; i < 100; i++) {

			TravelView travel = new TravelView();

			travel.setName("Путешествие " + i);

			travels.add(travel);
		}

		return travels;
	}

}

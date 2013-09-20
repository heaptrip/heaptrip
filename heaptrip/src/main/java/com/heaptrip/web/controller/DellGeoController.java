package com.heaptrip.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heaptrip.web.controller.base.ExceptionHandlerControler;

@Controller
@RequestMapping({ "/geo" })
@Deprecated
public class DellGeoController extends ExceptionHandlerControler{ 
	final Logger logger = LoggerFactory.getLogger(DellGeoController.class);

	/*@RequestMapping(value = { "{name}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public Travel getTravelByName(@PathVariable String name) {
		logger.info(name);

		Travel travel = new Travel();
		travel.setName(name);
		travel.setDateCreate(new Date());

		User owner = new User("Иван", "Иванов");
		travel.setOwner(owner);

		User[] users = { new User("Петр", "Петров"), new User("Сидр", "Сидоров") };

		travel.setUsers(users);

		return travel;
	}/*

	/**
	 * @param regionName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/searchRegion/{regionName}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public List<String> searchRegion(@PathVariable String regionName) throws Exception {
		logger.info("searchRegion for {} by geoNamesServer {}", regionName, WebService.getGeoNamesServer());

		WebService.setUserName("heaptrip");

		ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
		searchCriteria.setLanguage("ru");
		searchCriteria.setQ(regionName);

		List<String> result = new ArrayList<String>();
		ToponymSearchResult searchResult = WebService.search(searchCriteria);
		for (Toponym toponym : searchResult.getToponyms()) {
			StringBuilder sb = new StringBuilder();

			sb.append("name=").append(toponym.getName()).append(" countryName=").append(toponym.getCountryName())
					.append(" [Latitude, Longitude]=[").append(toponym.getLatitude()).append(",")
					.append(toponym.getLongitude()).append("]");

			String str = sb.toString();

			logger.info(str);

			result.add(sb.toString());
		}

		return result;
	}
}

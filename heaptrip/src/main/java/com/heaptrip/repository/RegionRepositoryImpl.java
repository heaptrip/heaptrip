package com.heaptrip.repository;

import java.util.Locale;

import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Region;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.repository.RegionRepository;
import com.heaptrip.util.LanguageUtils;

@Service
public class RegionRepositoryImpl implements RegionRepository {

	@Autowired
	private MongoContext mongoContext;

	@Override
	public Region findById(String Id) {
		MongoCollection coll = mongoContext.getCollection(Region.COLLECTION_NAME);
		return coll.findOne("{ _id: #}", Id).as(Region.class);
	}

	@Override
	public Region findById(String Id, Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String.format("{'name.%s': 1, 'path.%s': 1, parent: 1, ancestors: 1, type: 1}", lang, lang);
		MongoCollection coll = mongoContext.getCollection(Region.COLLECTION_NAME);
		return coll.findOne("{ _id: #}", Id).projection(fields).as(Region.class);
	}

}

package com.heaptrip.repository;

import java.util.Locale;

import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Region;
import com.heaptrip.domain.repository.RegionRepository;
import com.heaptrip.util.LanguageUtils;

@Service
public class RegionRepositoryImpl extends CrudRepositoryImpl<Region> implements RegionRepository {

	@Override
	public Region findById(String Id, Locale locale) {
		MongoCollection coll = getCollection();
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String.format("{'name.%s': 1, 'path.%s': 1, parent: 1, ancestors: 1, type: 1}", lang, lang);
		return coll.findOne("{ _id: #}", Id).projection(fields).as(Region.class);
	}

	@Override
	protected String getCollectionName() {
		return Region.COLLECTION_NAME;
	}

	@Override
	protected Class<Region> getCollectionClass() {
		return Region.class;
	}

}

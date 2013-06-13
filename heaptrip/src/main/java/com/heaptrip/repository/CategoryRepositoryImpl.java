package com.heaptrip.repository;

import java.util.List;
import java.util.Locale;

import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.util.LanguageUtils;
import com.heaptrip.util.collection.IteratorConverter;

@Service
public class CategoryRepositoryImpl extends BaseRepositoryImpl<Category> implements CategoryRepository {

	@Override
	public List<Category> findAll(Locale locale) {
		MongoCollection coll = getCollection();
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String.format("{'name.%s': 1, parent: 1, ancestors: 1}", lang);
		Iterable<Category> iter = coll.find().projection(fields).sort("{_id: 1}").as(Category.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	protected String getCollectionName() {
		return Category.COLLECTION_NAME;
	}

	@Override
	protected Class<Category> getCollectionClass() {
		return Category.class;
	}

}

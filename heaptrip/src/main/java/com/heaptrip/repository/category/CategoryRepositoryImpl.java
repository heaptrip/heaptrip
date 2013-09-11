package com.heaptrip.repository.category;

import java.util.List;
import java.util.Locale;

import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.category.Category;
import com.heaptrip.domain.repository.category.CategoryRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.heaptrip.util.language.LanguageUtils;

@Service
public class CategoryRepositoryImpl extends CrudRepositoryImpl<Category> implements CategoryRepository {

	@Override
	public Category findById(String id, Locale locale) {
		MongoCollection coll = getCollection();
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String.format("{'name.%s': 1, parent: 1, ancestors: 1}", lang);
		return coll.findOne("{ _id: #}", id).projection(fields).as(Category.class);

	}

	@Override
	public List<Category> findAll(Locale locale) {
		MongoCollection coll = getCollection();
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String fields = String.format("{'name.%s': 1, parent: 1, ancestors: 1}", lang);
		Iterable<Category> iter = coll.find().projection(fields).sort("{_id: 1}").as(Category.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public Category getParentId(String categoryId) {
		MongoCollection coll = getCollection();
		return coll.findOne("{_id: #}", categoryId).projection("{parent: 1}").as(getCollectionClass());
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

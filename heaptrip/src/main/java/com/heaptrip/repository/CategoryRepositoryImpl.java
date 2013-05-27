package com.heaptrip.repository;

import java.util.List;
import java.util.Locale;

import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.util.collection.IteratorConverter;

@Service(CategoryRepository.SERVICE_NAME)
public class CategoryRepositoryImpl implements CategoryRepository {

	@Autowired
	private MongoContext mongoContext;

	@Override
	public void save(Category category) {
		MongoCollection coll = mongoContext.getCollection(Category.COLLECTION_NAME);
		coll.save(category);
	}

	@Override
	public void save(List<Category> categories) {
		MongoCollection coll = mongoContext.getCollection(Category.COLLECTION_NAME);
		for (Category category : categories) {
			coll.save(category);
		}
	}

	@Override
	public Category findById(String Id) {
		MongoCollection coll = mongoContext.getCollection(Category.COLLECTION_NAME);
		return coll.findOne("{ _id : #}", Id).as(Category.class);
	}

	@Override
	public List<Category> findAll(Locale locale) {
		MongoCollection coll = mongoContext.getCollection(Category.COLLECTION_NAME);
		Iterable<Category> iter = coll.find().as(Category.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public void removeAll() {
		MongoCollection coll = mongoContext.getCollection(Category.COLLECTION_NAME);
		coll.remove();
	}

}

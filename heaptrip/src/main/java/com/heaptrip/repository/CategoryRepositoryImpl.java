package com.heaptrip.repository;

import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Category;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.repository.converter.CategoryConveter;
import com.heaptrip.util.collection.IteratorConverter;

@Service(CategoryRepository.SERVICE_NAME)
public class CategoryRepositoryImpl implements CategoryRepository {

	private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Autowired
	private CategoryConveter categoryConveter;

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
	public List<Category> findAll() {
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

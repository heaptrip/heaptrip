package com.heaptrip.repository;

import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.repository.Repository;

public abstract class BaseRepositoryImpl implements Repository {

	@Autowired
	protected MongoContext mongoContext;

	protected abstract String getCollectionName();

	protected MongoCollection getCollection() {
		return mongoContext.getCollection(getCollectionName());
	}

}

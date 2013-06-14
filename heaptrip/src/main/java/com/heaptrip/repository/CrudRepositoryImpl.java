package com.heaptrip.repository;

import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.repository.MongoContext;

public abstract class CrudRepositoryImpl<T extends BaseObject> implements CrudRepository<T> {

	@Autowired
	protected MongoContext mongoContext;

	protected abstract String getCollectionName();

	protected abstract Class<T> getCollectionClass();

	protected MongoCollection getCollection() {
		return mongoContext.getCollection(getCollectionName());
	}

	@Override
	public <S extends T> S save(S entity) {
		MongoCollection coll = getCollection();
		coll.save(entity);
		return entity;
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		MongoCollection coll = getCollection();
		for (S entity : entities) {
			coll.save(entity);
		}
		return entities;
	}

	@Override
	public long count() {
		MongoCollection coll = getCollection();
		return coll.count();
	}

	@Override
	public Iterable<T> findAll() {
		MongoCollection coll = getCollection();
		return coll.find().as(getCollectionClass());
	}

	@Override
	public Iterable<T> findAll(Iterable<String> ids) {
		MongoCollection coll = getCollection();
		return coll.find("{_id: {$in: #}}", ids).as(getCollectionClass());
	}

	@Override
	public T findOne(String id) {
		MongoCollection coll = getCollection();
		return coll.findOne("{_id: #}", id).as(getCollectionClass());
	}

	@Override
	public boolean exists(String id) {
		MongoCollection coll = getCollection();
		long count = coll.count("{_id: #}", id);
		return (count > 0) ? true : false;
	}

	@Override
	public void remove(String id) {
		MongoCollection coll = getCollection();
		coll.remove("{_id: #}", id);
	}

	@Override
	public void remove(T entity) {
		remove(entity.getId());
	}

	@Override
	public void remove(Iterable<? extends T> entities) {
		for (T entity : entities) {
			remove(entity);
		}
	}

	@Override
	public void removeAll() {
		MongoCollection coll = getCollection();
		coll.remove();
	}
}

package com.heaptrip.repository;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.repository.CrudRepository;
import org.jongo.MongoCollection;

public abstract class CrudRepositoryImpl<T extends BaseObject> extends BaseRepositoryImpl implements CrudRepository<T> {

    protected abstract Class<T> getCollectionClass();

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
    public T findOne() {
        MongoCollection coll = getCollection();
        return coll.findOne().as(getCollectionClass());
    }

    @Override
    public boolean exists(String id) {
        MongoCollection coll = getCollection();
        long count = coll.count("{_id: #}", id);
        return count > 0;
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
    public void remove(Iterable<String> ids) {
        MongoCollection coll = getCollection();
        coll.remove("{_id: {$in: #}}", ids);
    }

    @Override
    public void removeAll() {
        MongoCollection coll = getCollection();
        coll.remove();
    }
}

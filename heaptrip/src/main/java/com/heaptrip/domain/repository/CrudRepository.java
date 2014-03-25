package com.heaptrip.domain.repository;

import com.heaptrip.domain.entity.BaseObject;

/**
 * Interface for generic CRUD operations on a repository for a specific domain
 * type.
 *
 * @param <T> domain type
 */
public interface CrudRepository<T extends BaseObject> extends Repository {

    /**
     * Saves a given entity.
     *
     * @param entity entity
     * @return persist entity
     */
    public <S extends T> S save(S entity);

    /**
     * Saves all given entities.
     *
     * @param entities entities
     * @return persist entities
     */
    public <S extends T> Iterable<S> save(Iterable<S> entities);

    /**
     * Returns the number of entities available.
     *
     * @return count
     */
    public long count();

    /**
     * Returns all instances of the type.
     *
     * @return entities
     */
    public Iterable<T> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids collection of entity id
     * @return entities
     */
    public Iterable<T> findAll(Iterable<String> ids);

    /**
     * Retrieves an entity by its id.
     *
     * @param id entity id
     * @return entity
     */
    public T findOne(String id);

    /**
     * Retrieves any first entity.
     *
     * @return entity
     */
    public T findOne();

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id entity id
     * @return true or false
     */
    public boolean exists(String id);

    /**
     * Deletes the entity with the given id.
     *
     * @param id entity id
     */
    public void remove(String id);

    /**
     * Deletes a given entity.
     *
     * @param entity entity
     */
    public void remove(T entity);

    /**
     * Deletes the given entities.
     *
     * @param ids collection of entity id
     */
    public void remove(Iterable<String> ids);

    /**
     * Deletes all entities managed by the repository.
     */
    public void removeAll();
}

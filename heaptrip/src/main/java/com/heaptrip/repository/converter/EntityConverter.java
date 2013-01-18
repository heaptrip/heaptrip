package com.heaptrip.repository.converter;

import com.heaptrip.domain.entity.BaseEntity;
import com.mongodb.DBObject;

public interface EntityConverter<T extends BaseEntity> {

	public static final String POST_CONVERTER = "postConveter";

	public abstract DBObject toDbObject(T entity);

	public abstract T parseDbObject(DBObject dbObject);
}

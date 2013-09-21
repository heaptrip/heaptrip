package com.heaptrip.repository.converter;

import com.heaptrip.domain.entity.content.post.BaseEntity;
import com.mongodb.DBObject;

@Deprecated
public interface EntityConverter<T extends BaseEntity> {

	public static final String POST_CONVERTER = "postConveter";

	public static final String CATEGORY_CONVERTER = "categoryConveter";

	public abstract DBObject toDbObject(T entity);

	public abstract T parseDbObject(DBObject dbObject);
}

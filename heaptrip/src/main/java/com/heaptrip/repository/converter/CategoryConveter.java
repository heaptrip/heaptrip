package com.heaptrip.repository.converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CategoryEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service(EntityConverter.CATEGORY_CONVERTER)
public class CategoryConveter implements EntityConverter<CategoryEntity> {

	@Override
	public DBObject toDbObject(CategoryEntity entity) {
		BasicDBObject dbObject = new BasicDBObject();

		dbObject.append("_id", entity.getId());
		dbObject.append("pId", entity.getParentId());
		dbObject.append("nRu", entity.getNameRu());
		dbObject.append("nEn", entity.getNameEn());

		return dbObject;
	}

	@Override
	public CategoryEntity parseDbObject(DBObject dbObject) {
		BasicDBObject basicObject = new BasicDBObject(dbObject.toMap());

		CategoryEntity entity = new CategoryEntity();

		entity.setId(basicObject.getString("_id"));
		entity.setParentId(basicObject.getString("pId"));
		entity.setNameRu(basicObject.getString("nRu"));
		entity.setNameEn(basicObject.getString("nEn"));

		return entity;
	}
}

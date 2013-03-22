package com.heaptrip.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CategoryEntity;
import com.heaptrip.domain.repository.CategoryRepository;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.repository.converter.CategoryConveter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

@Service(CategoryRepository.SERVICE_NAME)
public class CategoryRepositoryImpl implements CategoryRepository {

	private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Autowired
	private CategoryConveter categoryConveter;

	@Override
	public void save(CategoryEntity category) {
		DBCollection coll = mongoContext.getDbCollection(CategoryEntity.COLLECTION_NAME);
		DBObject dbObject = categoryConveter.toDbObject(category);
		logger.debug("save dbObject: {}", dbObject);
		WriteResult writeResult = coll.insert(dbObject);
		logger.debug("insert writeResult: {}", writeResult);
	}

	@Override
	public void save(List<CategoryEntity> categories) {
		List<DBObject> list = new ArrayList<>();
		for (CategoryEntity category : categories) {
			DBObject dbObject = categoryConveter.toDbObject(category);
			list.add(dbObject);
			logger.debug("save dbObject: {}", dbObject);
		}
		DBCollection coll = mongoContext.getDbCollection(CategoryEntity.COLLECTION_NAME);
		WriteResult writeResult = coll.insert(list);
		logger.debug("insert writeResult: {}", writeResult);
	}

	@Override
	public List<CategoryEntity> findAll() {
		List<CategoryEntity> result = new ArrayList<CategoryEntity>();
		DBCollection coll = mongoContext.getDbCollection(CategoryEntity.COLLECTION_NAME);
		DBCursor dbCursor = coll.find();
		logger.debug("get dbCursor: {}", dbCursor);
		List<DBObject> list = dbCursor.toArray();
		for (DBObject dbObject : list) {
			logger.debug("next dbObject: {}", dbObject);
			CategoryEntity entity = categoryConveter.parseDbObject(dbObject);
			logger.debug("convert to categoryEntity: {}", entity);
			result.add(entity);
		}
		return result;
	}

	@Override
	public void removeAll() {
		DBCollection coll = mongoContext.getDbCollection(CategoryEntity.COLLECTION_NAME);
		WriteResult writeResult = coll.remove(new BasicDBObject());
		logger.debug("remove writeResult: {}", writeResult);
	}
}

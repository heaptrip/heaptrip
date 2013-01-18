package com.heaptrip.repository.post;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.post.PostEntity;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.repository.post.PostRepository;
import com.heaptrip.repository.converter.EntityConverter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service(PostRepository.SERVICE_NAME)
public class PostRepositoryImpl implements PostRepository {

	private static final Logger logger = LoggerFactory.getLogger(PostRepositoryImpl.class);

	private static final String IMAGE_BUCKET = "image";

	@Autowired
	private MongoContext mongoContext;

	@Autowired
	@Qualifier(EntityConverter.POST_CONVERTER)
	private EntityConverter<PostEntity> postConverter;

	@Override
	public void savePost(PostEntity post) {
		logger.debug("Save post: {}", post);
		DBObject dbObject = postConverter.toDbObject(post);
		logger.debug("Convert to dbObject: {}", dbObject);
		DB db = mongoContext.getDb();
		DBCollection coll = db.getCollection(PostEntity.COLLECTION_NAME);
		if (post.getId() == null) {
			WriteResult writeResult = coll.insert(dbObject);
			logger.debug("Insert post writeResult: {}", writeResult);
		} else {
			dbObject.removeField("_id");
			WriteResult writeResult = coll.update(new BasicDBObject().append("_id", new ObjectId(post.getId())),
					dbObject);
			logger.debug("Update (replace) post writeResult: {}", writeResult);
		}
	}

	@Override
	public List<PostEntity> findAll() {
		List<PostEntity> result = new ArrayList<PostEntity>();
		DBCollection coll = mongoContext.getDbCollection(PostEntity.COLLECTION_NAME);
		DBCursor dbCursor = coll.find();
		logger.debug("Get dbCursor: {}", dbCursor);
		while (dbCursor.hasNext()) {
			DBObject dbObject = dbCursor.next();
			logger.debug("Next dbObject: {}", dbObject);
			if (dbObject != null) {
				PostEntity post = postConverter.parseDbObject(dbObject);
				logger.debug("Convert to post: {}", post);
				result.add(post);
			}
		}
		return result;
	}

	@Override
	public PostEntity findById(String id) {
		PostEntity post = null;
		DBCollection coll = mongoContext.getDbCollection(PostEntity.COLLECTION_NAME);
		DBObject query = new BasicDBObject("_id", new ObjectId(id));
		DBObject dbObject = coll.findOne(query);
		logger.debug("Find dbObject: {}", dbObject);
		if (dbObject != null) {
			post = postConverter.parseDbObject(dbObject);
			logger.debug("Convert to post: {}", post);
		}
		return post;
	}

	@Override
	public String saveImage(InputStream is, String fileName) {
		DB db = mongoContext.getDb();
		GridFS gfsPhoto = new GridFS(db, IMAGE_BUCKET);
		GridFSInputFile gfsFile = gfsPhoto.createFile(is);
		gfsFile.setFilename(fileName);
		gfsFile.save();
		return gfsFile.getId().toString();
	}

	@Override
	public InputStream getImage(String fileId) {
		DB db = mongoContext.getDb();
		GridFS gfsPhoto = new GridFS(db, IMAGE_BUCKET);
		GridFSDBFile imageForOutput = gfsPhoto.find(new ObjectId(fileId));
		return imageForOutput.getInputStream();
	}

	@Override
	public void removeById(String id) {
		DBCollection coll = mongoContext.getDbCollection(PostEntity.COLLECTION_NAME);
		DBObject query = new BasicDBObject("_id", new ObjectId(id));
		coll.remove(query);
	}
}

package com.heaptrip.repository.converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.post.PostEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service(EntityConverter.POST_CONVERTER)
public class PostConveter implements EntityConverter<PostEntity> {

	@Override
	public DBObject toDbObject(PostEntity post) {
		BasicDBObject dbObject = new BasicDBObject();
		if (post.getId() != null) {
			dbObject.append("_id", new ObjectId(post.getId()));
		}
		dbObject.append("name", post.getName());
		dbObject.append("dateCreate", post.getDateCreate());
		dbObject.append("photoUrl", post.getPhotoUrl());
		dbObject.append("smallPhotoUrl", post.getSmallPhotoUrl());
		dbObject.append("description", post.getDescription());
		return dbObject;
	}

	@Override
	public PostEntity parseDbObject(DBObject dbObject) {
		BasicDBObject basicObject = new BasicDBObject(dbObject.toMap());
		PostEntity post = new PostEntity();
		if (basicObject.getObjectId("_id") != null) {
			post.setId(basicObject.getObjectId("_id").toString());
		}
		post.setName(basicObject.getString("name"));
		post.setDateCreate(basicObject.getDate("dateCreate"));
		post.setPhotoUrl(basicObject.getString("photoUrl"));
		post.setSmallPhotoUrl(basicObject.getString("smallPhotoUrl"));
		post.setDescription(basicObject.getString("description"));
		return post;
	}
}

package com.heaptrip.repository.converter;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.post.ImageEntity;
import com.heaptrip.domain.entity.content.post.PostEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Deprecated
@Service(EntityConverter.POST_CONVERTER)
public class PostConveter implements EntityConverter<PostEntity> {

	@Override
	public DBObject toDbObject(PostEntity post) {
		BasicDBObject dbObject = new BasicDBObject();

		if (post.getId() != null && !post.getId().isEmpty()) {
			dbObject.append("_id", new ObjectId(post.getId()));
		}

		dbObject.append("name", post.getName());
		dbObject.append("dateCreate", post.getDateCreate());
		dbObject.append("description", post.getDescription());

		List<BasicDBObject> dbImages = imagesToDbObjects(post.getImages());
		if (dbImages != null && !dbImages.isEmpty()) {
			dbObject.append("images", dbImages);
		}

		return dbObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PostEntity parseDbObject(DBObject dbObject) {
		BasicDBObject basicObject = new BasicDBObject(dbObject.toMap());
		PostEntity post = new PostEntity();
		if (basicObject.getObjectId("_id") != null) {
			post.setId(basicObject.getObjectId("_id").toString());
		}

		post.setName(basicObject.getString("name"));
		post.setDateCreate(basicObject.getDate("dateCreate"));
		post.setDescription(basicObject.getString("description"));

		if (basicObject.get("images") != null) {
			List<BasicDBObject> dbImages = (List<BasicDBObject>) basicObject.get("images");
			post.setImages(dbObjectsToImages(dbImages));
		}

		return post;
	}

	private List<BasicDBObject> imagesToDbObjects(List<ImageEntity> images) {
		List<BasicDBObject> dbImages = null;
		if (images != null && !images.isEmpty()) {
			dbImages = new ArrayList<BasicDBObject>();
			for (ImageEntity image : images) {
				BasicDBObject dbImage = new BasicDBObject();
				if (image.getId() != null && !image.getId().isEmpty()) {
					dbImage.append("_id", new ObjectId(image.getId()));
				}
				dbImage.append("name", image.getName());
				dbImage.append("size", image.getSize());
				dbImages.add(dbImage);
			}
		}
		return dbImages;
	}

	private List<ImageEntity> dbObjectsToImages(List<BasicDBObject> dbImages) {
		List<ImageEntity> images = null;
		if (dbImages != null && !dbImages.isEmpty()) {
			images = new ArrayList<ImageEntity>();
			for (BasicDBObject dbImage : dbImages) {
				ImageEntity image = new ImageEntity();
				if (dbImage.getObjectId("_id") != null) {
					image.setId(dbImage.getObjectId("_id").toString());
				}
				image.setName(dbImage.getString("name"));
				image.setSize(dbImage.getLong("size"));
				images.add(image);
			}
		}
		return images;
	}
}
